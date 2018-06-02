package com.chatlocaly.model;

import java.util.List;

/**
 * Created by ashok on 21/2/18.
 */

public class GroupInfoModel  {

    public  String groupId,userId,groupName,admin,bussinessName,businessId;
    private List<String> groupMemberId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(List<String> groupMemberId) {
        this.groupMemberId = groupMemberId;
    }
}
