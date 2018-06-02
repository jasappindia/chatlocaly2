package com.chatlocaly.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.api.conversation.MobiComMessageService;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.BusinessProfileActivity;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.ProfileInfoActivity;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.model.BlockResultModel;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.StoreListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.ui.RoundedImageView;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by anjani on 18/12/17.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    private Context context;
    private List<StoreListModel.DataData.BusinessListData> list;
    private SparseBooleanArray mSelectedItemsIds;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_Id, encrypt_key;
    private String bussinessName;

    public StoreListAdapter(Context context, List<StoreListModel.DataData.BusinessListData> list) {
        this.context = context;
        this.list = list;
        mSelectedItemsIds = new SparseBooleanArray();
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_Id = chatlocalyshareprefrence.getUserId();
        encrypt_key = chatlocalyshareprefrence.getEncryptKey();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_store, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        final StoreListModel.DataData.BusinessListData businessListData = list.get(position);

        /*
        *
        * /**
             * b_id : 1
             * business_name : Patanjali
             * home_services : NO
             * services_km : 0.00
             * business_logo : http://192.168.0.60/chatlocaly/assets/uploads/businesses/default-b.png
             * rating_count : 1
             * business_rating : 5.0000 (1)
             * last_conversion : Anurag (owner): Apko...
             */


        holder.tv_message.setVisibility(View.GONE);

        if (businessListData.getBusinessName() != null && !businessListData.getBusinessName().equalsIgnoreCase(""))
            holder.tv_storetitle.setText("" + businessListData.getBusinessName());
    //    else
            //  bussiness name is messing
            //    holder.tv_storetitle.setText(" Vinod General store ");



          //   holder.tv_rate.setText("" + businessListData.getRatingCount());
        if (!businessListData.getRatingCount().equalsIgnoreCase("0")) {
            holder.iv_rate.setImageResource(R.mipmap.israted);
            holder.tv_rate.setText("" + businessListData.getBusinessRating());

        }
            else {
            holder.iv_rate.setImageResource(R.mipmap.notrated);
            holder.tv_rate.setText("" + context.getString(R.string.no_one_rating_yet));

        }

        holder.tv_message.setText("" + businessListData.getLastConversion());


        // home service code

       /* if (businessListData.getHomeServices().toString() != null) {

            if (businessListData.getHomeServices().toString().equalsIgnoreCase("no"))
                holder.tv_homeservice.setText("" + "No home  service ");

            else if (businessListData.getHomeServices().toString().equalsIgnoreCase("yes"))
                holder.tv_homeservice.setText("Upto  " + businessListData.getServicesKm() + " Km");

            else if (businessListData.getHomeServices().toString().equalsIgnoreCase("everywhere"))
                holder.tv_homeservice.setText("" + "No home  service ");


        } else

        {
            holder.tv_homeservice.setText("" + "No home  service ");

        }*/


        if (businessListData.getHomeServices().toString() != null) {

            if (businessListData.getHomeServices().toString().equalsIgnoreCase("no")) {
                holder.tv_homeservice.setText("" + "No home service ");
                holder.iv_homeservice.setImageResource(R.mipmap.nohomeservice);
            } else if (businessListData.getHomeServices().toString().equalsIgnoreCase("yes")) {
                holder.tv_homeservice.setText("Upto " + businessListData.getServicesKm() + " Km");
                holder.iv_homeservice.setImageResource(R.drawable.ic_home);
            } else if (businessListData.getHomeServices().toString().equalsIgnoreCase("everywhere")) {
                holder.tv_homeservice.setText("" + "Everywhere ");
                holder.iv_homeservice.setImageResource(R.drawable.ic_home);

            }

        } else {
            holder.tv_homeservice.setText("" + "No home service ");
            holder.iv_homeservice.setImageResource(R.mipmap.nohomeservice);
        }

        // imageview  notified or

        if (businessListData.getTag_business() != null && !businessListData.getTag_business().equalsIgnoreCase("0"))
            holder.iv_tag.setImageResource(R.mipmap.istaged);
        else
            holder.iv_tag.setImageResource(R.mipmap.nottaqed);

        // imageview  notified

        if (businessListData.getNotification() != null && !businessListData.getNotification().equalsIgnoreCase("1"))
            holder.iv_notified.setImageResource(R.mipmap.isnotified);
        else
            holder.iv_notified.setImageResource(R.mipmap.notnotified);




        // business icon
        Utill.imageshow(context, holder.ivr_business_logo, "" + businessListData.getBusinessLogo());


        if (mSelectedItemsIds.get(position)) {        /** Change background color of the selected items in list view  **/
            holder.itemView.setBackgroundColor(mSelectedItemsIds.get(position) ? context.getResources().getColor(R.color.toolbar) : Color.WHITE);
            holder.iv_isselected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_isselected.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);

        }

        // handle click event  of business fragment


        holder.rl_b_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent chat=new Intent(context, BusinessProfileActivity.class);
                context.startActivity(chat);
