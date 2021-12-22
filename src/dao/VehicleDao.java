package dao;

import models.vehicles.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Negin Mousavi
 */
public class VehicleDao extends AccessToDB {
    public VehicleDao() throws ClassNotFoundException, SQLException {
    }

    public int getId(String plaque) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Vehicle v where v.plaque=:plaque";
        Query<Vehicle> query = session.createQuery(hql, Vehicle.class);
        query.setParameter("plaque", plaque);
        List<Vehicle> list = query.list();
        transaction.commit();
        session.close();
        return list.size() > 0 ? list.get(0).getId() : -1;
    }

    public boolean isObjectFound(String plaque) {
        return getId(plaque) > 0;
    }

    public void addNewVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(vehicle);
        transaction.commit();
        session.close();
    }
}
