package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class captioncreate extends AppCompatActivity {
private MaterialButton create;
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD hud;
    private EditText titletxt, captiontxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captioncreate);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        create = findViewById(R.id.create);
        captiontxt = findViewById(R.id.captiontxt);
        titletxt = findViewById(R.id.titletxt);
        if (getIntent().getStringExtra("type").equals("edit")){
            captiontxt.setText(getIntent().getStringExtra("caption"));
            titletxt.setText(getIntent().getStringExtra("title"));
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rqmap.put("id", sp.getString("id",""));
                    rqmap.put("cid", getIntent().getStringExtra("id"));
                    rqmap.put("caption", captiontxt.getText().toString());
                    rqmap.put("title", titletxt.getText().toString());
                    hud = KProgressHUD.create(captioncreate.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rq.setParams(rqmap, 0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/captionupdate.php","", _rq_request_listener);
                }
            });
        }else{
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rqmap.put("id", sp.getString("id",""));
                    rqmap.put("caption", captiontxt.getText().toString());
                    rqmap.put("title", titletxt.getText().toString());
                    hud = KProgressHUD.create(captioncreate.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rq.setParams(rqmap, 0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/captionadd.php","", _rq_request_listener);
                }
            });
        }

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
                    Log.d("Response", _response);
                    finish();
                }catch(Exception e){
                    hud.dismiss();
                    finish();
                    Log.d("Error Response caption", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

    }}
