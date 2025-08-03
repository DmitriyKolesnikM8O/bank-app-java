package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd_objs.MyJDBC;
import bd_objs.User;

/*
 * This gui will allow user to login or launch the register gui.
 * This extends from BaseFrame which means we will need to define our own addGuiComponents() method.
 */
public class LoginGui extends BaseFrame{
    public LoginGui() {
        super("Login");
    }

    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("Banking Application Login");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setHorizontalAlignment(JLabel.CENTER);
        bankingAppLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));

        add(bankingAppLabel);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(20, 100, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 130, getWidth() - 50, 40);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(usernameField);

        JLabel passportLabel = new JLabel("Passport: ");
        passportLabel.setBounds(20, 200, getWidth() - 30, 24);
        passportLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        add(passportLabel);

        JPasswordField passportField = new JPasswordField();
        passportField.setBounds(20, 230, getWidth() - 50, 40);
        passportField.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(passportField);

        JButton LoginButton = new JButton("Login");
        LoginButton.setBounds(20, 400, getWidth() - 50, 40);
        LoginButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passportField.getPassword());

                User user = MyJDBC.validateLogin(username, password);
                if (user != null) {
                    LoginGui.this.dispose();

                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed...");
                }
            }
        });
        
        add(LoginButton);

        //TODO: maybe button
        JLabel registeredLabel = new JLabel("<html><a href=\"#\">Don`t have account? Registered</a></html>");
        registeredLabel.setBounds(0, 450, getWidth() - 10, 30);
        registeredLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        registeredLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registeredLabel);
    }
}
