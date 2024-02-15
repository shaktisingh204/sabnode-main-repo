package com.waplia.watool.biolink;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.waplia.watool.R;

public class vcardlink extends AppCompatActivity {
    private LinearLayout endback, startback, messageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcardlink);
        endback = findViewById(R.id.endback);
        startback = findViewById(R.id.startback);
        messageback = findViewById(R.id.messageback);
        setRoundedCorners(endback, "00000000", "dadcdf");
        setRoundedCorners(startback, "00000000", "dadcdf");
        setRoundedCorners(messageback, "00000000", "dadcdf");
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
