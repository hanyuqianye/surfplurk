/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plurker.ui.notify;

import com.google.jplurk_oauth.data.Plurk;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import plurker.source.PlurkPool;
import plurker.ui.NotifyPanel;
import plurker.ui.util.GUIUtil;
import shu.util.Persistence;

/**
 *
 * @author SkyforceShen
 */
public class NotificationsPanel extends javax.swing.JPanel implements AWTEventListener {

    /**
     * Creates new form NotificationsPanel
     */
    public NotificationsPanel() {
        initComponents();
        this.setSize(this.getPreferredSize());
        this.jPanel3.setVisible(false);
//        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent mouseevent = (MouseEvent) event;
            if (mouseevent.getID() == MouseEvent.MOUSE_WHEEL) {
                if (GUIUtil.isMouseInWindow(this.getBounds())) {
                }
            }

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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton_Close = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(300, 475));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setToolTipText("");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("所有更新", jScrollPane1);

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(jPanel2);

        jTabbedPane1.addTab("追蹤中", jScrollPane2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plurker/ui/resource/dialog_close.png"))); // NOI18N
        jButton_Close.setBorder(null);
        jButton_Close.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/plurker/ui/resource/dialog_cancel.png"))); // NOI18N
        jPanel3.add(jButton_Close, java.awt.BorderLayout.EAST);
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void alterToFitsize(NotifyPanel notify) {
        int width = jPanel1.getWidth();
        int contentHeight = getContentHeight(notify, width);
        Dimension size = new Dimension(width, contentHeight);
        notify.setSize(width, contentHeight);
        notify.setPreferredSize(size);
    }

    public void addToAll(NotifyPanel notify) {
        alterToFitsize(notify);
        jPanel1.add(notify);
    }

    private static int getContentHeight(NotifyPanel notify, int width) {
//        TinyNotificationPanel tmppanel = new TinyNotificationPanel(component);
        notify.setSize(width, Short.MAX_VALUE);
        return notify.getPreferredSize().height;
    }

    public void addToFollow(NotifyPanel notify) {
        jPanel2.add(notify);
    }

    public static void main(String[] args) {


        PlurkPool plurkpool = null;// new PlurkPool(plurkSourcerInstance);
        Plurk plurk = (Plurk) Persistence.readObjectAsXML("plurk.obj");
//        NotifyPanel notifyPanel2 = new NotifyPanel("1234", 300);


//        NotificationsPanel panel = new NotificationsPanel();
//        NotificationsWindow window1 = panel.getWindow();
//        window1.setDragable(true);
//        window1.setVisible(true);

//        NotificationsFrame frame = new NotificationsFrame();
//        frame.setVisible(true);
//        window1.frame = frame;
NotificationManager notifyManager = NotificationManager.getInstance();
        for (int x = 0; x < 15; x++) {
            NotifyPanel notifyPanel2 = new NotifyPanel(plurk, plurkpool);
//            notifyPanel2.updateWidth(300);
//            panel.addToAll(notifyPanel2);
            notifyManager.addtToNotificationsWindow(notifyPanel2);
//            frame.addToAll((NotifyPanel) notifyPanel2.clone());
        }


//        panel.addToFollow(notifyPanel2);
    }

    public NotificationsWindow getWindow() {
        if (null == window) {
            window = new NotificationsWindow(this);
            Dimension size = this.getSize();
            window.setSize(this.getSize());
            jButton_Close.addActionListener(window);
        }
        return window;
    }
    private NotificationsWindow window;

    class NotificationsWindow extends StandardWindow {

        private NotificationsPanel notificationsPanel;

        NotificationsWindow(NotificationsPanel notificationsPanel) {
            super(notificationsPanel, jPanel3);
            this.notificationsPanel = notificationsPanel;
            getContentPane().add(notificationsPanel);
//            notificationsPanel.jButton_Close.addActionListener(this);
            //            this.addMouseMotionListener(null);
            NotifyMouseAdapter notifyMouseAdapter = new NotifyMouseAdapter();
            this.addMouseListener(notifyMouseAdapter);
            this.addMouseMotionListener(notifyMouseAdapter);
        }
        private boolean dragable = false;

        public void setDragable(boolean dragable) {
            this.dragable = dragable;
        }

        class NotifyMouseAdapter extends MouseAdapter {

            public void mousePressed(MouseEvent e) {
                dragStartpoint = e.getPoint();
            }
            private Point dragStartpoint;

            public void mouseDragged(MouseEvent e) {
                if (null != dragStartpoint) {
                    Point locationOnScreen = e.getLocationOnScreen();
                    int x = locationOnScreen.x - dragStartpoint.x;
                    int y = locationOnScreen.y - dragStartpoint.y;
                    setLocation(x, y);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_Close) {
                this.setVisible(false);
            }
        }
    }
}
