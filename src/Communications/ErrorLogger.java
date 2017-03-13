/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communications;

import java.io.*;
import java.sql.Timestamp;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author gene
 */
public class ErrorLogger {
    public static void logError(Exception e) {
        File log = new File("ErrorLog.txt");
        PrintStream stream = null;
        try {
             stream = new PrintStream(new FileOutputStream(log, true));
             stream.println("" + new Timestamp(System.currentTimeMillis()));
             stream.println("" + e.getMessage());
             stream.println("" + Arrays.toString(e.getStackTrace()) + "\n\n");
             stream.close();
        }
        catch(FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
