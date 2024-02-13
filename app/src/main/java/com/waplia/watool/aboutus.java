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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class aboutus extends AppCompatActivity {
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();

    private KProgressHUD hud;
    private MaterialButton contact, privacy;
    private String privacyp, headtxt, featurestxt, mail, waurl, bannerimg, bannerurl, appurl;
    private TextView haddertext, features, mailtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        haddertext = findViewById(R.id.haddertext);
        features = findViewById(R.id.features);
        mailtxt = findViewById(R.id.mail);
        contact = findViewById(R.id.contact);
        privacy = findViewById(R.id.privacy);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response", _response);
                    bannerimg = list.get(0).get("bannerimg").toString();
                    bannerurl = list.get(0).get("bannerurl").toString();
                    privacyp = list.get(0).get("privacypolicy").toString();
                    headtxt = list.get(0).get("headtxt").toString();
                    featurestxt = list.get(0).get("featurestxt").toString();
                    mail = list.get(0).get("mail").toString();
                    waurl = list.get(0).get("waurl").toString();
                    haddertext.setText(headtxt);
                    features.setText(featurestxt);
                    mailtxt.setText(mail);
                    hud.dismiss();
                    privacy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(privacyp));
                            startActivity(i);
                        }
                    });
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(waurl));
                            startActivity(i);
                        }
                    });
                    mailtxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Replace 'your.email@example.com' with the desired email address
                            String email = mailtxt.getText().toString();

                            // Create an intent to send an email
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                            emailIntent.setData(Uri.parse("mailto:" + email));
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "From"+R.string.app_name);
                            // Verify if there's an email client available to handle the intent
                            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(emailIntent);
                            }
                        }
                    });
                }catch(Exception e){
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        hud = KProgressHUD.create(aboutus.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/appdata.php","", _rq_request_listener);

    }
}
