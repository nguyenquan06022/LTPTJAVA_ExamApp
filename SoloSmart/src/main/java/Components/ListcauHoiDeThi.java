/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author THANH PHU
 */
public class ListcauHoiDeThi extends JPanel{
    private List<CauHoiDeThi> dsCauHoiDeThis;
    public ListcauHoiDeThi(){
        init(10);
    }
    public ListcauHoiDeThi(int soLuong){
        init(soLuong);
    }
    public void init(int soLuong){
        dsCauHoiDeThis.clear();
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        container.setOpaque(false);
        for(int i=0;i<soLuong;i++){
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
    
}

