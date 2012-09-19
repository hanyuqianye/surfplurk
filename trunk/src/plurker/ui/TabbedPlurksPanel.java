/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plurker.ui;

import javax.swing.JFrame;
import plurker.source.PlurkPool;

/**
 *
 * @author SkyforceShen
 */
public class TabbedPlurksPanel extends javax.swing.JPanel {

    /**
     * Creates new form TabbedPlurksPanel
     */
    public TabbedPlurksPanel(PlurkPool plurkPool) {
        initComponents();
        this.plurkPool = plurkPool;
    }
    private PlurkPool plurkPool;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_All = new javax.swing.JPanel();
        jPanel_My = new javax.swing.JPanel();
        jPanel_Private = new javax.swing.JPanel();
        jPanel_Response = new javax.swing.JPanel();
        jPanel_Liked = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        javax.swing.GroupLayout jPanel_AllLayout = new javax.swing.GroupLayout(jPanel_All);
        jPanel_All.setLayout(jPanel_AllLayout);
        jPanel_AllLayout.setHorizontalGroup(
            jPanel_AllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        jPanel_AllLayout.setVerticalGroup(
            jPanel_AllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("所有", jPanel_All);

        javax.swing.GroupLayout jPanel_MyLayout = new javax.swing.GroupLayout(jPanel_My);
        jPanel_My.setLayout(jPanel_MyLayout);
        jPanel_MyLayout.setHorizontalGroup(
            jPanel_MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        jPanel_MyLayout.setVerticalGroup(
            jPanel_MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("我的", jPanel_My);

        javax.swing.GroupLayout jPanel_PrivateLayout = new javax.swing.GroupLayout(jPanel_Private);
        jPanel_Private.setLayout(jPanel_PrivateLayout);
        jPanel_PrivateLayout.setHorizontalGroup(
            jPanel_PrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        jPanel_PrivateLayout.setVerticalGroup(
            jPanel_PrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("私人", jPanel_Private);

        javax.swing.GroupLayout jPanel_ResponseLayout = new javax.swing.GroupLayout(jPanel_Response);
        jPanel_Response.setLayout(jPanel_ResponseLayout);
        jPanel_ResponseLayout.setHorizontalGroup(
            jPanel_ResponseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        jPanel_ResponseLayout.setVerticalGroup(
            jPanel_ResponseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("回應", jPanel_Response);

        javax.swing.GroupLayout jPanel_LikedLayout = new javax.swing.GroupLayout(jPanel_Liked);
        jPanel_Liked.setLayout(jPanel_LikedLayout);
        jPanel_LikedLayout.setHorizontalGroup(
            jPanel_LikedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        jPanel_LikedLayout.setVerticalGroup(
            jPanel_LikedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("讚", jPanel_Liked);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_All;
    private javax.swing.JPanel jPanel_Liked;
    private javax.swing.JPanel jPanel_My;
    private javax.swing.JPanel jPanel_Private;
    private javax.swing.JPanel jPanel_Response;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new TabbedPlurksPanel(null));
        frame.pack();
        frame.setVisible(true);
    }
}