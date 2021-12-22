package dao;

import models.members.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

/**
 * @author Negin Mousavi
 */
public abstract class AccessToDB {
    protected Connection connection;
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public AccessToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi-agency", "root", "123456");
    }

/*
    public abstract void showAllObjectsInDB() throws SQLException;
*/

/*    public User returnUserIfExists(String tableName, String columnName, String value) throws SQLException {
        if (connection != null) {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?;", tableName, columnName);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            }
        }
        return null;
    }*/

/*    public User createUser(ResultSet resultSet) throws SQLException {
        return null;
    }*/

/*    public User returnUserById(String tableName, int id) throws SQLException {
        if (connection != null) {
            String sql = String.format("SELECT * FROM %s WHERE id = ?;", tableName);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            }
        }
        return null;
    }*/
}