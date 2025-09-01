import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BankAccount {
    private double balance;
    private String pin;

    public BankAccount(double initialBalance, String pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    } 

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean validatePIN(String enteredPin) {
        return this.pin.equals(enteredPin);
    }
}


class ATM_GUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField amountField;
    private JTextArea outputArea;
    private JButton depositBtn, withdrawBtn, checkBtn, clearBtn;

    public ATM_GUI(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Amount:"));
        amountField = new JTextField(10);
        inputPanel.add(amountField);

        
        JPanel buttonPanel = new JPanel();
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        checkBtn = new JButton("Check Balance");
        clearBtn = new JButton("Clear");

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        checkBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.add(clearBtn);

        outputArea = new JTextArea(8, 35);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == depositBtn) {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    account.deposit(amount);
                    outputArea.append(" Deposited: Rs." + amount + "\n");
                } else {
                    outputArea.append(" Enter a positive amount to deposit!\n");
                }
            } else if (e.getSource() == withdrawBtn) {
                double amount = Double.parseDouble(amountField.getText());
                boolean success = account.withdraw(amount);
                if (success) {
                    outputArea.append(" Withdrawn: Rs." + amount + "\n");
                } else {
                    outputArea.append(" Insufficient Balance or Invalid Amount!\n");
                }
            } else if (e.getSource() == checkBtn) {
                outputArea.append(" Current Balance: Rs." + account.getBalance() + "\n");
            } else if (e.getSource() == clearBtn) {
                amountField.setText("");
                outputArea.setText("");
            }
        } catch (NumberFormatException ex) {
            outputArea.append(" Please enter a valid number!\n");
        }
    }
}

class LoginFrame extends JFrame implements ActionListener {
    private BankAccount account;
    private JPasswordField pinField;
    private JButton loginBtn;

    public LoginFrame(BankAccount account) {
        this.account = account;

        setTitle("ATM Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Enter PIN:"));
        pinField = new JPasswordField(10);
        add(pinField);

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        add(loginBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredPin = new String(pinField.getPassword());
        if (account.validatePIN(enteredPin)) {
            JOptionPane.showMessageDialog(this, " Login Successful!");
            this.dispose(); // close login window
            new ATM_GUI(account); // open ATM
        } else {
            JOptionPane.showMessageDialog(this, " Invalid PIN. Try again!");
            pinField.setText("");
        }
    }

}
public class ATM_WithLogin {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000, "1234"); 
        new LoginFrame(account);
    }
}
