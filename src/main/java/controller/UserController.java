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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Bill;
import model.User;
import controller.MyMail;

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

    /**
     *It initializes user and loads data from database
     */
    public UserController() {
        searchPattern = "";
        IsLoggedIn = false;
        currentUser = new User();
        refreshFromDB();
    }

    /**
     * To check whether its logged in or not
     * @return IsLoggedIn
     */
    public boolean isIsLoggedIn() {
        return IsLoggedIn;
    }

    /**
     * To set the status as logged in
     * @param IsLoggedIn
     */
    public void setIsLoggedIn(boolean IsLoggedIn) {
        this.IsLoggedIn = IsLoggedIn;
    }

    /**
     * Gets the list of users
     * @return userList-list of user
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Sets the list to variable
     * @param userList-list of user
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     *Gets the current User
     * @return currentUser-the user who has signed in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currentUser to new variable
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Adds the user to the db
     * @return to index page
     */
    public String addUser() {
        MyMail newMail = new MyMail();
        currentUser.setPassword(DBUtils.hash(currentUser.getPassword()));
        try {

            String sql = "INSERT INTO `user` (`user_id`, `email_id`, `password`, `first_name`, `last_name`) VALUES (NULL, ?, ?, ?, ?);";
            Connection conn = DBUtils.getConnection();
            currentUser.setPassword(DBUtils.hash(currentUser.getPassword()));
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, currentUser.getEmail_id());
            pst.setString(2, currentUser.getPassword());
            pst.setString(3, currentUser.getFirst_name());
            pst.setString(4, currentUser.getLast_name());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                currentUser.setUser_id(rs.getInt(1));
                userList.add(currentUser);
                newMail.sendWelcomeMail(currentUser.getEmail_id(), currentUser.getFirst_name() + " " + currentUser.getLast_name());
                return "index?faces-redirect=true";

            }

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * delete the user with the id
     * @param id
     * @return boolean
     */
    public boolean deleteUserById(int id) {
        for (User u : userList) {
            if (u.getUser_id() == id) {
                userList.remove(u);
            }
            return true;
        }
        return false;
    }

    /**
     * Updates the user details in the db 
     * @return home page
     */
    public String updateUser() {

        try {

            String sql = "UPDATE `user` SET `email_id` = ?, `password` = ?, `first_name` = ?, `last_name` = ? WHERE `user`.`user_id` = ?";
            Connection conn = DBUtils.getConnection();
            currentUser.setPassword(DBUtils.hash(currentUser.getPassword()));
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, currentUser.getEmail_id());
            pst.setString(2, currentUser.getPassword());
            pst.setString(3, currentUser.getFirst_name());
            pst.setString(4, currentUser.getLast_name());
            pst.setInt(5, currentUser.getUser_id());

            pst.executeUpdate();

            for (User u : userList) {
                if (u.getUser_id() == currentUser.getUser_id()) {
                    u.setEmail_id(currentUser.getEmail_id());
                    u.setFirst_name(currentUser.getFirst_name());
                    u.setLast_name(currentUser.getLast_name());
                    u.setPassword(currentUser.getPassword());

                    return "home?faces-redirect=true";
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * get the user with the id
     * @param id
     * @return u- object of user
     */
    public User getUserById(int id) {
        for (User u : userList) {
            if (u.getUser_id() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * Verifies the username and the password and goes to the respective page
     * @return to home page or index page
     */
    public String doLogIn() {
        currentUser.setPassword(DBUtils.hash(currentUser.getPassword()));
        for (User u : userList) {
            if (u.getEmail_id().equals(currentUser.getEmail_id()) && u.getPassword().equals(currentUser.getPassword())) {
                IsLoggedIn = true;
                currentUser = u;
                return "home?faces-redirect=true";
            }
        }
        return "index?faces-redirect=true";
    }

    /**
     *gets the list and checks email id 
     * @return email
     */
    public List<String> getSuggestion() {
        List<String> email = new ArrayList<>();

        for (User u : userList) {
            email.add(u.getEmail_id());
        }
        return email;
    }

    /**
     * checks whether is login or not
     * @return index page
     */
    public String logout() {
        this.IsLoggedIn = false;
        this.currentUser = new User();
        return "index?faces-redirect=true";
    }

    /**
     * takes user  to sign up page
     * @return  to sign up page
     */
    public String signup() {
        this.currentUser = new User();
        return "signup?faces-redirect=true";
    }

    /**
     * Selecting the particular user from the list
     */
    public void refreshFromDB() {
        userList = new ArrayList<>();
        try {
            User user;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
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
