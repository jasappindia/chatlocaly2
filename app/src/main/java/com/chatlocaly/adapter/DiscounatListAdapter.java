package com.chatlocaly.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import com.chatlocaly.R;
import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.LocalityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashok on 14-Apr-16.
 */
public class DiscounatListAdapter extends RecyclerView.Adapter<DiscounatListAdapter.LocalityViewHolder> {
    public Context context;
    List<FilterModel.FilterData.DiscountListData> list;
    boolean isFooter = false;
    private int fragment_type_call = 0;
    String[]   localityName={" Greater Noida West "," Uttam Nagar","Noida Extension "," Indirapuram"," Raj Nagar Extension "," Sector 88 Faridabad "," Sector 1 Greater Noida West "};
    public DiscounatListAdapter(List<FilterModel.FilterData.DiscountListData> discountListDataList, Context context, int sendMessageType) {
        this.list = discountListDataList;
        isFooter = false;
        this.fragment_type_call = sendMessageType;
        this.context = context;

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final LocalityViewHolder contactViewHolder, final int i) {


        contactViewHolder.cb_selectLocality.setText(""+ list.get(i).getLabel());

        if(list.get(i).isSelected())
            contactViewHolder.cb_selectLocality.setChecked(true);
        else
            contactViewHolder.cb_selectLocality.setChecked(false);

        contactViewHolder.cb_selectLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(((CheckBox)v).isChecked())
                {
                    FilterActivity.filtercount++;
                    list.get(i).setSelected(true);
                    ((FilterActivity)context).changeClearTextcolor();

                }
                else
                {
                    FilterActivity.filtercount--;
                    list.get(i).setSelected(false);
                    ((FilterActivity)context).changeClearTextcolor();

                }





            }
        });


    }

    @Override
    public LocalityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_locality, viewGroup, false);
        return new LocalityViewHolder(itemView);
    }

    public void notify(List<FilterModel.FilterData.DiscountListData> list, int sendMessageType) {
        this.list = list;
        isFooter = false;
        this.fragment_type_call = sendMessageType;
        notifyDataSetChanged();
    }

    class LocalityViewHolder extends RecyclerView.ViewHolder {
        protected CheckBox cb_selectLocality;

        public LocalityViewHolder(View v) {
            super(v);
            cb_selectLocality = (CheckBox) v.findViewById(R.id.cb_locality);

        }


    }
}





