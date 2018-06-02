package com.chatlocaly.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.ServiceDetailActivity;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.model.ServiceModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder>
{

    public static ServiceModel.Datadata.ServiceListdata productListdata;
    Context context;
    List<ServiceModel.Datadata.ServiceListdata> productList;
    BusinessProfileInfoModel.Datadata.BusinessDetaildata info;
    int counter = 0;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;

    public ServicesAdapter(Context context, BusinessProfileInfoModel.Datadata.BusinessDetaildata info) {
        this.productList = info.getServices();
        this.context = context;
        this.info = info;
        chatlocalyshareprefrence=new Chatlocalyshareprefrence(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_recyclerview, parent, false);
        return new ServicesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(productList.get(position).getDiscount().equals("0"))
        {

            holder.tv_productPerDiscount.setVisibility(View.GONE);
            holder.tv_prodiscount.setVisibility(View.GONE);
            holder.tv_proPrice.setText("Rs "+ Utill.getPriceFormatted(""+Utill.getRoundOffFunction(Double.parseDouble(productList.get(position).getServicePrice()))));

        }
        else {
            holder.tv_prodiscount.setVisibility(View.VISIBLE);
            holder.tv_productPerDiscount.setVisibility(View.VISIBLE);
            holder.tv_productPerDiscount.setText("" +productList.get(position).getDiscount()+"%off"   );
            holder.tv_proPrice.setText("Rs "+Utill.getPriceFormatted(""+getdiscountprice(Float.parseFloat(productList.get(position).getServicePrice()),Integer.parseInt(productList.get(position).getDiscount()))));
            holder.tv_prodiscount.setText(Utill.getPriceFormatted(productList.get(position).getServicePrice()));
            holder.tv_prodiscount.setPaintFlags(holder.tv_prodiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        holder.tv_serviceLable.setText(""+productList.get(position).getServiceLabel());
        holder.tv_productName.setText(""+productList.get(position).getServiceName());
        Glide.with(context).load(productList.get(position).getServiceImage()).into(holder.iv_prodImage);
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            if (productList.size() > 0 && productList.size() < 6) {
                return productList.size();
            } else return 6;
        } else return 0;
    }

    /***************************************                                 ********************/
// get bussiness info code
    public void getbussinessGroupInfo(String encrypt_key, final String c_user_id, String bussinessId) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);
        /*
        *
        *
        *
        * get_chat_group?encrypt_key=dfgfdg&c_user_id=5&b_id=4
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
                        // set admin of user
                        groupInfoModel.setAdmin(clientModel.getData().getChatGroup().getChatGroupUsers().getBusinessAdmin().get(0).getBUserId());
                        groupInfoModel.setUserId(c_user_id);
                        groupInfoModel.setBussinessName(clientModel.getData().getChatGroup().getBusinessDetail().getBusinessName());
                        // create group code
                        createGroup(groupInfoModel);

                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<BussinessGroupCreateModel> call, Throwable t) {

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


        if (Utill.isRegisterdAtApplozic(context)) {


            final AlGroupInformationAsyncTask.GroupMemberListener channelInfoTask = new AlGroupInformationAsyncTask.GroupMemberListener() {
                @Override
                public void onSuccess(Channel channel, Context context) {
                    Log.i("Group ", "Details " + channel);

                    if (channel.getKey() == null) {
                        // logout user data
                        CreateGroup createGroup = new CreateGroup();
                        createGroup.execute(groupInfoModel);
                        //Logout success

                    } else {

                        Intent intent = new Intent(context, ServiceDetailActivity.class);
                        intent.putExtra(Constants.PRODUCT_DETAIL, productListdata);
                        intent.putExtra(Constant.BUSINESSNAME, groupInfoModel.getBussinessName());
                        intent.putExtra(Constant.GROUPID, groupInfoModel.getGroupId());
                        intent.putExtra(Constant.CHANNELKEY, channel.getKey());
                        intent.putExtra(Constant.GROUPNAME, groupInfoModel.groupName);
                        context.startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Channel channel, Exception e, Context context) {

                    Log.i("Group ", "Exception " + channel);

                    if (channel == null) {
                        CreateGroup createGroup = new CreateGroup();
                        createGroup.execute(groupInfoModel);
                    }
                }
            };


            AlGroupInformationAsyncTask alGroupInformationAsyncTask = new AlGroupInformationAsyncTask(context, groupInfoModel.getGroupId(), channelInfoTask);
            alGroupInformationAsyncTask.execute();


        } else {
            Log.i("storeListActivity", "user not login");

        }
    }

    /***************************************  update rating of  user  *******************************/

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

                   // Utill.showCenteredToast(context, "channel key successfully ");
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodiscount, tv_proPrice, tv_productName, tv_productPerDiscount,tv_serviceLable;
        ImageView iv_prodImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
            tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
            tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);
            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);
            tv_serviceLable = (TextView) itemView.findViewById(R.id.tv_serviceLable);
            tv_productPerDiscount = (TextView) itemView.findViewById(R.id.tv_discount_per);

            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);
            iv_prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    getbussinessGroupInfo(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), productList.get(getAdapterPosition()).getBId());
                    productListdata = productList.get(getAdapterPosition());

                    Intent intent = new Intent(context, ServiceDetailActivity.class);
                    intent.putExtra(Constants.PRODUCT_DETAIL,productListdata);
                    context.startActivity(intent);


                }
            });


        }
    }

    public int getdiscountprice(float price, int persent_discount) {
        int discountprice = 0;
        discountprice = (int) ((price * persent_discount)/100);
        discountprice=((int)price)-discountprice;
        return discountprice;
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

                    // update channel key
                    updateGroupInfoDetails(chatlocalyshareprefrence.getEncryptKey(), chatlocalyshareprefrence.getUserId(), groupID, "" + channel.getKey());

                    Intent intent = new Intent(context, ServiceDetailActivity.class);
                    intent.putExtra(Constants.PRODUCT_DETAIL, productListdata);
                    intent.putExtra(Constant.BUSINESSNAME, bussinessName);
                    intent.putExtra(Constant.GROUPID, groupID);
                    intent.putExtra(Constant.CHANNELKEY, channel.getKey());
                    intent.putExtra(Constant.GROUPNAME, groupname);
                    context.startActivity(intent);


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


    }


}
