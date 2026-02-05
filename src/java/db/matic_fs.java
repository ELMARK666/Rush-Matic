/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class matic_fs{
    
    public static Connection getConnection()
    {
        
        final Properties prop = new Properties();
	OutputStream output;
        InputStream input;
        try
            {
             Class.forName("com.mysql.cj.jdbc.Driver");
            
                //LOCAL
                String urls = "localhost"; 
                String username = "root";
                String password = "1234";//Password1
                Connection conn = DriverManager.getConnection("jdbc:mysql://"+urls+":3307/fs?allowMultiQueries=true&useUnicode=true&characterEncode=UTF-8", username, password);


              //LIVE DB CONN PORT 
//              String urls = "10.4.2.76";
//              String username = "mlmaticusr";
//              String password = "UDS78%fuRApQd@pK";//Password1
//              Connection conn = DriverManager.getConnection("jdbc:mysql://"+urls+":3306/glextraction?allowMultiQueries=true&useUnicode=true&characterEncode=UTF-8", username, password);

            return conn;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }    
    private static void setSqlMode(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SET sql_mode = ''")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
