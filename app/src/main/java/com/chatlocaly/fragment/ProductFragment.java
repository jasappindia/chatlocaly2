package com.chatlocaly.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.adapter.ProductListAdapter;
import com.chatlocaly.adapter.ProductsListAdapter;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.ProductListModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.other.EndlessScrollListener;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements View.OnClickListener {

    private static final int BY_FILTER_SORT =5003 ;
    ProgressBar progressBar;
    private RecyclerView rView;
    private GridLayoutManager layoutManager;
    private ProductsListAdapter adapter;
    private View view;
    private Context context;
    private List<ProductListModel.productdata.ProductListdata> list;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id, sec_key, cat_id;
    private RelativeLayout rl_storeListfragment;
    private RelativeLayout rl_location;
    private int apiCall = 0, currentPage = 0, remaningCount = 0;
    protected int callByApiType=0;
    public static int BY_FILTER=5001;
    public static int BY_CALL_PRODOUCT=5002;
    public static int Pagination=1;
    private String limit=""+20;


    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newProductFragment() {

        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init(view);
        rl_storeListfragment = (RelativeLayout) view.findViewById(R.id.rl_filter);
        rl_location = (RelativeLayout) view.findViewById(R.id.rl_location);

        /*********************************************      rview         ******************************/
        rl_location.setOnClickListener(this);
        rl_storeListfragment.setOnClickListener(this);

        // check multiple device login
        new BasicUtill().CheckStatus(context);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        StoreListActivity.CURRENT_FRAGMENT_SHOW = FilterActivity.PORODUCT_FRAGMENT;
    }

    private void init(View view) {

        context = getActivity();

        progressBar = view.findViewById(R.id.progressBar);

/**************************         *********************/
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();
        cat_id = ((StoreListActivity) context).getSubcategoryId();
        sec_key = chatlocalyshareprefrence.getEncryptKey();
/***********************************                ***********************************/
        list = new ArrayList<>();
        rView = (RecyclerView) view.findViewById(R.id.rView);
        layoutManager = new GridLayoutManager(context, 2);
        adapter = new ProductsListAdapter(context, list);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************         *********************/


        if (getArguments() != null) {
            callByApiType=BY_FILTER;
            currentPage=1;

            // Utill.showCenteredToast(context,"by Product");
            getproductListByFiltered(0);

        } else {
            currentPage=1;
            callByApiType=BY_CALL_PRODOUCT;
            getProductlist(sec_key, user_id, cat_id);
        }

        rView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //      Toast.makeText(getApplicationContext(), page + " " + totalItemsCount , Toast.LENGTH_LONG).show();


                if (list.size() == totalItemsCount) {

                    if(callByApiType==BY_CALL_PRODOUCT) {
                        if (apiCall == 0) {

                            if (remaningCount > 0) {
                                currentPage = 1 + currentPage;
                                getProductlistbyPage(sec_key, user_id, cat_id, "" + currentPage);
                                // adapterForBills.addFooter();

                            }
                        }
                    }
                    else if(callByApiType==BY_FILTER)
                    {

                        if (remaningCount > 0) {
                            currentPage = 1 + currentPage;
                             getproductListByFiltered(1);
                            // adapterForBills.addFooter();

                        }

                    }
                    else if(callByApiType==BY_FILTER_SORT)
                    {

                        if (remaningCount > 0) {
                            currentPage = 1 + currentPage;
                            getProductListBySort(chatlocalyshareprefrence.getEncryptKey(),chatlocalyshareprefrence.getUserId(),"",1);
                            // adapterForBills.addFooter();

                        }

                    }

                }
            }
        });


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rl_filter:

                Intent i = new Intent(context, FilterActivity.class);
                // Bundle bundle=new Bundle();
                // bundle.putInt(SEND_BY_BUNDLE,CURRENT_FRAGMENT_SHOW);
                startActivityForResult(i, FilterActivity.requestFilteredActivity);

                break;
            case R.id.rl_location:
                showDialog(context);

                break;

        }

    }

    private void showDialog(final Context context) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_sort);
        dialog.setCancelable(true);
        dialog.show();

        final CheckBox ch_popurialty = (CheckBox) dialog.findViewById(R.id.cb_popularity);
        final CheckBox ch_relevent = (CheckBox) dialog.findViewById(R.id.cb_relevance);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.dialog);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);

