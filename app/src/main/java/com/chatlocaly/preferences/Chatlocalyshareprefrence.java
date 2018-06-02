package com.chatlocaly.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.chatlocaly.application.ChatLocalyApplication;

public class Chatlocalyshareprefrence {
    public static String FIRST_NAME = "fName";
    public static String Full_NAME = "fullName";

    public static String ITEM_COUNT = "item_count";
    public static String LAST_NAME = "lName";
    public static String COUNTRY_ID = "country_id";
    public static String STATE_ID = "state_id";
    public static String CITY_ID = "city_id";
    public static String USER_IMAGE = "user_image";
    public static String USER_ID = "id";
    public static String EMAIL_ID = "email_id";
    public static String MOBILENUMBER = "mobile_number";
    public static String ADDRESS = "address";
    public static String POST_CODE = "post_code";
    public static String COUNTRY_NAME = "country_name";
    public static String STATE_NAME = "state_name";
    public static String CITY_NAME = "city_name";
    public static String LANGUAGE="language";
    public static String RADIUS="radius";
    public static String IS_CHECK="is_check";
    public static String GCM_Token_Id="token_id";
    public static String category_type="category_type";
    public static String GENDER="gender";
    public static String DOB="dob";
    public static String FIRST_TIME="first_time";
    public static String LOGIN_KEY="login_key";
    private static String EMAIL="email";
    public static String PASSWORD="password";
    public static String DIVICE_ID="device_id";
    public static String SORT_BY_POPULARITY="SORTBY";

    public static String Vibration_status="vibration_status";
    public static String NOTIFICATION_STATUS="Notification_status";

    public static String RING_TONE_LIST="ringtone";
    public static String RING_TONE_LIST_title="ringtonetitle";
    public static String ENCRYPT_KEY="encrypt_key";

    public static String Applozic_key="applozic_key";


    public static String UserType="userType";

    private Context context;
    private static final String APP_SHARED_PREFS = Chatlocalyshareprefrence.class.getSimpleName(); // Name of the file -.xml
    private static SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;
    public Chatlocalyshareprefrence(Context ctx) {
        this.context = ctx;
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();


    }


    public   String getLoginKey() {
        return _sharedPrefs.getString(LOGIN_KEY, "");
    }

    public void setLoginKey(String loginKey) {
        _prefsEditor.putString(LOGIN_KEY,loginKey);
        _prefsEditor.commit();
    }

    public void setDistance( boolean is_check)
    {
        _prefsEditor.putBoolean(IS_CHECK,is_check);
        _prefsEditor.commit();

    }
    public void setPassword( String password)
    {
        _prefsEditor.putString(PASSWORD,password);
        _prefsEditor.commit();

    }


    public  String getRingToneList() {
        return _sharedPrefs.getString(RING_TONE_LIST,"Default Sound");
    }

    public  void setRingToneList(String ringToneList) {
        _prefsEditor.putString(RING_TONE_LIST,ringToneList);
        _prefsEditor.commit();

    }


    public  String getRING_TONE_LIST_title() {
        return _sharedPrefs.getString(RING_TONE_LIST_title,"Default Sound");
    }

    public  void setRING_TONE_LIST_title(String ringToneList) {
        _prefsEditor.putString(RING_TONE_LIST_title,ringToneList);
        _prefsEditor.commit();

    }
    public void setSortByPopularity( boolean byPopularity)
    {
        _prefsEditor.putBoolean(SORT_BY_POPULARITY,byPopularity);
        _prefsEditor.commit();

    }
    public boolean getSortByPopularity()
    {
        return _sharedPrefs.getBoolean(SORT_BY_POPULARITY,true);

    }
    public void setCartCount(String count)
    {
        _prefsEditor.putString(ITEM_COUNT,count);
        _prefsEditor.commit();

    }

    public void setUserType(String userType)
    {
        _prefsEditor.putString(UserType,userType);
        _prefsEditor.commit();

    }
    public  String getUserType()
    {
        return  _sharedPrefs.getString(UserType,"new_user");
    }

