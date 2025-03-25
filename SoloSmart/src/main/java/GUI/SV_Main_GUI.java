/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dao.BaiKiemTra_DAO;
import Entity.BaiKiemTra;
import java.util.List;

/**
 *
 * @author THANH PHU
 */
public class SV_Main_GUI extends javax.swing.JPanel {

    /**
     * Creates new form SV_Main_GUI
     */
    private BaiKiemTra_DAO bkt_dao;
    public SV_Main_GUI() {
        initComponents();
        bkt_dao= new BaiKiemTra_DAO(Main_GUI.em);
        List<BaiKiemTra> list= bkt_dao.getBaiKiemTraTheoTaiKhoan(Main_GUI.tk.getMaTaiKhoan());
        list.forEach(x->{
            System.out.println(x.toString());
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new Components.Card();

        setOpaque(false);

        card1.setColor1(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Components.Card card1;
    // End of variables declaration//GEN-END:variables
}
