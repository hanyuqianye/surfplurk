/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plurker.ui;

import com.google.jplurk_oauth.data.*;
import com.google.jplurk_oauth.module.Timeline;
import com.google.jplurk_oauth.skeleton.RequestException;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.scribe.model.Token;
import plurker.source.PlurkPool;
import plurker.source.PlurkSourcer;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.sexydock.tabs.DefaultTab;
import org.sexydock.tabs.ITab;
import org.sexydock.tabs.TabbedPane;
import org.sexydock.tabs.event.ITabbedPaneListener;
import org.sexydock.tabs.event.TabbedPaneEvent;
import org.sexydock.tabs.jhrome.JhromeTabUI;
import plurker.source.PlurkFormater;
import plurker.ui.util.JTrayIcon;
import plurker.ui.util.SheetableJFrameHelper;
import shu.util.Persistence;

/**
 * Plurker分為以下模組, 依照開發優先順序如下 訊息同步 聊天介面 易用介面 Content處理
 *
 * @author skyforce
 */
public class PlurkerApplication extends javax.swing.JFrame implements AWTEventListener, ITabbedPaneListener {

    @Override
    public void eventDispatched(AWTEvent event) {
    }
    public final static boolean debugMode = new File("debug.txt").exists();
    public final static boolean offlineMode = new File("offline.txt").exists();
    public final static boolean cacheImage = true;
    private ResponsePanel currentResponsePanel = new ResponsePanel();

    private DefaultTab addToTabbedPane(String title, ResponsePanel responsePanel, boolean enableCloseButton) {
        title = (null == title) ? responsePanel.getTabTitle() : title;
        DefaultTab tab = new DefaultTab(title, responsePanel);
        responsePanel.setDefaultTab(tab);
        tab.getCloseButton().setVisible(enableCloseButton);
        int tabCount = tabbedPane.getTabCount();
        tabbedPane.addTab(tabCount, tab, false);
        return tab;
    }

