package com.chatlocaly.fragment.filter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chatlocaly.R;
import com.chatlocaly.adapter.BrandListAdapter;
import com.chatlocaly.adapter.LocalityListAdapter;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.singleton.FilterDataSingleTon;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrandFragment extends Fragment {


    private RecyclerView rView;
    private BrandListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private View view;
private EditText edt_search;
    private List<FilterModel.FilterData.BrandListData> brandListData;

    public BrandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_brand, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        context = view.getContext();
        brandListData= FilterDataSingleTon.getInstance().getFilterModel().getData().getBrandList();
        edt_search= (EditText) view.findViewById(R.id.etd_search);
         /*--------------- rView ---------------*/
        rView = (RecyclerView) view.findViewById(R.id.rView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(mLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rView.getContext(), mLayoutManager.getOrientation());
        rView.addItemDecoration(mDividerItemDecoration);
        adapter = new BrandListAdapter(brandListData, context, 0);
        rView.setAdapter(adapter);
        search(edt_search);
        /*--------------- rView ---------------*/
    }


    private void search(EditText searchView) {

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {

                if(adapter!=null)
                    adapter.getFilter().filter(newText);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
