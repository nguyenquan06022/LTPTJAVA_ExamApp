/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Components.ScrollBarCustom;
import Components.TableActionCellEditor;
import Components.TableActionCellRender;
import Components.TableActionEvent;
import Dao.KetQuaHocTap_DAO;
import Dao.LopHoc_DAO;
import Dao.MonHoc_DAO;
import Dao.TaiKhoan_DAO;
import Entity.KetQuaHocTap;
import Entity.LopHoc;
import Entity.MonHoc;
import Entity.TaiKhoan;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import jnafilechooser.api.JnaFileChooser;

/**
 *
 * @author THANH PHU
 */
public class Admin_Account extends javax.swing.JPanel {

    /**
     * Creates new form Admin_Subject
     */
    private MonHoc_DAO mh_dao= new MonHoc_DAO(Main_GUI.em);
    private LopHoc_DAO lh_dao= new LopHoc_DAO(Main_GUI.em);
    private TaiKhoan_DAO tk_dao= new TaiKhoan_DAO(Main_GUI.em);
    private KetQuaHocTap_DAO kqht_dao= new KetQuaHocTap_DAO(Main_GUI.em);
    private ImageIcon icon = new ImageIcon(getClass().getResource("/Image/favicon_1.png"));
    private ArrayList<TaiKhoan> listAddStudent= new ArrayList<>();
    private ArrayList<TaiKhoan> listUpdateStudent= new ArrayList<>();
    private ArrayList<KetQuaHocTap> dsKQHT= new ArrayList<>();
    public Admin_Account() {
        initComponents();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
               
                    
                
                
            }

            @Override
            public void onDelete(int row) {
                
                
                
            }

