/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    public GroupMemberController() {
        groupMemberList = new ArrayList<>();
        currentGroupMember = new GroupMember();
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
}
