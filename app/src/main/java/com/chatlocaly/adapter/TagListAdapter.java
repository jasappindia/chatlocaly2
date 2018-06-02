package com.chatlocaly.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.api.conversation.MobiComMessageService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicommons.commons.core.utils.DateUtils;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.BusinessProfileActivity;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.fragment.TagFragments;
import com.chatlocaly.model.ThreadModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anjani on 18/12/17.
 */

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.ViewHolder> {

    private Context context;
    private List<ThreadModel.Datadata.MessageListdata> list;
    private SparseBooleanArray mSelectedItemsIds;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String user_Id, encrypt_key;
    TagFragments tagFragment;
    private String bussinessName;

    public TagListAdapter(Context context, List<ThreadModel.Datadata.MessageListdata> list, TagFragments tagFragment) {
        this.context = context;
        this.list = list;
        mSelectedItemsIds = new SparseBooleanArray();
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        user_Id = chatlocalyshareprefrence.getUserId();
        encrypt_key = chatlocalyshareprefrence.getEncryptKey();
        this.tagFragment = tagFragment;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_thread, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ThreadModel.Datadata.MessageListdata businessListData = list.get(position);



         String message="";

        if (businessListData.getBusinessName() != null && !businessListData.getBusinessName().equalsIgnoreCase(""))
            holder.tv_storetitle.setText("" + businessListData.getBusinessName());

        holder.tv_rate.setText("" + businessListData.getBusiness_rating());
        if(businessListData.getRating_count()!=0 )
            holder.iv_rate.setImageResource(R.mipmap.israted);
        else
            holder.iv_rate.setImageResource(R.mipmap.notrated);

      /*  if(businessListData.getToFullName()!=null && !TextUtils.isEmpty(businessListData.getToFullName()))
        {
               if(businessListData.getDesignation()!=null && !TextUtils.isEmpty(businessListData.getDesignation()))
                message = businessListData.getToFullName()+"("+businessListData.getDesignation()+"):" ;
                else
                message = businessListData.getToFullName() ;

        }
        holder.tv_message.setText( message+ "" + businessListData.getMessage());

*/

        if (businessListData.getToFullName() != null && !TextUtils.isEmpty(businessListData.getToFullName())) {
            if (businessListData.getDesignation() != null && !TextUtils.isEmpty(businessListData.getDesignation())&& !businessListData.getDesignation().equalsIgnoreCase("You"))
                message = businessListData.getToFullName() + " (" + businessListData.getDesignation() + "): ";
            else if (businessListData.getDesignation() != null && !TextUtils.isEmpty(businessListData.getDesignation())&& businessListData.getDesignation().equalsIgnoreCase("You"))
                message=" You: ";
            else
                message = businessListData.getToFullName();

        }
        if (businessListData.getMessage() != null)
            holder.tv_message.setText(message + "" + businessListData.getMessage());
        else
            holder.tv_message.setText(message + "");


        // home service code

        if (businessListData.getHome_services().toString() != null) {

            if (businessListData.getHome_services().toString().equalsIgnoreCase("no")) {
                holder.tv_homeservice.setText("" + "No home service ");
                holder.iv_homeservice.setImageResource(R.mipmap.nohomeservice);
            }
            else if (businessListData.getHome_services().toString().equalsIgnoreCase("yes")) {
                holder.tv_homeservice.setText("Upto  " + businessListData.getServices_km() + " Km");
                holder.iv_homeservice.setImageResource(R.drawable.ic_home);
            }
            else if (businessListData.getHome_services().toString().equalsIgnoreCase("everywhere")) {
                holder.tv_homeservice.setText("" + "Everywhere ");
                holder.iv_homeservice.setImageResource(R.drawable.ic_home);

            }

        } else {
            holder.tv_homeservice.setText("" + "No home service ");
            holder.iv_homeservice.setImageResource(R.mipmap.nohomeservice);}
        // business icon
        Utill.imageshow(context, holder.ivr_business_logo, "" + businessListData.getBusinessLogo());


        if (mSelectedItemsIds.get(position)) {

            holder.itemView.setBackgroundColor(mSelectedItemsIds.get(position) ? context.getResources().getColor(R.color.toolbar) : Color.WHITE);
            holder.iv_isselected.setVisibility(View.VISIBLE);

        } else {

            holder.iv_isselected.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);

        }

        // imageview  notified or

        if (businessListData.getCustomer_tag() != null &&  !businessListData.getCustomer_tag().equalsIgnoreCase("0"))
            holder.iv_tag.setImageResource(R.mipmap.istaged);
        else
            holder.iv_tag.setImageResource(R.mipmap.nottaqed);

        // imageview  notified

        if (businessListData.getCustomer_notification() != null && !businessListData.getCustomer_notification().equalsIgnoreCase("1"))
            holder.iv_notified.setImageResource(R.mipmap.isnotified);
        else
            holder.iv_notified.setImageResource(R.mipmap.notnotified);


        // date formate
        SimpleDateFormat simpleDateFormat = Utill.dateFormate(Constant.DATE_MM_DD_YY);
        Date date = new Date(businessListData.getCreatedAtTime());
        if (DateUtils.isSameDay(businessListData.getCreatedAtTime())) {

            SimpleDateFormat dateFormat=Utill.dateFormate(Constant.DATE_hh_mm_a);

            holder.tv_time.setText(""+dateFormat.format(date));



        } else if (DateUtils.isYesterday(businessListData.getCreatedAtTime())) {
            holder.tv_time.setText(R.string.yesterday);

        } else {
            holder.tv_time.setText("" + simpleDateFormat.format(date));
        }
        //int channelKey;
        int channelUnreadCount = new MessageDatabaseService(context).getUnreadMessageCountForChannel(businessListData.getGroupId());

        if(channelUnreadCount!=0) {
            holder.tv_item_chatcount.setVisibility(View.VISIBLE);
            holder.tv_item_chatcount.setText("" + channelUnreadCount);
        }
        else
            holder.tv_item_chatcount.setVisibility(View.INVISIBLE);

        /*****************************************           ******************************/




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

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public void notify(List<ThreadModel.Datadata.MessageListdata> list) {

        this.list = list;
        notifyDataSetChanged();

        if(TagFragments.iv_tag!=null)
        {
            if(list.size()>0)
            TagFragments.iv_tag.setVisibility(View.GONE);
               else
                TagFragments.iv_tag.setVisibility(View.VISIBLE);


        }
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






public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener
{
        TextView tv_storetitle, tv_message, tv_rate, tv_homeservice, tv_time,tv_item_chatcount;
        ImageView iv_rate, iv_notified, iv_tag, iv_homeservice, iv_isselected;
        ImageView ivr_business_logo;
        private RelativeLayout rl_profile, rl_b_chat,ll_complete_threadList;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            tv_storetitle = (TextView) itemView.findViewById(R.id.tv_row_store_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_row_message_first);
            tv_rate = (TextView) itemView.findViewById(R.id.tv_row_rate);
            rl_profile = (RelativeLayout) itemView.findViewById(R.id.rl_b_profile);
            rl_b_chat = (RelativeLayout) itemView.findViewById(R.id.rl_b_chat);
            tv_item_chatcount= (TextView) itemView.findViewById(R.id.tv_item_count);
           // ll_complete_threadList=(RelativeLayout) itemView.findViewById(R.id.ll_complete_thread_card);
            //
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            iv_homeservice = (ImageView) itemView.findViewById(R.id.iv_home_service);
            iv_rate = (ImageView) itemView.findViewById(R.id.iv_rate);
            iv_notified = (ImageView) itemView.findViewById(R.id.iv_notification);
            iv_tag = (ImageView) itemView.findViewById(R.id.iv_tag);
            tv_time = (TextView) itemView.findViewById(R.id.tv_row_time);
            iv_isselected = (ImageView) itemView.findViewById(R.id.iv_isselected);
            ivr_business_logo = (ImageView) itemView.findViewById(R.id.ivr_row_iv_user);

         //   tv_storetitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            itemView.setOnCreateContextMenuListener(this);




            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(getAdapterPosition()).getBlock_chat_group().equalsIgnoreCase("1"))
                {

                    if(list.get(getAdapterPosition()).isIs_blocked() && list.get(getAdapterPosition()).getBlock_side().equalsIgnoreCase("customer"))

                    {
                        showDialog(context, list.get(getAdapterPosition()).getbId(), list.get(getAdapterPosition()).getChatGroupId(), list.get(getAdapterPosition()).getBusinessName(), getAdapterPosition(),list.get(getAdapterPosition()).getBlock_side());
                    }
                    else
                    {
                        showDialog(context, list.get(getAdapterPosition()).getbId(), list.get(getAdapterPosition()).getChatGroupId(), list.get(getAdapterPosition()).getBusinessName(), getAdapterPosition(),list.get(getAdapterPosition()).getBlock_side());

                    }
                    /*   // for tag list update
                                MobiComMessageService.chatUpdateTag = 1;
                                tagFragment.setBlockBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNBLOCK_BUSINESS, list.get(getAdapterPosition()).getbId(), list.get(getAdapterPosition()).getChatGroupId(), getAdapterPosition());
     */               }
                else
                {
                    ApplozicBridge.launchIndividualGroupChatByClientGroupId(context, "" + list.get(getAdapterPosition()).getGroupId(), list.get(getAdapterPosition()).getbId(), list.get(getAdapterPosition()).getToFullName(), list.get(getAdapterPosition()).getBusinessName(),list.get(getAdapterPosition()).getChatGroupId());

                }            }
        });


        }



    private void showDialog(final Context context, final String bId, final String chatGroupId,String businessName ,final int position,String blockedBy) {




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
                    tagFragment.setBlockBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNBLOCK_BUSINESS, bId, chatGroupId,position);
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
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Menu Options");
        final ThreadModel.Datadata.MessageListdata messageListdata = list.get(getAdapterPosition());
        contextMenu.add("Business Info").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent profile = new Intent(context, BusinessProfileActivity.class);
                //   profile.putExtra(BusinessProfileActivity.BUSSINESS_PROFILE_INFO, (Serializable) businessListData);
                profile.putExtra(BusinessProfileActivity.BUSSINESS_ID, messageListdata.getBId());
                context.startActivity(profile);

                return false;
            }
        });


        if(!list.get(getAdapterPosition()).getBlock_side().equalsIgnoreCase("business")  ) {



            if (list.get(getAdapterPosition()).getBlock_chat_group().equalsIgnoreCase("1")) {
                contextMenu.add("Unblock").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // for tag list update
                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setBlockBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNBLOCK_BUSINESS, messageListdata.getbId(), messageListdata.getChatGroupId(), getAdapterPosition());

                        return false;
                    }
                });
            } else {
                contextMenu.add("Block").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // for tag list update
                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setBlockBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.BLOCK_BUSINESS, messageListdata.getbId(), messageListdata.getChatGroupId(), getAdapterPosition());

                        return false;
                    }
                });
            }
        }



        if(list.get(getAdapterPosition()).getBlock_chat_group().equalsIgnoreCase("1")) {
            if (list.get(getAdapterPosition()).getCustomer_tag().equalsIgnoreCase("1")) {
                contextMenu.add("Untag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // for tag list update

                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setTagBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNTAG, list.get(getAdapterPosition()).getbId(), getAdapterPosition());

                        return false;
                    }
                });


            }
        }


        if(list.get(getAdapterPosition()).getBlock_chat_group().equalsIgnoreCase("0")) {


            if (list.get(getAdapterPosition()).getCustomer_tag().equalsIgnoreCase("1")) {
                contextMenu.add("Untag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // for tag list update

                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setTagBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.UNTAG, list.get(getAdapterPosition()).getbId(), getAdapterPosition());

                        return false;
                    }
                });


            } else {
                contextMenu.add("Tag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // for tag list update
                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setTagBusiness(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.TAG, list.get(getAdapterPosition()).getbId(), getAdapterPosition());

                        return false;
                    }
                });
            }


            if (list.get(getAdapterPosition()).getCustomer_notification().equalsIgnoreCase("1")) {

                contextMenu.add("Unmute").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // for tag list update
                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setNotification(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.ADD_NOTIFICATION, messageListdata.getbId(), getAdapterPosition());

                        return false;
                    }
                });
            } else {

                contextMenu.add("Mute").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // for tag list update
                        MobiComMessageService.chatUpdateTag = 1;
                        tagFragment.setNotification(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), Constant.BLOCK_NOTIFIACTION, messageListdata.getbId(), getAdapterPosition());

                        return false;
                    }
                });

            }
        }


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {



        return false;
    }


    }


}

