package com.chatlocaly.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.ProductListAdapter;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private ProductListAdapter adapter;
    private View view;
    private Context context;
    private List<StoreListModel.DataData.BusinessListData> list;
    private List<StoreListModel.DataData.BusinessListData> remove;
    private List<StoreListModel.DataData.BusinessListData> remove2;
    private ActionMode mActionMode;
    private Toolbar mToolbar;
    private RelativeLayout rl_storeListfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        init();


        // setonclick
        setonClickliner();

        // check multiple device login
        new BasicUtill().CheckStatus(this);



    }

    private void callapi() {

        getbusinessList("","","","","","","");


    }

    private void setonClickliner() {
    }

    private void init() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.text_color_light));


        /*******************************        rView         **************************/
        list = new ArrayList<>();
        rView = (RecyclerView) findViewById(R.id.rView);
        rl_storeListfragment= (RelativeLayout) findViewById(R.id.rl_filter);

        context = ProductListActivity.this;
        layoutManager = new LinearLayoutManager(context);
        adapter = new ProductListAdapter(context, list);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /*********************************************      rview         ******************************/



        rl_storeListfragment.setOnClickListener(this);

    }





    public void getbusinessList(String encrypt_key, String c_user_id,String  latitude,String longitude,String currentpage,String limit,String accessTocken) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


        /*
        *
        *
        *
        *     encrypt_key=dfgfdg&c_user_id=1&latitude=25.321377&longitude=74.586953&current_page=1&limit=10&radius=1000

        * */


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", "1");
        params.put("encrypt_key", "asdf");
        params.put("latitude", ""+25.321377);
        params.put("longitude", ""+74.586953);
        params.put("current_page", ""+1);
        params.put("limit","" +50);

        Call<StoreListModel> call = apiService.getBusinessList(params);
        call.enqueue(new Callback<StoreListModel>() {
            @Override
            public void onResponse(Call<StoreListModel> call, retrofit2.Response<StoreListModel> response) {
                pDialog.dismiss();

                StoreListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        list=clientModel.getData().getBusinessList();
                        adapter.notify(list);
                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<StoreListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }






    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.rl_filter:

                Intent i = new Intent(context, FilterActivity.class);
                startActivity(i);

                break;
        }

    }
}
