import java.io.Serializable;
import java.util.Set;

public class SerializedData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<Customer> customers;
    private Set<Order> orders;

    public SerializedData(Set<Customer> customers, Set<Order> orders) {
        this.customers = customers;
        this.orders = orders;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
