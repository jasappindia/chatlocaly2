package com.chatlocaly.model;

import com.google.gson.annotations.Expose;

/**
 * Created by windows on 12/8/2017.
 */

public class UpdateProfileModel {


    /**
     * data : {"c_user_id":1,"c_full_name":"shiv","c_profile_image":{"name":"banner.png","type":"image/png","tmp_name":"E:\\xampp\\tmp\\php98C5.tmp","error":0,"size":216028},"message":"User profile successfully updated.","resultCode":"1"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * c_user_id : 1
         * c_full_name : shiv
         * c_profile_image : {"name":"banner.png","type":"image/png","tmp_name":"E:\\xampp\\tmp\\php98C5.tmp","error":0,"size":216028}
         * message : User profile successfully updated.
         * resultCode : 1
         */

        private int c_user_id;
        private String c_full_name;
        @Expose
        private CProfileImageBean c_profile_image;
        private String message;
        private String resultCode;

        public int getC_user_id() {
            return c_user_id;
        }

        public void setC_user_id(int c_user_id) {
            this.c_user_id = c_user_id;
        }

        public String getC_full_name() {
            return c_full_name;
        }

        public void setC_full_name(String c_full_name) {
            this.c_full_name = c_full_name;
        }

        public CProfileImageBean getC_profile_image() {
            return c_profile_image;
        }

        public void setC_profile_image(CProfileImageBean c_profile_image) {
            this.c_profile_image = c_profile_image;
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

        public static class CProfileImageBean {
            /**
             * name : banner.png
             * type : image/png
             * tmp_name : E:\xampp\tmp\php98C5.tmp
             * error : 0
             * size : 216028
             */

            private String name;
            private String type;
            private String tmp_name;
            private int error;
            private int size;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTmp_name() {
                return tmp_name;
            }

            public void setTmp_name(String tmp_name) {
                this.tmp_name = tmp_name;
            }

            public int getError() {
                return error;
            }

            public void setError(int error) {
                this.error = error;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }
}