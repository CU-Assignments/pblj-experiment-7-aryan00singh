// Program 2: Student Management System Using JDBC and MVC

// Student.java (Model)
class Student {
    int id;
    String name, department;
    double marks;

    public Student(int id, String name, String department, double marks) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
}

// StudentController.java (Controller)
class StudentController {
    Connection conn;

    public StudentController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "yourpassword");
    }

    public void addStudent(Student s) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
        pst.setInt(1, s.id);
        pst.setString(2, s.name);
        pst.setString(3, s.department);
        pst.setDouble(4, s.marks);
        pst.executeUpdate();
    }

    public void viewStudents() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Student");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4));
        }
    }

    public void updateStudent(int id, double newMarks) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("UPDATE Student SET Marks=? WHERE StudentID=?");
        pst.setDouble(1, newMarks);
        pst.setInt(2, id);
        pst.executeUpdate();
    }

    public void deleteStudent(int id) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE FROM Student WHERE StudentID=?");
        pst.setInt(1, id);
        pst.executeUpdate();
    }
}

// StudentView.java (View)
public class StudentView {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            StudentController controller = new StudentController();
            int choice;

            do {
                System.out.println("\n1. Add Student\n2. View Students\n3. Update Marks\n4. Delete Student\n5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Marks: ");
                        double marks = sc.nextDouble();
                        controller.addStudent(new Student(id, name, dept, marks));
                        break;
                    case 2:
                        controller.viewStudents();
                        break;
                    case 3:
                        System.out.print("Enter ID: ");
                        int sid = sc.nextInt();
                        System.out.print("New Marks: ");
                        double newMarks = sc.nextDouble();
                        controller.updateStudent(sid, newMarks);
                        break;
                    case 4:
                        System.out.print("Enter ID: ");
                        int did = sc.nextInt();
                        controller.deleteStudent(did);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                }
            } while (choice != 5);
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

//OUTPUT:-
1. Add Student
2. View Students
3. Update Marks
4. Delete Student
5. Exit
Enter choice: 1
ID: 1
Name: Aryan
Department: CSE
Marks: 88.5
Enter choice: 2
1 Aryan CSE 88.5
Enter choice: 3
Enter ID: 1
New Marks: 91.2
Enter choice: 4
Enter ID: 1
Enter choice: 5
Exiting...
