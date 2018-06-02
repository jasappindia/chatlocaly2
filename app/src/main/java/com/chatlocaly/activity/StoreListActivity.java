package com.chatlocaly.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.user.UserLogoutTask;
import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelAddMemberTask;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelRemoveMemberTask;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.channel.ChannelMetadata;
import com.chatlocaly.R;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.other.RecyclerClick_Listener;
import com.chatlocaly.other.RecyclerTouchListener;
import com.chatlocaly.other.Toolbar_ActionMode_Callback;
import com.chatlocaly.Utill;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.adapter.SubcategaryListAdapter;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.fragment.BusinessFragment;
import com.chatlocaly.fragment.ProductFragment;
import com.chatlocaly.fragment.ServiceFragment;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.StoreListModel;
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


public class StoreListActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    //  flag section
    public static final String ISFILTERED_KEY = "isfiltered";
    public static final int ISFILTERED = 201;
    private static final String SEND_BY_BUNDLE = "sendby";
    public   static  int APPLYFILTERCOUNT=0;
    public static int CURRENT_FRAGMENT_SHOW = 0;
    public static int BY_FILTER = 0;

    /// dialog sort
    Dialog dialog;
    BottomNavigationView navigation;
    int counter = 0;
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private StoreListAdapter adapter;
    private View view;
    private Context context;
    private List<StoreListModel.DataData.BusinessListData> list, remove, remove2;
    private ActionMode mActionMode;
    private Toolbar mToolbar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id, sec_key, lat, log, current_page, limit, home_services, services_km, sort, cat_id;
    private List<String> category_id, selected_sub_cat_id, selected_sort_id;
    private String businessId;
    /* params.put("c_user_id", "1");
            params.put("encrypt_key", "dfgfdg");
            params.put("latitude", "" + 25.321377);
            params.put("longitude", "" + 74.586953);
            params.put("current_page", "" + 1);
            params.put("limit", "" + 50);
            params.put("loc_ids[0]", "" + 1);
            params.put("sub_cat_ids[0]", "" + 1);
            params.put("sorts[0]", "popularity");
            params.put("sorts[1]", "relevance");
            params.put("home_services", "YES");
            params.put("services_km", "1");
    */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            if (FilterActivity.filtercount > 0 && !FilterActivity.ISBACKPRESS) {
                switch (item.getItemId()) {
                    case R.id.navigation_business:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, BusinessFragment.newbusinessFragment()).commit();

                        break;
                    case R.id.navigation_product:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, ProductFragment.newProductFragment()).commit();

                        break;
                    case R.id.navigation_service:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, ServiceFragment.newServiceFragment()).commit();

                        break;
                }
            } else {
                switch (item.getItemId()) {
                    case R.id.navigation_business:


                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new BusinessFragment()).commit();
                        break;
                    case R.id.navigation_product:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new ProductFragment()).commit();

                        break;
                    case R.id.navigation_service:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new ServiceFragment()).commit();

                        break;
                }
            }
            return true;
        }
    };
    private String chatGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        init();



        if (getIntent().getExtras() != null && getIntent().getExtras().getString(SubcategaryListAdapter.CATEGORY_ID) != null)
            //Utill.showCenteredToast(context, getIntent().getExtras().getString(SubcategaryListAdapter.CATEGORY_ID));
            cat_id = getIntent().getExtras().getString(SubcategaryListAdapter.CATEGORY_ID);
        // setonclick
        setonClickliner();
        // recaycle view
        implementRecyclerViewClickListeners();
        // call api function
        // callapi();


        if (getIntent().hasExtra(DetailedConversationAdapter.BILL_ORDER_ID)) {
            Log.e("bill id ", "" + getIntent().getExtras().get(DetailedConversationAdapter.BILL_ORDER_ID));
            Log.e(" confirmationcode ", "" + getIntent().getExtras().get(DetailedConversationAdapter.BILL_CONFIRMATION_CODE));


        }

        // check multiple device login
        new BasicUtill().CheckStatus(this);

    }

    private void callapi() {

        getbusinessListByCategoryId(sec_key, user_id, cat_id);


    }

    private void setonClickliner() {
    }


    private void init() {


        context = this;
        // **********************************
        sec_key = "asdf";
        FilterActivity.filtercount=0;
        /**********************                   ***********************/
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();


        /************************                                     *******************************/

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // mToolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitlecolor));
        /*******************************         rView         **************************/
        getSupportActionBar().setTitle("");
        TextView toolbar_title=mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("ChatLocaly");

        list = new ArrayList<>();
        rView = (RecyclerView) findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(context);
        adapter = new StoreListAdapter(context, list);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        // filter list data
        getFilterList(sec_key, user_id, chatlocalyshareprefrence.getCityId());
        //
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navigation.setItemIconTintList(getResources().getColorStateList(R.color.bottomviewscolorslist));
        navigation.setItemTextColor((getResources().getColorStateList(R.color.bottomviewscolorslist)));
        navigation.setSelectedItemId(R.id.navigation_business);

    }



    //   http://192.168.0.60/chatlocaly/customer_api/fliter_parameters?encrypt_key=1&c_user_id=dfgd&city_id=1

    public void getbusinessList(String encrypt_key, String c_user_id, String lat, String log, String currentpage, String limit, String accessTocken, List<String> categoryList, List<String> sub_cat_idList, List<String> sortList, String home_serviceStatus, String services_km) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


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
        params.put("encrypt_key", accessTocken);
        params.put("latitude", "" + log);
        params.put("longitude", "" + lat);
        params.put("current_page", "" + currentpage);
        params.put("limit", "" + limit);

