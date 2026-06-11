import views.MembershipFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainMenu {
    public static void main(String[] args) {
        // Pakai look & feel sistem agar tombol warna muncul dengan benar
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // fallback default
        }

        SwingUtilities.invokeLater(() -> {
            MembershipFrame frame = new MembershipFrame();
            frame.setVisible(true);
        });
    }
}
