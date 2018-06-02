package com.chatlocaly.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.CityListActivity;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.SearchActivity;
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.model.SearchKeyWord;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class KeyworldListAdapter extends RecyclerView.Adapter<KeyworldListAdapter.ViewHolder> {
    Context context;
    List<SearchKeyWord.Datadata.SearchKeyListdata> list;
    Chatlocalyshareprefrence chatlocalyshareprefrence;

    public KeyworldListAdapter(Context context, List<SearchKeyWord.Datadata.SearchKeyListdata> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();

        }
        chatlocalyshareprefrence=new Chatlocalyshareprefrence(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_citylist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.tv_name.setText(""+list.get(position).getSearchKey());


        holder.iv_selected.setVisibility(View.INVISIBLE);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifydatasetChange(List<SearchKeyWord.Datadata.SearchKeyListdata> list) {

        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_code, tv_name;
        ImageView iv_selected;
        RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_cityName);
            iv_selected=(ImageView) itemView.findViewById(R.id.iv_city_selected);
            rl_item=(RelativeLayout)itemView.findViewById(R.id.relative_country_list_row);

itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    ((SearchActivity)context).searchKeywordSet(getAdapterPosition());

    }
});
        }
    }
/*

*//*
public void customDialog(final Context context, final List<SearchKeyWord.Datadata.SearchKeyListdata> list) {
    dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

    dialog.setContentView(R.layout.popupwindowcitylistdialog);

    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
    RecyclerView recyclerView= (RecyclerView) dialog.findViewById(R.id.rView);

    CityListAdapter adapter=new CityListAdapter(context,list);
    recyclerView.setAdapter(adapter);


    *//****************** ***************************//*

    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
        @Override
        public void onClick(View view, int position) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                tv_cityName.setText(list.get(position).getName());

            }




        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));



    dialog.show();
    dialog.setCanceledOnTouchOutside(true);
}*/
}

