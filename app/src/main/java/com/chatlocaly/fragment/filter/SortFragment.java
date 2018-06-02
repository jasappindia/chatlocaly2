
package com.chatlocaly.fragment.filter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    View view;
    Context context;
    private CheckBox  ch_popurialty,ch_relevent;

    public static  final String SORTFRAGMENT_TAG=SortFragment.class.getName();
    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sort, container, false);
        init(view);

        // check box  change lisner
        ch_popurialty.setOnCheckedChangeListener(this);
        ch_relevent.setOnCheckedChangeListener(this);
        return view;

    }

    private void init(View view) {
         ch_popurialty=(CheckBox) view.findViewById(R.id.cb_popularity);
         ch_relevent=(CheckBox)view.findViewById(R.id.cb_relevance);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId())
        {






        }




    }
}
