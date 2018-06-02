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
import com.chatlocaly.adapter.LocalityListAdapter;
import com.chatlocaly.adapter.PriceListAdapter;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.singleton.FilterDataSingleTon;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment {


    private RecyclerView rView;
    private PriceListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private List<FilterModel.FilterData.PriceListData> priceListDataList;
    private Context context;
    private View view;
    public PriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_price, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        context = view.getContext();
        priceListDataList= FilterDataSingleTon.getInstance().getFilterModel().getData().getPriceList();
         /*--------------- rView ---------------*/
        rView = (RecyclerView) view.findViewById(R.id.rView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(mLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rView.getContext(), mLayoutManager.getOrientation());
        rView.addItemDecoration(mDividerItemDecoration);
        adapter = new PriceListAdapter(priceListDataList, context, 0);
        rView.setAdapter(adapter);
        /*--------------- rView ---------------*/
    }








}
