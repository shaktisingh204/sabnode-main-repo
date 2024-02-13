package com.waplia.watool;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class chatbotsetting extends AppCompatActivity {
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();

    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private RequestNetwork rq;
    private RequestNetwork rq2;
    private EditText apikey, apitemp, aimdel, start, stop;
    private RadioButton enable, disable;
    private IndicatorSeekBar seekBar;
    private TextView aitemp;
    private Boolean isexist = false;
    private MaterialButton submiit;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbotsetting);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //chatsettings
        apikey = findViewById(R.id.aiapi);
        seekBar = findViewById(R.id.seekBar);
        aimdel = findViewById(R.id.model);
        start = findViewById(R.id.startedit);
        stop = findViewById(R.id.stopedit);
        enable = findViewById(R.id.enable);
        disable = findViewById(R.id.disable);
        aitemp = findViewById(R.id.Aitemp);
        submiit = findViewById(R.id.create);
        setRoundedCorners(apikey);
        setRoundedCorners(start);
        setRoundedCorners(stop);
        setRoundedCorners(aimdel);
        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                /*aitemp.setText(seekParams.progress);*/
                aitemp.setText(String.valueOf(seekBar.getProgress()));
                Log.d("progress", String.valueOf(seekParams.progress));
                Log.i(TAG, String.valueOf(seekParams.seekBar));
                Log.i(TAG, String.valueOf(seekParams.progress));
                Log.i(TAG, String.valueOf(seekParams.progressFloat));
                Log.i(TAG, String.valueOf(seekParams.fromUser));
                Log.i(TAG, String.valueOf(seekParams.thumbPosition));
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });
        submiit.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
             Subbmit();
             }
        });
        sp = getSharedPreferences("data", MODE_PRIVATE);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud.dismiss();
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    apikey.setText(list.get(0).get("apikey").toString());
                    Float i = Float.parseFloat(list.get(0).get("temperature").toString())*10;
                    aitemp.setText(String.valueOf(i));
                    seekBar.setProgress(i);
                    seekBar.setProgress((float)i);
                    aimdel.setText(list.get(0).get("model").toString());
                    start.setText(list.get(0).get("key_enable").toString());
                    stop.setText(list.get(0).get("key_disable").toString());
                    Log.d("Status Chatsettings", list.get(0).get("status").toString());
                    if (list.get(0).get("status").toString().equals("0")){
                        disable.setChecked(true);
                    }else {
                        enable.setChecked(true);
                    }
                    isexist = true;
                    Toast.makeText(chatbotsetting.this, "Settings saved", Toast.LENGTH_SHORT).show();
                    Log.d("Response Chatsettings", _response);
                }catch(Exception e){
                    Log.d("Error code", e.toString());
                    isexist = false;
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                isexist = false;
                Log.d("Error Response", _message);
            }
        };
        hud = KProgressHUD.create(chatbotsetting.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", getIntent().getExtras().getString("id"));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatsettings.php","", _rq_request_listener);
        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    rqmap.put("id", getIntent().getExtras().getString("id"));
                    rq.setParams(rqmap,0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatsettings.php","", _rq_request_listener);
                    Log.d("Response Chatsettings save", _response);
                }catch(Exception e){
                    Log.d("Error code", e.toString());
                    if (_response.contains("success")){
                        rqmap.put("id", getIntent().getExtras().getString("id"));
                        rq.setParams(rqmap,0);
                        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatsettings.php","", _rq_request_listener);

                    }
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                isexist = false;
                Log.d("Error Response", _message);
            }
        };

    }

    public void Subbmit() {
        if (isexist){
            rqmap2.clear();
            rqmap2.put("id", list.get(0).get("id").toString());
            rqmap2.put("apikey", apikey.getText().toString());
            rqmap2.put("temperature", Float.parseFloat(aitemp.getText().toString())/10);
            rqmap2.put("model", aimdel.getText().toString());
            rqmap2.put("key_enable", start.getText().toString());
            rqmap2.put("key_disable", stop.getText().toString());
            if (enable.isChecked()) {
                rqmap2.put("status", 1);
            }else {
                rqmap2.put("status", 0);
            }
            rq2.setParams(rqmap2,0);
            rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatsetsave.php","", _rq2_request_listener);
        }else {
            rqmap2.clear();
            rqmap2.put("team_id", sp.getString("id", ""));
            rqmap2.put("id", getIntent().getExtras().getString("id"));
            if (enable.isChecked()) {
                rqmap2.put("status", 1);
            }else {
                rqmap2.put("status", 0);
            }
            rqmap2.put("apikey", apikey.getText().toString());
            rqmap2.put("temperature", Float.parseFloat(aitemp.getText().toString())/10);
            rqmap2.put("model", aimdel.getText().toString());
            rqmap2.put("key_enable", start.getText().toString());
            rqmap2.put("key_disable", stop.getText().toString());
            if (enable.isChecked()) {
                rqmap2.put("status", 1);
            }else {
                rqmap2.put("status", 0);
            }
            rq2.setParams(rqmap2,0);
            rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatnewsave.php","", _rq2_request_listener);

        }
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
