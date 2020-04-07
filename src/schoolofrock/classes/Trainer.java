/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolofrock.classes;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Trainer {
    private String firstName;
    private String lastName;
    private String subject;
    static int count;
    
    private static ArrayList <Trainer> allTrainers = new ArrayList ();

    public Trainer(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }



    public static ArrayList<Trainer> getAllTrainers() {
        return allTrainers;
    }

    public static void setAllTrainers(ArrayList<Trainer> allTrainers) {
        Trainer.allTrainers = allTrainers;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
         return  firstName  + " " + lastName + ", Subject: " + "\''"+ subject + "\''";
    }
    
    
    
}
