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
public class Course {
    private String title;
    private String stream;
    private String type;
    private LocalDate start_date;
    private LocalDate end_date;
    
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private ArrayList <Trainer> listOfTrainers = new ArrayList ();
    private ArrayList <Assignment> listOfAssignments = new ArrayList ();
    private ArrayList <Student> listOfStudents = new ArrayList ();
    private static ArrayList <Course> allCourses = new ArrayList ();
    
   

    public Course(String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    

    public ArrayList<Assignment> getListOfAssignments() {
        return listOfAssignments;
    }

    public void setListOfAssignments(ArrayList<Assignment> listOfAssignments) {
        this.listOfAssignments = listOfAssignments;
    }

    public static ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    public static void setAllCourses(ArrayList<Course> allCourses) {
        Course.allCourses = allCourses;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public ArrayList<Trainer> getListOfTrainers() {
        return listOfTrainers;
    }

    public void setListOfTrainers(ArrayList<Trainer> listOfTrainers) {
        this.listOfTrainers = listOfTrainers;
    }


    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(ArrayList<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    @Override
    public String toString() {
        return "Stream: " + stream + ", Type: " + type + ", Start_date: " + start_date.format(formatter) + ", End_date: " + end_date.format(formatter);
    }
    
    
}
