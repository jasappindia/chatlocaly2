package com.chatlocaly.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.model.StoreListModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by anjani on 18/12/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<StoreListModel.DataData.BusinessListData> list;

    public ProductListAdapter(Context context, List<StoreListModel.DataData.BusinessListData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);






        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        StoreListModel.DataData.BusinessListData businessListData = list.get(position);


        holder.tv_storetitle.setText("" + businessListData.getBusinessName());
        holder.tv_rate.setText("" + businessListData.getBusinessRating());
        holder.tv_message.setText("" + businessListData.getLastConversion());
        holder.tv_homeservice.setText("" + businessListData.getHomeServices());







    }








    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 10);
    }

    public void notify(List<StoreListModel.DataData.BusinessListData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_storetitle, tv_message, tv_rate, tv_homeservice, tv_time;
        ImageView iv_rate, iv_notified, iv_tag, iv_homeservice, iv_isselected;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            tv_storetitle = (TextView) itemView.findViewById(R.id.tv_row_store_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_row_message_first);
            tv_rate = (TextView) itemView.findViewById(R.id.tv_row_rate);

            //
            tv_homeservice = (TextView) itemView.findViewById(R.id.tv_row_home_service);
            iv_homeservice = (ImageView) itemView.findViewById(R.id.iv_home_service);
            iv_rate = (ImageView) itemView.findViewById(R.id.iv_rate);
            iv_notified = (ImageView) itemView.findViewById(R.id.iv_notification);
            iv_tag = (ImageView) itemView.findViewById(R.id.iv_tag);

            iv_isselected = (ImageView) itemView.findViewById(R.id.iv_isselected);


        }

    }


}

