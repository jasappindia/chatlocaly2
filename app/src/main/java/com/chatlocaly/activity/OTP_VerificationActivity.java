package com.chatlocaly.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.account.user.UserService;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.model.LoginModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.ui.CustomViewPagerNoswip;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.splunk.mint.Mint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class OTP_VerificationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final int REQUEST_PERMISSIONS = 152;
    public EditText edt_mobileNumber, edt_inputotpnumber;
    public TextView tv_privacypolicy, tv_termsandcondition, tv_otpverifyLine;
    public View otp_toolbar;
    private String TAG = OTP_VerificationActivity.class.getSimpleName();
    private Button btn_mobile_continue, btn_otp_continue;
    private CustomViewPagerNoswip viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms;
    private ProgressBar progressBar;

    private Chatlocalyshareprefrence sharedPreferences;
    private Context context;
    private String deviceId = "";
    private View view;
    private List<String> list;
    private TextView tv_otp_resend;
    private String user_Activestatus="";

    // firebase analytics
    private FirebaseAnalytics mFirebaseAnalytics;
    private String seckey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);
        init();
        //
        Applozic.init(getApplicationContext(),getString(R.string.application_key));

        // chatlocaly
         Mint.initAndStartSession(this.getApplication(), "0b98af39");
        // firebase analytics
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);





        // custom event log code

       /* Bundle params = new Bundle();
        params.putString("name", "ashok");
        mFirebaseAnalytics.logEvent("share_image", params);

*/


        // Checking for user session
        // if user is already logged in, take him to main activity
        if (!sharedPreferences.getUserId().equalsIgnoreCase("0")) {
            Intent intent = new Intent(OTP_VerificationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {

          /* if( Utill.isConnectingToInternet(context))
            // get country
            getCountryList();
            else
           Utill.showCenteredToast(context,"Please Check internet Connection");
*/
        }

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


      /*  *//**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         *//*

       ///      mobile number in textview in android

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            // Do something for M and above versions
            // request for permission in
            requestMultiplePermissions();
*/

        edt_mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 9) {

                    btn_mobile_continue.setFocusable(true);
                    btn_mobile_continue.setClickable(true);
                    btn_mobile_continue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btn_mobile_continue.setTextColor(getResources().getColor(R.color.white));

                } else {
                    btn_mobile_continue.setFocusable(false);
                    btn_mobile_continue.setClickable(false);
                    btn_mobile_continue.setBackgroundColor(getResources().getColor(R.color.button_back));
                    btn_mobile_continue.setTextColor(getResources().getColor(R.color.text_color_light));
                }


            }
        });


        edt_inputotpnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 5) {


                    btn_otp_continue.setFocusable(true);
                    btn_otp_continue.setClickable(true);
                    btn_otp_continue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btn_otp_continue.setTextColor(getResources().getColor(R.color.white));

                } else {
                    btn_otp_continue.setFocusable(false);
                    btn_otp_continue.setClickable(false);
                    btn_otp_continue.setBackgroundColor(getResources().getColor(R.color.button_back));
                    btn_otp_continue.setTextColor(getResources().getColor(R.color.text_color_light));
                }
            }


        });

    }
    int count=0;

    @Override
    public void onBackPressed() {
        if(viewPager!=null)
        {
            if(viewPager.getCurrentItem()==1)
            {
                viewPager.setCurrentItem(0);
            }
            else
            {

                if(count==0)
                {
                    count++;
                    Utill.showCenteredToast(context,getResources().getString(R.string.back_first_message));
                }
                else

                {
                    count++;
                    OTP_VerificationActivity.this.finish();
                    //  Utill.showCenteredToast(context,getResources().getString(R.string.back_first_message));

                }


            }

        }


    }

    private void init() {

        list = new ArrayList<>();
        context = OTP_VerificationActivity.this;
        sharedPreferences = new Chatlocalyshareprefrence(context);
        seckey=sharedPreferences.getEncryptKey();
        viewPager = (CustomViewPagerNoswip) findViewById(R.id.viewPagerVertical);
        view = findViewById(R.id.viewContainer);
        //
        edt_mobileNumber = (EditText) findViewById(R.id.edt_phonenum);
        edt_inputotpnumber = (EditText) findViewById(R.id.edt_inputotp);

        //
        btn_mobile_continue = (Button) findViewById(R.id.btn_continueLogin);
        btn_otp_continue = (Button) findViewById(R.id.btn_continue);
        //
        tv_termsandcondition = (TextView) findViewById(R.id.tv_terms);
        tv_privacypolicy = (TextView) findViewById(R.id.tv_privacyPolicy);
        tv_otpverifyLine = (TextView) findViewById(R.id.tv_otpverifyLine);
        tv_otp_resend = (TextView) findViewById(R.id.tv_otp_resend);
        // view click listeners
        btn_mobile_continue.setOnClickListener(this);
        btn_otp_continue.setOnClickListener(this);
        tv_privacypolicy.setOnClickListener(this);
        tv_termsandcondition.setOnClickListener(this);
        tv_otp_resend.setOnClickListener(this);

    //    tv_termsandcondition.setMovementMethod(LinkMovementMethod.getInstance());
      //  tv_privacypolicy.setMovementMethod(LinkMovementMethod.getInstance());

        // handle  done  option in  key bord option
        edt_mobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    login();

                }
                return false;
            }
        });


        // handle  done  option in  key bord option
        edt_inputotpnumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    verifyOtp();

                }
                return false;
            }
        });
        //* device id
        try {

              deviceId = sharedPreferences.getDeviceId();
              if(deviceId.equalsIgnoreCase(""))
              deviceId=FirebaseInstanceId.getInstance().getToken();

        } catch (Exception e) {

        }


    }

    //String emp_num =getArguments().getString("number");
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String str = intent.getStringExtra("message");
            if (str.length() > 0) {
                str = str.replaceAll("\\D+", "");
                try {
                  //  edttxt_confrmation_code.setText(str);
                } catch (Exception e) {
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter("OTP"));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.title_next:
                validate();
           *//*     //break;

            case R.id.tv_send_otp:
            //    verifyOtp();
                break;*/
            case R.id.tv_otp_resend:
                break;
            case R.id.btn_continue:
                verifyOtp();
                break;
            case R.id.btn_continueLogin:
               login();

                break;

        }
    }

    private void login() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            requestMultiplePermissions();

        } else{
            validate();

        }

    }


    private void validate() {

        if (edt_mobileNumber.getText().toString().trim() != null && edt_mobileNumber.getText().toString().trim().length() < 10)
            Utill.showCenteredToast(context, " Please enter mobile number ");

        else {
            // code here
            Utill.keyboardHide(context, edt_mobileNumber);

            //   if(deviceId!=null && ! deviceId.equalsIgnoreCase(""))
            sendUserLogin("91", seckey, deviceId, edt_mobileNumber.getText().toString().trim());
        }

    }


    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {

        if (edt_inputotpnumber.getText().toString() != null && edt_inputotpnumber.getText().toString().equalsIgnoreCase("")) {

            Utill.showCenteredToast(context, " Please  Enter otp number code");
        } else if(edt_inputotpnumber.getText().toString().trim().length()>1 && edt_inputotpnumber.getText().toString().trim().length()<6)
        {
            Utill.showCenteredToast(context, getString(R.string.otp_code));

        }
        else {

            String otp = edt_inputotpnumber.getText().toString().trim();
            verifyOtp(otp, edt_mobileNumber.getText().toString(), seckey);
            // code here
            Utill.keyboardHide(context, edt_inputotpnumber);

        }
    }
