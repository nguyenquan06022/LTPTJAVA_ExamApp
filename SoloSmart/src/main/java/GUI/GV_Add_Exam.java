    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
     */
    package GUI;

    import Components.CauHoiDeThi;
    import Components.ComboBoxSuggestion;
    import Dao.CauHoi_DAO;
    import Dao.DeThi_DAO;
    import Dao.DsLuaChon_DAO;
    import Dao.MonHoc_DAO;
    import Entity.*;

    import java.awt.Color;
    import java.util.ArrayList;
    import java.util.List;
    import javax.swing.*;

    /**
     *
     * @author THANH PHU
     */
    public class GV_Add_Exam extends javax.swing.JPanel {
        private MonHoc_DAO monHoc_DAO = new MonHoc_DAO(Main_GUI.em);
        private DeThi_DAO deThi_DAO = new DeThi_DAO(Main_GUI.em);
        private CauHoi_DAO cauHoiDao = new CauHoi_DAO(Main_GUI.em);
        private DsLuaChon_DAO dsLuaChonDao = new DsLuaChon_DAO(Main_GUI.em);
        private String maDeThi;
        /**
         * Creates new form GV_Add_Exam
         */
        public GV_Add_Exam() {
            initComponents();
            loadListMonHoc();
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
            maDeThi = deThi_DAO.generateMa();
            circleBackgroundPanel1 = new Components.CircleBackgroundPanel();
            jLabel1 = new javax.swing.JLabel();
            btnTaoDeThi = new Components.Button();
            jLabel2 = new javax.swing.JLabel();
            sliderGradient1 = new Components.SliderGradient();
            jLabel3 = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            tfTenDeThi = new javax.swing.JTextArea();
            jLabel4 = new javax.swing.JLabel();
            cbbMonHoc = new Components.ComboBoxSuggestion();
            listcauHoiDeThi1 = new Components.ListcauHoiDeThi(maDeThi);

            circleBackgroundPanel1.setColor1(new java.awt.Color(58, 138, 125));

            jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(255, 255, 255));
            jLabel1.setText("Số lượng:");

            btnTaoDeThi.setBackground(new java.awt.Color(0, 0, 0));
            btnTaoDeThi.setForeground(new java.awt.Color(255, 255, 255));
            btnTaoDeThi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-add-30.png"))); // NOI18N
            btnTaoDeThi.setText("Tạo");
            btnTaoDeThi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
            btnTaoDeThi.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTaoDeThiActionPerformed(evt);
                }
            });

            jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(255, 255, 255));
            jLabel2.setText("10 câu hỏi");

            sliderGradient1.setForeground(new java.awt.Color(255, 255, 255));
            sliderGradient1.setMajorTickSpacing(10);
            sliderGradient1.setMinimum(10);
            sliderGradient1.setPaintLabels(true);
            sliderGradient1.setPaintTicks(true);
            sliderGradient1.setValue(10);
            sliderGradient1.setColor1(new java.awt.Color(58, 138, 125));
            sliderGradient1.setColor2(new java.awt.Color(150, 91, 155));
            sliderGradient1.setTicksColor(new java.awt.Color(255, 255, 255));
            sliderGradient1.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    sliderGradient1StateChanged(evt);
                }
            });

            jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(255, 255, 255));
            jLabel3.setText("Tên đề thi:");

            tfTenDeThi.setColumns(20);
            tfTenDeThi.setRows(5);
            jScrollPane1.setViewportView(tfTenDeThi);

            jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
            jLabel4.setForeground(new java.awt.Color(255, 255, 255));
            jLabel4.setText("Tên môn học:");

            cbbMonHoc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cbbMonHocActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout circleBackgroundPanel1Layout = new javax.swing.GroupLayout(circleBackgroundPanel1);
            circleBackgroundPanel1.setLayout(circleBackgroundPanel1Layout);
            circleBackgroundPanel1Layout.setHorizontalGroup(
                circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(sliderGradient1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addComponent(btnTaoDeThi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                            .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(cbbMonHoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            circleBackgroundPanel1Layout.setVerticalGroup(
                circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addGap(18, 18, 18)
                    .addComponent(sliderGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbbMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTaoDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(listcauHoiDeThi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(listcauHoiDeThi1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
        }// </editor-fold>//GEN-END:initComponents

        private void sliderGradient1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGradient1StateChanged
            jLabel2.setText(sliderGradient1.getValue()+" câu hỏi");
            listcauHoiDeThi1.updateCauHoi(sliderGradient1.getValue());
        }//GEN-LAST:event_sliderGradient1StateChanged

        private void btnTaoDeThiActionPerformed(java.awt.event.ActionEvent evt) {
            //GEN-FIRST:event_btnTaoDeThiActionPerformed
            try {
                System.out.println("---click---");
                List<CauHoiDeThi> listCauHoi = listcauHoiDeThi1.getDsCauHoiDeThis();
                if(!listcauHoiDeThi1.isDone()) return;
                List<Boolean> res = new ArrayList<>();

                // Create new exam
                DeThi deThi = new DeThi();
                deThi.setMaDeThi(maDeThi);
                deThi.setLinkFile("");
                deThi.setMonHoc(cbbMonHoc.getSelectedItem().toString());
                deThi.setSoLuongCauHoi(listCauHoi.size());
                deThi.setTenDeThi(tfTenDeThi.getText().trim());
                deThi.setTrangThai("enable");
                deThi.setNganHangDeThi(new NganHangDeThi());
                deThi.setTaiKhoan(new TaiKhoan(Main_GUI.tk.getMaTaiKhoan()));

                // Add exam to database
                res.add(deThi_DAO.addDeThi(deThi));

                // Add all questions to database
                for (CauHoiDeThi item : listCauHoi) {
                    CauHoi cauHoi = item.getCauHoi();
                    cauHoi.setDeThi(new DeThi(maDeThi));
                    List<LuaChons> dsLuaChons = cauHoi.getDsLuaChon();
                    res.add(cauHoiDao.addCauHoi(cauHoi));
                    for (LuaChons dsLuaChon : dsLuaChons) {
                        res.add(dsLuaChonDao.themLuaChon(
                                cauHoi.getMaCauHoi(),
                                dsLuaChon.getLuaChon(),
                                dsLuaChon.isDapAnDung()
                        ));
                    }
                }

                System.out.println(maDeThi);
                System.out.println(res);

                // Check results
                if (res.contains(false)) {
                    JOptionPane.showMessageDialog(this, "Thêm đề thi thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    // Optionally clear the form or do other cleanup
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }//GEN-LAST:event_btnTaoDeThiActionPerformed

        private void cbbMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMonHocActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_cbbMonHocActionPerformed

        private void loadListMonHoc() {
            List<MonHoc> listMonHoc = monHoc_DAO.getDanhSachMonHoc();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                for (MonHoc item : listMonHoc) {
                    model.addElement(item.getTenMonHoc());
                }
            cbbMonHoc.setModel(model);
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private Components.Button btnTaoDeThi;
        private Components.ComboBoxSuggestion cbbMonHoc;
        private Components.CircleBackgroundPanel circleBackgroundPanel1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JScrollPane jScrollPane1;
        private Components.ListcauHoiDeThi listcauHoiDeThi1;
        private Components.SliderGradient sliderGradient1;
        private javax.swing.JTextArea tfTenDeThi;
        // End of variables declaration//GEN-END:variables
    }
