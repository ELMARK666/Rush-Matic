/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    
    public static Connection getConnection()
    {
        final Properties prop = new Properties();
	OutputStream output;
        InputStream input;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            output = new FileOutputStream("db1.properties");
//		set the properties value
 		prop.setProperty("url","localhost");
		prop.setProperty("username", "root");
		prop.setProperty("password", "1234");//
                       
//                LIVE 
//                String urls = "10.4.2.76";
//                String username = "mlmaticusr";
//                String password = "UDS78%fuRApQd@pK";//
                
		// save properties to project root folder
		prop.store(output, null);
                
                input = new FileInputStream("db1.properties");

		// load a properties file
		prop.load(input);
//                
            String urls = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            
            //LIVE DB CONN PORT 
//            Connection conn = DriverManager.getConnection("jdbc:mysql://"+urls+":3306/asset?allowMultiQueries=true&useUnicode=true&characterEncode=UTF-8", username, password);
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+urls+":3307/asset?useUnicode=yes&characterEncoding=UTF-8", username, password);
            return conn;
        }
        catch(IOException | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static void setSqlMode(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SET sql_mode = ''")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
