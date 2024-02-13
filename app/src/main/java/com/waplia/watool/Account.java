package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class Account extends AppCompatActivity {
private LinearLayout homel, morel, views1, views2;
private CardView hcard, mcard;
private EditText fullname, usernametxt, emailedit, password, rpassword;
private TextView username, email, name;
private ImageView avtar;
private SharedPreferences sp;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
       private RequestNetwork rq1;
    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private KProgressHUD hud, hud2;
    private MaterialButton spass;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homel = findViewById(R.id.homel);
        morel = findViewById(R.id.morel);
        hcard = findViewById(R.id.hcard);
        mcard = findViewById(R.id.mcard);
        views1 = findViewById(R.id.views1);
        views2 = findViewById(R.id.views2);
        fullname = findViewById(R.id.fullname);
        usernametxt = findViewById(R.id.usernametxt);
        emailedit = findViewById(R.id.emailedit);
        password = findViewById(R.id.password);
        rpassword = findViewById(R.id.rpassword);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        avtar = findViewById(R.id.avtar);
        spass = findViewById(R.id.spass);
        homel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcard.setVisibility(View.INVISIBLE);
                hcard.setVisibility(View.VISIBLE);
                views1.setVisibility(View.VISIBLE);
                views2.setVisibility(View.GONE);
            }
        });
        morel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcard.setVisibility(View.VISIBLE);
                hcard.setVisibility(View.INVISIBLE);
                views1.setVisibility(View.GONE);
                views2.setVisibility(View.VISIBLE);
            }
        });
        rpassword.setTransformationMethod(null);

        spass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (password.getText().toString().equals(rpassword.getText().toString())) {
                   if (password.getText().toString().length() < 6) {
                       password.setError("Password length should be greater than 6");
                       rpassword.setError("Password length should be greater than 6");
                   }else{
                       hud2 = KProgressHUD.create(Account.this)
                               .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                               .setCancellable(false)
                               .setAnimationSpeed(2)
                               .setDimAmount(0.8f)
                               .show();
                       rqmap1.put("password", rpassword.getText().toString());
                       rqmap1.put("id", list.get(0).get("id").toString());
                       rq1.setParams(rqmap1,0);
                       rq1.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/passwordchange.php","", _rq1_request_listener);
                   }
               } else {
                   password.setError("Password not matched");
                   rpassword.setError("Password not matched");
               }
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
              Log.d("Response", _response);
                        email.setText(list.get(0).get("email").toString());
                        emailedit.setText(list.get(0).get("email").toString());
                        fullname.setText(list.get(0).get("fullname").toString());
                    name.setText(list.get(0).get("fullname").toString());
                        usernametxt.setText(list.get(0).get("username").toString());
                        username.setText(list.get(0).get("username").toString());
                    Glide.with(getApplicationContext())
                            .load(getResources().getString(R.string.base_url)+"writable/"+list.get(0).get("avatar").toString())
                            .placeholder(R.drawable.img_8)
                            .into(avtar);
                }catch(Exception e){
                    finish();
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        hud = KProgressHUD.create(Account.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("email", sp.getString("email",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/accountinfo.php","", _rq_request_listener);
 rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud2.dismiss();
                    list = new Gson().fromJson("["+_response+"]", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("password Response", _response);
                    if (list.get(0).get("status").toString().equals("changed")) {
                    Toast.makeText(Account.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                    sp.edit().clear().apply();
                    Intent intent = new Intent(Account.this, Login.class);
                    startActivity(intent);
                    finish();
                    }else {
                        Toast.makeText(Account.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    finish();
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

    }
}
