import java.io.*;
import java.util.*;

public class ManageOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<Order> orders;
    private transient int orderLimit;
    private  Administrator admin;
    private transient PriorityQueue<Order> OrderQueue;

    public ManageOrder(int limit,Administrator  admin){
        this.orders = new HashSet<>();
        this.orderLimit = limit; // Default Order Limit
        this.admin = admin;
        this.OrderQueue = new PriorityQueue<>(Comparator.comparingInt(Order::getPriority));
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public boolean addOrder(Order order) {
        if (orders.size() < orderLimit) {
            boolean added = orders.add(order);
            if (added) {
                System.out.println("Order added successfully for customer: " + order.getCustomerName());
                OrderQueue.offer(order);
            } else {
                System.out.println("Order already exists for Order ID: " + order.getOrderID());
            }
            return added;
        } else {
            System.out.println("Order limit reached. Cannot add more orders.");
            return false;
        }
    }

    public void removeOrder(Order order) {
        boolean removed = orders.remove(order);
        if (removed) {
            System.out.println("Order removed successfully for Order ID: " + order.getOrderID());
            OrderQueue.remove(order);
        } else {
            System.out.println("Order not found: Order ID " + order.getOrderID());
        }
    }

    public Order getOrderById(int orderID) {
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        System.out.println("Order not found for Order ID: " + orderID);
        return null;
    }

    public boolean updateOrderStatus(int orderID, String newStatus) {
        Order order = getOrderById(orderID);
        if (order != null) {
            order.setOrderStatus(newStatus);
            System.out.println("Order status updated successfully for Order ID: " + orderID + " to " + newStatus);
            return true;
        }
        return false;
    }

    public StringBuilder getAllOrders() {
        StringBuilder totalOrder = new StringBuilder("Order Summary:\n");

        if (orders.isEmpty()) {
            totalOrder.append("No orders found.\n");
        } else {
            for (Order order : orders) {
                totalOrder.append("Customer: ").append(order.getCustomerName())
                        .append(", Order ID: ").append(order.getOrderID())
                        .append(", Status: ").append(order.getOrderStatus())
                        .append(", Priority: ").append(order.getPriority() > 0 ? "Yes" : "No").append("\n");

                double orderTotalPrice = 0.0;

                for (Map.Entry<Integer, Double> entry : order.getItemAndQuantity().entrySet()) {
                    int itemId = entry.getKey();
                    double quantity = entry.getValue();
                    Item item = admin.getItemList().getItemById(itemId);

                    if (item != null) {
                        double price = item.getPrice();
                        double itemTotal = price * quantity;
                        orderTotalPrice += itemTotal;

                        totalOrder.append("  - Item ID: ").append(itemId)
                                .append(", Name: ").append(item.getItemName())
                                .append(", Price: $").append(price)
                                .append(", Quantity: ").append(quantity)
                                .append(", Total: $").append(itemTotal).append("\n");
                    } else {
                        totalOrder.append("  - Item ID: ").append(itemId)
                                .append(", Quantity: ").append(quantity)
                                .append(" (Item not found in inventory)\n");
                    }
                }

                totalOrder.append("Order Total Price: $").append(orderTotalPrice).append("\n\n");
            }
        }

        System.out.println(totalOrder.toString());
        return totalOrder;
    }


    public boolean isOrderLimitReached() {
        return orders.size() >= orderLimit;
    }

    public boolean cancelOrder(int orderID) {
        Order order = getOrderById(orderID);
        if (order != null) {
            order.setOrderStatus("Canceled");
            return true;
        }
       return false;
    }

    public StringBuilder ProcessNextOrder(){
        Order nextOrder = OrderQueue.poll();
        StringBuilder Next = new StringBuilder("Orders Completed:\n");
        if (nextOrder == null) {
            Next.append("No more orders to process");
        }else{
            nextOrder.setOrderStatus("Completed");
            Next.append("Order Id: ").append(nextOrder.getOrderID()).append(" | Staus: ").append(nextOrder.getOrderStatus()).append("\n");
        }
        return Next;
    }
}
