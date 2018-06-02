package com.chatlocaly.views;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.chatlocaly.R;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.helper.Utill;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;


/**
 * Created by Prateek on 5/14/2015.
 */
public class CustomActionbar extends Activity {

    public static ImageView  btnMenu, btnCart,btnBack;
    public static TextView mTitleTextView,mSubtitleTitleTextView,mItemCount;

    public static void customActionbar(final AppCompatActivity activity, String title,String subTitle,int visibility) {

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        /// /actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
/*
        LayoutInflater mInflater = LayoutInflater.from(activity);
        View mCustomView = mInflater.inflate(R.layout.customactionbar, null);

        mSubtitleTitleTextView= (TextView) mCustomView.findViewById(R.id.toolbar_sub_title);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.toolbar_title);
        mItemCount = (TextView) mCustomView.findViewById(R.id.tv_cart_item_count);
        mTitleTextView.setText(title);
        mSubtitleTitleTextView.setText(subTitle);
        mSubtitleTitleTextView.setVisibility(visibility);
        new Utill().setTypeFace(mTitleTextView, "OswaldMedium.ttf",activity);
        new Utill().setTypeFace(mSubtitleTitleTextView, "OswaldLight.ttf",activity);

        btnMenu = (ImageView) mCustomView.findViewById(R.id.iv_menu_drawer);
        btnCart = (ImageView) mCustomView.findViewById(R.id.iv_menu_cart);
        btnBack = (ImageView) mCustomView.findViewById(R.id.iv_arrow_right);
        mItemCount.setText(new Chatlocalyshareprefrence(activity).getCartITEM_COUNT());

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.header_color)));
        actionBar.setCustomView(mCustomView, lp);
        

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).drawerOpenClose();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).openAddToCart();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).onBackPressed();
            }
        });*/

    }







}
