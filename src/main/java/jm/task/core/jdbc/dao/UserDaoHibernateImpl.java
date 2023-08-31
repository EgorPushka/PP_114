package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        String sql_create = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(50) NOT NULL,"
                + "lastName VARCHAR(50) NOT NULL,"
                + "age TINYINT"
                + ")";
        String sql_exist = "SHOW TABLES LIKE 'users'";

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            boolean tableExist = session
                    .createNativeQuery(sql_exist)
                    .uniqueResult() != null;
            if (tableExist) {
                System.out.printf("%s DB :: The Table 'users' - already exists! %s%n", CB, RE);
            } else {
                session.createNativeQuery(sql_create)
                        .executeUpdate();
                System.out.printf("%s DB :: Table 'users' - successfully created! %s%n", CB, RE);
            }
            transaction.commit();

        } catch (HibernateException e) {
            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS users";

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        createUsersTable();

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        String sql = "DELETE FROM users WHERE id = ?";

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            int i = session
                    .createNativeQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();
            transaction.commit();

            if (i > 0) {
                System.out.printf("%s DB :: User (id = %d) - successfully deleted! %s%n", CB, id, RE);
            } else {
                System.out.printf("%s DB :: User (id = %d) - does not exist! %s%n", CB, id, RE);
            }

        } catch (HeadlessException e) {
            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        String sql_exist = "SHOW TABLES LIKE 'users'";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            boolean existTable = session
                    .createNativeQuery(sql_exist)
                    .uniqueResult() != null;

            if (existTable) {
                userList = session.createNativeQuery(sql).addEntity(User.class).getResultList();
            } else {
                System.out.printf("%s DB :: Table is empty NO Users! %s%n", CB, RE);
            }
            transaction.commit();

        } catch (HibernateException e) {
            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {

            System.out.println("Hiber :: Trouble! " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}