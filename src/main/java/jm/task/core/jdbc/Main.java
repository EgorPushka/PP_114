package jm.task.core.jdbc;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // реализуйте алгоритм здесь
//        Connection connection = Util.getConnection();
//        System.out.println("--------------");
//        Util.closeConnection(connection);

        UserDaoJDBCImpl u1 = new UserDaoJDBCImpl();

//        u1.dropUsersTable();
//
//        u1.createUsersTable();
        u1.saveUser("egor","push",(byte) 38);

    }
}
