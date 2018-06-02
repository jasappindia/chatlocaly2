package com.chatlocaly.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.CityListAdapter;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityListActivity extends AppCompatActivity {


    protected ProgressBar progressBar;
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private CityListAdapter adapter;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private View view;
    private Context context;
    private List<CityListModel.citydata.CityListData> list;
    private String user_id;
    private String sec_key;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        init();

       if(Utill.isConnectingToInternet(context)) {
           // check multiple device login
           new BasicUtill().CheckStatus(this);
           getCityList(user_id, sec_key);
       }
       else
       {
           Toast.makeText(context, ""+getString(R.string.no_internet_connected), Toast.LENGTH_SHORT).show();

       }

    }

    private void init() {

        context = this;

/**************************         *********************/
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();
        sec_key = "asjhdf";
        progressBar = findViewById(R.id.progressBar);
/***********************************                ***********************************/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitlecolor));
        TextView toolbartitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbartitle.setText("Select City");
        getSupportActionBar().setTitle("");

        list = new ArrayList<>();
        rView = (RecyclerView) findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(context);
        adapter = new CityListAdapter(context, list);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************         *********************/


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // get city list
    public void getCityList(String user_id, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", accessTocken);
        Call<CityListModel> call = apiService.getCityList(params);
        call.enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {

                progressBar.setVisibility(View.GONE);

                CityListModel cityListModel = response.body();
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {

                        adapter.notifydatasetChange(cityListModel.getData().getCityList());

                    }

                } else
                    Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

}