/*********************** ******************/

        if (chatlocalyshareprefrence.getSortByPopularity()) {

            ch_popurialty.setChecked(true);
            ch_relevent.setChecked(false);

        } else {

            ch_popurialty.setChecked(false);
            ch_relevent.setChecked(true);


        }


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });
        ch_popurialty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //    dialog.dismiss();
                if (buttonView.isChecked()) {
                    ch_popurialty.setChecked(true);
                    ch_relevent.setChecked(false);

                    chatlocalyshareprefrence.setSortByPopularity(true);
                }

                currentPage=1;
                callByApiType=BY_FILTER_SORT;
                getProductListBySort(sec_key, user_id, cat_id,0);
                dialog.dismiss();

            }
        });
        ch_relevent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //    dialog.dismiss();
                if (buttonView.isChecked()) {
                    chatlocalyshareprefrence.setSortByPopularity(false);

                    ch_popurialty.setChecked(false);
                    ch_relevent.setChecked(true);
                }
            }
        });

    }


    /*****************************************                    ********************************/


//http://192.168.0.60/chatlocaly/customer_api/product_list?encrypt_key=dfgfdg&
// c_user_id=1
// &current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
// sorts[0]=popularity&sorts[1]=relevance&services_km=1
// &home_services=YES&brand_ids[0]=1&price_fps[0][low_price]=0&
// price_fps[0][high_price]=100&discount_fps[0][low_discount]=10&discount_fps[0][high_discount]=20

    /*******************************************************                        ************************************************************/

    public void getProductlist(String encrypt_key, String c_user_id, String category_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //   final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        progressBar.setVisibility(View.VISIBLE);

        //  c_user_id=1
// &current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
// sorts[0]=popularity&sorts[1]=relevance&services_km=1
// &home_services=YES&brand_ids[0]=1&price_fps[0][low_price]=0&
// price_fps[0][high_price]=100&discount_fps[0][low_discount]=10&discount_fps[0][high_discount]=20
    /*
*/

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("city_id", chatlocalyshareprefrence.getCityId());

        // params.put("current_page", current_page);
         params.put("limit", limit);

/************************* **************************************/
        // params.put("loc_ids["+0+"]", ""+category_id);
/************************* *************************/
        params.put("sub_cat_ids[" + 0 + "]", "" + category_id);


        /***********************                *****************************/
        //  params.put("sorts["+0+"]", ""+sort);


        //   params.put("brand_ids["+0+"]", ""+brand_ids);

/*

        params.put("services_km", ""+services_km);
        params.put("home_services", ""+home_services);
*/

        /*************************** ***************/
        // price_fps[0][low_price]=0&;
// price_fps[0][high_price]=100&discount_fps[0][low_discount]=10&discount_fps[0][high_discount]=20
    /*
*/



          /*      params.put(" price_fps[0][low_price]", ""+home_services);
               params.put("price_fps[0][high_price]", ""+home_services);

             params.put(" discount_fps[0][low_discount]", ""+home_services);
             params.put("discount_fps[0][high_discount]", ""+home_services);
*/


        Call<ProductListModel> call = apiService.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, retrofit2.Response<ProductListModel> response) {
                progressBar.setVisibility(View.GONE);

                ProductListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                         remaningCount=clientModel.getData().getRemainingCount();
                        list = clientModel.getData().getProductList();
                        adapter.notifydatachange(list);
                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    public void getProductlistbyPage(String encrypt_key, String c_user_id, String category_id,String current_page) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //   final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        if(currentPage==1)
        progressBar.setVisibility(View.VISIBLE);

        //  c_user_id=1
// &current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
// sorts[0]=popularity&sorts[1]=relevance&services_km=1
// &home_services=YES&brand_ids[0]=1&price_fps[0][low_price]=0&
// price_fps[0][high_price]=100&discount_fps[0][low_discount]=10&discount_fps[0][high_discount]=20
    /*
*/

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("city_id", chatlocalyshareprefrence.getCityId());

        // params.put("current_page", current_page);
         params.put("limit", limit);

/************************* **************************************/
        // params.put("loc_ids["+0+"]", ""+category_id);
/************************* *************************/
        params.put("sub_cat_ids[" + 0 + "]", "" + category_id);
        params.put("current_page", "" + current_page);


        /***********************                *****************************/
        //  params.put("sorts["+0+"]", ""+sort);


        //   params.put("brand_ids["+0+"]", ""+brand_ids);

/*

        params.put("services_km", ""+services_km);
        params.put("home_services", ""+home_services);
*/

        /*************************** ***************/
        // price_fps[0][low_price]=0&;
// price_fps[0][high_price]=100&discount_fps[0][low_discount]=10&discount_fps[0][high_discount]=20
    /*
*/



          /*      params.put(" price_fps[0][low_price]", ""+home_services);
               params.put("price_fps[0][high_price]", ""+home_services);

             params.put(" discount_fps[0][low_discount]", ""+home_services);
             params.put("discount_fps[0][high_discount]", ""+home_services);
*/


        Call<ProductListModel> call = apiService.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, retrofit2.Response<ProductListModel> response) {
                progressBar.setVisibility(View.GONE);

                ProductListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        remaningCount=clientModel.getData().getRemainingCount();

                        list.addAll( clientModel.getData().getProductList());
                        adapter.notifydatachange(list);
                        remaningCount=clientModel.getData().getRemainingCount();

                            } else
                                {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }


    public void getProductListBySort(String encrypt_key, String c_user_id, String category_id, final int pagination) {


        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if(currentPage==1)
        progressBar.setVisibility(View.VISIBLE);

        /*
        *
        *
        *
        *   encrypt_key=dfgfdg&c_user_id=1&current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
    sorts[0]=popularity&sorts[1]=relevance&services_km=1&home_services=YES


        * */


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", sec_key);
        params.put("current_page", ""+currentPage);
        params.put("limit", limit);

/************************* **************************************/


        if (FilterDataSingleTon.getInstance().getCategoryListData().size() > 0) {
            for (FilterModel.FilterData.CategoryListData categoryListData : FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList()) {
                int count = 0;
                for (FilterModel.FilterData.CategoryListData.SubCategoriesData subcateg : categoryListData.getSubCategories())
                    if (subcateg.isIsselecteble()) {
                        params.put("sub_cat_ids[" + count + "]", "" + subcateg.getSubCatId());
                        count++;
                    }
            }
        }


        // sorts[0]=popularity
        // &sorts[1]=relevance&
        // services_km=1
        // &home_services=YES&brand_ids[0]=1
        // &price_fps[0][low_price]=0
        // &price_fps[0][high_price]=100
        // &discount_fps[0][low_discount]=10
        // &discount_fps[0][high_discount]=20

        if (chatlocalyshareprefrence.getSortByPopularity())
            params.put("sorts[" + 0 + "]", "popularity");
        else
            params.put("sorts[" + 0 + "]", "relevance");

        params.put("services_km", FilterDataSingleTon.getInstance().getHomeServiceSelected().getService_km());
        params.put("home_services", FilterDataSingleTon.getInstance().getHomeServiceSelected().getHome_service());

        if (FilterDataSingleTon.getInstance().getCategoryListData().size() > 0) {
            for (FilterModel.FilterData.BrandListData brandlist : FilterDataSingleTon.getInstance().getFilterModel().getData().getBrandList()) {
                int count = 0;
                if (brandlist.isSelected()) {
                    params.put("brand_ids[" + count + "]", "" + brandlist.getBrandId());
                    count++;
                }
            }
        }

        if (FilterDataSingleTon.getInstance().getCategoryListData().size() > 0) {
            for (FilterModel.FilterData.LocalityListData locality : FilterDataSingleTon.getInstance().getFilterModel().getData().getLocalityList()) {
                int count = 0;
                if (locality.isSelected()) {
                    params.put("locality_ids[" + count + "]", "" + locality.getLocId());
                    count++;
                }
            }
        }



       /* if(FilterDataSingleTon.getInstance().getCategoryListData().size()>0)
        {
            for(FilterModel.FilterData.LocalityListData locality:FilterDataSingleTon.getInstance().getFilterModel().getData().getLocalityList()) {
                int count=0;
                if(locality.isSelected()) {
                    params.put("locality_ids[" + count + "]", "" + locality.getLocId());
                    count++;
                }
            }
        }*/

        // &price_fps[0][low_price]=0
        // &price_fps[0][high_price]=100
        // &discount_fps[0][low_discount]=10
        // &discount_fps[0][high_discount]=20


        if (FilterDataSingleTon.getInstance().getCategoryListData().size() > 0) {
            for (FilterModel.FilterData.PriceListData priceListData : FilterDataSingleTon.getInstance().getFilterModel().getData().getPriceList()) {
                int count = 0;
                if (priceListData.isSelected()) {
                    params.put("price_fps[" + count + "][low_price]", "" + priceListData.getLowPrice());
                    params.put("price_fps[" + count + "][high_price]", "" + priceListData.getHighPrice());

                    count++;
                }
            }
        }


        if (FilterDataSingleTon.getInstance().getCategoryListData().size() > 0) {
            for (FilterModel.FilterData.DiscountListData discount : FilterDataSingleTon.getInstance().getFilterModel().getData().getDiscountList()) {
                int count = 0;
                if (discount.isSelected()) {
                    params.put("discount_fps[" + count + "][low_discount]", "" + discount.getLowDiscount());
                    params.put("discount_fps[" + count + "][high_discount]", "" + discount.getHighDiscount());

                    count++;
                }
            }
        }


        Call<ProductListModel> call = apiService.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, retrofit2.Response<ProductListModel> response) {
                progressBar.setVisibility(View.GONE);

                ProductListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                         if(pagination!=1)
                              list.clear();
                        if (clientModel.getData().getProductList() != null) {
                            list = clientModel.getData().getProductList();
                            remaningCount=clientModel.getData().getRemainingCount();


                        }
                            adapter.notifydatachange(list);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }


