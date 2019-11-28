
package utils;

import Model.User;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 *  Logging class 
 *  @author hosea
 */

/**
 *  Method to Log information to text file
 *  Logs username and if login is successful or if it Failed
 */
public class Logger {
    private static final String FILENAME = "userLog.txt";
    public static User Logger = new User();
    public Logger(){}
    
    public static void log (String username, boolean success){
        try(FileWriter fw = new FileWriter(FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)) {
            pw.println(ZonedDateTime.now()+ "" + username + (success ? " Success" : "Failure"));
        } catch(IOException e){
                    System.out.println("Logger Error: " + e.getMessage());
                }
  
        }  
    }