    public  boolean getVibration_status() {
        return _sharedPrefs.getBoolean(Vibration_status,true);
    }

    public  void setVibration_status(boolean vibration_status) {

        _prefsEditor.putBoolean(Vibration_status,vibration_status);
        _prefsEditor.commit();
    }



    public  boolean getNotication_status() {

        return _sharedPrefs.getBoolean(NOTIFICATION_STATUS,true);
    }

    public  void setNotication_status(boolean notication_status) {

        _prefsEditor.putBoolean(NOTIFICATION_STATUS,notication_status);
        _prefsEditor.commit();
    }

    public void setFirstTime(boolean first)
    {
        _prefsEditor.putBoolean(FIRST_TIME,first);
        _prefsEditor.commit();

    }
    public void setFullName(String firstname)
    {
        _prefsEditor.putString(Full_NAME,firstname);
        _prefsEditor.commit();

    }

    public void setUserId(final String user_id)
    {
        _prefsEditor.putString(USER_ID,user_id);
        _prefsEditor.commit();

    }
    public void setRadius(final String radius)
    {
        _prefsEditor.putString(RADIUS,radius);
        _prefsEditor.commit();

    }

    public void setCategory(final String category)
    {
        _prefsEditor.putString(category_type,category);
        _prefsEditor.commit();

    }

    public void setCountry_name(final String country_name)
    {
        _prefsEditor.putString(COUNTRY_NAME,country_name);
        _prefsEditor.commit();

    }

    public void setEmail(final String email)
    {
        _prefsEditor.putString(EMAIL_ID,email);
        _prefsEditor.commit();

    }

    public void setGender(final String gender)
    {
        _prefsEditor.putString(GENDER,gender);
        _prefsEditor.commit();

    }
    public void setDOB(final String dob)
    {
        _prefsEditor.putString(DOB,dob);
        _prefsEditor.commit();

    }

    public  boolean getFirstTime() {
        return  _sharedPrefs.getBoolean(FIRST_TIME, false);
    }

    public  String getFullName() {
        return  _sharedPrefs.getString(Full_NAME, "");
    }


    public String getGENDER() {
        return _sharedPrefs.getString(GENDER, "");
    }

    public   String getCartITEM_COUNT() {
        return _sharedPrefs.getString(ITEM_COUNT, "0");
    }

    public  String getDOB() {
        return _sharedPrefs.getString(DOB, "");
    }

    public void setState_name(final String state_name)
    {
        _prefsEditor.putString(STATE_NAME,state_name);
        _prefsEditor.commit();

    }
    public void setCountry_id(final String country_id)
    {
        _prefsEditor.putString(COUNTRY_ID,country_id);
        _prefsEditor.commit();

    }

    public void setState_Id(final String city_name)
    {
        _prefsEditor.putString(STATE_ID,city_name);
        _prefsEditor.commit();

    }
    public void setCity_ID(final String city_name)
    {
        _prefsEditor.putString(CITY_ID,city_name);
        _prefsEditor.commit();

    }

    public void setCity_name(final String city_name)
    {
        _prefsEditor.putString(CITY_NAME,city_name);
        _prefsEditor.commit();

    }
    public  String getpassword() {
        return _sharedPrefs.getString(PASSWORD, "");
    }


    public  String getRADIUS() {
        return _sharedPrefs.getString(RADIUS, "");
    }


    public  String getCategory_type() {
          return _sharedPrefs.getString(category_type, "");
    }

    public  String getLANGUAGE() {
          return _sharedPrefs.getString(LANGUAGE, "");
    }

    public  String getCountryName() {
        return _sharedPrefs.getString(COUNTRY_NAME, "");
    }

    public  String getStateName() {
        return _sharedPrefs.getString(STATE_NAME, "");
    }

    public  String getCityName() {
        return _sharedPrefs.getString(CITY_NAME, "");
    }


    public  boolean getIsCheck() {
          return _sharedPrefs.getBoolean(IS_CHECK, false);
    }

    public void logout() {
        String language=_sharedPrefs.getString(LANGUAGE,"");
        this._prefsEditor.clear().commit();
        _prefsEditor.putString(LANGUAGE,language);
        _prefsEditor.commit();
    }


