/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Dao.DeThi_DAO;
import Entity.DeThi;
import GUI.Main_GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Admin
 */
public class ListDeThi extends JPanel{
    private DeThi_DAO deThi_DAO = new DeThi_DAO(Main_GUI.em);
    public ListDeThi(ArrayList<DeThi> dsDeThi) {
        init(dsDeThi);
        repaint();
        revalidate();
    }
    
    public void updateDsDeThi(ArrayList<DeThi> dsDeThi) {
        removeAll();
        init(dsDeThi);
        repaint();
        revalidate();
    }
    
    public ListDeThi() {
        setOpaque(false);
        
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 2, fillx", "[grow][grow]", "[]"));
        container.setOpaque(false);
        
        for (int i= 0; i<10;i++) {
            DeThiCard deThiCard = new DeThiCard();
            container.add(deThiCard, "growx");
        }
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setPreferredSize(new Dimension(450, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void init(ArrayList<DeThi> dsDeThi) {
        setOpaque(false);
        
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 2, fillx", "[grow][grow]", "[]"));
        container.setOpaque(false);
        
        dsDeThi.forEach(item -> {
            DeThiCard deThiCard = new DeThiCard(item);
            container.add(deThiCard, "growx");
            deThiCard.getButton2().addActionListener(x->deleteDeThi(item));
        });
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setPreferredSize(new Dimension(450, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void deleteDeThi(DeThi x) {
        int options = JOptionPane.showConfirmDialog(null, "Xác nhận xóa","Xóa đề thi",JOptionPane.YES_NO_OPTION);
        if(options == JOptionPane.YES_OPTION) {
            deThi_DAO.deleteDeThi(x.getMaDeThi());
            updateDsDeThi(deThi_DAO.getDanhSachDeThi());
        }
    }
    
}
