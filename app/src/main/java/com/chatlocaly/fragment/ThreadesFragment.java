package com.chatlocaly.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.ApplozicClient;
import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.MobiComMessageService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.api.notification.MuteNotificationAsync;
import com.applozic.mobicomkit.api.notification.MuteNotificationRequest;
import com.applozic.mobicomkit.feed.ApiResponse;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.notification.MTNotificationBroadcastReceiver;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.adapter.StoreListAdapter;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    public static final String Tag = "TagFragments";
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private ThreadListAdapter adapter;
    private View view;
    private Context context;
    private List<ThreadModel.Datadata.MessageListdata> list;
    ProgressBar progressBar;
    private ActionMode mActionMode;
    private Toolbar mToolbar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_id, sec_key, lat, log, current_page, limit, home_services, services_km, sort, subcat_id;
    private int apiCallCounter=0;
    public static ImageView iv_net,iv_tag;
    private TextView tv_retry;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MuteNotificationRequest muteNotificationRequest;

    public ThreadesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tag_fragments, container, false);
        init(view);
        // check multiple device login
        new BasicUtill().CheckStatus(context);

        return view;
    }


    private void init(View view) {
        context = getActivity();
        tv_retry=view.findViewById(R.id.btn_retry);
        /**************************             *******************/
        swipeRefreshLayout=view.findViewById(R.id.sr_layout);
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_id = chatlocalyshareprefrence.getUserId();
        //  subcat_id=((StoreListActivity)context).getSubcategoryId();
        sec_key = chatlocalyshareprefrence.getEncryptKey();
        progressBar=view.findViewById(R.id.progressBar);
        iv_net=(ImageView) view.findViewById(R.id.iv_tag_net);
        iv_tag=(ImageView) view.findViewById(R.id.iv_data_not_Found);


        iv_tag.setImageResource(R.drawable.nothreads);

        /****************************          ***********************************/
        list = new ArrayList<>();
        rView = (RecyclerView) view.findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(context);
        adapter = new ThreadListAdapter(context, list, this);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        /**************************              *********************/
        iv_net.setVisibility(View.GONE);
        iv_tag.setVisibility(View.GONE);

        tv_retry.setVisibility(View.GONE);
        /***************************           *******************/

        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(Utill.isConnectingToInternet(context))
                getbusinessThreadListByUserId(sec_key, user_id);
                else {
                     iv_tag.setVisibility(View.GONE);
                     iv_net.setVisibility(View.VISIBLE);
                   tv_retry.setVisibility(View.VISIBLE);

                 //  Utill.showCenteredToast(context, getString(R.string.no_internet_connected));
               }

            }
        });

        // swip to regfesh layout

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
        if (MobiComMessageService.chatUpdate == 1){


            if(Utill.isConnectingToInternet(context))
        {
        // set sort variable

            getbusinessThreadListByUserId(sec_key, user_id);
            MobiComMessageService.chatUpdate = 0;
        }
        else {
                if (list != null) {
                    list.clear();
                    adapter.notify(list);
                }

                if (iv_net != null && tv_retry != null) {
                    iv_net.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);

                    if (ThreadesFragment.iv_tag != null) {
                        ThreadesFragment.iv_tag.setVisibility(View.GONE);

                    }
                }

            }
           }




        ((MainActivity)context).setThreadTabCount(new MessageDatabaseService(context).getUnreadConversationCount());

        LocalBroadcastManager.getInstance(context).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));

    }

    public static void upMessageCounter(Context context)
    {


        ((MainActivity)context).setThreadTabCount(new MessageDatabaseService(context).getUnreadConversationCount());

    }

    BroadcastReceiver unreadCountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                int unreadCount = new MessageDatabaseService(context).getTotalUnreadCount();
                //Update unread count in UI
                getbusinessThreadListByUserId(sec_key, user_id);
            }
        }
    };



    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);



        if(Utill.isConnectingToInternet(context)) {
            if(iv_net!=null && tv_retry!=null) {
                iv_net.setVisibility(View.GONE);
                tv_retry.setVisibility(View.GONE);
            }
                if (menuVisible) {
                if (apiCallCounter == 0)
                    getbusinessThreadListByUserId(sec_key, user_id);

                else if (MobiComMessageService.chatUpdate == 1) {

                    getbusinessThreadListByUserId(sec_key, user_id);
                    MobiComMessageService.chatUpdate = 0;

                } else {

                    if (list.size() < 1) {
                      if(iv_tag!=null)
                        iv_tag.setVisibility(View.VISIBLE);

                //        Utill.showCenteredToast(context, getString(R.string.thread_no_message));
                    }
                }


            }
        }
        else
        {
            if(list!=null && list.size()<1) {

                if (iv_net != null && tv_retry!=null) {
                    iv_net.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);

                    if(ThreadesFragment.iv_tag!=null)
                    {
                            ThreadesFragment.iv_tag.setVisibility(View.GONE);

                    }
                }
                }
          /*  else {
                if (iv_net != null && tv_retry!=null) {


                    iv_net.setVisibility(View.GONE);
                    tv_retry.setVisibility(View.GONE);
                    if(ThreadesFragment.iv_tag!=null)
                    {
                        ThreadesFragment.iv_tag.setVisibility(View.GONE);


                    }
                }
            }*/
        }

    }


    public void getbusinessThreadListByUserId(String encrypt_key, String c_user_id) {

        iv_tag.setVisibility(View.GONE);
        iv_net.setVisibility(View.GONE);
        tv_retry.setVisibility(View.GONE);


        apiCallCounter=apiCallCounter+1;
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
     //   final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        if(list.size()<1)
        progressBar.setVisibility(View.VISIBLE);
/*encrypt_key=ajkdsskj&
c_user_id=
c_user_id=12&
user_id=0C12&
device_key=7476ee51-9309-4a71-b23b-0b7ca0b72ea0*/


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("user_id", Utill.getApplozicUserId(c_user_id));
        params.put("device_key", chatlocalyshareprefrence.getApplozicDeviceKey());
        Call<ThreadModel> call = apiService.getThreadList(params);
        call.enqueue(new Callback<ThreadModel>() {
            @Override
            public void onResponse(Call<ThreadModel> call, retrofit2.Response<ThreadModel> response) {
                progressBar.setVisibility(View.GONE);

               ThreadModel clientModel = response.body();

                if(iv_net!=null && tv_retry!=null) {
                    iv_net.setVisibility(View.GONE);
                    tv_retry.setVisibility(View.GONE);
                }


                if (clientModel != null && clientModel.getThread() != null) {

                    if (clientModel.getThread().getResultCode().equalsIgnoreCase("1")) {
                        list = clientModel.getThread().getMessageList();
                        if(clientModel.getThread().getMessageList()!=null && clientModel.getThread().getMessageList().size()>0)
                            adapter.notify(list);
                        else
                        {
                            iv_tag.setVisibility(View.VISIBLE);
                        }




                    } else {
                        iv_tag.setVisibility(View.VISIBLE);
                        //Utill.showCenteredToast(context, clientModel.getThread().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ThreadModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");
             //   Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    /***************************************    setTag    *****************************/
    public void setTagBusiness(String encrypt_key, String c_user_id, final String tag_type, String b_id, final int position) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


        // encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_id=1
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
        params.put("tag_type", tag_type);
        params.put("b_id", "" + b_id);

        Call<SetTagModel> call = apiService.setBusinessTag(params);
        call.enqueue(new Callback<SetTagModel>() {
            @Override
            public void onResponse(Call<SetTagModel> call, retrofit2.Response<SetTagModel> response) {
                pDialog.dismiss();

                SetTagModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        if (tag_type == Constant.TAG) {
                            Utill.showCenteredToast(context, getString(R.string.tag_business));
                            list.get(position).setCustomer_tag("1");
                            adapter.notify(list);
                        } else {
                            list.get(position).setCustomer_tag("0");
                            Utill.showCenteredToast(context, getString(R.string.untag_business));
                            adapter.notify(list);

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

        //        Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

    /***************************************    setblock business *****************************/
    public void setBlockBusiness(String encrypt_key, String c_user_id, final String block_type, String b_id, String chat_group_id, final int position) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


        //    block_chat_group?encrypt_key=dfsdf&c_user_id=1&chat_group_id=1&b_id=2&block_type=block
        final HashMap<String, String> params = new HashMap<>();
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

                            list.get(position).setIs_blocked(false);
                            list.get(position).setBlock_side("customer");
                            Utill.showCenteredToast(context, getString(R.string.unblock_business));
                            adapter.notify(list);

                        } else if(block_type == Constant.BLOCK_BUSINESS) {

                            list.get(position).setBlock_chat_group("1");
                            list.get(position).setIs_blocked(true);
                            list.get(position).setBlock_side("customer");
                            Utill.showCenteredToast(context, getString(R.string.block_business));
                            adapter.notify(list);

                        }


                        if (block_type == Constant.REMOVE_BUSINESS) {

                            list.remove(position);
                            Utill.showCenteredToast(context, getResources().getString(R.string.business_removed_successfully));
                            adapter.notify(list);

                        }
                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BlockResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

       //         Utill.showCenteredToast(context, str);
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
                            adapter.notify(list);

                        } else {
                            list.get(position).setCustomer_notification("1");
                            Utill.showCenteredToast(context, getString(R.string.block_notification));
                            adapter.notify(list);

                        }

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationResultModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                if (str.contains(ApiClient.BASE_URL))
                    Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));
                else
                    Utill.showCenteredToast(context, t.getMessage());

                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }


    @Override
    public void onRefresh() {

        if(Utill.isConnectingToInternet(context)) {


            getbusinessThreadListByUserId(sec_key, user_id);
            swipeRefreshLayout.setRefreshing(false);

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

long millisecond;

    public void muteGroupChat(final int channelKey, final String bId, final int getPosition) {

        final CharSequence[] items = {getString(com.applozic.mobicomkit.uiwidgets.R.string.eight_Hours), getString(com.applozic.mobicomkit.uiwidgets.R.string.one_week), getString(com.applozic.mobicomkit.uiwidgets.R.string.one_year)};
        Date date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        millisecond = date.getTime();

        final MuteNotificationAsync.TaskListener taskListener = new MuteNotificationAsync.TaskListener() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
              setNotification(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.BLOCK_NOTIFIACTION, bId, getPosition);

   //             Toast.makeText(getActivity(), "muted successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(ApiResponse apiResponse, Exception exception) {

            }

            @Override
            public void onCompletion() {

            }
        };


        millisecond = millisecond + 31558000000L;

        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
        muteNotificationAsync.execute((Void) null);

     /*   AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(com.applozic.mobicomkit.uiwidgets.R.string.mute_group_for))
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, final int selectedItem) {
                        if (selectedItem == 0) {
                            millisecond = millisecond + 28800000;
                        } else if (selectedItem == 1) {
                            millisecond = millisecond + 604800000;

                        } else if (selectedItem == 2) {
                            millisecond = millisecond + 31558000000L;
                        }

                        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
                        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
                        muteNotificationAsync.execute((Void) null);
                        dialog.dismiss();

                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();*/
    }

    public void umuteGroupChat(final int channelKey, final String bId, final int position) {
        Date date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        millisecond = date.getTime();

        final MuteNotificationAsync.TaskListener taskListener = new MuteNotificationAsync.TaskListener() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
             //   Toast.makeText(getActivity(), "unmuted successfully", Toast.LENGTH_SHORT).show();
                setNotification(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.ADD_NOTIFICATION, bId, position);

            }

            @Override
            public void onFailure(ApiResponse apiResponse, Exception exception) {

            }

            @Override
            public void onCompletion() {

            }
        };
        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
        muteNotificationAsync.execute((Void) null);


    }

}
