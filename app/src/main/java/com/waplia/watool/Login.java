package com.waplia.watool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private MaterialButton login;
    private EditText email;
    private SharedPreferences sp;
    private LinearLayout register;
    private EditText password;
    private KProgressHUD hud2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        setRoundedCorners(email);
        setRoundedCorners(password);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        register = findViewById(R.id.register);
        login.setTextColor(Color.parseColor("#FFFFFF"));
        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                hud2 = KProgressHUD.create(Login.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.8f)
                        .show();
                    rqmap.put("email", email.getText().toString());
                    rqmap.put("password", password.getText().toString());
                    rq.setParams(rqmap,0);
                rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/login.php","", _rq_request_listener);
            }
        });
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent()
                        .setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getResources().getString(R.string.base_url)+"signup"));
                startActivity(i);
            }
        });
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud2.dismiss();
                   list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

                    if (!list.contains("status")) {
                        Log.d("Responsel",_response);
                        sp.edit().putString("id", list.get(0).get("id").toString()).commit();
                        sp.edit().putString("login", "done").commit();
                        sp.edit().putString("email", list.get(0).get("email").toString()).commit();
                        sp.edit().putString("plan", list.get(0).get("email").toString()).commit();
                        Log.d("Responsel", String.valueOf(list));
                        Intent i = new Intent(Login.this, Main2Home.class);
                        if (list.get(0).get("mobile_number").toString().equals("")) {
                            i.putExtra("showm","showm");
                            sp.edit().putString("showm", "showm").apply();
                        }
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(),_response,Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
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


    private void setRoundedCorners(View linearLayout) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(getResources().getColor(R.color.edittextback));

        // Set the stroke (optional)
        gradientDrawable.setStroke(2, getResources().getColor(R.color.edittextback));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
}}