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
import com.chatlocaly.singleton.singletonmodel.BrandListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashok on 14-Apr-16.
 */
public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.LocalityViewHolder> implements Filterable {
    public Context context;
    List<FilterModel.FilterData.BrandListData> list;
    List<FilterModel.FilterData.BrandListData> mFilteredList;
    boolean isFooter = false;
    private int fragment_type_call = 0;
    String[]   localityName={"Unbranded ","Aachi "," Tata ","Aashirvaad","Madhur ","Patanjali ","Amul "};
    public BrandListAdapter(List<FilterModel.FilterData.BrandListData> brandListDataList, Context context, int sendMessageType)
    {
        this.list = brandListDataList;
        mFilteredList = brandListDataList;
        isFooter = false;
        this.fragment_type_call = sendMessageType;
        this.context = context;

    }



    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public void onBindViewHolder(final LocalityViewHolder contactViewHolder, final int i) {


        contactViewHolder.cb_selectLocality.setText(""+ mFilteredList.get(i).getBrandName());



        contactViewHolder.cb_selectLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                {
                       FilterActivity.filtercount++;
                       mFilteredList.get(i).setSelected(true);
                      ((FilterActivity)context).changeClearTextcolor();

                }
                else
                {
                    FilterActivity.filtercount--;
                    mFilteredList.get(i).setSelected(false);
                    ((FilterActivity)context).changeClearTextcolor();

                }
            }

        });


        if(mFilteredList.get(i).isSelected())
            contactViewHolder.cb_selectLocality.setChecked(true);
        else
            contactViewHolder.cb_selectLocality.setChecked(false);

    }

    @Override
    public LocalityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_locality, viewGroup, false);
        return new LocalityViewHolder(itemView);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (list != null && list.size() > 0) {
                    if (charString.isEmpty()) {
                        mFilteredList = list;

                    } else {

                        ArrayList<FilterModel.FilterData.BrandListData> filteredList = new ArrayList<>();

                        for (FilterModel.FilterData.BrandListData androidVersion : list) {

                            if (androidVersion.getBrandName().toLowerCase().contains(charString)) {
                                filteredList.add(androidVersion);
                            }
                        }

                        mFilteredList = filteredList;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<FilterModel.FilterData.BrandListData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notify(List<FilterModel.FilterData.BrandListData> list, int sendMessageType) {
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





