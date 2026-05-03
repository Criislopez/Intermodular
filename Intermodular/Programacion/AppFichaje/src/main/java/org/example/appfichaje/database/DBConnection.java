package org.example.appfichaje.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection(){

        if (connection == null){
            createConnection();
        }

        return connection;
    }

    private  static void createConnection(){

        String user = "root";
        String pass = "";
        String url = "localhost";
        String port = "3306";
        String dbName = "fichajeapp";

        String urlJDBC = String.format("jdbc:mysql://%s:%s/%s", url,port,dbName);

        try {

            connection = DriverManager.getConnection(urlJDBC,user,pass);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
