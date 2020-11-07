package com.example.pushnotifyandservices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import static com.example.pushnotifyandservices.AppNotify.CHANNEL_ID;

public class MyService extends Service {
    static MediaPlayer myPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this,"Music player Created",Toast.LENGTH_SHORT).show();
        myPlayer = MediaPlayer.create(this, R.raw.song);
        myPlayer.setLooping(false);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Service")
                .setContentText("Service is running in the background")
                .setContentIntent(notificationPendingIntent)
                .setSmallIcon(R.drawable.ic_audio)
//                .setColor(Color.RED)
                .build();
        startForeground(1, notification);
        Toast.makeText(this,"Music playing",Toast.LENGTH_SHORT).show();
        myPlayer.start();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"Service has been stopped",Toast.LENGTH_SHORT).show();
        myPlayer.stop();
        super.onDestroy();
    }
}






