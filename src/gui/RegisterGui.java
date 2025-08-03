package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd_objs.MyJDBC;
import bd_objs.User;


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

        JPasswordField passwortField = new JPasswordField();
        passwortField.setBounds(20, 230, getWidth() - 50, 40);
        passwortField.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(passwortField);

        JLabel passportLabelRepeat = new JLabel("Re-type password: ");
        passportLabelRepeat.setBounds(20, 300, getWidth() - 30, 24);
        passportLabelRepeat.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        add(passportLabelRepeat);

        JPasswordField passwortFieldRepeat = new JPasswordField();
        passwortFieldRepeat.setBounds(20, 330, getWidth() - 50, 40);
        passwortFieldRepeat.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        
        add(passwortFieldRepeat);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 400, getWidth() - 50, 40);
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwortField.getPassword());
                String passwordRepeat = String.valueOf(passwortFieldRepeat.getPassword());

                boolean isValid = validateUserInput(username, password, passwordRepeat);

                if (isValid) {
                    if (MyJDBC.register(username, password)) {
                        RegisterGui.this.dispose();
    
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);
                        JOptionPane.showMessageDialog(loginGui, "Register Success!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "User already exist");
                    }
      
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this, "Register failed...");
                } 
            }
        });
        
        add(registerButton);

        
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have account? Sign in</a></html>");
        loginLabel.setBounds(0, 450, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        loginLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });

        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String passwordRepeat) {
        if (username.length() == 0 || password.length() == 0) {
            return false;
        }
        if (!password.equals(passwordRepeat)) {
            return false;
        }

        return true;
    }
}
