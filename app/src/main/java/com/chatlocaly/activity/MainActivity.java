package com.chatlocaly.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.firebasenotification.MyFirebaseMessagingService;
import com.chatlocaly.other.RecyclerClick_Listener;
import com.chatlocaly.other.RecyclerTouchListener;
import com.chatlocaly.adapter.CategoryViewpager.CategoryViewpagerAdapter;
import com.chatlocaly.adapter.CityListAdapter;
import com.chatlocaly.fragment.FragmentDrawer;
import com.chatlocaly.model.CityListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener, TabLayout.OnTabSelectedListener {

    public static final int SELECT_CITY = 0744;
    public static final String Tag = "MainActivity";

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private DrawerLayout drawer;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tv_threadstab, tv_tagtab;
    public static TextView tv_cityName;
    public static TextView tv_tabcounter, tv_tag_tag_count;
    private ProgressDialog progressBar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private String secKey = "adkjfd";
    private List<CityListModel.citydata.CityListData> citylist;
    public static ImageView iv_cityIcon,iv_search;

    private Dialog dialog;
    //    get device id
    TelephonyManager telephonyManager;
    String deveviceId = "";
    int backCount=0;
    private LocalBroadcastManager broadcaster;
    // get  email  function

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (getIntent().getExtras() != null) {


            if (getIntent().getExtras().containsKey("data")) {

                String redirection_key = "";

                if (getIntent().getExtras().get("data") != null) {

                    Object messageData = getIntent().getExtras().get("data");
                    try {
                        JSONObject object = new JSONObject(messageData.toString());
                        redirection_key=object.getString("redirection_key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                     Intent intent;

                    if (redirection_key.toString().toUpperCase().contains("BILL_UPDATED")) {
                        intent = new Intent(this, BillListShowActivity.class);
                        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent2 = new Intent(MyFirebaseMessagingService.REQUEST_ACCEPT);
                        //intent.putExtra("Key", value);
                        //  intent.putExtra("key", value);
                        broadcaster.sendBroadcast(intent2);


                    } else if (redirection_key.toString().toUpperCase().contains("NEW_BILL")) {
                        intent = new Intent(this, BillListShowActivity.class);

                        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent2 = new Intent(MyFirebaseMessagingService.REQUEST_ACCEPT);
                        //intent.putExtra("Key", value);
                        //  intent.putExtra("key", value);
                        broadcaster.sendBroadcast(intent2);

                    } else
                        intent = new Intent(this, SplashScreenActivity.class);

                    startActivity(intent);


                }


            }
        }

         // check multiple device login
         new BasicUtill().CheckStatus(context);

        // get city id when first time login

        if (chatlocalyshareprefrence.getCityId().equalsIgnoreCase("0"))
            getCityList(chatlocalyshareprefrence.getUserId(), secKey);
        if (!chatlocalyshareprefrence.getCityId().equalsIgnoreCase("0")) {
            tv_cityName.setText("" + chatlocalyshareprefrence.getCityName());

        }



        iv_search.setOnClickListener(this);


    }


    private void init() {
        context = MainActivity.this;
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        // set sort variable
        chatlocalyshareprefrence.setSortByPopularity(true);




        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_cityName = (TextView) mToolbar.findViewById(R.id.toolbar_cityname);
        tv_cityName.setText("Delhi");
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        tv_cityName.setOnClickListener(this);
        iv_cityIcon=(ImageView) mToolbar.findViewById(R.id.iv_city_icon);

        iv_search=(ImageView) mToolbar.findViewById(R.id.iv_search);

        //mToolbar.setTitleTextColor(getResources().getColor(R.color.text_color_light));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawer, mToolbar);
        drawerFragment.setDrawerListener(this);
        // tablayout code
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new CategoryViewpagerAdapter(getSupportFragmentManager(), MainActivity.this));
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            if (i == 1) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(R.layout.tab_layout);
            }
            if (i == 2) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(R.layout.tag_tab_layout);
            }
        }
        tv_threadstab = (TextView) tabLayout.findViewById(R.id.tab_title);
        tv_tabcounter = (TextView) tabLayout.findViewById(R.id.tabcounter);
        tv_tabcounter.setVisibility(View.INVISIBLE);

        tv_tagtab = (TextView) tabLayout.findViewById(R.id.tag_tab_title);
        tv_tag_tag_count = (TextView) tabLayout.findViewById(R.id.tag_tabcounter);
        tv_tag_tag_count.setVisibility(View.INVISIBLE);

        tabLayout.addOnTabSelectedListener(this);
        // login user task in applozic
        downloadfile();

    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        if (intent.getExtras() != null) {


            if (intent.getExtras().containsKey("data")) {

                String redirection_key = "";

                if (intent.getExtras().get("data") != null) {

                    Object messageData = intent.getExtras().get("data");
                    try {
                        JSONObject object = new JSONObject(messageData.toString());
                        redirection_key=object.getString("redirection_key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (redirection_key.toString().toUpperCase().contains("BILL_UPDATED")) {
                        intent = new Intent(this, BillListShowActivity.class);
                        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent2 = new Intent(MyFirebaseMessagingService.REQUEST_ACCEPT);
                        //intent.putExtra("Key", value);
                        //  intent.putExtra("key", value);
                        broadcaster.sendBroadcast(intent2);


                    } else if (redirection_key.toString().toUpperCase().contains("NEW_BILL")) {
                        intent = new Intent(this, BillListShowActivity.class);

                        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent2 = new Intent(MyFirebaseMessagingService.REQUEST_ACCEPT);
                        //intent.putExtra("Key", value);
                        //  intent.putExtra("key", value);
                        broadcaster.sendBroadcast(intent2);

                    } else
                        intent = new Intent(this, SplashScreenActivity.class);

                    startActivity(intent);


                }


            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(unreadCountBroadcastReceiver);
    }

    @Override
    public void onClick(View view) {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {

            iv_cityIcon.setVisibility(View.VISIBLE);
            tv_cityName.setText(""+ chatlocalyshareprefrence.getCityName());

            drawer.closeDrawer(GravityCompat.START);

        }
        else if (view.getId() == R.id.toolbar_cityname) {

       /*     if(popupWindow!=null && popupWindow.isShowing())*/
       /*            popupWindow.dismiss();*/
/*
*/

       /*     else {*/
       /*         if (citylist != null && citylist.size() > 0)*/
       /*             popUpindowtaskstatus(citylist);*/
       /*     }*/
            // customDialog(context,citylist);
            startActivityForResult(new Intent(context, CityListActivity.class), SELECT_CITY);


        } else if (view == tv_cityName) {
            startActivityForResult(new Intent(context, CityListActivity.class), SELECT_CITY);
        }
        else if(view==iv_search)
        {
            startActivity(new Intent(context,SearchActivity.class));
        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        // set sort variable

        chatlocalyshareprefrence.setSortByPopularity(true);
       setThreadTabCount(new MessageDatabaseService(context).getUnreadConversationCount());

//Add this in your Activity onResume(), to start observing it

        LocalBroadcastManager.getInstance(this).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));


    }


    BroadcastReceiver unreadCountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                int unreadCount = new MessageDatabaseService(context).getUnreadConversationCount();
                //Update unread count in UI
                setThreadTabCount(unreadCount);
            }
        }
    };

    public static void setThreadTabCount(int count) {
        if (count == 0)
            tv_tabcounter.setVisibility(View.GONE);
        else {

            tv_tabcounter.setVisibility(View.VISIBLE);
            tv_tabcounter.setText("" + count);
        }

    }

    public static void setTagTabCount(int count) {
        if (count == 0)
            tv_tag_tag_count.setVisibility(View.GONE);
        else {
            tv_tag_tag_count.setVisibility(View.VISIBLE);
            tv_tag_tag_count.setText("" + count);
        }

    }

    PopupWindow popupWindow;

    public void popUpindowtaskstatus(final List<CityListModel.citydata.CityListData> list) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.rView);

        CityListAdapter adapter = new CityListAdapter(context, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //    ***************** **************************

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    tv_cityName.setText(list.get(position).getName());

                     chatlocalyshareprefrence.setCity_ID(list.get(position).getCityId());
                    chatlocalyshareprefrence.setCity_name(list.get(position).getName());


                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        //    tv_decline.setOnClickListener(this);
        popupWindow.showAsDropDown(tv_cityName);

    }
