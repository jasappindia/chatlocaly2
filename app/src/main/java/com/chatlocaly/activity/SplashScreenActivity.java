package com.chatlocaly.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.firebasenotification.MyFirebaseMessagingService;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.splunk.mint.Mint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {


    Context context;
    private Chatlocalyshareprefrence mySharedpreference;
    private LocalBroadcastManager broadcaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        //    Mint.initAndStartSession(context, "9fff2c84");
        Handler h = new Handler();
        mySharedpreference = new Chatlocalyshareprefrence(context);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mySharedpreference.getLoginKey().trim().equalsIgnoreCase("") && mySharedpreference.getUserType().equalsIgnoreCase("old_user")) {
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                    finish();
                }
               else if (!mySharedpreference.getLoginKey().trim().equalsIgnoreCase("") && mySharedpreference.getUserType().equalsIgnoreCase("new_user")) {

                    mySharedpreference.logout();
                    Intent i = new Intent(context, OTP_VerificationActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    mySharedpreference.logout();
                    Intent i = new Intent(context, OTP_VerificationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);



        /*
        *
        *     if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getInt(MyFirebaseMessagingService.OPEN_FRAGMENT_KEY,0)!=0)
            {
                int fragment_key=getIntent().getExtras().getInt(MyFirebaseMessagingService.OPEN_FRAGMENT_KEY,0);
                Log.e("income fragment onCre "," "+fragment_key);

                switch (fragment_key)
                {
                    case MyFirebaseMessagingService.MESSAGE_FRAGMENT:

                        setFragment(fragment_key);
                        break;
                    case MyFirebaseMessagingService.TASK_FRAGMENT:
                        setFragment(fragment_key);

                        break;
                    case MyFirebaseMessagingService.EVENT_REQUEST_FRAGMENT:
                        setFragment(fragment_key);

                        break;
                    case MyFirebaseMessagingService.EVENT_SENT_FRAGM ENT:
                        setFragment(fragment_key);

                        break;

                }




            }





        }*/

    }





    private void init() {
        context = SplashScreenActivity.this;


    }
}