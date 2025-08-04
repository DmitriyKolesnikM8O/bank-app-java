package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd_objs.User;

/*
 * This gui will allow user to user functionality of the banking application.
 * This extends from BaseFrame which means we will need to define our own addGuiComponents() method.
 */
public class BankingAppGui extends BaseFrame implements ActionListener{

    private JTextField currentBalanceField;
    public JTextField getCurrentBalanceField() {
        return currentBalanceField;
    }

    public BankingAppGui(User user) {
        super("Banking Application", user);
    }

    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html>" + 
        "<body style='text-align: center'>" + "<b>Hello " + user.getUsername() + "</b><br>" +
        "What would you like to do today?</body></html>";

        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 30, getWidth() - 10, 45);
        welcomeMessageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Current Balance");
        currentBalanceLabel.setBounds(10, 80, getWidth(), 45);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 20));

        add(currentBalanceLabel);

        currentBalanceField = new JTextField("$" + user.getCurrentBalance());
        currentBalanceField.setBounds(15, 120, getWidth() - 40, 50);
        currentBalanceField.setFont(new Font("Dialog", Font.BOLD, 40));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);

        add(currentBalanceField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 200, getWidth() - 40, 45);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 30));
        depositButton.setHorizontalAlignment(SwingConstants.CENTER);
        depositButton.addActionListener(this);

        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 260, getWidth() - 40, 45);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 30));
        withdrawButton.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawButton.addActionListener(this);

        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Past Transaction");
        pastTransactionButton.setBounds(15, 320, getWidth() - 40, 45);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 30));
        pastTransactionButton.setHorizontalAlignment(SwingConstants.CENTER);
        pastTransactionButton.addActionListener(this);

        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 380, getWidth() - 40, 45);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 30));
        transferButton.setHorizontalAlignment(SwingConstants.CENTER);
        transferButton.addActionListener(this);

        add(transferButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 480, getWidth() - 40, 45);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 30));
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        logoutButton.addActionListener(this);

        add(logoutButton);
    }   

    @Override
    public void actionPerformed(ActionEvent var1) {
        String buttonPressed =var1.getActionCommand();

        if (buttonPressed.equalsIgnoreCase("Logout")) {
            new LoginGui().setVisible(true);
            this.dispose();
            return;
        }


        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);
        bankingAppDialog.setTitle(buttonPressed);
        
        if (buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
        || buttonPressed.equalsIgnoreCase("Transfer")) {
            bankingAppDialog.addCurrentBalanceAndAmount();
            
            bankingAppDialog.addActionButton(buttonPressed);
            

            if (buttonPressed.equalsIgnoreCase("Transfer")) {
                bankingAppDialog.addUserField();
                
                
            }

        } else if(buttonPressed.equalsIgnoreCase("Past Transaction")) {
            bankingAppDialog.addPastTransaction();
        }

        bankingAppDialog.setVisible(true);

    }
}
