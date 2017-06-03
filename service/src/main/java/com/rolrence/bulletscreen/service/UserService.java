package com.rolrence.bulletscreen.service;

import com.rolrence.bulletscreen.DAO.GenericDAO;
import com.rolrence.bulletscreen.entity.User;
import com.rolrence.bulletscreen.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Rolrence on 2017/5/30.
 *
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private User buildUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return user;
    }

    public User auth(String name, String password) {
        User user = this.buildUser(name, password);
        return userDAO.auth(user);
    }

    public boolean add(String name, String password) {
        User user = this.buildUser(name, password);
        return this.userDAO.add(user);
    }

    public List<User> userList() {
        return this.userDAO.findAll();
    }

    public int userCount() {
        return this.userList().size();
    }

    public void updateName(User user, String newName) {
        this.userDAO.updateName(user, newName);
    }

    public void updatePassword(User user, String newPassword) {
        this.userDAO.updatePassword(user, newPassword);
    }
}