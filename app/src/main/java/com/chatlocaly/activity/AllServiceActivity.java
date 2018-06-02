package com.chatlocaly.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.ServiceListAdapter;
import com.chatlocaly.fragment.ServiceFragment;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.ServiceModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.singleton.FilterDataSingleTon;
import com.chatlocaly.utill.BasicUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AllServiceActivity extends AppCompatActivity implements View.OnClickListener
{


    ProgressBar progressBar;
    private RecyclerView rView;
    private GridLayoutManager layoutManager;
    private ServiceListAdapter adapter;
    private Context context;
    private List<ServiceModel.Datadata.ServiceListdata> list;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id;
    private String sec_key;
    private BusinessProfileInfoModel.Datadata.BusinessDetaildata info;
    private TextView tv_businessName;
    private ImageView iv_arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        init();


        //get business id
        if(getIntent().hasExtra(Constants.SERVICE_SERIALIZABLE))
        {
            info= (BusinessProfileInfoModel.Datadata.BusinessDetaildata) getIntent().getExtras().getSerializable(Constants.SERVICE_SERIALIZABLE);
            getServicelist(sec_key, user_id,info.getBId());
            tv_businessName.setText(info.getBusinessName());

        }
        // check multiple device login
        new BasicUtill().CheckStatus(this);

    }

    private void init() {

        context = this;

/**************************         *********************/
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();
        sec_key = chatlocalyshareprefrence.getEncryptKey();
        tv_businessName=findViewById(R.id.tv_businessName);
        iv_arrowBack=findViewById(R.id.iv_arrowBack);

/***********************************                ***********************************/
        list = new ArrayList<>();
        rView = (RecyclerView) findViewById(R.id.rView);
        layoutManager = new GridLayoutManager(context, 2);
        adapter = new ServiceListAdapter(context, null);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************         *********************/
        /*****************************************                    ********************************/
        progressBar = findViewById(R.id.progressBar);
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

        }

    }


    /*******************************************************                        ************************************************************/

    public void getServicelist(String encrypt_key, String c_user_id,String  b_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("b_id",b_id);
        /***********************                *****************************/
        Call<ServiceModel> call = apiService.getServiceList(params);
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, retrofit2.Response<ServiceModel> response) {
                progressBar.setVisibility(View.GONE);

                ServiceModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        list = clientModel.getData().getServiceList();
                        adapter.notifydatachange(list);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
            }
        });
    }


}
