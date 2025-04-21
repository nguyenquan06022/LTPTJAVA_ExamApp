/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Entity.BaiKiemTra;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author THANH PHU
 */
public class ListBaiKiemTra extends JPanel{
    public ListBaiKiemTra(){
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        for(int i=0;i<10;i++){
            BaiKiemTraCard bktCard= new BaiKiemTraCard();
            
            container.add(bktCard,"growx, wrap");
        }
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setPreferredSize(new Dimension(800, 460));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void init(ArrayList<BaiKiemTra> dsBKT){
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        dsBKT.forEach(x->{
            BaiKiemTraCard card= new BaiKiemTraCard(x);
            container.add(card,"growx, wrap");
        });
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setPreferredSize(new Dimension(800, 460));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Ẩn scrollbar ngang
        scrollPane.getViewport().setOpaque(false); // Nền trong suốt);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    public ListBaiKiemTra(ArrayList<BaiKiemTra> dsBKT){
        init(dsBKT);
    }
    public void updateList(ArrayList<BaiKiemTra> dsBKT){
        removeAll();
        init(dsBKT);
    }
}
