package com.heroku.kubajabkodemoapp.persistence;

import org.hibernate.Session;

public class DatabaseConnector {

    protected static com.heroku.kubajabkodemoapp.persistence.DatabaseConnector instance = null;

    public static com.heroku.kubajabkodemoapp.persistence.DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new com.heroku.kubajabkodemoapp.persistence.DatabaseConnector();
        }
        return instance;
    }

    private Session session;

    private DatabaseConnector() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void teardown() {
        session.close();
        HibernateUtil.shutdown();
        instance = null;
    }

    public Session getSession() {
        return session;
    }

}
