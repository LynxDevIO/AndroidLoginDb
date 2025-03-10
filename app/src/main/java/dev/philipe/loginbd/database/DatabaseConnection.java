package dev.philipe.loginbd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class DatabaseConnection {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static volatile DatabaseConnection instance;
    private Connection con;
    private DatabaseConnection(DatabaseConfig dbconfig) {
        try {
            Class.forName(dbconfig.getDriver());
            con = DriverManager.getConnection(dbconfig.getURL(), dbconfig.getUSer(), dbconfig.getPassword());
            logger.info("Connected to database");
        } catch (SQLException | ClassNotFoundException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
            //logger.info("Connection error");
        }
    }

    public static DatabaseConnection getInstance(DatabaseConfig dbconfig) {
        DatabaseConnection result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DatabaseConnection.class) {
            if (instance == null) {
                instance = new DatabaseConnection(dbconfig);
            }
            return instance;
        }
    }

    public Connection getConnection() {
        return con;
    }
}
