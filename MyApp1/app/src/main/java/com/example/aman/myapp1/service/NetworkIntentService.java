package com.example.aman.myapp1.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.activity.MainActivity;

/**
 * Created by aman on 22/4/16.
 */
public class NetworkIntentService extends IntentService {
    private static final String TAG = "NetworkIntentService";

    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");
        if (intent != null) {

            Log.i(TAG, "intent result - " + intent.getStringExtra("test_intent"));

            sendNotification(intent.getStringExtra("test_intent"));
        }
    }

    private void sendNotification(String test_intent) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(Color.RED)
                .setContentTitle(test_intent)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManager notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notiManager.notify(0, builder.build());
    }

}
