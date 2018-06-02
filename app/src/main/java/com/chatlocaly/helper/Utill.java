package com.chatlocaly.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.chatlocaly.R;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.views.CustomActionbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class Utill {

    public static final String BASE_IMAGE =  "http://americankeysupply.com/images/";


    public interface ApiListener {

        void onSuccess(JSONObject jsonObject, int id);

    }


    public static String user_id = "", order_token = "";

    Chatlocalyshareprefrence sharedpreference;

    Dialog dialog;

    Context context;

    public static String radius = "20";


    public static JSONObject jsonObject;

    AlertDialog.Builder alertDialog = null;


    /*final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    final String CONFIG_CLIENT_ID = "AddiYhvOC8v5Ntcf1uczVu8p4j9ycYQkNkw-ja4nfA3l2_EwimGoeM1Vy5gTGs5wrrwpPU7hTwUuzovu";
    final int REQUEST_CODE_PAYMENT = 1;*/


    /* OpenSansRegular*/


    public static void Raleway(Context context, EditText editText, int typeface) {
        try {
            Typeface font = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
            editText.setTypeface(font, typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void keyboardHide(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }

    public static RequestBody encodeString(String s) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        /*byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);
*/
            return body;



    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static String convertFromOneTimeZoneToOhter(String date_time) {

        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = sdf.parse(date_time);
        } catch (Exception e) {
        }


        TimeZone fromTimezone = TimeZone.getTimeZone("GMT+0");//get Timezone object


        long fromOffset = fromTimezone.getOffset(date.getTime());//get offset
        long toOffset = TimeZone.getDefault().getOffset(date.getTime());

        long convertedTime = date.getTime() - (fromOffset - toOffset);
        Date d2 = new Date(convertedTime);
        return sdf.format(d2);
    }

    public static void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public static void keyboardShow(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTypeFace(TextView tv, String name, Context context) {
       Typeface externalFont = Typeface.createFromAsset(context.getAssets(),name);
       tv.setTypeface(externalFont);
    }

    public static void showCenteredToast(Context ctx, String msg) {
        try {
            Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*

 public  static void setActionBarText(ActionBarActivity activity, String title,String subTitle,int visibility) {
        CustomActionbar.customActionbar(activity, title,subTitle,visibility);
    }
*/



    public static void statusBarColor(Context ctx) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = ((Activity) ctx).getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ctx.getResources().getColor(R.color.background_color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectingToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String SetDate(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String SetDate1(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String SetCustomText(String text) {
        String custom_text = "";
        custom_text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        return custom_text;
    }


    public static String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));
    }


    public static String Decode_Base64(String text) {
        byte[] valueDecoded = org.apache.commons.codec.binary.Base64.decodeBase64(text.getBytes());
        return new String(valueDecoded);
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    public static int getYear(Date date1, Date date2) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(date1));
        return Integer.parseInt(simpleDateformat.format(date2)) - Integer.parseInt(simpleDateformat.format(date1));
    }

    public static void imageUplode(Context context, ImageView imageView,String imagePath){
        Glide.with(context)
                .load(BASE_IMAGE+imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

/* get  keybord height */



}












