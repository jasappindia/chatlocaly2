package com.chatlocaly.fragment.filter;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.CategoryListAdapter;
import com.chatlocaly.adapter.ExpandListCategoryListAdapter;
import com.chatlocaly.adapter.FilterCategoryListAdapter;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.model.FilterCategoryListModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.singleton.FilterDataSingleTon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    public static  final String CATEGORY_FRAGMENT_TAG=CategoryFragment.class.getName();

 /* //  RecyclerView rView;
    //    CreditCartListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    private FilterCategoryListAdapter adapter;

 */

    ExpandableListView expandableListView;
    private List<FilterModel.FilterData.CategoryListData>  list;
    private View view;
    private Context context;
    ExpandListCategoryListAdapter adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_category, container, false);
        init(view);
        list=FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList();
        adapter=new ExpandListCategoryListAdapter(context,list);
        expandableListView.setAdapter(adapter);
        return view;

    }

    private void init(View view) {

        context=view.getContext();
        list=new ArrayList<>();


        expandableListView=(ExpandableListView)view.findViewById(R.id.exp_cateList);
             /*   *//*--------------- rView ---------------*//*
        rView = (RecyclerView) view.findViewById(R.id.rView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(mLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rView.getContext(),
                mLayoutManager.getOrientation());
        rView.addItemDecoration(mDividerItemDecoration);
        adapter = new FilterCategoryListAdapter(list,context);
        rView.setAdapter(adapter);
        *//*--------------- rView ---------------*/
    }

    public void getCategory(String user_id, final String accessTocken) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", "1");
        params.put("encrypt_key", "asdf");

        Call<CategoryListModel> call = apiService.getCategoryList(params);
        call.enqueue(new Callback<CategoryListModel>() {
            @Override
            public void onResponse(Call<CategoryListModel> call, retrofit2.Response<CategoryListModel> response) {
                pDialog.dismiss();

                CategoryListModel clientModel = response.body();

                if (clientModel != null && clientModel.getCategory() != null) {

                    if (clientModel.getCategory().getResultCode().equalsIgnoreCase("1")) {



                    } else {


                        Utill.showCenteredToast(context, clientModel.getCategory().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

}
