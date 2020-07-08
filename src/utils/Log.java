package utils;

import java.io.*;
import java.time.ZonedDateTime;

public class Log {

    // Class variables go here
   private static final String LOGTITLE = "log";
   private static final String filePath = "C:\\Users\\dcfox\\Desktop\\Coding\\C195\\src\\utils\\" + LOGTITLE;
   private static final String altFilePath = "C:\\Users\\dcfox\\IdeaProjects\\C195\\src\\utils\\" + LOGTITLE;

    // Class Constructors go here
    public Log() {};

    // Class methods go here

    public static void writeLog(int userID, String username, boolean success) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);){
            printWriter.println("User ID: "+ userID + " , User Name: " + username + " , Time: " + ZonedDateTime.now()
            + " , Login Successful: " + (success ? " True" : " False"));
        }catch (IOException ex) {
            System.out.println("Error logged: " + ex.getMessage() + ". Trying alternate file path.");
            try (FileWriter fileWriter = new FileWriter(altFilePath, true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter);){
                printWriter.println("User ID: " + userID + " , User Name: " + username + " , Time: " + ZonedDateTime.now()
                        + " , Login Successful: " + (success ? " True" : " False"));
                }catch (IOException ex2){
                System.out.println("Error: " + ex2);
            }
        }
    }
}