    public void setImage(final String image_url)
    {
        _prefsEditor.putString(USER_IMAGE,image_url);
        _prefsEditor.commit();

    }

    public void setLanguage(final String lang)
    {
        _prefsEditor.putString(LANGUAGE,lang);
        _prefsEditor.commit();

    }

    public void setFirstName(final String name)
    {
        _prefsEditor.putString(FIRST_NAME,name);
        _prefsEditor.commit();

    }
    public void setLastName(final String name)
    {
        _prefsEditor.putString(LAST_NAME,name);
        _prefsEditor.commit();

    }

    public void setLocationInfo(final String country_id,final String state_id,final String city_id)
    {
        _prefsEditor.putString(COUNTRY_ID,country_id);
        _prefsEditor.putString(STATE_ID,state_id);
        _prefsEditor.putString(CITY_ID,city_id);
        _prefsEditor.commit();

    }

    public void setExtraInfo(final String email,final String address,final String postcode,final String mobile_number)
    {
        _prefsEditor.putString(EMAIL_ID,email);
        _prefsEditor.putString(ADDRESS,address);
        _prefsEditor.putString(POST_CODE,postcode);
        _prefsEditor.putString(MOBILENUMBER,mobile_number);
        _prefsEditor.commit();

    }
    public  String getGCM_Token_Id() {
        return  _sharedPrefs.getString(GCM_Token_Id, "");
    }

    public  void  setToken_Id(final String Token_Id)
    {
        _prefsEditor.putString(GCM_Token_Id, Token_Id);
        _prefsEditor.commit();
    }


    public  String getEncryptKey() {
        return  _sharedPrefs.getString(ENCRYPT_KEY, "asdef");
    }

    public  void  setEncryptKey(final String encryptKey)
    {
        _prefsEditor.putString(ENCRYPT_KEY, encryptKey);
        _prefsEditor.commit();
    }

    public  void setMOBILENUMBER(String MOBILENUMBER) {
        _prefsEditor.putString(Chatlocalyshareprefrence.MOBILENUMBER,MOBILENUMBER);
        _prefsEditor.commit();

    }


    public  void setDiviceId(String diviceId) {
        _prefsEditor.putString(Chatlocalyshareprefrence.DIVICE_ID,diviceId);
        _prefsEditor.commit();

    }


    public  String getUserId() {
        return _sharedPrefs.getString(USER_ID, "0");
    }

    public  String getFirstName() {
        return _sharedPrefs.getString(FIRST_NAME, null);
    }

    public  String getLastName() {
        return _sharedPrefs.getString(LAST_NAME, null);
    }

    public  String getCountryId() {
        return _sharedPrefs.getString(COUNTRY_ID, "");
    }

    public  String getStateId() {
        return _sharedPrefs.getString(STATE_ID, "");
    }

    public  String getCityId() {
        return _sharedPrefs.getString(CITY_ID, "0");
    }

    public  String getUserImage() {
        return _sharedPrefs.getString(USER_IMAGE, " ");
    }

    public  String getEmailId() {
        return _sharedPrefs.getString(EMAIL_ID, null);
    }

    public  String getMOBILENUMBER() {
        return _sharedPrefs.getString(MOBILENUMBER, "");
    }

    public  String getADDRESS() {
        return _sharedPrefs.getString(ADDRESS, "");
    }

    public  String getPostCode() {
        return _sharedPrefs.getString(POST_CODE, null);
    }
    public  String getDeviceId() {
        return _sharedPrefs.getString(DIVICE_ID, "");
    }


    public  void removeAllPreference( Context context) {

         ChatLocalyApplication.getInstance().clearApplicationData();
         _prefsEditor.clear().commit();

    }

    public String getApplozicDeviceKey() {
        return _sharedPrefs.getString(Applozic_key,"");
    }
    public  void setApplozic_key(String applozic_key)
    {
        _prefsEditor.putString(Applozic_key,applozic_key);
        _prefsEditor.commit();

    }
}