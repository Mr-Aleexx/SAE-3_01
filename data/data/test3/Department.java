import java.util.List;

public class Department {
    private String name;
    private University university;
    private List<Professor> professors;
    private List<Course> courses;
    
    
    public Department(String name, University university, List<Professor> professors, List<Course> courses) {
        this.name = name;
        this.university = university;
        this.professors = professors;
        this.courses = courses;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public University getUniversity() {
        return university;
    }


    public void setUniversity(University university) {
        this.university = university;
    }


    public List<Professor> getProfessors() {
        return professors;
    }


    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }


    public List<Course> getCourses() {
        return courses;
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    
}
