import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customerName;
    private int orderID;
    private HashMap<Integer, Double> itemIdAndQuantity; // Maps item ID to quantity
    private String orderStatus;
    private int priority; // Flag for order priority
    private String specialRequest; // Additional field for special instructions
    private  Administrator admin;
    private  boolean request_accepted;

    // Constructor
    public Order(String customerName, int orderID, int priority, Administrator admin) {
        this.customerName = customerName;
        this.orderID = orderID;
        this.itemIdAndQuantity = new HashMap<>();
        this.orderStatus = "Pending";
        this.priority = priority;
        this.specialRequest = ""; // Default is no special request
        this.admin = admin;
    }

    // Getters and setters

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public HashMap<Integer, Double> getItemAndQuantity() {
        return itemIdAndQuantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public  void setRequest_accepted(boolean request_accepted) {
        this.request_accepted = request_accepted;
    }

    public boolean getRequest_accepted() {
        return request_accepted;
    }
    // Method to add an item and quantity to the order
    public boolean addItemAndQuantity(int itemID, Double quantity) {
        Double previousValue = itemIdAndQuantity.put(itemID, quantity);
        return previousValue == null; // Returns true if item was newly added, false if quantity was updated.
    }

    // Method to calculate the total price of the order (requires item prices from elsewhere)
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Integer, Double> entry : itemIdAndQuantity.entrySet()) {
            Item item = admin.getItemList().getItemById(entry.getKey());
            if (item != null) {
                double price = item.getPrice();
                totalPrice += price * entry.getValue();
            }
        }
        return totalPrice;
    }

    // Method to display order details using StringBuilder, including item prices and quantities
    public StringBuilder displayOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderID).append("\n");
        details.append("Customer Name: ").append(customerName).append("\n");
        details.append("Priority: ").append(priority > 0 ? "Yes" : "No").append("\n");
        details.append("Order Status: ").append(orderStatus).append("\n");
        details.append("Special Request: ").append(specialRequest).append("\n");
        details.append("Items Ordered:\n");

        double totalPrice = 0.0;

        for (Map.Entry<Integer, Double> entry : itemIdAndQuantity.entrySet()) {
            Item item = admin.getItemList().getItemById(entry.getKey());
            if (item != null) {
                double price = item.getPrice();
                double quantity = entry.getValue();
                double itemTotal = price * quantity;

                details.append(" - Item ID: ").append(entry.getKey())
                        .append(", Name: ").append(item.getItemName())
                        .append(", Price: $").append(price)
                        .append(", Quantity: ").append(quantity)
                        .append(", Total: $").append(itemTotal).append("\n");

                totalPrice += itemTotal;
            }
        }

        details.append("Total Order Price: $").append(totalPrice).append("\n");
        return details;
    }
}
