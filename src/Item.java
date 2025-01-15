import java.io.Serializable;
import java.lang.foreign.SegmentAllocator;
import java.util.ArrayList;
import java.util.Objects;

public class Item implements Serializable {
    private String itemName;
    private int  price;
    private int item_id;
    private int quantity;
    private boolean available;
    private String denialReason;
    private int category;
    private ArrayList<String> Review;

    public Item(String itemName, int price, int item_id, int quantity, int category, boolean available) {
        this.itemName = itemName;
        this.price = price;
        this.item_id = item_id;
        this.quantity = quantity;
        this.available = available; // Default is available
        this.denialReason = ""; // Default no reason for denial
        this.category = category;
        this.Review = new ArrayList<>();
    }

    public String getItemName() { return itemName; }
    public int getPrice() { return price; }
    public int getItem_id() { return item_id; }
    public int getQuantity() { return quantity; }
    public boolean isAvailable() { return available; }
    public String getDenialReason() { return denialReason; }
    public int getCategory() { return category; }

    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setPrice(int price) { this.price = price; }
    public void setItem_id(int item_id) { this.item_id = item_id; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setDenialReason(String denialReason) { this.denialReason = denialReason; }
    public void setCategory(int category) { this.category = category; }

    public void addQuantity(int value) {
        this.quantity += value; // Increment quantity by value
    }

    // Method to deny an item with a reason
    public void denyItem(String reason) {
        this.available = false;
        this.denialReason = reason;
    }

    // Method to make an item available again
    public void allowItem() {
        this.available = true;
        this.denialReason = ""; // Reset the denial reason
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return item_id == item.item_id; // Use == for primitive types
    }


    @Override
    public int hashCode() {
        return Objects.hash(item_id); // Hash based on item_id only
    }

    @Override

    public String toString() {
        return String.format("%-15s | %-10.2f | %-5d | %-10d | %-10s",
                itemName, (double) price, item_id, quantity, available ? "Yes" : "No");
    }


    public boolean isEmpty() {
        return quantity == 0;
    }
    public void add_review (String review) {
        this.Review.add(review);
    }

    public ArrayList<String> getReview() {
        return this.Review;
    }
}
