/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.glasspane;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Egg Hsu
 */
public class MyGlassPanel extends javax.swing.JPanel {

//    private final JFrame frame;
//    private Point point = new Point();
//
//    public void setPoint(Point point) {
//        this.point = point;
//    }
    /**
     * Creates new form MyGlassPanel
     */
    public MyGlassPanel() {
        initComponents();
//        this.frame = frame;
        setOpaque(false);
//        jPanel1.addMouseListener(new MouseAdapter() {
//
//            public void mouseEntered(MouseEvent e) {
//                System.out.println("enter");
//            }
//
//            public void mouseExited(MouseEvent e) {
//                System.out.println("exit");
//            }
//        });

    }
//    protected void paintComponent(Graphics g) {
//      
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.GREEN.darker());
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//        int d = 22;
////        g2.fillRect(getWidth() - d, 0, d, d);
//        if (point != null) {
//            g2.fillOval(point.x + d, point.y + d, d, d);
//        }
//        g2.dispose();
//          super.paintComponent(g);
//    }
//    public void eventDispatched(AWTEvent event) {
//        if (event instanceof MouseEvent) {
//            MouseEvent me = (MouseEvent) event;
//
//            if (!SwingUtilities.isDescendingFrom(me.getComponent(), frame)) {
//                return;
//            }
//
////            if (me.getID() == MouseEvent.MOUSE_EXITED && me.getComponent() == frame) {
////                //移出frame的時候 沒有point(不畫?)
////                point = null;
////            } else {
////                //要不然就轉換point, 用來畫?
////                MouseEvent converted = SwingUtilities.convertMouseEvent(me.getComponent(), me, frame.getGlassPane());
////                point = converted.getPoint();
////            }
//
//            repaint();
//        }
//    }
    /**
     * If someone adds a mouseListener to the GlassPane or set a new cursor we
     * expect that he knows what he is doing and return the super.contains(x, y)
     * otherwise we return false to respect the cursors for the underneath
     * components
     */
//    @Override
//    public boolean contains(int x, int y) {
//        if (inGlassPanel) {
//            return true;
//        } else {
////            return super.contains(x, y);
////        }
////        return true;
//            if (getMouseListeners().length == 0 && getMouseMotionListeners().length == 0
//                    && getMouseWheelListeners().length == 0
//                    && getCursor() == Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) {
//                return false;
//            }
//            boolean contains = super.contains(x, y);
//            return contains;
//        }
//    }
    private boolean inGlassPanel = false;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1);

        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);

        jButton1.setText("jButton1");
        jButton1.setDoubleBuffered(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("gogo");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        // TODO add your handling code here:
        System.out.println("enter");
        inGlassPanel = true;
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        // TODO add your handling code here:
        System.out.println("exit");
        inGlassPanel = false;
    }//GEN-LAST:event_jPanel1MouseExited
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
