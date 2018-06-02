package com.chatlocaly.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.utill.BasicUtill;

public class AboutActivity extends AppCompatActivity  implements View.OnClickListener{



    private TextView tv_version;
    private RelativeLayout rl_policaies,rl_terms;
    private Toolbar mToolbar;
    private TextView tv_toolbartitle,tv_terms,tv_policy;
    private ImageView iv_home;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_home=findViewById(R.id.iv_arrowBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        tv_toolbartitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv_toolbartitle.setText("About Us");

        //********************* ***********************/
        tv_version= (TextView) findViewById(R.id.tv_versioncode);
        rl_policaies= (RelativeLayout) findViewById(R.id.rl_policy);
        rl_terms= (RelativeLayout) findViewById(R.id.rl_terms);
        tv_terms= (TextView) findViewById(R.id.tv_terms);
        tv_policy= (TextView) findViewById(R.id.tv_policy);


        /********************** *********************/
        tv_version.setOnClickListener(this);
        rl_terms.setOnClickListener(this);
        rl_policaies.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        // check multiple device login
        new BasicUtill().CheckStatus(this);


        // set linked to page
      //  tv_policy.setMovementMethod(LinkMovementMethod.getInstance());
      //  tv_terms.setMovementMethod(LinkMovementMethod.getInstance());



    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.tv_versioncode:

                break;


            case R.id.rl_policy:
            //    Utill.openUrl(getBaseContext(),"https://www.chatlocaly.com/legal");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.chatlocaly.com/legal"));
                startActivity(i);

                break;
            case R.id.rl_terms:
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.chatlocaly.com/legal"));
              startActivity(i2);
              //  Utill.openUrl(getBaseContext(),"https://www.chatlocaly.com/legal");

                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);



    }
}
