package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 30/3/18.
 */

public class BusinessMemberProfile {


    /**
     * data : {"b_user_id":"2","b_full_name":"shiv","b_mobile_number":"2222222222","designation":"0","b_profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/user-default.png","message":"Business user profile successfully get.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata {
        /**
         * b_user_id : 2
         * b_full_name : shiv
         * b_mobile_number : 2222222222
         * designation : 0
         * b_profile_image : http://192.168.0.60/chatlocaly/assets/uploads/user-default.png
         * message : Business user profile successfully get.
         * resultCode : 1
         */

        @SerializedName("b_user_id")
        private String bUserId;
        @SerializedName("b_full_name")
        private String bFullName;
        @SerializedName("b_mobile_number")
        private String bMobileNumber;
        @SerializedName("designation")
        private String designation;
        @SerializedName("b_profile_image")
        private String bProfileImage;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

        public String getBMobileNumber() {
            return bMobileNumber;
        }

        public void setBMobileNumber(String bMobileNumber) {
            this.bMobileNumber = bMobileNumber;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getBProfileImage() {
            return bProfileImage;
        }

        public void setBProfileImage(String bProfileImage) {
            this.bProfileImage = bProfileImage;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }
    }
}
