/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.Color;
import java.awt.Component;
import static java.awt.Label.CENTER;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ComboBoxRenderer extends ComboBoxSuggestion implements TableCellRenderer {

    // Constructor nhận mảng items và thiết lập model cho ComboBoxSuggestion
    public ComboBoxRenderer(String[] items) {
        super();
        setModel(new DefaultComboBoxModel<>(items)); // Thiết lập model cho ComboBoxSuggestion
        setOpaque(true); // Đảm bảo giao diện trong suốt
        setBorder(null);
        setRenderer(new CenteredListCellRenderer());

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { // Kiểm tra nếu value là null thì gán giá trị mặc định 
        if (value == null || value.toString().isEmpty()) {
            value = getItemAt(0).toString(); // Sử dụng giá trị đầu tiên làm mặc định 

        }

        if (isSelected) {
            setForeground(Color.black);
            setBackground(table.getSelectionBackground());
        } else {
            if (row % 2 == 0) {

                setForeground(Color.black);
                setBackground(Color.white);
            } else {
                setForeground(Color.black);
                setBackground(table.getBackground());
            }
        }
        setSelectedItem(value);
        return this;
    }
}
