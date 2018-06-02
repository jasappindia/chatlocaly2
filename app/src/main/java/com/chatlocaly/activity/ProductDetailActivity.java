package com.chatlocaly.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.ProductListModel;
import com.chatlocaly.model.ProductModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by windows on 1/17/2018.
 */

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {


    String groupId, businessName, groupName, businessId;
    LinearLayout linearLayout;
    View view;
    int counter = 0;
    private ViewPager viewPager;
    private ProductImageAdapter productImageAdapter;
    private ImageView iv_edit, iv_share, iv_homeButton;
    private TextView tv_businessName, tv_proPrice_discount, tv_proPrice, tv_discription, tv_productName, tv_discountTag;
    private Utill utill;
    private Chatlocalyshareprefrence prefrence;
    private ProductListModel.productdata.ProductListdata info;
    private int position;
    private ImageButton ib_share, ib_chat, ib_flag, ib_info, ib_product_add_to_chat;
    private LinearLayout ll_share;
    private Context context;
    private boolean is_getChannelKey = false;
    private Integer channelKey = 0;
    private ImageView productImage;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private String chatGroupId;

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproduct);
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(this);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.sv_scrollView);

        init();
        if (!Utill.isConnectingToInternet(context))
            progressBar.setVisibility(View.VISIBLE);

        tv_businessName.setText("" );

        scrollView.setVisibility(View.INVISIBLE);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.containsKey(Constant.PRODUCT_KEY)) {


                if(bundle.getString(Constant.PRODUCT_KEY)!=null)
                {
                    String productId=bundle.getString(Constant.PRODUCT_KEY);
                    getProductDetails(chatlocalyshareprefrence.getEncryptKey(),chatlocalyshareprefrence.getUserId(),productId);
                }
                else
                {

                    Utill.showCenteredToast(context,getString(R.string.something_went_wrong));
                }


            } else {

                info = (ProductListModel.productdata.ProductListdata) bundle.getSerializable(Constants.PRODUCT_DETAIL);
                businessId = info.getBId();

                //

                tv_businessName.setText("" + info.getBusiness_name());
                tv_discription.setText(info.getDescription());
                tv_productName.setText(info.getProductName());
                if (info.getDiscount().equals("0")) {

                    tv_discountTag.setVisibility(View.GONE);
                    tv_proPrice.setText(info.getPrice());
                    tv_proPrice_discount.setVisibility(View.GONE);

                } else {
                    tv_discountTag.setVisibility(View.VISIBLE);
                    tv_discountTag.setText(info.getDiscount() + "% off");
                    tv_proPrice.setText("Rs " + Utill.getPercentPrice(Double.parseDouble("" + info.getPrice()), Double.parseDouble(info.getDiscount())));
                    tv_proPrice_discount.setText(" " + info.getPrice());
                    tv_proPrice_discount.setPaintFlags(tv_proPrice_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                }
                /**********************                   ****************/

                linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                productImage = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                productImage.setLayoutParams(params);
                linearLayout.addView(productImage);
                Glide.with(context).load(info.getProductImage()).into(productImage);



                if (Utill.isConnectingToInternet(context))

                    getbussinessGroupInfo(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), businessId);
                else
                    Utill.showCenteredToast(context, getString(R.string.no_internet_connected));


                productImageAdapter = new ProductImageAdapter(ProductDetailActivity.this, info);
                viewPager.setAdapter(productImageAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
                tabLayout.setupWithViewPager(viewPager, true);
                init();
                setImageRatio();

            }
        }
        // check multiple device login
        new BasicUtill().CheckStatus(this);
