package com.heroku.kubajabkodemoapp.persistence;

import com.heroku.kubajabkodemoapp.model.Comment;
import com.heroku.kubajabkodemoapp.model.User;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("commentService")
public class CommentService {

    DatabaseConnector connector;

    @Autowired
    UserService userService;

    public CommentService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Comment> getAll() {
        String hql = "FROM Comment";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Comment findById(long id) {
        String hql = "FROM Comment as comment where comment.id =" + id;
        Query query = connector.getSession().createQuery(hql);
        return (Comment) query.uniqueResult();
    }

    public void createComment(Comment comment) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(comment);
        transaction.commit();
    }

    public void removeComment(Comment comment) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(comment);
        transaction.commit();
    }

    public User addLike(String userLogin, Comment comment) {
        User user = userService.findByLogin(userLogin);
        comment.addLike(user);
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(comment);
        transaction.commit();
        return user;
    }

    public void removeLike(String userLogin, Comment comment) {
        User user = userService.findByLogin(userLogin);
        comment.removeLike(user);
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(comment);
        transaction.commit();
    }
}
