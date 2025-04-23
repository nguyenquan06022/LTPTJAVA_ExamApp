/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

import Components.chart.Chart;
import Components.chart.ModelChart;
import Dao.BaiKiemTra_DAO;
import Dao.DeThi_DAO;
import Dao.KetQuaHocTap_DAO;
import Dao.KetQuaKiemTra_DAO;
import Dao.LopHoc_DAO;
import Dao.MonHoc_DAO;
import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.KetQuaKiemTra;
import Entity.MonHoc;
import Entity.TaiKhoan;
import GUI.GV_ClassRoom_Detail;
import GUI.Main_GUI;
import GUI.SV_KiemTra;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author THANH PHU
 */
public class BaiKiemTraCard extends javax.swing.JPanel {
    // private DateChooser datach= new DateChooser();

    /**
     * Creates new form BaiKiemTraCard
     */
    private CustomDateTimeChooser datech = new CustomDateTimeChooser();
    private DeThi_DAO dt_dao = new DeThi_DAO(Main_GUI.em);
    private BaiKiemTra_DAO bkt_dao = new BaiKiemTra_DAO(Main_GUI.em);
    private KetQuaHocTap_DAO kqht_dao = new KetQuaHocTap_DAO(Main_GUI.em);
    private MonHoc_DAO mh_dao = new MonHoc_DAO(Main_GUI.em);
    private KetQuaKiemTra_DAO kqkt_dao= new KetQuaKiemTra_DAO(Main_GUI.em);
    private BaiKiemTra bkt;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    private DecimalFormat de = new DecimalFormat("#.##");

    public BaiKiemTraCard() {
        initComponents();
    }

    public BaiKiemTraCard(BaiKiemTra bkt) {
        this.bkt = bkt;
        initComponents();
        DeThi dt = dt_dao.getDeThi(bkt.getDeThi().getMaDeThi());
        jLabel2.setText(dt.getTenDeThi());
        jLabel3.setText("Thời gian bắt đầu: " + df.format(bkt.getThoiGianBatDau()));
        jLabel4.setText("Thời gian kết thúc: " + df.format(bkt.getThoiGianKetThuc()));
        jLabel5.setText("Thời gian làm bài: " + bkt.getThoiGianLamBai() + " phút");
        jLabel6.setText("Số lần làm bài: " + bkt.getSoLanLamBai());
        if (Main_GUI.tk.getVaiTro().equalsIgnoreCase("GV")) {
            button2.setText("Xem");
            button2.addActionListener(x -> buttonGVXem());
            button3.setText("Xóa");
            button3.addActionListener(x->buttonGVXoa());
        } else {
            button2.addActionListener(x->buttonSVThamGia());
        }
    }