            @Override
            public void onView(int row) {
                
                                            // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        jTable1.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));
        initTable();
        
        searchTextField1.addActionListener(x->{
            ArrayList<LopHoc> list= lh_dao.getDanhSachLopHocByKey(searchTextField1.getText());
            System.out.println(list.size());
            if(list.size()>0){
//                updateTable(list);
            }
            else{
                JOptionPane.showMessageDialog(null,"khong tim thay");
            }
            
        });
    }
    public void initTable(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        ArrayList<TaiKhoan> list= tk_dao.getDanhSachTaiKhoan();
        updateTable(list);
                
    }
    public void updateTable(ArrayList<TaiKhoan> list){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        list.forEach(x->{
            model.addRow(new Object[]{
                x.getMaTaiKhoan(),
                x.getHo()+" "+x.getTen(),
                x.getGioiTinh(),
                x.getVaiTro().equalsIgnoreCase("SV")?"Sinh viên":x.getVaiTro().equalsIgnoreCase("GV")?"Giảng viên":"Quản trị viên",
                x.getDangOnline()
            });
        });
                
    }
    public boolean validateAccount(){
        String ho= myTextField1.getText();
        String ten= myTextField13.getText();
        String tenTK= myTextField14.getText().trim();
        String email = myTextField15.getText().trim();
        String sdt = myTextField16.getText().trim();    
        if (ho.isEmpty() || ten.isEmpty() || tenTK.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Họ, tên, và tên tài khoản không được để trống.");
            return false;
        }
        if(!(ho.matches("^[\\p{L} ]+$")||ten.matches("^[\\p{L} ]+$"))){
            JOptionPane.showMessageDialog(null, "Họ, tên phải là ký tự chữ, không chứa các ký tự đặc biệt.");
            return false;
        }
        if (tenTK.contains(" ")) {
            JOptionPane.showMessageDialog(null, "Tên tài khoản không được chứa khoảng trắng.");
            return false;
        }

        if (tenTK.length() < 5) {
            JOptionPane.showMessageDialog(null, "Tên tài khoản phải có ít nhất 5 ký tự.");
            return false;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ.");
            return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0.");
            return false;
        }
        return true;
    }
    public void ClearAddDialog(){
        myTextField1.setText("");
        myTextField13.setText("");
        myTextField14.setText("");
        myTextField15.setText("");
        myTextField16.setText("");
        comboBoxSuggestion7.setSelectedIndex(0);
        comboBoxSuggestion8.setSelectedIndex(0);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddDialog = new javax.swing.JDialog();
        roundedGradientPanel1 = new Components.RoundedGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        myTextField1 = new Components.MyTextField();
        jLabel3 = new javax.swing.JLabel();
        button3 = new Components.Button();
        circleBackgroundPanel2 = new Components.CircleBackgroundPanel();
        jLabel17 = new javax.swing.JLabel();
        myTextField13 = new Components.MyTextField();
        jLabel25 = new javax.swing.JLabel();
        myTextField14 = new Components.MyTextField();
        jLabel26 = new javax.swing.JLabel();
        comboBoxSuggestion7 = new Components.ComboBoxSuggestion();
        jLabel27 = new javax.swing.JLabel();
        comboBoxSuggestion8 = new Components.ComboBoxSuggestion();
        jLabel28 = new javax.swing.JLabel();
        myTextField15 = new Components.MyTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        myTextField16 = new Components.MyTextField();
        AddStudents = new javax.swing.JDialog();
        roundedPanel2 = new Components.RoundedPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        button6 = new Components.Button();
        button7 = new Components.Button();
        myTextField9 = new Components.MyTextField();
        jLabel16 = new javax.swing.JLabel();
        button8 = new Components.Button();
        AddSuccess = new javax.swing.JDialog();
        roundedPanel3 = new Components.RoundedPanel();
        roundedGradientPanel2 = new Components.RoundedGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        AddExcel = new javax.swing.JDialog();
        roundedPanel4 = new Components.RoundedPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        roundedPanel1 = new Components.RoundedPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        circleBackgroundPanel1 = new Components.CircleBackgroundPanel();
        button2 = new Components.Button();
        button1 = new Components.Button();
        searchTextField1 = new Components.SearchTextField();
        button14 = new Components.Button();

        AddDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddDialog.setTitle("Thêm lớp học");
        AddDialog.setResizable(false);

        roundedGradientPanel1.setColor1(new java.awt.Color(255, 255, 255));
        roundedGradientPanel1.setColor2(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Profiling-bro.png"))); // NOI18N

        myTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 12)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Họ:");

        button3.setBackground(new java.awt.Color(58, 138, 125));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("Xác nhận");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        circleBackgroundPanel2.setColor1(new java.awt.Color(58, 138, 125));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Thêm tài khoản");

        javax.swing.GroupLayout circleBackgroundPanel2Layout = new javax.swing.GroupLayout(circleBackgroundPanel2);
        circleBackgroundPanel2.setLayout(circleBackgroundPanel2Layout);
        circleBackgroundPanel2Layout.setHorizontalGroup(
            circleBackgroundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(circleBackgroundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        circleBackgroundPanel2Layout.setVerticalGroup(
            circleBackgroundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(circleBackgroundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        myTextField13.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 12)));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel25.setText("Tên:");

        myTextField14.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 12)));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel26.setText("Tên tài khoản:");

        comboBoxSuggestion7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));
        comboBoxSuggestion7.setPreferredSize(new java.awt.Dimension(68, 36));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel27.setText("Giới tính:");

        comboBoxSuggestion8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sinh Viên", "Giảng Viên", "Quản trị viên" }));
        comboBoxSuggestion8.setPreferredSize(new java.awt.Dimension(109, 36));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel28.setText("Vai trò:");

        myTextField15.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 12)));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel29.setText("Mail:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel30.setText("Số điện thoại:");

        myTextField16.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 12)));

        javax.swing.GroupLayout roundedGradientPanel1Layout = new javax.swing.GroupLayout(roundedGradientPanel1);
        roundedGradientPanel1.setLayout(roundedGradientPanel1Layout);
        roundedGradientPanel1Layout.setHorizontalGroup(
            roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(circleBackgroundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                        .addComponent(myTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(myTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                        .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBoxSuggestion7, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(comboBoxSuggestion8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(myTextField15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myTextField16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedGradientPanel1Layout.setVerticalGroup(
            roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10))
                    .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                        .addComponent(circleBackgroundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel25))
                        .addGap(7, 7, 7)
                        .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26)
                        .addGap(7, 7, 7)
                        .addComponent(myTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundedGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxSuggestion7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedGradientPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxSuggestion8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29)
                        .addGap(7, 7, 7)
                        .addComponent(myTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30)
                        .addGap(7, 7, 7)
                        .addComponent(myTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout AddDialogLayout = new javax.swing.GroupLayout(AddDialog.getContentPane());
        AddDialog.getContentPane().setLayout(AddDialogLayout);
        AddDialogLayout.setHorizontalGroup(
            AddDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDialogLayout.createSequentialGroup()
                .addComponent(roundedGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        AddDialogLayout.setVerticalGroup(
            AddDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedGradientPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        AddStudents.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddStudents.setResizable(false);

        roundedPanel2.setBackground(new java.awt.Color(58, 138, 125));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setOpaque(false);
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã tài khoản", "Họ tên", "Giới tính", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(40);
        jTable2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        button6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button6.setForeground(new java.awt.Color(51, 51, 51));
        button6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-excel-30.png"))); // NOI18N
        button6.setText("Thêm từ Excel");
        button6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        button7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button7.setForeground(new java.awt.Color(51, 51, 51));
        button7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-add-30 (1).png"))); // NOI18N
        button7.setText("Thêm sinh viên");
        button7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button7ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Mã sinh viên:");

        button8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button8.setText("Xác nhận");
        button8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button8.setPreferredSize(new java.awt.Dimension(42, 36));
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(myTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addComponent(button8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout AddStudentsLayout = new javax.swing.GroupLayout(AddStudents.getContentPane());
        AddStudents.getContentPane().setLayout(AddStudentsLayout);
        AddStudentsLayout.setHorizontalGroup(
            AddStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        AddStudentsLayout.setVerticalGroup(
            AddStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        AddSuccess.setIconImage(null);
        AddSuccess.setModal(true);
        AddSuccess.setResizable(false);

        roundedPanel3.setBackground(new java.awt.Color(255, 255, 255));

        roundedGradientPanel2.setColor1(new java.awt.Color(58, 138, 125));
        roundedGradientPanel2.setColor2(new java.awt.Color(194, 230, 118));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-success-100.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Thành công!");

        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout roundedGradientPanel2Layout = new javax.swing.GroupLayout(roundedGradientPanel2);
        roundedGradientPanel2.setLayout(roundedGradientPanel2Layout);
        roundedGradientPanel2Layout.setHorizontalGroup(
            roundedGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedGradientPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        roundedGradientPanel2Layout.setVerticalGroup(
            roundedGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedGradientPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel5)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addComponent(roundedGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AddSuccessLayout = new javax.swing.GroupLayout(AddSuccess.getContentPane());
        AddSuccess.getContentPane().setLayout(AddSuccessLayout);
        AddSuccessLayout.setHorizontalGroup(
            AddSuccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSuccessLayout.createSequentialGroup()
                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        AddSuccessLayout.setVerticalGroup(
            AddSuccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSuccessLayout.createSequentialGroup()
                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        AddExcel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddExcel.setTitle("Thêm từ Excel");
        AddExcel.setResizable(false);

        roundedPanel4.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel4.setPreferredSize(new java.awt.Dimension(827, 460));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout roundedPanel4Layout = new javax.swing.GroupLayout(roundedPanel4);
        roundedPanel4.setLayout(roundedPanel4Layout);
        roundedPanel4Layout.setHorizontalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel4Layout.createSequentialGroup()
                .addGap(0, 229, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundedPanel4Layout.setVerticalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout AddExcelLayout = new javax.swing.GroupLayout(AddExcel.getContentPane());
        AddExcel.getContentPane().setLayout(AddExcelLayout);
        AddExcelLayout.setHorizontalGroup(
            AddExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddExcelLayout.createSequentialGroup()
                .addComponent(roundedPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AddExcelLayout.setVerticalGroup(
            AddExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddExcelLayout.createSequentialGroup()
                .addComponent(roundedPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setOpaque(false);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã tài khoản", "Họ tên", "Giới tính", "Vai trò", "Trạng thái", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(61, 141, 122));
        jTable1.setRowHeight(40);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        circleBackgroundPanel1.setColor1(new java.awt.Color(61, 141, 122));

        button2.setBorder(null);
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-reload-30.png"))); // NOI18N
        button2.setText("Làm mới");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(0, 0, 0));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-add-30.png"))); // NOI18N
        button1.setText("Thêm tài khoản");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button14.setBorder(null);
        button14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-excel-30.png"))); // NOI18N
        button14.setText("Thêm từ Excel");
        button14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout circleBackgroundPanel1Layout = new javax.swing.GroupLayout(circleBackgroundPanel1);
        circleBackgroundPanel1.setLayout(circleBackgroundPanel1Layout);
        circleBackgroundPanel1Layout.setHorizontalGroup(
            circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, circleBackgroundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(button14, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        circleBackgroundPanel1Layout.setVerticalGroup(
            circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addGap(32, 32, 32))
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

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        updateTable(tk_dao.getDanhSachTaiKhoan());
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        
        AddDialog.pack();
        AddDialog.setLocationRelativeTo(null);
        AddDialog.setModal(true);
        AddDialog.setIconImage(icon.getImage());
        AddDialog.setVisible(true);
    }//GEN-LAST:event_button1ActionPerformed
    
    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
       
    }//GEN-LAST:event_button6ActionPerformed

    private void button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button7ActionPerformed
        
    }//GEN-LAST:event_button7ActionPerformed

    private void button8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button8ActionPerformed
        
    }//GEN-LAST:event_button8ActionPerformed
    
    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        if(validateAccount()){
            TaiKhoan tk= new TaiKhoan(tk_dao.generateMa(), myTextField14.getText().trim(), 
                    tk_dao.generatePassword(myTextField13.getText(), myTextField1.getText(), comboBoxSuggestion8.getSelectedIndex()),
                    myTextField1.getText(), myTextField13.getText(), 
                    comboBoxSuggestion8.getSelectedIndex()==0?"SV":comboBoxSuggestion8.getSelectedIndex()==1?"GV":"AD",
                    "enable", 
                    "offline", comboBoxSuggestion7.getSelectedItem().toString(),
                    myTextField16.getText().trim(), myTextField15.getText().trim());
            if(tk_dao.addTaiKhoan(tk)){
                JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công");
                ClearAddDialog();
                AddDialog.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm tài khoản thất bại");
            }
        }else{
            System.out.println("them that bai");
        }
        
    }//GEN-LAST:event_button3ActionPerformed

    private void button14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AddDialog;
    private javax.swing.JDialog AddExcel;
    private javax.swing.JDialog AddStudents;
    private javax.swing.JDialog AddSuccess;
    private Components.Button button1;
    private Components.Button button14;
    private Components.Button button2;
    private Components.Button button3;
    private Components.Button button6;
    private Components.Button button7;
    private Components.Button button8;
    private Components.CircleBackgroundPanel circleBackgroundPanel1;
    private Components.CircleBackgroundPanel circleBackgroundPanel2;
    private Components.ComboBoxSuggestion comboBoxSuggestion7;
    private Components.ComboBoxSuggestion comboBoxSuggestion8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private Components.MyTextField myTextField1;
    private Components.MyTextField myTextField13;
    private Components.MyTextField myTextField14;
    private Components.MyTextField myTextField15;
    private Components.MyTextField myTextField16;
    private Components.MyTextField myTextField9;
    private Components.RoundedGradientPanel roundedGradientPanel1;
    private Components.RoundedGradientPanel roundedGradientPanel2;
    private Components.RoundedPanel roundedPanel1;
    private Components.RoundedPanel roundedPanel2;
    private Components.RoundedPanel roundedPanel3;
    private Components.RoundedPanel roundedPanel4;
    private Components.SearchTextField searchTextField1;
    // End of variables declaration//GEN-END:variables
}
