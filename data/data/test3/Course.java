import java.util.List;

public class Course {
    private static String title;
    private Department department;
    private Professor professor;
    private List<Student> students;
    
    
    public Course(String title, Department department, Professor professor, List<Student> students) {
        this.title = title;
        this.department = department;
        this.professor = professor;
        this.students = students;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Department getDepartment() {
        return department;
    }


    public void setDepartment(Department department) {
        this.department = department;
    }


    public Professor getProfessor() {
        return professor;
    }


    public void setProfessor(Professor professor) {
        this.professor = professor;
    }


    public List<Student> getStudents() {
        return students;
    }


    public void setStudents(List<Student> students) {
        this.students = students;
    }

    

    
}

