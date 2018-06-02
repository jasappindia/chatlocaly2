package com.chatlocaly.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by Prateek on 5/14/2015.
 */
public class CustomActionbar extends Activity {
/*
    public static ImageView back_button;
    public static TextView mTitleTextView;

    public static void customActionbar(final ActionBarActivity activity, String title) {

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        activity.getSupportActionBar().setHomeButtonEnabled(false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(activity);
        View mCustomView = mInflater.inflate(R.layout.customactionbar, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(title);
        back_button = (ImageView) mCustomView.findViewById(R.id.back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        activity.getSupportActionBar().setCustomView(mCustomView, lp);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.header_color)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.header_color));
        }

    }


    public static void setStatusBarColor(ActionBarActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.header_color));
        }
    }

    public static void NativeCustomActionBar(ActionBarActivity actionBarActivity) {
        if (actionBarActivity.getSupportActionBar() != null) {
            actionBarActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionBarActivity.getResources().getColor(R.color.header_color)));
            actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBarActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        }
    }*/


}
