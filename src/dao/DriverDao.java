package dao;

import enumerations.Gender;
import enumerations.TypeOfVehicle;
import enumerations.UserStatus;
import models.members.Driver;
import models.members.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Negin Mousavi
 */
public class DriverDao extends AccessToDB {
    public DriverDao() throws ClassNotFoundException, SQLException {
    }

    public int getId(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Driver d where d.personalId=:id";
        Query<Driver> query = session.createQuery(hql, Driver.class);
        query.setParameter("id", id);
        List<Driver> list = query.list();
        transaction.commit();
        session.close();
        return list.size() > 0 ? list.get(0).getId() : -1;
    }

    public boolean isObjectFound(String id) {
        return getId(id) > 0;
    }

    public Driver returnDriverByPersonalCode(String id) {
        int driverId = getId(id);
        Driver driver = null;
        if (driverId != -1) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            driver = session.get(Driver.class, driverId);
            transaction.commit();
            session.close();
        }
        return driver;
    }

    public Driver returnDriverByLocation(String location) {
        Driver driver = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Driver d where d.location=:location";
        Query<Driver> query = session.createQuery(hql, Driver.class);
        query.setParameter("location", location);
        List<Driver> list = query.list();
        driver = list.get(0);
        transaction.commit();
        session.close();
        return driver;
    }

    public Driver returnDriverById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, id);
        transaction.commit();
        session.close();
        return driver;
    }

/*    @Override
    public void showAllObjectsInDB() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM drivers");
            while (resultSet.next()) {
                User driver = createUser(resultSet);
                System.out.println(driver.toString());
            }
        }
    }*/

    public List<Driver> showAllDrivers() {
        Session session = sessionFactory.openSession();
        List<Driver> result;
        Transaction transaction = session.beginTransaction();
        String hql = "from Driver";
        System.out.println(hql);
        Query<Driver> query = session.createQuery(hql, Driver.class);
        result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

/*    @Override
    public User createUser(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver(resultSet.getString(7), resultSet.getString(2),
                resultSet.getString(3), Gender.valueOf(resultSet.getString(5).toUpperCase()),
                resultSet.getString(6), resultSet.getInt(4),
                TypeOfVehicle.valueOf(resultSet.getString(9).toUpperCase()),
                resultSet.getInt(10), resultSet.getString(11));
        driver.setId(resultSet.getInt(1));
        driver.setUserStatus(UserStatus.valueOf(resultSet.getString(8).toUpperCase()));
        return driver;
    }*/

    public void updateStatus(Driver driver, UserStatus userStatus) throws SQLException {
        if (connection != null) {
            String sql = "UPDATE drivers SET status = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userStatus.toString().toLowerCase());
            statement.setInt(2, driver.getId());
            statement.executeUpdate();
        }
    }

    public int addNewDriver(Driver driver) throws SQLException {
        if (connection != null) {
            String sql = "INSERT INTO `taxi-agency`.`drivers` (`first_name`, `last_name`, `birth_year`, `gender`, `phone_number`," +
                    " `personal_id`, `status`, `vehicle_type`, `vehicle_id_fk`, `location`) VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setInt(3, driver.getBirthYear());
            statement.setString(4, driver.getGender().toString().toLowerCase());
            statement.setString(5, driver.getPhoneNumber());
            statement.setString(6, driver.getPersonalId());
            statement.setString(7, driver.getUserStatus().toString().toLowerCase());
            statement.setString(8, driver.getTypeOfVehicle().toString().toLowerCase());
//            statement.setInt(9, driver.getVehicleId());
            statement.setString(10, driver.getLocation());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                return rowsInserted;
        }
        return 0;
    }

    public List<String> findLocationOfWaitingStatus() throws SQLException {
        List<String> locations = new ArrayList<>();
        if (connection != null) {
            Statement statement = connection.createStatement();
            String status = "waiting";
            String sql = "SELECT location FROM drivers WHERE status=\"waiting\";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                locations.add(resultSet.getString("location"));
            }
        }
        return locations;
    }

    public void updateDriverLocation(Driver driver, String location) throws SQLException {
        if (connection != null) {
            String sql = "UPDATE drivers SET location = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, location);
            statement.setInt(2, driver.getId());
            statement.executeUpdate();
        }
    }
}
