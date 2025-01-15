import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField nameField;
    private JButton loginButton;
    private JButton exitbutton;
    private Administrator admin;

    public LoginPage(Administrator admin) {
        this.admin = admin;
        setTitle("Log In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel nameLabel = new JLabel("Enter Customer Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(nameLabel, gbc);

        gbc.gridy++;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(nameField, gbc);

        gbc.gridy++;
        loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(loginButton, gbc);

        gbc.gridy++;
        exitbutton = new JButton("Exit");
        exitbutton.setFont(new Font("Arial", Font.BOLD, 16));
        add(exitbutton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = nameField.getText();
                if (customerName == null || customerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                    return;
                }
                for (Customer customer : admin.getCustomers()) {
                    if (customer.getName().equalsIgnoreCase(customerName)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        dispose();
                        new CustomerMenu(admin, customer).setVisible(true);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Customer not found. Please sign up first.");
            }
        });
        exitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Reuse the current Administrator instance
                JFrame mainFrame = new JFrame("Main Menu");
                mainFrame.setContentPane(new Main(admin).getContentPane());
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });
    }
}