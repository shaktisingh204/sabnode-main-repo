package com.waplia.watool.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.waplia.watool.R;
import com.waplia.watool.start;

public class signup extends AppCompatActivity {
    private ImageView mailic, key, google, mobile, phoneic, key2, useric;
    private TextView signin;
    private LinearLayout devider, glogin, mlogin;
    private CardView login;
    private LinearLayout emailback, passwordback, nameback, phoneback, passwordback2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);
        emailback = findViewById(R.id.emailback);
        passwordback = findViewById(R.id.passwordback);
        mailic = findViewById(R.id.mailic);
        key = findViewById(R.id.key);
        google = findViewById(R.id.google);
        mobile = findViewById(R.id.mobile);
        phoneic = findViewById(R.id.phoneic);
        key2 = findViewById(R.id.key2);
        nameback = findViewById(R.id.nameback);
        phoneback = findViewById(R.id.phoneback);
        useric = findViewById(R.id.useric);
        passwordback2 = findViewById(R.id.passwordback2);
        phoneic.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
        key2.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
        key.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
        mailic.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
        useric.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
        google.setColorFilter(Color.parseColor("#2962FF"));
        mobile.setColorFilter(Color.parseColor("#2962FF"));
        setRoundedCorners(emailback, "00000000", "f3f5f8");
        setRoundedCorners(passwordback, "00000000", "f3f5f8");
        setRoundedCorners(passwordback2, "00000000", "f3f5f8");
        setRoundedCorners(nameback, "00000000", "f3f5f8");
        setRoundedCorners(phoneback, "00000000", "f3f5f8");
        signin = (TextView) findViewById(R.id.signin);
        changeStatusBarColor();
        devider = findViewById(R.id.divider);
        glogin = findViewById(R.id.glogin);
        mlogin = findViewById(R.id.mlogin);
        setRoundedCorners(glogin, "00000000", "f3f5f8");
        setRoundedCorners(mlogin, "00000000", "f3f5f8");
        setRoundedCorners(signin, "2962FF", "2962FF");
        setRoundedCorners(devider, "717595", "717595");
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, singin.class));
            }
        });
    }
    private void setRoundedCorners(View linearLayout, String color, String scolor) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(Color.parseColor("#" + color));

        // Set the stroke (optional)
        gradientDrawable.setStroke(2, Color.parseColor("#" + scolor));

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(signup.this, start.class));
    }
}
