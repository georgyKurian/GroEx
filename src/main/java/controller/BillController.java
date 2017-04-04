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
    
    public BillController(){
        billList = new ArrayList<>();
        currentBill = new Bill();
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
    
    public void addBill(){
        billList.add(currentBill);
        currentBill = new Bill();
    }
    
    public boolean deleteBillWithBillId( int billid){
        boolean deleted = false;
        for( Bill b: billList){
            if (b.getBill_id() == billid){
                billList.remove(b);
                deleted = true;
            }
        }
        return deleted;
    }
    
     public boolean editBill() {
        for (Bill b: billList) {
            if (b.getBill_id() == currentBill.getBill_id()) {
                b.setBill_id(currentBill.getBill_id());
            }
            return true;
        }
        return false;
    }
}
