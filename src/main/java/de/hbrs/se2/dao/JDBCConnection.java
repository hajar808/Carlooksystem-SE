package de.hbrs.se2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

    private static final String USERNAME = "hmenss2s";
    private static final String PASSWORT  ="hmenss2s";
    private static final String URL ="jdbc:postgresql://dumbo.inf.h-brs.de/hmenss2s";

    private static JDBCConnection instance;
    private Connection connection;


    public static JDBCConnection getInstance(){
        if(instance == null){
            instance = new JDBCConnection();
        }
        return instance;
    }

    private JDBCConnection(){

    }

    public Connection openConnection(){
        try {
            DriverManager.registerDriver( new org.postgresql.Driver() );
            Properties props = new Properties();
            props.setProperty("user",USERNAME);
            props.setProperty("password",PASSWORT);

            connection = DriverManager.getConnection(URL, props);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public  void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
