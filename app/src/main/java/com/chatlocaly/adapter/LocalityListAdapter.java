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
public class LocalityListAdapter extends RecyclerView.Adapter<LocalityListAdapter.LocalityViewHolder> implements Filterable {
    public Context context;
    List<FilterModel.FilterData.LocalityListData> list;
    List<FilterModel.FilterData.LocalityListData> mFilteredList;
    boolean isFooter = false;
    private int fragment_type_call = 0;
    String[]   localityName={" Greater Noida West "," Uttam Nagar","Noida Extension "," Indirapuram"," Raj Nagar Extension "," Sector 88 Faridabad "," Sector 1 Greater Noida West "};
    public LocalityListAdapter(List<FilterModel.FilterData.LocalityListData> localityListData, Context context, int sendMessageType) {
      this.list = localityListData;
        mFilteredList = localityListData;
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


        contactViewHolder.cb_selectLocality.setText(""+ mFilteredList.get(i).getName());

      /*  String message = mFilteredList.get(i).getText_Message().replace("/n", "").trim();
        contactViewHolder.tv_message.loadDataWithBaseURL(null, "" + message, "text/html", "utf-8", null);

        contactViewHolder.tv_subject.setText("" + mFilteredList.get(i).getSubject());
        // sent_on
        contactViewHolder.tv_date.setText("" + Utill.setDateChangeFormate("" + mFilteredList.get(i).getSent_On()));
        contactViewHolder.progressDialog.setVisibility(View.GONE);*/


      if(mFilteredList.get(i).isSelected())
          contactViewHolder.cb_selectLocality.setChecked(true);
      else
          contactViewHolder.cb_selectLocality.setChecked(false);

        contactViewHolder.cb_selectLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((CheckBox)v).isChecked()) {
                    FilterActivity.filtercount++;
                    mFilteredList.get(i).setSelected(true);
                    ((FilterActivity) context).changeClearTextcolor();
                } else {
                    FilterActivity.filtercount--;
                    mFilteredList.get(i).setSelected(false);

                    ((FilterActivity) context).changeClearTextcolor();

                }


            }

        });



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

                        ArrayList<FilterModel.FilterData.LocalityListData> filteredList = new ArrayList<>();

                        for (FilterModel.FilterData.LocalityListData androidVersion : list) {

                            if (androidVersion.getName().contains(charString)) {

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
                mFilteredList = (ArrayList<FilterModel.FilterData.LocalityListData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notify(List<FilterModel.FilterData.LocalityListData> list, int sendMessageType) {
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





