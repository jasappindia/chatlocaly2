package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ashok on 20/4/18.
 */

public class RateModel implements Serializable {


    /**
     * business_rating : {"rating_flag":"NO","rating":"","comment":""}
     */
    /**
         * rating_flag : NO
         * rating :
         * comment :
         */

        @SerializedName("rating_flag")
        private String ratingFlag;
        @SerializedName("rating")
        private String rating;
        @SerializedName("comment")
        private String comment;
        @SerializedName("brt_id")
         private String brt_id="";

        public String getRatingFlag() {
            return ratingFlag;
        }

        public void setRatingFlag(String ratingFlag) {
            this.ratingFlag = ratingFlag;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    public String getBrt_id() {
        return brt_id;
    }

    public void setBrt_id(String brt_id) {
        this.brt_id = brt_id;
    }
}
