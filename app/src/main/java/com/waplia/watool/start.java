package com.waplia.watool;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.waplia.watool.ui.dashboard;
import com.waplia.watool.ui.singin;

import java.time.temporal.Temporal;
import java.util.Timer;
import java.util.TimerTask;

public class start extends AppCompatActivity {
    private TextView signin;
    private SharedPreferences sp;
    private CardView card1, card2, card3, card4;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        signin = (TextView) findViewById(R.id.signin);
        changeStatusBarColor();
        setRoundedCorners(signin, "2962FF");
        animate(card1);
        animate(card2);
        animate(card3);
        animate(card4);
        sp = getSharedPreferences("sabdata", MODE_PRIVATE);

        if (sp.getString("login","").equals("done")) {
            Intent i = new Intent(start.this, dashboard.class);
            startActivity(i);
            finish();
        }else {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(start.this, singin.class);
                startActivity(i);
            }
        });
        }
    }
    private void setRoundedCorners(View linearLayout, String color) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(Color.parseColor("#" + color));

        // Set the stroke (optional)
        gradientDrawable.setStroke(2, Color.parseColor("#" + color));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public void animate(View view){
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f);
        scaleAnimator.setDuration(1000); // Animation duration in milliseconds
        scaleAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Infinite repeat
        scaleAnimator.setRepeatMode(ObjectAnimator.REVERSE); // Reverse the animation on each repeat
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f);
        scaleYAnimator.setDuration(1000);
        scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Infinite repeat
        scaleYAnimator.setRepeatMode(ObjectAnimator.REVERSE); // Reverse the animation on each repeat

        // Start the animation
        scaleAnimator.start();
        scaleYAnimator.start();
    }
}
