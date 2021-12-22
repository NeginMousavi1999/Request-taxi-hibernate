package dao;

import enumerations.UserStatus;
import models.members.Driver;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Negin Mousavi
 */
public class DriverDao extends AccessToDB {

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
        Driver driver;
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

    public List<Driver> showAllDrivers() {
        List<Driver> result;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Driver";
        System.out.println(hql);
        Query<Driver> query = session.createQuery(hql, Driver.class);
        result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    public void update(Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(driver);
        transaction.commit();
        session.close();
    }

    public int addNewDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(driver);
        transaction.commit();
        session.close();
        return 1;
    }

    public List<String> findLocationOfWaitingStatus() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "select d.location from Driver d where d.userStatus=:status";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("status", UserStatus.WAITING);
        List<String> list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
