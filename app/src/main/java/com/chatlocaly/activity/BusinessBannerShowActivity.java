package com.chatlocaly.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicommons.commons.image.ImageUtils;
import com.bumptech.glide.Glide;
import com.chatlocaly.R;
import com.chatlocaly.global.Constants;
import com.chatlocaly.imageView.TouchImageView;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.utill.BasicUtill;
import com.chatlocaly.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 1/11/2018.
 */
public class BusinessBannerShowActivity extends AppCompatActivity implements View.OnClickListener {
    List<BusinessProfileInfoModel.Datadata.BusinessDetaildata.BusinessImagesdata> businessImageList;
    private TextView tv_toolbarTitle;
    private View view;
    private Context context;
    private ViewPager viewpager;
    private BannerShowAdapter bannerShowAdapter;
    private BusinessProfileInfoModel.Datadata.BusinessDetaildata info;
    private ImageView iv_arrowBack, iv_share;
    private AlertDialog alertdialog;
    private Chatlocalyshareprefrence preference;
    private Utill utill;
    private int position = -1;
    private ProgressBar progressBar;

    @Override
    protected void onResume() {
        super.onResume();
        //    new BasicUtill().CheckStatus(BusinessBannerShowActivity.this,0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannershow);
        context = this;
        utill = new Utill();
        preference = new Chatlocalyshareprefrence(BusinessBannerShowActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
        iv_share = findViewById(R.id.iv_share);

        iv_share.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            info = (BusinessProfileInfoModel.Datadata.BusinessDetaildata) b.getSerializable(Constants.PRODUCT_SERIALIZABLE);
            position = b.getInt(Constants.POSITION);
            setImages();
            tv_toolbarTitle.setText("" + info.getBusinessName());

        }
        iv_arrowBack.setOnClickListener(this);


        // check multiple device login
        new BasicUtill().CheckStatus(this);

    }

    private void setImages() {
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        bannerShowAdapter = new BannerShowAdapter(BusinessBannerShowActivity.this, info);
        viewpager.setAdapter(bannerShowAdapter);
        if (position > -1)
            viewpager.setCurrentItem(position);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_share:
                com.chatlocaly.Utill.shareImageIntent(this.view, context);
                break;

        }
    }

    public class BannerShowAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public BannerShowAdapter(Context context, BusinessProfileInfoModel.Datadata.BusinessDetaildata info) {
            mContext = context;
            businessImageList = info.getBusinessImages();
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (businessImageList != null)
                return businessImageList.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.iv_product_image);
            Glide.with(mContext).load(businessImageList.get(position).getImageName()).into(imageView);
            container.addView(itemView);
            if (position == 0)
                view = container;
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }

    }


}
