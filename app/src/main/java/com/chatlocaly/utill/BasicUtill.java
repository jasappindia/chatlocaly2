package com.chatlocaly.utill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chatlocaly.activity.SplashScreenActivity;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.CheckStatusModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by windows on 3/12/2018.
 */

public class BasicUtill {
    Chatlocalyshareprefrence preference;
    private AlertDialog alertdialog;

    public  void CheckStatus(final Context context) {
    preference=new Chatlocalyshareprefrence(context);

//        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", preference.getLoginKey());
        param.put("c_user_id", preference.getUserId());


        Call<CheckStatusModel> call = apiServices.checkStatus(param);
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {
//                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        CheckStatusModel.Datadata data = response.body().getData();
                        if (preference.getLoginKey() != null) {
                            if (!data.getLoginKey().equals(preference.getLoginKey())) {
                                loginAlert(context);
                                //layout.setVisibility(View.INVISIBLE);
                            } else if (!data.getAppVersion().equalsIgnoreCase(Constants.APP_VERSION)) {
                                appVersionAlert(context);
                            }
                            //  'BLOCKED', 'APPROVED', 'UNAPPROVED'
                            /*else if (data.getStatus().equalsIgnoreCase("BLOCKED")) {
                                blockAlert(data.get,context);
                                layout.setVisibility(View.INVISIBLE);

                            }*/ /*else if (data.getStatus().equalsIgnoreCase("UNAPPROVED")) {
                                reviewAlert(context);
                                layout.setVisibility(View.INVISIBLE);

                            } else if (data.getStatus().equalsIgnoreCase("APPROVED")&&i==1) {

                                *//*if (preference.getBusinessROlE().equals(Constants.ADMIN))
                                    onApprovedAdmin(context);
                                else
                                    onApprovedWorker(context);*//*
                            }
*/
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        });

    }
    public void loginAlert(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been logged out!");
        builder.setMessage("You are already login with some other device");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    preference.logout();
                    Intent intent = new Intent(context, SplashScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);

                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preference.logout();
                alertdialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }


    public void appVersionAlert(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You are using an older version!");
        builder.setMessage("Please get the latest version from playstore.");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent sendtoApp = new Intent(Intent.ACTION_VIEW);
                sendtoApp.setData(Uri.parse("market://details?id=" + "co.inlook"));
                context.startActivity(sendtoApp);
            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }
   /* public void blockAlert(String blockMessage, final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You are blocked out!");
        builder.setMessage(blockMessage);
        builder.setPositiveButton("Appeal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(context, AppealActivity.class));
                ((Activity)context).finish();

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        *//*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        *//*
        alertdialog.show();
    }
*/
    public void reviewAlert(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been blocked out!");
        builder.setMessage("We will reveiew your appeal and get back to you shortly");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }



    public static String getDateFromString(String str)
    {
//       String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
          SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        try {
            date = format.parse(str);
            format2.format(date);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return  format2.format(date);
    }
    public static Date getDateFormat( long milliseconds )
    {
//        long yourmilliseconds = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultdate = new Date(milliseconds);
        System.out.println(sdf.format(resultdate));
        return resultdate;
    }
    public static String getDate(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yy");
        return fullDateFormat.format(date);
    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),      view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public static void sendIntent(Context context) {
        Intent intent = new Intent();
        ((Activity)context).setResult(RESULT_OK, intent);
        ((Activity)context).finish();
    }

    public static void sendCancelIntent(Context context) {

        Intent intent = new Intent();
        ((Activity)context).setResult(RESULT_CANCELED, intent);
        ((Activity)context).finish();
    }
    public static Date getWeekDate(int weeks){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, weeks * 7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthStartDate(int months){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthEndDate(int months){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

     public  static  String getDateFormatLocal(String str)
  {
//      SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
      Date date = null;
      try {
          date = format.parse(str);
          format2.format(date);

          System.out.println(date);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return  format2.format(date);

  }

}


