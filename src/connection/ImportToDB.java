/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import connection.ExportFromDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import schoolofrock.classes.*;
import validators.Validator;

/**
 *
 * @author user
 */
public class ImportToDB {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    static ArrayList<Course> coursesInTime = new ArrayList<>();
    public static ExportFromDB export = new ExportFromDB();
    public static Connection con;
    public static int assCounter;
    static int crsCounter;
    static int trCounter;
    static int stCounter;

//CREATE CONNECTION
    public static void createConnection() {

        String url = "jdbc:mysql: //localhost:3306/school_of_rock";
        String username = "root";
        String password = "******";
        if (password.equals("******")){
        System.out.println("Make sure to give your password inside ImportToDB.createConnection (String password = \"******\")");}
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // these counters keep track of data creation (++ when create new ) They are used in relating the new auto-increment ids with the arraylists.
    public static void initializeCounters() {
        try {
            assCounter = export.returnAllAssignments().size();
            crsCounter = export.returnAllCourses().size();
            trCounter = export.returnAllTrainers().size();
            stCounter = export.returnAllStudents().size();
        } catch (SQLException ex) {
            Logger.getLogger(ImportToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//CREATE COURSES AND ASSIGNMENTS ----------------------------------------------------------------
    public static void createCoursesIN() throws SQLException {
        ImportToDB.createAndAddACourseIN();
        boolean x = false;
        while (!x) {
            System.out.println("\nWould you like to create another course? (Yes/No) ");
            String moreCourses = Validator.yesOrNoInput();
            if (moreCourses.equals("yes")) {
                ImportToDB.createAndAddACourseIN();
            } else if (moreCourses.equals("no")) {
                System.out.println("Creation of courses finished.  ");
                x = true;
                break;
            }
        }
    }

    public static void createAndAddACourseIN() throws SQLException {

        PreparedStatement pstm = con.prepareStatement("INSERT INTO COURSE VALUES ( NULL, ?, ?, ?, ?, ? )");

        System.out.println("\nIn order to create a course, please give: ");

        System.out.println("Title: ");
        String courseTitle = Validator.inputString();
        pstm.setString(1, courseTitle);

        System.out.println("Stream: ");
        String courseStream = Validator.inputString();
        pstm.setString(2, courseStream);

        System.out.println("Type: ");
        String courseType = Validator.inputString();
        pstm.setString(3, courseType);

        System.out.println("Start Date: ");
        LocalDate startDate = Validator.inputdate();
        pstm.setDate(4, Date.valueOf(startDate));

        System.out.println("End Date:");
        LocalDate endDate = Validator.inputdate();
        boolean x = false;
        while (!x) {
            if (endDate.isAfter(startDate)) {
                x = true;
                break;
            } else {
                System.out.println("This date is out of range. Please type again. ");
                endDate = Validator.inputdate();
            }
        }
        pstm.setDate(5, Date.valueOf(endDate));

        int rowsAffected = pstm.executeUpdate();

        if (rowsAffected == 0) {
            System.out.println("Something went wrong, please try again");
            ImportToDB.createAndAddACourseIN();
        }

        Course cs = new Course(courseTitle, courseStream, courseType, startDate, endDate);
        crsCounter++;

        ImportToDB.createAndAddAnAssignmentIN(cs, crsCounter);

        boolean y = false;
        while (!y) {
            System.out.println("Would you like to create another assignment for this course? (Yes/No) ");
            String moreAssignments = Validator.yesOrNoInput();
            if (moreAssignments.equals("yes")) {
                ImportToDB.createAndAddAnAssignmentIN(cs, crsCounter);
            } else if (moreAssignments.equals("no")) {
                System.out.println("Creation of assignments for this course finished. ");
                y = true;
                break;
            }
        }
    }

    public static void createAndAddAnAssignmentIN(Course crs, int courseCounter) throws SQLException {

        PreparedStatement pstm = con.prepareStatement("INSERT INTO ASSIGNMENT VALUES ( NULL, ?, ?, ?, ?, ? )");
        int rowsAffected = 0;

        while (rowsAffected == 0) {
            System.out.println("In order to create an assignment for course " + crs.getTitle() + ", please give: ");
            System.out.println("Title: ");
            String title = Validator.inputString();
            pstm.setString(1, title);

            System.out.println("Description: ");
            String description = Validator.inputString();
            pstm.setString(2, description);

            System.out.println("Assignment's submission date: ");
            LocalDate subDate = Validator.inputdate();
            boolean x = false;
            while (!x) {
                if (subDate.isAfter(crs.getStart_date()) && subDate.isBefore(crs.getEnd_date())) {
                    x = true;
                    break;
                } else {
                    System.out.println("This date is out of the course's duration. Please type again:");
                    subDate = Validator.inputdate();
                }
            }
            pstm.setDate(3, Date.valueOf(subDate));

            System.out.println("Assignment's oral mark: ");
            int oralMark = Validator.inputInt();
            pstm.setInt(4, oralMark);

            System.out.println("Assignment's total mark: ");
            int totalMark = Validator.inputInt();
            pstm.setInt(5, totalMark);

            rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Something went wrong, please try again");
            }
        }
        assCounter++;
        PreparedStatement pstm2 = con.prepareStatement("INSERT INTO ASSIGNMENTS_PER_COURSE VALUES (?,?);");
        pstm2.setInt(1, assCounter);
        pstm2.setInt(2, courseCounter);
        int assignToCourse = pstm2.executeUpdate();
        if (assignToCourse == 0) {
            System.out.println("Cannot relate this assignment to this course");

        }
    }

//CREATE TRAINERS AND ADD TO COURSE ----------------------------------------------------------------
    public static void createAndAddATrainerIn() throws SQLException {

        PreparedStatement pstm = con.prepareStatement("INSERT INTO TRAINER VALUES ( NULL, ?, ?, ?)");

        boolean x = false;
        while (!x) {
            System.out.println("\nPlease create a trainer and assign them to a course: ");
            System.out.println("Trainer's first name: ");
            String firstname = Validator.useForNames();
            pstm.setString(1, firstname);

            System.out.println("Trainer's last name: ");
            String lastname = Validator.useForNames();
            pstm.setString(2, lastname);

            System.out.println("Trainer's subject: ");
            String subject = Validator.inputString();
            pstm.setString(3, subject);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Something went wrong, please try again");
                ImportToDB.createAndAddATrainerIn();
            }

            Trainer tr = new Trainer(firstname, lastname, subject);
            trCounter++;

            ImportToDB.addTrainerToCourse(tr);

            System.out.println("Do you wanna create another trainer? (Yes/No) ");
            String anotherTrainer = Validator.yesOrNoInput();
            switch (anotherTrainer) {
                case "no":
                    System.out.println("Ok, thank you.");
                    x = true;
                    break;
                case "yes":
            }
        }
    }

    public static void addTrainerToCourse(Trainer tr) throws SQLException {

        int f = 0;
        System.out.println("Please assign trainer: " + tr.getFirstName() + " " + tr.getLastName() + " to a course from the list below: ");
        export.returnAllCourses();

        for (Course c : Course.getAllCourses()) {
            f += 1;
            System.out.println(f + ". " + c.toString());
        }
        boolean z = false;
        while (!z) {
            System.out.println("\nChoose the number of the course you wish to assign the trainer to. ");
            int numberOfCourse = Validator.inputInt();
            while (numberOfCourse > Course.getAllCourses().size()) {
                System.out.println("Wrong number, please chose again: ");
                numberOfCourse = Validator.inputInt();
            }
            if (numberOfCourse == 0) {
                z = true;
                break;
            } else {
                PreparedStatement pstm = con.prepareStatement("INSERT INTO TRAINERS_PER_COURSE VALUES (?,?)");
                pstm.setInt(1, trCounter);
                pstm.setInt(2, numberOfCourse);
                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Could not assign the trainer to this course");
                } else {
                    System.out.println("Ok");
                }
            }
            System.out.println("Would you like to add the trainer to another course? (Yes/No) ");
            String anotherCourse = Validator.yesOrNoInput();
            switch (anotherCourse) {
                case "no":
                    System.out.println("Ok, thank you");
                    z = true;
                    break;
                default:

            }
        }
    }

//CREATE STUDENTS. ADD TO COURSE , ADD ASSIGNMENTS -----------------------------------------------------------
    public static void createAndAddAStudent() throws SQLException {

        PreparedStatement pstm = con.prepareStatement("INSERT INTO STUDENT VALUES (NULL, ?,?,?,?)");

        boolean x = false;
        while (!x) {
            System.out.println("Please create a Student and assign them to a course: ");
            System.out.println("Student's first name: ");
            String firstname = Validator.useForNames();
            pstm.setString(1, firstname);

            System.out.println("Student's last name: ");
            String lastname = Validator.useForNames();
            pstm.setString(2, lastname);

            System.out.println("Student's date of birth: ");
            LocalDate dateOfBirth = Validator.inputdate();
            pstm.setDate(3, Date.valueOf(dateOfBirth));

            double fees = 2500;
            pstm.setDouble(4, fees);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Something went wrong, please try again");
                ImportToDB.createAndAddAStudent();
            }
            Student s = new Student(firstname, lastname, dateOfBirth, fees);
            stCounter++;

            ImportToDB.addStudentToCourse(s);

            System.out.println("Would you like to create another Student? (Yes/No) ");
            String anotherStudent = Validator.yesOrNoInput();
            switch (anotherStudent) {
                case "no":
                    System.out.println("Ok, thank you.");
                    x = true;
                    break;
                case "yes":
            }
        }
    }

