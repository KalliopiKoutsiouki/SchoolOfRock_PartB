/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolofrock.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 *
 * @author user
 */
public class Student {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double tuitionFees;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private static ArrayList<Student> listOfStudentsWithAssignments = new ArrayList<>();
    private static ArrayList <Student> allStudents = new ArrayList ();
    private  ArrayList <Assignment> assignmentsPerStudent = new ArrayList ();
    private  ArrayList <Assignment> assignmentsPerStudentWithMark = new ArrayList ();
    private ArrayList <Course>  coursesPerStudent = new ArrayList (); 
   
    

        public Student(String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }


    
    public ArrayList<Course> getCoursesPerStudent() {
        return coursesPerStudent;
    }

    public void setCoursesPerStudent(ArrayList<Course> coursesPerStudent) {
        this.coursesPerStudent = coursesPerStudent;
    }

    public static ArrayList<Student> getAllStudents() {
        return allStudents;
    }

    public static void setAllStudents(ArrayList<Student> allStudents) {
        Student.allStudents = allStudents;
    }
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public ArrayList<Assignment> getAssignmentsPerStudent() {
        return assignmentsPerStudent;
    }

    public void setAssignmentsPerStudent(ArrayList<Assignment> assignmentsPerStudent) {
        this.assignmentsPerStudent = assignmentsPerStudent;
    }
    
    @Override
    public String toString() {
         return  firstName + " " + lastName + ", DateOfBirth: " + dateOfBirth.format(formatter) + ", TuitionFees: " + tuitionFees ;
    }
    
    public ArrayList<Assignment> getAssignmentsPerStudentWithMark() {
        return assignmentsPerStudentWithMark;
    }

    public void setAssignmentsPerStudentWithMark(ArrayList<Assignment> assignmentsPerStudentWithMark) {
        this.assignmentsPerStudentWithMark = assignmentsPerStudentWithMark;
    }
    
    public static ArrayList<Student> getListOfStudentsWithAssignments() {
        return listOfStudentsWithAssignments;
    }

    public static void setListOfStudentsWithAssignments(ArrayList<Student> listOfStudentsWithAssignments) {
        Student.listOfStudentsWithAssignments = listOfStudentsWithAssignments;
    }
    }
    

