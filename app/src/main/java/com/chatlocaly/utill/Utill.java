package com.chatlocaly.utill;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Utill extends Activity {




    public static final String EVENT_GROUP_ID = "er_group_id";
    public static final String EVENT_ID = "er_id";
    public static final String IS_SHOW_RSVP = "show_rsvp";
    private static final String EXCEPTION = "java.lang.IllegalStateException: Expected";

    public static String IMAGE_URL = "http://184.154.53.181/tutorgyan_new/upload/";
    public static String key = "tutorbdl64330ed9gyan";
    public static String USERID = "used_id";
    public static String USERTYPE = "userType";
    public static int age_limit = 10;
    public static String user_id = "0", order_token = "";
    public static String radius = "20";
    public static JSONObject jsonObject;

    public static String LISTPOSITION = "listposition";
    public static String BASE_URL = "www.inlook.co";
    Dialog dialog;
    Context context;
    AlertDialog.Builder alertDialog = null;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    public static String capitazileword(String role) {
        return role.substring(0, 1).toUpperCase() + role.substring(1);
    }


    //  public Geocoder geocoder;

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

    public static String getDatehour(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(" hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    // get date from milisecond
    public static String getDateYYYYMMdd(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    // get date from milisecond 2017-08-26 16:52
    public static String getDateYYYY_MM_dd_hh_mm(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public static void keyboardShow(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCenteredToast(Context ctx, String msg) {


        try {


            if (msg != null) {
                String str = msg.toString().toLowerCase();
                Log.e("message ", str);
                if (str.contains(Utill.BASE_URL)) {
                    msg = "Check internet Connection";
                } else if (str.contains(Utill.EXCEPTION))
                    msg = "Something went wrong, Try Later";

            }


            if (msg != null && !msg.equalsIgnoreCase("")) {
                Toast toast = Toast.makeText(ctx, msg, 100);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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

    // 2017-08-06 18:30:00
    public static String SetDate(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static long convertDateToSectnd(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);

        } catch (ParseException e) {

        }
        return date1.getTime();
    }

    public static long convertDateToSec(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy/MM/dd").parse(date);

        } catch (ParseException e) {

        }
        return date1.getTime();
    }

    public static String convertDateFormatetoHoure(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    //  2017-08-06 18:30:00
    public static String convertDateFormateDDMMYYYYSentEvent(String date) {
        String date1 = "";
        try {
            Log.e("date", date + "" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(date));
            date1 = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }


    //  08/25/2017, 06:47 PM

    //  2017-08-06 18:30:00
    public static long convertDateFormateToMilisecond(String date) {
        Date date1 = null;
        try {
            Log.e("date", date + "" + new SimpleDateFormat("MM/dd/yyyy,hh:mm a").parse(date));
            date1 = new SimpleDateFormat("MM/dd/yyyy, hh:mm a").parse(date);
            return date1.getTime();

        } catch (ParseException e) {
            Log.e("date", date + "   " + e.toString());

        }
        return 0;
    }

    public static String convertDateFormatetoHoureSentEvent(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("hh:mm ").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String convertDateFormateDDMMYYYY(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String convertDateFormateDDMMMYYhhmm(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd MMM yy, hh:mm").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));

        } catch (Exception e) {

        }
        return date1;
    }

    //"2017-06-09 12:16:09"

    //
    //  "2017-06-09 13:31:48"
    public static String SetDate1(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String setDateChangeFormate(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));

        } catch (ParseException e) {
        }
        return date1;
    }

    public static String SetCustomText(String text) {
        String custom_text = "";
        custom_text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        return custom_text;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        /*************** add commons code jar file *************/
        return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));
    }

    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            address = address.replaceAll(" ", "%20");
            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();
            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            //  getLatLong(jsonObject);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    /*   public static String Decode_Base64(String text) {
           byte[] valueDecoded = Base64.decodeBase64(text.getBytes());
           return new String(valueDecoded);
       }
   */
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

    public static long getCurrentDate() {
        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        return currentCalendar.getTime().getTime();


    }

    public static void imageShow(Context context, ImageView imageView, String imagePath) {
try {


    Glide.with(context)
            .load(imagePath)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //  .error(R.mipmap.user_icon)
            .into(imageView);
}
catch (Exception e)
{

}

    }

    public static void imageShowLarge(Context context, ImageView imageView, String imagePath) {


        Glide.with(context)
                .load(imagePath)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(500, 350)
              //  .error(R.drawable.noimagefound)
                .fitCenter()
                .into(imageView);


    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static void sendSms(String mobileNumber, String message, Context context) {


        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + mobileNumber));
        smsIntent.putExtra("sms_body", " Hey friend - Start using  EventApp.Its new way to connect.");
        context.startActivity(smsIntent);
    }

    public static void showsuccessDialog(final Context context, String message) {

     /*   final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_method_eventselect);
        dialog.show();
        dialog.setCancelable(false);

        TextView ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView title = (TextView) dialog.findViewById(R.id.tv_title);

        title.setText("" + message);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
*/

    }

    public static void snakbarShow(View view, String message) {
        try {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            layout.setPadding(0, 0, 0, 0);//set padding to 0

            snackbar.show();
        } catch (Exception e) {
            Log.e("snack bar", e.toString());
        }
    }

    // get date from milisecond 2017-08-26 16:52
    public static String getDateYYYY_MM_dd_hh_mm_ss(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        TimeZone timezone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timezone);
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String convertmilisecondToUtctime(long miliseccond) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseccond);
        return sdf.format(calendar.getTime());


    }

    public static String convertmilisecondToLocaltime(long miliseccond) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseccond);
        return sdf.format(calendar.getTime());


    }

    // convert utc time to local time zone in android
    public static String convertDateUtcToDateToLocaldate(String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date1 = null;
        try {
            date1 = formatter.parse(date);

        } catch (ParseException e) {

        }

        return convertmilisecondToLocaltime(date1.getTime());
    }

