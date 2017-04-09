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
    
    public String submitNewBill(){
        this.currentBill = new Bill();
        addBill();
        return "groupHome?faces-redirect=true";
    }
    
    public void delete(Bill bill){
        this.currentBill = bill;
        deleteCurrentBill();
    }
    
    public String edit(Bill bill){
        this.currentBill = bill;
        return "editBill?faces-redirect=true";
    }
    
    public List<Bill> getBillListByGroupId(int groupId) {
        List<Bill> refinedBillList = new ArrayList<>();
        for (Bill b : billList) {
            if (b.getGroup_id() == groupId) {
                refinedBillList.add(b);
            }
        }
        return refinedBillList;
    }

    public boolean deleteCurrentBill() {
        for (Bill b : billList) {
            if (b.getBill_id() == this.currentBill.getBill_id()) {
                billList.remove(this.currentBill);
                this.currentBill = new Bill();
                return true;
            }
        }
        this.currentBill = new Bill();
        return false;
    }

    public String editBill() {
        for (Bill b : billList) {
            if (b.getBill_id() == currentBill.getBill_id()) {
                b.setBill_id(currentBill.getBill_id());
            }
            this.currentBill = new Bill();
            return "groupHome";
        }
        this.currentBill = new Bill();
        return "";
    }
    
    public double getTotalExpenseByGroupId(int groupId)
    {
        double total = 0.0;
         for (Bill b : billList) {
            if (b.getGroup_id()== groupId) {
                total += b.getBill_amount();
            }
        }
        return total;
    }
    
    public double getTotalExpenseByGroupIdAndUserId(int groupId,int userId)
    {
        double total = 0.0;
         for (Bill b : billList) {
            if (b.getGroup_id()== groupId & b.getUser_id() == userId) {
                total += b.getBill_amount();
            }
        }
        return total;
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
