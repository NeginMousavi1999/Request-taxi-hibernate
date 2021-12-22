package dao;

import models.vehicles.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

/**
 * @author Negin Mousavi
 */
public class AccessToVehicleDB extends AccessToDB {
    public AccessToVehicleDB() throws ClassNotFoundException, SQLException {
    }

    @Override
    public void showAllObjectsInDB() {

    }

    public void addNewVehicle(Vehicle vehicle) throws SQLException {
/*        if (connection != null) {
            String sql = "INSERT INTO `taxi-agency`.`vehicles` (`type`, `name`, `color`, `plaque`) VALUES (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vehicle.getTypeOfVehicle().toString().toLowerCase());
            statement.setString(2, vehicle.getName());
            statement.setString(3, vehicle.getColor());
            statement.setString(4, vehicle.getPlaque());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                return rowsInserted;
        }
        return 0;*/

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(vehicle);
        transaction.commit();
        session.close();
    }
}
