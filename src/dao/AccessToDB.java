package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Negin Mousavi
 */
public abstract class AccessToDB {
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}