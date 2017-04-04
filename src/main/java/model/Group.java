package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c0687174
 */
public class Group {

    private int group_id;
    private String group_name;

    public Group() {
    }

    public Group(int group_id, String group_name) {
        this.group_id = group_id;
        this.group_name = group_name;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            Group grp = (Group) obj;
            if (grp.group_id == this.group_id & (grp.group_name == null ? this.group_name == null : grp.group_name.equals(this.group_name))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.group_id + " " + group_name;
    }

}
