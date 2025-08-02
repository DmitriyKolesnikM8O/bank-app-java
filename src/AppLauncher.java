import javax.swing.SwingUtilities;
import gui.LoginGui;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
            }
        });
    }
}
