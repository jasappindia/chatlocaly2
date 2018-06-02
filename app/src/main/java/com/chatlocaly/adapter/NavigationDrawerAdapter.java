package com.chatlocaly.adapter;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.AboutActivity;
import com.chatlocaly.activity.BillListShowActivity;
import com.chatlocaly.activity.BillShowActivity;
import com.chatlocaly.activity.SettingActivity;
import com.chatlocaly.model.DrawerItem;
import com.chatlocaly.model.UserProfileModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<DrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Chatlocalyshareprefrence preference;

    public NavigationDrawerAdapter(Context context, List<DrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
         MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());


//        holder.iv.setImageResource(current.getDrawable());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    context.startActivity(new Intent(context, BillListShowActivity.class));
                else if (position == 1)
                    context.startActivity(new Intent(context, SettingActivity.class));
                else if (position == 2) {
                    Utill.sendEmail(context, "", "");

                } else if (position == 3) {

                    showRateDialog(context);
                } else if (position == 4)
                    context.startActivity(new Intent(context, AboutActivity.class));


            }
        });


    }

    private void showRateDialog(final Context context) {


        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_rate_us);
        TextView textView = (TextView) dialog.findViewById(R.id.tv_ok);
        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating_bar);

        RelativeLayout relativeLayout=dialog.findViewById(R.id.rl_rate);
         // rate bar change color

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ratingBar.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utill.sendPlayStorePage(context);
                dialog.dismiss();
                //

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notify(List<DrawerItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv;
        LinearLayout ll_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            iv = (ImageView) itemView.findViewById(R.id.ivDrawerIcon);

        }
    }







}