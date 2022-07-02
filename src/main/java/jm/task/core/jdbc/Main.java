package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 13);
        userDao.saveUser("Oleg", "Svetlov", (byte) 34);
        userDao.saveUser("Alexei", "Petrov", (byte) 21);
        userDao.saveUser("Petr", "Ivanov", (byte) 43);

        System.out.println(userDao.getAllUsers());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
        Util.close();
    }
}