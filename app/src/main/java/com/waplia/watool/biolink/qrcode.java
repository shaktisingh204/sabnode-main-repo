package com.waplia.watool.biolink;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.waplia.watool.R;

public class qrcode extends AppCompatActivity {
    private LinearLayout phonesback, advanceback, startback;
    private ImageView chooseica;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitys_qrcode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        phonesback = findViewById(R.id.phonesback);
        advanceback = findViewById(R.id.advanceback);
        startback = findViewById(R.id.startback);
        chooseica = findViewById(R.id.chooseica);
        chooseica.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        setRoundedCorners(phonesback, "00000000", "dadcdf");
        setRoundedCorners(advanceback, "00000000", "dadcdf");
        setRoundedCorners(startback, "00000000", "dadcdf");
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
        linearLayout.setElevation(5);
    }

}
