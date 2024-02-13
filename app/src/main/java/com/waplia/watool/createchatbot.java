package com.waplia.watool;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createchatbot  extends AppCompatActivity {
    private RadioButton enable, disable, matchno, matchyes, all, individual, group, match, nomatch, useai, nouseai;
   private EditText nameedit, disedit, keywordedit, captionedit;
   private MaterialButton create;
   private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private KProgressHUD hud;
    private RequestNetwork rq;
    private RequestNetwork rq2;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createchatbot);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        enable = findViewById(R.id.enable);
        disable = findViewById(R.id.disable);
        matchno = findViewById(R.id.matchno);
        matchyes = findViewById(R.id.matchyes);
        all = findViewById(R.id.all);
        individual = findViewById(R.id.indivisual);
        group = findViewById(R.id.group);
        match = findViewById(R.id.match);
        nomatch = findViewById(R.id.nomatch);
        useai = findViewById(R.id.useai);
        nouseai = findViewById(R.id.nouseai);
        nameedit = findViewById(R.id.nameedit);
        disedit = findViewById(R.id.disedit);
        keywordedit = findViewById(R.id.Keywordsedit);
        captionedit = findViewById(R.id.captionedit);
        create = findViewById(R.id.create);
        setRoundedCorners(nameedit);
        setRoundedCorners(disedit);
        setRoundedCorners(keywordedit);
        setRoundedCorners(captionedit);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        create.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          createchatbot();
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
                // list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                Log.d("Response session chat", _response);
                hud.dismiss();
                Toast.makeText(createchatbot.this, "Bot Saved", Toast.LENGTH_LONG).show();
                finish();
            }catch(Exception e){
                Log.d("Error Response chat", _response);
            }
        }
        @Override
        public void onErrorResponse(String _param1, String _param2) {
            final String _tag = _param1;
            final String _message = _param2;
            Log.d("Error Response", _message);
        }
    };
    rq2 = new RequestNetwork(this);
    _rq2_request_listener = new RequestNetwork.RequestListener() {
        @Override
        public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
            final String _tag = _param1;
            final String _response = _param2;
            final HashMap<String, Object> _responseHeaders = _param3;
            try{
                 list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                if (list.get(0).get("status").toString().equals("1")) {
                    enable.setChecked(true);
                }else if (list.get(0).get("status").toString().equals("0")){
                    disable.setChecked(true);
                }
                if (list.get(0).get("type_search").toString().equals("1")) {
                    match.setChecked(true);
                }else if (list.get(0).get("type_search").toString().equals("2")){
                    nomatch.setChecked(true);
                }
                if (list.get(0).get("use_ai").toString().equals("1")) {
                    useai.setChecked(true);
                }else if (list.get(0).get("use_ai").toString().equals("0")){
                    nouseai.setChecked(true);
                }
                if (list.get(0).get("is_default").toString().equals("1")) {
                    matchyes.setChecked(true);
                }else if (list.get(0).get("is_default").toString().equals("0")){
                    matchno.setChecked(true);
                }
                if (list.get(0).get("send_to").toString().equals("1")) {
                    all.setChecked(true);
                }else if (list.get(0).get("send_to").toString().equals("2")){
                    individual.setChecked(true);
                }else if (list.get(0).get("send_to").toString().equals("3")){
                    group.setChecked(true);
                }
                nameedit.setText(list.get(0).get("name").toString());
                keywordedit.setText(list.get(0).get("keywords").toString());
                captionedit.setText(list.get(0).get("caption").toString());
                disedit.setText(list.get(0).get("description").toString());

                Log.d("Response chat", _response);
            }catch(Exception e){
                Log.d("Error Response", e.toString());
                Log.d("Error Response", _response);
            }
        }
        @Override
        public void onErrorResponse(String _param1, String _param2) {
            final String _tag = _param1;
            final String _message = _param2;
            Log.d("Error Response", _message);
        }
    };

    if (getIntent().getStringExtra("type").equals("new")) {
        Log.d("type of bot", "new");
    }else if (getIntent().getStringExtra("type").equals("edit")) {
        Log.d("type of bot", "edit");
        rqmap2.clear();
        rqmap2.put("id", getIntent().getStringExtra("id"));
        rq2.setParams(rqmap2, 0);
        rq2.startRequestNetwork(RequestNetworkController.POST,getResources().getString(R.string.base_url)+"apis/getchatbotdata.php","",_rq2_request_listener);
    }
         }
    public void createchatbot() {

        if(enable.isChecked()) {
            rqmap.put("status", "1");
        }else if(disable.isChecked()) {
            rqmap.put("status", "0");
        }
        if(match.isChecked()) {
            rqmap.put("type_search", "1");

        }else if(nomatch.isChecked()) {
            rqmap.put("type_search", "2");

        }
        if(useai.isChecked()) {
            rqmap.put("use_ai", "1");
        }else if(nouseai.isChecked()) {
            rqmap.put("use_ai", "0");
        }
        if (matchyes.isChecked()) {
            rqmap.put("is_default", "1");
        }else if (matchno.isChecked()) {
            rqmap.put("is_default", "0");
        }
        if(all.isChecked()) {
            rqmap.put("send_to", "1");
        }else {
            if (individual.isChecked()) {
                rqmap.put("send_to", "2");
            }else if (group.isChecked()) {
                rqmap.put("send_to", "3");
            }

        }
        if (nomatch.isChecked()) {
            rqmap.put("match", "2");
        }else if (match.isChecked()) {
            rqmap.put("match", "1");
        }
        rqmap.put("instance_id", getIntent().getStringExtra("id"));
        rqmap.put("name", nameedit.getText().toString());
        rqmap.put("keywords", keywordedit.getText().toString());
        rqmap.put("team_id", sp.getString("id", ""));
        rqmap.put("caption", captionedit.getText().toString());
        rqmap.put("type", "1");
        rqmap.put("presenceTime", "1");
        rqmap.put("presenceType", 0);
        rqmap.put("description", disedit.getText().toString());
        create.setEnabled(false);
        if (getIntent().getStringExtra("type").equals("new")) {
            rq.setParams(rqmap,0);
            rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/createbot.php","", _rq_request_listener);
            hud = KProgressHUD.create(createchatbot.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.8f)
                    .show();
        }else if (getIntent().getStringExtra("type").equals("edit")) {
            rqmap.put("id", getIntent().getStringExtra("id"));
            rq.setParams(rqmap,0);
            rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/updatechatbot.php","", _rq_request_listener);
            hud = KProgressHUD.create(createchatbot.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.8f)
                    .show();  }

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