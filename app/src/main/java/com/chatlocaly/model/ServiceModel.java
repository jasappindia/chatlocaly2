package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anjani on 17/1/18.
 */

public class ServiceModel implements Serializable {

    /**
     * data : {"remaining_count":12,"service_list":[{"service_id":"11","service_name":"Designer","description":"this is AAKRITI-THE SHAPE","service_price":"15000.00","service_label":"let's be innovative","discount":"2","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png"},{"sim_id":"11","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725hd52bxmhiq.png"}],"service_brands":[{"brand_id":"22","brand_name":"AAKRITI"}]},{"service_id":"11","service_name":"Designer","description":"this is AAKRITI-THE SHAPE","service_price":"15000.00","service_label":"let's be innovative","discount":"2","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png"},{"sim_id":"11","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725hd52bxmhiq.png"}],"service_brands":[{"brand_id":"22","brand_name":"AAKRITI"}]},{"service_id":"10","service_name":"Carpenter","description":"this is famous painter","service_price":"10000.00","service_label":"let's desigm","discount":"5","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png"},{"sim_id":"10","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729xzwftnmtr2.png"}],"service_brands":[]},{"service_id":"10","service_name":"Carpenter","description":"this is famous painter","service_price":"10000.00","service_label":"let's desigm","discount":"5","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png"},{"sim_id":"10","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729xzwftnmtr2.png"}],"service_brands":[]},{"service_id":"9","service_name":"Car Dealers","description":"this is description","service_price":"500000.00","service_label":"let's drive","discount":"10","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png"},{"sim_id":"9","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849zff2cf0htp.png"}],"service_brands":[{"brand_id":"21","brand_name":"The Carss"}]},{"service_id":"9","service_name":"Car Dealers","description":"this is description","service_price":"500000.00","service_label":"let's drive","discount":"10","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png"},{"sim_id":"9","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849zff2cf0htp.png"}],"service_brands":[{"brand_id":"21","brand_name":"The Carss"}]},{"service_id":"8","service_name":"Car Dealer","description":"let's drive along the way","service_price":"80000.00","service_label":"let's drive","discount":"15","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png"},{"sim_id":"8","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/201804061014534aai0zhp3a.png"}],"service_brands":[]},{"service_id":"8","service_name":"Car Dealer","description":"let's drive along the way","service_price":"80000.00","service_label":"let's drive","discount":"15","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png"},{"sim_id":"8","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/201804061014534aai0zhp3a.png"}],"service_brands":[]},{"service_id":"7","service_name":"Automobiles","description":"this is not to be published","service_price":"79000.00","service_label":"let's move","discount":"250","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png"}],"service_brands":[]},{"service_id":"7","service_name":"Automobiles","description":"this is not to be published","service_price":"79000.00","service_label":"let's move","discount":"250","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png"}],"service_brands":[]}],"message":"Service list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata  implements Serializable {
        /**
         * remaining_count : 12
         * service_list : [{"service_id":"11","service_name":"Designer","description":"this is AAKRITI-THE SHAPE","service_price":"15000.00","service_label":"let's be innovative","discount":"2","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png"},{"sim_id":"11","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725hd52bxmhiq.png"}],"service_brands":[{"brand_id":"22","brand_name":"AAKRITI"}]},{"service_id":"11","service_name":"Designer","description":"this is AAKRITI-THE SHAPE","service_price":"15000.00","service_label":"let's be innovative","discount":"2","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png"},{"sim_id":"11","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725hd52bxmhiq.png"}],"service_brands":[{"brand_id":"22","brand_name":"AAKRITI"}]},{"service_id":"10","service_name":"Carpenter","description":"this is famous painter","service_price":"10000.00","service_label":"let's desigm","discount":"5","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png"},{"sim_id":"10","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729xzwftnmtr2.png"}],"service_brands":[]},{"service_id":"10","service_name":"Carpenter","description":"this is famous painter","service_price":"10000.00","service_label":"let's desigm","discount":"5","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729i647lkcaqp.png"},{"sim_id":"10","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406102729xzwftnmtr2.png"}],"service_brands":[]},{"service_id":"9","service_name":"Car Dealers","description":"this is description","service_price":"500000.00","service_label":"let's drive","discount":"10","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png"},{"sim_id":"9","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849zff2cf0htp.png"}],"service_brands":[{"brand_id":"21","brand_name":"The Carss"}]},{"service_id":"9","service_name":"Car Dealers","description":"this is description","service_price":"500000.00","service_label":"let's drive","discount":"10","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849c5yutpmyvt.png"},{"sim_id":"9","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101849zff2cf0htp.png"}],"service_brands":[{"brand_id":"21","brand_name":"The Carss"}]},{"service_id":"8","service_name":"Car Dealer","description":"let's drive along the way","service_price":"80000.00","service_label":"let's drive","discount":"15","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png"},{"sim_id":"8","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/201804061014534aai0zhp3a.png"}],"service_brands":[]},{"service_id":"8","service_name":"Car Dealer","description":"let's drive along the way","service_price":"80000.00","service_label":"let's drive","discount":"15","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406101453kpkytlh7gi.png"},{"sim_id":"8","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/201804061014534aai0zhp3a.png"}],"service_brands":[]},{"service_id":"7","service_name":"Automobiles","description":"this is not to be published","service_price":"79000.00","service_label":"let's move","discount":"250","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png"}],"service_brands":[]},{"service_id":"7","service_name":"Automobiles","description":"this is not to be published","service_price":"79000.00","service_label":"let's move","discount":"250","b_id":"2","business_name":"sniper stop","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-service-image.png"}],"service_brands":[]}]
         * message : Service list successfully got.
         * resultCode : 1
         */

