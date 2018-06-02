package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 23/4/18.
 */

public class CheckStatusModel {


    /**
     * data : {"c_user_id":"25","status":"APPROVED","login_key":"dkq5p76uzg82m1dayab812jau6fc6y","app_version":"1.0","message":"Successfully get business status.","resultCode":"1"}
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
         * c_user_id : 25
         * status : APPROVED
         * login_key : dkq5p76uzg82m1dayab812jau6fc6y
         * app_version : 1.0
         * message : Successfully get business status.
         * resultCode : 1
         */

        @SerializedName("c_user_id")
        private String cUserId;
        @SerializedName("status")
        private String status;
        @SerializedName("login_key")
        private String loginKey;
        @SerializedName("app_version")
        private String appVersion;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
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
