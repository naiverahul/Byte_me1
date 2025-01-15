import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    private JPanel contentPane;
    private JButton adminButton;
    private JButton customerButton;
    private JButton exitButton;
    private JTextField titleTextField;
    private Administrator admin;

    public JPanel getContentPane() {
        return contentPane;
    }

    public Main(Administrator admin) {
        this.admin = admin; // Reuse the existing instance
        initializeUI();
    }

    public Main() {
        this.admin = new Administrator(10); // Default initialization
        try {
            admin.loadDataFromFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        initializeUI();
    }

    private void initializeUI() {
        contentPane = new JPanel(new BorderLayout());

        // Title text field
        titleTextField = new JTextField("Byte Me!");
        titleTextField.setHorizontalAlignment(JTextField.CENTER);
        titleTextField.setFont(new Font("Arial", Font.BOLD, 50));
        titleTextField.setEditable(false);
        contentPane.add(titleTextField, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        adminButton = new JButton("Enter as Admin");
        customerButton = new JButton("Enter as Customer");
        exitButton = new JButton("Exit");

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        adminButton.setFont(buttonFont);
        customerButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        buttonPanel.add(adminButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(exitButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(contentPane);
                frame.dispose();
                new AdminMenu(admin).setVisible(true);
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(contentPane);
                frame.dispose();
                String[] customerOptions = {"Sign Up", "Log In"};
                int customerChoice = JOptionPane.showOptionDialog(null, "Choose an option to continue:", "Customer Menu",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, customerOptions, customerOptions[0]);

                if (customerChoice == 0) {
                    new SignUpPage(admin).setVisible(true);
                } else if (customerChoice == 1) {
                    new LoginPage(admin).setVisible(true);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.saveDataToFile();
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setContentPane(new Main().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}