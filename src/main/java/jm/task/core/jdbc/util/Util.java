package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "1root";
    private static final String DB_USER_PASSWORD = "root";
    private static final String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName(DB_DRIVER_NAME);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USER_PASSWORD);
            System.out.println(CB + "BD :: Start Connection" + RE);
        } catch (ClassNotFoundException e) {
            System.out.println(CB + "BD :: Driver loading problems! " + RE + e.getMessage());
        } catch (SQLException e) {
            System.out.println(CB + "BD :: SQL Connection Problem! " + RE + e.getMessage());
        }
        return connection;
    }

}
