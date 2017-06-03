package com.rolrence.bulletscreen.DAO;

import com.rolrence.bulletscreen.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import java.util.List;

/**
 * Created by Rolrence on 2017/5/30.
 *
 */
@Repository
@Transactional
public class UserDAO extends GenericDAO {
    public User auth(User user) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", user.getName()));
        criteria.add(Restrictions.eq("password", user.getPassword()));
        return (User)criteria.uniqueResult();
    }

    public boolean add(User user) {
        User temp = this.findByName(user.getName());
        boolean status = temp == null;
        if (status) {
            Session session = sessionFactory.getCurrentSession();
            session.save(user);
        }
        return status;
    }

    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    public List<User> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }

    public User findById(int id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id));
        return (User)criteria.uniqueResult();
    }

    public User findByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", name));
        return (User)criteria.uniqueResult();
    }

    public User updateName(User user, String newName) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = this.findByName(user.getName());
        user1.setName(newName);
        session.update(user1);
        return user1;
    }

    public User updatePassword(User user, String newPassword) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = this.findByName(user.getName());
        user1.setPassword(newPassword);
        session.update(user1);
        return user1;
    }
}