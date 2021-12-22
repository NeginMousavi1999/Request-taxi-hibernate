package dao;

import enumerations.Gender;
import enumerations.UserStatus;
import models.members.Driver;
import models.members.Passenger;
import models.members.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Negin Mousavi
 */
public class PassengerDao extends AccessToDB {
    public PassengerDao() throws ClassNotFoundException, SQLException {
    }

    public int getId(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Passenger p where p.personalId=:id";
        Query<Passenger> query = session.createQuery(hql, Passenger.class);
        query.setParameter("id", id);
        List<Passenger> list = query.list();
        transaction.commit();
        session.close();
        return list.size() > 0 ? list.get(0).getId() : -1;
    }

    public boolean isObjectFound(String id) {
        return getId(id) > 0;
    }

    public Passenger returnPassengerByPersonalCode(String id) {
        int passengerId = getId(id);
        Passenger driver = null;
        if (passengerId != -1) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            driver = session.get(Passenger.class, passengerId);
            transaction.commit();
            session.close();
        }
        return driver;
    }


    public Passenger returnPassengerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, id);
        transaction.commit();
        session.close();
        return passenger;
    }

    public int addNewPassenger(Passenger passenger) throws SQLException {
        if (connection != null) {
            String sql = "INSERT INTO `taxi-agency`.`passengers` (`first_name`, `last_name`, `birth_year`, `gender`, `phone_number`," +
                    " `personal_id`, `status`, `account_balance`) VALUES (?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, passenger.getFirstName());
            statement.setString(2, passenger.getLastName());
            statement.setInt(3, passenger.getBirthYear());
            statement.setString(4, passenger.getGender().toString().toLowerCase());
            statement.setString(5, passenger.getPhoneNumber());
            statement.setString(6, passenger.getPersonalId());
            statement.setString(7, passenger.getUserStatus().toString().toLowerCase());
            statement.setDouble(8, passenger.getAccountBalance());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                return rowsInserted;
        }
        return 0;
    }

/*    public List<Passenger> showAllObjectsInDB() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM passengers");
            while (resultSet.next()) {
                User passenger = createUser(resultSet);
                System.out.println(passenger.toString());
            }
        }
    }*/

    public List<Passenger> showAllPassengers() {
        Session session = sessionFactory.openSession();
        List<Passenger> result;
        Transaction transaction = session.beginTransaction();
        String hql = "from Driver";
        System.out.println(hql);
        Query<Passenger> query = session.createQuery(hql, Passenger.class);
        result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

/*    @Override
    public User createUser(ResultSet resultSet) throws SQLException {
        Passenger passenger = new Passenger(resultSet.getString(7), resultSet.getString(2),
                resultSet.getString(3), Gender.valueOf(resultSet.getString(5).toUpperCase()), resultSet.getString(6),
                resultSet.getInt(4), resultSet.getDouble(9));
        passenger.setId(resultSet.getInt(1));
        passenger.setUserStatus(UserStatus.valueOf(resultSet.getString(8).toUpperCase()));
        return passenger;
    }*/

    public void updateStatus(Passenger passenger, UserStatus userStatus) throws SQLException {
        if (connection != null) {
            String sql = "UPDATE passengers SET status = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userStatus.toString().toLowerCase());
            statement.setInt(2, passenger.getId());
            statement.executeUpdate();
        }
    }

    public void updateAccountBalance(double value, int id) throws SQLException {
        if (connection != null) {
            String sql = String.format("UPDATE passengers SET account_balance='%s' WHERE id=%o", value, id);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate(sql);
        }
    }
}
