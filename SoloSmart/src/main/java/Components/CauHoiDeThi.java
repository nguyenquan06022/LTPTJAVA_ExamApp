/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

import Dao.CauHoi_DAO;
import Dao.ICauHoi_DAO;
import Entity.CauHoi;
import Entity.DeThi;
import Entity.LuaChons;
import GUI.Main_GUI;
import service.RmiServiceLocator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class CauHoiDeThi extends javax.swing.JPanel {
    private ICauHoi_DAO ICauHoi_DAO = RmiServiceLocator.getCauHoiDao();
    private String maDeThi;
    private String maCauHoi;
    /**
     * Creates new form CauHoiDeThi
     */
    public CauHoiDeThi() throws RemoteException {
        initComponents();
        answer2.getRoundedRectPanel1().setText("B.");
        answer3.getRoundedRectPanel1().setText("C.");
        answer4.getRoundedRectPanel1().setText("D.");
    }

    public List<String> getDsLuaChonCu() {
        List<String> dsLuaChonCu = new ArrayList<>();
        dsLuaChonCu.add(answer1.getLuaChonCu());
        dsLuaChonCu.add(answer2.getLuaChonCu());
        dsLuaChonCu.add(answer3.getLuaChonCu());
        dsLuaChonCu.add(answer4.getLuaChonCu());
        return dsLuaChonCu;
    }
    
    public CauHoiDeThi(List<LuaChons> dsLuaChon) throws RemoteException {
        initComponents();
        answer1.setValueForUpdate(dsLuaChon.get(0).getLuaChon().substring(dsLuaChon.get(0).getLuaChon().indexOf(".") + 1).trim());
        answer2.setValueForUpdate(dsLuaChon.get(1).getLuaChon().substring(dsLuaChon.get(1).getLuaChon().indexOf(".") + 1).trim());
        answer3.setValueForUpdate(dsLuaChon.get(2).getLuaChon().substring(dsLuaChon.get(2).getLuaChon().indexOf(".") + 1).trim());
        answer4.setValueForUpdate(dsLuaChon.get(3).getLuaChon().substring(dsLuaChon.get(3).getLuaChon().indexOf(".") + 1).trim());
        answer2.getRoundedRectPanel1().setText("B.");
        answer3.getRoundedRectPanel1().setText("C.");
        answer4.getRoundedRectPanel1().setText("D.");
        
        int dapAnDungIndex = -1;
        for (int i = 0; i < dsLuaChon.size(); i++) {
            if (dsLuaChon.get(i).isDapAnDung()) {
                dapAnDungIndex = i;
                break;
            }
        }
        comboBoxSuggestion1.setSelectedIndex(dapAnDungIndex);
    }
    
    public CauHoiDeThi(String maDeThi) throws RemoteException {
        initComponents();
        answer2.getRoundedRectPanel1().setText("B.");
        answer3.getRoundedRectPanel1().setText("C.");
        answer4.getRoundedRectPanel1().setText("D.");
        this.maDeThi = maDeThi;
    }
    
    public CauHoi getCauHoi() throws RemoteException {
        String luaChon1 = answer1.getValue();
        String luaChon2 = answer2.getValue();
        String luaChon3 = answer3.getValue();
        String luaChon4 = answer4.getValue();
        CauHoi cauHoi;
        
        LuaChons lc1 = new LuaChons(luaChon1,false);
        LuaChons lc2 = new LuaChons(luaChon2,false);
        LuaChons lc3 = new LuaChons(luaChon3,false);
        LuaChons lc4 = new LuaChons(luaChon4,false);
        
        
        int indexDapAn = comboBoxSuggestion1.getSelectedIndex() + 1;
        switch (indexDapAn) {
            case 1:
                lc1.setDapAnDung(true);
                break;
            case 2:
                lc2.setDapAnDung(true);
                break;
            case 3:
                lc3.setDapAnDung(true);
                break;
            case 4:
                lc4.setDapAnDung(true);
                break;
            default:
                throw new AssertionError();
        }

        String noiDungCauHoi = jTextArea1.getText().trim();

        List<LuaChons> dsLuaChon = new ArrayList<>();
        dsLuaChon.add(lc1);
        dsLuaChon.add(lc2);
        dsLuaChon.add(lc3);
        dsLuaChon.add(lc4);
        
        String loiGiai = tfLoiGiai.getText().trim();
        
        cauHoi = new CauHoi(ICauHoi_DAO.generateMa(),"vận dụng",jTextArea1.getText().trim(),1,dsLuaChon,loiGiai,"enable",new DeThi());
        try {
            Thread.sleep(300); // dừng 300 mili giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cauHoi;
    }

    public CauHoi getCauHoiForUpdate() {
        String luaChon1 = answer1.getValue();
        String luaChon2 = answer2.getValue();
        String luaChon3 = answer3.getValue();
        String luaChon4 = answer4.getValue();
        CauHoi cauHoi;

        LuaChons lc1 = new LuaChons(luaChon1,false);
        LuaChons lc2 = new LuaChons(luaChon2,false);
        LuaChons lc3 = new LuaChons(luaChon3,false);
        LuaChons lc4 = new LuaChons(luaChon4,false);


        int indexDapAn = comboBoxSuggestion1.getSelectedIndex() + 1;
        switch (indexDapAn) {
            case 1:
                lc1.setDapAnDung(true);
                break;
            case 2:
                lc2.setDapAnDung(true);
                break;
            case 3:
                lc3.setDapAnDung(true);
                break;
            case 4:
                lc4.setDapAnDung(true);
                break;
            default:
                throw new AssertionError();
        }

        String noiDungCauHoi = jTextArea1.getText().trim();

        List<LuaChons> dsLuaChon = new ArrayList<>();
        dsLuaChon.add(lc1);
        dsLuaChon.add(lc2);
        dsLuaChon.add(lc3);
        dsLuaChon.add(lc4);

        String loiGiai = tfLoiGiai.getText().trim();

        cauHoi = new CauHoi(maCauHoi,"vận dụng",jTextArea1.getText().trim(),1,dsLuaChon,loiGiai,"enable",new DeThi());
        try {
            Thread.sleep(300); // dừng 300 mili giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cauHoi;
    }

    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }
    public void setNoiDungCauHoi(String cauHoi) {
        jTextArea1.setText(cauHoi);
    }
    public void setTfLoiGiai(String loiGiai) {
        tfLoiGiai.setText(loiGiai);
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }
    public void setCauHoi(String key){
        jLabel1.setText(key);
    }
    public boolean isDone(){
        if(jTextArea1.getText().trim().isEmpty()) return false;
        if(answer1.getMyTextField1().getText().trim().isEmpty()) return false;
        if(answer2.getMyTextField1().getText().trim().isEmpty()) return false;
        if(answer3.getMyTextField1().getText().trim().isEmpty()) return false;
        if(answer4.getMyTextField1().getText().trim().isEmpty()) return false;
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        answer = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        roundedPanel1 = new Components.RoundedPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        answer1 = new Components.Answer();
        answer2 = new Components.Answer();
        answer3 = new Components.Answer();
        answer4 = new Components.Answer();
        jLabel2 = new javax.swing.JLabel();
        comboBoxSuggestion1 = new Components.ComboBoxSuggestion();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfLoiGiai = new Components.MyTextField();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setOpaque(false);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Câu 1");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Chọn đáp án đúng:");

        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C", "D" }));
        comboBoxSuggestion1.setPreferredSize(new java.awt.Dimension(50, 36));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nhập lời giải");

        tfLoiGiai.setBackground(new java.awt.Color(246, 246, 246));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(answer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(answer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(answer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfLoiGiai, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(answer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answer4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(tfLoiGiai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup answer;
    private Components.Answer answer1;
    private Components.Answer answer2;
    private Components.Answer answer3;
    private Components.Answer answer4;
    private Components.ComboBoxSuggestion comboBoxSuggestion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private Components.RoundedPanel roundedPanel1;
    private Components.MyTextField tfLoiGiai;
    // End of variables declaration//GEN-END:variables
}
