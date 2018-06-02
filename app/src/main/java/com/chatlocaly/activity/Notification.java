package com.chatlocaly.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.other.RecyclerClick_Listener;
import com.chatlocaly.other.RecyclerTouchListener;
import com.chatlocaly.adapter.RingtoneListAdapter;
import com.chatlocaly.model.RingToneListModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.utill.BasicUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity implements View.OnClickListener {

    protected ImageView ivArrowBack;
    protected TextView tvTitleBar;
    protected CheckBox cbOnOffNotification;
    protected TextView tvOnOffNotification;
    protected TextView tvAudio;
    protected TextView tvVibration, tv_notificationSound, tv_audio;
    protected CheckBox cbVibrationOnOff;
    List<RingToneListModel> ringToneListModelList;
    Chatlocalyshareprefrence chatlocalyshareprefrence;

    LinearLayout ll_vibration;
    Context context;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_notification);
        initView();
        tv_audio.setText(chatlocalyshareprefrence.getRING_TONE_LIST_title());
        // check multiple device login
        new BasicUtill().CheckStatus(this);


    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.iv_arrowBack) {
            onBackPressed();

        } else if (view.getId() == R.id.cb_vibration_on_off) {
            if (((CheckBox) view).isChecked()) {
                chatlocalyshareprefrence.setVibration_status(true);
            } else
                chatlocalyshareprefrence.setVibration_status(false);


        } else if (view.getId() == R.id.tv_notificationSound) {

            ringToneListModelList = listRingtones();
            customDialog(context, ringToneListModelList);


        }
        if (view.getId() == R.id.cb_on_off_notification) {




            if (((CheckBox) view).isChecked()) {
                updateUserNotificationStatus(chatlocalyshareprefrence.getUserId(), "1", chatlocalyshareprefrence.getEncryptKey());


            } else {
                cbOnOffNotification.setChecked(true);

            /*    ll_vibration.setVisibility(View.INVISIBLE);
                chatlocalyshareprefrence.setNotication_status(false);
                updateUserNotificationStatus(chatlocalyshareprefrence.getUserId(), "0", chatlocalyshareprefrence.getEncryptKey());
*/

            showDialog(context);

            }

        }

    }

    private void initView() {
        context = Notification.this;
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
        ringToneListModelList = new ArrayList<>();
        ll_vibration = (LinearLayout) findViewById(R.id.ll_vibration);

        ivArrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        ivArrowBack.setOnClickListener(Notification.this);
        tvTitleBar = (TextView) findViewById(R.id.tv_titleBar);
        tvTitleBar.setOnClickListener(Notification.this);
        cbOnOffNotification = (CheckBox) findViewById(R.id.cb_on_off_notification);
        tvOnOffNotification = (TextView) findViewById(R.id.tv_on_off_notification);
        tvAudio = (TextView) findViewById(R.id.tv_audio);
        tvVibration = (TextView) findViewById(R.id.tv_vibration);
        tv_notificationSound = (TextView) findViewById(R.id.tv_notificationSound);
        tv_audio = (TextView) findViewById(R.id.tv_audio);
        cbVibrationOnOff = (CheckBox) findViewById(R.id.cb_vibration_on_off);
        cbVibrationOnOff.setOnClickListener(this);
        tv_notificationSound.setOnClickListener(this);
        cbOnOffNotification.setOnClickListener(this);

        // value set
        if (chatlocalyshareprefrence.getVibration_status())
            cbVibrationOnOff.setChecked(true);
        else
            cbVibrationOnOff.setChecked(false);
        //notification
        if (chatlocalyshareprefrence.getNotication_status()) {
            cbOnOffNotification.setChecked(true);
            ll_vibration.setVisibility(View.VISIBLE);
        } else {
            cbOnOffNotification.setChecked(false);
            ll_vibration.setVisibility(View.INVISIBLE);
        }

    }


    public List<RingToneListModel> listRingtones() {
        List<RingToneListModel> ringToneListModels = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {


            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            RingToneListModel ringToneListModel = new RingToneListModel(title, ringtoneURI);
            ringToneListModels.add(ringToneListModel);
            // Do something with the title and the URI of ringtone
        }
        RingToneListModel ringToneListModel = new RingToneListModel("Default Sound", Uri.parse("Default Sound"));
        ringToneListModels.add(0, ringToneListModel);

        return ringToneListModels;
    }


    public void customDialog(final Context context, final List<RingToneListModel> list) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.popupwindowcitylistdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
        final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_ringtone);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final RingtoneListAdapter adapter = new RingtoneListAdapter(context, list);
        recyclerView.setAdapter(adapter);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {

                if (dialog != null && dialog.isShowing()) {

                    chatlocalyshareprefrence.setRingToneList(list.get(position).getRingtoneListUri());
                    chatlocalyshareprefrence.setRING_TONE_LIST_title(list.get(position).getRingtoneTitle());
                    tv_audio.setText(list.get(position).getRingtoneTitle());
                    adapter.notifydatasetChange(list);
//                    dialog.dismiss();

                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);


//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), Uri.parse(prefrence.getringtoneUri()));
//                        mp.start();
                        if(r!=null)
                        {
                            if (r.isPlaying())
                                r.stop();
                        }
                        r = RingtoneManager.getRingtone(getBaseContext(), Uri.parse(chatlocalyshareprefrence.getRingToneList()));

                        //playing sound alarm
                        r.play();
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(5000);  //30000 is for 30 seconds, 1 sec =1000
                                    if (r.isPlaying())
                                        r.stop();   // for stopping the ringtone
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        th.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    sendNotification("title", "check message body");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(r!=null)
                {
                    if (r.isPlaying())
                        r.stop();
                }
            }
        });


    }


    private void sendNotification(String title, String messageBody) {


        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);

        /**/


   /*     broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        Intent intent2 = new Intent(REQUEST_ACCEPT);
        //intent.putExtra("Key", value);
      //  intent.putExtra("key", value);
        broadcaster.sendBroadcast(intent2);

*/
       /* switch (id)
        {
            case 1:


                break;

            case 1:


                break;
            case 1:


                break;





        }*/

        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);



      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notification_icon);
            notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.newlogoinlook);
        }
