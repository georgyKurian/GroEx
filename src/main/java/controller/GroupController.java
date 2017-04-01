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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Group;
import model.GroupMem;

/**
 *
 * @author Georgi
 */
@Named
@ApplicationScoped
public class GroupController implements Serializable{
    private List<Group> groupList;
    private Group currentGroup;

    
    public GroupController() {
        groupList = new ArrayList<>();
        currentGroup = new Group();
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
        
}