/****************    onclick handle     *************************/
        iv_edit.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        ib_chat.setOnClickListener(this);
        ib_share.setOnClickListener(this);
        ib_info.setOnClickListener(this);
        ib_flag.setOnClickListener(this);
        ib_product_add_to_chat.setOnClickListener(this);
        iv_homeButton.setOnClickListener(this);



    }

    public void setImageRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
        double height = displayMetrics.widthPixels * (0.523);
        int height1 = (int) height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.height = height1;
        params.width = displayMetrics.widthPixels;

    }

    public void init() {
        utill = new Utill();
        prefrence = new Chatlocalyshareprefrence(ProductDetailActivity.this);
        context = this;
        productImage = new ImageView(context);
        tv_discription = (TextView) findViewById(R.id.tv_discription);
        tv_proPrice = (TextView) findViewById(R.id.tv_proPrice);
        tv_productName = (TextView) findViewById(R.id.tv_productName);
        tv_businessName = (TextView) findViewById(R.id.tv_businessName);
        tv_discountTag = (TextView) findViewById(R.id.tv_discountTag);
        tv_proPrice_discount = (TextView) findViewById(R.id.tv_proPrice_discount);
        iv_homeButton = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        /*****************************                     **********************/
        ib_flag = (ImageButton) findViewById(R.id.ib_flag);
        ib_share = (ImageButton) findViewById(R.id.ib_share);
        ib_chat = (ImageButton) findViewById(R.id.ib_chat);
        ib_info = (ImageButton) findViewById(R.id.ib_info);
        ib_product_add_to_chat = (ImageButton) findViewById(R.id.ib_product_add_to_chat);


       /* tv_businessName.setText("" + info.getBusiness_name());
        tv_discription.setText(info.getDescription());
        tv_productName.setText(info.getProductName());
        if (info.getDiscount().equals("0")) {

            tv_discountTag.setVisibility(View.GONE);
            tv_proPrice.setText(info.getPrice());
            tv_proPrice_discount.setVisibility(View.GONE);

        } else {
            tv_discountTag.setVisibility(View.VISIBLE);
            tv_discountTag.setText(info.getDiscount() + "% off");
            tv_proPrice.setText("Rs " + Utill.getPercentPrice(Double.parseDouble("" + info.getPrice()), Double.parseDouble(info.getDiscount())));
            tv_proPrice_discount.setText(" " + info.getPrice());
            tv_proPrice_discount.setPaintFlags(tv_proPrice_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }
        *//**********************                   ****************//*

        linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        productImage = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
        productImage.setLayoutParams(params);
        linearLayout.addView(productImage);
        Glide.with(context).load(info.getProductImage()).into(productImage);


        *//****************    onclick handle     *************************//*
        iv_edit.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        ib_chat.setOnClickListener(this);
        ib_share.setOnClickListener(this);
        ib_info.setOnClickListener(this);
        ib_flag.setOnClickListener(this);
        ib_product_add_to_chat.setOnClickListener(this);
        iv_homeButton.setOnClickListener(this);
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.ib_chat:

                if (!info.isIs_blocked()) {
                    if (channelKey != 0)

                        chatInitiate(channelKey);
                } else {

                    if (info.getBlock_side().equalsIgnoreCase("customer") || info.getBlock_side().equalsIgnoreCase("")) {


                        Utill.showCenteredToast(context, "Sorry! you can't chat because " + "you have blocked " + info.getBusiness_name());
                    } else {

                        Utill.showCenteredToast(context, "Sorry! you can't chat because " + info.getBusiness_name() + " have blocked you");

                    }


                }


                break;
            case R.id.ib_share:
                    shareImageIntent(this.view, info.getProductName());


                break;
            case R.id.ib_info:
                Intent profile = new Intent(context, BusinessProfileActivity.class);
                //   profile.putExtra(BusinessProfileActivity.BUSSINESS_PROFILE_INFO, (Serializable) businessListData);
                profile.putExtra(BusinessProfileActivity.BUSSINESS_ID,  info.getProductName()+"  "+businessId);
                context.startActivity(profile);
                break;
            case R.id.ib_flag:

                if (info.getReport_abuse_status().equalsIgnoreCase("0"))
                    reportToabuse();
                else
                    Utill.showCenteredToast(context, getString(R.string.product_already_reported));
                break;
            case R.id.ib_product_add_to_chat:


                if (!info.isIs_blocked())
                    shareImage(this.view, info.getProductName(), channelKey);

                else {

                    if (info.getBlock_side().equalsIgnoreCase("customer") || info.getBlock_side().equalsIgnoreCase("")) {


                        Utill.showCenteredToast(context, "Sorry! you can't chat because " + "you have blocked " + info.getBusiness_name());
                    } else {

                        Utill.showCenteredToast(context, "Sorry! you can't chat because " + info.getBusiness_name() + " have blocked you");

                    }


                    break;

                }
        }
    }

    public void share_bitMap_to_Apps() {

        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("image*//*");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        i.putExtra(Intent.EXTRA_STREAM, getImageUri(ProductDetailActivity.this, getBitmapFromView(ll_share)));
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."));
        } catch (android.content.ActivityNotFoundException ex) {

            ex.printStackTrace();
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void shareImage(View viewGroup, String product_name, Integer channelKey) {
        viewGroup.setBackgroundColor(getResources().getColor(R.color.white));

        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = context.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        viewGroup.setDrawingCacheEnabled(false);
        //now send it out to share
        ApplozicBridge.sendCustomMessage(context, product_name + " Find this product on ChatLocaly ", channelKey, sharefile.getAbsolutePath(), Constant.PRODUCT_KEY, info.getProductId());
        Utill.showCenteredToast(context, "Product enquiry sent");

        //chatInitiate(channelKey);
    }

    public void chatInitiate(Integer channelKey) {

        ApplozicBridge.launchIndividualGroupChat(context, "" + channelKey, businessId, groupName, businessName, chatGroupId);

    }

    public void reportToabuse() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_report_to_abuse, null);
        final EditText reason = alertLayout.findViewById(R.id.edt_reason);
        final EditText description = alertLayout.findViewById(R.id.edt_description);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);
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


                String reasonbyuser = reason.getText().toString().trim();
                String des = description.getText().toString().trim();

                if (!TextUtils.isEmpty(reasonbyuser) && reasonbyuser.length() > 6) {

                    if (TextUtils.isEmpty(des))

                        sendReportToabuser(prefrence.getEncryptKey(), prefrence.getUserId(), info.getProductId(), reasonbyuser, des, Constant.REPORT_PRODUCT, dialogshow);

                    else {

                        if (!TextUtils.isEmpty(des) && des.length() > 15)
                            sendReportToabuser(prefrence.getEncryptKey(), prefrence.getUserId(), info.getProductId(), reasonbyuser, des, Constant.REPORT_PRODUCT, dialogshow);
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

    /************************************                         **********************************/


    public void shareImageIntent(View viewGroup,String name) {
        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = context.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
/*
        //  create image    from bit map
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("image", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

*/

        // Toast.makeText(context, "Saved in Gallery", Toast.LENGTH_SHORT).show();
        viewGroup.setDrawingCacheEnabled(false);
        //now send it out to share
        Intent share = new Intent(Intent.ACTION_SEND);
        Log.e("image path", Uri.fromFile(sharefile).toString());
        share.setType("*/*");

        share.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, name+"  Find this product on ChatLocaly " + "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName());

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sharefile));

        context.startActivity(Intent.createChooser(share, getString(R.string.app_name)));
        viewGroup.setDrawingCacheEnabled(false);

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
        params.put("product_id", product_id);
        params.put("report_abuse_type", report_abuse_type);
        params.put("report_comment", report_comment);

        Call<ResultModel> call = apiService.sendReportToabuse(params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {

                ResultModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        dialog.dismiss();
                        info.setReport_abuse_status("1");
                        Utill.showCenteredToast(context, getString(R.string.str_reporttoabuser_product));
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
            }
        });
    }

    /***************************************                                 ********************/
