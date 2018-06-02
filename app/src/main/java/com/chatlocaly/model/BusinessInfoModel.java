package com.chatlocaly.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 1/2/2018.
 */

public class BusinessInfoModel implements Serializable{


        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    public class BusinessDetail implements Serializable{

        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("home_services")
        @Expose
        private String homeServices;
        @SerializedName("services_km")
        @Expose
        private String servicesKm;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("locality_name")
        @Expose
        private String localityName;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("b_cm_code")
        @Expose
        private String bCmCode;
        @SerializedName("b_mobile_number")
        @Expose
        private String bMobileNumber;
        @SerializedName("business_banner")
        @Expose
        private String businessBanner;
        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("business_images")
        @Expose
        private List<BusinessImage> businessImages = null;
        @SerializedName("rating_count")
        @Expose
        private String ratingCount;
        @SerializedName("avg_rating")
        @Expose
        private String businessRating;
        @SerializedName("ratings")
        @Expose
        private Ratings ratings;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
        @SerializedName("services")
        @Expose
        private List<Object> services = null;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getLocalityName() {
            return localityName;
        }

        public void setLocalityName(String localityName) {
            this.localityName = localityName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getBCmCode() {
            return bCmCode;
        }

        public void setBCmCode(String bCmCode) {
            this.bCmCode = bCmCode;
        }

        public String getBMobileNumber() {
            return bMobileNumber;
        }

        public void setBMobileNumber(String bMobileNumber) {
            this.bMobileNumber = bMobileNumber;
        }

        public String getBusinessBanner() {
            return businessBanner;
        }

        public void setBusinessBanner(String businessBanner) {
            this.businessBanner = businessBanner;
        }

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public List<BusinessImage> getBusinessImages() {
            return businessImages;
        }

        public void setBusinessImages(List<BusinessImage> businessImages) {
            this.businessImages = businessImages;
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

        public Ratings getRatings() {
            return ratings;
        }

        public void setRatings(Ratings ratings) {
            this.ratings = ratings;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public List<Object> getServices() {
            return services;
        }

        public void setServices(List<Object> services) {
            this.services = services;
        }

    }
    public class BusinessImage implements Serializable{

        @SerializedName("bim_id")
        @Expose
        private String bimId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getBimId() {
            return bimId;
        }

        public void setBimId(String bimId) {
            this.bimId = bimId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class Data implements Serializable{

        @SerializedName("business_detail")
        @Expose
        private BusinessDetail businessDetail;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public BusinessDetail getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(BusinessDetail businessDetail) {
            this.businessDetail = businessDetail;
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

    public class Product implements Serializable{

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("product_status")
        @Expose
        private String product_status;
        @SerializedName("brand_name")
        @Expose
        private String brand_name;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("product_images")
        @Expose
        private List<ProductImage> productImages = null;


        public String getProductStatus()
        {
            return product_status;
        }
        public String getBrandName()
        {
            return brand_name;
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

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public List<ProductImage> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImage> productImages) {
            this.productImages = productImages;
        }

    }

    public class ProductImage implements Serializable{

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getPimId() {
            return pimId;
        }

        public void setPimId(String pimId) {
            this.pimId = pimId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class Ratings implements Serializable{

        @SerializedName("one_star")
        @Expose
        private Integer one_star;

        @SerializedName("two_star")
        @Expose
        private Integer two_star;
        @SerializedName("three_star")
        @Expose
        private Integer three_star;
        @SerializedName("four_star")
        @Expose
        private Integer four_star;
        @SerializedName("five_star")
        @Expose
        private Integer five_star;

        public Integer getOneStar() {
            return one_star;
        }
        public void setOneStar(Integer one_star)
        {
            this.one_star = one_star;
        }
        public Integer getTwostar() {
            return two_star;
        }
        public void setTwostar(Integer two_star)
        {
            this.two_star = two_star;
        }
        public Integer getThreestar() {
            return three_star;
        }
        public void setThreestar(Integer three_star)
        {
            this.three_star = three_star;
        }
        public Integer getFourstar() {
            return four_star;
        }
        public void setFourstar(Integer four_star)
        {
            this.four_star = four_star;
        }
        public Integer getFivestar() {
            return five_star;
        }
        public void setFivestar(Integer five_star)
        {
            this.five_star = five_star;
        }


    }
}
