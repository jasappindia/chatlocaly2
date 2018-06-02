package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 12/3/18.
 */

public class UserProfileModel {


    /**
     * data : {"c_user_id":"1","c_full_name":"ashok","c_cm_code":"91","c_mobile_number":"1234567890","c_profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/20180208124651a6f7z0qnew.png","message":"User profile successfully updated.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata getUserProfile;

    public Datadata getGetUserProfile() {
        return getUserProfile;
    }

    public void setGetUserProfile(Datadata getUserProfile) {
        this.getUserProfile = getUserProfile;
    }

    public static class Datadata {
        /**
         * c_user_id : 1
         * c_full_name : ashok
         * c_cm_code : 91
         * c_mobile_number : 1234567890
         * c_profile_image : http://192.168.0.60/chatlocaly/assets/uploads/customers/20180208124651a6f7z0qnew.png
         * message : User profile successfully updated.
         * resultCode : 1
         */

        @SerializedName("c_user_id")
        private String cUserId;
        @SerializedName("c_full_name")
        private String cFullName;
        @SerializedName("c_cm_code")
        private String cCmCode;
        @SerializedName("c_mobile_number")
        private String cMobileNumber;
        @SerializedName("c_profile_image")
        private String cProfileImage;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public String getCUserId() {
            return cUserId;
        }

        public void setCUserId(String cUserId) {
            this.cUserId = cUserId;
        }

        public String getCFullName() {
            return cFullName;
        }

        public void setCFullName(String cFullName) {
            this.cFullName = cFullName;
        }

        public String getCCmCode() {
            return cCmCode;
        }

        public void setCCmCode(String cCmCode) {
            this.cCmCode = cCmCode;
        }

        public String getCMobileNumber() {
            return cMobileNumber;
        }

        public void setCMobileNumber(String cMobileNumber) {
            this.cMobileNumber = cMobileNumber;
        }

        public String getCProfileImage() {
            return cProfileImage;
        }

        public void setCProfileImage(String cProfileImage) {
            this.cProfileImage = cProfileImage;
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
