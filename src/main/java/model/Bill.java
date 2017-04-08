package model;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0687174
 */
public class Bill {
    private int bill_id;
    private int group_id;
    private int user_id;
    private String bill_description;
    private double bill_amount;
    private Date bill_date;
    private String bill_type;

    public Bill() {
    }

    public Bill(int bill_id, int group_id, int user_id, String bill_des, double bill_amount, Date bill_date, String bill_type) {
        this.bill_id = bill_id;
        this.group_id = group_id;
        this.user_id = user_id;
        this.bill_description = bill_des;
        this.bill_amount = bill_amount;
        this.bill_date = bill_date;
        this.bill_type = bill_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    
    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getBill_description() {
        return bill_description;
    }

    public void setBill_description(String bill_description) {
        this.bill_description = bill_description;
    }

    public double getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(double bill_amount) {
        this.bill_amount = bill_amount;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }
  
}
