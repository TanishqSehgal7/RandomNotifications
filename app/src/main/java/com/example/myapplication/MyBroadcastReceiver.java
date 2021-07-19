package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // create notification builder with pending intent

        Intent intentTarget= new Intent(context, MainActivity.class);
        intentTarget.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentTarget.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intentTarget,0);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, "RandomNotifications");
        builder.setContentText("This is a test for Random Notifications!");
        builder.setContentTitle("Random Notifications");
        builder.setPriority(Notification.PRIORITY_MAX).setLights(Color.GREEN,1000,2000);
        builder.setSmallIcon(R.mipmap.ic_launcher_round).setTimeoutAfter(5*60*1000)
                .setContentIntent(pendingIntent).build();

    }
}
