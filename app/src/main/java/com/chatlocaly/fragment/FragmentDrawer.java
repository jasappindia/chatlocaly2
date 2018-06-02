package com.chatlocaly.fragment;

/**
 * Created by Ashok on 12/6/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.UserProfileActivity;
import com.chatlocaly.adapter.NavigationDrawerAdapter;
import com.chatlocaly.model.DrawerItem;
import com.chatlocaly.model.UserProfileModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDrawer extends Fragment {

    public static NavigationDrawerAdapter adapter;
    private static String TAG = FragmentDrawer.class.getSimpleName();
    private static String[] titles = null;
    public TextView tv_userName;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private Context context;
    private ImageView iv_userImage;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    RelativeLayout rl_sendbusiness;

    public FragmentDrawer() {

    }

    public static List<DrawerItem> getData() {

        List<DrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            DrawerItem navItem = new DrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }


        return data;
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);


    }

    @Override
    public void onResume() {
        super.onResume();
        //
        tv_userName.setText("" + Utill.capitalize(chatlocalyshareprefrence.getFullName()));
        Utill.imageshow(context, iv_userImage, chatlocalyshareprefrence.getUserImage());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        iv_userImage = (ImageView) layout.findViewById(R.id.iv_userImage);
        tv_userName = (TextView) layout.findViewById(R.id.tv_username);
        rl_sendbusiness=(RelativeLayout) layout.findViewById(R.id.rl_sendbusiness);
        /************************                          ***********************/
        getUserProfile(chatlocalyshareprefrence.getUserId(), chatlocalyshareprefrence.getEncryptKey());

        tv_userName.setText("" + Utill.capitalize(chatlocalyshareprefrence.getFullName()));
        Utill.imageshow(context, iv_userImage, chatlocalyshareprefrence.getUserImage());


        titles = getResources().getStringArray(R.array.nav_drawer_labels_contacts);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);

                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        iv_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity (new Intent(context, UserProfileActivity.class));

            }
        });
        tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(context, UserProfileActivity.class));


            }
        });


        rl_sendbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String businessApplicationPackageName="com.chatlocalybusiness";
             Utill.sendPlayStore(context,businessApplicationPackageName);


            }
        });

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();

                MainActivity.tv_cityName.setText(getString(R.string.app_name));
                MainActivity.iv_cityIcon.setVisibility(View.INVISIBLE);
                MainActivity.iv_search.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

                MainActivity.tv_cityName.setText(chatlocalyshareprefrence.getCityName());
                MainActivity.iv_cityIcon.setVisibility(View.VISIBLE);
                MainActivity.iv_search.setVisibility(View.VISIBLE);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        //   mDrawerToggle.setDrawerIndicatorEnabled(false);

        //    Drawable drawable = ResourcesCompat.getDrawable(getResources(),   R.drawable.bars, getActivity().getTheme());

        //    mDrawerToggle.setHomeAsUpIndicator(drawable);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public void getUserProfile(String c_user_id, String encrypt) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id",/*Utill.createPartFromString(*/c_user_id/*)*/);
        params.put("encrypt_key", /*Utill.createPartFromString(*/encrypt/*)*/);
        Call<UserProfileModel> call = null;
        call = apiService.getUserProfileById(params);
        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                UserProfileModel resultModel = response.body();
                if (resultModel != null && resultModel.getGetUserProfile() != null && resultModel.getGetUserProfile().getResultCode().equalsIgnoreCase("1")) {

                    if (resultModel.getGetUserProfile().getCFullName() != null && !resultModel.getGetUserProfile().getCFullName().equals("")) {
                        chatlocalyshareprefrence.setFullName(resultModel.getGetUserProfile().getCFullName());
                        tv_userName.setText(Utill.capitalize(resultModel.getGetUserProfile().getCFullName()));
                    }
                    if (resultModel.getGetUserProfile().getCProfileImage() != null && !resultModel.getGetUserProfile().getCProfileImage().equals("")) {

                        chatlocalyshareprefrence.setImage("" + resultModel.getGetUserProfile().getCProfileImage());
                        Utill.imageshow(context, iv_userImage, resultModel.getGetUserProfile().getCProfileImage());

                    }


                }
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {


            }
        });


    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    return
                            true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }


}