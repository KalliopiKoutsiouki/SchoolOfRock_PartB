/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import connection.ImportToDB;
import java.sql.SQLException;
import java.util.Scanner;
import static connection.ImportToDB.con;
import static connection.ImportToDB.export;
import org.apache.commons.lang3.StringUtils;
import schoolofrock.classes.*;
import validators.Validator;

/**
 *
 * @author user
 */
public class Menu {

    public static void introduction() throws SQLException {

        ImportToDB.createConnection();

        Scanner sc = new Scanner(System.in);
        String x = "*";
        System.out.println(StringUtils.center(x, 14));
        for (int i = 0; i < 2; i++) {
            x += "**";
            System.out.println(StringUtils.center(x, 14));
        }
        System.out.println("SCHOOL OF ROCK");
        x = "***";
        for (int i = 2; i > 0; i--) {
            for (int j = 0; j < i - j; j++) {
                x += "**";
            }
            System.out.println(StringUtils.center(x, 14));
            x = "*";
        }
        System.out.println(StringUtils.center(x, 14));

        System.out.println();

        System.out.println("Hello and Welcome to my project!\n\nWould you like to navigate the existing database,"
                + " or create and add new values?");
        System.out.println();
        System.out.println("Kindly insert \"PREMADE\" to see the data so far or \"CREATE\", to create your own:");

        String select = sc.nextLine();

        while (!select.equals(null)) {
            if (select.equalsIgnoreCase("premade")) {
                System.out.println("\nYou chose premade! \nFollowing are the details of the Musical Bootcamp 2020\n");

               
                export.returnAllStudents();
                export.returnAllCourses();
                export.returnAllTrainers();
                export.returnAllAssignments();

                Menu.menu();
                break;

            } else if (select.equalsIgnoreCase("create")) {

                System.out.println("\nYou chose to create your own! You can also skip these steps");
                ImportToDB.initializeCounters();

                
                System.out.println("\nWould you like to create courses? (Yes/No)");
                String inputCourse = Validator.yesOrNoInput();
                if (inputCourse.equalsIgnoreCase("yes")){
                ImportToDB.createCoursesIN();
                }
                
                System.out.println("\nWould you like to create trainers? (Yes/No)");
                String inputTrainer = Validator.yesOrNoInput();
                if (inputTrainer.equalsIgnoreCase("yes")){
                ImportToDB.createAndAddATrainerIn();
                } 
                
                System.out.println("\nWould you like to create students?  (Yes/No)");
                String inputStudent=Validator.yesOrNoInput();
                if (inputStudent.equalsIgnoreCase("yes")){
                ImportToDB.createAndAddAStudent();
                }
                               
                System.out.println("\nWould you like to set the students' grades?  (Yes/No)");
                String skip = Validator.yesOrNoInput();
                if (skip.equalsIgnoreCase("yes")){
                ImportToDB.adjustTheMarks();}
                else { System.out.println("\nOk, maybe another time.");}
                
              
                export.returnAllStudents();
                export.returnAllCourses();
                export.returnAllTrainers();
                export.returnAllAssignments();

                Menu.menu();

                break;
            }
            System.out.println();
            System.out.println("Invalid input. Please try again:");
            select = sc.nextLine();
        }
        System.out.println("");
    }
    
    
    public static void menu() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("\nPlease chose from the choices below or type \"end\" to terminate:");
        boolean telos = false;
        while (!telos) {
            System.out.println("\nMENU ");
            System.out.println("1. Check the list of all students enrolled. "
                    + "\n2. Check the list of all trainers."
                    + "\n3. Check the list of all assignments."
                    + "\n4. Check the list of all courses."
                    + "\n5. Check the list of all the students per course."
                    + "\n6. Check the list of all the trainers per course."
                    + "\n7. Check the list of all the assignments per course."
                    + "\n8. Check the list of all the assignments per student per course."
                    + "\n9. Check the list of the students enrolled in more than one courses."
                    + "\nPress \"end\", to terminate the procedure."
                    + "\nPress 0 to navigate again from the top");

            String menu = sc.nextLine();
            switch (menu) {

                case "1":
                    System.out.println("\nList of all enrolled PRODIGIES: ");
                    System.out.println("-------------------------------");
                    for (int i = 0; i < Student.getAllStudents().size(); i++) {
                        int j = i + 1;
                        System.out.println(j + ". " + Student.getAllStudents().get(i).toString());
                    }
                    break;

                case "2":
                    System.out.println("\nList of all TRAINERS: ");
                    System.out.println("---------------------");
                    for (int i = 0; i < Trainer.getAllTrainers().size(); i++) {
                        int j = i + 1;
                        System.out.println(j + ". " + Trainer.getAllTrainers().get(i).toString());
                    }
                    break;

                case "3":
                    System.out.println("\nList of all ASSIGNMENTS: ");
                    System.out.println("------------------------");
                    for (int i = 0; i < Assignment.getAllAssignments().size(); i++) {
                        int j = i + 1;
                        System.out.println(j + ". " + Assignment.getAllAssignments().get(i).toString());
                    }
                    break;

                case "4":
                    System.out.println("\nList of all COURSES: ");
                    System.out.println("--------------------");
                    for (int i = 0; i < Course.getAllCourses().size(); i++) {
                        int j = i + 1;
                        System.out.println(j + ". " + Course.getAllCourses().get(i).toString());
                    }
                    break;

                case "5":
                    System.out.println("\nList of Students per Course: ");
                    System.out.println("----------------------------");
                    for (Course c : Course.getAllCourses()) {
                        ImportToDB.export.returnStudentsPerCourse(c);
                        System.out.println("\n" + "Students of course " + c.getStream() + " " + c.getType() + ":");
                        for (int i = 0; i < c.getListOfStudents().size(); i++) {
                            System.out.println("· " + c.getListOfStudents().get(i));
                        }
                    }
                    break;

                case "6":
                    System.out.println("\nList of Trainers per Course: ");
                    System.out.println("---------------------------");
                    for (Course c : Course.getAllCourses()) {
                        ImportToDB.export.returnTrainersPerCourse(c);
                        System.out.println("\n" + "Trainers of course " + c.getStream() + " " + c.getType() + ":");
                        for (int i = 0; i < c.getListOfTrainers().size(); i++) {
                            System.out.println("· " + c.getListOfTrainers().get(i));
                        }
                    }

                    break;

                case "7":
                    System.out.println("\nList of Assignments per Course: ");
                    System.out.println("--------------------------------");
                    for (Course c : Course.getAllCourses()) {
                        ImportToDB.export.returnAssignmentsPerCourse(c);
                        System.out.println("\n" + "Assignments of course " + c.getStream() + " " + c.getType() + ":");
                        for (int i = 0; i < c.getListOfAssignments().size(); i++) {
                            System.out.println("· " + c.getListOfAssignments().get(i));
                        }
                    }
                    break;

                case "8":
                    System.out.println("\nList of Assignments per Student per Course: ");
                    System.out.println("------------------------------------------");
                    ImportToDB.export.returnAssignmentsPerStudentsPerCourse();

                    break;

                case "9":
                    System.out.println("Students enrolled in more than one courses:");
                    System.out.println("----------------------------------------");
                    for (int i = 0; i < ImportToDB.export.returnStudentsInMoreCourses().size(); i++) {
                        System.out.println("· " + ImportToDB.export.returnStudentsInMoreCourses().get(i));
                    }

                    break;
                    
                case "0":
                    Menu.introduction();
                    break;
                    
                case "end":
                    con.close();
                    System.out.println("\nThank you very much for your time!  ");
                    telos = true;
                    break;

                case "END": 
                    con.close();
                    System.out.println("\nThank you very much for your time! ");
                    telos = true;
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}
