package com.chatlocaly.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.BillShowActivity;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.InvoiceOrderListModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 12/20/2017.
 */
public class AdapterForBills extends RecyclerView.Adapter<AdapterForBills.MyViewHolder> {

    Context context;
    List<InvoiceOrderListModel.OrderList> orderList;
    private boolean isFooter = false;

    public AdapterForBills(Context context, List<InvoiceOrderListModel.OrderList> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_bills, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_payeeName.setText(Utill.capitalize(orderList.get(position).getBusinessName()));
        holder.tv_paidStatus.setText(Utill.capitalize(orderList.get(position).getOrderStatus()));
        holder.tv_paymentAmount.setText("Rs " + orderList.get(position).getTotal());
        holder.tv_dueDate.setText("" + orderList.get(position).getBillDate());
        holder.tv_sentOn.setText("" + orderList.get(position).getSend_date());
        holder.progressBar.setVisibility(View.GONE);
        if (orderList.get(position).getOrderStatus().equalsIgnoreCase("paid"))
            holder.tv_paidStatus.setTextColor(context.getResources().getColor(R.color.green));
        else
            holder.tv_paidStatus.setTextColor(context.getResources().getColor(R.color.red));


        if (isFooter) {
            if (position == (orderList.size() - 1)) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }
        } else {
            holder.progressBar.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        if (orderList != null)
            return orderList.size();
        else
            return 0;
    }

    public void notifiedDatalist(List<InvoiceOrderListModel.OrderList> orderList) {
        if (orderList != null && orderList.size() > 0)
            this.orderList = orderList;
        notifyDataSetChanged();

    }

    public void addFooter() {
        isFooter = true;
        notifyDataSetChanged();
    }

    public void removeFooter() {
        isFooter = false;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_sentOn, tv_paymentAmount, tv_paidStatus, tv_payeeName, tv_dueDate;
        LinearLayout ll_billLayout;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            ll_billLayout = (LinearLayout) itemView.findViewById(R.id.ll_billLayout);
            tv_sentOn = (TextView) itemView.findViewById(R.id.tv_sentOn);
            tv_paymentAmount = (TextView) itemView.findViewById(R.id.tv_paymentAmount);
            tv_paidStatus = (TextView) itemView.findViewById(R.id.tv_paidStatus);
            tv_payeeName = (TextView) itemView.findViewById(R.id.tv_payeeName);
            tv_dueDate = (TextView) itemView.findViewById(R.id.tv_dueDate);
            progressBar = (ProgressBar) itemView.findViewById(R.id.row_bill_list_pBar);


            ll_billLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, BillShowActivity.class);
                    intent.putExtra(Constants.ORDER_DETAILS, (Serializable) orderList.get(getAdapterPosition()));
                    context.startActivity(intent);


                }
            });
        }
    }

}
