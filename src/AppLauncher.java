import java.math.BigDecimal;

import javax.swing.SwingUtilities;

import bd_objs.User;
import gui.BankingAppGui;
import gui.LoginGui;
import gui.RegisterGui;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
                // new RegisterGui().setVisible(true);
                // new BankingAppGui(
                //     new User(0, "username", "password", new BigDecimal("20.00"))
                // ).setVisible(true);
            }
        });

    }
}
