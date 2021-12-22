package dao;

import enumerations.Gender;
import enumerations.TypeOfVehicle;
import enumerations.UserStatus;
import models.members.Driver;
import models.members.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Negin Mousavi
 */
public class DriversDao extends AccessToDB {
    public DriversDao() throws ClassNotFoundException, SQLException {
    }

    @Override
    public void showAllObjectsInDB() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM drivers");
            while (resultSet.next()) {
                User driver = createUser(resultSet);
                System.out.println(driver.toString());
            }
        }
    }

    @Override
    public User createUser(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver(resultSet.getString(7), resultSet.getString(2),
                resultSet.getString(3), Gender.valueOf(resultSet.getString(5).toUpperCase()),
                resultSet.getString(6), resultSet.getInt(4),
                TypeOfVehicle.valueOf(resultSet.getString(9).toUpperCase()),
                resultSet.getInt(10), resultSet.getString(11));
        driver.setId(resultSet.getInt(1));
        driver.setUserStatus(UserStatus.valueOf(resultSet.getString(8).toUpperCase()));
        return driver;
    }

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
            statement.setInt(9, driver.getVehicleId());
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
