/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

/**
 *
 * @author Admin
 */
public class GroupCard extends javax.swing.JPanel {

    /**
     * Creates new form GroupCard
     */
    public GroupCard() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new Components.RoundedPanel();
        title = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        card3 = new Components.Card();
        card1 = new Components.Card();
        card2 = new Components.Card();
        card5 = new Components.Card();
        card4 = new Components.Card();
        card6 = new Components.Card();

        setLayout(new java.awt.BorderLayout());

        roundedPanel1.setBackground(new java.awt.Color(61, 141, 122));
        roundedPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundedPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        title.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Title");
        roundedPanel1.add(title);

        add(roundedPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setLayout(new java.awt.GridLayout(2, 3, 10, 10));
        jPanel3.add(card3);
        jPanel3.add(card1);
        jPanel3.add(card2);
        jPanel3.add(card5);
        jPanel3.add(card4);
        jPanel3.add(card6);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Components.Card card1;
    private Components.Card card2;
    private Components.Card card3;
    private Components.Card card4;
    private Components.Card card5;
    private Components.Card card6;
    private javax.swing.JPanel jPanel3;
    private Components.RoundedPanel roundedPanel1;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
