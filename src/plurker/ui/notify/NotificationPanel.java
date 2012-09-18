/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plurker.ui.notify;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author SkyforceShen
 */
public class NotificationPanel extends javax.swing.JPanel {

    /**
     * Creates new form NotificationPanel
     */
    public NotificationPanel(JComponent component) {
        initComponents();
        this.add(component, java.awt.BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton_Close = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jButton_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plurker/ui/notify/dialog_close.png"))); // NOI18N
        jButton_Close.setBorder(null);
        jButton_Close.setName(""); // NOI18N
        jButton_Close.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/plurker/ui/notify/dialog_cancel.png"))); // NOI18N
        jButton_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CloseActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Close);

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CloseActionPerformed
    }//GEN-LAST:event_jButton_CloseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Close;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    public JButton getjButton_Close() {
        return jButton_Close;
    }
}
