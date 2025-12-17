package company;

import java.util.List;

public class Employee extends Person {
    private int employeeId;

    // Self association
    private Employee manager;          // 0..1
    private List<Employee> subordinates; // 0..*

    private Department department;
}