// get bussiness info code
    public void getbussinessGroupInfo(String encrypt_key, final String c_user_id, String bussinessId) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //  final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        /*

        *
        *
        *
        *   get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4
        * */
        params.put("encrypt_key", encrypt_key);
/************************* **************************************/
        params.put("b_id", bussinessId);


        Call<BussinessGroupCreateModel> call = apiService.getGroupListInfo(params);
        call.enqueue(new Callback<BussinessGroupCreateModel>() {
            @Override
            public void onResponse(Call<BussinessGroupCreateModel> call, retrofit2.Response<BussinessGroupCreateModel> response) {
                // pDialog.dismiss();

                BussinessGroupCreateModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        GroupInfoModel groupInfoModel = new GroupInfoModel();
                        groupInfoModel.setGroupId("" + clientModel.getData().getChatGroup().getChatGroupId());
                        groupInfoModel.setGroupName("" + clientModel.getData().getChatGroup().getChatGroupName());
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
                            Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                        }


                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BussinessGroupCreateModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                //   pDialog.dismiss();
            }
        });
    }


































  //  get_product_details?encrypt_key=sad&product_id=100
    public void getProductDetails(String encrypt_key, final String c_user_id, String productId) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //  final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        /*

        *
        *
        *
        *   get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4
        * */
        params.put("encrypt_key", encrypt_key);
