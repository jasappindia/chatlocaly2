package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 25/5/18.
 */

public class ProductModel {

    /**
     * data : {"business_detail":[{"product_id":"100","b_id":"55","business_name":"ABC Pvt. Ltd.","product_original_name":"gdfgggf","description":"Dhjpohigigg","price":"150.00","discount":"25","sku":"","product_status":"1","status":"APPROVED","brand_name":"unbranded","brand_id":"0","product_name":"Unbranded - Gdfgggf","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315y7ay6cryyy.png","product_images":[{"pim_id":"0","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315y7ay6cryyy.png"},{"pim_id":"452","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315pr86rlx6ht.png"},{"pim_id":"453","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315h7mow2j9wj.png"},{"pim_id":"454","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/201805020703155lh59d7r2w.png"},{"pim_id":"455","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315gheeevq1ry.png"}]}],"message":"Product details successfully got","resultCode":"1"}
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
         * business_detail : [{"product_id":"100","b_id":"55","business_name":"ABC Pvt. Ltd.","product_original_name":"gdfgggf","description":"Dhjpohigigg","price":"150.00","discount":"25","sku":"","product_status":"1","status":"APPROVED","brand_name":"unbranded","brand_id":"0","product_name":"Unbranded - Gdfgggf","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315y7ay6cryyy.png","product_images":[{"pim_id":"0","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315y7ay6cryyy.png"},{"pim_id":"452","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315pr86rlx6ht.png"},{"pim_id":"453","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315h7mow2j9wj.png"},{"pim_id":"454","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/201805020703155lh59d7r2w.png"},{"pim_id":"455","product_image":"http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-55/20180502070315gheeevq1ry.png"}]}]
         * message : Product details successfully got
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("business_detail")
        private List<ProductListModel.productdata.ProductListdata> businessDetail;

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

        public List<ProductListModel.productdata.ProductListdata> getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(List<ProductListModel.productdata.ProductListdata> businessDetail) {
            this.businessDetail = businessDetail;
        }
    }
}