    /**
     * Creates new form LayoutTester
     */
    public PlurkerApplication() {
        initComponents();

        jLabel_NewPlurk.setVisible(false);
        jLabel_NewResponse.setVisible(false);
        jLabel_ShowAll.setVisible(false);
        jLabel_MarkAsRead.setVisible(false);
        jPanel_Toolbar.setVisible(false);

        this.editMenu.setVisible(false);
        this.viewMenu.setVisible(false);

        boolean useTabbedPane = true;
        if (useTabbedPane) {
            //jhrome的 tab
            tabbedPane = new TabbedPane();
            tabbedPane.addTabbedPaneListener(this);
            tabbedPane.setBackground(Color.white);

            tabbedPane.setUseUniformWidth(false);
            tabbedPane.getNewTabButton().setVisible(false);


//            currentPlurklTab = addToTabbedPane(Current, currentResponsePanel, false);

//            DefaultTab secondTab = new DefaultTab(NewPlurk, jScrollPane_NewPlurk);
//            secondTab.getCloseButton().setVisible(false);
//            int tabCount = tabbedPane.getTabCount();
//            tabbedPane.addTab(tabCount, secondTab, false);

//            tabbedPane.setSelectedTab(currentPlurklTab);
            jPanel3.add(tabbedPane, java.awt.BorderLayout.CENTER);
        }

        if (usePlurksPanel) {
            plurksPanel = new PlurksPanel(plurkPool, Timeline.Filter.None, true);
            plurksPanel.setPlurker(this);
            jPanel1.add(plurksPanel, java.awt.BorderLayout.CENTER);
            jPanel1.updateUI();
        }

        boolean useSystemTray = true;
        if (useSystemTray && SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit()
                    .getImage("image/plurk tray.png");
            trayicon = new JTrayIcon(image);
            trayicon.setJPopupMenu(this.jPopupMenu1);
//            TrayIcon tray = new TrayIcon(image, "jPlurker", this.popupMenu_SystemTray);
            try {
                systemTray.add(trayicon);
            } catch (AWTException ex) {
                Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class CometChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            PlurkPool pool = (PlurkPool) e.getSource();
            TreeSet<Plurk> newPlurkSet = pool.getNewPlurkSet();
            TreeSet<Comment> newResponseSet = pool.getNewResponseSet();
            int plurksize = newPlurkSet.size();
            int responsesize = newResponseSet.size();
            String message = "有" + ((plurksize != 0) ? plurksize + "則新噗" : "") + " " + ((responsesize != 0) ? responsesize + "則回應" : "");
            trayicon.displayMessage(null, message, TrayIcon.MessageType.INFO);
        }
    }
    private JTrayIcon trayicon;
    public final static String Current = "目前";
    public final static String NewPlurk = "發噗";
    private TabbedPane tabbedPane;
    private DefaultTab currentPlurklTab;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane_NewPlurk = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jEditorPane_NewPlurk = new javax.swing.JEditorPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel_Username = new javax.swing.JLabel();
        jComboBox_Qualifier = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel_NewPlurkNotify = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Exit = new javax.swing.JMenuItem();
        statusBarLabel = new javax.swing.JLabel();
        statusBarLabel.setVisible(false);
        jPanel8 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel_Toolbar = new javax.swing.JPanel();
        jLabel_NewPlurk = new javax.swing.JLabel();
        jLabel_NewResponse = new javax.swing.JLabel();
        jLabel_MarkAsRead = new javax.swing.JLabel();
        jLabel_ShowAll = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loginMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        statusBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jEditorPane_NewPlurk, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("plurker/ui/Bundle"); // NOI18N
        jLabel_Username.setText(bundle.getString("PlurkerApplication.jLabel_Username.text")); // NOI18N
        jPanel5.add(jLabel_Username);

        jComboBox_Qualifier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ":", "愛", "喜歡", "分享", "給", "討厭", "想要", "期待", "需要", "打算", "希望", "問", "已經", "曾經", "好奇", "覺得", "想", "說", "正在" }));
        jComboBox_Qualifier.setSelectedIndex(17);
        jPanel5.add(jComboBox_Qualifier);

        jPanel2.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jButton1.setText(bundle.getString("PlurkerApplication.jButton1.text")); // NOI18N
        jPanel6.add(jButton1, java.awt.BorderLayout.EAST);
        jPanel6.add(jPanel7, java.awt.BorderLayout.WEST);
        jPanel6.add(jLabel_NewPlurkNotify, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jScrollPane_NewPlurk.setViewportView(jPanel2);

        jMenuItem1.setText(bundle.getString("PlurkerApplication.jMenuItem1.text")); // NOI18N
        jPopupMenu1.add(jMenuItem1);
        jPopupMenu1.add(jSeparator1);

        jMenuItem_Exit.setText(bundle.getString("PlurkerApplication.jMenuItem_Exit.text")); // NOI18N
        jMenuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ExitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem_Exit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(bundle.getString("PlurkerApplication.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        statusBarLabel.setText(bundle.getString("PlurkerApplication.statusBarLabel.text")); // NOI18N
        getContentPane().add(statusBarLabel, java.awt.BorderLayout.SOUTH);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(400);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel_Toolbar.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Toolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel_NewPlurk.setText(bundle.getString("PlurkerApplication.jLabel_NewPlurk.text")); // NOI18N
        jLabel_NewPlurk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_NewPlurk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseExited(evt);
            }
        });
        jPanel_Toolbar.add(jLabel_NewPlurk);

        jLabel_NewResponse.setText(bundle.getString("PlurkerApplication.jLabel_NewResponse.text")); // NOI18N
        jLabel_NewResponse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_NewResponse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseExited(evt);
            }
        });
        jPanel_Toolbar.add(jLabel_NewResponse);

        jLabel_MarkAsRead.setText(bundle.getString("PlurkerApplication.jLabel_MarkAsRead.text")); // NOI18N
        jLabel_MarkAsRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MarkAsRead.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseExited(evt);
            }
        });
        jPanel_Toolbar.add(jLabel_MarkAsRead);

        jLabel_ShowAll.setText(bundle.getString("PlurkerApplication.jLabel_ShowAll.text")); // NOI18N
        jLabel_ShowAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ShowAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ShowAllMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_NewResponseMouseExited(evt);
            }
        });
        jPanel_Toolbar.add(jLabel_ShowAll);

        jPanel1.add(jPanel_Toolbar, java.awt.BorderLayout.SOUTH);

        jSplitPane3.setLeftComponent(jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(400, 600));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 600));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setRightComponent(jPanel3);

        jPanel8.add(jSplitPane3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel8, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText(bundle.getString("PlurkerApplication.fileMenu.text")); // NOI18N

        loginMenuItem.setText(bundle.getString("PlurkerApplication.loginMenuItem.text")); // NOI18N
        loginMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loginMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText(bundle.getString("PlurkerApplication.exitMenuItem.text")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText(bundle.getString("PlurkerApplication.editMenu.text")); // NOI18N

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText(bundle.getString("PlurkerApplication.cutMenuItem.text")); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText(bundle.getString("PlurkerApplication.copyMenuItem.text")); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText(bundle.getString("PlurkerApplication.pasteMenuItem.text")); // NOI18N
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText(bundle.getString("PlurkerApplication.deleteMenuItem.text")); // NOI18N
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        viewMenu.setText(bundle.getString("PlurkerApplication.viewMenu.text")); // NOI18N

        statusBarMenuItem.setText(bundle.getString("PlurkerApplication.statusBarMenuItem.text")); // NOI18N
        statusBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusBarMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(statusBarMenuItem);

        menuBar.add(viewMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(bundle.getString("PlurkerApplication.helpMenu.text")); // NOI18N

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText(bundle.getString("PlurkerApplication.aboutMenuItem.text")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void scrollBarAdjustmentValueChanged(AdjustmentEvent e) {
    }

    private void updateUnread() {
        UnreadCount unreadCount = plurkSourcer.getUnreadCount();
        try {
            int all = unreadCount.getAll();
            if (0 != all) {
                jLabel_NewResponse.setVisible(true);
                jLabel_NewResponse.setText(Integer.toString(all) + "則新回應");
            } else {
                jLabel_NewResponse.setVisible(false);
            }
        } catch (JSONException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean usePlurksPanel = true;
    private PlurksPanel plurksPanel;

    private void updatePlurks(int tabIndex) {

        if (/*null == plurkPanels ||*/null == plurkPool) {
            return;
        }
        if (usePlurksPanel) {
            plurksPanel.setPlurkPool(plurkPool);
            plurksPanel.updatePlurks();
            jPanel1.updateUI();
        }

    }
    private Properties properties;

    private static Properties loadProperties(File file) {
        Properties properties = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            properties = new Properties();
            properties.loadFromXML(fis);
        } catch (InvalidPropertiesFormatException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    private void storeProperties(File file, Properties properties) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            properties.storeToXML(fos, "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private final static String CONFIG_FILENAME = "config.xml";
    private final static String TOKEN_KEY = "token key";
    private final static String TOKEN_SECRET = "token secret";
    private final static File configFile = new File(CONFIG_FILENAME);

    private static int getHTTPStatus(String message) {
        String httpStatus = "http status";
        int indexOfHttpStatus = message.indexOf(httpStatus);
        int indexOfComma = message.indexOf(",", indexOfHttpStatus);
        String status = message.substring(indexOfHttpStatus + httpStatus.length(), indexOfComma);
        status = status.trim();
        return Integer.parseInt(status);
    }

    public static PlurkSourcer getPlurkSourcerInstance() {
        if (configFile.exists()) {
            Properties properties = loadProperties(configFile);
            String tokenKey = properties.getProperty(TOKEN_KEY);
            String tokenSecret = properties.getProperty(TOKEN_SECRET);
            if (null != tokenKey) {
//                PlurkSourcer.setDoValidToken(!offlineMode);
                try {
                    PlurkSourcer plurkSourcer = new PlurkSourcer(PlurkSourcer.API_KEY, PlurkSourcer.APP_SECRET, tokenKey, tokenSecret);
                    return plurkSourcer;
                } catch (RequestException | JSONException ex) {
                    Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (configFile.exists()) {
            properties = loadProperties(configFile);
            String tokenKey = properties.getProperty(TOKEN_KEY);
            String tokenSecret = properties.getProperty(TOKEN_SECRET);
            if (null != tokenKey) {
                try {
                    PlurkSourcer.setDoValidToken(!offlineMode);
                    plurkSourcer = new PlurkSourcer(PlurkSourcer.API_KEY, PlurkSourcer.APP_SECRET, tokenKey, tokenSecret);
                    plurkPool = new PlurkPool(plurkSourcer);
                    plurkPool.addCometChangeListener(new CometChangeListener());
                    plurkPool.startComet();
                } catch (RequestException ex) {
                    Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
                    String message = ex.getMessage();
                    int status = getHTTPStatus(message);
                    switch (status) {
                        case HttpURLConnection.HTTP_PROXY_AUTH:
                            JOptionPane.showMessageDialog(this, "407 Error");
                            return;
                        case HttpURLConnection.HTTP_BAD_REQUEST:
                            JOptionPane.showMessageDialog(this, message);
                            break;
                        default:

                    }

                    plurkSourcer = null;
                    this.loginMenuItem.setText("登入Plurk");
                    loginMenuItemActionPerformed(null);
                } catch (JSONException ex) {
                    Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.loginMenuItem.setText("登出Plurk");
                updatePlurks(0);
            }
        } else {
            loginMenuItemActionPerformed(null);
        }


    }//GEN-LAST:event_formWindowOpened

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private Point getCenterLocation(Dimension clientSize) {
        Dimension size = this.getSize();
        Point location = this.getLocation();
        int x = location.x + size.width / 2 - clientSize.width / 2;
        int y = location.y + size.height / 2 - clientSize.height / 2;
        return new Point(x, y);
    }

    private void centerWindow(Window client) {
        Dimension clientSize = client.getSize();
        Point centerLocation = getCenterLocation(clientSize);
        client.setLocation(centerLocation);
    }

    private void loginMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginMenuItemActionPerformed
        if (null == plurkSourcer) {
            //login
            OAuthDialog dialog = new OAuthDialog(this, true);
            centerWindow(dialog);
            dialog.setVisible(true);
//            SheetableJFrameHelper helder = new SheetableJFrameHelper(this);
//            helder.showJDialogAsSheet(dialog);
            
            Token accessToken = dialog.accessToken;
            if (null != accessToken) {
                properties = new Properties();
                properties.setProperty(TOKEN_KEY, accessToken.getToken());
                properties.setProperty(TOKEN_SECRET, accessToken.getSecret());
                storeProperties(configFile, properties);
                try {
                    plurkSourcer = new PlurkSourcer(PlurkSourcer.API_KEY, PlurkSourcer.APP_SECRET, accessToken.getToken(), accessToken.getSecret());
                    plurkPool = new PlurkPool(plurkSourcer);
                    plurkPool.startComet();
                } catch (RequestException | JSONException ex) {
                    Logger.getLogger(PlurkerApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.updatePlurks(0);
                this.loginMenuItem.setText("Logout");
            }
        } else {
            //logout
            plurkSourcer = null;
            properties = loadProperties(configFile);
            properties.remove(TOKEN_KEY);
            properties.remove(TOKEN_SECRET);
            storeProperties(configFile, properties);
            this.loginMenuItem.setText("Login");
        }
    }//GEN-LAST:event_loginMenuItemActionPerformed

    private void statusBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusBarMenuItemActionPerformed
        this.statusBarLabel.setVisible(statusBarMenuItem.isSelected());

    }//GEN-LAST:event_statusBarMenuItemActionPerformed

    private void jLabel_NewResponseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_NewResponseMouseEntered
        JLabel source = (JLabel) evt.getSource();
        String text = source.getText();
        source.setText("<html><u>" + text + "</u></html>");
    }//GEN-LAST:event_jLabel_NewResponseMouseEntered

    private static String removeItalicHTMLTag(String text) {
        int firstu = text.indexOf("<u>");
        int secondu = text.indexOf("</u>");
        return text.substring(firstu + 3, secondu);
    }

    private void jLabel_NewResponseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_NewResponseMouseExited
        JLabel source = (JLabel) evt.getSource();
        String text = source.getText();
        source.setText(removeItalicHTMLTag(text));
    }//GEN-LAST:event_jLabel_NewResponseMouseExited

    private void jLabel_NewResponseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_NewResponseMouseClicked
        jLabel_MarkAsRead.setVisible(true);
        jLabel_ShowAll.setVisible(true);
        jLabel_NewResponse.setVisible(false);
    }//GEN-LAST:event_jLabel_NewResponseMouseClicked

    private void jLabel_ShowAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ShowAllMouseClicked
        jLabel_MarkAsRead.setVisible(false);
        jLabel_ShowAll.setVisible(false);
        jLabel_NewResponse.setVisible(true);
    }//GEN-LAST:event_jLabel_ShowAllMouseClicked
    private AboutFrame about;
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        if (null == about) {
            about = new AboutFrame();
        }
        if (!about.isVisible()) {
            centerWindow(about);
            about.setVisible(true);
        }
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void jMenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem_ExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        GUIUtil.initGUI();

        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlurkerApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlurkerApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlurkerApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlurkerApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                PlurkerApplication plurkerApplication = new PlurkerApplication(); //.setVisible(true);
////                Toolkit.getDefaultToolkit().addAWTEventListener(plurkerApplication, AWTEvent.COMPONENT_EVENT_MASK | AWTEvent.CONTAINER_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
//                plurkerApplication.setVisible(true);
//            }
//        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PlurkerApplication plurkerApplication = new PlurkerApplication(); //.setVisible(true);
                plurkerApplication.setVisible(true);
            }
        });


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox_Qualifier;
    private javax.swing.JEditorPane jEditorPane_NewPlurk;
    private javax.swing.JLabel jLabel_MarkAsRead;
    private javax.swing.JLabel jLabel_NewPlurk;
    private javax.swing.JLabel jLabel_NewPlurkNotify;
    private javax.swing.JLabel jLabel_NewResponse;
    private javax.swing.JLabel jLabel_ShowAll;
    private javax.swing.JLabel jLabel_Username;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem_Exit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel_Toolbar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane_NewPlurk;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JMenuItem loginMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JLabel statusBarLabel;
    private javax.swing.JCheckBoxMenuItem statusBarMenuItem;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
//    Border etchedBorder = javax.swing.BorderFactory.createEtchedBorder();
//    Border lineBorder = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0));
//    private HyperlinkListener hyperlinkListener = new HyperlinkListener() {
//        @Override
//        public void hyperlinkUpdate(HyperlinkEvent e) {
//            try {
//                URI uri = e.getURL().toURI();
//                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                    Desktop.getDesktop().browse(uri);
//                } else if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
//                    statusBarLabel.setText(uri.toString());
//                } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
//                    statusBarLabel.setText(" ");
//                }
//            } catch (URISyntaxException | IOException ex) {
//            }
//        }
//    };
    private PlurkSourcer plurkSourcer = null;
    private PlurkPool plurkPool = null;

    void setCurrentFollow(final ContentPanel contentPanel) {
        if (null == currentPlurklTab) {
            currentPlurklTab = addToTabbedPane(Current, currentResponsePanel, false);
        }

        currentResponsePanel.setRootContentPanel(contentPanel);
        tabbedPane.setSelectedTab(currentPlurklTab);
        if (debugMode && !new File("plurk.obj").exists()) {
            Persistence.writeObjectAsXML(contentPanel.getPlurk(), "plurk.obj");
        }
    }

    void addNewFollow(ContentPanel plurkPanel) {
        ResponsePanel responsePanel = new ResponsePanel();
        responsePanel.setRootContentPanel(plurkPanel);
        DefaultTab tab = addToTabbedPane(null, responsePanel, true);
        tabbedPane.setSelectedTab(tab);
    }

    @Override
    public void onEvent(TabbedPaneEvent event) {
        TabbedPane tabbedPane1 = event.getTabbedPane();
        ITab selectedTab = tabbedPane1.getSelectedTab();
        if (selectedTab instanceof DefaultTab) {
            JhromeTabUI ui = new JhromeTabUI();
            DefaultTab defaultTab = (DefaultTab) selectedTab;
            defaultTab.setUI(ui);

        }
    }
}
