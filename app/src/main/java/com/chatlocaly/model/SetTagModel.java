package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 23/3/18.
 */

public class SetTagModel {


    /**
     * data : {"message":"Already taged","resultCode":"0"}
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
         * message : Already taged
         * resultCode : 0
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

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
