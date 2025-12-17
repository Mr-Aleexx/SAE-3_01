import java.util.List;

public class Customer extends Person implements Payable {
    private int customerId;
    private List<Order> orders;

    @Override
    public double getAmount() {
        return 0.0;
    }
}

