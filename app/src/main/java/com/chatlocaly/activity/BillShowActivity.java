package com.chatlocaly.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ChannelCreateActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.applozic.mobicommons.commons.image.ImageCache;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocaly.R;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.firebasenotification.MyFirebaseMessagingService;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.InvoiceOrderListModel;
import com.chatlocaly.model.Order_Details;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;
import com.chatlocaly.utill.Utill;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chatlocaly.Utill.getBitmapFromView;
import static com.chatlocaly.Utill.order_token;

/**
 * Created by windows on 4/4/2018.
 */

public class BillShowActivity extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {


    public static int billDelete = 0;
    public static int paymentStatus = 0;
    LinearLayout ll_billDate, ll_dueDate, ll_confirmation;
    RelativeLayout ll_confirmation_code, rl_showrate;
    double totalAmount = 0;
    Chatlocalyshareprefrence preference;
    RelativeLayout rl_bill;
    InvoiceOrderListModel.OrderList order;
    String orderid;
    int callApi = 0;
    private RecyclerView rv_bill;
    private AdapterForBillShow adapterForBillShow;
    private LinearLayoutManager layoutManager;
    private ImageView iv_businessIcon, iv_chatIcon;
    private TextView tv_businessName, tv_buyerName, tv_contact, tv_billNo, tv_billType,
            tv_BillDate, tv_dueDate, tv_finalAmount, tv_sendBill, tv_submit;
    private ImageCache imageCache;
    private ProgressBar progressBar;
    private RelativeLayout rl_chat_bill,rl_invoice;
    private ImageView iv_arrowBack, iv_resendIcon;
    private TextView tv_paymentStatus, tv_billNumber, tv_paid_code, tv_billTotal, tv_billDate, tv_paidate, tv_customerName, tv_ratecount, tv_showediterate;
    private RecyclerView rv_sentList;
    private LinearLayoutManager linearLayoutManager;
    private InvoiceSentListAdapter sentListAdapter;
    private AlertDialog alertdialog;
    private EditText et_confirmationCode;
    private LinearLayout ll_bill;
    private int apicallStatus = 0, channelKey = 0;
    private Context context;
    private String chatGroupName = "";
    private int counter = 0;
    private ImageView iv_sendConfermetionCode;
    private RatingBar businessRating, showRating;
    private float ratingValue = 0;
    private String comment = "";
    private RelativeLayout rl_rate;
    private int iseditable = 0;
    private String chatGroupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billshow);
        init();

        // check multiple device login
        new BasicUtill().CheckStatus(this);

        getValues();

    }

    private void getValues() {


        if (getIntent().getExtras() != null) {


            if (getIntent().hasExtra(DetailedConversationAdapter.BILL_ORDER_ID)) {
                ll_bill.setVisibility(View.GONE);
                //get_order_detail?encrypt_key=sdfsd&c_user_id=25&order_id=1

                String order_id = getIntent().getExtras().getString(DetailedConversationAdapter.BILL_ORDER_ID);

                getOrderDetails(order_id);

            } else {

                order = (InvoiceOrderListModel.OrderList) getIntent().getExtras().getSerializable(Constants.ORDER_DETAILS);
                if (order != null) {

                    setRv_bill();
                    setBillDetails(order);
                    setswitches();
                    setInvoiceRecyclerView(order.getSendOrders());
                    setBuyerINfo();
                    // set paid code
                    if (order.getOrderStatus().equalsIgnoreCase("unpaid")) {
                        ll_confirmation_code.setVisibility(View.VISIBLE);
                        tv_paid_code.setText(" " + order.getOrderToken());
                        rl_rate.setVisibility(View.GONE);


                    } else {
                        ll_confirmation_code.setVisibility(View.GONE);
                        if (order.getBusiness_rating().getRatingFlag().equalsIgnoreCase("NO")) {
                            rl_rate.setVisibility(View.VISIBLE);
                            iseditable = 0;
                            tv_submit.setText("Submit");
                        } else {
/*

                            rl_rate.setVisibility(View.VISIBLE);
                            iseditable = 1;
                            tv_submit.setText("Edit");
                            businessRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));

*/

                            rl_rate.setVisibility(View.GONE);
                            rl_showrate.setVisibility(View.VISIBLE);
                            if (Integer.parseInt(order.getBusiness_rating().getRating()) == 1)
                                tv_ratecount.setText(" You rated " + Integer.parseInt(order.getBusiness_rating().getRating()) + " star");
                            else
                                tv_ratecount.setText(" You rated " + Integer.parseInt(order.getBusiness_rating().getRating()) + " stars");

                                 /*   rl_rate.setVisibility(View.VISIBLE);
                                    iseditable=1;
                                    tv_submit.setText("Edit");
                                    businessRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));
*/
                            showRating.setNumStars(Integer.parseInt(order.getBusiness_rating().getRating()));
                            showRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));
                            LayerDrawable stars1 = (LayerDrawable) showRating.getProgressDrawable();
                            stars1.getDrawable(2).setColorFilter(showRating.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


                        }

                    }


                }

                if(!order.isIs_blocked())
                getbussinessGroupInfo(preference.getEncryptKey(), preference.getUserId(), order.getBId());

            }
        }

    }

    private void init() {

        context = this;
        preference = new Chatlocalyshareprefrence(this);
        ll_confirmation = (LinearLayout) findViewById(R.id.ll_confirmation);
        ll_billDate = (LinearLayout) findViewById(R.id.ll_billDate);
        ll_dueDate = (LinearLayout) findViewById(R.id.ll_dueDate);
        ll_confirmation_code = (RelativeLayout) findViewById(R.id.ll_confirmation_code);
        rl_invoice=(RelativeLayout) findViewById(R.id.rl_invoice);
        businessRating = (RatingBar) findViewById(R.id.rating_bar);
        rl_showrate = (RelativeLayout) findViewById(R.id.rl_businessRateShow);
        showRating = findViewById(R.id.show_rating_bar);
        tv_ratecount = findViewById(R.id.tv_show_sub_title);
        tv_showediterate = findViewById(R.id.tv_show_submitRate);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_businessIcon = (ImageView) findViewById(R.id.iv_businessIcon);
        tv_businessName = (TextView) findViewById(R.id.tv_businessName);
        tv_buyerName = (TextView) findViewById(R.id.tv_buyerName);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_billNo = (TextView) findViewById(R.id.tv_billNo);
        tv_billType = (TextView) findViewById(R.id.tv_billType);
        tv_BillDate = (TextView) findViewById(R.id.tv_BillDate);
        tv_dueDate = (TextView) findViewById(R.id.tv_dueDate);
        tv_finalAmount = (TextView) findViewById(R.id.tv_finalAmount);
        et_confirmationCode = (EditText) findViewById(R.id.et_confirmationCode);
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);

        rl_rate = (RelativeLayout) findViewById(R.id.rl_businessRate);

        ll_bill = (LinearLayout) findViewById(R.id.ll_bill);
        rv_sentList = (RecyclerView) findViewById(R.id.rv_sentList);
        rl_chat_bill = (RelativeLayout) findViewById(R.id.rl_chat_bill);
        tv_submit = (TextView) findViewById(R.id.tv_submitRate);
        iv_chatIcon = (ImageView) findViewById(R.id.iv_chat);
        iv_sendConfermetionCode = (ImageView) findViewById(R.id.iv_sendConfermetionCode);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_resendIcon = (ImageView) findViewById(R.id.iv_resendIcon);
        tv_paymentStatus = (TextView) findViewById(R.id.tv_paymentStatus);
        tv_billNumber = (TextView) findViewById(R.id.tv_billNumber);
        tv_billTotal = (TextView) findViewById(R.id.tv_billTotal);
        tv_billDate = (TextView) findViewById(R.id.tv_billDate);
        tv_paidate = (TextView) findViewById(R.id.tv_paidate);
        tv_customerName = (TextView) findViewById(R.id.tv_customerName);
        tv_paid_code = (TextView) findViewById(R.id.tv_paidCode);

        iv_resendIcon.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_chatIcon.setOnClickListener(this);
        iv_chatIcon.setVisibility(View.INVISIBLE);
        businessRating.setOnRatingBarChangeListener(this);
        tv_submit.setOnClickListener(this);
        tv_showediterate.setOnClickListener(this);


        iv_sendConfermetionCode.setOnClickListener(this);
        // rating icon change


        LayerDrawable stars = (LayerDrawable) businessRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(businessRating.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        LayerDrawable stars1 = (LayerDrawable) showRating.getProgressDrawable();
        stars1.getDrawable(2).setColorFilter(showRating.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

//        BasicUtill.getImageUri(InvoiceActivity.this, BasicUtill.getBitmapFromView(rl_bill));
    }

    public void setswitches() {
        if (order.getShowBillDate().equals("1")) {
            ll_billDate.setVisibility(View.VISIBLE);
            tv_BillDate.setText(order.getBillDate());
        } else ll_billDate.setVisibility(View.GONE);

        if (order.getShowBillDueDate().equals("1")) {
            ll_dueDate.setVisibility(View.VISIBLE);
            tv_dueDate.setText(order.getBillDueDate());
        } else ll_dueDate.setVisibility(View.GONE);

        if (order.getUseIcon().equals("1")) {
            iv_businessIcon.setVisibility(View.VISIBLE);
            Utill.imageShow(context, iv_businessIcon, order.getBusinessLogo());
        } else iv_businessIcon.setVisibility(View.GONE);



/*
        if (order.getUseLocation().equals("1")) {

        } else
*/

    }

/*
    public void shareImage(View viewGroup, String confirmationCode, int billOrderID) {

        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = getBitmapFromView(viewGroup);
//        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = this.getApplicationContext().getExternalCacheDir();
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
        String messgae = "Please click to see bill in detail";
//        ApplozicBridge.sendCustomMessage(BillShowActivity.this,messgae, chatGroup ,sharefile.getAbsolutePath(),confirmationCode,String.valueOf(billOrderID));
//        ApplozicBridge.launchIndividualGroupChat(this,String.valueOf(messageList.get(getAdapterPosition()).getGroupId()),
//                preference.getUserId(),messageList.get(getAdapterPosition()).getFullName(), "0C"+messageList.get(getAdapterPosition()).getCUserId(),
//                messageList.get(getAdapterPosition()).getChatGroupId());

    }*/

    public void setBuyerINfo() {
     /*   if (order.getOrderStatus().equalsIgnoreCase("paid"))
            ll_confirmation.setVisibility(View.GONE);
        else ll_confirmation.setVisibility(View.VISIBLE);
   */
        tv_businessName.setText(Utill.capitazileword(order.getBusinessName()));
        tv_buyerName.setText(order.getBuyerName());
        tv_contact.setText(order.getBuyerMobile());
        tv_billNo.setText(order.getOrderId());
        tv_finalAmount.setText("Total: Rs " + order.getTotal() + "");
        tv_BillDate.setText(order.getBillDate());
        tv_dueDate.setText(order.getBillDueDate());
    }

    public void setRv_bill() {
        rv_bill = (RecyclerView) findViewById(R.id.rv_bill);
        adapterForBillShow = new AdapterForBillShow(BillShowActivity.this, order);
        layoutManager = new LinearLayoutManager(BillShowActivity.this);
        rv_bill.setLayoutManager(layoutManager);
        rv_bill.setAdapter(adapterForBillShow);

    }

    public void setInvoiceRecyclerView(List<InvoiceOrderListModel.SendOrder> sendOrders) {
        linearLayoutManager = new LinearLayoutManager(this);
        sentListAdapter = new InvoiceSentListAdapter(this, sendOrders);
        rv_sentList.setAdapter(sentListAdapter);
        rv_sentList.setLayoutManager(linearLayoutManager);


    }

    public void setBillDetails(InvoiceOrderListModel.OrderList orderDetail) {
        tv_customerName.setText(orderDetail.getBusinessName());
        //   tv_paymentStatus.setText("Payment Status : " + orderDetail.getOrderStatus());
        tv_billNumber.setText("Bill No : " + orderDetail.getOrderId());
        tv_billTotal.setText("Bill Total : Rs " + orderDetail.getTotal() + "");
        tv_billDate.setText("Bill Date : " + orderDetail.getBillDate());
        if (orderDetail.getOrderStatus().equalsIgnoreCase("paid")) {

          /*  String styledText = "Payment Status : " + " <font color='green'>" + orderDetail.getOrderStatus() + "</font>.";
            tv_paymentStatus.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
         */
            String styledText = "Payment Status : " + orderDetail.getOrderStatus();
            tv_paymentStatus.setText(styledText);

            tv_paidate.setVisibility(View.VISIBLE);
            tv_paidate.setText("Paid Date : " + orderDetail.getPaidDate());

        } else {

          /*  String styledText = "Payment Status : " + "<font color='red'>" + orderDetail.getOrderStatus() + "</font>.";
            tv_paymentStatus.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
*/
            String styledText = "Payment Status : " + orderDetail.getOrderStatus();
            tv_paymentStatus.setText(styledText);

            tv_paidate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_chat:

                if (apicallStatus == 1 && channelKey != 0)
                    chatInitiate(channelKey);

                break;

            case R.id.iv_sendConfermetionCode:

                sendConfirmationCode(order.getOrderId(), order.getOrderToken(), order.getBId(), order.getOrderToken());
                break;

            case R.id.tv_submitRate:
                if(ratingValue >=1) {
                    if (iseditable == 0)
                        sendRateingBar(order.getOrderId(), "" + ratingValue, order.getBId(), comment);

                    else
                        updateRateingBar(order.getOrderId(), "" + ratingValue, order.getBId(), comment, order.getBusiness_rating().getBrt_id());
                }
                else
                    Utill.showCenteredToast(context,getString(R.string.str_rating_error));

                break;

            case R.id.tv_show_submitRate:

                rl_showrate.setVisibility(View.GONE);
                rl_rate.setVisibility(View.VISIBLE);
                iseditable = 1;
                tv_submit.setText("Submit");
                businessRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));
                break;


        }
    }

    public void getOrderDetails(final String order_id/*,String Pagenumber*/) {

        progressBar.setVisibility(View.VISIBLE);
//       encrypt_key=fsdf&c_user_id=2&current_page=1
        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getEncryptKey());
        param.put("c_user_id", preference.getUserId());
        param.put("order_id", order_id);

        Call<Order_Details> call = apiServices.getOrderDetail(param);
        call.enqueue(new Callback<Order_Details>() {
            @Override
            public void onResponse(Call<Order_Details> call, Response<Order_Details> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equals("1")) {


                        order = response.body().getData().getOrderDetail();
                        if (order != null) {

                            setRv_bill();
                            setBillDetails(order);
                            setswitches();
                            setInvoiceRecyclerView(order.getSendOrders());
                            setBuyerINfo();
                            if(!order.isIs_blocked())

                                getbussinessGroupInfo(preference.getEncryptKey(), preference.getUserId(), order.getBId());
                            // set paid code
                            if (order.getOrderStatus().equalsIgnoreCase("unpaid")) {
                                ll_confirmation_code.setVisibility(View.VISIBLE);
                                rl_showrate.setVisibility(View.GONE);
                                tv_paid_code.setText(" " + order.getOrderToken());
                                rl_rate.setVisibility(View.GONE);


                            } else {
                                ll_confirmation_code.setVisibility(View.GONE);
                                if (order.getBusiness_rating().getRatingFlag().equalsIgnoreCase("NO")) {
                                    rl_rate.setVisibility(View.VISIBLE);
                                    iseditable = 0;
                                    tv_submit.setText("Submit");
                                } else {
                                    rl_rate.setVisibility(View.GONE);
                                    rl_showrate.setVisibility(View.VISIBLE);
                                    if (Integer.parseInt(order.getBusiness_rating().getRating()) == 1)
                                        tv_ratecount.setText(" You rated " + Integer.parseInt(order.getBusiness_rating().getRating()) + " star");
                                    else
                                        tv_ratecount.setText(" You rated " + Integer.parseInt(order.getBusiness_rating().getRating()) + " stars");

                                 /*   rl_rate.setVisibility(View.VISIBLE);
                                    iseditable=1;
                                    tv_submit.setText("Edit");
                                    businessRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));
*/
                                    showRating.setNumStars(Integer.parseInt(order.getBusiness_rating().getRating()));
                                    showRating.setRating(Float.parseFloat(order.getBusiness_rating().getRating()));


                                }

                            }


                        }
                        ll_bill.setVisibility(View.VISIBLE);

                    } else
                        Utill.showCenteredToast(getApplicationContext(), response.body().getData().getMessage());


                }


            }

            @Override
            public void onFailure(Call<Order_Details> call, Throwable t) {
                //Toast.makeText(context, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }



    BroadcastReceiver receiver;

    @Override
    public void onStart() {
        super.onStart();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {

                    if(order.getOrderId()!=null )
                    getOrderDetails(order.getOrderId());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyFirebaseMessagingService.REQUEST_ACCEPT)
        );
    }

    /***************************************                                 ********************/
