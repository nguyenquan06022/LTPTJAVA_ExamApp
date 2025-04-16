/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Admin
 */
public class ListDeThi extends JPanel{
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
        add(scrollPane, BorderLayout.CENTER);
    }
}
