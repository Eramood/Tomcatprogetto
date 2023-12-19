package it.tomcatprogetto.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbHandler {
	private static final String NOMEDB = "db_tomcat";
    private static final String URL = "jdbc:mysql://localhost:3306/"+NOMEDB;
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static DbHandler instance;
    private Connection connection;

    private DbHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DbHandler getInstance() {
        if (instance == null) {
            instance = new DbHandler();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}

