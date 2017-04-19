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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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

    /**
     * It initializes currentGroupMember and loads data from database
     */
    public GroupMemberController() {
        currentGroupMember = new GroupMember();
        refreshFromDB();
    }

    /**
     * getting the list from db
     *
     * @return groupMemberList
     */
    public List<GroupMember> getGroupMemberList() {
        refreshFromDB();
        return groupMemberList;
    }

    /**
     * Setting the groupMemberList
     *
     * @param groupMemberList
     */
    public void setGroupMemberList(List<GroupMember> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }

    /**
     * get the currentGroupMember
     *
     * @return currentGroupMember
     */
    public GroupMember getCurrentGroupMember() {
        return currentGroupMember;
    }

    /**
     * Sets currentGroupMember to the variable
     *
     * @param currentGroupMember
     */
    public void setCurrentGroupMember(GroupMember currentGroupMember) {
        this.currentGroupMember = currentGroupMember;
    }

    /**
     * Adding a new GroupMember to db
     */
    public void addGroupmember() {
        try {

            String sql = "INSERT INTO `group_members` (`group_id`, `user_id`) VALUES (?, ?);";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, currentGroupMember.getGroup_id());
            pst.setInt(2, currentGroupMember.getUser_id());

            pst.executeUpdate();

            groupMemberList.add(currentGroupMember);
            currentGroupMember = new GroupMember();
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Checks whether the groupMember is present in the list or not.
     *
     * @param groupId
     * @param userId
     * @return boolean
     */
    public boolean hasGroupMemberInGroup(int groupId, int userId) {
        List<GroupMember> refinedGroupMembers = new ArrayList<>();
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId && gMember.getUser_id() == userId) {
                return true;
            }
        }
        return false;
    }

    /**
     * getting a groupMember with the id
     *
     * @param groupId
     * @return refinedGroupMembers
     */
    public List<GroupMember> getgroupMembersByGroupId(int groupId) {
        List<GroupMember> refinedGroupMembers = new ArrayList<>();
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId) {
                refinedGroupMembers.add(gMember);
            }
        }
        return refinedGroupMembers;
    }

    /**
     * deleting groupMember from the database
     *
     * @return boolean
     */
    public boolean deleteCurrentGroupmember() {
        try {

            String sql = "DELETE FROM `group_members` WHERE `group_members`.`group_id` = ? AND `group_members`.`user_id` = ?";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, currentGroupMember.getGroup_id());
            pst.setInt(2, currentGroupMember.getUser_id());
            pst.executeUpdate();

            for (GroupMember gMember : groupMemberList) {
                if (gMember.getGroup_id() == currentGroupMember.getGroup_id() & gMember.getUser_id() == currentGroupMember.getUser_id()) {
                    groupMemberList.remove(gMember);
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Deleting from the list
     *
     * @param groupId
     * @param userId
     * @return boolean
     */
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

    /**
     * Removing from the list using groupId
     *
     * @param groupId
     * @return boolean
     */
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

    /**
     * Removing from the list using grupId and userId
     *
     * @param groupId
     * @param userId
     * @return boolean
     */
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

    /**
     * delete from db
     *
     * @param groupmember
     * @return groupHome
     */
    public String delete(GroupMember groupmember) {
        currentGroupMember = groupmember;
        deleteCurrentGroupmember();
        return "groupHome?faces-redirect=true";
    }

    /**
     * getting the number of Members from the list
     *
     * @param groupId
     * @return count
     */
    public int getNumberOfMembersByGroupId(int groupId) {
        int count = 0;
        for (GroupMember gMember : groupMemberList) {
            if (gMember.getGroup_id() == groupId) {
                count++;
            }
        }
        return count;
    }

    /**
     * Updates the groupMemberList with the changes
     */
    public void refreshFromDB() {
        groupMemberList = new ArrayList<>();
        try {
            GroupMember groupMember;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM group_members");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
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
