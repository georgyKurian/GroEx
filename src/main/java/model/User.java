package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c0687174
 */
public class User {

    private int user_id;
    private String email_id;
    private String password;
    private String first_name;
    private String last_name;

    public User() {
    }

    public User(int user_id, String email_id, String password, String first_name, String last_name) {
        this.user_id = user_id;
        this.email_id = email_id;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;

            if (user.user_id == this.user_id
                    && user.email_id == this.email_id
                    && user.first_name == this.first_name
                    && user.last_name == this.last_name) {
                return true;
            }
        }
        return false;
    }

}
