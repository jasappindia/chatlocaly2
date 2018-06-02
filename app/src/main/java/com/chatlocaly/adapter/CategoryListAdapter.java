package com.chatlocaly.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applozic.mobicomkit.api.account.user.User;
import com.chatlocaly.R;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.fragment.MarketFragment;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;

import java.util.List;

/**
 * Created by Ashok on 18/12/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private Context context;
    private List<CategoryListModel.DataData.CategoryListData> list;


    public CategoryListAdapter(Context context, List<CategoryListModel.DataData.CategoryListData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CategoryListModel.DataData.CategoryListData categoryListData = list.get(position);

        holder.tv_category_title.setText(" " + categoryListData.getCatName());

        if (categoryListData.getSubCategories() != null && categoryListData.getSubCategories().size() > 0) {
            SubcategaryListAdapter adapter = new SubcategaryListAdapter(context, categoryListData.getSubCategories());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.rView.setLayoutManager(linearLayoutManager);
            holder.rView.setAdapter(adapter);
        }


        holder.tv_category_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public void notify(List<CategoryListModel.DataData.CategoryListData> list) {
         this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category_title;
        RecyclerView rView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_category_title = (TextView) itemView.findViewById(R.id.tv_category_title);
            rView = (RecyclerView) itemView.findViewById(R.id.rView);
        }

    }


}

