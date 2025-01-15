

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private Administrator admin;

    public AdminMenu(Administrator admin) {
        this.admin = admin;
        setTitle("Admin Menu");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] buttonLabels = {
                "Add New Item", "View Items", "Update Item", "Delete Item", "Modify Price", "Update Availability",
                "View Pending Orders", "Update Order Status", "Process Refunds",
                "Handle Special Requests", "Generate Daily Sales Report", "Process Next Order in Queue","View Customer Detail", "Exit"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            panel.add(button);
            panel.add(Box.createVerticalStrut(10));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (label) {
                        case "Add New Item":
                            admin.Add_new_items();
                            break;
                        case "View Items":
                            admin.view_items();
                            break;
                        case "Update Item":
                            admin.Updat_item();
                            break;
                        case "Delete Item":
                            admin.Delete_item();
                            break;
                        case "Modify Price":
                            admin.MOdify_price();
                            break;
                        case "Update Availability":
                            admin.update_availablity();
                            break;
                        case "View Pending Orders":
                            admin.Veiw_pending_oders();
                            break;
                        case "Update Order Status":
                            admin.Update_order_status();
                            break;
                        case "Process Refunds":
                            admin.Process_refunds();
                            break;
                        case "Handle Special Requests":
                            admin.Handle_special_requests();
                            break;
                        case "Generate Daily Sales Report":
                            admin.Daily_sales_report();
                            break;
                        case "Process Next Order in Queue":
                            admin.ProcessNextOrder();
                            break;
                        case "View Customer Detail":
                            admin.viewCustomerDetails();
                            break;
                        case "Exit":
                            dispose();
                            JFrame mainFrame = new JFrame("Main Menu");
                            mainFrame.setContentPane(new Main(admin).getContentPane());
                            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            mainFrame.pack();
                            mainFrame.setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                    }
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }
}