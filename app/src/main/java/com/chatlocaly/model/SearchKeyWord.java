package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 4/5/18.
 */

public class SearchKeyWord {


    /**
     * data : {"search_key_list":[{"search_key_id":"0","search_key":"a"}],"message":"Search key list successfully got.","resultCode":"1"}
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
         * search_key_list : [{"search_key_id":"0","search_key":"a"}]
         * message : Search key list successfully got.
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("search_key_list")
        private List<SearchKeyListdata> searchKeyList;

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

        public List<SearchKeyListdata> getSearchKeyList() {
            return searchKeyList;
        }

        public void setSearchKeyList(List<SearchKeyListdata> searchKeyList) {
            this.searchKeyList = searchKeyList;
        }

        public static class SearchKeyListdata {
            /**
             * search_key_id : 0
             * search_key : a
             */

            @SerializedName("search_key_id")
            private String searchKeyId;
            @SerializedName("search_key")
            private String searchKey;

            public String getSearchKeyId() {
                return searchKeyId;
            }

            public void setSearchKeyId(String searchKeyId) {
                this.searchKeyId = searchKeyId;
            }

            public String getSearchKey() {
                return searchKey;
            }

            public void setSearchKey(String searchKey) {
                this.searchKey = searchKey;
            }
        }
    }
}