/************************* **************************************/
        for (int i = 0; i < categoryList.size(); i++) {
            params.put("loc_ids[" + i + "]", "" + categoryList.get(i));

        }

        for (int i = 0; i < sub_cat_idList.size(); i++) {
            params.put("sub_cat_ids[" + i + "]", "" + sub_cat_idList.get(i));

        }


        for (int i = 0; i < sortList.size(); i++) {
            params.put("sorts[" + i + "]", "" + sortList.get(i));

        }
        params.put("home_services", "" + home_serviceStatus);
        params.put("services_km", "" + services_km);

        Call<StoreListModel> call = apiService.getBusinessList(params);
        call.enqueue(new Callback<StoreListModel>() {
            @Override
            public void onResponse(Call<StoreListModel> call, retrofit2.Response<StoreListModel> response) {
                pDialog.dismiss();

                StoreListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        list = clientModel.getData().getBusinessList();
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


    //   http://192.168.0.60/chatlocaly/customer_api/fliter_parameters?encrypt_key=1&c_user_id=dfgd&city_id=1

    /*********************************************************                         ***********************************************/


    public void getbusinessListByCategoryId(String encrypt_key, String c_user_id, String category_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


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

/************************* **************************************/
        params.put("loc_ids[" + 0 + "]", "" + category_id);


        Call<StoreListModel> call = apiService.getBusinessList(params);
        call.enqueue(new Callback<StoreListModel>() {
            @Override
            public void onResponse(Call<StoreListModel> call, retrofit2.Response<StoreListModel> response) {
                pDialog.dismiss();

                StoreListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        list = clientModel.getData().getBusinessList();
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

    /*******************************************************                        ************************************************************/

    public void getbusinessListBySort(String encrypt_key, String c_user_id, String sort, String category_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


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

/************************* **************************************/
        params.put("loc_ids[" + 0 + "]", "" + category_id);

        /***********************                *****************************/
        params.put("sorts[" + 0 + "]", "" + sort);


        Call<StoreListModel> call = apiService.getBusinessList(params);
        call.enqueue(new Callback<StoreListModel>() {
            @Override
            public void onResponse(Call<StoreListModel> call, retrofit2.Response<StoreListModel> response) {
                pDialog.dismiss();

                StoreListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        list = clientModel.getData().getBusinessList();
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

    /*******************************************************                        ************************************************************/

    public void getFilterList(String encrypt_key, String c_user_id, String city_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //   final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


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

        params.put("city_id", city_id);

        Call<FilterModel> call = apiService.getfilterparamitter(params);
        call.enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, retrofit2.Response<FilterModel> response) {
                //   pDialog.dismiss();

                FilterModel clientModel = response.body();


                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        FilterModel filterModel = new FilterModel();
                        filterModel.setData(clientModel.getData());

                        FilterDataSingleTon.getInstance().setFilterModel(filterModel);

                        FilterDataSingleTon.getInstance().setNonselectedfilterModel(filterModel);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                // pDialog.dismiss();
            }
        });
    }

    /***********************************************
     * action mode code
     *************************************/
    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        rView.addOnItemTouchListener(new RecyclerTouchListener(context, rView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);//Toggle the selection
        // hide and show toolbar
        mToolbar.setVisibility(View.GONE);
        boolean hasCheckedItems = adapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((StoreListActivity) context).startSupportActionMode(new Toolbar_ActionMode_Callback(StoreListActivity.this, adapter, list));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " selected");


    }

    //Set action mode null after use
    public void setNullToActionMode() {
        // hide and show toolbar

        if (mActionMode != null)
            mActionMode = null;
        mToolbar.setVisibility(View.VISIBLE);

    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = adapter
                .getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                list.remove(selected.keyAt(i));
                adapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(context, selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rl_filter:

                Intent i = new Intent(context, FilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SEND_BY_BUNDLE, CURRENT_FRAGMENT_SHOW);
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

                if (ISFILTERED == ISFILTERED) {
                    getbusinessListBySort(sec_key, user_id, sort, cat_id);
                    dialog.dismiss();

                } else {

                }
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);


    }

    /*******************************************                        **************************************/

    public String getSubcategoryId() {
        return cat_id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// old code for activity
      /*  if (requestCode == FilterActivity.requestFilteredActivity) {


            if (CURRENT_FRAGMENT_SHOW == FilterActivity.BUSINESS_FRAGMENT) {
                BY_FILTER = 1;
                navigation.setSelectedItemId(R.id.navigation_business);

            } else if (CURRENT_FRAGMENT_SHOW == FilterActivity.PORODUCT_FRAGMENT)

            {
                BY_FILTER = 1;

                navigation.setSelectedItemId(R.id.navigation_product);


            } else if (CURRENT_FRAGMENT_SHOW == FilterActivity.SERVICE_FRAGMENT)

            {
                BY_FILTER = 1;

                navigation.setSelectedItemId(R.id.navigation_service);

            }


        }
*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FilterActivity.filtercount=0;
        APPLYFILTERCOUNT=0;
    }

    public void updateGroupInfoDetails(final String encrypt_key, final String c_user_id, final String chat_group_id, final String applozic_group_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
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
                pDialog.dismiss();
                ResultModel clientModel = response.body();
                counter++;

                if (clientModel != null && clientModel.getData() != null) {


                    //Utill.showCenteredToast(context, "channel key successfully ");
                } else {
                    if (counter < 3)
                        updateGroupInfoDetails(encrypt_key, c_user_id, chat_group_id, applozic_group_id);

                }

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }


    // createGroup
    public void createGroup(final GroupInfoModel groupInfoModel) {

        //ApplozicBridge.launchIndividualGroupChat(context, "" + 28, "" + groupInfoModel.getGroupName(),groupInfoModel.getBussinessName());

        //   ApplozicBridge.launchIndividualChat(context,user_id,"ashok");
        businessId = groupInfoModel.getBusinessId();

        if (Utill.isRegisterdAtApplozic(context)) {


            AlGroupInformationAsyncTask.GroupMemberListener channelInfoTask = new AlGroupInformationAsyncTask.GroupMemberListener() {
                @Override
                public void onSuccess(Channel channel, Context context) {
                    Log.i("Group ", "Details " + channel);

                    if (channel.getKey() == null) {
                        // logout user data
                        StoreListActivity.CreateGroup createGroup = new StoreListActivity.CreateGroup();
                        createGroup.execute(groupInfoModel);
                        //Logout success

                    } else
                        ApplozicBridge.launchIndividualGroupChat(context, "" + channel.getKey(), businessId, "" + groupInfoModel.getGroupName(), groupInfoModel.getBussinessName(), chatGroupId);


                }

                @Override
                public void onFailure(Channel channel, Exception e, Context context) {

                    Log.i("Group ", "Exception " + channel);

                    if (channel == null) {
                        StoreListActivity.CreateGroup createGroup = new StoreListActivity.CreateGroup();
                        createGroup.execute(groupInfoModel);
                    }
                }
            };
            chatGroupId=groupInfoModel.getGroupId();


            AlGroupInformationAsyncTask alGroupInformationAsyncTask = new AlGroupInformationAsyncTask(context, groupInfoModel.getGroupId(), channelInfoTask);
            alGroupInformationAsyncTask.execute();


        } else {
            Log.i("storeListActivity", "user not login");

        }
    }

    // storelist activity

    public void channelMetadata()

    {

        ChannelMetadata channelMetadata = new ChannelMetadata();
        channelMetadata.setCreateGroupMessage(ChannelMetadata.ADMIN_NAME + " created " + ChannelMetadata.GROUP_NAME);
        channelMetadata.setAddMemberMessage(ChannelMetadata.ADMIN_NAME + " added " + ChannelMetadata.USER_NAME);
        channelMetadata.setRemoveMemberMessage(ChannelMetadata.ADMIN_NAME + " removed " + ChannelMetadata.USER_NAME);
        channelMetadata.setGroupNameChangeMessage(ChannelMetadata.USER_NAME + " changed group name " + ChannelMetadata.GROUP_NAME);
        channelMetadata.setJoinMemberMessage(ChannelMetadata.USER_NAME + " joined");
        channelMetadata.setGroupLeftMessage(ChannelMetadata.USER_NAME + " left group " + ChannelMetadata.GROUP_NAME);
        channelMetadata.setGroupIconChangeMessage(ChannelMetadata.USER_NAME + " changed icon");
        channelMetadata.setDeletedGroupMessage(ChannelMetadata.ADMIN_NAME + " deleted group " + ChannelMetadata.GROUP_NAME);

    }

    // add  member  in group in android
    public void addMemberInGroup(String groupId, final String userId, Context context) {

        Log.d("memberId", "" + userId);

        final int channelKey = Integer.parseInt(groupId);


        ApplozicChannelAddMemberTask.ChannelAddMemberListener channelAddMemberListener = new ApplozicChannelAddMemberTask.ChannelAddMemberListener() {
            @Override
            public void onSuccess(String response, Context context) {
                //Response will be "success" if user is added successfully
                Log.i("ApplozicChannelMember", "Add Response:" + response);
            }

            @Override
            public void onFailure(String response, Exception e, Context context) {
                Log.i("ApplozicChannelMember", "Add Response:" + response);

            }
        };
        ApplozicChannelAddMemberTask applozicChannelAddMemberTask = new ApplozicChannelAddMemberTask(context, channelKey, userId, channelAddMemberListener);//pass channel key and userId whom you want to add to channel
        applozicChannelAddMemberTask.execute((Void) null);


        //


        // channelKey
        // Unique identifier of the group/channel
        // userId
        // Unique identifier of the user


        //


/*

       UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

           @Override
           public void onSuccess(RegistrationResponse registrationResponse, Context context) {
               //After successful registration with Applozic server the callback will come here

               ApplozicChannelAddMemberTask.ChannelAddMemberListener channelAddMemberListener = new ApplozicChannelAddMemberTask.ChannelAddMemberListener() {
                   @Override
                   public void onSuccess(String response, Context context) {
                       //Response will be "success" if user is added successfully
                       Log.i("ApplozicChannelMember", "Add Response:" + response);
                   }

                   @Override
                   public void onFailure(String response, Exception e, Context context) {
                       Log.i("ApplozicChannelMember", "Add Response:" + response );

                   }
               };
               ApplozicChannelAddMemberTask applozicChannelAddMemberTask = new ApplozicChannelAddMemberTask(context,channelKey, userId, channelAddMemberListener);//pass channel key and userId whom you want to add to channel
               applozicChannelAddMemberTask.execute((Void) null);

           }

           @Override
           public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
               //If any failure in registration the callback  will come here
           }};

           */
/* User user = new User();
            user.setUserId(Utill.getApplozicUserId(chatlocalyshareprefrence.getUserId())); //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
*//*
       */
/* user.setDisplayName(displayName); //displayName is the name of the user which will be shown in chat messages
        user.setEmail(email); //optional
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink("");//optional, set your image link if you have
       *//*
 new UserLoginTask(ApplozicBridge.createUserbyId(userId), listener, context).execute((Void) null);





*/


    }

    //
    public void removeFromGroup(String userId, String groupId) {
        ApplozicChannelRemoveMemberTask.ChannelRemoveMemberListener channelRemoveMemberListener = new ApplozicChannelRemoveMemberTask.ChannelRemoveMemberListener() {
            @Override
            public void onSuccess(String response, Context context) {
                //Response will be "success" if user is removed successfully
                Log.i("ApplozicChannel", "remove member response:" + response);
            }

            @Override
            public void onFailure(String response, Exception e, Context context) {

            }
        };
        //  ApplozicChannelRemoveMemberTask applozicChannelRemoveMemberTask =  new ApplozicChannelRemoveMemberTask(context,channelKey,userId,channelRemoveMemberListener);//pass channelKey and userId whom you want to remove from channel
        //   applozicChannelRemoveMemberTask.execute((Void)null);
    }

    // user logout task
    public void logOutTask() {

        UserLogoutTask.TaskListener userLogoutTaskListener = new UserLogoutTask.TaskListener() {
            @Override
            public void onSuccess(Context context) {
                //Logout success
            }

            @Override
            public void onFailure(Exception exception) {
                //Logout failure
            }
        };
        UserLogoutTask userLogoutTask = new UserLogoutTask(userLogoutTaskListener, context);
        userLogoutTask.execute((Void) null);


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
                    chatGroupId=groupID;
                    ApplozicBridge.launchIndividualGroupChat(context, "" + channel.getKey(), businessId, groupname, bussinessName, chatGroupId);
                    updateGroupInfoDetails(sec_key, user_id, groupID, "" + channel.getKey());


                }

                @Override
                public void onFailure(ChannelFeedApiResponse channelFeedApiResponse, Context context) {
                    Log.e("group", "fail");

                }
            };

            List<String> channelMembersList = new ArrayList<String>();
            if (groupmemberId != null) {
                for (String addgroupmember : groupmemberId)
                    channelMembersList.add(Utill.getApplozicGroupMemberId(addgroupmember));//Note:while creating group exclude logged in userId from list
            }
            channelMembersList.add(Utill.getApplozicUserId(user_id));//Note:while creating group exclude logged in userId from list
            ChannelInfo channelInfo = new ChannelInfo(groupname, channelMembersList);
            channelInfo.setType(Channel.GroupType.PRIVATE.getValue().intValue());

            //group type
            //channelInfo.setImageUrl(""); //pass group image link URL
            //channelInfo.setChannelMetadata(channelMetadata); //Optional option for setting group meta data
            channelInfo.setClientGroupId(groupID); //Optional if you have your own groupId then you can pass here
            channelInfo.setAdmin(Utill.getApplozicGroupMemberId(admin));
            // old code
            // Channel channel = ChannelService.getInstance(context).createChannel(channelInfo);
            // Log.e(" group id",channel.getClientGroupId());
            AlChannelCreateAsyncTask alChannelCreateAsyncTask = new AlChannelCreateAsyncTask(context, channelInfo, taskListenerInterface);
            alChannelCreateAsyncTask.execute((Void) null);


        }

        // group id creater function
        public String groupIdCreate(String user_id, String groupId) {
            String id = "";


            return id;
        }


    }


    // get group information
    /*
    *
    * AlGroupInformationAsyncTask.GroupMemberListener channelInfoTask =  new AlGroupInformationAsyncTask.GroupMemberListener() {
           @Override
           public void onSuccess(Channel channel, Context context) {
               Log.i("Group ","Details "+channel);
           }
           @Override
           public void onFailure(Channel channel, Exception e, Context context) {
           }
       } ;
       AlGroupInformationAsyncTask alGroupInformationAsyncTask = new AlGroupInformationAsyncTask(this,clinetGroupIdOrGroupId,channelInfoTask);
       alGroupInformationAsyncTask.execute();
    * */


}
