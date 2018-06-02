package com.chatlocaly.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anjani on 22/12/17.
 */

public class LocalityModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("locality_list")
        @Expose
        private List<LocalityList> localityList = null;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public List<LocalityList> getLocalityList() {
            return localityList;
        }

        public void setLocalityList(List<LocalityList> localityList) {
            this.localityList = localityList;
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

    public class LocalityList {

        @SerializedName("loc_id")
        @Expose
        private String locId;
        @SerializedName("name")
        @Expose
        private String name;

        public String getLocId() {
            return locId;
        }

        public void setLocId(String locId) {
            this.locId = locId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
