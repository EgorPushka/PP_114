package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "root";
    private static final String DB_USER_PASSWORD = "root";
    private static final String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection;
        Class.forName(DB_DRIVER_NAME); //динамическая загрузка драйвера, лучше чем статическая
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USER_PASSWORD);
        System.out.println(CB + "BD :: Start Connection" + RE);
        return connection;
    }

//    public static void closeConnection(Connection connection) throws SQLException {
//        connection.close();
//        System.out.println(CB + "BD :: Close Connection" + RE);
//    }

}
