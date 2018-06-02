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
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    Context context;
    List<CityListModel.citydata.CityListData> list;
    Chatlocalyshareprefrence chatlocalyshareprefrence;

    public CityListAdapter(Context context, List<CityListModel.citydata.CityListData> list) {
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


        holder.tv_name.setText(""+list.get(position).getName());

        if(chatlocalyshareprefrence.getCityId().equalsIgnoreCase(""+list.get(position).getCityId()))
            holder.iv_selected.setVisibility(View.VISIBLE);
        else
        holder.iv_selected.setVisibility(View.INVISIBLE);



        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(position).getIs_available().equalsIgnoreCase("1"))
                {
                    holder.iv_selected.setVisibility(View.VISIBLE);
                    chatlocalyshareprefrence.setCity_name(list.get(position).getName());
                    chatlocalyshareprefrence.setCity_ID(list.get(position).getCityId());

                    notifyDataSetChanged();
                    ((CityListActivity) context).setResult(MainActivity.SELECT_CITY, new Intent(context, MainActivity.class));
                    ((CityListActivity) context).finish();
                }
                else
                    Utill.showCenteredToast(context,context.getString(R.string.city_not_available));


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifydatasetChange(List<CityListModel.citydata.CityListData> list) {

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


        }
    }
/*

*//*
public void customDialog(final Context context, final List<CityListModel.citydata.CityListData> list) {
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

