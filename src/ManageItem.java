import java.io.Serializable;
import java.util.*;

public class ManageItem implements Serializable {
    private Set<Item> items;
    private Set<String> Category;

    public ManageItem(Set<String> Category) {
        items = new HashSet<>();
        Category = new HashSet<>();
    }
    public Set<Item> getItems() {
        return items;
    }
    
    // Method to add an item to the inventory
    public boolean addItem(Item item) {
        if (item.isEmpty()) {
            System.out.println("Cannot add item: Item quantity is zero.");
            return false;
        }
        boolean added = items.add(item);
        if (added) {
            System.out.println("Item added successfully: " + item.getItemName());
        } else {
            System.out.println("Item already exists: " + item.getItemName());
        }
        return added;
    }

    // Method to remove an item from the inventory by item ID
    public boolean removeItem(int item_id) {
        boolean removed = items.removeIf(item -> item.getItem_id() == item_id);
        if (removed) {
            System.out.println("Item removed successfully: ID " + item_id);
        } else {
            System.out.println("Item not found: ID " + item_id);
        }
        return removed;
    }

    // Method to get an item by its ID
    public Item getItemById(int item_id) {
        for (Item item : items) {
            if (item.getItem_id() == item_id) {
                return item;
            }
        }
        System.out.println("Item not found: ID " + item_id);
        return null;
    }

    // Method to display all items in the inventory
    public ArrayList<StringBuilder> displayItems() {
        ArrayList<StringBuilder> displayedItems = new ArrayList<>();
        if (items.isEmpty()) {
            System.out.println("No items available in the inventory.");
        } else {
            for (Item item : items) {
                StringBuilder stringBuilder = new StringBuilder()
                        .append("Item ID: ").append(item.getItem_id()).append(", ")
                        .append("Item Name: ").append(item.getItemName()).append(", ")
                        .append("Item Price: ").append(item.getPrice()).append(", ")
                        .append("Item Quantity: ").append(item.getQuantity()).append(", ")
                        .append("Availability: ").append(item.isAvailable() ? "Available" : "Denied")
                        .append(item.isAvailable() ? "" : " (Reason: " + item.getDenialReason() + ")")
                        .append("\n");
                displayedItems.add(stringBuilder);
                System.out.println(stringBuilder.toString());
            }
        }
        return displayedItems;
    }

    // Method to update an item by its ID
    public boolean updateItem(int item_id, String newName, int newPrice, int newQuantity) {
        Item item = getItemById(item_id);
        if (item != null) {
            item.setItemName(newName);
            item.setPrice(newPrice);
            item.setQuantity(newQuantity);
            System.out.println("Item updated successfully: ID " + item_id);
            return true;
        }
        System.out.println("Item not found: ID " + item_id);
        return false;
    }

    // Method to check if any items are available
    public boolean checkAvailability() {
        for (Item item : items) {
            if (item.isAvailable() && item.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    // Method to check if a specific item by ID is available
    public boolean isItemAvailable(int item_id) {
        Item item = getItemById(item_id);
        if (item != null && item.isAvailable() && item.getQuantity() > 0) {
            return true;
        }
        return false;
    }
    
    public  ArrayList<String> getItemsNameList(){
        ArrayList<String> items_list = new ArrayList<>();
        if(items.isEmpty()){
            return null;
        }
        else{
            for (Item item : items){
                items_list.add(item.getItemName());
            }
        }
        return items_list;
    }

    public Item find_item_by_id(int item_id) {
        for(Item item : items){
            if(item.getItem_id() == item_id){
                return item;
            }
        }
        return null;
    }

    public Set<Item> sort_items_low_to_high() {
        Set<Item> sorted_items = new TreeSet<>(Comparator.comparingInt(Item::getPrice));
        sorted_items.addAll(items); // Add all items to the sorted set
        return sorted_items;
    }
    public Set<Item> sort_items_high_to_low() {
        Set<Item> sorted_items = new TreeSet<>(Comparator.comparingInt(Item::getPrice).reversed());
        sorted_items.addAll(items); // Add all items to the sorted set
        return sorted_items;
    }
}
