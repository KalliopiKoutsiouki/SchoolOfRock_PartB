/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import schoolofrock.classes.Assignment;
import schoolofrock.classes.Course;
import static connection.ImportToDB.con;
import schoolofrock.classes.Student;
import schoolofrock.classes.Trainer;

/**
 *
 * @author user
 */
public class ExportFromDB {


    // all students:
    public ArrayList<Student> returnAllStudents() throws SQLException {

        Student.getAllStudents().clear();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM STUDENT");
        while (result.next()) {
            Student.getAllStudents().add(new Student(result.getString("FIRST_NAME"), result.getString("LAST_NAME"), result.getDate("DATE_OF_BIRTH").toLocalDate(), result.getDouble("TUITION_FEES")));
        }
        return Student.getAllStudents();
    }

    // all trainers:
    public ArrayList<Trainer> returnAllTrainers() throws SQLException {

        Trainer.getAllTrainers().clear();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM TRAINER");
        while (result.next()) {
            Trainer.getAllTrainers().add(new Trainer(result.getString("FIRST_NAME"), result.getString("LAST_NAME"), result.getString("SUBJECT")));
        }
        return Trainer.getAllTrainers();
    }

    // all courses: 
    public ArrayList<Course> returnAllCourses() throws SQLException {

        Course.getAllCourses().clear();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM COURSE");
        while (result.next()) {
            Course.getAllCourses().add(new Course(result.getString("TITLE"), result.getString("STREAM"), result.getString("TYPE"), result.getDate("START_DATE").toLocalDate(), result.getDate("END_DATE").toLocalDate()));
        }
        return Course.getAllCourses();
    }

    // all assignments: 
    public ArrayList<Assignment> returnAllAssignments() throws SQLException {
        Assignment.getAllAssignments().clear();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM ASSIGNMENT");
        while (result.next()) {
            Assignment.getAllAssignments().add(new Assignment(result.getString("TITLE"), result.getString("DESCRIPTION"), result.getDate("SUB_DATE").toLocalDate(), result.getInt("ORAL_MARK"), result.getInt("TOTAL_MARK")));
        }
        return Assignment.getAllAssignments();
    }


// return students per course: 
    public ArrayList<Student> returnStudentsPerCourse(Course c) throws SQLException {
        if (!c.getListOfStudents().isEmpty()) {
            return c.getListOfStudents();
        }
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select *\n"
                + "FROM STUDENT S\n"
                + "INNER JOIN STUDENTS_PER_COURSE SC \n"
                + "ON S.STUDENT_ID = SC.STUDENT_ID\n"
                + "WHERE SC.COURSE_ID = " + (Course.getAllCourses().indexOf(c) + 1) + ";");
        while (result.next()) {
            c.getListOfStudents().add(Student.getAllStudents().get(result.getInt("STUDENT_ID") - 1));
        }

        return c.getListOfStudents();
    }

    // return trainers per course:
    public ArrayList<Trainer> returnTrainersPerCourse(Course c) throws SQLException {
        if (!c.getListOfTrainers().isEmpty()) {
            return c.getListOfTrainers();
        }
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM TRAINER T "
                + "INNER JOIN "
                + "(SELECT TC.TRAINER_ID "
                + "FROM TRAINERS_PER_COURSE TC "
                + "WHERE COURSE_ID =  " + (Course.getAllCourses().indexOf(c) + 1) + ") AS C1 "
                + "ON T.TRAINER_ID = C1.TRAINER_ID");
        while (result.next()) {
            c.getListOfTrainers().add(Trainer.getAllTrainers().get(result.getInt("TRAINER_ID") - 1));
        }
        return c.getListOfTrainers();
    }

    // return assignments per course:
    public ArrayList<Assignment> returnAssignmentsPerCourse(Course c) throws SQLException {
        if (!c.getListOfAssignments().isEmpty()) {
            return c.getListOfAssignments();
        }
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM ASSIGNMENT A "
                + "INNER JOIN "
                + "(SELECT AC.ASSIGNMENT_ID "
                + "FROM ASSIGNMENTS_PER_COURSE AC "
                + "WHERE COURSE_ID =  " + (Course.getAllCourses().indexOf(c) + 1) + ") AS C1 "
                + "ON A.ASSIGNMENT_ID = C1.ASSIGNMENT_ID");
        while (result.next()) {
            c.getListOfAssignments().add(Assignment.getAllAssignments().get(result.getInt("ASSIGNMENT_ID") - 1));
        }
        return c.getListOfAssignments();
    }

    // return assignments per student per course:
    public void returnAssignmentsPerStudentsPerCourse() throws SQLException {
        // ArrayList <String> tempArrayl = new ArrayList();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT A.TITLE, S.FIRST_NAME, S.LAST_NAME, C.STREAM , C.TYPE, C.START_DATE, C.END_DATE\n"
                + "FROM ASSIGNMENT A\n"
                + "INNER JOIN ASSIGNMENTS_PER_STUDENT APS\n"
                + "ON A.ASSIGNMENT_ID = APS.ASSIGNMENT_ID\n"
                + "INNER JOIN STUDENT S\n"
                + "ON S.STUDENT_ID = APS.STUDENT_ID\n"
                + "INNER JOIN ASSIGNMENTS_PER_COURSE AC\n"
                + "ON AC.ASSIGNMENT_ID = A.ASSIGNMENT_ID\n"
                + "INNER JOIN COURSE C\n"
                + "ON C.COURSE_ID = AC.COURSE_ID");

        while (result.next()) {
            System.out.printf("%-30s%-10s%-6s%-20s%-3s"
                    + "\n", result.getString("TITLE"), result.getString("FIRST_NAME"), result.getString("LAST_NAME"), result.getString("STREAM"), result.getString("TYPE"));
        }
    }

    // return list of students in more courses:
    public ArrayList<Student> returnStudentsInMoreCourses() throws SQLException {
        ArrayList<Student> listOfStudentsInMoreCourses = new ArrayList();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT S.STUDENT_ID, S.FIRST_NAME, S.LAST_NAME\n"
                + "FROM STUDENT S\n"
                + "INNER JOIN (SELECT SC.STUDENT_ID, COUNT(*)\n"
                + "FROM STUDENTS_PER_COURSE SC\n"
                + "GROUP BY SC.STUDENT_ID\n"
                + "HAVING COUNT(*) > 1) AS SIMC\n"
                + "ON S.STUDENT_ID = SIMC.STUDENT_ID");
        while (result.next()) {
            listOfStudentsInMoreCourses.add(Student.getAllStudents().get(result.getInt("STUDENT_ID") - 1));
        }
        return listOfStudentsInMoreCourses;
    }

    public ArrayList <Assignment> returnAssignmentsPerStudent (Student s) throws SQLException {
          if (!s.getAssignmentsPerStudent().isEmpty()) {
            return s.getAssignmentsPerStudent();
        }
            Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM ASSIGNMENT A "
                + "INNER JOIN "
                + "(SELECT AC.ASSIGNMENT_ID "
                + "FROM ASSIGNMENTS_PER_STUDENT AC "
                + "WHERE STUDENT_ID =  " + (Student.getAllStudents().indexOf(s) + 1) + ") AS C1 "
                + "ON A.ASSIGNMENT_ID = C1.ASSIGNMENT_ID");
        while (result.next()) {
            s.getAssignmentsPerStudent().add(Assignment.getAllAssignments().get(result.getInt("ASSIGNMENT_ID") - 1));
        }
        return s.getAssignmentsPerStudent();
    }
    
    
}
