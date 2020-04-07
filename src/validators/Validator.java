/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import connection.ImportToDB;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Validator {
    
        
    public static String yesOrNoInput() {
        Scanner sc = new Scanner(System.in);
        String reply = sc.nextLine();
        boolean z = false;
        while (!z) {
            if (reply.equalsIgnoreCase("yes")) {
                reply = "yes";
                break;
            } else if (reply.equalsIgnoreCase("no")) {
                reply = "no";
                break;
            } else {
                System.out.println("Wrong input. Please type \"yes\" or \"no\"");
                reply = sc.nextLine();
            }
        }
        return reply;
    }

    public static LocalDate inputdate() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.MIN;
        System.out.println("(Please give the date in the proper form: DD/MM/YYYY)");
        try {
            String inputDate = sc.nextLine();
            date = LocalDate.parse(inputDate, formatter);
        } catch (Exception e) { 
            date = inputdate();
        }
        return date;
    }

    public static int inputInt() {
        Scanner sc = new Scanner(System.in);
        int number = -1;
        do {
            System.out.println("(Please give a number. No negative numbers are allowed)");
            String temp = sc.nextLine();
            try {
                number = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        } while (number < 0);
        return number;
    }

    public static String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (input.isEmpty()) {
            System.out.println("You forgot to type. ");
            input = sc.nextLine();
        }
        return input;
    }

    public static String useForNames (){
        Scanner sc = new Scanner (System.in); 
        String input = sc.nextLine();
        for (char c : input.toCharArray()){
            if (!Character.isLetter(c)){
                System.out.println("The name field requires only letters. Please type again.");
                return useForNames();
            }  
        } 
        return input;
    }
    
}
