/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Dao.DeThi_DAO;
import Entity.CauHoi;
import GUI.Main_GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author THANH PHU
 */
public class ListcauHoiDeThi extends JPanel{
    private List<CauHoiDeThi> dsCauHoiDeThis = new ArrayList<>();
    private List<CauHoi> dsCauHoi = new ArrayList<>();
    private DeThi_DAO deThi_DAO = new DeThi_DAO(Main_GUI.em);
    private String maDeThi;

    public String getMaDeThi() {
        return maDeThi;
    }
    
    public List<CauHoiDeThi> getDsCauHoiDeThis() {
        return dsCauHoiDeThis;
    }
    
    public List<CauHoi> getDsCauHoi() {
        return dsCauHoi;
    }

    public ListcauHoiDeThi(String maDeThi){
        this.maDeThi = maDeThi;
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        for(int i=0;i<10;i++){
            CauHoiDeThi cauHoi= new CauHoiDeThi();
            cauHoi.setCauHoi("Câu "+(i+1));
            dsCauHoiDeThis.add(cauHoi);
            container.add(cauHoi,"growx, wrap");
        }
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());

        scrollPane.setPreferredSize(new Dimension(800, 460));
        scrollPane.setMinimumSize(new Dimension(800, 460));
        scrollPane.setMaximumSize(new Dimension(1200,600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public ListcauHoiDeThi(){
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        for(int i=0;i<10;i++){
            CauHoiDeThi cauHoi= new CauHoiDeThi();
            cauHoi.setCauHoi("Câu "+(i+1));
            dsCauHoiDeThis.add(cauHoi);
            container.add(cauHoi,"growx, wrap");
        }
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        
        scrollPane.setPreferredSize(new Dimension(800, 460));
        scrollPane.setMinimumSize(new Dimension(800, 460));
        scrollPane.setMaximumSize(new Dimension(1200,600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }
    public ListcauHoiDeThi(int soLuong){
        init(soLuong);
    }
    public void updateCauHoi(int soluong){
        removeAll();
        init(soluong);
    }
    public void init(int soLuong){

        dsCauHoiDeThis.clear();
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        for(int i=0;i<soLuong;i++){
            CauHoiDeThi cauHoi= new CauHoiDeThi(maDeThi);
            cauHoi.setCauHoi("Câu "+(i+1));
            dsCauHoiDeThis.add(cauHoi);
            container.add(cauHoi,"growx, wrap");
        }
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        
        scrollPane.setPreferredSize(new Dimension(800, 460));
        scrollPane.setMinimumSize(new Dimension(800, 460));
        scrollPane.setMaximumSize(new Dimension(1200,600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
        
        repaint();
        revalidate();
    }
    
    public boolean isDone() {
        int i=1;
        for (CauHoiDeThi x : dsCauHoiDeThis) {
            if (!x.isDone()){
                JOptionPane.showMessageDialog(null, "Thông tin câu hỏi "+(i++)+ "chưa hoàn chỉnh. Xin hãy kiểm tra lại.");
                return false;
            }
        }
        return true;
    }

    
}

