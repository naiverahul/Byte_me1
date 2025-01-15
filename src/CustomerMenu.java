import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenu extends JFrame {
    private Administrator admin;
    private Customer customer;

    public CustomerMenu(Administrator admin, Customer customer) {
        this.admin = admin;
        this.customer = customer;
        setTitle("Customer Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] customerOptions = {
                "View All Items", "Search Items", "Filter by Category", "Sort by Price", "View Review", "Add Review",
                "View Cart", "Place Order", "Order History", "View Order Status", "Cancel Order", "Become VIP",
                "Remove Item", "Modify Order", "Exit"
        };

        for (String option : customerOptions) {
            JButton button = new JButton(option);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(button);
            panel.add(Box.createVerticalStrut(10));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (option) {
                        case "View All Items":
                            customer.view_all_items();
                            break;
                        case "Search Items":
                            customer.Search_items();
                            break;
                        case "Filter by Category":
                            customer.Filter_by_category();
                            break;
                        case "Sort by Price":
                            customer.Sort_by_price();
                            break;
                        case "View Review":
                            customer.view_review();
                            break;
                        case "Add Review":
                            customer.add_review();
                            break;
                        case "View Cart":
                            customer.view_total();
                            break;
                        case "Place Order":
                            customer.Checkout_process();
                            break;
                        case "Order History":
                            customer.Order_history();
                            break;
                        case "View Order Status":
                            customer.View_order_status();
                            break;
                        case "Cancel Order":
                            customer.Cancel_order();
                            break;
                        case "Become VIP":
                            customer.become_vip();
                            break;
                        case "Remove Item":
                            customer.Remove_items();
                            break;
                        case "Modify Order":
                            customer.Modify_quantity();
                            break;
                        case "Exit":
                            dispose();
                            // Reuse the current Administrator instance
                            JFrame mainFrame = new JFrame("Main Menu");
                            mainFrame.setContentPane(new Main(admin).getContentPane());
                            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            mainFrame.pack();
                            mainFrame.setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                    }
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }
}