import java.util.List;

public class University {
    private String name;
    private Address address;
    private List<Department> departments;
    
    
    public University(String name, Address address, List<Department> departments) {
        this.name = name;
        this.address = address;
        this.departments = departments;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }


    public List<Department> getDepartments() {
        return departments;
    }


    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    
}