/*
    *//**************/
    /**************************** ***********************************************//**//**/
    public void sendUserLogin(String iso, String accessTocken, String deviceId, String usermobileNumber) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

      /*  encrypt_key=dfdfskfj
       &c_mobile_number=9460405321
       &c_cm_code=91&
      c_device_id=sdfgshd&
      c_device_type=ANDROID
*/
        HashMap<String, String> params = new HashMap<>();
        params.put("c_cm_code", iso);
        params.put("encrypt_key", accessTocken);
        params.put("c_mobile_number", usermobileNumber);
        params.put("c_device_id", ""+this.deviceId);
        params.put("c_device_type", "ANDROID");

        Call<ResultModel> call = apiService.getLoginData(params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                pDialog.dismiss();

                ResultModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        // set text  mobile number
                        tv_otpverifyLine.setText("A text message was sent to " + edt_mobileNumber.getText().toString());
                        user_Activestatus=clientModel.getData().getRegisterStatus();
                        sharedPreferences.setUserType(user_Activestatus);

                        /*sharedPreferences.*/

                        viewPager.setCurrentItem(1);

                    } else {



                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

/*
    *//**/

    /****************************************************************
     * verify otp
     *************************************************//**//*

    *//**/

    // code for  permission work  in application
    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissions() {



        String readSms = Manifest.permission.READ_SMS;
        String callPhone = Manifest.permission.CALL_PHONE;
        String camera = Manifest.permission.CAMERA;
        String readContact = Manifest.permission.READ_CONTACTS;
        String readStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        String writeStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;


        int hasreadSms = context.checkSelfPermission(readSms);
        int hascallPhone = context.checkSelfPermission(callPhone);
        int hasreadcontact = context.checkSelfPermission(readContact);
        int hascamera = context.checkSelfPermission(camera);
        int hasreadstorage = context.checkSelfPermission(readStorage);
        int haswritestorage = context.checkSelfPermission(writeStorage);

        List<String> permissions = new ArrayList<String>();
        if (hasreadSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readSms);
        }
        if (hascallPhone != PackageManager.PERMISSION_GRANTED) {
            permissions.add(callPhone);
        }

        if (hasreadcontact != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readContact);
        }
        if (hascamera != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera);
        }
        if (haswritestorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(writeStorage);
        }
        if (hasreadstorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readStorage);
        }


        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_PERMISSIONS);

        }
        else
            validate();

    }




