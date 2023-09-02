package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService u1 = new UserServiceImpl();

        u1.removeUserById(2);
        u1.createUsersTable();
        u1.dropUsersTable();
        u1.createUsersTable();

    }
}
