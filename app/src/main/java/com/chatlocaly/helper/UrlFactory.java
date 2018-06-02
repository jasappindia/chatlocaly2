package com.chatlocaly.helper;

/**
 * Created by ashok on 5/12/2015.
 */
public class UrlFactory {


   public static final String BASE_URL = "http://192.168.0.70/loyaltyapp/web_services/api/";
  //public static final String BASE_URL = "http://104.131.46.8/loyaltyapp/web_services/api/";
 //  public static final String BASE_URL = "http://184.154.53.181/loyaltyapp/web_services/api/";
  // public  static final String Image_Url="http://104.131.46.8/loyaltyapp/assets/uploads/";
  // public  static final String Image_Url="http://184.154.53.181/loyaltyapp/assets/uploads/";
   public  static final String Image_Url="http://192.168.0.70/loyaltyapp/assets/uploads/";
    public static final String PDF_Url="https://docs.google.com/viewer?url=";
    public static String getLoginUrl() {
        return BASE_URL + "login";
    }
    public static String getRegisterUrl() {
        return BASE_URL + "register";
    }
    public static String getForgotUrl() {
        return BASE_URL + "forgot_password";
    }
    public static String getEditProfileUrl() {
        return BASE_URL + "edit_profile";
    }
    public static String getUpdate_Gender_DobUrl() {
        return BASE_URL + "update_other_user_detail";
    }
    public static String getBusinessListUrl() {
        return BASE_URL + "business_list";
    }
    public static String getPostRatingUrl() {
        return BASE_URL + "add_business_rating";
    }
    public static String getCountryUrl() {
        return BASE_URL + "get_countries";
    }
    public static String getStateUrl() {
        return BASE_URL + "get_states";
    }
    public static String getCityUrl() {
        return BASE_URL + "get_cities";
    }
    public static String getBusinessDetailsUrl() {
        return BASE_URL + "business_detail";
    }
    public static String getEventsListUrl() {
        return BASE_URL + "event_list";
    }
    public static String getEventDetailsUrl() {
        return BASE_URL + "event_detail";
    }
    public static String getBusinessRatingUrl(){    return BASE_URL + "business_rating_list";}
    public static String getProfileUrl() {
        return BASE_URL + "get_profile";
    }
    public static String getSearchFilterUrl() {
        return BASE_URL + "get_search_filter";
    }
    public static String getFeedbackUrl() {
        return BASE_URL + "send_feedback";
    }
    public static String getDiscountListUrl() {
        return BASE_URL + "discount_list";
    }
    public static String getHomeDeliveryListUrl() {
        return BASE_URL + "hd_business_list";
    }
    public static String getChangePasswordUrl() {
        return BASE_URL + "change_password";
    }
    public static String getStampCardListUrl() {
        return BASE_URL + "user_stampcard_list";
    }
    public static String getStampCardDetailUrl() { return BASE_URL + "stampcard_detail";    }
    public static String getPointCardDetailUrl() { return BASE_URL + "point_detail";    }
    public static String getGenerateQr_stamp_Url() { return BASE_URL + "gen_qr_stampcard";    }
    public static String getGenerateQr_points_Url() { return BASE_URL + "gen_qr_point";    }
    public static String getHomeCategoriesUrl() { return BASE_URL + "restaurant_food_list";    }
    public static String getFoodItemUrl() { return BASE_URL + "res_food_cat_items";    }
    public static String getVerifyDiscountUrl(){return  BASE_URL+"verify_discount_code";}
    public static String getAddressListUrl(){return  BASE_URL+"shipping_address_listing";}
    public static String getUpdateAddressUrl(){return  BASE_URL+"update_shipping_address";}
    public static String getDeleteAddressUrl(){return  BASE_URL+"delete_shipping_address";}
    public static String getAddAddressUrl(){return  BASE_URL+"add_shipping_address";}
    public static String get_Generate_Order_Url(){return  BASE_URL+"customer_gen_order";}
    public static String get_Final_Order_Url(){return  BASE_URL+"update_order_status";}
    public static String get_Order_List_Url(){return  BASE_URL+"customer_order_history";}
    public static String get_cancel_Order_Url(){return  BASE_URL+"customer_order_cancel";}
    public static String get_generate_Order_qr_Url(){return  BASE_URL+"customer_gen_qr_order";}
    public static String get_Order_Token_Details_Url(){return  BASE_URL+"scan_order_qrcode";}
    public static String get_Cashier__Table_Order_Url(){return  BASE_URL+"customer_table_gen_order";}



}


