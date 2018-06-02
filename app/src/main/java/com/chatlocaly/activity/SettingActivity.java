package com.chatlocaly.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.utill.BasicUtill;

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{



    private TextView tv_notification,tv_yourprofile;
    private Toolbar mToolbar;
    private TextView tv_toolbartitle;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbartitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv_toolbartitle.setText("Settings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
         context=this;



        //********************* ***********************/
        tv_notification= (TextView) findViewById(R.id.tv_notification);
        tv_yourprofile= (TextView) findViewById(R.id.tv_profile);

        /********************** *********************/
       tv_notification.setOnClickListener(this);
        tv_yourprofile.setOnClickListener(this);
        // check multiple device login
        new BasicUtill().CheckStatus(this);

    }

    @Override
    public void onClick(View v) {

        if(Utill.isConnectingToInternet(context)) {
            switch (v.getId()) {

                case R.id.tv_profile:


                    startActivity(new Intent(this, UserProfileActivity.class));
                    break;
                case R.id.tv_notification:

                    startActivity(new Intent(this, Notification.class));

                    break;

            }
        }
        else
            Utill.showCenteredToast(context,getString(R.string.str_check_internet_connection));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
          onBackPressed();
        return super.onOptionsItemSelected(item);



    }
}
