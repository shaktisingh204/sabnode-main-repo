package com.waplia.watool.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.waplia.watool.R;

public class addsite extends AppCompatActivity {
    private LinearLayout nameback, websiteback, typeback, goneline;
    private TextView signin;
    private CardView add;
    private TextView typetxt, lighttxt, advancedtxt;
    private  ImageView checkadvanced, checkedlight;
    private EditText name, website;
    private ImageView webic, nameic, trackic, moreic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsite);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameback = (LinearLayout) findViewById(R.id.nameback);
        websiteback = (LinearLayout) findViewById(R.id.websiteback);
        signin = (TextView) findViewById(R.id.signin);
        typeback = (LinearLayout) findViewById(R.id.typeback);
        webic = findViewById(R.id.webic);
        nameic = findViewById(R.id.nameic);
        trackic = findViewById(R.id.trackic);
        goneline = findViewById(R.id.goneline);
        moreic = findViewById(R.id.moreic);
        name = findViewById(R.id.name);
        website = findViewById(R.id.website);
        typetxt = findViewById(R.id.typetxt);
        lighttxt = findViewById(R.id.lighttxt);
        advancedtxt = findViewById(R.id.advancedtxt);
        checkedlight = findViewById(R.id.checkedlight);
        checkadvanced = findViewById(R.id.checkadvanced);

        nameic.setColorFilter(Color.parseColor("#6F8AB3"));
        trackic.setColorFilter(Color.parseColor("#6F8AB3"));
        webic.setColorFilter(Color.parseColor("#6F8AB3"));
        setRoundedCorners(nameback, "00000000", "dadcdf");
        setRoundedCorners(websiteback, "00000000", "dadcdf");
        setRoundedCorners(typeback, "00000000", "dadcdf");
        setRoundedCorners(signin, "2962FF", "2962FF");
        if (getIntent().hasExtra("type"))
         {
            name.setText(getIntent().getStringExtra("name"));
            website.setText(getIntent().getStringExtra("host"));
            if (getIntent().getStringExtra("tracking_type").equals("lightweight")) {
                typetxt.setText(getIntent().getStringExtra("tracking_type"));
                lighttxt.setTextColor(Color.parseColor("#3D5AFE"));
                advancedtxt.setTextColor(Color.parseColor("#000000"));
                checkadvanced.setVisibility(View.GONE);
                checkedlight.setVisibility(View.VISIBLE);
            } else {
                typetxt.setText(getIntent().getStringExtra("tracking_type"));
                advancedtxt.setTextColor(Color.parseColor("#3D5AFE"));
                lighttxt.setTextColor(Color.parseColor("#000000"));
                checkedlight.setVisibility(View.GONE);
                checkadvanced.setVisibility(View.VISIBLE);
            }

        }
        lighttxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                typetxt.setText("Lightweight");
                lighttxt.setTextColor(Color.parseColor("#3D5AFE"));
                advancedtxt.setTextColor(Color.parseColor("#000000"));
                checkadvanced.setVisibility(View.GONE);
                checkedlight.setVisibility(View.VISIBLE);
                goneline.setVisibility(View.GONE);
                moreic.setRotation(-90);
            }
        });
        advancedtxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                typetxt.setText("Advanced");
                advancedtxt.setTextColor(Color.parseColor("#3D5AFE"));
                lighttxt.setTextColor(Color.parseColor("#000000"));
                checkedlight.setVisibility(View.GONE);
                checkadvanced.setVisibility(View.VISIBLE);
                goneline.setVisibility(View.GONE);
                moreic.setRotation(-90);
            }
        });
        typeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goneline.getVisibility() == View.VISIBLE) {
                    goneline.setVisibility(View.GONE);
                    moreic.setRotation(90);
                } else {
                    goneline.setVisibility(View.VISIBLE);
                    moreic.setRotation(-90);
                }
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
        gradientDrawable.setStroke(4, Color.parseColor("#" + scolor));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
    }

}
