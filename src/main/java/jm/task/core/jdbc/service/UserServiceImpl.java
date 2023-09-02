package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDaoHibernateImpl userHiber = new UserDaoHibernateImpl();

    @Override
    public void createUsersTable() {
        userHiber.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userHiber.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userHiber.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        userHiber.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userHiber.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userHiber.cleanUsersTable();
    }

}

