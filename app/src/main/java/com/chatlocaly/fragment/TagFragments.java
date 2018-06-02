package com.chatlocaly.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.MobiComMessageService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.adapter.TagListAdapter;
import com.chatlocaly.adapter.ThreadListAdapter;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.model.BlockResultModel;
import com.chatlocaly.model.NotificationResultModel;
import com.chatlocaly.model.SetTagModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.model.ThreadModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragments extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    public static final String Tag = "TagFragments";
    public static int tagCount = 0;
    public static ImageView iv_net, iv_tag;
    ProgressBar progressBar;
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private TagListAdapter adapter;
    private View view;
    private Context context;
    private List<ThreadModel.Datadata.MessageListdata> list;
    private ActionMode mActionMode;
    private Toolbar mToolbar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id, sec_key, lat, log, current_page, limit, home_services, services_km, sort, subcat_id;
    private int apiCallCounter = 0;
    private TextView tv_retry;


    BroadcastReceiver unreadCountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                int unreadCount = new MessageDatabaseService(context).getTotalUnreadCount();
                //Update unread count in UI
                getbusinessTagListByUserId(sec_key, user_id);
            }
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;


    public TagFragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tag_fragments, container, false);
        init(view);
        //    getbusinessTagListByUserId(sec_key, user_id);

        // check multiple device login
        new BasicUtill().CheckStatus(context);

        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);


        if (Utill.isConnectingToInternet(context)) {
            if (iv_net != null && tv_retry != null) {
                iv_net.setVisibility(View.GONE);
                tv_retry.setVisibility(View.GONE);

            }

            if (iv_tag != null)
                iv_tag.setVisibility(View.GONE);

            try {
                if (menuVisible)
                    if (apiCallCounter == 0)
                        getbusinessTagListByUserId(sec_key, user_id);

                    else if (MobiComMessageService.chatUpdateTag == 1) {
                        getbusinessTagListByUserId(sec_key, user_id);
                        MobiComMessageService.chatUpdateTag = 0;

                    } else {
                        if (list.size() < 1) {
                            if (iv_tag != null)
                                iv_tag.setVisibility(View.VISIBLE);
                            //        Utill.showCenteredToast(context, getString(R.string.tag_no_message));
                        }
                    }
            } catch (Exception e) {
                Log.e("tag fragment", "" + e.toString());
            }

        } else {
            if (list != null && list.size() < 1) {
                if (iv_net != null && tv_retry != null) {
                    iv_net.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                    if (iv_tag != null) {
                        iv_tag.setVisibility(View.GONE);

                    }

                }
            } else {
                if (iv_net != null && tv_retry != null) {
                    iv_net.setVisibility(View.GONE);
                    tv_retry.setVisibility(View.GONE);


                }
            }
        }


    }


    private void init(View view) {
        context = getActivity();
        tv_retry = view.findViewById(R.id.btn_retry);
        progressBar = view.findViewById(R.id.progressBar);
        /**************************             *******************/

        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();
        //  subcat_id=((StoreListActivity)context).getSubcategoryId();
        sec_key = chatlocalyshareprefrence.getEncryptKey();
        progressBar = view.findViewById(R.id.progressBar);
        iv_net = view.findViewById(R.id.iv_tag_net);
        iv_tag = view.findViewById(R.id.iv_data_not_Found);
        swipeRefreshLayout = view.findViewById(R.id.sr_layout);
        /****************************          ***********************************/
        list = new ArrayList<>();
        rView = (RecyclerView) view.findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(context);
        adapter = new TagListAdapter(context, list, this);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************              *********************/
        iv_tag.setVisibility(View.GONE);
        iv_net.setVisibility(View.GONE);
        tv_retry.setVisibility(View.GONE);

        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utill.isConnectingToInternet(context))
                    getbusinessTagListByUserId(sec_key, user_id);
                else {
                    iv_tag.setVisibility(View.GONE);
                    iv_net.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(unreadCountBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        // set sort variable

        ((MainActivity)context).setThreadTabCount(new MessageDatabaseService(context).getUnreadConversationCount());
         LocalBroadcastManager.getInstance(context).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));

    }

    public void getbusinessTagListByUserId(String encrypt_key, String c_user_id) {
        iv_tag.setVisibility(View.GONE);
        iv_net.setVisibility(View.GONE);
        tv_retry.setVisibility(View.GONE);
        apiCallCounter = apiCallCounter + 1;
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if (list.size() < 1)
            progressBar.setVisibility(View.VISIBLE);
      /*encrypt_key=ajkdsskj&c_user_id=c_user_id=12&user_id=0C12&device_key=7476ee51-9309-4a71-b23b-0b7ca0b72ea0*/
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("user_id", Utill.getApplozicUserId(c_user_id));
        params.put("device_key", chatlocalyshareprefrence.getApplozicDeviceKey());

        /*************************              *****************************/
        Call<ThreadModel> call = apiService.getThreadList(params);
        call.enqueue(new Callback<ThreadModel>() {
            @Override
            public void onResponse(Call<ThreadModel> call, retrofit2.Response<ThreadModel> response) {
                MobiComMessageService.chatUpdate = 1;
                progressBar.setVisibility(View.GONE);

                ThreadModel clientModel = response.body();
               /* try {
                    Log.e("res",clientModel.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                if (clientModel != null && clientModel.getThread() != null) {

                    if (clientModel.getThread().getResultCode().equalsIgnoreCase("1")) {


                        if (clientModel.getThread().getMessageList() != null && clientModel.getThread().getMessageList().size() > 0) {

                            list.clear();
                            for (ThreadModel.Datadata.MessageListdata messageListdata : clientModel.getThread().getMessageList()) {
                                if (messageListdata.getCustomer_tag() != null && messageListdata.getCustomer_tag().equalsIgnoreCase("1")) {
                                    list.add(messageListdata);
                                }

                            }
                            adapter.notify(list);

                        }


                        if (list.size() < 1)
                            //    Utill.showCenteredToast(context, getString(R.string.tag_no_message));
                            iv_tag.setVisibility(View.VISIBLE);
                        else
                            iv_tag.setVisibility(View.GONE);


                    } else {
                        iv_tag.setVisibility(View.VISIBLE);
                        //Utill.showCenteredToast(context, clientModel.getThread().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ThreadModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                if (str.contains(ApiClient.BASE_URL))
                    Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));
                else
                    Utill.showCenteredToast(context, t.getMessage());

                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    /***************************************    setTag    *****************************/
    public void setTagBusiness(String encrypt_key, String c_user_id, final String tag_type, String b_id, final int position) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        MobiComMessageService.chatUpdate = 1;

        progressBar.setVisibility(View.VISIBLE);
        // encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_id=1
        final HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("tag_type", tag_type);
        params.put("b_id", "" + b_id);

        Call<SetTagModel> call = apiService.setBusinessTag(params);
        call.enqueue(new Callback<SetTagModel>() {
            @Override
            public void onResponse(Call<SetTagModel> call, retrofit2.Response<SetTagModel> response) {
                progressBar.setVisibility(View.GONE);

                SetTagModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        if (tag_type == Constant.TAG) {
                            Utill.showCenteredToast(context, getString(R.string.tag_business));
                            list.get(position).setCustomer_tag("1");
                            adapter.notify(list);
                        } else {
                            list.get(position).setCustomer_tag("0");
                            list.remove(position);
                            adapter.notify(list);
                            if (list.size() == 0)
                                iv_tag.setVisibility(View.VISIBLE);
                            Utill.showCenteredToast(context, getString(R.string.untag_business));
                        }

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<SetTagModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //   Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    /***************************************    setblock business *****************************/
    public void setBlockBusiness(String encrypt_key, String c_user_id, final String block_type, String b_id, String chat_group_id, final int position) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        MobiComMessageService.chatUpdate = 1;


        //    block_chat_group?encrypt_key=dfsdf&c_user_id=1&chat_group_id=1&b_id=2&block_type=block
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("chat_group_id", chat_group_id);
        params.put("block_type", block_type);
        params.put("b_id", "" + b_id);

        Call<BlockResultModel> call = apiService.setBusinessBlock(params);
        call.enqueue(new Callback<BlockResultModel>() {
            @Override
            public void onResponse(Call<BlockResultModel> call, retrofit2.Response<BlockResultModel> response) {
                pDialog.dismiss();

                BlockResultModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        if (block_type == Constant.UNBLOCK_BUSINESS) {

                            list.get(position).setBlock_chat_group("0");

                            Utill.showCenteredToast(context, getString(R.string.unblock_business));
                            list.get(position).setIs_blocked(false);
                            list.get(position).setBlock_side("customer");



                        } else {
                            list.get(position).setBlock_chat_group("1");
                            Utill.showCenteredToast(context, getString(R.string.block_business));
                            list.get(position).setIs_blocked(true);
                            list.get(position).setBlock_side("customer");
                        }
                        adapter.notify(list);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BlockResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //   Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }


//http://192.168.0.60/chatlocaly/customer_api/customer_notification?
// encrypt_key=dgffdg&
// flag_type=remove&
// c_user_id=1
// &b_id=1

    /***************************************    setblock business *****************************/
    public void setNotification(String encrypt_key, String c_user_id, final String flag_type, String b_id, final int position) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        MobiComMessageService.chatUpdate = 1;

        //    block_chat_group?encrypt_key=dfsdf&c_user_id=1&chat_group_id=1&b_id=2&block_type=block
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("flag_type", flag_type);
        params.put("b_id", "" + b_id);

        Call<NotificationResultModel> call = apiService.setNotification(params);
        call.enqueue(new Callback<NotificationResultModel>() {
            @Override
            public void onResponse(Call<NotificationResultModel> call, retrofit2.Response<NotificationResultModel> response) {
                pDialog.dismiss();

                NotificationResultModel clientModel = response.body();

                if (clientModel != null && clientModel.getClass() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        if (flag_type == Constant.ADD_NOTIFICATION) {
                            list.get(position).setCustomer_notification("0");
                            Utill.showCenteredToast(context, getString(R.string.unblock_notification));
                        } else {
                            list.get(position).setCustomer_notification("1");
                            Utill.showCenteredToast(context, getString(R.string.block_notification));
                        }
                        adapter.notify(list);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //     Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {

        if (Utill.isConnectingToInternet(context)) {

            swipeRefreshLayout.setRefreshing(false);
            getbusinessTagListByUserId(sec_key, user_id);


        }
        else {
            swipeRefreshLayout.setRefreshing(false);
            list.clear();
            adapter.notify(list);
            iv_tag.setVisibility(View.GONE);
            iv_net.setVisibility(View.VISIBLE);
            tv_retry.setVisibility(View.VISIBLE);
        }
    }
}
