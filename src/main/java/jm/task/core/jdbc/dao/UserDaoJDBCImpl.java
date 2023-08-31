package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(50) NOT NULL,"
                + "lastName VARCHAR(50) NOT NULL,"
                + "age TINYINT"
                + ")";

        if ((connection = Util.getConnection()) != null) {

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'users'");
                if (resultSet.next()) {
                    System.out.printf("%s DB :: The Table 'users' - already exists! %s%n", CB, RE);
                } else {
                    statement.executeUpdate(sql);
                    System.out.printf("%s DB :: Table 'users' - successfully created! %s%n", CB, RE);
                }

            } catch (SQLException e) {
                System.out.printf("%s DB :: Open Connection to DB Problem! %s%s%n", CB, RE, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(DB_CON_ERROR + e.getMessage());
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS users";

        if ((connection = Util.getConnection()) != null) {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                System.out.println(CB + "DB :: Table 'users' - successfully deleted" + RE);

            } catch (SQLException e) {
                System.out.printf(CB + "DB :: Open Connection to DB Problem! " + RE + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("DB :: Сonnection close error! " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        if ((connection = Util.getConnection()) != null) {

            try (PreparedStatement preStatement = connection.prepareStatement(sql)) {

                preStatement.setString(1, name);
                preStatement.setString(2, lastName);
                preStatement.setByte(3, age);
                preStatement.executeUpdate();
                System.out.printf("%s User (%s, %s, %s) - successfully added %s%n", CB, name, lastName, age, RE);

            } catch (SQLException e) {
                System.out.printf("%s DB :: Trouble adding record to database! %s%s%n", CB, RE, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(DB_CON_ERROR + e.getMessage());
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        String sql = "DELETE FROM users WHERE id = ?";

        if ((connection = Util.getConnection()) != null) {

            try (PreparedStatement preStatement = connection.prepareStatement(sql)) {

                preStatement.setLong(1, id);

                if (preStatement.executeUpdate() > 0) {
                    System.out.printf("%s DB :: User (id = %d) - successfully deleted! %s%n", CB, id, RE);
                } else {
                    System.out.printf("%s DB :: User (id = %d) - does not exist! %s%n", CB, id, RE);
                }

            } catch (SQLException e) {
                System.out.printf("%s DB :: Problems when trying to delete user with id = %d %s%s", CB, id, RE, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(DB_CON_ERROR + e.getMessage());
                }
            }
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        if ((connection = Util.getConnection()) != null) {

            try (Statement statement = connection.createStatement()) {

                ResultSet resSet = statement.executeQuery(sql);
                while (resSet.next()) {
                    userList.add(new User(resSet.getLong("id")
                            , resSet.getString("name")
                            , resSet.getString("lastName")
                            , resSet.getByte("age")));
                }

            } catch (SQLException e) {
                System.out.printf("%s DB :: No table or connection Problem! %s%s%n", CB, RE, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(DB_CON_ERROR + e.getMessage());
                }
            }
            if (userList.isEmpty()) {
                System.out.printf("%s DB :: Table is empty NO Users! %s%n", CB, RE);
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE users";

        if ((connection = Util.getConnection()) != null) {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                System.out.printf("%s DB :: Table 'users' - successfully cleared! %s%n", CB, RE);

            } catch (SQLException e) {
                System.out.printf("%s DB :: Trouble clearing table! %s%s%n", CB, RE, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(DB_CON_ERROR + e.getMessage());
                }
            }
        }

    }
}
