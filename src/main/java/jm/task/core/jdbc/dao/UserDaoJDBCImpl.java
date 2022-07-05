package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.con;

public class UserDaoJDBCImpl implements UserDao {
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(50), lastName VARCHAR(50), age TINYINT UNSIGNED)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String SAVE_STUDENT = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?);";
    private static final String CLEAN_TABLE = "DELETE FROM users WHERE id>0";
    private static final String REMOVE_BY_ID = "DELETE FROM users WHERE id=?";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = con.createStatement()) {
            statement.execute(CREATE_TABLE);
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = con.createStatement()) {
            statement.execute(DROP_TABLE);
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepState = con.prepareStatement(SAVE_STUDENT)) {
            prepState.setString(1, name);
            prepState.setString(2, lastName);
            prepState.setByte(3, age);
            prepState.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prepState = con.prepareStatement(REMOVE_BY_ID)) {
            prepState.setLong(1, id);
            prepState.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement prepState = con.prepareStatement(SELECT_ALL)) {
            ResultSet resSet = prepState.executeQuery();
            while (resSet.next()) {
                allUsers.add(new User(resSet.getLong("id"), resSet.getString("name"),
                        resSet.getString("lastName"), resSet.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (PreparedStatement prepState = con.prepareStatement(CLEAN_TABLE)) {
            prepState.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}