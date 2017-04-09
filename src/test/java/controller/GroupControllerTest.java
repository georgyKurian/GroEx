/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Group;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author c0687988
 */
public class GroupControllerTest {
    
    GroupController groupController;
    Group newGroup;
    Group oldGroup;

    public GroupControllerTest() {
        
    }
    
    @Before
    public void setUp() {
        groupController = new GroupController();
        ArrayList<Group> groupList = new ArrayList<>();
        groupList.add(new Group(1, "Group1"));
        groupList.add(new Group(2, "Group2"));
        groupList.add(new Group(3, "Group3"));
        
        newGroup = new Group(4, "Group4");
        oldGroup = new Group(2, "Group2");
        
        groupController.setGroupList(groupList);
        
    }

    /**
     * Test of deleteGroupWithGroupId method, of class GroupController.
     */
    @Test
    public void testDeleteGroup() {
        System.out.println("deleteGroupWithGroupId");
        
        groupController.setCurrentGroup(oldGroup);
        groupController.deleteGroup();
        Group result = groupController.getGroupById(oldGroup.getGroup_id());
        
        assertNull(result);
        
    }

    /**
     * Test of editGroupWithGroupId method, of class GroupController.
     */
    @Test
    public void testEditGroup() {
        System.out.println("editGroup");
        Group editGroup = new Group(2, "Changed");
        groupController.setCurrentGroup(editGroup);
        groupController.editGroup();
        Group result = groupController.getGroupById(editGroup.getGroup_id());
        
        assertEquals(editGroup, result);
        
    }

    /**
     * Test of addGroup method, of class GroupController.
     */
    @Test
    public void testAddGroup() {
        System.out.println("addGroup");
        groupController.setCurrentGroup(newGroup);
        groupController.addGroup(1);
        Group result = groupController.getGroupById(newGroup.getGroup_id());
        
        assertEquals(newGroup, result);
        
    }
    
}