    private Map<TaiKhoan, Float> dsSVLamBaiKiemTra;
    public void buttonSVThamGia(){
        LocalDateTime now= LocalDateTime.now();
        ArrayList<KetQuaKiemTra> dsKQKT= kqkt_dao.getDanhSachKetQuaKiemTra(Main_GUI.tk.getMaTaiKhoan(), bkt.getMaBaiKiemTra());
        if(bkt.getThoiGianBatDau().isAfter(now)){
            JOptionPane.showMessageDialog(null, "Chưa tới thời gian làm bài kiểm tra");
        }
        else if(bkt.getThoiGianKetThuc().isBefore(now)){
            JOptionPane.showMessageDialog(null, "Đã hết thời gian làm bài kiểm tra");
        }
        else if(bkt.getSoLanLamBai()<dsKQKT.size()){
            JOptionPane.showMessageDialog(null, "Đã hết lượt làm bài kiểm tra");
        }
        else {
            String matKhau= bkt.getMatKhauBaiKiemTra();
            System.out.println("mat khâu " + matKhau);
            if(JOptionPane.showConfirmDialog(null, 
                    "Khi bắt đầu bài kiểm tra, bạn sẽ không thể thoát ra giữa chừng. Nếu bạn thoát, bài làm sẽ tự động được nộp và kết quả sẽ được lưu lại.\nXác nhận kiểm tra?",
                    "Xác nhận kiểm tra",JOptionPane.YES_NO_OPTION)
                    ==JOptionPane.YES_OPTION){
                if(matKhau==null||matKhau.trim().equals("")){
                    initBaiKiemTra();
                }
                else{
                    String nhapMatKhau= JOptionPane.showInputDialog(null,"Vui lòng nhập mật khẩu để bắt đầu bài kiểm tra");
                    if(nhapMatKhau==null){
                        return;
                    }
                    if(!nhapMatKhau.equalsIgnoreCase(matKhau)){
                        JOptionPane.showMessageDialog(null, "Mật khẩu không đúng, vui lòng thử lại");
                        return;
                    }
                    initBaiKiemTra();
                }
            }
        }
    }
    public void initBaiKiemTra(){
        SV_KiemTra kiemTraGUI= new SV_KiemTra(bkt);
        kiemTraGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        kiemTraGUI.setVisible(true);
    }
    public void buttonGVXem() {
        dsSVLamBaiKiemTra = bkt_dao.getDsTaiKhoanThamGiaKiemTraVaDiemSo(bkt.getMaBaiKiemTra());
        int soLuongSV = dsSVLamBaiKiemTra.size();
        int tongSV = kqht_dao.getDanhSachKetQuaHocTap(bkt.getLopHoc().getMaLop()).size();
        initData();
        float trungBinhDiem = (float) dsSVLamBaiKiemTra.values()
                .stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0.0);
        thongKeCard1.updateCard("E74888", "885FBF", "F66060", soLuongSV + "", "icons8-user-groups-50",
                "Số sinh viên làm bài kiểm tra", (int) (((float) soLuongSV / tongSV) * 100));
        thongKeCard2.updateCard("38419D", "52D3D8", "38419D", de.format(trungBinhDiem) + "", "icons8-test-50",
                "Điểm trung bình", (int) (((float) trungBinhDiem / 10) * 100));
        loadTable(1);
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadTable(page);
            }

        });
        XemBKT.pack();
        XemBKT.setLocationRelativeTo(null);
        XemBKT.setVisible(true);
    }
    public void buttonGVXoa(){
        if(JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa bài kiểm tra này không?","Xóa bài kiểm tra",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            if(bkt_dao.deleteBaiKiemTra(bkt.getMaBaiKiemTra())){
                GV_ClassRoom_Detail.loadBKT();
            }
            else{
                JOptionPane.showMessageDialog(null, "Xóa thất bại");
            }
        }
    }
    public void initData() {
        jCheckBoxCustom1.setSelected(bkt.isChoPhepXemDiem());
        jCheckBoxCustom2.setSelected(bkt.isChoPhepXemLai());
        jCheckBoxCustom3.setSelected(bkt.isHienThiDapAn());
        deThiCard21.update(dt_dao.getDeThi(bkt.getDeThi().getMaDeThi()));
        customDateChooser1.setDateTime(bkt.getThoiGianBatDau());
        customDateChooser2.setDateTime(bkt.getThoiGianKetThuc());
        jSlider1.setValue(bkt.getThoiGianLamBai());
        jLabel11.setText(bkt.getThoiGianLamBai() + " phút");
        jSpinner1.setValue(bkt.getSoLanLamBai());
        comboBoxSuggestion1.setSelectedItem(bkt.getHeSo()+"");
        myPasswordField1.setText(bkt.getMatKhauBaiKiemTra());
    }

    public void loadTable(int page) {
        int limit = 10;
        int totalPage = (int) Math.ceil((double) dsSVLamBaiKiemTra.size() / limit);
        pagination1.setPagegination(page, totalPage);
        int currentPage = page; // Bạn có thể thay đổi thành biến động nếu muốn điều khiển trang
        int skip = (currentPage - 1) * limit;
        System.out.println(dsSVLamBaiKiemTra.size());
        Map<TaiKhoan, Float> listToShow = dsSVLamBaiKiemTra.entrySet().stream()
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        System.out.println(listToShow.size());
        model.setRowCount(0);
        listToShow.entrySet().forEach(x -> {
            TaiKhoan sv = x.getKey();
            Float diem = x.getValue();
            model.addRow(new Object[] {
                    sv.getHo() + " " + sv.getTen(), diem
            });
        });
    }

    public void initDSDeThi() {
        MonHoc mon = mh_dao.getMonHoc(GV_ClassRoom_Detail.lopHoc.getMonHoc().getMaMonHoc());
        ArrayList<DeThi> dsDeThi = dt_dao.getDanhSachDeThiTheoMonCuaGV(Main_GUI.tk.getMaTaiKhoan(), mon.getTenMonHoc());
        listDeThi21.updateDsDeThi(dsDeThi);
        listDeThi21.dsCard.forEach(x -> {
            x.getButton1().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deThiCard21.update(x.getDethi());
                    jDialog1.dispose();
                }
            });
        });
    }

    public static Button getButton4() {
        return button4;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customDateChooser1 = new Components.CustomDateTimeChooser();
        XemBKT = new javax.swing.JDialog();
        roundedPanel4 = new Components.RoundedPanel();
        circleBackgroundPanel1 = new Components.CircleBackgroundPanel();
        thongKeCard1 = new Components.ThongKeCard();
        thongKeCard2 = new Components.ThongKeCard();
        roundedPanel5 = new Components.RoundedPanel();
        jCheckBoxCustom1 = new Components.JCheckBoxCustom();
        jCheckBoxCustom2 = new Components.JCheckBoxCustom();
        jCheckBoxCustom3 = new Components.JCheckBoxCustom();
        jLabel7 = new javax.swing.JLabel();
        myTextField1 = new Components.MyTextField();
        jLabel8 = new javax.swing.JLabel();
        myTextField2 = new Components.MyTextField();
        jLabel9 = new javax.swing.JLabel();
        button1 = new Components.Button();
        button4 = new Components.Button();
        button5 = new Components.Button();
        deThiCard21 = new Components.DeThiCard2();
        jSpinner1 = new javax.swing.JSpinner();
        jSlider1 = new javax.swing.JSlider();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        button6 = new Components.Button();
        jLabel12 = new javax.swing.JLabel();
        myPasswordField1 = new Components.MyPasswordField();
        button7 = new Components.Button();
        comboBoxSuggestion1 = new Components.ComboBoxSuggestion();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new Components.Pagination();
        customDateChooser2 = new Components.CustomDateTimeChooser();
        DialogThongKe = new javax.swing.JDialog();
        chart1 = new Components.chart.Chart(quantity);
        jDialog1 = new javax.swing.JDialog();
        listDeThi21 = new Components.ListDeThi2();
        roundedPanel1 = new Components.RoundedPanel();
        roundedPanel2 = new Components.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        roundedPanel3 = new Components.RoundedPanel();
        button2 = new Components.Button();
        button3 = new Components.Button();

        customDateChooser1.setTextReference(myTextField1);

        XemBKT.setModal(true);
        XemBKT.setResizable(false);

        roundedPanel4.setBackground(new java.awt.Color(255, 255, 255));

        circleBackgroundPanel1.setColor1(new java.awt.Color(58, 138, 125));

        javax.swing.GroupLayout circleBackgroundPanel1Layout = new javax.swing.GroupLayout(circleBackgroundPanel1);
        circleBackgroundPanel1.setLayout(circleBackgroundPanel1Layout);
        circleBackgroundPanel1Layout.setHorizontalGroup(
            circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(circleBackgroundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thongKeCard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(thongKeCard2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        circleBackgroundPanel1Layout.setVerticalGroup(
            circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, circleBackgroundPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(circleBackgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(thongKeCard2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thongKeCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        roundedPanel5.setBackground(new java.awt.Color(247, 247, 247));

        jCheckBoxCustom1.setText("Cho phép xem điểm");

        jCheckBoxCustom2.setText("Cho phép xem lại");

        jCheckBoxCustom3.setText("Hiển thị đáp án");

        jLabel7.setText("Số lần làm lại:");

        myTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myTextField1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Ngày bắt đầu:");

        myTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myTextField2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Ngày kết thúc:");

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-reload-30.png"))); // NOI18N
        button1.setText("Đặt lại");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(0, 0, 0));
        button4.setForeground(new java.awt.Color(255, 255, 255));
        button4.setText("Cập nhật");
        button4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button4.setPreferredSize(new java.awt.Dimension(47, 40));
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        button5.setText("Chọn đề thi");
        button5.setPreferredSize(new java.awt.Dimension(61, 36));
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        deThiCard21.setOpaque(false);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jSlider1.setMajorTickSpacing(15);
        jSlider1.setMaximum(120);
        jSlider1.setMinimum(5);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel10.setText("Thời gian làm bài:");

        jLabel11.setText("jLabel11");

        button6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-statistics-30.png"))); // NOI18N
        button6.setText("Thống kê");
        button6.setPreferredSize(new java.awt.Dimension(42, 40));
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        jLabel12.setText("Mật khẩu:");

        myPasswordField1.setPreferredSize(new java.awt.Dimension(64, 40));
        myPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myPasswordField1ActionPerformed(evt);
            }
        });

        button7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/eye_closed.png"))); // NOI18N
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button7ActionPerformed(evt);
            }
        });

        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.2", "0.3", "0.5" }));

        jLabel13.setText("Hệ số:");

        javax.swing.GroupLayout roundedPanel5Layout = new javax.swing.GroupLayout(roundedPanel5);
        roundedPanel5.setLayout(roundedPanel5Layout);
        roundedPanel5Layout.setHorizontalGroup(
            roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, Short.MAX_VALUE)
                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(myTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(deThiCard21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addComponent(button6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        roundedPanel5Layout.setVerticalGroup(
            roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel5Layout.createSequentialGroup()
                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundedPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel5Layout.createSequentialGroup()
                                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                                        .addComponent(myTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(myTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                                        .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(roundedPanel5Layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(jLabel9))
                                            .addGroup(roundedPanel5Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jCheckBoxCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(jCheckBoxCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel13)
                                                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11)))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(myPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE))))
                    .addGroup(roundedPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundedPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(deThiCard21, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(roundedPanel5Layout.createSequentialGroup()
                                    .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Họ tên", "Điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(pagination1);

        javax.swing.GroupLayout roundedPanel4Layout = new javax.swing.GroupLayout(roundedPanel4);
        roundedPanel4.setLayout(roundedPanel4Layout);
        roundedPanel4Layout.setHorizontalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(roundedPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundedPanel4Layout.setVerticalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel4Layout.createSequentialGroup()
                .addComponent(circleBackgroundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout XemBKTLayout = new javax.swing.GroupLayout(XemBKT.getContentPane());
        XemBKT.getContentPane().setLayout(XemBKTLayout);
        XemBKTLayout.setHorizontalGroup(
            XemBKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        XemBKTLayout.setVerticalGroup(
            XemBKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(XemBKTLayout.createSequentialGroup()
                .addComponent(roundedPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        customDateChooser2.setTextReference(myTextField2);

        DialogThongKe.setTitle("Biểu đồ phân phối điểm");
        DialogThongKe.setIconImage(new ImageIcon(getClass().getResource("/Image/favicon_1.png")).getImage());
        DialogThongKe.setResizable(false);

        chart1.setToolTipText("");

        javax.swing.GroupLayout DialogThongKeLayout = new javax.swing.GroupLayout(DialogThongKe.getContentPane());
        DialogThongKe.getContentPane().setLayout(DialogThongKeLayout);
        DialogThongKeLayout.setHorizontalGroup(
            DialogThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogThongKeLayout.createSequentialGroup()
                .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DialogThongKeLayout.setVerticalGroup(
            DialogThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogThongKeLayout.createSequentialGroup()
                .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(listDeThi21, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listDeThi21, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));

        roundedPanel2.setBackground(new java.awt.Color(58, 138, 125));
        roundedPanel2.setPreferredSize(new java.awt.Dimension(72, 72));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-exam-30-reverse.png"))); // NOI18N

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-18.png"))); // NOI18N
        jLabel3.setText("jLabel3");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-18.png"))); // NOI18N
        jLabel4.setText("jLabel4");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-time-18 (1).png"))); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-how-many-quest-18.png"))); // NOI18N
        jLabel6.setText("jLabel6");

        roundedPanel3.setPreferredSize(new java.awt.Dimension(72, 72));

        button2.setBackground(new java.awt.Color(0, 0, 0));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Tham gia");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button2.setPreferredSize(new java.awt.Dimension(80, 26));
        roundedPanel3.add(button2);

        button3.setText("Xem lại");
        button3.setPreferredSize(new java.awt.Dimension(80, 26));
        roundedPanel3.add(button3);

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)))
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        if(bkt.getThoiGianBatDau().isBefore(LocalDateTime.now())){
            JOptionPane.showMessageDialog(null, "Thời gian bắt đầu dã qua, không thể cập nhât!");
        
        }
        else if(customDateChooser1.getSelectedDateTime().isAfter(customDateChooser2.getSelectedDateTime())){
            JOptionPane.showMessageDialog(null, "Thời gian bắt đầu phải sau thời gian kết thúc!");
        }
        else{
            BaiKiemTra bktUpdate= new BaiKiemTra(bkt.getMaBaiKiemTra(),
                    customDateChooser1.getSelectedDateTime(), customDateChooser2.getSelectedDateTime(), 
                    jSlider1.getValue(),new String(myPasswordField1.getPassword()).trim(),
                    jCheckBoxCustom1.isSelected(), jCheckBoxCustom2.isSelected(), jCheckBoxCustom3.isSelected(),
                    (Integer)jSpinner1.getValue(), 
                    10, Float.parseFloat(comboBoxSuggestion1.getSelectedItem().toString()),
                    "enable", deThiCard21.getDethi(),GV_ClassRoom_Detail.lopHoc);
            if(bkt_dao.updateBaiKiemTra(bktUpdate)){
                JOptionPane.showMessageDialog(null, "Cập nhật bài kiểm tra thành công");
                GV_ClassRoom_Detail.loadBKT();
            }
            else{
                JOptionPane.showMessageDialog(null, "Cập nhật bài kiểm tra thất bại");
            }
        }
    }//GEN-LAST:event_button4ActionPerformed
    private boolean eye=false;
    private void button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button7ActionPerformed
        if(!eye){
            myPasswordField1.setEchoChar((char)0);
            button7.setIcon(new ImageIcon(BaiKiemTraCard.class.getResource("/Image/eye_open.png")));
            eye=true;
            
        }
        else{
            myPasswordField1.setEchoChar('*');
            button7.setIcon(new ImageIcon(BaiKiemTraCard.class.getResource("/Image/eye_closed.png")));
            eye=false;
        }
    }//GEN-LAST:event_button7ActionPerformed

    private void myPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myPasswordField1ActionPerformed

    private void myTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myTextField1ActionPerformed

    private void myTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myTextField2ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider1StateChanged
        jLabel11.setText(jSlider1.getValue() + " phút");
    }// GEN-LAST:event_jSlider1StateChanged

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button1ActionPerformed
        initData();
    }// GEN-LAST:event_button1ActionPerformed

    private int quantity;

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button6ActionPerformed
        String maBaiKiemTra = bkt.getMaBaiKiemTra();
        Map<TaiKhoan, Float> map = bkt_dao.getDsTaiKhoanThamGiaKiemTraVaDiemSo(maBaiKiemTra);
        
       
        int[] thongKeDiem = new int[10];
        quantity = 0;
        for (Float diem : map.values()) {
            if (diem != null) {
                int diemLamTron = (int) Math.ceil(diem);
                if (diemLamTron >= 1 && diemLamTron <= 10) {
                    thongKeDiem[diemLamTron - 1]++;
                    quantity++;
                }
            }
        }

        chart1.clearData();
        chart1.addLegend("Số lượng sinh viên", Color.decode("#3a8a7d"));
        for (int i = 0; i < thongKeDiem.length; i++) {
            String nhan = String.valueOf(i + 1);
            chart1.addData(new ModelChart(nhan, new double[] { thongKeDiem[i] }));
        }

        DialogThongKe.pack();
        DialogThongKe.setModal(true);
        DialogThongKe.setLocationRelativeTo(null);
        DialogThongKe.setVisible(true);
    }// GEN-LAST:event_button6ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button5ActionPerformed
        initDSDeThi();
        jDialog1.pack();
        jDialog1.setModal(true);
        jDialog1.setLocationRelativeTo(null);
        jDialog1.setVisible(true);
    }// GEN-LAST:event_button5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogThongKe;
    private javax.swing.JDialog XemBKT;
    private Components.Button button1;
    private Components.Button button2;
    private Components.Button button3;
    public static Components.Button button4;
    private Components.Button button5;
    private Components.Button button6;
    private Components.Button button7;
    private Components.chart.Chart chart1;
    private Components.CircleBackgroundPanel circleBackgroundPanel1;
    private Components.ComboBoxSuggestion comboBoxSuggestion1;
    private Components.CustomDateTimeChooser customDateChooser1;
    private Components.CustomDateTimeChooser customDateChooser2;
    private Components.DeThiCard2 deThiCard21;
    private Components.JCheckBoxCustom jCheckBoxCustom1;
    private Components.JCheckBoxCustom jCheckBoxCustom2;
    private Components.JCheckBoxCustom jCheckBoxCustom3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private Components.ListDeThi2 listDeThi21;
    private Components.MyPasswordField myPasswordField1;
    private Components.MyTextField myTextField1;
    private Components.MyTextField myTextField2;
    private Components.Pagination pagination1;
    private Components.RoundedPanel roundedPanel1;
    private Components.RoundedPanel roundedPanel2;
    private Components.RoundedPanel roundedPanel3;
    private Components.RoundedPanel roundedPanel4;
    private Components.RoundedPanel roundedPanel5;
    private Components.ThongKeCard thongKeCard1;
    private Components.ThongKeCard thongKeCard2;
    // End of variables declaration//GEN-END:variables
}
