package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anjani on 19/1/18.
 */

public class LoginModel {
    /**
     * data : {"message":"OTP successfully verified.","resultCode":"1","user_detail":{"c_user_id":"23","c_mobile_number":"78474744444"}}
     */

    @SerializedName("data")
    private Logindata data;

    public Logindata getData() {
        return data;
    }

    public void setData(Logindata data) {
        this.data = data;
    }

    public static class Logindata {
        /**
         * message : OTP successfully verified.
         * resultCode : 1
         * user_detail : {"c_user_id":"23","c_mobile_number":"78474744444"}
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("user_detail")
        private UserDetaildata userDetail;

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

        public UserDetaildata getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetaildata userDetail) {
            this.userDetail = userDetail;
        }

        public static class UserDetaildata {
            /**
             * c_user_id : 23
             * c_mobile_number : 78474744444
             */

            @SerializedName("c_user_id")
            private String cUserId;
            @SerializedName("c_mobile_number")
            private String cMobileNumber;
            @SerializedName("login_key")
            private String login_key;

            public String getLogin_key() {
                return login_key;
            }

            public void setLogin_key(String login_key) {
                this.login_key = login_key;
            }

            public String getCUserId() {
                return cUserId;
            }

            public void setCUserId(String cUserId) {
                this.cUserId = cUserId;
            }

            public String getCMobileNumber() {
                return cMobileNumber;
            }

            public void setCMobileNumber(String cMobileNumber) {
                this.cMobileNumber = cMobileNumber;
            }
        }
    }
}
