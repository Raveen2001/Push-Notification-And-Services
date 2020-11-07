package com.example.pushnotifyandservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button start, play, pause, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startBtn);
        play = findViewById(R.id.playBtn);
        pause = findViewById(R.id.pauseBtn);
        stop = findViewById(R.id.stopBtn);
        if (isMyServiceRunning(MyService.class)){
                loadData();
        }
        else {
            start.setEnabled(true);
            stop.setEnabled(false);
            play.setEnabled(false);
            pause.setEnabled(false);
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextCompat.startForegroundService(v.getContext(), new Intent(v.getContext(), MyService.class));
                start.setEnabled(false);
                stop.setEnabled(true);
                play.setEnabled(true);
                pause.setEnabled(true);
                saveData();

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.myPlayer.start();
                Toast.makeText(v.getContext(), "Music Resumed", Toast.LENGTH_SHORT).show();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.myPlayer.pause();
                Toast.makeText(v.getContext(), "Music Paused", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(v.getContext(), MyService.class));
                start.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(false);
                pause.setEnabled(false);
                saveData();
            }
        });


    }

    void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("start", start.isEnabled());
        editor.putBoolean("play", play.isEnabled());
        editor.putBoolean("stop", stop.isEnabled());
        editor.putBoolean("pause", pause.isEnabled());
        editor.apply();
    }

    void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        start.setEnabled(sharedPreferences.getBoolean("start",false));
        stop.setEnabled(sharedPreferences.getBoolean("stop",true));
        play.setEnabled(sharedPreferences.getBoolean("play", true));
        pause.setEnabled(sharedPreferences.getBoolean("pause", true));
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
