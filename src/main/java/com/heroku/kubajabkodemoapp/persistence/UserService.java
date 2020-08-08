package com.heroku.kubajabkodemoapp.persistence;

import com.heroku.kubajabkodemoapp.model.User;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("userService")
public class UserService {

    DatabaseConnector connector;

    public UserService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<User> getAll() {
        return connector.getSession().createCriteria(User.class).list();
    }

    public User findByLogin(String login) {
        return (User) connector.getSession().get(User.class, login);
    }

    public User add(User user) {
        user.setPassword(user.getPassword());
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(user);
        transaction.commit();
        return user;
    }

    public void update(User user) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(user);
        transaction.commit();
    }

    public void delete(User user) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(user);
        transaction.commit();
    }

}