/************************* **************************************/
        params.put("product_id", productId);


        Call<ProductModel> call = apiService.getProductDetails(params);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, retrofit2.Response<ProductModel> response) {
                // pDialog.dismiss();

                ProductModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        if(clientModel.getData().getBusinessDetail()!=null  && clientModel.getData().getBusinessDetail().get(0)!=null )
                         info=clientModel.getData().getBusinessDetail().get(0);
                          businessId = info.getBId();
                        if (Utill.isConnectingToInternet(context))

                            getbussinessGroupInfo(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), businessId);
                        else
                            Utill.showCenteredToast(context, getString(R.string.no_internet_connected));



                        tv_businessName.setText("" + info.getBusiness_name());
                        tv_discription.setText(info.getDescription());
                        tv_productName.setText(info.getProductName());
                        if (info.getDiscount().equals("0")) {

                            tv_discountTag.setVisibility(View.GONE);
                            tv_proPrice.setText(info.getPrice());
                            tv_proPrice_discount.setVisibility(View.GONE);

                        } else {
                            tv_discountTag.setVisibility(View.VISIBLE);
                            tv_discountTag.setText(info.getDiscount() + "% off");
                            tv_proPrice.setText("Rs " + Utill.getPercentPrice(Double.parseDouble("" + info.getPrice()), Double.parseDouble(info.getDiscount())));
                            tv_proPrice_discount.setText(" " + info.getPrice());
                            tv_proPrice_discount.setPaintFlags(tv_proPrice_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                        }
                        /**********************                   ****************/

                        linearLayout = new LinearLayout(context);
                        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        productImage = new ImageView(context);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                        productImage.setLayoutParams(params);
                        linearLayout.addView(productImage);
                        Glide.with(context).load(info.getProductImage()).into(productImage);







                        productImageAdapter = new ProductImageAdapter(ProductDetailActivity.this, info);
                        viewPager.setAdapter(productImageAdapter);

                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
                        tabLayout.setupWithViewPager(viewPager, true);
                        init();
                        setImageRatio();

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
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


        if (Utill.isRegisterdAtApplozic(context)) {


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

                        businessName = groupInfoModel.getBussinessName();
                        groupId = groupInfoModel.getGroupId();
                        groupName = groupInfoModel.groupName;
                        channelKey = channel.getKey();
                        chatGroupId = groupId;
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);


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
        //      final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
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
                //      pDialog.dismiss();
                ResultModel clientModel = response.body();
                counter++;

                if (clientModel != null && clientModel.getData() != null) {

                 //   Utill.showCenteredToast(context, "channel key successfully ");
                } else {

                    if (counter < 3)
                        updateGroupInfoDetails(encrypt_key, c_user_id, chat_group_id, applozic_group_id);
                }

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                //\


                // pDialog.dismiss();
            }
        });
    }

    private class ProductImageAdapter extends PagerAdapter {


        Context mContext;
        LayoutInflater mLayoutInflater;
        List<ProductListModel.productdata.ProductListdata.ProductImage> imageList;

        public ProductImageAdapter(Context context, ProductListModel.productdata.ProductListdata info) {

            mContext = context;
            this.imageList = info.getProductImages();
//            this.businessImageList= info.getBusinessImages();
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item_tuchimageview, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_product_image);

            Glide.with(mContext).load(imageList.get(position).getProductImage()).into(imageView);
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

                    // update channel key
                    updateGroupInfoDetails(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), groupID, "" + channel.getKey());
                    businessName = bussinessName;
                    groupId = groupID;
                    groupName = groupname;
                    channelKey = channel.getKey();
                    chatGroupId = groupID;
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);


                }

                @Override
                public void onFailure(ChannelFeedApiResponse channelFeedApiResponse, Context context) {
                    Log.e("group", "fail");

                }
            };

            List<String> channelMembersList = new ArrayList<String>();
            if (groupmemberId != null) {
                for (String addgroupmember : groupmemberId)
                    channelMembersList.add(Utill.getApplozicGroupMemberId(addgroupmember));//Note:while creating group exclude logged in userId from list
            }
            channelMembersList.add(Utill.getApplozicUserId(user_id));//Note:while creating group exclude logged in userId from list
            ChannelInfo channelInfo = new ChannelInfo(groupname, channelMembersList);
            channelInfo.setType(Channel.GroupType.PRIVATE.getValue().intValue());

            //group type
            //channelInfo.setImageUrl(""); //pass group image link URL
            //channelInfo.setChannelMetadata(channelMetadata); //Optional option for setting group meta data
            channelInfo.setClientGroupId(groupID); //Optional if you have your own groupId then you can pass here
            channelInfo.setAdmin(Utill.getApplozicGroupMemberId(admin));
            // old code
            // Channel channel = ChannelService.getInstance(context).createChannel(channelInfo);
            // Log.e(" group id",channel.getClientGroupId());
            AlChannelCreateAsyncTask alChannelCreateAsyncTask = new AlChannelCreateAsyncTask(context, channelInfo, taskListenerInterface);
            alChannelCreateAsyncTask.execute((Void) null);


        }


    }


}
