public class Employee extends Person implements Payable {
    private int employeeId;
    private double salary;

    @Override
    public double getAmount() {
        return salary;
    }
}

