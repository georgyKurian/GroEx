/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.User;

/**
 *
 * @author dilip
 */
@Named
@ApplicationScoped
public class UserController implements Serializable {

    private boolean IsLoggedIn;

    private List<User> userList;
    private User currentUser;

    public UserController() {
        IsLoggedIn = false;
        userList = new ArrayList<>();
        currentUser = new User();
    }

    public boolean isIsLoggedIn() {
        return IsLoggedIn;
    }

    public void setIsLoggedIn(boolean IsLoggedIn) {
        this.IsLoggedIn = IsLoggedIn;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addUser() {
        userList.add(currentUser);
        currentUser = new User();
    }

    public boolean deleteUserById(int id) {
        for (User u : userList) {
            if (u.getUser_id() == id) {
                userList.remove(u);
            }
            return true;
        }
        return false;
    }

    public boolean updateUserById() {
        for (User u : userList) {
            if (u.getUser_id() == currentUser.getUser_id()) {
                u.setUser_id(currentUser.getUser_id());
            }
            return true;
        }
        return false;
    }

    public User getUserById(int id) {
        for (User u : userList) {
            if (u.getUser_id() == id) {
                return u;
            }
        }
        return null;
    }

    public String doLogIn() {
        for (User u : userList) {
            if (u.getEmail_id().equals(currentUser.getEmail_id()) && u.getPassword().equals(currentUser.getPassword())) {
                IsLoggedIn = true;
                return "home";
            }
        }
        return "login";
    }

}
