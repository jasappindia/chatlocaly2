package com.chatlocaly.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.adapter.CategoryListAdapter;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.model.LocalityListModel;
import com.chatlocaly.model.ResultModel;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {

    private RecyclerView rView;


    private LinearLayoutManager layoutManager;
    private CategoryListAdapter adapter;
    private View view;
    private Context context;
    private List<CategoryListModel.DataData.CategoryListData> list;
    private String user_id, secratKey;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private FilterDataSingleTon filterDataSingleTon;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;
    private ImageView iv_net;
    private TextView tv_retry;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)context).setThreadTabCount(new MessageDatabaseService(context).getUnreadConversationCount());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_market, container, false);
        init(view);
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        // get
        user_id = chatlocalyshareprefrence.getUserId();
        secratKey = chatlocalyshareprefrence.getEncryptKey();
        // get city citylist
        //getCitylist(user_id,secratKey);
        // call category list  api


        api_callfunction();
        // set onclick
        onClick();

        // check multiple device login
        new BasicUtill().CheckStatus(context);


        return view;
    }

    private void onClick() {


    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {


            api_callfunction();


        }
    }

    private void api_callfunction() {


        if (Utill.isConnectingToInternet(context)) {
            if (filterDataSingleTon.getLocalityListModel() != null && filterDataSingleTon.getLocalityListModel().getData().getLocalityList() != null && filterDataSingleTon.getLocalityListModel().getData().getLocalityList().size() < 1)
                // get locality
                getLocalitylist(user_id, chatlocalyshareprefrence.getCityId(), secratKey);


            if (list != null && list.size() < 1) {
                getCategory(user_id, secratKey);
            }
           } else if (iv_net != null && tv_retry !=null ) {

           if(list.size()<1)
           {

               tv_retry.setVisibility(View.VISIBLE);
               iv_net.setVisibility(View.VISIBLE);

           }
            /*if (list != null && adapter!=null  ) {
                list.clear();
                adapter.notify(list);
            }*/
        }
    }

    public void setdatafunction() {

    }

    private void init(View view) {
        if (apiCallCounter == 0)
            list = new ArrayList<>();
        filterDataSingleTon = FilterDataSingleTon.getInstance();
        rView = (RecyclerView) view.findViewById(R.id.rView);
        context = getActivity();
        progressBar = view.findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(context);
        iv_net = view.findViewById(R.id.iv_net);
        tv_retry = view.findViewById(R.id.btn_retry);
        rView.setLayoutManager(layoutManager);

        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_callfunction();
            }
        });
        tv_retry.setVisibility(View.GONE);

        iv_net.setVisibility(View.GONE);
//tv_retry.se
    }


    public void getCategory(String user_id, final String accessTocken) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        apiCallCounter++;
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", accessTocken);

        Call<CategoryListModel> call = apiService.getCategoryList(params);
        call.enqueue(new Callback<CategoryListModel>() {
            @Override
            public void onResponse(Call<CategoryListModel> call, retrofit2.Response<CategoryListModel> response) {
                progressBar.setVisibility(View.GONE);

                if(iv_net!=null && tv_retry!=null) {
                    iv_net.setVisibility(View.GONE);
                    tv_retry.setVisibility(View.GONE);
                }
                CategoryListModel clientModel = response.body();

                if (clientModel != null && clientModel.getCategory() != null) {

                    if (clientModel.getCategory().getResultCode().equalsIgnoreCase("1")) {

                        list = clientModel.getCategory().getCategoryList();
                        filterDataSingleTon.setCategoryListData(list);
                        adapter = new CategoryListAdapter(context, list);
                        rView.setAdapter(adapter);

                    } else {


                        Utill.showCenteredToast(context, clientModel.getCategory().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

           //     Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    public void getLocalitylist(String user_id, String city_id, final String accessTocken) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", accessTocken);
        params.put("city_id", accessTocken);

        Call<LocalityListModel> call = apiService.getLocalityList(params);
        call.enqueue(new Callback<LocalityListModel>() {
            @Override
            public void onResponse(Call<LocalityListModel> call, retrofit2.Response<LocalityListModel> response) {

                LocalityListModel locality = response.body();

                if (locality != null && locality.getData() != null) {

                    if (locality.getData().getResultCode().equalsIgnoreCase("1")) {

                        filterDataSingleTon.setLocalityListModel(locality);

                    } else {
                        Utill.showCenteredToast(context, "" + locality.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<LocalityListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

            //    Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
            }
        });
    }


}
