package com.chatlocaly.fragment.filter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.model.HomeServiceSelected;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.singleton.FilterDataSingleTon;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import static com.chatlocaly.R.id.rangeSeekbar;
import static com.chatlocaly.R.id.tv_max;
import static com.chatlocaly.R.id.tv_min;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeServiceFragment extends Fragment {

    public static  final String HOMESERVICE_TAG=HomeServiceFragment.class.getName();

    View view;
    Context context;
    CrystalSeekbar  seekbar;
    TextView tv_minValue,tv_maxValue;
    Chatlocalyshareprefrence chatlocalyshareprefrence;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        context=getActivity();
        seekbar= (CrystalSeekbar) view.findViewById(R.id.rangeSeekbar);
        tv_maxValue= (TextView) view.findViewById(R.id.tv_max);
        tv_minValue= (TextView) view.findViewById(R.id.tv_min);
        seekbar.setBarColor(getResources().getColor(R.color.graylight));
        seekbar.setBarHighlightColor(getResources().getColor(R.color.colorPrimary));
        seekbar.setLeftThumbColor(getResources().getColor(R.color.graylight));
        seekbar.setLeftThumbHighlightColor(getResources().getColor(R.color.colorPrimary));
//
//        app:bar_color="@color/graylight"
//        app:bar_highlight_color="@color/colorPrimary"
//        app:left_thumb_color="@color/colorPrimary"
//        app:left_thumb_color_pressed="@color/colorPrimary"


        // set listener
        seekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {


            @Override
            public void valueChanged(Number value) {


                if(Integer.parseInt(""+value)>99)
                {
                    seekbar.setBarColor(getResources().getColor(R.color.graylight));
                    seekbar.setBarHighlightColor(getResources().getColor(R.color.colorPrimary));
                    seekbar.setLeftThumbColor(getResources().getColor(R.color.colorPrimary));
                    seekbar.setLeftThumbHighlightColor(getResources().getColor(R.color.colorPrimary));


                    HomeServiceSelected homeServiceSelected=new HomeServiceSelected();
                    homeServiceSelected.setService_km(String.valueOf(value));
                    homeServiceSelected.setHome_service("Everywhere");
                    FilterDataSingleTon.getInstance().setHomeServiceSelected(homeServiceSelected);
                    tv_maxValue.setText("Everywhere");

                }

              else   if(Integer.parseInt(""+value)<1)
                {

                    seekbar.setBarColor(getResources().getColor(R.color.graylight));
                    seekbar.setBarHighlightColor(getResources().getColor(R.color.colorPrimary));
                    seekbar.setLeftThumbColor(getResources().getColor(R.color.graylight));
                    seekbar.setLeftThumbHighlightColor(getResources().getColor(R.color.colorPrimary));


                    HomeServiceSelected homeServiceSelected=new HomeServiceSelected();
                    homeServiceSelected.setService_km(String.valueOf(value));
                    homeServiceSelected.setHome_service("Everywhere");
                    FilterDataSingleTon.getInstance().setHomeServiceSelected(homeServiceSelected);
                    tv_maxValue.setText("All");
/*
                    tv_maxValue.setText(" Up to  "+String.valueOf(value)+" km");
*/

                }

                else {

                    seekbar.setBarColor(getResources().getColor(R.color.graylight));
                    seekbar.setBarHighlightColor(getResources().getColor(R.color.colorPrimary));
                    seekbar.setLeftThumbColor(getResources().getColor(R.color.colorPrimary));
                    seekbar.setLeftThumbHighlightColor(getResources().getColor(R.color.colorPrimary));


                    HomeServiceSelected homeServiceSelected=new HomeServiceSelected();
                    homeServiceSelected.setService_km(String.valueOf(value));
                    homeServiceSelected.setHome_service("Yes");
                    FilterDataSingleTon.getInstance().setHomeServiceSelected(homeServiceSelected);

                    tv_maxValue.setText(" Up to  " + String.valueOf(value) + " km");

                }



            }


        });




    }



}