// get bussiness info code
    public void getbussinessGroupInfo(String encrypt_key, final String c_user_id, String bussinessId) {
        apicallStatus = 0;
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //      final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
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
                //    pDialog.dismiss();

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
                //  pDialog.dismiss();
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

    /************************************************                       *******************************/

    /***************************************  update rating of  user  *******************************/

    public void updateGroupInfoDetails(final String encrypt_key, final String c_user_id, final String chat_group_id, final String applozic_group_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //    final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
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
                //  pDialog.dismiss();
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
                //   pDialog.dismiss();
            }
        });
    }

    public void chatInitiate(Integer channelKey) {

        ApplozicBridge.launchIndividualGroupChat(context, "" + channelKey, order.getBId(), chatGroupName, order.getBusinessName(),chatGroupId );

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


        ratingValue = rating;


    }

    public void shareImage(View viewGroup, String orderId, String confirmation_code, Integer channelKey) {

        iv_sendConfermetionCode.setVisibility(View.INVISIBLE);
        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = context.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        viewGroup.setDrawingCacheEnabled(false);

        //  Utill.showCenteredToast(context, "Confirmation code sent");

        //now send it out to share//Please share these code with business
        ApplozicBridge.sendCustomMessageTwokey(context, "Confirmation Id for Bill No:- "+orderId+" is Code " + order.getOrderToken(), channelKey, sharefile.getAbsolutePath(), Constant.BILL_CONFIRMATION_CODE, confirmation_code, Constant.BILL_ORDER_ID, orderId);
        //chatInitiate(channelKey);
        iv_sendConfermetionCode.setVisibility(View.VISIBLE);

    }

    // confirmation code is send


    public void sendConfirmationCode(final String order_id, String rating, String b_id, String confirmationCode) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", getString(R.string.please_wait), false, false);

        //
        //  update_order_confirmation_code?encrypt_key=dghdfg&c_user_id=24
        // &order_id=5654&confirmation_code=sdfsd

        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getEncryptKey());
        param.put("c_user_id", preference.getUserId());
        param.put("b_id", b_id);
        param.put("confirmation_code", confirmationCode);
        param.put("order_id", order_id);
        Call<ResultModel> call = apiServices.sendConfirmationCode(param);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                if (response.isSuccessful()) {


                    if (response.body().getData().getResultCode().equals("1")) {

                        shareImage(rl_invoice, order.getOrderId(), order.getOrderToken(), channelKey);
                        Utill.showCenteredToast(getApplicationContext(), getString(R.string.str_confirmation_code));

                    } else
                        Utill.showCenteredToast(getApplicationContext(), response.body().getData().getMessage());


                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void sendRateingBar(final String order_id, String rating, String b_id, String comment) {

        //http://192.168.0.60/chatlocaly/customer_api/add_business_rating?
// encrypt_key=dfgfd&c_user_id=24&b_id=35
// &order_id=50&rating=3.5&
// comment=Grt

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", getString(R.string.please_wait), false, false);

        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getEncryptKey());
        param.put("c_user_id", preference.getUserId());
        param.put("b_id", b_id);
        param.put("rating", rating);
        param.put("order_id", order_id);
        param.put("comment", comment);
        Call<ResultModel> call = apiServices.sendBusinessRating(param);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                if (response.isSuccessful()) {


                    if (response.body().getData().getResultCode().equals("1")) {

                        getOrderDetails(order.getOrderId());

                        Utill.showCenteredToast(getApplicationContext(), getString(R.string.str_rate_successfully));

                    } else
                        Utill.showCenteredToast(getApplicationContext(), response.body().getData().getMessage());


                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void updateRateingBar(final String order_id, String rating, String b_id, String comment, String brt_id) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", getString(R.string.please_wait), false, false);

        //   b_id=35&order_id=50&c_user_id=24&rating=5&comment=ggg&=1
        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getEncryptKey());
        param.put("c_user_id", preference.getUserId());
        param.put("b_id", b_id);
        param.put("brt_id", brt_id);

        param.put("rating", rating);
        param.put("order_id", order_id);
        param.put("comment", comment);


        Call<ResultModel> call = apiServices.updateBusinessRating(param);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equals("1")) {


                        Utill.showCenteredToast(getApplicationContext(), getString(R.string.str_rate_update));
                        getOrderDetails(order.getOrderId());


                    } else
                        Utill.showCenteredToast(getApplicationContext(), response.body().getData().getMessage());


                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private class AdapterForBillShow extends RecyclerView.Adapter<AdapterForBillShow.MyHolder> {
        InvoiceOrderListModel.OrderList order;
        Context context;

        public AdapterForBillShow(Context context, InvoiceOrderListModel.OrderList order) {
            this.context = context;
            this.order = order;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_invoice, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv_itemName.setText(order.getProducts().get(position).getProductName());
            holder.tv_Rate.setText((order.getProducts().get(position).getProductPrice()));
            holder.tv_discount.setText((order.getProducts().get(position).getDiscount()));
            holder.tv_qty.setText((order.getProducts().get(position).getProductQty()));
            holder.tv_amount.setText((order.getProducts().get(position).getTotalPrice()));
        }

        @Override
        public int getItemCount() {
            if (order.getProducts() != null)
                return order.getProducts().size();
            else
                return 0;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            private TextView tv_itemName, tv_Rate, tv_discount, tv_qty, tv_amount;

            public MyHolder(View itemView) {
                super(itemView);
                tv_itemName = (TextView) itemView.findViewById(R.id.tv_itemName);
                tv_Rate = (TextView) itemView.findViewById(R.id.tv_Rate);
                tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
                tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
                tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            }
        }
    }

    private class InvoiceSentListAdapter extends RecyclerView.Adapter<InvoiceSentListAdapter.MyViewHolder> {
        Context context;
        List<InvoiceOrderListModel.SendOrder> sendOrders;

        public InvoiceSentListAdapter(Context context, List<InvoiceOrderListModel.SendOrder> sendOrders) {
            this.context = context;
            this.sendOrders = sendOrders;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_sentinvoice, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_sentOn.setText(sendOrders.get(position).getSendBy());
        }

        @Override
        public int getItemCount() {
            if (sendOrders != null)
                if (sendOrders.size() > 0)
                    return sendOrders.size();
                else return 0;
            else return 0;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_sentOn;

            public MyViewHolder(View itemView) {
                super(itemView);

                tv_sentOn = (TextView) itemView.findViewById(R.id.tv_sentOn);
            }
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
                    iv_chatIcon.setVisibility(View.VISIBLE);

                    apicallStatus = 1;
                    channelKey = channel.getKey();
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


}
