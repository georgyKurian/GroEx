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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import model.Bill;

/**
 *
 * @author c0687174
 */
@Named
@SessionScoped
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

    public void addNewBill() {
        try {

            String sql = "INSERT INTO bill (bill_id, group_id, user_id, description, amount, date, type) VALUES (NULL, ?, ?, ?, ?, ?, ?);";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, currentBill.getGroup_id());
            pst.setInt(2, currentBill.getUser_id());
            pst.setString(3, currentBill.getBill_description());
            pst.setDouble(4, currentBill.getBill_amount());
            pst.setDate(5, new java.sql.Date(currentBill.getBill_date().getTime()));
            pst.setString(6, currentBill.getBill_type());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                this.currentBill.setBill_id(rs.getInt(1));
                billList.add(currentBill);
                this.currentBill = new Bill();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String submitNewBill() {
        addNewBill();
        return "groupHome?faces-redirect=true";
    }

    public void delete(Bill bill) {
        this.currentBill = bill;
        deleteCurrentBill();
    }

    public String edit(Bill bill) {
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
        try {

            String sql = "DELETE FROM `bill` WHERE `bill`.`bill_id` = ?;";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, currentBill.getBill_id());
            pst.executeUpdate();

            for (Bill b : billList) {
                if (b.getBill_id() == this.currentBill.getBill_id()) {
                    billList.remove(this.currentBill);
                    this.currentBill = new Bill();
                    return true;
                }
            }
            this.currentBill = new Bill();

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public String editBill() {
        try {

            String sql = "UPDATE `bill` SET "
                    + "`description` = ?, "
                    + "`amount` = ?, `date` = ?, "
                    + "`type` = ? "
                    + "WHERE `bill`.`bill_id` = ?;";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, currentBill.getBill_description());
            pst.setDouble(2, currentBill.getBill_amount());
            pst.setDate(3, new java.sql.Date(currentBill.getBill_date().getTime()));
            pst.setString(4, currentBill.getBill_type());
            pst.setInt(5, currentBill.getBill_id());
            pst.executeUpdate();

            for (Bill b : billList) {
                if (b.getBill_id() == currentBill.getBill_id()) {
                    b.setBill_description(this.currentBill.getBill_description());
                    b.setBill_amount(this.currentBill.getBill_amount());
                    b.setBill_type(this.currentBill.getBill_type());
                    b.setBill_date(this.currentBill.getBill_date());
                }
                this.currentBill = new Bill();
                return "groupHome";
            }

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public double getTotalExpenseByGroupId(int groupId) {
        double total = 0.0;
        for (Bill b : billList) {
            if (b.getGroup_id() == groupId) {
                total += b.getBill_amount();
            }
        }
        return total;
    }

    public double getTotalExpenseByGroupIdAndUserId(int groupId, int userId) {
        double total = 0.0;
        for (Bill b : billList) {
            if (b.getGroup_id() == groupId & b.getUser_id() == userId) {
                total += b.getBill_amount();
            }
        }
        return total;
    }

    public String getMonthlyExpense(int groupId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        //format it to MMM-yyyy // January-2012
        String previousMonthYear;

        JsonArrayBuilder monthName = Json.createArrayBuilder();
        JsonArrayBuilder expense = Json.createArrayBuilder();
        
        double total;
        for (int i = 0; i < 5; i++) {
            previousMonthYear = sdf.format(cal.getTime());
            total = 0.0;
            for (Bill b : billList) {
                if (b.getGroup_id() == groupId & sdf.format(b.getBill_date()).equals(previousMonthYear)) {
                    total += b.getBill_amount();
                }
            }
            monthName.add(previousMonthYear);
            expense.add(total);
            cal.add(Calendar.MONTH, 1);
        }
        
        
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("x", monthName.build());
        json.add("y", expense.build());
        json.add("type", "bar");

        return ("[" + json.build() + "]");
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
            Logger.getLogger(UserController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