    public static void addStudentToCourse(Student s) throws SQLException {
        int y = 1;
        int f = 0;
        System.out.println("Please assign student: " + s.getFirstName() + " " + s.getLastName() + " to a course from the list below: ");
        export.returnAllCourses();

        for (Course c : Course.getAllCourses()) {
            f += 1;
            System.out.println(f + ". " + c.toString());
        }
        boolean z = false;
        while (!z) {
            System.out.println("\nChoose the number of the course you wish to assign the student to. ");
            int numberOfCourse = Validator.inputInt();
            while (numberOfCourse > Course.getAllCourses().size()) {
                System.out.println("Wrong number, please chose again: ");
                numberOfCourse = Validator.inputInt();
            }
            if (numberOfCourse == 0) {
                System.out.println("stop adding");
                z = true;
                break;

            } else {
                PreparedStatement pstm = con.prepareStatement("INSERT INTO STUDENTS_PER_COURSE VALUES (?,?)");
                pstm.setInt(1, stCounter);
                pstm.setInt(2, numberOfCourse);
                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Could not assign the student to this course");
                } else {
                    System.out.println("Ok");
                }

                
                Statement stm2 = con.createStatement();
                stm2.executeUpdate("UPDATE STUDENT SET TUITION_FEES =" + (y * 2500) + " WHERE STUDENT_ID = " + stCounter + ";");                
                ImportToDB.relateAssignmentsPerStudent(numberOfCourse);
              
            }
            System.out.println("Would you like to add the student to another course? (Yes/No) ");
            String anotherCourse = Validator.yesOrNoInput();
            switch (anotherCourse) {
                case "no":
                    System.out.println("Ok, thank you");
                    z = true;
                    break;
                default:
                    y++;

            }
        }
    }

    public static void relateAssignmentsPerStudent(int numberOfCourse) throws SQLException {

        // statement2 returns all the assignments ids of the course you are assigning the student to
        Statement getStudentsCourses = con.createStatement();
        ResultSet rs = getStudentsCourses.executeQuery("SELECT A.ASSIGNMENT_ID\n"
                + "FROM ASSIGNMENT A\n"
                + "INNER JOIN ASSIGNMENTS_PER_COURSE AC\n"
                + "ON AC.ASSIGNMENT_ID = A.ASSIGNMENT_ID\n"
                + "WHERE AC.COURSE_ID = " + numberOfCourse);
        
        while (rs.next()) {

            // passes the assignment id & student id to the table assignments_per_student
            Statement addIDs = con.createStatement();
            String st1 = "INSERT INTO ASSIGNMENTS_PER_STUDENT (STUDENT_ID , ASSIGNMENT_ID)  VALUES (" + stCounter + ", " + rs.getInt("ASSIGNMENT_ID") + ")";
            int success = addIDs.executeUpdate(st1);
            if (success == 0) {
                System.out.println("error 404");
            } 
        }
    }

    public static void adjustTheMarks() throws SQLException {

        export.returnAllStudents();
        export.returnAllAssignments();

        int f = 0;
        System.out.println("Please adjust the students' performance.");

        for (Student s : Student.getAllStudents()) {
            f += 1;
            System.out.println(f + ". " + s.toString());
        }
        boolean z = false;
        while (!z) {
            System.out.println("\nChoose the number of the student you want to mark. Press 0 to terminate. ");
            int numberOfStudent = Validator.inputInt();
            while (numberOfStudent > Student.getAllStudents().size()) {
                System.out.println("Wrong number, please chose again: ");
                numberOfStudent = Validator.inputInt();
            }
            if (numberOfStudent == 0) {
                System.out.println("Ok, thank you");
                z = true;
                break;

            } else {

                System.out.println("The assignments of student: " + Student.getAllStudents().get(numberOfStudent - 1).toString() + " are:");
                export.returnAssignmentsPerStudent(Student.getAllStudents().get(numberOfStudent - 1));
                for (int i = 0; i < Student.getAllStudents().get(numberOfStudent - 1).getAssignmentsPerStudent().size(); i++) {
                    System.out.println("· " + Student.getAllStudents().get(numberOfStudent - 1).getAssignmentsPerStudent().get(i).getTitle());
                }
                System.out.println("\nFor each assignment, please type the student's submission date and score:");
                for (Assignment a : Student.getAllStudents().get(numberOfStudent - 1).getAssignmentsPerStudent()) {
                    PreparedStatement pstm = con.prepareStatement("UPDATE ASSIGNMENTS_PER_STUDENT SET  SUB_DATE = ? , ORAL_MARK = ? , TOTAL_MARK = ? "
                            + " WHERE ASSIGNMENT_ID = ? AND STUDENT_ID = ?");
                    pstm.setInt(5, numberOfStudent);
                    System.out.println("\nAssignmet: " + a);
                    pstm.setInt(4, Assignment.getAllAssignments().indexOf(a));
                    System.out.println("\nStudent's assignment submission date:");
                    LocalDate subDate = Validator.inputdate();
                    pstm.setDate(1, Date.valueOf(subDate));
//                if (subDate.isAfter(a.getSubDateTime())) {
//                    System.out.println("The assignment is outdated, the student gets 0.");
//       //             Student.getAllStudents().get(numberOfStudent-1).getAssignmentsPerStudentWithMark().add(new Assignment(a.getTitle(), a.getDescription(), subDate, 0, 0));
//                } else {
                    System.out.println("Student's oral mark (score): ");
                    int oralMark = Validator.inputInt();
                    while (oralMark > a.getOralMark()) {
                        System.out.println("The maximum oral mark of this assignment is: " + "\"" + a.getOralMark() + "\"" + " Please give a valid number.");
                        oralMark = Validator.inputInt();
                    }
                    pstm.setInt(2, oralMark);
                    System.out.println("Student's total mark (score): ");
                    int totalMark = Validator.inputInt();
                    while (totalMark > a.getTotalMark()) {
                        System.out.println("The maximum total mark of this assignment is: " + "\"" + a.getTotalMark() + "\"" + " Please give a valid number.");
                        totalMark = Validator.inputInt();
                    }
                    pstm.setInt(3, totalMark);
                    int rowsAffected = pstm.executeUpdate();
                    if (rowsAffected == 0) {
                        System.out.println("Something went wrong");
                    } else {
                        System.out.println("Ok");
                    }
                }
            }
        }
    }
}
