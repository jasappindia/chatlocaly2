package com.chatlocaly.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.utill.BasicUtill;
import com.chatlocaly.utill.Utill;

import java.util.List;

public class Full_ImageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_toolbarTitle;
    private View view;
    private Context context;
    private ViewPager viewpager;
    private ImageView iv_arrowBack, iv_share,iv_fullImageView;
    private AlertDialog alertdialog;
    private Chatlocalyshareprefrence preference;
    private Utill utill;
    private int position = -1;
    private ProgressBar progressBar;

    private  String imagepath;

    @Override
    protected void onResume() {
        super.onResume();
        //    new BasicUtill().CheckStatus(BusinessBannerShowActivity.this,0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image);
        context = this;
        utill = new Utill();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);

        iv_share = findViewById(R.id.iv_share);
        iv_fullImageView=findViewById(R.id.full_screen_image);

        iv_share.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            imagepath = (String) b.getSerializable(Constants.FUILL_IMAGE_PATH);
        }

        Utill.imageShow(context,iv_fullImageView,imagepath);


        // check multiple device login
        new BasicUtill().CheckStatus(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_share:
                com.chatlocaly.Utill.shareImageIntent(iv_fullImageView, context);
                break;

        }
    }





}