        @SerializedName("remaining_count")
        private int remainingCount;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("service_list")
        private List<ServiceListdata> serviceList;

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

        public List<ServiceListdata> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceListdata> serviceList) {
            this.serviceList = serviceList;
        }

        public static class ServiceListdata  implements Serializable {
            /**
             * service_id : 11
             * service_name : Designer
             * description : this is AAKRITI-THE SHAPE
             * service_price : 15000.00
             * service_label : let's be innovative
             * discount : 2
             * b_id : 2
             * "report_abuse_status": "0"
             * business_name : sniper stop
             * service_image : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png
             * service_images : [{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png"},{"sim_id":"11","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725hd52bxmhiq.png"}]
             * service_brands : [{"brand_id":"22","brand_name":"AAKRITI"}]
             */

            @SerializedName("service_id")
            private String serviceId;
            @SerializedName("service_name")
            private String serviceName;
            @SerializedName("description")
            private String description;
            @SerializedName("service_price")
            private String servicePrice;
            @SerializedName("service_label")
            private String serviceLabel;
            @SerializedName("discount")
            private String discount;
            @SerializedName("b_id")
            private String bId;
            @SerializedName("business_name")
            private String businessName;
            @SerializedName("service_image")
            private String serviceImage;


            @SerializedName("service_images")
            private List<ServiceImagesdata> serviceImages;
            @SerializedName("service_brands")
            private List<ServiceBrandsdata> serviceBrands;
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

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getServicePrice() {
                return servicePrice;
            }

            public void setServicePrice(String servicePrice) {
                this.servicePrice = servicePrice;
            }

            public String getServiceLabel() {
                return serviceLabel;
            }

            public void setServiceLabel(String serviceLabel) {
                this.serviceLabel = serviceLabel;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
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

            public String getServiceImage() {
                return serviceImage;
            }

            public void setServiceImage(String serviceImage) {
                this.serviceImage = serviceImage;
            }

            public List<ServiceImagesdata> getServiceImages() {
                return serviceImages;
            }

            public void setServiceImages(List<ServiceImagesdata> serviceImages) {
                this.serviceImages = serviceImages;
            }

            public List<ServiceBrandsdata> getServiceBrands() {
                return serviceBrands;
            }

            public void setServiceBrands(List<ServiceBrandsdata> serviceBrands) {
                this.serviceBrands = serviceBrands;
            }

            public static class ServiceImagesdata  implements Serializable{
                /**
                 * sim_id : 0
                 * image_name : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-2/20180406112725qmd0sahvmh.png
                 */

                @SerializedName("sim_id")
                private String simId;
                @SerializedName("image_name")
                private String imageName;

                public String getSimId() {
                    return simId;
                }

                public void setSimId(String simId) {
                    this.simId = simId;
                }

                public String getImageName() {
                    return imageName;
                }

                public void setImageName(String imageName) {
                    this.imageName = imageName;
                }
            }

            public static class ServiceBrandsdata  implements Serializable{
                /**
                 * brand_id : 22
                 * brand_name : AAKRITI
                 */

                @SerializedName("brand_id")
                private String brandId;
                @SerializedName("brand_name")
                private String brandName;

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
            }
        }
    }
}
