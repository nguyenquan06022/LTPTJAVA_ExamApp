/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Trong Nghia
 */
public class CenteredListCellRenderer extends JLabel implements ListCellRenderer<Object> {

    public CenteredListCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER); // Căn giữa nội dung theo chiều ngang 
        setVerticalAlignment(CENTER); // Căn giữa nội dung theo chiều dọc 
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText((value == null) ? "" : value.toString()); // Thiết lập văn bản cho JLabel 
        return this;
    }
}