// dialog of

    // convert bitmap to string
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            Log.e("image ", e.toString());

            return null;
        }
    }

    // fragment replace with title in android

    /*

     public void customDialog(final Context context) {
         sharedpreference = new LoyaltySharedpreference(context);
         dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

         dialog.setContentView(R.layout.language_dialog);

         TextView txt_header = (TextView) dialog.findViewById(R.id.dialog_txt_header);

         TextView txt_select = (TextView) dialog.findViewById(R.id.alert_txt_select);
         TextView txt_cancel = (TextView) dialog.findViewById(R.id.alert_txt_cancel);
         TextView txt_english = (TextView) dialog.findViewById(R.id.txt_english);
         TextView txt_german = (TextView) dialog.findViewById(R.id.txt_german);


         final LinearLayout ll_english = (LinearLayout) dialog.findViewById(R.id.ll_english);
         final LinearLayout ll_german = (LinearLayout) dialog.findViewById(R.id.ll_german);

         final ImageView iView_english_check = (ImageView) dialog.findViewById(R.id.iView_english_right);
         final ImageView iView_german_check = (ImageView) dialog.findViewById(R.id.iView_german_right);

         if (sharedpreference.getLANGUAGE().equalsIgnoreCase("ENGLISH")) {
             iView_english_check.setVisibility(View.VISIBLE);
             iView_german_check.setVisibility(View.INVISIBLE);
         } else if (sharedpreference.getLANGUAGE().equals("GERMAN")) {
             iView_english_check.setVisibility(View.INVISIBLE);
             iView_german_check.setVisibility(View.VISIBLE);
         } else {
             iView_english_check.setVisibility(View.INVISIBLE);
             iView_german_check.setVisibility(View.INVISIBLE);
         }

         ll_english.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 iView_english_check.setVisibility(View.VISIBLE);
                 iView_german_check.setVisibility(View.INVISIBLE);
                 ll_english.setBackgroundColor(Color.parseColor("#EEEEEE"));
                 ll_german.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
             }
         });

         ll_german.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 iView_english_check.setVisibility(View.INVISIBLE);
                 iView_german_check.setVisibility(View.VISIBLE);
                 ll_german.setBackgroundColor(Color.parseColor("#EEEEEE"));
                 ll_english.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
             }
         });

         txt_select.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 dialog.dismiss();

                 if (iView_english_check.getVisibility() == View.VISIBLE) {
                     sharedpreference.saveLanguage("ENGLISH");
                     setLocale("en", context);
                 } else {
                     sharedpreference.saveLanguage("GERMAN");
                     setLocale("de", context);
                 }
             }
         });

         txt_cancel.setOnClickListener(new View.OnClickListener() {

             public void onClick(View v) {
                 dialog.dismiss();
             }
         });
         dialog.show();
         dialog.setCanceledOnTouchOutside(true);
     }

     public void setLocale(String lang, Context context) {
         Locale myLocale = new Locale(lang);
         Resources res = context.getResources();
         DisplayMetrics dm = res.getDisplayMetrics();
         Configuration conf = res.getConfiguration();
         conf.locale = myLocale;
         res.updateConfiguration(conf, dm);
         if (!sharedpreference.getUserId().equals("0")) {
             Intent i = new Intent(context, MainActivity.class);
             i.putExtra("open_tab", ((MainActivity) context).fragment_position);
             context.startActivity(i);
             MainActivity.toolbar_text.setText(MainActivity.nav_item[((MainActivity) context).fragment_position]);
             ((Activity) context).finish();
         } else {
             Intent i = new Intent(context, Splash_First_Activity.class);
             context.startActivity(i);
             ((Activity) context).finish();

         }

     }
 */
    public String getDateYYYY_MM_dd(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void redirectPlayStore(FragmentActivity activity) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public void sendEmail(Context context) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"alljasappusers@jasapp.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Tutor Gyaan Queries");
        i.putExtra(Intent.EXTRA_TEXT, " Enter text here ... ");
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareApp(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<p>https://play.google.com/store/apps/details?id=" + context.getPackageName() + "</p>"));
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }

    public void replaceCharecterInstring(String s) {
        String mobile = s.replaceAll("[\\-\\+\\(\\)]", "");

    }

    public void replacefragment(Fragment inputfragment, String title, Context context) {


        Fragment fragment = inputfragment;
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_Activity_Fragmentcontenarlayout, fragment).addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

