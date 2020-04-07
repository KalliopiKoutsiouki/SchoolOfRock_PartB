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
public class Assignment {
    private String title;
    private String description;
    private LocalDate subDateTime;
    private int oralMark;
    private int totalMark;
    
    static int counter;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private static ArrayList <Assignment> allAssignments = new ArrayList ();
    
    
    public Assignment(String title, String description, LocalDate subDateTime, int oralMark, int totalMark) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }
    
      public Assignment(Assignment assignment) {
        this.title= assignment.title;
        this.description = assignment.description;
        this.subDateTime = assignment.subDateTime;
        this.oralMark = assignment.oralMark;
        this.totalMark = assignment.totalMark;
    }


    public static ArrayList<Assignment> getAllAssignments() {
        return allAssignments;
    }

    public static void setAllAssignments(ArrayList<Assignment> allAssignments) {
        Assignment.allAssignments = allAssignments;
    } 
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(LocalDate subDateTime) {
        this.subDateTime = subDateTime;
    }

    public int getOralMark() {
        return oralMark;
    }

    public void setOralMark(int oralMark) {
        this.oralMark = oralMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    @Override
    public String toString() {
                return "Title: " + "\''" + title + "\''" + ", Description: " + description + ", SubDateTime: " + subDateTime.format(formatter)+ ", OralMark: " + oralMark + ", TotalMark: " + totalMark;
    }
    
    
}
