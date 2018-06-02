
package com.chatlocaly.retrofit;


import com.chatlocaly.model.BlockResultModel;
import com.chatlocaly.model.BusinessMemberProfile;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.model.CheckStatusModel;
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.InvoiceOrderListModel;
import com.chatlocaly.model.LocalityListModel;
import com.chatlocaly.model.LoginModel;
import com.chatlocaly.model.NotificationResultModel;
import com.chatlocaly.model.Order_Details;
import com.chatlocaly.model.ProductListModel;
import com.chatlocaly.model.ProductModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.model.SearchKeyWord;
import com.chatlocaly.model.ServiceDetailsModel;
import com.chatlocaly.model.ServiceModel;
import com.chatlocaly.model.SetTagModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.model.ThreadModel;
import com.chatlocaly.model.UpdateProfileModel;
import com.chatlocaly.model.UserProfileModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("customer_register")
    Call<ResultModel> getLoginData(@FieldMap  HashMap<String, String> login);
    @FormUrlEncoded
    @POST("otp_verify")
    Call<LoginModel> getOtpVerification(@FieldMap HashMap<String, String> login);
    @FormUrlEncoded
    @POST("c_profile_update")
    Call<ResultModel> getUserProfile(@FieldMap HashMap<String, String> login);
    @FormUrlEncoded
    @POST("category_list")
    Call<CategoryListModel> getCategoryList(@FieldMap HashMap<String, String> login);
    @FormUrlEncoded
    @POST("business_list")
    Call<StoreListModel> getBusinessList(@FieldMap HashMap<String, String> login);
    //http://192.168.0.60/chatlocaly/customer_api/locality_list?encrypt_key=gdfgdfd&c_user_id=1&city_id=1
    @FormUrlEncoded
    @POST("locality_list")
    Call<LocalityListModel> getLocalityList(@FieldMap HashMap<String, String> locality);

   //city list
    @FormUrlEncoded
    @POST("city_list")
    Call<CityListModel> getCityList(@FieldMap HashMap<String, String> citylist);
    //filter list
    //http://192.168.0.60/chatlocaly/customer_api/fliter_parameters?encrypt_key=1&c_user_id=dfgd&city_id=1
    @FormUrlEncoded
    @POST("fliter_parameters")
    Call<FilterModel> getfilterparamitter(@FieldMap HashMap<String, String> citylist);



    @FormUrlEncoded
    @POST("product_list")
    Call<ProductListModel> getProductList(@FieldMap HashMap<String, String> productlist);

    @FormUrlEncoded
    @POST("service_list")
    Call<ServiceModel> getServiceList(@FieldMap HashMap<String, String> productlist);


    @Multipart
    @POST("c_profile_update")
//    Call<UpdateProfileModel> updateProfile(@PartMap HashMap<String, RequestBody> param);

    Call<UpdateProfileModel> updateProfile(@PartMap HashMap<String, RequestBody> param, @Part MultipartBody.Part image);


    @Multipart
    @POST("c_profile_update")
    Call<ResultModel> uploadFile(@Part MultipartBody.Part file, @Part("c_profile_image") RequestBody name, @PartMap  HashMap<String, RequestBody> request);


    @FormUrlEncoded
    @POST("get_chat_group")
    Call<BussinessGroupCreateModel> getGroupListInfo(@FieldMap HashMap<String, String> productlist);


    @FormUrlEncoded
    @POST("get_product_details")
    Call<ProductModel> getProductDetails(@FieldMap HashMap<String, String> productlist);

    @FormUrlEncoded
    @POST("get_service_details")
    Call<ServiceDetailsModel> getServiceDetails(@FieldMap HashMap<String, String> productlist);


    @FormUrlEncoded
    @POST("get_customer_profile")
    Call<UserProfileModel> getUserProfileById(@FieldMap HashMap<String, String> profileparam);

    @FormUrlEncoded
    @POST("get_threads_list")
    Call<ThreadModel> getThreadList(@FieldMap HashMap<String, String> thread);

