package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(50) NOT NULL,"
                + "lastName VARCHAR(50) NOT NULL,"
                + "age TINYINT"
                + ")";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'users'");
            if (resultSet.next()) {
                System.out.println(CB + "DB :: The Table 'users' - already exists" + RE);
            } else {
                statement.executeUpdate(sql);
                System.out.println(CB + "DB :: Table 'users' - successfully created" + RE);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(CB + "DB :: Driver Problem! " + RE + e.getMessage());
        } catch (SQLException e) {
            System.out.println(CB + "DB :: Open Connection to DB Problem! " + RE + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(CB + "DB :: Table 'users' - successfully deleted" + RE);
        } catch (ClassNotFoundException e) {
            System.out.printf(CB + "DB :: Driver Problem! " + RE + e.getMessage());
        } catch (SQLException e) {
            System.out.printf(CB + "DB :: Open Connection to DB Problem! " + RE + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, name);
            preStatement.setString(2, lastName);
            preStatement.setByte(3, age);
            preStatement.executeUpdate();
            System.out.printf("%s User (%s, %s, %s) - successfully added%s", CB, name, lastName, age, RE);
        } catch (ClassNotFoundException e) {
            System.out.println(CB + "DB :: Driver Problem! " + RE + e.getMessage());
        } catch (SQLException e) {
            System.out.println(CB + "DB :: Open Connection to DB Problem! " + RE + e.getMessage());
        }
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection()) {

        } catch () {

        } catch () {

        }
    }

}
