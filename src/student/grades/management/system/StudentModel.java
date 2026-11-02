package student.grades.management.system;

public class StudentModel {
    private String id;
    private String name;
    private double grade;

    public StudentModel(String id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getGrade() { return grade; }
}