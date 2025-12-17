import java.util.List;

public class Student {
    private String name;
    private Address address;
    private List<Course> courses;
    
    
    public Student(String name, Address address, List<Course> courses) {
        this.name = name;
        this.address = address;
        this.courses = courses;
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


    public List<Course> getCourses() {
        return courses;
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    
}
