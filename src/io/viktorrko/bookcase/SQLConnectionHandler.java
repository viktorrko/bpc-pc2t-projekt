package io.viktorrko.bookcase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnectionHandler {
    private static SQLConnectionHandler instance;
    private Connection connection;

    private SQLConnectionHandler() {
        try {
            if(!FileHandler.validPath(String.format("%s\\db\\bookcase.db", System.getProperty("user.dir"))))
            		FileHandler.createDirectory(System.getProperty("user.dir") + "\\db");
        	
        	String url = String.format("jdbc:sqlite:%s\\db\\bookcase.db", System.getProperty("user.dir"));
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized SQLConnectionHandler getInstance() {
        if (instance == null) {
            instance = new SQLConnectionHandler();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Connection to SQLite has been closed.");
            } catch (SQLException e) {
                System.out.println("Error closing the SQLite database connection: " + e.getMessage());
            }
        }
    }
}