package gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/*
 * This gui will allow user to register or launch the login gui.
 * This extends from BaseFrame which means we will need to define our own addGuiComponents() method.
 */
public class RegisterGui extends BaseFrame {
    public RegisterGui() {
        super("Register");
    }

    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("Banking Application Register");
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

        JLabel passportLabelRepeat = new JLabel("Re-type password: ");
        passportLabelRepeat.setBounds(20, 300, getWidth() - 30, 24);
        passportLabelRepeat.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        add(passportLabelRepeat);

        JPasswordField passportFieldRepeat = new JPasswordField();
        passportFieldRepeat.setBounds(20, 330, getWidth() - 50, 40);
        passportFieldRepeat.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(passportFieldRepeat);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 400, getWidth() - 50, 40);
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(registerButton);

        //TODO: maybe button
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have account? Sign in</a></html>");
        loginLabel.setBounds(0, 450, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }
}
