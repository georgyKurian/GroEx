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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.Bill;
import model.GroupMember;

/**
 *
 * @author c0687174
 */
@Named
@ApplicationScoped
public class BillController implements Serializable {

    private List<Bill> billList;
    private Bill currentBill;

    public BillController() {
        currentBill = new Bill();
        refreshFromDB();
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public Bill getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(Bill currentBill) {
        this.currentBill = currentBill;
    }

    public void addBill() {
        billList.add(currentBill);
        currentBill = new Bill();
    }

    public boolean deleteBillWithBillId(int billid) {
        boolean deleted = false;
        for (Bill b : billList) {
            if (b.getBill_id() == billid) {
                billList.remove(b);
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean editBill() {
        for (Bill b : billList) {
            if (b.getBill_id() == currentBill.getBill_id()) {
                b.setBill_id(currentBill.getBill_id());
            }
            return true;
        }
        return false;
    }

    public void refreshFromDB() {
        billList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
            Bill bill;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM bill");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                bill = new Bill(
                        resultSet.getInt("bill_id"), 
                        resultSet.getInt("group_id"), 
                        resultSet.getInt("user_id"),
                        resultSet.getString("description"), 
                        resultSet.getDouble("amount"),
                        resultSet.getDate("date"),
                        resultSet.getString("type"));
                billList.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}