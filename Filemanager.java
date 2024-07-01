import java.io.*;
import java.util.*;

public class Filemanager {
    private static final String FILE_NAME = "students.txt";

    public static void saveStudents(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "\n");
                for (Course course : student.getCourses()) {
                    writer.write(course.getCode() + "," + course.getName() + "," +
                            course.getCredits() + "," + course.getGrade() + "\n");
                }
            }
        }
    }

    public static List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Student currentStudent = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Student line
                    currentStudent = new Student(parts[0], parts[1]);
                    students.add(currentStudent);
                } else if (parts.length == 4 && currentStudent != null) { // Course line
                    Course course = new Course(parts[0], parts[1], Integer.parseInt(parts[2]), Grade.valueOf(parts[3]));
                    currentStudent.addCourse(course);
                }
            }
        }
        return students;
    }
}