/******************************************                     ********************************/
/*
public void customDialog(final Context context, final List<CityListModel.citydata.CityListData> list) {
    dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

    dialog.setContentView(R.layout.popupwindowcitylistdialog);

    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
    RecyclerView recyclerView= (RecyclerView) dialog.findViewById(R.id.rView);

    CityListAdapter adapter=new CityListAdapter(context,list);
    recyclerView.setAdapter(adapter);


    */

    /****************** ***************************//*

    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
        @Override
        public void onClick(View view, int position) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                tv_cityName.setText(list.get(position).getName());

            }




        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));



    dialog.show();
    dialog.setCanceledOnTouchOutside(true);
}*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_CITY) {
                tv_cityName.setText("" + chatlocalyshareprefrence.getCityName());
            }


        }


    }

    @Override
    public void onDrawerItemSelected(View view, int position) {








    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {


        if (tab.getPosition() == 1) {
            tv_threadstab.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_tabcounter.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedbuttonyellow));
            tv_tabcounter.setTextColor(getResources().getColor(R.color.off_white));


        }
        if (tab.getPosition() == 2) {
            tv_tagtab.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_tag_tag_count.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedbuttonyellow));
            tv_tag_tag_count.setTextColor(getResources().getColor(R.color.off_white));


        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {


        if (tab.getPosition() == 1) {
            tv_threadstab.setTextColor(getResources().getColor(R.color.text_color_light));
            tv_tabcounter.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedbutton));
            tv_tabcounter.setTextColor(getResources().getColor(R.color.white));

        }
        if (tab.getPosition() == 2) {

            tv_tagtab.setTextColor(getResources().getColor(R.color.text_color_light));
            tv_tag_tag_count.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedbutton));
            tv_tag_tag_count.setTextColor(getResources().getColor(R.color.white));

        }


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    // get fragment replace

    public void replaceFragment(Fragment inputfragment, String fragmenttitle) {


        Fragment fragment = inputfragment;

       /* if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack(null).commit();

            //  replaceFragment(fragment,title);

        }

*/
        if (fragment != null) {


            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    Log.e("fragment name", fragmenttitle + " " + getSupportFragmentManager().getBackStackEntryAt(i).getName());
                    if (fragmenttitle.equalsIgnoreCase((getSupportFragmentManager().getBackStackEntryAt(i).getName()))) {
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name", fragmenttitle + " " + count);

                        getSupportFragmentManager().popBackStack(fragmenttitle, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        int count2 = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name2", fragmenttitle + " " + count2);

                    }

                }
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack(fragmenttitle).commit();

        }

    }


    /// delete data from databse


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    // get city list
    public void getCityList(String user_id, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
      //  final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", user_id);
        params.put("encrypt_key", accessTocken);
        Call<CityListModel> call = apiService.getCityList(params);
        call.enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {


                CityListModel cityListModel = response.body();
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {


                        citylist = cityListModel.getData().getCityList();

                        if (chatlocalyshareprefrence.getCityId().equalsIgnoreCase("0"))

                        {

                            if (citylist.get(0).getName() != null) {
                                tv_cityName.setText("" + citylist.get(0).getName());


                                chatlocalyshareprefrence.setCity_ID(citylist.get(0).getCityId());
                                chatlocalyshareprefrence.setCity_name(citylist.get(0).getName());

                            }

                        }

                    } else
                        Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                }
           //     pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {
               // pDialog.dismiss();

                Toast.makeText(context, "Check Internet Connection !", Toast.LENGTH_SHORT).show();


            }
        });
    }


    public void onBackPressed() {




        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

              if (getSupportFragmentManager().getBackStackEntryCount() > 0) {


                  super.onBackPressed();

              } else {

                  if(backCount==0)
                  {
                      backCount++;
                      Utill.showCenteredToast(context,getResources().getString(R.string.back_first_message));
                  }
                  else

                  {
                      backCount++;
                      MainActivity.this.finish();
                    //  Utill.showCenteredToast(context,getResources().getString(R.string.back_first_message));

                  }

         /*
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("" + getString(R.string.str_exit).toUpperCase());
                alertDialogBuilder.setIcon(R.mipmap.logonew);

                alertDialogBuilder
                        .setMessage("" + getString(R.string.str_exitmessage))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                MainActivity.this.finish();
                            }
                        })


                        .setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
         */       //    }
            }
        }
    }



    public void downloadfile()
    {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call=apiInterface.downloadFile("http://www.shubrashifal.com/sync.json");

        call.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {


                //    http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-34/20180411102205pul3ej2pxl.png
                writeResponseBodyToDisk(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });




    }



    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "sync.text");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("file download", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}