//
// filtered api


    public void getproductListByFiltered(final int pagination) {

        StoreListActivity.APPLYFILTERCOUNT=1;

        adapter.notifydatachange(list);
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

      if(currentPage==1)
        progressBar.setVisibility(View.VISIBLE);


        /*
        *
        *
        *
        *   encrypt_key=dfgfdg&c_user_id=1&current_page=1&limit=10&loc_ids[0]=1&sub_cat_ids[0]=1&
    sorts[0]=popularity&sorts[1]=relevance&services_km=1&home_services=YES


        * */


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", sec_key);
         params.put("current_page", ""+currentPage);
         params.put("limit", limit);

/************************* **************************************/


        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList().size() > 0) {
            int count=0;

            for (FilterModel.FilterData.CategoryListData categoryListData : FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList()) {

                for (FilterModel.FilterData.CategoryListData.SubCategoriesData subcateg : categoryListData.getSubCategories())
                    if (subcateg.isIsselecteble()) {
                        params.put("sub_cat_ids[" + count + "]", "" + subcateg.getSubCatId());
                        count++;
                    }
            }
        }


        // sorts[0]=popularity
        // &sorts[1]=relevance&
        // services_km=1
        // &home_services=YES&brand_ids[0]=1
        // &price_fps[0][low_price]=0
        // &price_fps[0][high_price]=100
        // &discount_fps[0][low_discount]=10
        // &discount_fps[0][high_discount]=20

        if (chatlocalyshareprefrence.getSortByPopularity())
            params.put("sorts[" + 0 + "]", "popularity");
        else
            params.put("sorts[" + 0 + "]", "relevance");

        params.put("services_km", FilterDataSingleTon.getInstance().getHomeServiceSelected().getService_km());
        params.put("home_services", FilterDataSingleTon.getInstance().getHomeServiceSelected().getHome_service());

        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getBrandList().size()> 0) {
            int count = 0;
            for (FilterModel.FilterData.BrandListData brandlist : FilterDataSingleTon.getInstance().getFilterModel().getData().getBrandList()) {

                if (brandlist.isSelected()) {
                    params.put("brand_ids[" + count + "]", "" + brandlist.getBrandId());
                    count++;
                }
            }
        }

        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getLocalityList().size() > 0) {
            int count = 0;
            for (FilterModel.FilterData.LocalityListData locality : FilterDataSingleTon.getInstance().getFilterModel().getData().getLocalityList()) {

                if (locality.isSelected()) {
                    params.put("locality_ids[" + count + "]", "" + locality.getLocId());
                    count++;
                }
            }
        }



       /* if(FilterDataSingleTon.getInstance().getCategoryListData().size()>0)
        {
            for(FilterModel.FilterData.LocalityListData locality:FilterDataSingleTon.getInstance().getFilterModel().getData().getLocalityList()) {
                int count=0;
                if(locality.isSelected()) {
                    params.put("locality_ids[" + count + "]", "" + locality.getLocId());
                    count++;
                }
            }
        }*/

        // &price_fps[0][low_price]=0
        // &price_fps[0][high_price]=100
        // &discount_fps[0][low_discount]=10
        // &discount_fps[0][high_discount]=20


        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getPriceList().size() > 0) {
            int count = 0;
            for (FilterModel.FilterData.PriceListData priceListData : FilterDataSingleTon.getInstance().getFilterModel().getData().getPriceList()) {

                if (priceListData.isSelected()) {
                    params.put("price_fps[" + count + "][low_price]", "" + priceListData.getLowPrice());
                    params.put("price_fps[" + count + "][high_price]", "" + priceListData.getHighPrice());

                    count++;
                }
            }
        }


        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getDiscountList().size() > 0) {
            int count = 0;
            for (FilterModel.FilterData.DiscountListData discount : FilterDataSingleTon.getInstance().getFilterModel().getData().getDiscountList()) {

                if (discount.isSelected()) {
                    params.put("discount_fps[" + count + "][low_discount]", "" + discount.getLowDiscount());
                    params.put("discount_fps[" + count + "][high_discount]", "" + discount.getHighDiscount());
                    count++;
                }
            }
        }


        Call<ProductListModel> call = apiService.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, retrofit2.Response<ProductListModel> response) {
                progressBar.setVisibility(View.GONE);

                ProductListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                      if(pagination!=1)
                        list.clear();
                        if (clientModel.getData().getProductList() != null && clientModel.getData().getProductList().size() > 0)
                            if(pagination!=1)
                                list = clientModel.getData().getProductList();
                        else
                            list.addAll(clientModel.getData().getProductList());
                        remaningCount=clientModel.getData().getRemainingCount();
                        adapter.notifydatachange(list);
                    } else {

                        if(pagination!=1)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilterActivity.requestFilteredActivity && FilterActivity.filtercount > 0 ) {
           if(resultCode== Activity.RESULT_OK) {
               currentPage = 1;
               callByApiType = BY_FILTER;
               getproductListByFiltered(0);
           }
        }
            else
        {
            if (resultCode == RESULT_OK) {


                if (list != null) {
                    currentPage = 1;

                    callByApiType = BY_CALL_PRODOUCT;
                    list.clear();
                    adapter.notifydatachange(list);
                    getProductlist(sec_key, user_id, cat_id);
                }
            }
        }



    }
}
