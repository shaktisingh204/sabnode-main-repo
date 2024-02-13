package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        long timerDuration = 3000;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (sp.getString("login","").equals("done")) {
                    Intent i = new Intent(MainActivity.this, Main2Home.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(MainActivity.this, intro.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.schedule(task, timerDuration);

    }
}
