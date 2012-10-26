/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plurker.ui;

import plurker.ui.util.GUIUtil;
import com.google.jplurk_oauth.data.Comment;
import com.google.jplurk_oauth.data.Data;
import com.google.jplurk_oauth.data.Plurk;
import com.google.jplurk_oauth.data.Plurk.UnreadType;
import com.google.jplurk_oauth.data.UserInfo;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;
import org.json.JSONException;
import plurker.image.ImageUtils;
import plurker.source.PlurkChangeListener;
import plurker.source.PlurkFormater;
import plurker.source.PlurkPool;
import plurker.ui.tooltip.CommentToolTipPanel;
import plurker.ui.tooltip.PlurkerToolTip;
import plurker.ui.tooltip.ToolTipPanel;
import plurker.ui.util.DirectScroll;
import plurker.ui.util.HyperlinkHandler;
import plurker.util.Utils;

/**
 *
 * @author skyforceshen
 */
public class ContentPanel extends javax.swing.JPanel implements AWTEventListener, PlurkChangeListener {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (newBie) {
            newBie = false;
            newBieTimer = new Timer(16, new NewBieActionListener());
            newBieTimer.start();
        }
    }
    private ContentPanel contentPanel = this;

    class NewBieActionListener implements ActionListener {

        private int blue = PlurkFormater.HighLightColor.getBlue();

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = new Color(255, 255, blue++);
            contentPanel.jEditorPane1.setBackground(c);
            if (255 == blue) {
                newBieTimer.stop();
            }

        }
    }
    private Timer newBieTimer;
    private boolean newBie = false;

    public void setNewBie(boolean newBie) {
        this.newBie = newBie;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        GUIUtil.initGUI();
        JFrame frame = new JFrame();
        String content = Utils.readContent(new File("b.html"));
        frame.setLayout(new java.awt.BorderLayout());

        BufferedImage refreshImage = ImageUtils.loadImageFromURL(ContentPanel.class.getResource("/plurker/ui/resource/1349158187_refresh.png"));
        ContentPanel contentPanel = new ContentPanel(refreshImage);
//        ContentPanel contentPanel = new ContentPanel(content);
        contentPanel.setNewBie(true);
        contentPanel.getTimeLabel().setText("今天");
        contentPanel.getAvatarLabel().setText("1234");
        frame.add(contentPanel, java.awt.BorderLayout.CENTER);
        frame.setSize(300, 100);
//        frame.pack();
        frame.setVisible(true);

        contentPanel.setNofityLabelCount(8);

    }

    public Plurk getPlurk() {
        return plurk;
    }

    public Comment getComment() {
        return comment;
    }
    protected Plurk plurk;
    protected Comment comment;
    protected PlurkPool plurkPool;
    protected int prefferedWidth;

    public PlurkPool getPlurkPool() {
        return plurkPool;
    }

    private void initEditorPane1(String content, int width) {
        jEditorPane1.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);


        if (this.notifyMode) {
            jEditorPane1.setFont(GUIUtil.smallfont);
        } else {
            jEditorPane1.setFont(GUIUtil.font);
        }

        if (null != plurkPool && PlurkerApplication.cacheImage) {
            jEditorPane1.getDocument().putProperty("imageCache", plurkPool.getImageCache());
        }

        jEditorPane1.setText(content);
        updateWidth(width);
    }
    private boolean copyFromOther = false;
    private ContentPanel plurkPanel;

    public ContentPanel getPlurkPanel() {
        return plurkPanel;
    }

    public void setPopupMenuListener(DirectScroll.PopupMenuListener popupMenuListener) {

        jLabel_Avatar.addMouseListener(popupMenuListener);
        jLabel_Avatar.addMouseMotionListener(popupMenuListener);
        jLabel_Time.addMouseListener(popupMenuListener);
        jLabel_Time.addMouseMotionListener(popupMenuListener);
        jEditorPane1.addMouseListener(popupMenuListener);
        jEditorPane1.addMouseMotionListener(popupMenuListener);

    }

    public static enum Type {

        Plurk, FirstOfResponse, Comment, Image, Unknow
    }
    protected Type type;

    public Type getType() {
        return type;
    }

    //plurk panel, 屬於first panel擁有
    //first panel, 屬於comment panel擁有
    public ContentPanel(ContentPanel plurkPanel) {
        this(plurkPanel.plurk, plurkPanel.comment, plurkPanel.plurkPool, plurkPanel.prefferedWidth, plurkPanel.getJEditorPane().getText(), Type.FirstOfResponse);
//        copyFromOther = true;
        this.plurkPanel = plurkPanel;
        plurkPanel.setNotifyLabelNormal();
    }

    public ContentPanel(String content) {
        this(null, null, null, -1, content, Type.Unknow);
    }
    private BufferedImage image;

    public JLabel getjLabel_Image() {
        return jLabel_Image;
    }

    public ContentPanel(BufferedImage image) {
        this(null, null, null, -1, "", Type.Image);
        this.image = image;
        this.jLabel_Image.setVisible(true);
        this.jEditorPane1.setVisible(false);
        this.jPanel_Info.setVisible(false);
        this.jLabel_Avatar.setVisible(false);
        this.jLabel_Notify.setVisible(false);
        jLabel_Image.setIcon(new ImageIcon(image));
        jLabel_Image.setSize(jLabel_Image.getPreferredSize());
    }

    public ContentPanel(String content, int width) {
        this(null, null, null, width, content, Type.Unknow);
    }

    private void initLabel_Notify() {
        if ((Type.Plurk == type || Type.FirstOfResponse == type) && null != plurk) {
            try {
                UnreadType unreadType = plurk.getUnreadType();
                if (UnreadType.Unread == unreadType && Type.FirstOfResponse != type) {
                    this.setNotifyLabel(Long.toString(plurk.getResponseCount()), LabelHighLightColor, true, false);
                } else {
                    this.setNotifyLabel(Long.toString(plurk.getResponseCount()), LabelNormalColor, true, true);
                }
            } catch (JSONException ex) {
                Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private BufferedImage labelAvatarImage;

    private boolean isReplurk() {
        long replurkerId = plurk.getReplurkerId();
        return -1 != replurkerId && 0 != replurkerId;
    }

    private void initLabel_Avatar() {
        if (null == plurkPool) {
            return;
        }
        String profileImage = null;
        try {
            if ((Type.Plurk == type || Type.FirstOfResponse == type) && null != plurk) {
//                long replurkerId = plurk.getReplurkerId();
                long ownerId = isReplurk() ? plurk.getReplurkerId() : plurk.getOwnerId();
                UserInfo userInfo = plurkPool.getUserInfo(ownerId);
                UserInfo.ImageSize imageSize = isMuted() ? UserInfo.ImageSize.Medium.Small : UserInfo.ImageSize.Medium.Medium;
                profileImage = userInfo != null ? userInfo.getProfileImage(imageSize) : null;
            } else if (Type.Comment == type && null != comment) {
                long ownerId = comment.getOwnerId();
                UserInfo userInfo = plurkPool.getUserInfo(ownerId);
                if (null != userInfo) {
                    profileImage = userInfo.getProfileImage(UserInfo.ImageSize.Small);
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (null != profileImage) {
            try {
                URL url = new URL(profileImage);
                labelAvatarImage = plurkPool.getImage(url);
                this.jLabel_Avatar.setIcon(new ImageIcon(labelAvatarImage));
            } catch (MalformedURLException ex) {
                Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private UnreadType unreadType;

    private boolean isMuted() {
        if (null != plurk) {
            if (null == unreadType) {
                try {
                    unreadType = plurk.getUnreadType();
                } catch (JSONException ex) {
                    Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return unreadType == UnreadType.Muted;
        } else {
            return false;
        }

    }

    /**
     *
     * @param content
     */
    protected void initContent(String content) {
        if (null == content) {
            try {
                content = getContent();
            } catch (JSONException ex) {
                Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        initLabel_Avatar();
        initEditorPane1(content, prefferedWidth);
        this.jLabel_Time.setVisible(false);
        this.jLabel_Notify.setVisible(false);
        if (!isMuted() && !notifyMode) {
            initLabel_Notify();
            initLabel_Time();
        }

        //暫時不提供的功能
        this.jLabel_Floor.setVisible(false);
        this.jPanel_Button.setVisible(false);

        this.jEditorPane1.addHyperlinkListener(new PlurkerHyperlinkListener());

        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
    }

    protected ContentPanel(Plurk plurk, Comment comment, PlurkPool plurkPool, int width, String content, Type type) {
        this(plurk, comment, plurkPool, width, content, type, false);
    }
//    private static int SerialID = 0;

    protected ContentPanel(Plurk plurk, Comment comment, PlurkPool plurkPool, int width, String content, Type type, boolean notifyMode) {
        initComponents();
        this.plurk = plurk;
        this.comment = comment;
        this.plurkPool = plurkPool;
        this.prefferedWidth = width;
        this.type = type;
        this.notifyMode = notifyMode;
        initContent(content);
        this.jLabel_Image.setVisible(false);
//        System.out.println(SerialID++);
    }
    private boolean notifyMode = false;

    public void setNotifyMode(boolean notifyMode) {
        this.notifyMode = notifyMode;
    }

    public void setFloor(int floor) {
        if (null != comment) {
            jLabel_Floor.setVisible(true);
            jLabel_Floor.setText(floor + "樓");
        }
    }

    private void initLabel_Time() {
        if (null == plurk) {
//            this.jLabel_Time.setVisible(false);
            return;
        }
        Date postedDate = null;
        try {
            postedDate = plurk.getPostedDate();
        } catch (ParseException ex) {
            Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        String timeText = PlurkFormater.getTimeText(postedDate);
        this.jLabel_Time.setText(timeText);
        this.jLabel_Time.setVisible(true);
    }

    /**
     * Creates new form JEditorPanel
     */
    public ContentPanel(Plurk plurk, PlurkPool plurkPool) {
        this(plurk, null, plurkPool, -1, null, Type.Plurk);
    }

    public ContentPanel(Comment comment, PlurkPool plurkPool, ContentPanel firstPanel, JEditorPane responseInput) {
        this(null, comment, plurkPool, -1, null, Type.Comment);
        this.plurkPanel = firstPanel;
        this.responseInput = responseInput;
    }
    private JEditorPane responseInput;

    protected String getContent() throws JSONException {
        if (null != plurk) {
            return PlurkFormater.getInstance(plurkPool).getContent(plurk);
        } else if (null != comment) {
            return PlurkFormater.getInstance(plurkPool).getContent(comment);
        } else {
            return "N/A";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_Avatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel_Notify = new javax.swing.JLabel();
        jEditorPane1 = new javax.swing.JEditorPane();
        //        jEditorPane1 = new BrowserPane();
        jLabel_Image = new javax.swing.JLabel();
        jPanel_Info = new javax.swing.JPanel();
        jPanel_Button = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_Time = new javax.swing.JLabel();
        jLabel_Floor = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.BorderLayout());

        jLabel_Avatar.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Avatar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabel_Avatar, java.awt.BorderLayout.WEST);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(400, 300));
        jLayeredPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLayeredPane1ComponentResized(evt);
            }
        });

        jLabel_Notify.setBackground(new java.awt.Color(204, 0, 0));
        jLabel_Notify.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jLabel_Notify.setOpaque(true);
        jLabel_Notify.setBounds(384, 0, 0, 2);
        jLayeredPane1.add(jLabel_Notify, javax.swing.JLayeredPane.PALETTE_LAYER);

        jEditorPane1.setEditable(false);
        jEditorPane1.setBorder(null);
        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setPreferredSize(new java.awt.Dimension(400, 300));
        jEditorPane1.setBounds(0, 0, 390, 280);
        jLayeredPane1.add(jEditorPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLabel_Image.setBounds(0, 0, 0, 0);
        jLayeredPane1.add(jLabel_Image, javax.swing.JLayeredPane.PALETTE_LAYER);

        jPanel1.add(jLayeredPane1);

        jPanel_Info.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Info.setLayout(new java.awt.BorderLayout());

        jPanel_Button.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Button.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel1.setText("消音");
        jPanel_Button.add(jLabel1);

        jLabel2.setText("推文");
        jPanel_Button.add(jLabel2);

        jLabel3.setText("轉噗");
        jPanel_Button.add(jLabel3);

        jLabel4.setText("讚");
        jPanel_Button.add(jLabel4);

        jPanel_Info.add(jPanel_Button, java.awt.BorderLayout.SOUTH);

        jLabel_Time.setText(" ");
        jPanel_Info.add(jLabel_Time, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel_Info);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jLabel_Floor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabel_Floor, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
//    private Border lineBorder = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0));
    private void jLayeredPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLayeredPane1ComponentResized
        Dimension size = this.jLayeredPane1.getSize();

        jEditorPane1.setSize(size);
        Dimension notifyPreferredSize = this.jLabel_Notify.getPreferredSize();
        jLabel_Notify.setBounds(size.width - notifyPreferredSize.width, 0, notifyPreferredSize.width, notifyPreferredSize.height);
        if (jLabel_Image.isVisible()) {

            Dimension imagePreferredSize = jLabel_Image.getPreferredSize();
            int x = size.width / 2 - imagePreferredSize.width / 2;
            int y = size.height / 2 - imagePreferredSize.height / 2;
            jLabel_Image.setLocation(x, y);
        }
    }//GEN-LAST:event_jLayeredPane1ComponentResized

    Rectangle getViewportViewRect() {
        Container parent = this.getParent();
        if (null != parent) {
            parent = parent.getParent();
        }
        Rectangle viewRect = null;
        if (null != parent) {
            JViewport viewport = (JViewport) parent;
            viewRect = viewport.getViewRect();
        }
        return viewRect;
    }

    @SuppressWarnings("empty-statement")
    public void updateWidth(int width) {
//        Rectangle viewRect = getViewportViewRect();
        Rectangle bounds = this.getBounds();
        boolean seen = true;// viewRect != null ? viewRect.contains(bounds.x, bounds.y) : false;

        if (seen) {
            Border border = this.getBorder();
            Insets borderInsets = border.getBorderInsets(this);

            int editorPaneWidth = width - (null != labelAvatarImage ? labelAvatarImage.getWidth() : 0) - (borderInsets.left + borderInsets.right);
            String content = jEditorPane1.getText();
            int prefferedHeight = GUIUtil.getContentHeight(content, editorPaneWidth, null != plurkPool ? plurkPool.getImageCache() : null);

            int label2Height = (null != labelAvatarImage ? labelAvatarImage.getHeight() : 0);
            prefferedHeight = Math.max(prefferedHeight, label2Height);
            Dimension preferredSizeOfInfo = this.jPanel_Info.getPreferredSize();
            prefferedHeight += preferredSizeOfInfo.getHeight();

            Dimension fit = new Dimension(editorPaneWidth, prefferedHeight);
            jLayeredPane1.setSize(fit);
            jLayeredPane1.setPreferredSize(fit);
            jEditorPane1.setSize(fit);
            jEditorPane1.setPreferredSize(fit);

            Dimension fitWithBorder = new Dimension(width, prefferedHeight + borderInsets.top + borderInsets.bottom);
            this.setSize(fitWithBorder);
            this.setPreferredSize(fitWithBorder);
        } else {
            int height = this.getHeight();
            Dimension fitWithBorder = new Dimension(width, height);
            this.setSize(fitWithBorder);
            this.setPreferredSize(fitWithBorder);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_Avatar;
    private javax.swing.JLabel jLabel_Floor;
    private javax.swing.JLabel jLabel_Image;
    private javax.swing.JLabel jLabel_Notify;
    private javax.swing.JLabel jLabel_Time;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Button;
    private javax.swing.JPanel jPanel_Info;
    // End of variables declaration//GEN-END:variables

    public JEditorPane getJEditorPane() {
        return jEditorPane1;
    }

    public JLabel getNotifyLabel() {
        return jLabel_Notify;
    }

    public JLabel getAvatarLabel() {
        return jLabel_Avatar;
    }

    public JLabel getTimeLabel() {
        return jLabel_Time;
    }

    public void setNotifyLabel(String text, Color background, boolean isOpaque, boolean withBorder) {
        jLabel_Notify.setText(text);
        this.jLabel_Notify.setVisible(true);

        jLabel_Notify.setBackground(background);
        jLabel_Notify.setOpaque(isOpaque);

    }
    public final static Color LabelHighLightColor = Color.red;
    public final static Color LabelNormalColor = Color.white;

    public void setNofityLabelCount(long count) {
        if (null != plurkPanel) {
            plurkPanel.setNofityLabelCount(count);
        }
        if (Type.Plurk == type && null != plurk) {
            try {
                plurk.setResponseCount(count);
            } catch (JSONException ex) {
                Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.jLabel_Notify.setText(Long.toString(count));
        }
        this.initLabel_Notify();
        this.setNotifyLabelNormal();

        Dimension size = this.jLayeredPane1.getSize();
        Dimension labelPreferredSize = this.jLabel_Notify.getPreferredSize();
        jLabel_Notify.setBounds(size.width - labelPreferredSize.width, 0, labelPreferredSize.width, labelPreferredSize.height);
    }

    public void addNofityLabelCount() {
        try {
            long responseCount = (null != plurk) ? plurk.getResponseCount() : Integer.parseInt(this.jLabel_Notify.getText());
            setNofityLabelCount(responseCount + 1);
        } catch (JSONException ex) {
            Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void minusNofityLabelCount() {
        try {
            long responseCount = plurk.getResponseCount();
            setNofityLabelCount(responseCount - 1);

        } catch (JSONException ex) {
            Logger.getLogger(ContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNotifyLabelNormal() {
        jLabel_Notify.setBackground(LabelNormalColor);
//        jLabel_Notify.setBorder(lineBorder);
        if (null != plurkPanel) {
            plurkPanel.setNotifyLabelNormal();
        }
    }
    private ToolTipPanel toolTipPanel;
    private PlurkerToolTip tooltip;
    private int offsetOfToolTip = 0;

    public void setOffsetOfToolTip(int offsetOfToolTip) {
        this.offsetOfToolTip = offsetOfToolTip;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (null != jEditorPane1) {
            this.jEditorPane1.setBackground(bg);
        }
    }
    private boolean autoHighlight;

    public void setAutoHighlight(boolean autoHighlight) {
        this.autoHighlight = autoHighlight;
    }
    private Color autoHighlightColor = PlurkFormater.HighLightColor;

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent mouseevent = (MouseEvent) event;
            if (mouseevent.getID() == MouseEvent.MOUSE_MOVED) {
                Component component = mouseevent.getComponent();

                if (null != component) {

                    if (null != component && SwingUtilities.isDescendingFrom(component, this)) {
                        //如果是在這個component內就把notify關掉
                        this.jLabel_Notify.setVisible(false);
                        if (null != this.plurkPool && null == toolTipPanel) {
                            if (Type.Plurk == type && null != this.plurk) {
                                //plurk用的小視窗
                                //toolTipPanel = new PlurkToolTipPanel(this.plurkPool, this.plurk, this);
                            }
                            if (null != this.comment && !this.notifyMode) {
                                //回噗的小視窗
                                toolTipPanel = new CommentToolTipPanel(this.plurkPool, this.comment, this, responseInput);
                            }
                        }

                        if (null != toolTipPanel) {
                            tooltip = PlurkerToolTip.getInstance(this.getClass(), toolTipPanel);
                            int width = this.getWidth();
                            Point locationOnScreen = this.getLocationOnScreen();
                            tooltip.setLocation(locationOnScreen.x + width + offsetOfToolTip, locationOnScreen.y);
                            tooltip.begin();
                        }
                        if (this.autoHighlight) {
                            this.setBackground(this.autoHighlightColor);
                        }
                    } else {
                        //如果不是在這個component內就把notify打開
                        if (type == Type.Plurk || type == Type.FirstOfResponse) {
                            this.jLabel_Notify.setVisible(true);
                        }

                        if (this.autoHighlight) {
                            if (component == this.getParent()) {
                                Point locationOnScreen = mouseevent.getLocationOnScreen();
                                SwingUtilities.convertPointFromScreen(locationOnScreen, this);
                                if (!this.contains(locationOnScreen)) {
                                    this.setBackground(Color.white);
                                } else {
                                    this.setBackground(this.autoHighlightColor);
                                }
                            } else {
                                this.setBackground(Color.white);
                            }

                        }

                    }

                } else {
                }

            } else if (mouseevent.getID() == MouseEvent.MOUSE_CLICKED) {
                Component component = mouseevent.getComponent();
                if (null != tooltip && null != component && !SwingUtilities.isDescendingFrom(component, tooltip)) {
                    //有tooltip且不是點在自己身上, 就把tooltip關掉
                    tooltip.setVisible(false);
                }


            } else if (mouseevent.getID() == MouseEvent.MOUSE_WHEEL) {
                if (null != tooltip) {
                    tooltip.setVisible(false);
                }
            }
        }
    }
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void copyContentToClipboard() {
        String text = this.jEditorPane1.getText();
        StringSelection contents = new StringSelection(text);
        clipboard.setContents(contents, null);
    }

    public void copyContentRawToClipboard() {
        String text = "";
        if (null != plurk) {
            text = plurk.getContentRaw();
        } else if (null != comment) {
            text = comment.toString();
        }

        StringSelection contents = new StringSelection(text);
        clipboard.setContents(contents, null);
    }

    public void copyPlurkToClipboard() {
        String text = "";
        if (null != plurk) {
            text = plurk.toString();
        } else if (null != comment) {
            text = comment.toString();
        }

        StringSelection contents = new StringSelection(text);
        clipboard.setContents(contents, null);
    }

    @Override
    public void plurkChange(PlurkChangeListener.Type type, Data data) {
        if (type == PlurkChangeListener.Type.CommentAdd && data instanceof Comment) {
        }

    }
//    boolean inHyperlink = false;

//    boolean isInHyperlink() {
//        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
//        Point location = pointerInfo.getLocation();
//        SwingUtilities.convertPointFromScreen(location, this);
//
//        Component componentAt = this.getComponentAt(location);
//        System.out.println(componentAt);
//        if (null != componentAt && componentAt == this.jEditorPane1) {
//            return this.jEditorPane1.getCursor().getType() == Cursor.HAND_CURSOR;
//        } else {
//            return false;
//        }
//        return false;
//        Cursor cursor = getCursor();
//        Cursor cursor1 = this.jEditorPane1.getCursor();
//        System.out.println(cursor + " " + " " + cursor.getType() + " " + Cursor.HAND_CURSOR);
//        System.out.println(cursor1 + " " + " " + cursor1.getType() + " " + Cursor.HAND_CURSOR);
//        return cursor1.getType() == Cursor.HAND_CURSOR;
//        return cursor == Cursor.HAND_CURSOR;
//        return false;
//    }
    boolean isInHyperlink(MouseEvent mouseevent) {
        if (mouseevent.getSource() == this.jEditorPane1) {
            return inHyperlink;
        } else {
            return false;
        }
    }
    private boolean inHyperlink;
    private HyperlinkEvent hyperlinkEvent;

    public HyperlinkEvent getHyperlinkEvent() {
        return hyperlinkEvent;
    }

    class PlurkerHyperlinkListener implements HyperlinkListener {

        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            hyperlinkEvent = e;
            EventType eventType = e.getEventType();
            URL url = e.getURL();


            if (EventType.ACTIVATED == eventType) {
                HyperlinkHandler.getInstance().hyperlinkUpdate(e);
            } else if (EventType.ENTERED == eventType) {
                inHyperlink = true;
            } else if (EventType.EXITED == eventType) {
                inHyperlink = false;
            }

        }
    }
}
