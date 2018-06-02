package com.chatlocaly.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocaly.R;
import com.chatlocaly.adapter.ProductsAdapter;
import com.chatlocaly.adapter.ServicesAdapter;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;
import com.chatlocaly.utill.Utill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shiv on 12/22/2017.
 */

public class BusinessProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUSSINESS_ID = "bussiness_id";
    public static final String BUSSINESS_PROFILE_INFO = "bussinessprofileinfo";
    public static ImageButton ib_arrowLeft, ib_arrowRight;
    int apicallStatus = 0;
    ArrayList<Float> ratingArray = new ArrayList<>();
    float max;
    String chatGroupName = "";
    private RelativeLayout rl_location, rl_products, rl_services, rl_address, rl_ratings;
    private RecyclerView rv_products, rv_service;
    private LinearLayoutManager layoutManagerservices, layoutManagerProducts;
    private ServicesAdapter adapterservices;
    private ProductsAdapter adapterProducts;
    private ViewGroup rl_productContainer, rl_sericesContainer, rl_ratingsContainer;
    private ViewGroup newViewProducts, newViewServices, newViewRatings;
    private TextView tv_businessName, tv_homeservice, tv_contact, tv_ImageNo, tv_address, tv_ratings;
    private TextView tv_viewAllProducts, tv_viewAllServices;
    private ImageView iv_filledRating1, iv_filledRating2, iv_filledRating3, iv_filledRating4, iv_filledRating5;
    private TextView tv_noRatings1, tv_noRatings2, tv_noRatings3, tv_noRatings4, tv_noRatings5, tv_avgRatings, tv_totalRatings;
    private ImageView iv_chatIcon, iv_banner, iv_reportBusiness, iv_businessIcon, iv_arrowBack, iv_mapIcon;
    private ImageView iv_ratingsDown, iv_starIcon,iv_seviceDown, iv_locationDown, iv_productsDown, iv_home;
    private Utill utill;
    private Chatlocalyshareprefrence preference;
    private ProgressBar progressBar;
    private LinearLayout ll_info;
    private BusinessProfileInfoModel.Datadata.BusinessDetaildata info;
    private PagerForBanner pagerForBanner;
    private Context context;
    private String bussinessProfileId = "0";
    private LinearLayout ll_business_profile;
    private int counter = 0;
    private Integer channelKey = 0;
    private ImageView iv_notification, iv_tag;
    private TextView tv_toolbartitle;
    private ScrollView scrollView;
    private ViewPager vp_banner;
    private ImageView iv_contact;
    private String chatGroupId;

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        preference = new Chatlocalyshareprefrence(this);
        utill = new Utill();
        init();

        if(Utill.isConnectingToInternet(context)) {
            setData();
            // check multiple device login
            new BasicUtill().CheckStatus(this);
        }
        else
            Utill.showCenteredToast(context,getString(R.string.str_check_internet_connection));

