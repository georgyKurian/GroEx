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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Group;

/**
 *
 * @author Georgi
 */
@Named
@SessionScoped
public class GroupController implements Serializable{
    private List<Group> groupList;
    private Group currentGroup;
            
    public GroupController() {
        currentGroup = new Group();
        refreshFromDB();
        
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }
    
    public void addGroup(){
        groupList.add(currentGroup);
        currentGroup = new Group();        
    }
    
    public Group getGroupById(int groupId){
        for(Group group:groupList){
            if(group.getGroup_id()== groupId){
                return group;
            }
        }
        return null;
    }
    
    public boolean deleteGroup(){
        for(Group group:groupList){
            if(group.getGroup_id()==currentGroup.getGroup_id()){
                groupList.remove(group);
                return true;
            }
        }
        currentGroup = new Group();
        return false;
    }
    
    public Group editGroup(){
        for(Group group:groupList){
            if(group.getGroup_id() == currentGroup.getGroup_id()){
                group.setGroup_name(currentGroup.getGroup_name());
                return group;
            }
        }
        currentGroup = new Group();
        return null;
    }
    
    public String getGroupHomePage(Group group)
    {
        currentGroup = group;
        return "groupHome";
    }
    
    public void refreshFromDB() {
        groupList = new ArrayList<>();
        try {
            Group group;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM groups");
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                group = new Group(
                        resultSet.getInt("group_id"),
                        resultSet.getString("group_name"));
                        
                groupList.add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
}