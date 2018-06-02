package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anjani on 20/12/17.
 */

public class StoreListModel  implements Serializable  {


    /**
     * data : {"remaining_count":0,"business_list":[{"b_id":"1","business_name":"Mgm!hl","home_services":"NO","services_km":"0.00","business_logo":"20171228085548v4y8s7s3nk.png","rating_count":"1","business_rating":"5.0000 (1) ","last_conversion":"Anurag (owner): Apko..."}],"message":"Business list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData  implements Serializable {
        /**
         * remaining_count : 0
         * business_list : [{"b_id":"1","business_name":"Mgm!hl","home_services":"NO","services_km":"0.00","business_logo":"20171228085548v4y8s7s3nk.png","rating_count":"1","business_rating":"5.0000 (1) ","last_conversion":"Anurag (owner): Apko..."}]
         * message : Business list successfully got.
         * resultCode : 1
         */

        @SerializedName("remaining_count")
        private int remainingCount;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("business_list")
        private List<BusinessListData> businessList;

        public int getRemainingCount() {
            return remainingCount;
        }

        public void setRemainingCount(int remainingCount) {
            this.remainingCount = remainingCount;
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

        public List<BusinessListData> getBusinessList() {
            return businessList;
        }

        public void setBusinessList(List<BusinessListData> businessList) {
            this.businessList = businessList;
        }

        public static class BusinessListData implements Serializable {


            /**/
            /**
             * b_id : 1
             * business_name : Mgm!hl
             * home_services : NO
             * services_km : 0.00
             * business_logo : 20171228085548v4y8s7s3nk.png
             * rating_count : 1
             * business_rating : 5.0000 (1)
             * last_conversion : Anurag (owner): Apko...
             */



            @SerializedName("b_id")
            private String bId;
            @SerializedName("business_name")
            private String businessName;
            @SerializedName("home_services")
            private String homeServices;
            @SerializedName("services_km")
            private String servicesKm;
            @SerializedName("business_logo")
            private String businessLogo;
            @SerializedName("rating_count")
            private String ratingCount;
            @SerializedName("business_rating")
            private String businessRating;
            @SerializedName("last_conversion")
            private String lastConversion;

            @SerializedName("report_abuse_status")
            private String report_abuse_status;

            @SerializedName("blocked_by")
            private String blocked_by;

            @SerializedName("blocked_whom")
            private String blocked_whom;

            @SerializedName("block_side")
            private String block_side;

            @SerializedName("is_blocked")
            private boolean is_blocked;


            @SerializedName("tag_business")
            private String tag_business;

            @SerializedName("notification")
            private String notification;

            public String getTag_business() {
                return tag_business;
            }

            public void setTag_business(String tag_business) {
                this.tag_business = tag_business;
            }

            public String getNotification() {
                return notification;
            }

            public void setNotification(String notification) {
                this.notification = notification;
            }

            @SerializedName("chat_group_id")
            private String chat_group_id;

            public String getChat_group_id() {

                return chat_group_id;
            }

            public void setChat_group_id(String chat_group_id) {
                this.chat_group_id = chat_group_id;
            }

            public String getReport_abuse_status() {
                return report_abuse_status;
            }

            public void setReport_abuse_status(String report_abuse_status) {
                this.report_abuse_status = report_abuse_status;
            }


            public String getBlocked_by() {
                return blocked_by;
            }

            public void setBlocked_by(String blocked_by) {
                this.blocked_by = blocked_by;
            }

            public String getBlocked_whom() {
                return blocked_whom;
            }

            public void setBlocked_whom(String blocked_whom) {
                this.blocked_whom = blocked_whom;
            }

            public String getBlock_side() {
                return block_side;
            }

            public void setBlock_side(String block_side) {
                this.block_side = block_side;
            }

            public boolean isIs_blocked() {
                return is_blocked;
            }

            public void setIs_blocked(boolean is_blocked) {
                this.is_blocked = is_blocked;
            }

            public String getBId() {
                return bId;
            }

            public void setBId(String bId) {
                this.bId = bId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getHomeServices() {
                return homeServices;
            }

            public void setHomeServices(String homeServices) {
                this.homeServices = homeServices;
            }

            public String getServicesKm() {
                return servicesKm;
            }

            public void setServicesKm(String servicesKm) {
                this.servicesKm = servicesKm;
            }

            public String getBusinessLogo() {
                return businessLogo;
            }

            public void setBusinessLogo(String businessLogo) {
                this.businessLogo = businessLogo;
            }

            public String getRatingCount() {
                return ratingCount;
            }

            public void setRatingCount(String ratingCount) {
                this.ratingCount = ratingCount;
            }

            public String getBusinessRating() {
                return businessRating;
            }

            public void setBusinessRating(String businessRating) {
                this.businessRating = businessRating;
            }

            public String getLastConversion() {
                return lastConversion;
            }

            public void setLastConversion(String lastConversion) {
                this.lastConversion = lastConversion;
            }
        }
    }
}
