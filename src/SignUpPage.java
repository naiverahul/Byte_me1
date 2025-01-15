import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame {
    private JTextField nameField;
    private JButton signUpButton;
    private Administrator admin;

    public SignUpPage(Administrator admin) {
        this.admin = admin;
        setTitle("Sign Up");
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
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(signUpButton, gbc);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = nameField.getText();
                if (customerName == null || customerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                    return;
                }
                for (Customer existingCustomer : admin.getCustomers()) {
                    if (existingCustomer.getName().equalsIgnoreCase(customerName)) {
                        JOptionPane.showMessageDialog(null, "Customer already exists. Please try again.");
                        return;
                    }
                }
                Customer customer = new Customer(customerName, false, admin);
                if(admin.addCustomer(customer)) {
                    JOptionPane.showMessageDialog(null, "Sign-up successful! " + customer.getName());
                }else{
                    JOptionPane.showMessageDialog(null, "Sign-up failed. Please try again.");
                    return;
                }
                dispose();
                new CustomerMenu(admin, customer).setVisible(true);
            }
        });
    }
}