//        ll_info.setVisibility(View.VISIBLE);

    }

    private void setData() {


        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().hasExtra(BUSSINESS_ID))
            //   final StoreListModel.DataData.BusinessListData businessListData = (StoreListModel.DataData.BusinessListData) getIntent().getExtras().getSerializable(BUSSINESS_PROFILE_INFO);
            /***********************  get bussiness profile id      *********************************/
                bussinessProfileId = getIntent().getExtras().getString(BUSSINESS_ID);
            else if (getIntent().hasExtra(ConversationActivity.BUSINESS_ID))
                bussinessProfileId = getIntent().getExtras().getString(ConversationActivity.BUSINESS_ID);
            getBusinessDetails(bussinessProfileId);


        }


    }

    public void showProductArrow(int position) {
        Log.e("position ", "" + position);
        if (layoutManagerProducts.findFirstCompletelyVisibleItemPosition() == 1)
            ib_arrowLeft.setVisibility(View.GONE);
        else if (layoutManagerProducts.findFirstVisibleItemPosition() > 1 && layoutManagerProducts.findLastCompletelyVisibleItemPosition() < 6) {
            ib_arrowLeft.setVisibility(View.VISIBLE);
            ib_arrowRight.setVisibility(View.VISIBLE);

        } else {
            ib_arrowLeft.setVisibility(View.VISIBLE);
            ib_arrowRight.setVisibility(View.INVISIBLE);

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        //    getBusinessDetails();

    }

    public void init() {
        context = this;
        ll_business_profile = (LinearLayout) findViewById(R.id.ll_profile);
        rl_productContainer = (ViewGroup) findViewById(R.id.rl_productContainer);
        rl_sericesContainer = (ViewGroup) findViewById(R.id.rl_sericesContainer);
        rl_ratingsContainer = (ViewGroup) findViewById(R.id.rl_ratingsContainer);
        iv_reportBusiness = (ImageView) findViewById(R.id.iv_reportBusiness);
        //   vp_banner=(ViewPager)findViewById(R.id.vp_banner);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        ll_info = (LinearLayout) findViewById(R.id.ll_info);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        rl_products = (RelativeLayout) findViewById(R.id.rl_products);
        rl_services = (RelativeLayout) findViewById(R.id.rl_services);
        rl_ratings = (RelativeLayout) findViewById(R.id.rl_ratings);
        iv_chatIcon = (ImageView) findViewById(R.id.iv_chatIcon);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        vp_banner = (ViewPager) findViewById(R.id.vp_banner);
        iv_businessIcon = (ImageView) findViewById(R.id.iv_businessIcon);
        tv_businessName = (TextView) findViewById(R.id.tv_businessName);
        tv_homeservice = (TextView) findViewById(R.id.tv_homeservice);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        iv_contact = (ImageView) findViewById(R.id.iv_contact);
        tv_toolbartitle = (TextView) findViewById(R.id.tv_toolbartitle);


        tv_ImageNo = (TextView) findViewById(R.id.tv_ImageNo);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_ratings = (TextView) findViewById(R.id.tv_ratings);
        iv_starIcon = (ImageView) findViewById(R.id.iv_starIcon);


        iv_ratingsDown = (ImageView) findViewById(R.id.iv_ratingsDown);
        iv_tag = (ImageView) findViewById(R.id.iv_tagIcon);
        iv_notification = (ImageView) findViewById(R.id.iv_notifyIcon);


        iv_seviceDown = (ImageView) findViewById(R.id.iv_seviceDown);
        iv_locationDown = (ImageView) findViewById(R.id.iv_locationDown);
        iv_productsDown = (ImageView) findViewById(R.id.iv_productsDown);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_mapIcon = (ImageView) findViewById(R.id.iv_fwdMapIcon);

        /*************************                           *******************************/
        ll_info.setVisibility(View.GONE);
        iv_reportBusiness.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rl_location.setOnClickListener(this);
        rl_products.setOnClickListener(this);
        rl_services.setOnClickListener(this);
        rl_ratings.setOnClickListener(this);
        iv_chatIcon.setOnClickListener(this);
        iv_banner.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        iv_contact.setOnClickListener(this);
        iv_mapIcon.setOnClickListener(this);
        iv_reportBusiness.setOnClickListener(this);

        // iv_chat icon visibility
        iv_chatIcon.setVisibility(View.INVISIBLE);


        setImageRatio();
        //    com.chatlocaly.Utill.screenSize(context);
    }

    public void setImageRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
        double height = displayMetrics.widthPixels * (0.523);
        int height1 = (int) height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vp_banner.getLayoutParams();
        params.height = height1;
        params.width = displayMetrics.widthPixels;

    }

    public void setViewPager() {
        pagerForBanner = new PagerForBanner(this, info.getBusinessImages());
        vp_banner.setAdapter(pagerForBanner);
        vp_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_ImageNo.setText(String.valueOf(position + 1) + "/" + info.getBusinessImages().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
           super.onCreateOptionsMenu(menu);
         // getMenuInflater().inflate(R.menu.menu_user_profile, menu);
          return true;
      }


      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          return super.onOptionsItemSelected(item);
      }

      */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_location:
                if (rl_address.getVisibility() == View.VISIBLE) {
                    rl_address.setVisibility(View.GONE);
                    iv_locationDown.setImageResource(R.mipmap.arrow_down);

                } else {
                    rl_address.setVisibility(View.VISIBLE);
                    tv_address.setText("" + info.getAddress());
                    iv_locationDown.setImageResource(R.mipmap.up_arrow);

                }
                break;
            case R.id.rl_products:
                if (info.getProducts().size() > 0) {
                    if (rl_productContainer.getChildCount() == 0) {


                        addProducts();
                    } else {



                 /*   scrollView.post(new Runnable() {
                        @Override
                        public void run() {

                              // scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                scrollView.smoothScrollTo(0, newViewRatings.getBottom());
                        }
                    });*/

                        rl_productContainer.removeView(newViewProducts);
                        iv_productsDown.setImageResource(R.mipmap.arrow_down);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), " No Products Uploaded", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.rl_services:
                if (info.getServices().size() > 0) {


                    if (rl_sericesContainer.getChildCount() == 0) {


                        addServices();
                    } else {
                        rl_sericesContainer.removeView(newViewServices);
                        iv_seviceDown.setImageResource(R.mipmap.arrow_down);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), " No Service Uploaded", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.iv_chatIcon:



                if (apicallStatus == 1 && channelKey != 0)
                    chatInitiate(channelKey);

                break;
            case R.id.iv_banner:
               /* Intent intent = new Intent(BusinessProfileActivity.this, BusinessBannerShowActivity.class);
                intent.putExtra(Constants.PRODUCT_SERIALIZABLE, info);
                startActivity(intent);*/
                break;

            case R.id.rl_ratings:
                if (rl_ratingsContainer.getChildCount() == 0) {


                    addRatingsBar();
                } else {
                    rl_ratingsContainer.removeView(newViewRatings);
                    iv_ratingsDown.setImageResource(R.mipmap.arrow_down);
                }
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.tv_contact:
                showDialog(context);
                break;
            case R.id.iv_contact:
                showDialog(context);
                break;
            case R.id.iv_fwdMapIcon:
                com.chatlocaly.Utill.callMapIntent(context, info.getAddress());
                break;
            case R.id.iv_reportBusiness:

                if (info.getReport_abuse_status().equalsIgnoreCase("0"))
                    reportToabuse();
                else
                    com.chatlocaly.Utill.showCenteredToast(context, getString(R.string.business_already_reported));
                break;

            case R.id.ib_arrow_left:


                break;

        }
    }

    public void editBusinessProfile() {
        PopupMenu popupMenu = new PopupMenu(BusinessProfileActivity.this, iv_chatIcon);

        popupMenu.getMenuInflater().inflate(R.menu.edit_business_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_editBusinessInfo:

                        break;
                    case R.id.action_editOverview:
                        //    Intent editbusinessintent=new Intent(BusinessProfileActivity.this,EditBusinessOverviewActivity.class);
                        //  startActivity(editbusinessintent);
                        break;
                    case R.id.action_AddProducts:
                        //  Intent addProductintent=new Intent(BusinessProfileActivity.this,AddProductActivity.class);
                        //   startActivity(addProductintent);
                        break;
                    case R.id.action_AddSevices:
                        //   Intent addserviceintent=new Intent(BusinessProfileActivity.this,AddServiceActivity.class);
                        //   startActivity(addserviceintent);
                        break;
                }
                return true;
            }
        });
        popupMenu.show(); //showing popup menu
    }

    private void addProducts() {
        // Instantiate a new "row" view.
        newViewProducts = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_product, rl_productContainer, false);
        setRecycleProducts();
        //   ib_arrowLeft=(ImageButton) newViewProducts.findViewById(R.id.ib_arrow_left);
        //  ib_arrowRight=(ImageButton) newViewProducts.findViewById(R.id.ib_arrow_right);

        rl_productContainer.addView(newViewProducts);
        iv_productsDown.setImageResource(R.mipmap.up_arrow);
        //  ib_arrowLeft.setOnClickListener(this);
        //  ib_arrowRight.setOnClickListener(this);

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //    scrollView.smoothScrollTo(0,ll_info.getBottom());
                //  scrollView.fullScroll(View.FOCUS_DOWN);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // y = sView.getScrollY();
                //    scrollView.smoothScrollTo(0, newViewRatings.getBottom());
            }
        }, 200);

    }

    private void addRatingsBar() {

        if (info.getRatingCount() == 0)

        {
            newViewRatings = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.no_rate, rl_ratingsContainer, false);
         /*   RatingBar ratingBar=newViewRatings.findViewById(R.id.rating_bar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(ratingBar.getContext().getResources().getColor(R.color.text_color_dark), PorterDuff.Mode.SRC_ATOP);

*/
        }



        else {
            newViewRatings = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.ratings_layout, rl_ratingsContainer, false);
            showRatingBar();
        }
        rl_ratingsContainer.addView(newViewRatings);
        iv_ratingsDown.setImageResource(R.mipmap.up_arrow);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //    scrollView.smoothScrollTo(0,ll_info.getBottom());
                //  scrollView.fullScroll(View.FOCUS_DOWN);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // y = sView.getScrollY();
                //    scrollView.smoothScrollTo(0, newViewRatings.getBottom());
            }
        }, 300);


    }

    public void showRatingBar() {


        iv_filledRating5 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating5);
        iv_filledRating4 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating4);
        iv_filledRating3 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating3);
        iv_filledRating2 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating2);
        iv_filledRating1 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating1);

        tv_noRatings5 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings5);
        tv_noRatings4 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings4);
        tv_noRatings3 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings3);
        tv_noRatings2 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings2);
        tv_noRatings1 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings1);
        tv_totalRatings = (TextView) newViewRatings.findViewById(R.id.tv_totalRatings);
        tv_avgRatings = (TextView) newViewRatings.findViewById(R.id.tv_avgRatings);

        tv_avgRatings.setText(info.getAvgRating());
        if (info.getRatingCount() == 1)
            tv_totalRatings.setText(info.getRatingCount() + " Rating");
        else
            tv_totalRatings.setText(info.getRatingCount() + " Ratings");
        try {
            setRatingPercentage();
            setRatingBars();
        } catch (Exception ex) {
        }
        tv_totalRatings.getX();
    }

    public void setRatingPercentage() {
        if (ratingArray != null)
            ratingArray.clear();

        ratingArray.add(Float.valueOf(info.getRatings().getOneStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getTwoStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getThreeStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getFourStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getFiveStar()));
        Collections.sort(ratingArray);
        max = ratingArray.get(4);

        ratingArray.clear();
        if (max > 0) {

            ratingArray.add(Float.valueOf((info.getRatings().getOneStar()) / max));
            ratingArray.add(Float.valueOf((info.getRatings().getTwoStar()) / max));
            ratingArray.add(Float.valueOf((info.getRatings().getThreeStar()) / max));
            ratingArray.add(Float.valueOf((info.getRatings().getFourStar()) / max));
            ratingArray.add(Float.valueOf((info.getRatings().getFiveStar()) / max));
        } else {
            ratingArray.add(0f);
            ratingArray.add(0f);
            ratingArray.add(0f);
            ratingArray.add(0f);
            ratingArray.add(0f);
        }
        tv_noRatings5.setText("(" + info.getRatings().getFiveStar() + ")");
        tv_noRatings4.setText("(" + info.getRatings().getFourStar() + ")");
        tv_noRatings3.setText("(" + info.getRatings().getThreeStar() + ")");
        tv_noRatings2.setText("(" + info.getRatings().getTwoStar() + ")");
        tv_noRatings1.setText("(" + info.getRatings().getOneStar() + ")");

        Log.e("aarrray", ratingArray + "");
    }

    public void setRatingBars() {
        LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) iv_filledRating5.getLayoutParams();
        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) iv_filledRating4.getLayoutParams();
        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) iv_filledRating3.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) iv_filledRating2.getLayoutParams();
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) iv_filledRating1.getLayoutParams();


        params5.weight = ratingArray.get(4);
     //  iv_filledRating5.setLayoutParams(params5);
        params4.weight = ratingArray.get(3);
       //  iv_filledRating4.setLayoutParams(params4);
        params3.weight = ratingArray.get(2);
       //  iv_filledRating3.setLayoutParams(params3);
        params2.weight = ratingArray.get(1);
      //   iv_filledRating2.setLayoutParams(params2);
        params1.weight = ratingArray.get(0);
       // iv_filledRating1.setLayoutParams(params1);
    }

    private void setRecycleProducts() {
        if (info != null) {
            if (info.getProducts().size() > 0) {
                rv_products = (RecyclerView) newViewProducts.findViewById(R.id.rv_products);
                layoutManagerProducts = new LinearLayoutManager(BusinessProfileActivity.this, LinearLayoutManager.HORIZONTAL, true);
                adapterProducts = new ProductsAdapter(context, info);
                rv_products.setLayoutManager(layoutManagerProducts);
                rv_products.setAdapter(adapterProducts);
                tv_viewAllProducts = newViewProducts.findViewById(R.id.tv_viewAllProducts);
                tv_viewAllProducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(BusinessProfileActivity.this, AllProductsActivity.class);
                        intent.putExtra(Constants.PRODUCT_SERIALIZABLE, (Serializable) info);
                        startActivity(intent);

                    }
                });


            } else
                Toast.makeText(getApplicationContext(), " No Products Uploaded", Toast.LENGTH_SHORT).show();


        }
    }

    private void addServices() {
        // Instantiate a new "row" view.
        newViewServices = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_services, rl_sericesContainer, false);
        setRecycleServices();

        rl_sericesContainer.addView(newViewServices);
        iv_seviceDown.setImageResource(R.mipmap.up_arrow);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //    scrollView.smoothScrollTo(0,ll_info.getBottom());
                //  scrollView.fullScroll(View.FOCUS_DOWN);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // y = sView.getScrollY();
                //    scrollView.smoothScrollTo(0, newViewRatings.getBottom());
            }
        }, 200);

    }

    private void setRecycleServices() {
        if (info != null) {
            if (info.getServices().size() > 0) {
                rv_service = (RecyclerView) newViewServices.findViewById(R.id.rv_service);
                layoutManagerservices = new LinearLayoutManager(BusinessProfileActivity.this, LinearLayoutManager.HORIZONTAL, true);
                adapterservices = new ServicesAdapter(BusinessProfileActivity.this, info);
                rv_service.setLayoutManager(layoutManagerservices);
                rv_service.setAdapter(adapterservices);
                tv_viewAllServices = (TextView) newViewServices.findViewById(R.id.tv_viewAllServices);
                tv_viewAllServices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BusinessProfileActivity.this, AllServiceActivity.class);
                        intent.putExtra(Constants.SERVICE_SERIALIZABLE, (Serializable) info);
                        startActivity(intent);
                    }
                });
            } else
                Toast.makeText(getApplicationContext(), " No Service Uploaded", Toast.LENGTH_SHORT).show();

        }
    }

    public void getBusinessDetails(final String b_id) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", preference.getEncryptKey());
        params.put("c_user_id", preference.getUserId());
        params.put("b_id", b_id);

        Call<BusinessProfileInfoModel> call = apiServices.getBusinessDetail(params);
        call.enqueue(new Callback<BusinessProfileInfoModel>() {
            @Override
            public void onResponse(Call<BusinessProfileInfoModel> call, Response<BusinessProfileInfoModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        info = response.body().getData().getBusinessDetail();
                        setViewPager();
                        if (!info.getBusinessBanner().equalsIgnoreCase(""))
                            Glide.with(BusinessProfileActivity.this).load(info.getBusinessBanner()).into(iv_banner);
                        if (!info.getBusinessLogo().equalsIgnoreCase(""))
                            Glide.with(BusinessProfileActivity.this).load(info.getBusinessLogo()).into(iv_businessIcon);
                        tv_businessName.setText(info.getBusinessName());
                        tv_contact.setText(info.getBMobileNumber());


                        tv_ImageNo.setText("1/" + info.getBusinessImages().size());
                        if(info.getBusinessImages().size()==1)
                            tv_ImageNo.setVisibility(View.GONE);
                        else
                            tv_ImageNo.setVisibility(View.VISIBLE);


                        if (info.getHomeServices().equalsIgnoreCase("YES")) {
                            tv_homeservice.setText("Home Service Upto " +Integer.parseInt(info.getServicesKm())+" Km ");
                            iv_home.setImageResource(R.mipmap.ishomeservice);

                        } else {
                            tv_homeservice.setText("No Home Service");
                            iv_home.setImageResource(R.mipmap.nohomeservice);

                        }

                        if (info.getRatingCount() == 0) {

                            tv_ratings.setText(getString(R.string.no_one_rating_yet));
                            iv_starIcon.setImageResource(R.mipmap.notrated);

                        } else {
                            tv_ratings.setText("" + info.getAvgRating() + " (" + info.getRatingCount() + "Ratings)");
                            iv_starIcon.setImageResource(R.mipmap.israted);

                        }


                        //  tv_toolbartitle.setText(" " + info.getBusinessName());
                        // imageview  notified or

                        if (info.getCustomer_tag() != null && !info.getCustomer_tag().equalsIgnoreCase("0"))
                            iv_tag.setImageResource(R.mipmap.istaged);
                        else
                            iv_tag.setImageResource(R.mipmap.nottaqed);

                        // imageview  notified

                        if (info.getCustomer_notification() != null && !info.getCustomer_notification().equalsIgnoreCase("1"))
                            iv_notification.setImageResource(R.mipmap.isnotified);
                        else
                            iv_notification.setImageResource(R.mipmap.notnotified);

                        ll_info.setVisibility(View.VISIBLE);


                        if(   !info.isIs_blocked()   )
                            // call api chat api
                            getbussinessGroupInfo(preference.getEncryptKey(), preference.getUserId(), b_id);


                    } else
                        Utill.showCenteredToast(context, response.body().getData().getMessage());


                }


            }

            @Override
            public void onFailure(Call<BusinessProfileInfoModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /***************************************                                 ********************/
// get bussiness info code
    public void getbussinessGroupInfo(String encrypt_key, final String c_user_id, String bussinessId) {
        apicallStatus = 0;
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //    final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        /*
        *
        *
        *
        *   get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4
        * */
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
/************************* **************************************/
        params.put("b_id", bussinessId);


        Call<BussinessGroupCreateModel> call = apiService.getGroupListInfo(params);
        call.enqueue(new Callback<BussinessGroupCreateModel>() {
            @Override
            public void onResponse(Call<BussinessGroupCreateModel> call, retrofit2.Response<BussinessGroupCreateModel> response) {
                //        pDialog.dismiss();
                BussinessGroupCreateModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        GroupInfoModel groupInfoModel = new GroupInfoModel();
                        groupInfoModel.setGroupId("" + clientModel.getData().getChatGroup().getChatGroupId());
                        groupInfoModel.setGroupName("" + clientModel.getData().getChatGroup().getChatGroupName());
                        chatGroupName = "" + clientModel.getData().getChatGroup().getChatGroupName();
                        // groupmemberid

                        if (clientModel.getData().getChatGroup() != null && clientModel.getData().getChatGroup().getChatGroupUsers() != null && clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker() != null && clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker().size() > 0) {
                            List<String> groupmenberID = new ArrayList<>();
                            for (BussinessGroupCreateModel.Datadata.ChatGroupdata.ChatGroupUsersdata.BusinessWorkerdata bussinessWorkerData : clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker()) {
                                groupmenberID.add(bussinessWorkerData.getBUserId());
                            }
                            groupInfoModel.setGroupMemberId(groupmenberID);
                        }
                        try {
                            // set admin of user
                            groupInfoModel.setAdmin(clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessAdmin().get(0).getBUserId());
                            groupInfoModel.setUserId(c_user_id);
                            groupInfoModel.setBussinessName(clientModel.getData().getChatGroup().getBusinessDetail().getBusinessName());
                            // create group code
                            createGroup(groupInfoModel);

                        } catch (Exception e) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);

                            com.chatlocaly.Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                        }

                    } else {

                        com.chatlocaly.Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BussinessGroupCreateModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                com.chatlocaly.Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                //   pDialog.dismiss();
            }
        });
    }

    // createGroup
    public void createGroup(final GroupInfoModel groupInfoModel) {

        //ApplozicBridge.launchIndividualGroupChat(context, "" + 28, "" + groupInfoModel.getGroupName(),groupInfoModel.getBussinessName());

        //   ApplozicBridge.launchIndividualChat(context,user_id,"ashok");


        if (com.chatlocaly.Utill.isRegisterdAtApplozic(context)) {


            final AlGroupInformationAsyncTask.GroupMemberListener channelInfoTask = new AlGroupInformationAsyncTask.GroupMemberListener() {
                @Override
                public void onSuccess(Channel channel, Context context) {
                    Log.i("Group ", "Details " + channel);

                    if (channel.getKey() == null) {
                        // logout user data
                        CreateGroup createGroup = new CreateGroup();
                        createGroup.execute(groupInfoModel);

                        //Logout success

                    } else {

                        iv_chatIcon.setVisibility(View.VISIBLE);
                        iv_reportBusiness.setVisibility(View.VISIBLE);

                        apicallStatus = 1;
                        channelKey = channel.getKey();
                         chatGroupId=groupInfoModel.getGroupId();

                        //  ApplozicBridge.launchIndividualGroupChat(context,""+channel.getKey(),info.getBId(),groupInfoModel.getGroupName(),info.getBusinessName());

                    }

                }

                @Override
                public void onFailure(Channel channel, Exception e, Context context) {

                    Log.i("Group ", "Exception " + channel);

                    if (channel == null) {
                        CreateGroup createGroup = new CreateGroup();
                        createGroup.execute(groupInfoModel);
                    }
                }
            };


            AlGroupInformationAsyncTask alGroupInformationAsyncTask = new AlGroupInformationAsyncTask(context, groupInfoModel.getGroupId(), channelInfoTask);
            alGroupInformationAsyncTask.execute();


        } else {
            Log.i("storeListActivity", "user not login");

        }
    }

    /***************************************  update rating of  user  *******************************/

    public void updateGroupInfoDetails(final String encrypt_key, final String c_user_id, final String chat_group_id, final String applozic_group_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
      /*http://192.168.0.60/chatlocaly/customer_api/update_chat_group_detail?encrypt_key=fgdfg&c_user_id=1
      &chat_group_id=54&
      applozic_group_id=8024675*/
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
/************************* **************************************/
        params.put("chat_group_id", chat_group_id);
        params.put("applozic_group_id", applozic_group_id);


        Call<ResultModel> call = apiService.sendUpdateChatgroupDetail(params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                pDialog.dismiss();
                ResultModel clientModel = response.body();
                counter++;

                if (clientModel != null && clientModel.getData() != null) {

                  //  com.chatlocaly.Utill.showCenteredToast(context, "channel key successfully ");
                } else {

                    if (counter < 3)
                        updateGroupInfoDetails(encrypt_key, c_user_id, chat_group_id, applozic_group_id);
                }

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                com.chatlocaly.Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

    public void chatInitiate(Integer channelKey) {

        ApplozicBridge.launchIndividualGroupChat(context, "" + channelKey, info.getBId(), chatGroupName, info.getBusinessName(), chatGroupId);

    }

    public void reportToabuse() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_report_to_abuse, null);
        final EditText reason = alertLayout.findViewById(R.id.edt_reason);
        final EditText description = alertLayout.findViewById(R.id.edt_description);

        final TextView tag = alertLayout.findViewById(R.id.tag);
        tag.setText("Report Business");
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("Report", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*String reasonbyuser = reason.getText().toString();
                String des = description.getText().toString();

                if(!TextUtils.isEmpty(reasonbyuser))
                {

                    sendReportToabuser(prefrence.getEncryptKey(),prefrence.getUserId(),info.getProductId(),reasonbyuser,des,Constant.REPORT_PRODUCT,dialog);

                }
                else {
                    reason.setError(getString(R.string.isfield));
                }*/
            }
        });
        final AlertDialog dialogshow = alert.create();
        dialogshow.show();


        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialogshow.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String reasonbyuser = reason.getText().toString();
                String des = description.getText().toString();



                if (!TextUtils.isEmpty(reasonbyuser) && reasonbyuser.length() > 6) {

                    if (TextUtils.isEmpty(des))

                        sendReportToabuser(preference.getEncryptKey(), preference.getUserId(), info.getBId(), reasonbyuser, des, Constant.REPORT_BUSINESS, dialogshow);

                    else {

                        if (!TextUtils.isEmpty(des) && des.length() > 15)
                            sendReportToabuser(preference.getEncryptKey(), preference.getUserId(), info.getBId(), reasonbyuser, des, Constant.REPORT_BUSINESS, dialogshow);
                        else
                            description.setError(getString(R.string.isdescription));

                    }

                } else {
                    reason.setError(getString(R.string.isfield));
                }




            }

        });


        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialogshow.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogshow.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });


    }

    public void sendReportToabuser(String encrypt_key, final String c_user_id, String product_id, final String reason, String report_comment, String report_abuse_type, final DialogInterface dialog) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
       /* http://192.168.0.60/chatlocaly/customer_api/add_report_abuse?encrypt_key=gfdg&
       c_user_id=2&report_comment=dfdsf&
        // report_abuse_type=product
      */
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("reason", reason);
        params.put("b_id", product_id);
        params.put("report_abuse_type", report_abuse_type);
        params.put("report_comment", report_comment);

        Call<ResultModel> call = apiService.sendReportToabuse(params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {

                ResultModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        info.setReport_abuse_status("1");
                        dialog.dismiss();
                        com.chatlocaly.Utill.showCenteredToast(context, getString(R.string.str_reporttoabuser));
                    } else {

                        com.chatlocaly.Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                if(str.contains(ApiClient.BASE_URL))
                    Utill.showCenteredToast(context,getString(R.string.str_check_internet_connection));

                // Log error here since request failed
                Log.e("", t.toString());
            }
        });
    }

    public void showDialog(final Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        //  alertDialogBuilder.setTitle("" + getString(R.string.app_name).toUpperCase());
        // alertDialogBuilder.setIcon(R.mipmap.logonew);


        alertDialogBuilder
                .setMessage("" + getString(R.string.str_call_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        com.chatlocaly.Utill.callIntent(context, "" + info.getBMobileNumber());

                    }
                })


                .setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rouncounerbox));

        // show it
        alertDialog.show();
        //    }
