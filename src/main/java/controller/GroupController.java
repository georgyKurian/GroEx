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
        
}
