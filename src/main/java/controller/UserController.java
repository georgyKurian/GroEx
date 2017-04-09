/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.User;

/**
 *
 * @author dilip
 */
@Named
@SessionScoped
public class UserController implements Serializable {
    
    private boolean IsLoggedIn;

    private List<User> userList;
    private User currentUser;
    private String searchPattern;

    public UserController() {
        searchPattern = "";
        IsLoggedIn = false;
        currentUser = new User();
        refreshFromDB();
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
                currentUser = u;
                return "home";
            }
        }
        return "login";
    }
    
    public List<String> getSuggestion(String str) {
        List<User> users = new ArrayList<>();
        List<String> susers = new ArrayList<>();
        String pattern;
        pattern = "(.*)"+str+"(.*)";
        
        for (User u : userList) {
            if (u.getEmail_id().matches(pattern)) {
                users.add(u);
                susers.add(u.getEmail_id());
            }
        }
        return susers;
    }
    
    public String logout(){
        this.IsLoggedIn = false;
        this.currentUser = new User();
        return "index";
    }
    

    public void refreshFromDB() {
        userList = new ArrayList<>();
        try {
            User user;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                user = new User(
                        resultSet.getInt("user_id"), 
                        resultSet.getString("email_id"), 
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"));
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
