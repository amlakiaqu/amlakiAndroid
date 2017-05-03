package com.example.hamzawy.amlaki.activities;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.Utils.CustomSharedPreferences;
import com.example.hamzawy.amlaki.connection.VolleyHelper;
import com.example.hamzawy.amlaki.connection.VolleyRequest;
import com.example.hamzawy.amlaki.models.NotificationPayLoad;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by HamzawY on 4/25/17.
 */

public class MyApp extends Application {

    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        VolleyHelper.getInstance(this);
        CustomSharedPreferences.setContext(this);

        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("d6304011a0be3fc24eae", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);

                Gson gson = new Gson();
                NotificationPayLoad notificationPayLoad = gson.fromJson(data , NotificationPayLoad.class);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getAppContext())
                        .setSmallIcon(R.drawable.ic_logout)
                        .setContentTitle(notificationPayLoad.getMessage())
                        .setContentText(notificationPayLoad.getMessage())
                        .setPriority(NotificationCompat.PRIORITY_MAX) //must give priority to High, Max which will considered as heads-up notification
                        .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logout));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                }

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                mBuilder.setAutoCancel(true);
//
                Intent notificationIntent = new Intent(getApplicationContext(), CoreActivity.class);
                notificationIntent.putExtra("POST_ID", notificationPayLoad.getPostId());
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(intent);

                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                wl.acquire(15000);

                notificationManager.notify(0, mBuilder.build());
            }
        });

        pusher.connect();
    }

    public static MyApp getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

}
