import GUI.DangNhapGUI;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Runner {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }
        DangNhapGUI dangNhapGUI = new DangNhapGUI();
        dangNhapGUI.setVisible(true);
    }
}