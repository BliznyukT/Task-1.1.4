package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 13);
        userService.saveUser("Oleg", "Svetlov", (byte) 34);
        userService.saveUser("Alexei", "Petrov", (byte) 21);
        userService.saveUser("Petr", "Ivanov", (byte) 43);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
        HibernateUtil.close();
    }
}