*/

        if (chatlocalyshareprefrence.getVibration_status()) {
            //Vibration
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        } else
            notificationBuilder.setVibrate(new long[]{0L});


        if (chatlocalyshareprefrence.getRingToneList().equalsIgnoreCase("Default Sound"))

            notificationBuilder.setSound(defaultSoundUri);
        else//Ton
            notificationBuilder.setSound(Uri.parse(chatlocalyshareprefrence.getRingToneList()));

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setSmallIcon(R.mipmap.lobo);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        //


    }

    /*userName*/
    public void updateUserNotificationStatus(String c_user_id, final String notificationStatus, String encrypt) {

        final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
        pDialogofimaged.setCancelable(false);
        //      progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("c_user_id", Utill.createPartFromString(c_user_id));
        params.put("notification", Utill.createPartFromString(notificationStatus));
        params.put("encrypt_key", Utill.createPartFromString(encrypt));
        Call<ResultModel> call = null;
        call = apiService.uploadFile(null, null, params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                pDialogofimaged.dismiss();
                ResultModel resultModel = response.body();
                if (resultModel != null && resultModel.getData() != null && resultModel.getData().getResultCode().equalsIgnoreCase("1")) {

                    Utill.showCenteredToast(context, "" + getString(R.string.notificationUpdated));

                    if(notificationStatus.equalsIgnoreCase("0")) {


                        ll_vibration.setVisibility(View.GONE);
                        chatlocalyshareprefrence.setNotication_status(false);
                        cbOnOffNotification.setChecked(false);

                    }

                    else
                    {
                        ll_vibration.setVisibility(View.VISIBLE);
                        chatlocalyshareprefrence.setNotication_status(true);
                         cbOnOffNotification.setChecked(true);
                    }


                } else {
                    Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                pDialogofimaged.dismiss();
            }
        });


    }


    private void showDialog(final Context context) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCancelable(true);
        dialog.show();
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.dialog);
        TextView tv_leave = (TextView) dialog.findViewById(R.id.tv_leave_on);
        TextView tv_turn_off = (TextView) dialog.findViewById(R.id.tv_turn_off);

        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(""+getString(R.string.str_notification_dialog_message));

/*********************** ***************0***/
        tv_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });

        tv_turn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Utill.isConnectingToInternet(context)) {
                    dialog.dismiss();
                    ll_vibration.setVisibility(View.GONE);
                    chatlocalyshareprefrence.setNotication_status(false);
                    updateUserNotificationStatus(chatlocalyshareprefrence.getUserId(), "0", chatlocalyshareprefrence.getEncryptKey());
                }
                else
                {
                    Utill.showCenteredToast(context,getString(R.string.str_check_internet_connection));
                }

            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


    }







}
