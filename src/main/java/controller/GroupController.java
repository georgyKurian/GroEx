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
import model.Group;

/**
 *
 * @author Georgi
 */
@Named
@SessionScoped
public class GroupController implements Serializable {

    private List<Group> groupList;
    private Group currentGroup;

    /**
     * It initializes currentGroup and loads data from database
     */
    public GroupController() {
        currentGroup = new Group();
        refreshFromDB();

    }

    /**
     * gets the GroupList
     *
     * @return groupList
     */
    public List<Group> getGroupList() {
        return groupList;
    }

    /**
     * Sets the groupList to current variable
     *
     * @param groupList
     */
    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    /**
     * Gets currentGroup
     *
     * @return currentGroup
     */
    public Group getCurrentGroup() {
        return currentGroup;
    }

    /**
     * Sets currentGroup to the variable
     *
     * @param currentGroup
     */
    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    /**
     * Adds the group with groupId in the database
     *
     * @param userId
     * @return groupSetting page
     */
    public String addGroup(int userId) {
        try {

            String sql = "INSERT INTO `groups` (`group_id`, `group_name`) VALUES (NULL, ?)";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, currentGroup.getGroup_name());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                currentGroup.setGroup_id(rs.getInt(1));
            }

            GroupMemberController gmController = new GroupMemberController();

            // Add code for setting group id
            gmController.currentGroupMember.setGroup_id(currentGroup.getGroup_id());
            gmController.currentGroupMember.setUser_id(userId);
            gmController.addGroupmember();
            groupList.add(currentGroup);

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "groupSettings?faces-redirect=true";
    }

    /**
     * gets the group with id
     *
     * @param groupId
     * @return group
     */
    public Group getGroupById(int groupId) {
        for (Group group : groupList) {
            if (group.getGroup_id() == groupId) {
                return group;
            }
        }
        return null;
    }

    /**
     * Creates a new group page
     *
     * @return createGroup page
     */
    public String addGroupPage() {
        this.currentGroup = new Group();
        return "createGroup?faces-redirect=true";
    }

    /**
     * Removes the group from the database
     *
     * @return boolean
     */
    public boolean deleteGroup() {

        try {

            String sql = "UPDATE `groups` SET `group_name` = ? WHERE `groups`.`group_id` = ? ";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, currentGroup.getGroup_name());
            pst.setInt(2, currentGroup.getGroup_id());
            pst.executeUpdate();

            for (Group group : groupList) {
                if (group.getGroup_id() == currentGroup.getGroup_id()) {
                    groupList.remove(group);
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentGroup = new Group();
        return false;
    }

    /**
     * Updates the group with the changes
     */
    public void editGroup() {
        try {

            String sql = "UPDATE `groups` SET `group_name` = ? WHERE `groups`.`group_id` = ? ";
            Connection conn = DBUtils.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, currentGroup.getGroup_name());
            pst.setInt(2, currentGroup.getGroup_id());
            pst.executeUpdate();

            for (Group group : groupList) {
                if (group.getGroup_id() == currentGroup.getGroup_id()) {
                    group.setGroup_name(currentGroup.getGroup_name());

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Assigning group to currentGroup
     *
     * @param group
     * @return groupHome
     */
    public String getGroupHomePage(Group group) {
        currentGroup = group;
        return "groupHome?faces-redirect=true";
    }

    /**
     * Selecting the particular group from the list
     */
    public void refreshFromDB() {
        groupList = new ArrayList<>();
        try {
            Group group;
            Connection con = DBUtils.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM groups");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
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
