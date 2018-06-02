package com.chatlocaly.activity;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.KeyworldListAdapter;
import com.chatlocaly.adapter.ProductsListAdapter;
import com.chatlocaly.adapter.ServiceListAdapter;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.fragment.BusinessFragment;
import com.chatlocaly.fragment.ProductFragment;
import com.chatlocaly.fragment.ServiceFragment;
import com.chatlocaly.model.ProductListModel;
import com.chatlocaly.model.SearchKeyWord;
import com.chatlocaly.model.ServiceModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipDeletedListener;
import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener, TextWatcher {

    BottomNavigationView navigation;
    int counter = 0;
    List<StoreListModel.DataData.BusinessListData> listBusiness;
    StoreListAdapter adapterB;
    ProductsListAdapter adapterP;
    List<ProductListModel.productdata.ProductListdata> listProduct;
    List<ServiceModel.Datadata.ServiceListdata> listService;
    ServiceListAdapter adapterS;
    List<SearchKeyWord.Datadata.SearchKeyListdata> listsearchKeyword, allKeyworld;
    KeyworldListAdapter adapterK;
    private LinearLayout ll_search_view, ll_search_query;
    private EditText edt_search;
    private TextView tv_searchQuery;
    private ImageView iv_clear, iv_deletequery, iv_home, iv_data_not_Found;
    private String searchQuery;
    private ProgressBar progressBar;
    private RecyclerView rView;
    private LinearLayoutManager layoutManagerProduct, layoutManagerService, layoutManagerBusiness;
    private StoreListAdapter adapter;
    private View view;
    private Context context;
    private ActionMode mActionMode;
    private Toolbar mToolbar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id, sec_key, lat, log, current_page, limit, home_services, services_km, sort, cat_id;
    private List<String> category_id, selected_sub_cat_id, selected_sort_id;
    ;
    private String businessId;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            iv_data_not_Found.setVisibility(View.GONE);

            switch (item.getItemId()) {
                case R.id.navigation_business:
                    searchBusiness();
                    break;
                case R.id.navigation_product:
                    searchProducts();

                    break;
                case R.id.navigation_service:
                    searchService();

                    break;

            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();


    }

    private void init() {
        context = this;
        allKeyworld = new ArrayList<>();
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        ll_search_query = findViewById(R.id.ll_search_query);
        ll_search_view = findViewById(R.id.ll_search);
        edt_search = findViewById(R.id.edt_search);
        tv_searchQuery = findViewById(R.id.tv_earch_query);
        iv_clear = findViewById(R.id.iv_clear);
        iv_deletequery = findViewById(R.id.iv_delete_query);
        iv_home = findViewById(R.id.iv_home);
        iv_data_not_Found = findViewById(R.id.iv_data_not_Found);
        edt_search.setOnEditorActionListener(this);
        iv_home.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        iv_deletequery.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        // **********************************
        sec_key = chatlocalyshareprefrence.getEncryptKey();

        /**********************                   ***********************/
        user_id = chatlocalyshareprefrence.getUserId();


        /*******************************         rView         **************************/

        //
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navigation.setItemIconTintList(getResources().getColorStateList(R.color.bottomviewscolorslist));
        navigation.setItemTextColor((getResources().getColorStateList(R.color.bottomviewscolorslist)));
        navigation.setSelectedItemId(R.id.navigation_business);

        edt_search.addTextChangedListener(this);
        navigation.setVisibility(View.GONE);
        searchkeyword();

        if (Utill.isConnectingToInternet(context)) {

            getKeyword(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), "");
        } else
            Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));

    }

    private void searchBusiness() {

        if (!TextUtils.isEmpty(edt_search.getText().toString().trim())) {
            rView = findViewById(R.id.rView);
            List<StoreListModel.DataData.BusinessListData> list = new ArrayList<>();
            layoutManagerBusiness = new LinearLayoutManager(context);
            adapterB = new StoreListAdapter(context, listBusiness);
            rView.setLayoutManager(layoutManagerBusiness);
            rView.setAdapter(adapterB);
            getbusinessListByCategoryId(chatlocalyshareprefrence.getEncryptKey(), user_id, searchQuery);
        }


    }

    private void searchProducts() {


        if (!TextUtils.isEmpty(edt_search.getText().toString().trim())) {
            rView = findViewById(R.id.rView);
            listProduct = new ArrayList<>();
            layoutManagerProduct = new GridLayoutManager(context, 2);
            adapterP = new ProductsListAdapter(context, listProduct);
            rView.setLayoutManager(layoutManagerProduct);
            rView.setAdapter(adapterP);
            getProductlist(chatlocalyshareprefrence.getEncryptKey(), user_id, searchQuery);

        }


    }

    private void searchService() {


        if (!TextUtils.isEmpty(edt_search.getText().toString().trim())) {
            listService = new ArrayList<>();
            rView = (RecyclerView) findViewById(R.id.rView);
            layoutManagerService = new GridLayoutManager(context, 2);
            adapterS = new ServiceListAdapter(context, listService);
            rView.setLayoutManager(layoutManagerService);
            rView.setAdapter(adapterS);
            getServicelist(chatlocalyshareprefrence.getEncryptKey(), user_id, searchQuery);

        }


    }

    public void searchKeywordSet(int position) {
        String query = listsearchKeyword.get(position).getSearchKey();

        edt_search.setText("" + query);
        searchQuery = query;
        tv_searchQuery.setText(searchQuery);
        ll_search_view.setVisibility(View.GONE);
        ll_search_query.setVisibility(View.VISIBLE);
        navigation.setVisibility(View.VISIBLE);
        // call bottom navigation
        navigation.setSelectedItemId(navigation.getSelectedItemId());
        Utill.keyboardHide(context, edt_search);


    }

    private void searchkeyword() {


        rView = findViewById(R.id.rView);
        listsearchKeyword = new ArrayList<>();
        LinearLayoutManager layoutManagerKeyworld = new LinearLayoutManager(context);
        adapterK = new KeyworldListAdapter(context, listsearchKeyword);
        rView.setLayoutManager(layoutManagerKeyworld);
        rView.setAdapter(adapterK);


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            if (TextUtils.isEmpty(edt_search.getText().toString().trim())) {

            } else {


                searchQuery = edt_search.getText().toString().trim();

                tv_searchQuery.setText(searchQuery);
                ll_search_view.setVisibility(View.GONE);
                ll_search_query.setVisibility(View.VISIBLE);
                navigation.setVisibility(View.VISIBLE);
                // call bottom navigation
                navigation.setSelectedItemId(navigation.getSelectedItemId());
                Utill.keyboardHide(context, edt_search);
            }

            return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_clear:
                edt_search.setText("");
                break;

            case R.id.iv_delete_query:

                edt_search.setText("");
                ll_search_view.setVisibility(View.VISIBLE);
                ll_search_query.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);
                clearAllView();
                searchkeyword();
                if (allKeyworld != null && allKeyworld.size() > 0) {

                    listsearchKeyword = allKeyworld;
                    adapterK.notifydatasetChange(allKeyworld);
                }
                break;
            case R.id.iv_home:
                Utill.keyboardHide(context, edt_search);
                onBackPressed();
                break;

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        Utill.keyboardHide(context, edt_search);

    }

    @Override
    public void onBackPressed() {
        if (ll_search_query.getVisibility() == View.VISIBLE) {

            edt_search.setText("");
            ll_search_view.setVisibility(View.VISIBLE);
            ll_search_query.setVisibility(View.GONE);
            navigation.setVisibility(View.INVISIBLE);

            clearAllView();
            searchkeyword();
            if (listsearchKeyword != null && listsearchKeyword.size() > 0)
                adapterK.notifydatasetChange(listsearchKeyword);
        } else {
            super.onBackPressed();
        }

    }

    private void clearAllView() {

        iv_data_not_Found.setVisibility(View.GONE);

        if (listProduct != null) {

            listProduct.clear();
            adapterP.notifydatachange(listProduct);
        }
        if (listService != null) {

            listService.clear();
            adapterS.notifydatachange(listService);
        }
        if (listBusiness != null) {

            listBusiness.clear();
            adapterB.notify(listBusiness);
        }


    }


    public void getbusinessListByCategoryId(String encrypt_key, String c_user_id, String searchQuery) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        // final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        progressBar.setVisibility(View.VISIBLE);
        /*
        *
        *
        *
        *   ncrypt_key=dfgfdg&c_user_id=1&current_page=1&limit=10
        *   &loc_ids[0]=1&sub_cat_ids[0]=1&sorts[0]=popularity
        *   &sorts[1]=relevance&services_km=1&home_services=YES
         * */
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("search_key", searchQuery);
/************************* **************************************/
        Call<StoreListModel> call = apiService.getBusinessList(params);
        call.enqueue(new Callback<StoreListModel>() {
            @Override
            public void onResponse(Call<StoreListModel> call, retrofit2.Response<StoreListModel> response) {
                progressBar.setVisibility(View.GONE);

                StoreListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        listBusiness = clientModel.getData().getBusinessList();
                        adapterB.notify(listBusiness);
                    } else {
                        iv_data_not_Found.setVisibility(View.VISIBLE);

                        // Utill.showCenteredToast(context, clientModel.getData().getMessage());
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
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void getProductlist(String encrypt_key, String c_user_id, String searchQuery) {
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
        params.put("search_key", searchQuery);

        // params.put("current_page", current_page);
        // params.put("limit", limit);

/************************* **************************************/
        // params.put("loc_ids["+0+"]", ""+category_id);
/************************* *************************/


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


                        listProduct = clientModel.getData().getProductList();
                        adapterP.notifydatachange(listProduct);
                    } else {
                        iv_data_not_Found.setVisibility(View.VISIBLE);
                        //  Utill.showCenteredToast(context, clientModel.getData().getMessage());
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


    /*******************************************************                        ************************************************************/

    public void getServicelist(String encrypt_key, String c_user_id, String searchQuery) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
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
        // params.put("current_page", current_page);
        // params.put("limit", limit);

/************************* **************************************/
        //   params.put("loc_ids[" + 0 + "]", "" + category_id);
/************************* *************************/
        params.put("search_key", searchQuery);

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


        Call<ServiceModel> call = apiService.getServiceList(params);
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, retrofit2.Response<ServiceModel> response) {

                progressBar.setVisibility(View.GONE);

                ServiceModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        listService = clientModel.getData().getServiceList();
                        adapterS.notifydatachange(listService);

                    } else {
                        iv_data_not_Found.setVisibility(View.VISIBLE);
                        // Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }


    /*******************************************************                        ************************************************************/

    public void getKeyword(String encrypt_key, String c_user_id, final String searchQuery) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //  get_search_key_list?encrypt_key=dfg&c_user_id=24&search_key=a


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        /************************ *************************/
        params.put("search_key", searchQuery);

        Call<SearchKeyWord> call = apiService.getKeyWordList(params);
        call.enqueue(new Callback<SearchKeyWord>() {
            @Override
            public void onResponse(Call<SearchKeyWord> call, retrofit2.Response<SearchKeyWord> response) {


                SearchKeyWord clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        listsearchKeyword = clientModel.getData().getSearchKeyList();
                        adapterK.notifydatasetChange(listsearchKeyword);

                        if (searchQuery.equalsIgnoreCase("") && clientModel.getData().getSearchKeyList() != null)
                            allKeyworld = clientModel.getData().getSearchKeyList();

                    }
                }

            }

            @Override
            public void onFailure(Call<SearchKeyWord> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());


            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!TextUtils.isEmpty(s.toString().trim()) && s.toString().trim().length() > 0) {
            searchkeyword();
            if (Utill.isConnectingToInternet(context)) {
                getKeyword(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), s.toString().trim().toLowerCase());
            } else
                Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));


        }

    }
}
