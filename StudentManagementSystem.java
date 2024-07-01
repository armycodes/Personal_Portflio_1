import java.io.IOException;
import java.util.*;

public class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        try {
            students = Filemanager.loadStudents();
        } catch (IOException e) {
            students = new ArrayList<>();
        }
    }

    public void addStudent(String id, String name) {
        students.add(new Student(id, name));
    }

    public void deleteStudent(String id) {
        students.removeIf(student -> student.getId().equals(id));
    }

    public void updateStudent(String id, String newName) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                student = new Student(id, newName);
                break;
            }
        }
    }

    public void addCourseToStudent(String studentId, String courseCode, String courseName, int credits, String gradeStr) {
        Grade grade = Grade.valueOf(gradeStr.toUpperCase());
        Course course = new Course(courseCode, courseName, credits, grade);
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                student.addCourse(course);
                break;
            }
        }
    }

    public void displayStudents() {
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", GPA: " + student.calculateGPA());
            for (Course course : student.getCourses()) {
                System.out.println("  Course: " + course.getCode() + ", Name: " + course.getName() + ", Credits: " + course.getCredits() + ", Grade: " + course.getGrade());
            }
        }
    }

    public void save() {
        try {
            Filemanager.saveStudents(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Add Student");
                System.out.println("2. Delete Student");
                System.out.println("3. Update Student");
                System.out.println("4. Add Course to Student");
                System.out.println("5. Display Students");
                System.out.println("6. Save and Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        System.out.print("Enter student ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        system.addStudent(id, name);
                        break;
                    case 2:
                        System.out.print("Enter student ID to delete: ");
                        id = scanner.nextLine();
                        system.deleteStudent(id);
                        break;
                    case 3:
                        System.out.print("Enter student ID to update: ");
                        id = scanner.nextLine();
                        System.out.print("Enter new student name: ");
                        name = scanner.nextLine();
                        system.updateStudent(id, name);
                        break;
                    case 4:
                        System.out.print("Enter student ID: ");
                        id = scanner.nextLine();
                        System.out.print("Enter course code: ");
                        String courseCode = scanner.nextLine();
                        System.out.print("Enter course name: ");
                        String courseName = scanner.nextLine();
                        System.out.print("Enter course credits: ");
                        int credits = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter grade (A, B, C, D, F): ");
                        String gradeStr = scanner.nextLine();
                        system.addCourseToStudent(id, courseCode, courseName, credits, gradeStr);
                        break;
                    case 5:
                        system.displayStudents();
                        break;
                    case 6:
                        system.save();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }
}
