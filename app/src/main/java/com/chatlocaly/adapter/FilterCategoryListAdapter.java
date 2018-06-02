package com.chatlocaly.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import com.chatlocaly.R;
import com.chatlocaly.model.FilterCategoryListModel;
import com.chatlocaly.model.LocalityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashok on 14-Apr-16.
 */
public class FilterCategoryListAdapter extends RecyclerView.Adapter<FilterCategoryListAdapter.LocalityViewHolder> {
    public Context context;
    List<FilterCategoryListModel> list;

    String[]  subcategaryTitle={" Departmental Store  ","  Fruit Vegetable Retail"," Milk Dairy "," Sweet Shops "," Bakeries  "};

    public FilterCategoryListAdapter(List<FilterCategoryListModel> discountItems, Context context) {
        this.list = discountItems;
            this.context = context;

    }



    @Override
    public int getItemCount() {
        return /*mFilteredList.size()*/5;
    }

    @Override
    public void onBindViewHolder(final LocalityViewHolder contactViewHolder, int i) {


        contactViewHolder.cb_selectLocality.setText(""+ subcategaryTitle[i]);

      /*  String message = mFilteredList.get(i).getText_Message().replace("/n", "").trim();
        contactViewHolder.tv_message.loadDataWithBaseURL(null, "" + message, "text/html", "utf-8", null);

        contactViewHolder.tv_subject.setText("" + mFilteredList.get(i).getSubject());
        // sent_on
        contactViewHolder.tv_date.setText("" + Utill.setDateChangeFormate("" + mFilteredList.get(i).getSent_On()));
        contactViewHolder.progressDialog.setVisibility(View.GONE);*/


    }

    @Override
    public LocalityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filtercategorylist, viewGroup, false);
        return new LocalityViewHolder(itemView);
    }



    public void notify(List<FilterCategoryListModel> list, int sendMessageType) {
        this.list = list;
         notifyDataSetChanged();
    }

    class LocalityViewHolder extends RecyclerView.ViewHolder {
        protected CheckBox cb_selectLocality;

        public LocalityViewHolder(View v) {
            super(v);
            cb_selectLocality = (CheckBox) v.findViewById(R.id.cb_filtercategoryList);

        }


    }
}





