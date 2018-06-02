package com.chatlocaly.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anjani on 17/1/18.
 */

public class ProductListModel implements Serializable {


    /**
     * data : {"remaining_count":0,"product_list":[{"product_id":"2","b_id":"1","product_name":"Test product","description":"Test product","price":"20.00","discount":"10","sku":"23sdasf","brand_id":"1","brand_name":"Tata","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png"},{"product_id":"1","b_id":"1","product_name":"Test product","description":"Test product","price":"20.00","discount":"10","sku":"23sdasf","brand_id":"1","brand_name":"Tata","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png"}],"message":"Product list successfully get.","resultCode":"1"}
     */

    @SerializedName("data")
    private productdata data;

    public productdata getData() {
        return data;
    }

    public void setData(productdata data) {
        this.data = data;
    }

    public static class productdata implements Serializable {
        /**
         * remaining_count : 0
         * product_list : [{"product_id":"2","b_id":"1","product_name":"Test product","description":"Test product","price":"20.00","discount":"10","sku":"23sdasf","brand_id":"1","brand_name":"Tata","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png"},{"product_id":"1","b_id":"1","product_name":"Test product","description":"Test product","price":"20.00","discount":"10","sku":"23sdasf","brand_id":"1","brand_name":"Tata","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png"}]
         * message : Product list successfully get.
         * resultCode : 1
         */

        @SerializedName("remaining_count")
        private int remainingCount;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("product_list")
        private List<ProductListdata> productList;

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

        public List<ProductListdata> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListdata> productList) {
            this.productList = productList;
        }

        public static class ProductListdata implements Serializable {
            /**
             * product_id : 2
             * b_id : 1
             * product_name : Test product
             * description : Test product
             * price : 20.00
             * discount : 10
             * sku : 23sdasf
             * brand_id : 1
             * brand_name : Tata
             * product_image : http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png
             */









            @SerializedName("product_id")
            private String productId;
            @SerializedName("b_id")
            private String bId;
            @SerializedName("product_name")
            private String productName;
            @SerializedName("description")
            private String description;
            @SerializedName("price")
            private String price;
            @SerializedName("discount")
            private String discount;
            @SerializedName("sku")
            private String sku;
            @SerializedName("brand_id")
            private String brandId;
            @SerializedName("brand_name")
            private String brandName;
            @SerializedName("product_image")
            private String productImage;

            @SerializedName("business_name")
            private String business_name;

            @SerializedName("product_images")
            @Expose
            private List<ProductImage> productImages = null;


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

            public String getReport_abuse_status() {
                return report_abuse_status;
            }

            public void setReport_abuse_status(String report_abuse_status) {
                this.report_abuse_status = report_abuse_status;
            }



            public List<ProductImage> getProductImages() {
                return productImages;
            }


            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public void setProductImages(List<ProductImage> productImages) {
                this.productImages = productImages;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getBId() {
                return bId;
            }

            public void setBId(String bId) {
                this.bId = bId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public static class ProductImage implements   Serializable {

                @SerializedName("pim_id")
                @Expose
                private String pimId;
                @SerializedName("product_image")
                @Expose
                private String productImage;

                public String getPimId() {
                    return pimId;
                }

                public void setPimId(String pimId) {
                    this.pimId = pimId;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

            }

        }
    }
}
