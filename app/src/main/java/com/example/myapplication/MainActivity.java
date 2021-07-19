package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fireAlarm= findViewById(R.id.FireAlarm);
        TextView alarmTriggerTime=findViewById(R.id.AlarmTimne);

        fireAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fire alarm using alarm manager
                CreateNotificationChannel();
                if (FireRandomAlarm()) {
                    Intent intent= new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                    PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        System.out.println(GetRandomTimeSlots(2*60*60*1000));
    }

    public boolean FireRandomAlarm() {
        alarmManager= (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent);
        return true;
    }

    public void CreateNotificationChannel() {
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.O) {
            String channel_name="MyAlarmNotificationChannel";
            String description="Channel for Random Notification feature";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel("RandomNotifications",channel_name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static long GetRandomTimeSlots(long timeTillSignOutReminder) {

        long timeAtWhichNotificationAppears=0;

        System.out.println(timeTillSignOutReminder);
        long randomTime=0;

        if(timeTillSignOutReminder>0 && timeTillSignOutReminder<=4*60*60*1000) {

            int numberOFNotifications=1;
            long blockSize= timeTillSignOutReminder/numberOFNotifications;
            for(int i=0;i<numberOFNotifications;++i) {
                randomTime=(int) (Math.random() * blockSize);
                timeAtWhichNotificationAppears= i*blockSize + randomTime;
                System.out.println("Notification sent at: " + timeAtWhichNotificationAppears);
            }

        } else if(timeTillSignOutReminder>4*60*60*1000 && timeTillSignOutReminder<=8*60*60*1000) {

            int numberOFNotifications=2;
            long blockSize=timeTillSignOutReminder/numberOFNotifications;

            for(int i=0;i<numberOFNotifications;++i) {
                randomTime=(int) (Math.random() * blockSize);
                timeAtWhichNotificationAppears= i*blockSize + randomTime;
                System.out.println("Notification sent at: " + timeAtWhichNotificationAppears);
            }

        } else if(timeTillSignOutReminder>8*60*60*1000) {

            int numberOFNotifications = 3;
            long blockSize = timeTillSignOutReminder / numberOFNotifications;
            for (int i = 0; i <numberOFNotifications; ++i) {
                randomTime = (int) (Math.random() * blockSize);
                timeAtWhichNotificationAppears = i * blockSize + randomTime;
                System.out.println("Notification sent at: " + timeAtWhichNotificationAppears);
            }
        }
        return timeAtWhichNotificationAppears;
    }


}