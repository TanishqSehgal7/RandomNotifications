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
//        calendar = Calendar.getInstance();
//        long randomTime=GetRandomTimeSlots(2*60*60*1000);
//        calendar.setTimeInMillis(randomTime);
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

    public static long GetRandomTimeSlots(long timeTillSignInReminder) {

        long randomTime=0;

        if(timeTillSignInReminder>0 && timeTillSignInReminder<=4*60*60*1000) {

            randomTime=(long)Math.random()*timeTillSignInReminder;
            Log.d("Random Notification", String.valueOf(randomTime));
        }

        else if(timeTillSignInReminder>4*60*60*1000 && timeTillSignInReminder<=8*60*60*1000) {

            long mid=timeTillSignInReminder/2;
            // time between first half of the time till sign in reminder
            for(long i=0;i<=timeTillSignInReminder;i+=mid) {
                randomTime =i+ (long) (Math.random() * mid);
                Log.d("Random Notification", String.valueOf(randomTime));
                if(mid<(float)timeTillSignInReminder/2) {
                    randomTime =i+ (long) (Math.random() * (timeTillSignInReminder-mid));
                    Log.d("Random Notification", String.valueOf(randomTime));
                }
            }
        }

        else if(timeTillSignInReminder>8*60*60*1000) {
            long slotOfNotification=timeTillSignInReminder/3;

            for(long i=0;i<timeTillSignInReminder;i+=slotOfNotification) {
                randomTime = i + ((long) Math.random() * slotOfNotification);
                Log.d("Random Notification", String.valueOf(randomTime));
                if (slotOfNotification < (float) timeTillSignInReminder / 3) {
                    randomTime = i + ((long) Math.random() * (timeTillSignInReminder / 3));
                    Log.d("Random Notification", String.valueOf(randomTime));
                }
            }
        }
        return randomTime;
    }

}