*/

/*
        if (fragment != null) {


            if(context.getSupportFragmentManager().getBackStackEntryCount()>0)
            {
                for(int i=0;i<context.getSupportFragmentManager().getBackStackEntryCount();i++)
                {
                    Log.e("fragment name",title+" "+getSupportFragmentManager().getBackStackEntryAt(i).getName());
                    if(title.equalsIgnoreCase((getSupportFragmentManager().getBackStackEntryAt(i).getName())))
                    {
                        int count=getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name",title+" "+count);

                        getSupportFragmentManager().popBackStackImmediate(title,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        int count2=getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name2",title+" "+count2);

                    }

                }
            }

            getSupportFragmentManager().beginTransaction().addToBackStack(title).replace(R.id.home_Activity_Fragmentcontenarlayout, fragment).commit();

        }*/

    }

    public boolean isConnected(Context context) {

        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
    // convert string  to bitmap

    private void add_contact() {

// Creates a new Intent to insert a contact
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
// Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
    /*
 * Inserts new data into the Intent. This data is passed to the
 * contacts app's Insert screen
 */
// Inserts an email address
        // intent.putExtra(Intents.Insert.EMAIL, mEmailAddress.getText())
/*
 * In this example, sets the email type to be a work email.
 * You can set other email types as necessary.
 */
        // .putExtra(Intents.Insert.EMAIL_TYPE, CommonDataKinds.Email.TYPE_WORK)
// Inserts a phone number
        //   .putExtra(Intents.Insert.PHONE, mPhoneNumber.getText())
/*
 * In this example, sets the phone type to be a work phone.
 * You can set other phone types as necessary.
 */
        //  .putExtra(Intents.Insert.PHONE_TYPE, Phone.TYPE_WORK);


        startActivity(intent);

    }


    public interface ApiListener {

        void onSuccess(JSONObject jsonObject, int id);

    }/*
*//******************* **************//*
public List<Contact> readContacts() {


    List<Contact> list = new ArrayList<>();
    ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null);


    if (cur.getCount() > 0) {
        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String pic = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            String phone = null;
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Log.e("name", name);
                // get the phone number
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {

                    String phonemobile = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phonemobile = phonemobile.replaceAll("[\\-\\+\\(\\)]", "");

                    phone = phonemobile.replaceAll(" ", "");
                        *//*   if(pCur.getCount()>1)
                        return;
                     *//*
                    Log.e("mobile", phonemobile);
                    Contact contact = new Contact("0", name, phone, "", "false", "false", pic);
                    list.add(contact);

                }
                pCur.close();
            }
        }
    }
    return list;
}*/
}
