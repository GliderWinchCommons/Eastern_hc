/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashboardInterface;

import Communications.Observer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author dbennett3
 */
public class MessageLogger implements Observer {

    @Override
    public void update() { }

    @Override
    public void update(String msg) {
        String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
        String clientDriverName = "org.apache.derby.jdbc.ClientDriver";
        String databaseConnectionName = "jdbc:derby:WinchCommonsBlackBox;create=true";
        
        long unixTime = System.currentTimeMillis();
        
        try {
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(ClassNotFoundException e) {
            System.out.println("Error");
        }
        
        try(Connection connect = DriverManager.getConnection(databaseConnectionName)) {
            PreparedStatement insertStatement = connect.prepareStatement(
                    "INSERT INTO Messages(timestamp, message)"
                    + "values(?,?)");
            insertStatement.setString(1, String.valueOf(unixTime));
            insertStatement.setString(2, msg);
            insertStatement.executeUpdate();
            insertStatement.close();
            
        }catch(SQLException e) {
            System.out.println("Error 2");
        }
        
    }
    
    
    
}
