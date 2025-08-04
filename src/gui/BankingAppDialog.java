package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd_objs.MyJDBC;
import bd_objs.Transaction;
import bd_objs.User;

public class BankingAppDialog extends JDialog implements ActionListener{
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user) {
        setSize(500, 600);

        setModal(true);

        setLocationRelativeTo(bankingAppGui);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);

        setLayout(null);

        this.user = user;
        this.bankingAppGui = bankingAppGui;
    }
    
    public void addCurrentBalanceAndAmount() {
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 20, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter Amount: ");
        enterAmountLabel.setBounds(0, 60, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 30, 20);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountField.setHorizontalAlignment(SwingConstants.LEFT);
        add(enterAmountField);
    }

    public void addActionButton(String actionButtonType) {
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(5, 500, getWidth() - 15, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 40));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 120, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 140, getWidth() - 30, 20);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserField.setHorizontalAlignment(SwingConstants.LEFT);
        add(enterUserField);
    }

    public void addPastTransaction(){
        pastTransactionPanel = new JPanel();

        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 15);

        pastTransactions = MyJDBC.getPastTransactions(user);

        for (int i = 0; i < pastTransactions.size(); i++) {
            Transaction transaction = pastTransactions.get(i);

            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel = new JLabel(transaction.getTranscationType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionAmountLabel = new JLabel(String.valueOf(transaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);

            pastTransactionContainer.setBackground(Color.WHITE);

            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pastTransactionPanel.add(pastTransactionContainer);
        }

        add(scrollPane);
    }

    private void handleTransaction(String transactionType, float amountVal) {
        Transaction transaction;
        
        if (transactionType.equalsIgnoreCase("Deposit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        } else {
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));

            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
            
        }
        if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)) {
            JOptionPane.showMessageDialog(this, transactionType + " Success!");

            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, transactionType + " Failed...");
        }
    }

    private void resetFieldsAndUpdateCurrentBalance() {
        enterAmountField.setText("");

        if (enterUserField != null) {
            enterUserField.setText("");
        }

        balanceLabel.setText("Balance: $" + user.getCurrentBalance());
        bankingAppGui.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUsername, float amount) {
        if (MyJDBC.transfer(user, transferredUsername, amount)){
            JOptionPane.showMessageDialog(this, "Transfer success!");
            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, "Transfer failed...");
        }
    }

    @Override
    public void actionPerformed(ActionEvent var1) {
        String buttonPressed = var1.getActionCommand();

        float amountVal = Float.parseFloat(enterAmountField.getText());

        if (buttonPressed.equalsIgnoreCase("Deposit")) {    
            handleTransaction(buttonPressed, amountVal);
        } else {
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));

            if (result < 0) {
                JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                return;
            }

            if (buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountVal);
            } else {
                String transferredUser = enterUserField.getText();

                handleTransfer(user, transferredUser, amountVal);
            }

        }
    }

}
