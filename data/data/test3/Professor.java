import java.util.List;

public class Professor {
    private String name;
    private Department department;
    private List<Course> courses;
    
    
    public Professor(String name, Department department, List<Course> courses) {
        this.name = name;
        this.department = department;
        this.courses = courses;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Department getDepartment() {
        return department;
    }


    public void setDepartment(Department department) {
        this.department = department;
    }


    public List<Course> getCourses() {
        return courses;
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    
}

