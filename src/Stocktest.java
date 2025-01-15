import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Stocktest {

    @Test
    public void testSufficientStock() {
        Administrator admin = new Administrator(10);
        ManageItem manageItem = admin.getItemList();

        Item item = new Item("Chocolate", 10, 1, 20, 1, true);
        manageItem.addItem(item);
        boolean orderSuccessful = manageItem.find_item_by_id(1).getQuantity() >= 10;

        assertTrue(orderSuccessful, "Order should succeed when stock is sufficient.");
    }

    @Test
    public void testInsufficientStock() {
        Administrator admin = new Administrator(10);
        ManageItem manageItem = admin.getItemList();

        Item item = new Item("Chips", 15, 2, 5, 2, true); // Quantity: 5
        manageItem.addItem(item);

        boolean orderSuccessful = manageItem.find_item_by_id(2).getQuantity() >= 10;

        assertFalse(orderSuccessful, "Order should fail when stock is insufficient.");
    }
}