/*

        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setGravity(Gravity.CENTER);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
*/

    }

    public class CreateGroup extends AsyncTask<GroupInfoModel, Void, GroupInfoModel> {


        @Override
        protected GroupInfoModel doInBackground(GroupInfoModel... voids) {

            final GroupInfoModel infoModel = voids[0];
            createGroup(infoModel.getAdmin(), infoModel.getGroupMemberId(), infoModel.getUserId(), infoModel.getGroupName(), infoModel.getGroupId(), context, infoModel.getBussinessName());
            return infoModel;
        }

        @Override
        protected void onPostExecute(GroupInfoModel groupInfo) {
            super.onPostExecute(groupInfo);


        }

        public void createGroup(final String admin, final List<String> groupmemberId, final String user_id, final String groupname, final String groupID, Context context, final String bussinessName) {


            AlChannelCreateAsyncTask.TaskListenerInterface taskListenerInterface = new AlChannelCreateAsyncTask.TaskListenerInterface() {
                @Override
                public void onSuccess(final Channel channel, Context context) {
                    Log.e("group", "success");
                    iv_chatIcon.setVisibility(View.VISIBLE);
                    iv_reportBusiness.setVisibility(View.VISIBLE);

                    apicallStatus = 1;
                    channelKey = channel.getKey();
                    chatGroupId=groupID;

                    updateGroupInfoDetails(preference.getEncryptKey(), preference.getUserId(), groupID, "" + channelKey);
                    // ApplozicBridge.launchIndividualGroupChat(context,""+channel.getKey(),info.getBId(),groupname,info.getBusinessName());

                }

                @Override
                public void onFailure(ChannelFeedApiResponse channelFeedApiResponse, Context context) {
                    Log.e("group", "fail");

                }
            };

            List<String> channelMembersList = new ArrayList<String>();
            if (groupmemberId != null) {
                for (String addgroupmember : groupmemberId)
                    channelMembersList.add(com.chatlocaly.Utill.getApplozicGroupMemberId(addgroupmember));//Note:while creating group exclude logged in userId from list
            }
            channelMembersList.add(com.chatlocaly.Utill.getApplozicUserId(user_id));//Note:while creating group exclude logged in userId from list
            ChannelInfo channelInfo = new ChannelInfo(groupname, channelMembersList);
            channelInfo.setType(Channel.GroupType.PRIVATE.getValue().intValue());

            //group type
            //channelInfo.setImageUrl(""); //pass group image link URL
            //channelInfo.setChannelMetadata(channelMetadata); //Optional option for setting group meta data
            channelInfo.setClientGroupId(groupID); //Optional if you have your own groupId then you can pass here
            channelInfo.setAdmin(com.chatlocaly.Utill.getApplozicGroupMemberId(admin));
            // old code
            // Channel channel = ChannelService.getInstance(context).createChannel(channelInfo);
            // Log.e(" group id",channel.getClientGroupId());
            AlChannelCreateAsyncTask alChannelCreateAsyncTask = new AlChannelCreateAsyncTask(context, channelInfo, taskListenerInterface);

            alChannelCreateAsyncTask.execute((Void) null);


        }


    }

    private class PagerForBanner extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        List<BusinessProfileInfoModel.Datadata.BusinessDetaildata.BusinessImagesdata> businessImages;

        public PagerForBanner(Context context, List<BusinessProfileInfoModel.Datadata.BusinessDetaildata.BusinessImagesdata> businessImages) {
            mContext = context;
            this.businessImages = businessImages;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            if (businessImages != null)
                return businessImages.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {


            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = mLayoutInflater.inflate(R.layout.product_mage_pager, container, false);

            final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            file:///storage/emulated/0/cropped1517381496911.jpg

            Glide.with(mContext).load(businessImages.get(position).getImageName()).into(imageView);

//        imageView.setImageURI(Uri.parse("file://"+filePathList.get(position)));
            container.addView(itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BusinessProfileActivity.this, BusinessBannerShowActivity.class);
                    intent.putExtra(Constants.PRODUCT_SERIALIZABLE, info);
                    intent.putExtra(Constants.POSITION, position);
                    startActivity(intent);
                }
            });

            return itemView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    ;


}