*/

                // create bussiness group

                if(!businessListData.isIs_blocked()) {

                    if (businessListData.getBId() != null && !businessListData.getBId().equalsIgnoreCase("") && user_Id != null && !user_Id.equalsIgnoreCase(""))
                        getbussinessGroupInfo(encrypt_key, user_Id, businessListData.getBId(), businessListData.getBusinessName());

                }

                else
                {
                    showDialog(context,businessListData.getBId(),businessListData.getChat_group_id(),businessListData.getBusinessName(),businessListData.getBlock_side(),position);
                }

            }
        });


        holder.rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profile = new Intent(context, BusinessProfileActivity.class);
                //   profile.putExtra(BusinessProfileActivity.BUSSINESS_PROFILE_INFO, (Serializable) businessListData);
                profile.putExtra(BusinessProfileActivity.BUSSINESS_ID, businessListData.getBId());
                context.startActivity(profile);

            }
        });


    }



    private void showDialog(final Context context, final String bId, final String chatGroupId,String businessName ,String blockedBy, final int position) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_threadblocked_alert);
        dialog.setCancelable(true);
        dialog.show();
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.dialog);

        if(blockedBy.equalsIgnoreCase("customer")) {


            TextView tv_unblock = (TextView) dialog.findViewById(R.id.tv_cancel);
            TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
            tv_title.setText("Sorry! you can't chat because " +"you have blocked "+businessName);
            tv_unblock.setVisibility(View.VISIBLE);


/*********************** ***************0***/
            tv_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    MobiComMessageService.chatUpdateTag = 1;
                    setBlockBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNBLOCK_BUSINESS, bId, chatGroupId, position);
                    dialog.dismiss();

                }
            });

        }
        else
        {
            TextView tv_unblock = (TextView) dialog.findViewById(R.id.tv_cancel);
            TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
            tv_unblock.setVisibility(View.GONE);
            tv_title.setText("Sorry! you can't chat because "+ businessName +" have blocked you ");


        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


    }


    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public void notify(List<StoreListModel.DataData.BusinessListData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /***
     * Methods required for do selections, remove selections, etc.
     */

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    // get bussiness info code
    public void getbussinessGroupInfo(String encrypt_key, String c_user_id, final String bussinessId, final String businessName) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        /*
        *
        *
        *
        *   get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4
        * */
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);
/************************* **************************************/
        params.put("b_id", bussinessId);


        Call<BussinessGroupCreateModel> call = apiService.getGroupListInfo(params);
        call.enqueue(new Callback<BussinessGroupCreateModel>() {
            @Override
            public void onResponse(Call<BussinessGroupCreateModel> call, retrofit2.Response<BussinessGroupCreateModel> response) {
                pDialog.dismiss();

                BussinessGroupCreateModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                        GroupInfoModel groupInfoModel = new GroupInfoModel();
                        groupInfoModel.setGroupId("" + clientModel.getData().getChatGroup().getChatGroupId());
                        groupInfoModel.setGroupName("" + clientModel.getData().getChatGroup().getChatGroupName());
                        // groupmemberid

                        if (clientModel.getData().getChatGroup() != null && clientModel.getData().getChatGroup().getChatGroupUsers() != null && clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker() != null && clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker().size() > 0) {
                            List<String> groupmenberID = new ArrayList<>();
                            for (BussinessGroupCreateModel.Datadata.ChatGroupdata.ChatGroupUsersdata.BusinessWorkerdata bussinessWorkerData : clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessWorker()) {
                                groupmenberID.add(bussinessWorkerData.getBUserId());
                            }
                            groupInfoModel.setGroupMemberId(groupmenberID);
                        }

                        try {
                            // set admin of user
                            groupInfoModel.setAdmin("" + clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessAdmin().get(0).getBUserId());
                            groupInfoModel.setUserId("" + user_Id);
                            groupInfoModel.setBussinessName("" + businessName);
                            groupInfoModel.setBusinessId("" + bussinessId);
                            // create group code
                            ((StoreListActivity) context).createGroup(groupInfoModel);
                        }
                        catch (Exception e)
                        {

                            Utill.showCenteredToast(context, context.getString(R.string.something_went_wrong));

                        }
                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BussinessGroupCreateModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, context.getString(R.string.something_went_wrong));
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


                            list.get(position).setIs_blocked(false);
                            Utill.showCenteredToast(context, context.getString(R.string.unblock_business));
                            notifyDataSetChanged();

                        } else if(block_type == Constant.BLOCK_BUSINESS) {

                             list.get(position).setIs_blocked(true);
                             list.get(position).setBlock_side("customer");
                             Utill.showCenteredToast(context, context.getString(R.string.block_business));
                             notifyDataSetChanged();


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


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_storetitle, tv_message, tv_rate, tv_homeservice, tv_time;
        ImageView iv_rate, iv_notified, iv_tag, iv_homeservice, iv_isselected;
        ImageView ivr_business_logo;
        private RelativeLayout rl_profile, rl_b_chat;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            tv_storetitle = (TextView) itemView.findViewById(R.id.tv_row_store_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_row_message_first);
            tv_rate = (TextView) itemView.findViewById(R.id.tv_row_rate);
            rl_profile = (RelativeLayout) itemView.findViewById(R.id.rl_b_profile);
            rl_b_chat = (RelativeLayout) itemView.findViewById(R.id.rl_b_chat);


            //
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            iv_homeservice = (ImageView) itemView.findViewById(R.id.iv_home_service);
            iv_rate = (ImageView) itemView.findViewById(R.id.iv_rate);
            iv_notified = (ImageView) itemView.findViewById(R.id.iv_notification);
            iv_tag = (ImageView) itemView.findViewById(R.id.iv_tag);

            iv_isselected = (ImageView) itemView.findViewById(R.id.iv_isselected);
            ivr_business_logo = (ImageView) itemView.findViewById(R.id.ivr_row_iv_user);

            //tv_storetitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        }

    }


}

