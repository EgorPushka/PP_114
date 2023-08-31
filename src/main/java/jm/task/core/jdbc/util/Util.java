package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static jm.task.core.jdbc.color_const.AnsiColorConst.*;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "root";
    private static final String DB_USER_PASSWORD = "root";
    private static final String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory;

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

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = getProperties();
            configuration.setProperties(properties)
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DB_DRIVER_NAME);
        properties.put(Environment.URL, DB_URL);
        properties.put(Environment.USER, DB_USERNAME);
        properties.put(Environment.PASS, DB_USER_PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//        properties.put(Environment.HBM2DDL_AUTO, "create-drop");
        return properties;
    }

}
