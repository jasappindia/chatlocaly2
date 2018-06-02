package com.chatlocaly.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.ProductsListAdapter;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.ProductListModel;
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

public class AllProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rView;
    private GridLayoutManager layoutManager;
    private ProductsListAdapter adapter;
    private Context context;
    private List<ProductListModel.productdata.ProductListdata> list;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;

    private ImageView iv_homeButton;
    private TextView  tv_businessName;
    private BusinessProfileInfoModel.Datadata.BusinessDetaildata info;

protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        init();
        //get business id
        if(getIntent().hasExtra(Constants.PRODUCT_SERIALIZABLE))
        {
            info= (BusinessProfileInfoModel.Datadata.BusinessDetaildata) getIntent().getExtras().getSerializable(Constants.PRODUCT_SERIALIZABLE);
            getproductList();
            tv_businessName.setText(info.getBusinessName());

        }



        // check multiple device login
        new BasicUtill().CheckStatus(this);



    }

    private void init() {

         context=this;
         iv_homeButton=findViewById(R.id.iv_arrowBack);
         tv_businessName=findViewById(R.id.tv_businessName);

        /*********************            *****************/
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        /*****************                    ******************/
        list = new ArrayList<>();
        rView = (RecyclerView) findViewById(R.id.rView);
        layoutManager = new GridLayoutManager(context, 2);
        adapter = new ProductsListAdapter(context, list);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************            ******************/
        iv_homeButton.setOnClickListener(this);
        progressBar=findViewById(R.id.progressBar);
        /***********************************               *******************/


    }


    public void getproductList (){



        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
      //  final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        progressBar.setVisibility(View.VISIBLE);

        /*
        *
        *
        *
        *   encrypt_key=dfgfdg&c_user_id=1&current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
    sorts[0]=popularity&sorts[1]=relevance&services_km=1&home_services=YES


        * */


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", chatlocalyshareprefrence.getUserId());
        params.put("encrypt_key", chatlocalyshareprefrence.getEncryptKey());
        params.put("b_id", info.getBId());

        //  params.put("current_page", current_page);
        // params.put("limit", limit);

/************************* **************************************/


        Call<ProductListModel> call = apiService.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, retrofit2.Response<ProductListModel> response) {
                progressBar.setVisibility(View.GONE);

                ProductListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        list.clear();
                        if(clientModel.getData().getProductList()!=null)
                            list = clientModel.getData().getProductList();
                        adapter.notifydatachange(list);
                    } else {


                        list.clear();
                        adapter.notifydatachange(list);

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {

                list.clear();
                adapter.notifydatachange(list);

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void onClick(View v) {


        switch (v.getId())
        {

            case R.id.iv_arrowBack:

                onBackPressed();
                break;






        }


    }




}
