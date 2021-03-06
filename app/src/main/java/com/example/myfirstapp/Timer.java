package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static com.example.myfirstapp.App.CHANNEL_1_ID;
import static com.example.myfirstapp.App.CHANNEL_2_ID;

public class Timer extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private TextView countdownText;
    private Button countdownButton;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 1200000;
    private boolean timerRunning;
    private long savetime = 0;

    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);

        setContentView(R.layout.activity_timer);

        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
    }

    public void sendMessage(View view) {
        EditText chooseButton = (EditText)findViewById(R.id.changeTime);
        long newTime;
        try {
            newTime = 1000*Long.valueOf(chooseButton.getText().toString());
            timeLeftInMilliseconds = newTime;
        }
        catch(Exception e){
            timeLeftInMilliseconds = 1200000;
            newTime = 1200000;
        } finally {
            updateTimer();
            if(timerRunning){
                stopTimer();
                //искаме ли да се стартира веднага при сетване?
                startTimer();
            }
        }

        savetime = newTime;

        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

    //докато таймерът работи се натрупват нови прозорци FIXED
    //таймерът работи докато изскачащия прозорец е на екрана!
    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            //On finish function
            @Override
            public void onFinish() {
                if(!PopActivity.active) {
                    sendOnChannel1();
                    Intent i = new Intent(getApplicationContext(), PopActivity.class);
                    startActivity(i);
                }

                timeLeftInMilliseconds = savetime;
                updateTimer();
                stopTimer();
                startTimer();
            }
        }.start();

        countdownButton.setText("PAUSE");
        timerRunning = true;
    }

    public void stopTimer() {
        countdownButton.setText("START");
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

    public void sendOnChannel1() {
        Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_gamepad)
                .setVibrate( new long []{ 1000 , 1000 , 1000 , 1000 , 1000 })
                .setSound(alarmSound)
                .setContentTitle("Time to take a break!")
                .setContentText("It's been 20 minutes")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_gamepad)
                .setVibrate( new long []{ 1000 , 1000 , 1000 , 1000 , 1000 })
                .setSound(alarmSound)
                .setContentTitle("Time to take a break!")
                .setContentText("It's been 20 minutes")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(2, notification);
    }
}