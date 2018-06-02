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

import com.chatlocaly.R;
import com.chatlocaly.adapter.DiscounatListAdapter;
import com.chatlocaly.adapter.LocalityListAdapter;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.singleton.FilterDataSingleTon;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscountFragment extends Fragment {


    private RecyclerView rView;
    private DiscounatListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private List<FilterModel.FilterData.DiscountListData> listData;
    private Context context;
    private View view;
    public DiscountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discount, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        context = view.getContext();
        listData = FilterDataSingleTon.getInstance().getFilterModel().getData().getDiscountList();
         /*--------------- rView ---------------*/
        rView = (RecyclerView) view.findViewById(R.id.rView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(mLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rView.getContext(), mLayoutManager.getOrientation());
        rView.addItemDecoration(mDividerItemDecoration);
        adapter = new DiscounatListAdapter(listData, context, 0);
        rView.setAdapter(adapter);
        /*--------------- rView ---------------*/
    }

}
