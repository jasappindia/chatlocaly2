package com.chatlocaly.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocaly.R;
import com.chatlocaly.activity.BillShowActivity;
import com.chatlocaly.adapter.AdapterForBills;
import com.chatlocaly.firebasenotification.MyFirebaseMessagingService;
import com.chatlocaly.model.InvoiceOrderListModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.other.EndlessScrollListener;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;
import com.chatlocaly.utill.Utill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillListShowActivity extends AppCompatActivity implements View.OnClickListener {
    String b_id;
    private RecyclerView rv_bills;
    private LinearLayoutManager layoutManager;
    private AdapterForBills adapterForBills;
    private LinearLayout ll_filter, ll_sort;
    private ImageView iv_arrowBack,iv_no_bill;
    private TextView tv_filter, tv_sort;
    private Dialog dialog;
    private ProgressBar progressBar;
    private LinearLayout ll_billLayout;
    private Chatlocalyshareprefrence preference;
    private List<InvoiceOrderListModel.OrderList> orderList;
    private int apiCall = 0;
    private int currentPage = 1;
    private int remaningCount = 0;


    @Override
    protected void onResume() {
        super.onResume();

        // new Utill().CheckStatus(BillListShowActivity.this, 0);
    }


    BroadcastReceiver receiver;

    @Override
    public void onStart() {
        super.onStart();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {

                    if(Utill.isConnectingToInternet(context))

                    {
                        getOrderList("" + currentPage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyFirebaseMessagingService.REQUEST_ACCEPT)
        );
    }


    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        init();

        // check multiple device login
        new BasicUtill().CheckStatus(this);


        if(Utill.isConnectingToInternet(this))

        {
            getOrderList("" + currentPage);
        }
        else
            Utill.showCenteredToast(this,getString(R.string.str_check_internet_connection));

        rv_bills.addOnScrollListener(new EndlessScrollListener(layoutManager)
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
             //      Toast.makeText(getApplicationContext(), page + " " + totalItemsCount , Toast.LENGTH_LONG).show();


                if (orderList.size() == totalItemsCount) {

                       if (apiCall == 0) {

                            if (remaningCount > 0) {
                                currentPage = 1 + currentPage;
                                getOrderList("" + currentPage);
                                adapterForBills.addFooter();

                            }
                        }

                }
            }
        });


    }


    public void init() {
        orderList = new ArrayList<>();
        preference = new Chatlocalyshareprefrence(this);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        ll_filter = (LinearLayout) findViewById(R.id.ll_filter);
        ll_sort = (LinearLayout) findViewById(R.id.ll_sort);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        tv_sort = (TextView) findViewById(R.id.tv_sort);
        ll_billLayout = (LinearLayout) findViewById(R.id.ll_billLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        iv_no_bill = (ImageView) findViewById(R.id.iv_no_bill);
        iv_no_bill.setVisibility(View.GONE);

        /************************                      ***************/
        rv_bills = (RecyclerView) findViewById(R.id.rv_bills);
        layoutManager = new LinearLayoutManager(this);
        adapterForBills = new AdapterForBills(BillListShowActivity.this, orderList);
        rv_bills.setLayoutManager(layoutManager);
        rv_bills.setAdapter(adapterForBills);
        /***********************                      ********************/

        ll_filter.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        ll_sort.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_filter:
                dialog = filterDialog();
                dialog.show();
                break;
            case R.id.ll_sort:
                dialog = sortDialog();
                dialog.show();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;

        }
    }

    public Dialog sortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);



        final String[] array = {"Recent First", "Highest Total", "Lowest Total"};
        builder.setTitle("Sort").setCancelable(true);
        int position=0;

        if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[0]))
            position=0;
        else if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[1]))
            position=1;

        else if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[2]))
            position=2;


            builder.setSingleChoiceItems(array, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                tv_sort.setText(array[i]);
                orderList.clear();
                adapterForBills.notifiedDatalist(orderList);
                currentPage=1;
                getOrderList("" + currentPage);
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rouncounerbox));


        return dialog;
    }

    public Dialog filterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        final String[] array = {"All Bills", "Unpaid", "Paid"};
        builder.setTitle("Filter").setCancelable(true);

        int position=0;

        if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[0]))
            position=0;
        else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[1]))
            position=1;

        else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[2]))
            position=2;


        builder.setSingleChoiceItems(array, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialog.dismiss();
                tv_filter.setText(array[i]);
                orderList.clear();
                adapterForBills.notifiedDatalist(orderList);
                currentPage=1;
                getOrderList(""+currentPage);


            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rouncounerbox));

        return dialog;
    }

    public void getOrderList(final String currentPage/*,String Pagenumber*/) {

        apiCall=1;
    if (currentPage.equalsIgnoreCase("1"))
        progressBar.setVisibility(View.VISIBLE);
//       encrypt_key=fsdf&c_user_id=2&current_page=1
        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getEncryptKey());
        param.put("c_user_id",preference.getUserId());
        param.put("current_page",currentPage);
        param.put("sort_by", tv_sort.getText().toString());
        param.put("filter_by", tv_filter.getText().toString());

        Call<InvoiceOrderListModel> call = apiServices.getInvoiceOderLst(param);
        call.enqueue(new Callback<InvoiceOrderListModel>() {
            @Override
            public void onResponse(Call<InvoiceOrderListModel> call, Response<InvoiceOrderListModel> response) {

                apiCall=0;
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equals("1")) {

                        remaningCount = response.body().getData().getRemainingCount();
                        ll_billLayout.setVisibility(View.VISIBLE);
                        if(currentPage.equalsIgnoreCase("1")) {
                            orderList.clear();
                            orderList = response.body().getData().getOrderList();
                        }
                            else
                        {
                            orderList.addAll(response.body().getData().getOrderList());
                        }
                        adapterForBills.notifiedDatalist(orderList);

                        if(orderList.size()==0) {

                            iv_no_bill.setVisibility(View.VISIBLE);
                            //Utill.showCenteredToast(getApplicationContext(), getString(R.string.str_no_bill_yet));
                        }
                        else
                            iv_no_bill.setVisibility(View.GONE);

                    } else
                        Utill.showCenteredToast(getApplicationContext(), response.body().getData().getMessage());


                }
                progressBar.setVisibility(View.GONE);

                adapterForBills.removeFooter();

            }

            @Override
            public void onFailure(Call<InvoiceOrderListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                adapterForBills.removeFooter();
                apiCall=0;
                Toast.makeText(BillListShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
//http://192.168.0.60/chatlocaly/customer_api/add_business_rating?
// encrypt_key=dfgfd&c_user_id=24&b_id=35
// &order_id=50&rating=3.5&
// comment=Grt












}
