import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class Administrator implements Menu_Management, Orfder_Management, Report_Generation ,Serializable{
    private ManageItem ItemList;
    private ManageOrder OrderList;
    private List<Integer> Id_List;
    private int IDcounter;
    private Set<String> Category;
    private Set<Customer> Customers;

    public Administrator(int order_limit) {
        this.Category = new HashSet<>();
        Category.add("1. Meetha");
        Category.add("2. Namkeen");
        Category.add("3. Teekha");
        Category.add("4. Ghass-Phuss");
        ItemList = new ManageItem(Category);
        OrderList = new ManageOrder(order_limit, this);
        Id_List = new ArrayList<>();
        Customers = new HashSet<>();
        IDcounter = 2;

        Item item1 = new Item("Chocolate", 10, 0, 1, 1, true);
        Item item2 = new Item("Chips", 20, 1, 100, 2, true);
        Item item4 = new Item("Samosa", 15, 2, 4, 4, true);

        ItemList.addItem(item1);
        ItemList.addItem(item2);
        ItemList.addItem(item4);
    }

    public Set<Customer> getCustomers() {
        return Customers;
    }

    public ManageItem getItemList() {
        return ItemList;
    }

    public ManageOrder getOrderList() {
        return OrderList;
    }

    public StringBuilder showcategory() {
        StringBuilder categoryText = new StringBuilder("Select a category:\n");
        for (String cat : Category) {
            categoryText.append(cat).append("\n");
        }
        return categoryText;
    }

    public StringBuilder showitem() {
        StringBuilder itemText = new StringBuilder("Item_Name and ID:\n");
        for (Item i : ItemList.getItems()) {
            itemText.append(i.getItemName()).append(" : ").append(i.getItem_id()).append("\n");
        }
        return itemText;
    }
    public boolean addCustomer(Customer customer) {
        return Customers.add(customer);
    }

    public void showItemsInTable() {
        String[] columnNames = {"Item Name", "ID", "Category", "Price", "Availability", "Quantity"};
        List<Item> items = new ArrayList<>(ItemList.getItems());
        String[][] data = new String[items.size()][6];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getItemName();
            data[i][1] = String.valueOf(item.getItem_id());
            data[i][2] = String.valueOf(item.getCategory());
            data[i][3] = String.valueOf(item.getPrice());
            data[i][4] = item.isAvailable() ? "Yes" : "No";
            data[i][5] = String.valueOf(item.getQuantity());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("Items");
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(exitButton, BorderLayout.SOUTH);

        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void Add_new_items() {
        IDcounter++;
        String itemname = JOptionPane.showInputDialog("Enter the item name");
        if (itemname == null) return;
        JOptionPane.showMessageDialog(null, "Enter the price");
        int itemprice = Positive_Input.loop_input();
        if (itemprice == -1) return;
        int item_id = IDcounter;
        Id_List.add(item_id);
        JOptionPane.showMessageDialog(null, "Enter the quantity");
        int quantity = Positive_Input.loop_input();
        if (quantity == -1) return;
        boolean availale = quantity > 0;
        JOptionPane.showMessageDialog(null, showcategory());
        int cate = Positive_Input.loop_input();

        Item new_item = new Item(itemname, itemprice, item_id, quantity, cate, availale);
        ItemList.addItem(new_item);
    }

    @Override
    public void Updat_item() {
        JOptionPane.showMessageDialog(null, showitem());
        JOptionPane.showMessageDialog(null, "Enter the Item ID");
        int item_id = Positive_Input.loop_input();
        if (item_id == -1) return;
        Item item = ItemList.find_item_by_id(item_id);
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Item does not exist.");
        } else {
            String[] options = {"Change name", "Change Quantity", "Change Category", "Change price", "Change availability", "Exit"};
            boolean exit = false;
            while (!exit) {
                int choice = JOptionPane.showOptionDialog(null, "Select an action", "Update Item",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (choice) {
                    case 0:
                        String name = JOptionPane.showInputDialog("Enter new name:");
                        if (name == null) return;
                        item.setItemName(name);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Enter the new quantity");
                        int quan = Positive_Input.loop_input();
                        if (quan == -1) return;
                        item.setQuantity(quan);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, showcategory());
                        int cat = Positive_Input.loop_input();
                        if (cat == -1) return;
                        item.setCategory(cat);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "Enter the new price");
                        int price = Positive_Input.loop_input();
                        if (price == -1) return;
                        item.setPrice(price);
                        break;
                    case 4:
                        if (item.isAvailable()) {
                            JOptionPane.showMessageDialog(null, "Item set to Unavailable");
                            item.setAvailable(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Item set to available");
                            item.setAvailable(true);
                        }
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid input.");
                }
            }
        }
    }

    @Override
    public void Delete_item() {
        JOptionPane.showMessageDialog(null, showitem());
        JOptionPane.showMessageDialog(null, "Enter the Item ID");
        int item_id = Positive_Input.loop_input();
        if (item_id == -1) return;
        if (ItemList.removeItem(item_id)) {
            JOptionPane.showMessageDialog(null, "Item removed");
            for (Order order : this.OrderList.getOrders()) {
                for (int key : order.getItemAndQuantity().keySet()) {
                    if (key == item_id) {
                        order.setOrderStatus("Denied");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found");
        }
    }

    @Override
    public void MOdify_price() {
        JOptionPane.showMessageDialog(null, showitem());
        JOptionPane.showMessageDialog(null, "Enter the Item ID");
        int item_id = Positive_Input.loop_input();
        if (item_id == -1) return;
        Item item = ItemList.find_item_by_id(item_id);
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Item does not exist");
        } else {
            JOptionPane.showMessageDialog(null, "Enter new price");
            int price = Positive_Input.loop_input();
            if (price == -1) return;
            item.setPrice(price);
        }
    }

    @Override
    public void update_availablity() {
        JOptionPane.showMessageDialog(null, showitem());
        JOptionPane.showMessageDialog(null, "Enter the Item ID");
        int item_id = Positive_Input.loop_input();
        if (item_id == -1) return;
        Item item = ItemList.find_item_by_id(item_id);
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Item does not exist");
        } else {
            if (item.isAvailable()) {
                JOptionPane.showMessageDialog(null, "Item set to Unavailable");
                item.setAvailable(false);
            } else {
                JOptionPane.showMessageDialog(null, "Item set to available");
                item.setAvailable(true);
            }
        }
    }

    @Override
    public void Veiw_pending_oders() {
        StringBuilder pendingOrders = new StringBuilder("Pending Orders:\n");
        for (Order order : OrderList.getOrders()) {
            if ("Pending".equalsIgnoreCase(order.getOrderStatus())) {
                pendingOrders.append("Order ID: ").append(order.getOrderID())
                        .append(", Customer: ").append(order.getCustomerName())
                        .append(", Priority: ").append(order.getPriority())
                        .append(", Special Request: ").append(order.getSpecialRequest()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, pendingOrders.toString());
    }

    @Override
    public void Update_order_status() {
        String input = JOptionPane.showInputDialog("Enter the Order ID to update status:");
        if (input == null) return;

        int orderID;
        try {
            orderID = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Order ID. Please enter a valid integer.");
            return;
        }

        String[] statusOptions = {"Pending", "Processing", "Completed", "Cancelled"};
        String newStatus = (String) JOptionPane.showInputDialog(null, "Select new status:", "Update Order Status",
                JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);

        if (newStatus != null && OrderList.updateOrderStatus(orderID, newStatus)) {
            JOptionPane.showMessageDialog(null, "Order status updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update order status. Order may not exist.");
        }
    }

    public void Process_refunds() {
        String input = JOptionPane.showInputDialog("Enter the Order ID to process refund:");
        if (input == null) return;

        int orderID;
        try {
            orderID = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Order ID. Please enter a valid integer.");
            return;
        }

        Order order = OrderList.getOrderById(orderID);
        if (order != null && "Completed".equalsIgnoreCase(order.getOrderStatus())) {
            OrderList.removeOrder(order);
            JOptionPane.showMessageDialog(null, "Refund processed and order removed for Order ID: " + orderID);
        } else {
            JOptionPane.showMessageDialog(null, "Order not eligible for refund or not found.");
        }
    }

    @Override
    public void Handle_special_requests() {
        StringBuilder requests = new StringBuilder("Special Requests:\n");
        for (Order order : OrderList.getOrders()) {
            if (!order.getSpecialRequest().isEmpty()) {
                requests.append("Order ID: ").append(order.getOrderID())
                        .append(", Customer: ").append(order.getCustomerName())
                        .append(", Request: ").append(order.getSpecialRequest()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, requests.toString());

        String input = JOptionPane.showInputDialog("Enter the Order ID to accept the special request:");
        if (input == null) return;
        try {
            int orderId = Integer.parseInt(input);
            Order order = getOrderList().getOrderById(orderId);
            if (order != null) {
                order.setRequest_accepted(true);
                JOptionPane.showMessageDialog(null, "Order's special request accepted.");
            } else {
                JOptionPane.showMessageDialog(null, "Order does not exist.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid order ID.");
        }
    }

    @Override
    public void Daily_sales_report() {
        double totalSales = 0.0;
        StringBuilder report = new StringBuilder("Daily Sales Report:\n\n");

        for (Order order : OrderList.getOrders()) {
            if ("Completed".equalsIgnoreCase(order.getOrderStatus())) {
                double orderTotal = order.calculateTotalPrice();
                totalSales += orderTotal;
                report.append("Order ID: ").append(order.getOrderID())
                        .append(", Customer: ").append(order.getCustomerName())
                        .append(", Total: $").append(orderTotal).append("\n");
            }
        }

        report.append("\nTotal Sales for the Day: $").append(totalSales);
        JOptionPane.showMessageDialog(null, report.toString());
    }

    public boolean addOrder(Order order) {
        return OrderList.addOrder(order);
    }

    public void view_items() {
        String[] columnNames = {"Name", "Price", "Id", "Quantity", "Available"};
        List<Item> items = new ArrayList<>(ItemList.getItems());
        String[][] data = new String[items.size()][5];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getItemName();
            data[i][1] = String.valueOf(item.getPrice());
            data[i][2] = String.valueOf(item.getItem_id());
            data[i][3] = String.valueOf(item.getQuantity());
            data[i][4] = item.isAvailable() ? "Yes" : "No";
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("Items");
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(exitButton, BorderLayout.SOUTH);

        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void ProcessNextOrder() {
        StringBuilder next = OrderList.ProcessNextOrder();
        JOptionPane.showMessageDialog(null, next.toString() + "/n");
    }

    public void adminMenu() {
        final boolean[] Exit = {false};

        while (!Exit[0]) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            String[] buttonLabels = {
                    "Add New Item", "View_Items", "Update Item", "Delete Item", "Modify Price", "Update Availability",
                    "View Pending Orders", "Update Order Status", "Process Refunds",
                    "Handle Special Requests", "Generate Daily Sales Report", "Process Next Order in Queue", "Exit"
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
                                Add_new_items();
                                break;
                            case "View_Items":
                                view_items();
                                break;
                            case "Update Item":
                                Updat_item();
                                break;
                            case "Delete Item":
                                Delete_item();
                                break;
                            case "Modify Price":
                                MOdify_price();
                                break;
                            case "Update Availability":
                                update_availablity();
                                break;
                            case "View Pending Orders":
                                Veiw_pending_oders();
                                break;
                            case "Update Order Status":
                                Update_order_status();
                                break;
                            case "Process Refunds":
                                Process_refunds();
                                break;
                            case "Handle Special Requests":
                                Handle_special_requests();
                                break;
                            case "Generate Daily Sales Report":
                                Daily_sales_report();
                                break;
                            case "Process Next Order in Queue":
                                ProcessNextOrder();
                                break;
                            case "Exit":
                                Exit[0] = true;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                        }
                    }
                });
            }

            JOptionPane.showMessageDialog(null, panel, "Admin Menu", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void saveDataToFile() throws IOException {
        String filepath = "E:\\Asssignment_3\\src\\data.ser";
        File file = new File(filepath);

        if (!file.exists()) {
            System.out.println("File not found. Creating a new empty file.");
            file.createNewFile();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            if(this.Customers.isEmpty()){
                System.out.println("Null");
            }
            if(OrderList.getOrders().isEmpty()){
                System.out.println("Null");
            }
            SerializedData data = new SerializedData(this.Customers, this.OrderList.getOrders());
            oos.writeObject(data);
            System.out.println("Data saved successfully.");
            if(Customers.isEmpty()){
                System.out.println("Null");
            }
            if(OrderList.getOrders().isEmpty()){
                System.out.println("Null");
            }


            for(Customer c:Customers){
                c.setAdmin(this);
                System.out.println(c.getName());
            }
            for  (Order o:OrderList.getOrders()){
                o.setAdmin(this);
                System.out.println(o.getCustomerName());
            }
        }
    }

    public void loadDataFromFile() throws IOException, ClassNotFoundException {
        String filepath = "E:\\Asssignment_3\\src\\data.ser";
        File file = new File(filepath);

        if (!file.exists() || file.length() == 0) {
            System.out.println("File not found or is empty. Initializing default data.");
            this.Customers = new HashSet<>();
            this.OrderList = new ManageOrder(10, this);
            saveDataToFile();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            SerializedData data = (SerializedData) ois.readObject();
            this.Customers = data.getCustomers();
            this.OrderList = new ManageOrder(10, this);
            for (Order order : data.getOrders()) {
                OrderList.addOrder(order);
            }
            System.out.println("Data loaded successfully.");
            if(Customers.isEmpty()){
                System.out.println("Null");
            }
            if(OrderList.getOrders().isEmpty()){
                System.out.println("Null");
            }


            for(Customer c:Customers){
                c.setAdmin(this);
                System.out.println(c.getName());
            }
            for  (Order o:OrderList.getOrders()){
                o.setAdmin(this);
                System.out.println(o.getCustomerName());
            }
        }
    }

    public void viewCustomerDetails() {
        if (this.Customers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No customers available.");
            return;
        }

        StringBuilder details = new StringBuilder("Customer Details:\n");
        for (Customer customer : this.Customers) {
            details.append("Name: ").append(customer.getName())
                    .append(", VIP: ").append(customer.getVIP() ? "Yes" : "No")
                    .append("\nOrder History: ").append(customer.order_history_string_builder())
                    .append(" orders\n\n");
        }

        JOptionPane.showMessageDialog(null, details.toString());
    }
}