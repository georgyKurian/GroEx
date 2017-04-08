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
import model.GroupMember;

/**
 *
 * @author Georgi
 */
@Named
@SessionScoped
public class GroupMemberController implements Serializable {

    List<GroupMember> groupMemberList;
    GroupMember currentGroupMember;

    public GroupMemberController() {
        currentGroupMember = new GroupMember();
        refreshFromDB();
    }

    public List<GroupMember> getGroupMemberList() {
        return groupMemberList;
    }

    public void setGroupMemberList(List<GroupMember> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }

    public GroupMember getCurrentGroupMember() {
        return currentGroupMember;
    }

    public void setCurrentGroupMember(GroupMember currentGroupMember) {
        this.currentGroupMember = currentGroupMember;
    }

    public void addGroupmember() {
        groupMemberList.add(currentGroupMember);
        currentGroupMember = new GroupMember();
    }
    
    public List<GroupMember> getgroupMembersByGroupId(int groupId) {
        List<GroupMember> refinedGroupMembers = new ArrayList<>();
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId) {
                refinedGroupMembers.add(gMember);                
            }
        }
        return refinedGroupMembers;
    }
    
    public boolean deleteCurrentGroupmember() {
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == currentGroupMember.getGroup_id() & gMember.getUser_id() == currentGroupMember.getUser_id()) {
                groupMemberList.remove(gMember);
                return true;
            }
        }
        return false;
    }

    public boolean deleteWithGroupIdAndUserId(int groupId, int userId) {
        boolean deleted = false;
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId & gMember.getUser_id() == userId) {
                groupMemberList.remove(gMember);
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean deleteAllWithGroupId(int groupId) {
        boolean deleted = false;
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId) {
                groupMemberList.remove(gMember);
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean deleteAllWithUserId(int groupId, int userId) {
        boolean deleted = false;
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId & gMember.getUser_id() == userId) {
                groupMemberList.remove(gMember);
                deleted = true;
            }
        }
        return deleted;
    }
    
    public String delete(GroupMember groupmember){
        currentGroupMember = groupmember;
        deleteCurrentGroupmember();
        return "groupHome?faces-redirect=true";
    }
    
    public int getNumberOfMembersByGroupId(int groupId){
        int count = 0;
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId) {
                count++;
            }
        }
        return count;
    }
    
    public void refreshFromDB() {
        groupMemberList = new ArrayList<>();
        try {
            GroupMember groupMember;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM group_members");
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                groupMember = new GroupMember(
                        resultSet.getInt("group_id"),
                        resultSet.getInt("user_id"));
                groupMemberList.add(groupMember);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
