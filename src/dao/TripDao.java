package dao;

import enumerations.TripStatus;
import models.trip.Trip;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Negin Mousavi
 */
public class TripDao extends AccessToDB {

    public List<Trip> showAllTrips() {
        Session session = sessionFactory.openSession();
        List<Trip> result;
        session.beginTransaction();
        String hql = "FROM Trip";
        System.out.println(hql);
        Query<Trip> query = session.createQuery(hql, Trip.class);
        result = query.list();
        assert result != null;
        try {
            return result;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public void addNewTrip(Trip trip) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(trip);
        transaction.commit();
        session.close();
    }

    public void updateStatus(Trip trip, TripStatus tripStatus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Trip loadTrip = session.load(Trip.class, trip.getId());
        loadTrip.setTripStatus(tripStatus);
        session.saveOrUpdate(loadTrip);
        transaction.commit();
        session.close();
    }

    public Trip findTripByDriverId(int driverId) {
        Session session = sessionFactory.openSession();
        List<Trip> result;
        session.beginTransaction();
        String hql = "from Trip t where t.driver.id=:id";
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