//    http://192.168.0.60/chatlocaly/customer_api/business_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_id=1

    @FormUrlEncoded
    @POST("business_tag")
    Call<SetTagModel> setBusinessTag(@FieldMap HashMap<String, String> thread);

    @FormUrlEncoded
    @POST("get_business_user_profile")
    Call<BusinessMemberProfile> getBusinessProfile(@FieldMap HashMap<String, String> profileparam);

    @FormUrlEncoded
    @POST("block_chat_group")
    Call<BlockResultModel> setBusinessBlock(@FieldMap HashMap<String, String> thread);

    @FormUrlEncoded
    @POST("customer_notification")
    Call<NotificationResultModel> setNotification(@FieldMap HashMap<String, String> thread);


    @FormUrlEncoded
    @POST("add_report_abuse")
    Call<ResultModel> sendReportToabuse(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("update_chat_group_detail")
    Call<ResultModel> sendUpdateChatgroupDetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("Get_business_detail")
    Call<BusinessProfileInfoModel> getBusinessDetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("order_list")
    Call<InvoiceOrderListModel> getInvoiceOderLst(@FieldMap HashMap<String, String> param);
   // ?encrypt_key=sdfsd&c_user_id=25&order_id=1

    @FormUrlEncoded
    @POST("get_order_detail")
    Call<Order_Details> getOrderDetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("add_business_rating")
    Call<ResultModel> sendBusinessRating(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("update_order_confirmation_code")
    Call<ResultModel> sendConfirmationCode(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("edit_business_rating")
    Call<ResultModel> updateBusinessRating(@FieldMap HashMap<String, String> param);
    @FormUrlEncoded
    @POST("check_customer_status")
    Call<CheckStatusModel> checkStatus(@ FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("get_search_key_list")
    Call<SearchKeyWord> getKeyWordList(@FieldMap HashMap<String, String> productlist);

       @GET
    Call<ResponseBody> downloadFile(@Url String url);

    //http://192.168.0.60/chatlocaly/customer_api/update_chat_group_detail?encrypt_key=fgdfg&
    // c_user_id=1
    // &chat_group_id=54&applozic_group_id=8024675

    //192.168.0.60/chatlocaly/customer_api/get_business_user_profile?encrypt_key=fsdf&b_user_id=2&c_user_id=1


    // http://192.168.0.60/chatlocaly/customer_api/get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4



    /**//*

    Call<RegistrationModel> getRegistration(@Body RequestBody data);

    @GET("customer/question")
    Call<QuestionModel> getQuestion();

    @GET("categories")
    Call<ExpandableListModel> getExpandListcategories();

    @GET
    Call<ProductPartListModel> getProductspartList(@Url String id);

    @GET
    Call<ProductSearchItemListModel> getProductSearchItemList(@Url String productname);

    @GET("vehicle")
    Call<VehicleMakeListModel> getVehicleMakeList();

    @GET
    Call<VehicleBrandModel> getVehicleModelList(@Url String id);


    @GET
    Call<VehicleYearModel> getVehicleYearList(@Url String id);

    @GET
    Call<ProductSearchItemListModel> getVehicleSearchProductList(@Url String id);

    @GET
    Call<ProductCompleteInfoModel> getVehicleProductInfo(@Url String id);

    @POST("cart/add")
    Call<CartResponce> getCart(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body ProductList productList);

    @GET("cart")
    Call<AddToCartProductListModel> getCartList(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);


    @POST("cart/update")
    Call<CartResponce> getCartUpdate(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body ProductList productList);

    @GET(Constants.ADDRESS_LIST_API)
    Call<AddressListModel> getAddressList(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);

    @GET
    Call<CheckOutModel> getCheckOutListProduct(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Url String url);

    @GET("zones")
    Call<ZonesModel> getZone(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);


    @POST("customer/address/add")
    Call<RegistrationModel> sendAddress(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody data);

    @POST("customer/address/delete/{ADDRESS_ID}")
    Call<RegistrationModel> getDeleteAddress(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("ADDRESS_ID") String addressId);

    @POST("customer/address/edit/{ADDRESS_ID}")
    Call<RegistrationModel> getUpdateAddress(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("ADDRESS_ID") String addressId, @Body RequestBody data);


    @GET(Constants.SHIPPING_METHOD_ADDRESS + "{DIFFERENT_SHIPPING_ADDRESS}")
    Call<ShippingMethodModel> getshippingMethod(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("DIFFERENT_SHIPPING_ADDRESS") String DIFFERENT_SHIPPING_ADDRESS);


    @GET(Constants.ADDRESS_PAYMENT + "{DIFFERENT_SHIPPING_ADDRESS}")
    Call<PayMentMethodModel> getPayMentMethod(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("DIFFERENT_SHIPPING_ADDRESS") String DIFFERENT_SHIPPING_ADDRESS);


    @GET(Constants.CREDIT_CARD_LIST)
    Call<CreditCartListModel> getCreditCartList(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);

    @POST(Constants.ADD_CREDIT_CARD)
    Call<RegistrationModel> getAddCreditCart(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody requestBody);

    @GET(Constants.GET_STORE_CREDITE)
    Call<StoreCreditModel> getStoreCredit(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);

    @POST(Constants.CART_CHECKOUT)
    Call<CheckOutModel> getCheckoutCard(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody requestBody);

    @POST(Constants.CART_INVOICE)
    Call<InvoiceModel> getInvoice(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody requestBody);


    @POST(Constants.EditInfo)
    Call<RegistrationModel> getUpdateUserInfo(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody data);


    @GET(Constants.CUSTOMER_INFO)
    Call<CustomerInfoModle> getCustomerInfo(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);

    @POST(Constants.ORDER_TRACKING_SMS)
    Call<RegistrationModel> getSmsTracking(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Body RequestBody requestBody);

    @GET(Constants.ORDER_HISTORY)
    Call<OrderHistoryList> getOrderhistory(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key);


    @GET(Constants.ORDER_HISTORY_COMPLETE + "{ORDER-ID}")
    Call<OrderdetaileModel> getOrderHistoryDetails(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("ORDER-ID") String ORDER_ID);

    @POST(Constants.CREDIT_CARD_DEFAULT + "{CreditCardID}")
    Call<RegistrationModel> getDefaultCreditCard(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("CreditCardID") String ORDER_ID);

    @POST(Constants.CREDIT_CARD_DELETE + "{CreditCardID}")
    Call<RegistrationModel> getDefaultDeleteCard(@Header("Authorization") String Authorization, @Header("Time") String time, @Header("Key") String Key, @Path("CreditCardID") String ORDER_ID);


    */
/*@GET("brand_list")
    Call<BrandData> getCurrentPage(@Query("current_page") String current_page);

    @GET("brand_main_cat_list")
    Call<MainCatData> getCatMainList(@Query("current_page") String current_page);

    @GET("brand_cat_list")
    Call<CatData> getCatList(@Query("main_cat_id") String main_cat_id, @Query("current_page") String current_page);

    @GET("brand_sub_cat_list")
    Call<SubCatData> getSubCatList(@Query("cat_id") String main_cat_id, @Query("current_page") String current_page);

    *//*
*/
/*@GET("sub_cat_item_list")
    Call<ItemAddData> getItemList(@Query("sub_cat_id") String main_cat_id, @Query("current_page") String current_page);*//*
*/
/*

    *//*
*/
/*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*//*
*/
/*

    @GET("shop_list")
    Call<StoreDataResponce> getStoreData(@Query("current_page") String current_page);

    @GET("shop_main_cat_list")
    Call<StoreMainCatResponce> getStoreMainCatData(@Query("shop_id") String shop_id, @Query("current_page") String current_page);

    @GET("shop_cat_list")
    Call<StoreCatResponce> getStoreCatData(@Query("main_cat_id") String shop_id, @Query("current_page") String current_page);

    @GET("shop_sub_cat_list")
    Call<StoreSubCatResponce> getSubCatData(@Query("cat_id") String cat_id, @Query("current_page") String current_page
            , @Query("user_id") String user_id);

    @GET("sub_cat_item_list")
    Call<StoreItemResponce> getCatItemData(@Query("sub_cat_id") String sub_cat_id, @Query("current_page") String current_page,
                                           @Query("skip") String skip, @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("compare_items")
    Call<CompareItems> getComapreItems(@FieldMap Map<String, String> param);

    *//*
*/
/*@GET("search_items")
    Call<ItemAddData> getSearchItems(@Query("search_key") String search_key,@Query("limit") String limit);*//*
*/
/*

    @GET("factual_store_list")
    Call<StorePlacesModel> getSearchStore(@Query("shop_id") String shop_id, @Query("current_page") String current_page);

    // New API
    @GET("sub_cat_item_list")
    Call<StorePlacesModel> getStoreList(@Query("sub_cat_id") String sub_cat_id, @Query("skip") String skip,
                                        @Query("current_page") String current_page);

    @GET("lat_long")
    Call<MapDataModel> getLatLong(@Query("latitude") Double latitude, @Query("longitude") Double longitude);


    @GET("login")
    Call<LoginModel> getLoginData(@Query("email") String email, @Query("password") String password,
                                  @Query("device_id") String device_id, @Query("device_type") String device_type);


    @GET("register")
    Call<RegisterModelData> getRegisterData(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("email") String email,
            @Query("password") String password,
            @Query("dob") String dob,
            @Query("device_id") String device_id,
            @Query("device_type") String device_type
    );

    @GET("update_profile")
    Call<RegisterModelData> updateProfile(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("email") String email,
            @Query("dob") String dob,
            @Query("device_id") String device_id,
            @Query("device_type") String device_type
    );

    @GET("forgot_verify_code")
    Call<ForgotPasswordModel> forgotPassword(
            @Query("email") String email);

    @GET("change_old_password")
    Call<ForgotPasswordModel> changePassword(
            @Query("old_password") String old_password,
            @Query("new_password") String new_password,
            @Query("user_id") String user_id);*//*


 */
/*   @GET("login")
    Call<LoginModel> getLoginData(@Query("email") String email, @Query("password") String password,
                                  @Query("encrypt_key") String encrypt_key, @Query("device_id") String device_id,
                                  @Query("device_type") String device_type);

    *//*
*/
/* --------------- Add Client ---------------*//*
*/
/*
    @GET("get_countries")
    Call<CountryModel> getCountryData(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                      @Query("login_key") String login_key);

    @GET("get_states")
    Call<StateModel> getStateData(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                  @Query("country_id") String country_id, @Query("login_key") String login_key);

    @GET("get_cities")
    Call<CityModel> getCityData(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                @Query("state_id") String state_id, @Query("login_key") String login_key);

    @GET("get_client_type")
    Call<ClientTypeModel> getClientType(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                        @Query("login_key") String login_key);

    @GET("get_source_lead")
    Call<SourceLeadModel> getSourceLead(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                        @Query("login_key") String login_key);

    @FormUrlEncoded
    @POST("add_client")
    Call<CommonModel> addClient(@FieldMap HashMap<String, String> uploadData);

    *//*
*/
/* --------------- Add Client ---------------*//*
*/
/*

    @GET("get_client_list")
    Call<ClientModel> getClient(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                @Query("login_key") String login_key);


    *//*
*/
/* --------------- Daily Report Start---------------*//*
*/
/*
    @FormUrlEncoded
    @FormUrlEncoded
    @POST("add_report")
    Call<CommonModel> addReport(@FieldMap HashMap<String, String> addReport);

    @GET("report_list")
    Call<DailyReportModel> getReports(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                      @Query("login_key") String login_key);

    @GET("delete_report")
    Call<CommonModel> deleteReport(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                   @Query("r_id") String r_id, @Query("login_key") String login_key);

    @FormUrlEncoded
    @POST("edit_report")
    Call<CommonModel> editReport(@FieldMap HashMap<String, String> addReport);



*//*
*/
/* --------------- Daily Report End---------------*//*
*/
/*

    @FormUrlEncoded
    @POST("update_client")
    Call<CommonModel> updateClient(@FieldMap HashMap<String, String> updateData);

    @GET("log_listing")
    Call<DashBoardModel> getDashBoardData(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                                          @Query("login_key") String login_key);
    @GET("get_quote")
    Call<QuoteModel> getQuote(@Query("user_id") String user_id, @Query("encrypt_key") String encrypt_key,
                              @Query("login_key") String login_key);

*//*



}

*/
}