// permission request application in

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                validate();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**************************************** ***********************************************//**/
    public void verifyOtp(String otp, String mobile, String access) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


/*
*
* encrypt_key=jkhgfkg&
c_mobile_number=9460405321&
otp_code=123456

* */
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", access);
        params.put("c_mobile_number", mobile);
        params.put("otp_code", otp);

        Call<LoginModel> call = apiService.getOtpVerification(params);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                LoginModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        // check user is new or old
                       if(user_Activestatus!=null && user_Activestatus.equalsIgnoreCase("old_user"))
                         {

                             loginByApplozic(true,pDialog,clientModel);

                          }
                          else
                       {

                           loginByApplozic(false,pDialog,clientModel);

                       }

                       // applozic login user details






                    } else {
                        pDialog.dismiss();
                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");
                Utill.showCenteredToast(context, str);
                pDialog.dismiss();
            }
        });
    }

    private void loginByApplozic(final boolean  loginstatus, final ProgressDialog pDialog, final LoginModel clientModel) {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, final Context context) {
                //After successful registration with Applozic server the callback will come here
                Log.d("applozic login","login success");

                if(registrationResponse!=null) {

                    sharedPreferences.setApplozic_key(registrationResponse.getDeviceKey());
                    sharedPreferences.setMOBILENUMBER(clientModel.getData().getUserDetail().getCMobileNumber());
                    sharedPreferences.setUserId(clientModel.getData().getUserDetail().getCUserId());
                    sharedPreferences.setLoginKey(clientModel.getData().getUserDetail().getLogin_key());

                    pDialog.dismiss();





                    Log.d("TAG","login success");

                    if(MobiComUserPreference.getInstance(context).isRegistered()) {

                        PushNotificationTask pushNotificationTask = null;
                        PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
                            @Override
                            public void onSuccess(RegistrationResponse registrationResponse) {

                                if (loginstatus) {
                                    startActivity(new Intent(context, MainActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(context, ProfileInfoActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                            }

                        };

                        pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(), listener, context);
                        pushNotificationTask.execute((Void) null);

                    }



                }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                //If any failure in registration the callback  will come here
                pDialog.dismiss();

                finish();

            }};

        User user = new User();
        user.setUserId(Utill.getApplozicUserId(clientModel.getData().getUserDetail().getCUserId()));

        Log.e("userId",""+Utill.getApplozicUserId(clientModel.getData().getUserDetail().getCUserId()));
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");


        //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
       /* user.setDisplayName(displayName); //displayName is the name of the user which will be shown in chat messages
        user.setEmail(email); //optional
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink("");//optional, set your image link if you have
//       */
        new UserLoginTask(user, listener, this).execute((Void) null);





    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



  /*  public void verifyResendOtp(String mobile, String access) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

    *//**//**//**//*

         http://192.168.0.70/event_app/api/verify_account?verify_code=fznvm&
         mobile_no=7891439284&
         access_token=01eb33b064131953ff1e9a3178f7add28abd3f662b92aa3af2109ec413816bbb

    *//**//**//**//*

        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", access);
        params.put("mobile", mobile);
        params.put("message_skip", "N");
        Call<ResultModel> call = apiService.getResendVerify(params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                ResultModel clientModel = response.body();

                if (clientModel.getData() != null) {

                    if (   clientModel != null  &&   clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());

                    } else {


                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                Utill.showCenteredToast(context, t.toString());
                // Log error here since request failed
                Log.e("", t.toString());
                String str=t.toString().toLowerCase();
                if(str.contains("www.inlook.co")) Utill.snakbarShow(view,"Check internet Connection");

                pDialog.dismiss();
            }
        });
    }
*/
/// view pager activity in android


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }
/**********************************************                      ******************************************************/


/*
public void getCountryList() {
    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
  final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
//circularProgressBar.setVisibility(View.VISIBLE);

    Call<CountryListModel> call = apiService.getCountryList();
    call.enqueue(new Callback<CountryListModel>() {
        @Override
        public void onResponse(Call<CountryListModel> call, retrofit2.Response<CountryListModel> response) {

            CountryListModel clientModel = response.body();
            if (  clientModel!=null  && clientModel.getData()!=null) {
                if (clientModel != null && clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                    list = clientModel.getData().getCountryList();


                } else {

                    Utill.showCenteredToast(context, ""+clientModel.getData().getMessage());
                }
            }

//            circularProgressBar.setVisibility(View.GONE);

            // Utill.showCenteredToast(context, response.body().toString());
            pDialog.dismiss();
        }

        @Override
        public void onFailure(Call<CountryListModel> call, Throwable t) {
            // Log error here since request failed
            Log.e("error", t.toString());

            String str=t.toString().toLowerCase();
            if(str.contains("www.inlook.co")) Utill.snakbarShow(view,"Check internet Connection");

            pDialog.dismiss();
//            circularProgressBar.setVisibility(View.GONE);
        }
    });
}*/

}
