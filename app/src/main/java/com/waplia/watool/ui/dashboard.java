package com.waplia.watool.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.Main2Home;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.biolink.biodash;
import com.waplia.watool.scproof.socialdash;
import com.waplia.watool.shortlink.linkdash;
import com.waplia.watool.start;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class dashboard extends AppCompatActivity {
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private SharedPreferences sp;
    private KProgressHUD hud2;
    private boolean isBlinking = true;
    private TextView site, bulk, bot, email, sptxt, shorttxt, uptimetxt, biotxt;
    private LinearLayout analytics;
    private Handler handler = new Handler();
    private LinearLayout bulkr, social, shortlink;
    private CardView biolinks;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        site = findViewById(R.id.site);
        bulk = findViewById(R.id.bulk);
        email = findViewById(R.id.email);
        bot = findViewById(R.id.bot);
        sptxt = findViewById(R.id.sptxt);
        shorttxt = findViewById(R.id.shorttxt);
        uptimetxt = findViewById(R.id.uptimetxt);
        analytics = findViewById(R.id.analytics);
        biotxt = findViewById(R.id.biotxt);
        bulkr = findViewById(R.id.bulkr);
        social = findViewById(R.id.social);
        toggleBlinkEffect(analytics);
        toggleBlinkEffect(bulkr);
        biolinks = findViewById(R.id.biolinks);
        shortlink = findViewById(R.id.shortlink);
        sp = getSharedPreferences("sabdata", MODE_PRIVATE);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try {
                    hud2.dismiss();
                    Log.d("dashresponse", "" + _response);
                    cancelBlinkAnimation(analytics);

                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

                    site.setText(list.get(1).get("data").toString().replace("{websites=", "").replace(".0}", ""));
                    bulk.setText(list.get(0).get("data").toString().replace("{wa_total_sent=", "").replace(".0}", "").replace("}]", "").replace("[",""));
                    email.setText("0");
                    bot.setText("0");
                    sptxt.setText(list.get(2).get("data").toString().replace("{campaigns=", "").replace(".0}", ""));
                    shorttxt.setText(list.get(3).get("data").toString().replace("{links=", "").replace(".0}", ""));
                    uptimetxt.setText(list.get(5).get("data").toString().replace("{monitors=", "").replace(".0}", ""));
                    biotxt.setText(list.get(4).get("data").toString().replace("{biolink=", "").replace(".0}", ""));
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

        new Thread(() -> {
            while (isBlinking) {
                handler.post(() -> {
                    // Toggle blink effect continuously
                    toggleBlinkEffect(analytics);
                });
                try {
                    // Simulate a delay in the background task
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        hud2 = KProgressHUD.create(dashboard.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("email", sp.getString("email", ""));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "home.php", "", _rq_request_listener);
        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, site.class));
            }
        });
        bulkr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, Main2Home.class));
            }
        });
        shortlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, linkdash.class));
            }
        });
        biolinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, biodash.class);
                intent.putExtra("email", sp.getString("email", ""));
                startActivity(intent);
            }
        });
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, socialdash.class));
            }
        });

    }

    private void toggleBlinkEffect(LinearLayout line) {
        // Toggle blink effect
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        animation.setFillAfter(true);
        line.startAnimation(animation);
    }
    private static Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        Map<String, Object> innerMap = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            innerMap.put(entry.getKey(), entry.getValue());
        }
        return innerMap;
    }
    // Call this method to cancel the blink animation
    private void cancelBlinkAnimation(LinearLayout line) {
        isBlinking = false;
        line.setVisibility(TextView.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelBlinkAnimation(analytics);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
finishAffinity();
    }
}

/* Gson gson = new Gson();

        // Define the type for Gson to parse into
        Type listType = new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType();

        // Parse the JSON string into a List of HashMaps
        List<HashMap<String, Object>> list2 = gson.fromJson(jsonString, listType);
*/
