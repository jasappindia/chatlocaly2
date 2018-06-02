package com.chatlocaly.firebasenotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.chatlocaly.R;
import com.chatlocaly.activity.BillListShowActivity;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.SplashScreenActivity;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

/**
 * Created by anjani on 5/9/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static String REQUEST_ACCEPT = "message";
    Chatlocalyshareprefrence chatlocalyshareprefrence;
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    int id;
    private LocalBroadcastManager broadcaster;
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
 /*   private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }
*/

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        remoteMessage.getData();

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
/*

            if (*/
/* Check if data needs to be processed by long running job *//*
 true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
*/

        }


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> payload = remoteMessage.getData();
            try {
                JSONObject object = new JSONObject(payload);
                JSONObject APPLOZIC_01 = new JSONObject(object.getString("APPLOZIC_01"));
                JSONObject message1 = new JSONObject(APPLOZIC_01.getString("message"));
                String message2 = message1.getString("message");

                if(remoteMessage.getNotification()!=null && remoteMessage.getNotification().getSound()!=null)
                sendNotification("ChatLocaly", message2, ""+remoteMessage.getNotification().getSound(), "");
                 else
                    sendNotification("ChatLocaly", message2, "", "");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            String redirection_key = "" + remoteMessage.getData().get("redirection_key");


            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            try {
                if(remoteMessage.getNotification().getSound()!=null && !remoteMessage.getNotification().getSound().equalsIgnoreCase(" "))
                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getSound(), redirection_key);
else
                    sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "", redirection_key);

            } catch (Exception e) {
                Log.d(TAG, e.toString());

            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(String title, String messageBody, String ringtone, String redirection_key) {


        chatlocalyshareprefrence = new Chatlocalyshareprefrence(getApplicationContext());

        /**/



       /* switch (id)
        {
            case 1:


                break;

            case 1:


                break;
            case 1:


                break;





        }*/


        Intent intent = null;
      /*  if (redirection_key.toString().toUpperCase().contains(""))
            intent = new Intent(this, SplashScreenActivity.class);
*/

        if (redirection_key.toString().toUpperCase().contains("BILL_UPDATED"))
        {
            intent = new Intent(this, BillListShowActivity.class);
            broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
            Intent intent2 = new Intent(REQUEST_ACCEPT);
            //intent.putExtra("Key", value);
            //  intent.putExtra("key", value);
            broadcaster.sendBroadcast(intent2);


        } else if (redirection_key.toString().toUpperCase().contains("NEW_BILL")) {
            intent = new Intent(this, BillListShowActivity.class);

            broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
            Intent intent2 = new Intent(REQUEST_ACCEPT);
            //intent.putExtra("Key", value);
            //  intent.putExtra("key", value);
            broadcaster.sendBroadcast(intent2);

        } else
            intent = new Intent(this, SplashScreenActivity.class);


        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.mipmap.logonew);
            notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.logonew);
        }



        if (ringtone != null) {
            if (chatlocalyshareprefrence.getVibration_status())
                //Vibration
                notificationBuilder.setVibrate(new long[]{1000L});
            else
                notificationBuilder.setVibrate(new long[]{0L});

            //LED
//        notificationBuilder.setLights(Color.RED, 3000, 3000);
            if (ringtone != null && !ringtone.equalsIgnoreCase("") ) {

                if (chatlocalyshareprefrence.getRingToneList().equalsIgnoreCase("Default Sound"))

                    notificationBuilder.setSound(defaultSoundUri);
                else//Ton
                    notificationBuilder.setSound(Uri.parse(chatlocalyshareprefrence.getRingToneList()));


            }
            else
            {
                notificationBuilder.setSound(null);

            }

        }


        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomNumber() /* ID of notification */, notificationBuilder.build());

        //


    }


    private void sendNotification2(String title, String messageBody) {
        title = title.toUpperCase();

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

/*
        Intent intent = new Intent(this, MainActivity.class);
        if (title.toString().toUpperCase().contains("NEW_TASK"))
            intent.putExtra(OPEN_FRAGMENT_KEY, TASK_FRAGMENT);

        else if (title.toString().toUpperCase().contains("NEW_MESSAGE"))
            intent.putExtra(OPEN_FRAGMENT_KEY, MESSAGE_FRAGMENT);

        else if (title.toString().toUpperCase().contains("NEW_EVENT_REQUEST"))
            intent.putExtra(OPEN_FRAGMENT_KEY, EVENT_REQUEST_FRAGMENT);

        else if (title.toString().toUpperCase().contains("EVENT_REQUEST_RESPONSE"))
            intent.putExtra(OPEN_FRAGMENT_KEY, EVENT_SENT_FRAGMENT);
        else
            intent.putExtra(OPEN_FRAGMENT_KEY, MESSAGE_FRAGMENT);



        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);


        //     PendingIntent pendingIntent = PendingIntent.getActivity(this,randomNumber(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // for proper backstack i
        PendingIntent pendingIntent =  stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);;

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notification_icon);
            notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.newlogoinlook);
        }

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomNumber(), notificationBuilder.build());
   */
    }


    public int randomNumber() {
        Random rand = new Random();
        int value = rand.nextInt(1000);
        return value;

    }
}