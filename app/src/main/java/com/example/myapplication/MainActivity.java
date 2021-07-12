package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
            }
        });

    }


    public void FireRandomAlarm() {
        AlarmManager alarmManager= (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this, BroadcastReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, etRandomTimeSlots(), AlarmManager.IN);
    }

    public static void CreateNotificationChannel() {
        
    }

    public static int GetRandomTimeSlots() {
        return 0;
    }

}