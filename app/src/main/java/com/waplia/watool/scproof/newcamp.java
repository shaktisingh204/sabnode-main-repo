package com.waplia.watool.scproof;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class newcamp extends AppCompatActivity {
    private LinearLayout nameback, websiteback, typeback, goneline;
    private TextView signin;
    private CardView add;
    private TextView typetxt, lighttxt, advancedtxt;
    private ImageView checkadvanced, checkedlight;
    private EditText name, website;
    private ImageView webic, nameic, trackic, moreic;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD hud2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcamp);
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
        webic = findViewById(R.id.webic);
        nameic = findViewById(R.id.nameic);
        name = findViewById(R.id.name);
        website = findViewById(R.id.website);
        signin = (TextView) findViewById(R.id.signin);
        nameic.setColorFilter(Color.parseColor("#6F8AB3"));
        webic.setColorFilter(Color.parseColor("#6F8AB3"));
        setRoundedCorners(nameback, "00000000", "dadcdf");
        setRoundedCorners(websiteback, "00000000", "dadcdf");
        setRoundedCorners(signin, "2962FF", "2962FF");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    name.setError("Enter Name");
                } else if (website.getText().toString().equals("")) {
                    website.setError("Enter Website");
                } else {
                    hud2 = KProgressHUD.create(newcamp.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rqmap.put("user_id", Objects.requireNonNull(getIntent().getExtras()).getString("userid"));
                    rqmap.put("name", name.getText().toString());
                    rqmap.put("domain", website.getText().toString());
                    rq.setParams(rqmap, 0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "socialproof/newcamp.php", "", _rq_request_listener);
                }}});
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try {
                    Log.d("dashresponse", "" + _response);
                    finish();
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

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
