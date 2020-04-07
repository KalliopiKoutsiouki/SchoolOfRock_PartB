/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolofrock_partb;



import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import menu.Menu;


/**
 *
 * @author user
 */
public class SchoolOfRock_PartB {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args)  {
          
        
        try {
            Menu.introduction();
        } catch (SQLException ex) {
            Logger.getLogger(SchoolOfRock_PartB.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
}
