package dao;

import enumerations.PaymentMethod;
import enumerations.TripStatus;
import models.trip.Trip;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Negin Mousavi
 */
public class TripDao extends AccessToDB {
    public TripDao() throws ClassNotFoundException, SQLException {
    }


    public List<Trip> showAllTrips() throws SQLException {
/*        List<Trip> trips = new ArrayList<>();
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM trips");
            while (resultSet.next()) {
                Trip trip = createTrip(resultSet);
                trips.add(trip);
            }
        }
        return trips;*/

        Session session = sessionFactory.openSession();
        List<Trip> result;
        session.beginTransaction();
        String hql = "FROM Trip";
        System.out.println(hql);
        Query<Trip> query = session.createQuery(hql);
        result = query.list();
        assert result != null;
        try {
            return result;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

/*    public Trip createTrip(ResultSet resultSet) throws SQLException {
        return new Trip(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4),
                resultSet.getString(5), resultSet.getDouble(6),
                PaymentMethod.valueOf(resultSet.getString(7).toUpperCase()),
                TripStatus.valueOf(resultSet.getString(8).toUpperCase()));
    }*/

    public void addNewTrip(Trip trip) throws SQLException {
/*        if (connection != null) {
            String sql = "INSERT INTO `taxi-agency`.`trips` (`passenger_id_fk`, `origin`, `destination`, `cost`, `payment_method`, `driver_id_fk`," +
                    " `status`) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trip.getPassengerId());
            statement.setString(2, trip.getOrigin());
            statement.setString(3, trip.getDestination());
            statement.setInt(4, (int) trip.getCost());
            statement.setString(5, trip.getPaymentMethod().toString().toLowerCase());
            statement.setInt(6, trip.getDriverId());
            statement.setString(7, "on_trip");
            statement.executeUpdate();
        }*/
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(trip);
        transaction.commit();
        session.close();
    }

    public void updateStatus(Trip trip, TripStatus tripStatus) throws SQLException {
/*        if (connection != null) {
            String sql = "UPDATE trips SET status = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tripStatus.toString().toLowerCase());
            statement.setInt(2, trip.getId());
            statement.executeUpdate();
        }*/
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Trip loadTrip = session.load(Trip.class, trip.getId());
        loadTrip.setTripStatus(tripStatus);
        session.saveOrUpdate(loadTrip);
        transaction.commit();
        session.close();
    }

    public Trip findTripByDriverId(int driverId) throws SQLException {
/*        Trip trip = null;
        if (connection != null) {
            String sql = "SELECT * FROM trips where status = \"on_trip\" and driver_id_fk = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trip = createTrip(resultSet);
            }
        }
        return trip;*/
        Session session = sessionFactory.openSession();
        List<Trip> result;
        session.beginTransaction();
        String hql = "from Trip t where t.driverId=:id";
        Query<Trip> query = session.createQuery(hql, Trip.class);
        query.setParameter("id", driverId);
        result = query.list();
        assert result != null;
        try {
            return result.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}