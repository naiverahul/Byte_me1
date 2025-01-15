import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class LoginTest {

    @Test
    public void testValidLogin() {
        Administrator admin = new Administrator(10);

        // Add a mock customer to the system
        Customer mockCustomer = new Customer("rahul", false, admin);
        admin.getCustomers().add(mockCustomer);

        // Simulate login
        boolean loginSuccessful = false;
        for(Customer c : admin.getCustomers()){
            if(c.getName().equalsIgnoreCase("rahul")){
                loginSuccessful = true;
                break;
            }
        }
        assertTrue(loginSuccessful, "Login should succeed for a valid customer.");



    }

    @Test
    public void testInvalidLogin() {
        Administrator admin = new Administrator(10);
        Customer mockCustomer = new Customer("rahul", false, admin);
        admin.getCustomers().add(mockCustomer);

        boolean loginUnSuccessful = true;
        boolean found = false;
        for(Customer c : admin.getCustomers()){
            if(c.getName().equalsIgnoreCase("rahulagarwal")){
                found = true;
                break;
            }
        }
        if(found){
            loginUnSuccessful = false;
        }
        assertTrue(loginUnSuccessful, "Login should not succeed for a invalid customer.");
    }
}
