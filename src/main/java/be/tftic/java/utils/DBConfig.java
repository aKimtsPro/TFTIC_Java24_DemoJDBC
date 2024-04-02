package be.tftic.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DATABASE = "db_slide";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";


    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://%s:%d/%s".formatted(HOST,PORT,DATABASE),
                USER,
                PASSWORD
        );
    }


}
