import javax.swing.*;
import java.io.Serializable;
import java.util.*;

public class Customer implements Browse_Menu, Cart_Operations, Order_Tracking, Serializable {
    private String name;
    private boolean isVIP;
    private Map<Integer, Integer> cart; // Stores item ID and quantity
    private List<Order> orderHistory;
    private Administrator admin; // Reference to administrator
    private int Credit;

    public Customer(String name, boolean isVIP, Administrator admin) {
        this.name = name;
        this.isVIP = isVIP;
        this.cart = new HashMap<>();
        this.orderHistory = new ArrayList<>();
        this.admin = admin;
        this.Credit = 100;
    }
    public boolean getVIP(){
        return isVIP;
    }
    public String getName() {
        return name;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    // Display available items with buttons to add to cart
    @Override
    public void view_all_items() {
        Set<Item> items = admin.getItemList().getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items available.");
            return;
        }

        // Initialize the options array with item names
        String[] options = new String[items.size()];
        int i = 0;
        for (Item item : items) {
            options[i] = item.getItemName() + " | " + item.getPrice();
            i++;
        }

        // Show a dialog with the list of items
        int choice = JOptionPane.showOptionDialog(null, "Select an item", "View All Items",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Check if a valid choice was made
        if (choice >= 0 && choice < options.length) {
            // Extract the selected item's name from the choice
            String selectedItemName = options[choice].split(" \\| ")[0];

            // Find the item by its name in the Set
            for (Item item : items) {
                if (item.getItemName().equalsIgnoreCase(selectedItemName)) {
                    addItemToCart(item);  // Add selected item to the cart
                    return;  // Exit the method once the item is found and added
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected or invalid choice.");
        }
    }



    @Override
    public void Search_items() {
        String searchName = JOptionPane.showInputDialog("Enter the name of the item to search:");

        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (Item item : admin.getItemList().getItems()) {
            if (item.getItemName().toLowerCase().contains(searchName.trim().toLowerCase())) {
                result.append(item.getItemName()).append(" - ").append(item.getQuantity()).append(" in stock\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Item not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Search Results:\n" + result.toString());
        }
    }

    @Override
    public void Filter_by_category() {
        String[] options = {"Meetha", "Namkeen", "Teekha", "Ghass-Phuss"};
        int category = JOptionPane.showOptionDialog(null, "Select a category", "Filter Items",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (Item item : admin.getItemList().getItems()) {
            if (item.getCategory() == category) {
                result.append(item.getItemName()).append(" - ").append(item.getQuantity()).append(" in stock\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No items found in this category.");
        } else {
            JOptionPane.showMessageDialog(null, "Filtered Items by Category:\n" + result.toString());
        }
    }


    @Override
    public void Sort_by_price() {
        // Sort items by price
        List<Item> sortedItems = new ArrayList<>(admin.getItemList().getItems());
        sortedItems.sort((item1, item2) -> Integer.compare(item1.getPrice(), item2.getPrice()));

        // Build a string to display in JOptionPane
        StringBuilder itemList = new StringBuilder("Items Sorted by Price:\n");
        for (Item item : sortedItems) {
            itemList.append(item.getItemName())
                    .append(" - Price: ")
                    .append(item.getPrice())
                    .append(", Quantity: ")
                    .append(item.getQuantity())
                    .append(" in stock\n");
        }

        // Show the sorted items in a JOptionPane
        JOptionPane.showMessageDialog(null, itemList.toString(), "Items Sorted by Price", JOptionPane.INFORMATION_MESSAGE);
    }


    // Function to add items to the cart and reduce item quantity in inventory
    private void addItemToCart(Item item) {
        String quantityInput = JOptionPane.showInputDialog("Enter quantity for " + item.getItemName() + ":");
        try {
            int quantity = Integer.parseInt(quantityInput);
            if (item.getQuantity() >= quantity) {
                cart.put(item.getItem_id(), cart.getOrDefault(item.getItem_id(), 0) + quantity);
                item.setQuantity(item.getQuantity() - quantity);
                JOptionPane.showMessageDialog(null, "Added to cart.");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient stock.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid quantity entered.");
        }
    }

    @Override
    public void Add_items() {

    }

    // Function to remove items from the cart
    @Override

    public void Remove_items() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.");
            return;
        }
        String[] options = new String[cart.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            Item item = admin.getItemList().getItemById(itemId);
            if (item != null) {
                options[i] = item.getItemName() + " (ID: " + itemId + ")";
                i++;
            }
        }
        int choice = JOptionPane.showOptionDialog(null, "Select an item to remove from the cart", "Remove Item",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice >= 0 && choice < cart.size()) {
            int selectedItemId = Integer.parseInt(options[choice].split("\\(ID: ")[1].replace(")", "").trim());
            Item selectedItem = admin.getItemList().getItemById(selectedItemId);
            int quantity = cart.get(selectedItemId);selectedItem.setQuantity(selectedItem.getQuantity() + quantity);
            cart.remove(selectedItemId);
            JOptionPane.showMessageDialog(null, selectedItem.getItemName() + " removed from cart.");
        } else {
            JOptionPane.showMessageDialog(null, "No item selected or invalid choice.");
        }
    }



    // Modify quantity in the cart and update item quantity accordingly
    @Override
    public void Modify_quantity() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.");
            return;
        }
        String[] options = new String[cart.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            Item item = admin.getItemList().getItemById(itemId);
            if (item != null) {
                options[i] = item.getItemName() + " (ID: " + itemId + ")";
                i++;
            }
        }
        int choice = JOptionPane.showOptionDialog(null, "Select an item to modify its quantity", "Modify Item Quantity",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice >= 0 && choice < cart.size()) {
            try {
                int selectedItemId = Integer.parseInt(options[choice].split("\\(ID: ")[1].replace(")", "").trim());
                Item selectedItem = admin.getItemList().getItemById(selectedItemId);
                int currentQuantity = cart.get(selectedItemId);
                int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity for " + selectedItem.getItemName() + ":"));
                int quantityDifference = newQuantity - currentQuantity;
                if (selectedItem != null && selectedItem.getQuantity() >= quantityDifference) {
                    cart.put(selectedItemId, newQuantity);
                    selectedItem.setQuantity(selectedItem.getQuantity() - quantityDifference);
                    JOptionPane.showMessageDialog(null, "Item quantity updated.");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient stock to modify quantity.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid entry.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected or invalid choice.");
        }
    }

    // View total price of items in the cart
    @Override
    public void view_total() {
        int total = 0;
        StringBuilder cartDetails = new StringBuilder("Cart Details:\n");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            Item item = admin.getItemList().getItemById(itemId);

            if (item != null) {
                int itemTotal = item.getPrice() * quantity;
                total += itemTotal;
                cartDetails.append("Item: ").append(item.getItemName())
                        .append(", Quantity: ").append(quantity)
                        .append(", Total: ").append(itemTotal).append("\n");
            }
        }
        cartDetails.append("Total Price: ").append(total);
        JOptionPane.showMessageDialog(null, cartDetails.toString());
    }

    // Place the order, add to history, and update Administrator order list
    @Override
    public void Checkout_process() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.");
            return;
        }
        String special = JOptionPane.showInputDialog("Enter any special request(Optional: press enter to continue):");
        if(special == null) {
            special = "";
        }

        int order_id = admin.getOrderList().getOrders().size()+1;
        Order newOrder = new Order(name,order_id, isVIP ? 1 : 2,admin); // Priority: 1 for VIP, 2 for regular customers
        newOrder.setSpecialRequest(special);
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            newOrder.addItemAndQuantity(entry.getKey(), Double.valueOf(String.valueOf(entry.getValue())));
        }

        if (admin.addOrder(newOrder)) { // Add order to Administrator’s order list
            orderHistory.add(newOrder);
            cart.clear();
            JOptionPane.showMessageDialog(null, "Order placed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to place order.");
        }
    }

    // Check order status through Administrator
    @Override
    public void View_order_status() {
        Order order =null;
        try {
            int orderId = Integer.parseInt(JOptionPane.showInputDialog("Enter order ID to view status:"));
            order = admin.getOrderList().getOrderById(orderId);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid order ID.");
        }

        if (order != null) {
            JOptionPane.showMessageDialog(null, "Order Status: " + order.getOrderStatus());
        } else {
            JOptionPane.showMessageDialog(null, "Order not found.");
        }
    }

    // Cancel order, restore quantities, and remove from Administrator list
    @Override
    public void Cancel_order() {
        Order order =null;
        int orderId = -1;
        try {
            orderId = Integer.parseInt(JOptionPane.showInputDialog("Enter order ID to view status:"));
            order = admin.getOrderList().getOrderById(orderId);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid order ID.");
            return;
        }

        if (order != null && admin.getOrderList().cancelOrder(orderId)) {
            restoreItemQuantities(order); // Restore quantities in inventory
            JOptionPane.showMessageDialog(null, "Order canceled and quantities restored.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to cancel order.");
        }
    }

    // Helper to restore item quantities in inventory
    private void restoreItemQuantities(Order order) {
        for (Map.Entry<Integer, Double> entry : order.getItemAndQuantity().entrySet()) {
            int itemId = Integer.parseInt(String.valueOf(entry.getKey()));
            double quantity = (int) Double.parseDouble(String.valueOf(entry.getValue()));
            Item item = admin.getItemList().getItemById(itemId);

            if (item != null) {

                item.setQuantity((int) (item.getQuantity() + quantity)); // Restore quantity
            }
        }
    }

    // Display past order history
    @Override
    public void Order_history() {
        if (orderHistory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No past orders found.");
        } else {
            StringBuilder history = new StringBuilder("Order History:\n");
            for (Order order : orderHistory) {
                history.append("Order ID: ").append(order.getOrderID())
                        .append(", Status: ").append(order.getOrderStatus()).append(" ").append( order.getRequest_accepted() ? "Your special request is accepted" : "Your special request is not accepted").append("\n");
            }
            JOptionPane.showMessageDialog(null, history.toString());
        }
    }

    public StringBuilder order_history_string_builder() {
        if (orderHistory.isEmpty()) {
            return new StringBuilder("No past orders found.");
        } else {
            StringBuilder history = new StringBuilder("Order History:\n");
            for (Order order : orderHistory) {
                history.append("Order ID: ").append(order.getOrderID())
                        .append(", Status: ").append(order.getOrderStatus())
                        .append(", Items: ");
                for (Map.Entry<Integer, Double> entry : order.getItemAndQuantity().entrySet()) {
                    Item item = admin.getItemList().getItemById(entry.getKey());
                    if (item != null) {
                        history.append(item.getItemName()).append(" (Quantity: ").append(entry.getValue()).append("), ");
                    }
                }
                history.append(order.getRequest_accepted() ? "Your special request is accepted" : "Your special request is not accepted")
                        .append("\n");
            }
            return history;
        }
    }
    public void  become_vip(){
        if(!isVIP) {
            isVIP = true;
            JOptionPane.showMessageDialog(null, "Hey! You are now a VIP customer");
            Credit = Credit - 50;
            JOptionPane.showMessageDialog(null, "50 credit points charged from you | Credits left: " + Credit);
        }else{
            JOptionPane.showMessageDialog(null, "You are already VIP");
        }
    }

    public void view_review() {
        // Retrieve the list of items from the admin's item list
        Set<Item> items = admin.getItemList().getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items available for review.");
            return;
        }

        // Create an array of item names for the options in the dialog
        String[] options = new String[items.size()];
        int i = 0;
        for (Item item : items) {
            options[i] = item.getItemName();
            i++;
        }

        // Show a dialog allowing the user to select an item
        int choice = JOptionPane.showOptionDialog(null, "Select an item to view its reviews", "View Reviews",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Check if a valid choice was made
        if (choice >= 0 && choice < items.size()) {
            // Get the selected item based on the choice
            Item selectedItem = admin.getItemList().find_item_by_id(choice);

            // Check if the selected item has any reviews
            ArrayList<String> reviews = selectedItem.getReview(); // Access the reviews list
            if (reviews.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No reviews available for the selected item.");
            } else {
                // Build a formatted string to display all reviews
                StringBuilder reviewContent = new StringBuilder("Reviews for " + selectedItem.getItemName() + ":\n");
                for (String review : reviews) {
                    reviewContent.append("- ").append(review).append("\n");
                }
                JOptionPane.showMessageDialog(null, reviewContent.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected or invalid choice.");
        }
    }

    public void add_review() {
        // Retrieve the list of items from the admin's item list
        Set<Item> items = admin.getItemList().getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items available for adding a review.");
            return;
        }

        // Create an array of item names for the options in the dialog
        String[] options = new String[items.size()];
        int i = 0;
        for (Item item : items) {
            options[i] = item.getItemName();
            i++;
        }

        // Show a dialog allowing the user to select an item
        int choice = JOptionPane.showOptionDialog(null, "Select an item to add a review", "Add Review",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Check if a valid choice was made
        if (choice >= 0 && choice < items.size()) {
            // Get the selected item based on the choice
            Item selectedItem = admin.getItemList().find_item_by_id(choice);

            // Prompt the user to enter a review
            String review = JOptionPane.showInputDialog(null, "Enter your review for " + selectedItem.getItemName() + ":");

            // Check if the review is not null or empty
            if (review != null && !review.trim().isEmpty()) {
                selectedItem.add_review(review); // Add the review to the item
                JOptionPane.showMessageDialog(null, "Review added successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Review not added. No text entered.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected or invalid choice.");
        }
    }


}
