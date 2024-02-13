package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main2Home extends
        AppCompatActivity {
    private LinearLayout warepo;
    private LinearLayout profile;
    private LinearLayout linkgena;
    private LinearLayout contacts;
    private LinearLayout bulkmsg;
    private LinearLayout accountmanger;
    private LinearLayout caption;
    private LinearLayout autoresponse, chatbot;
    private ImageView home, more;
    private ScrollView view1, view2;
    private LinearLayout homel, morel;
    private CardView aboutus;
    private ImageView banner;
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private String bannerimg, bannerurl, waurl;

    private CardView resaler, share, plans, logout, account;
private String banner2;
    private KProgressHUD hud;
    private RequestNetwork rq1;
    private ImageView waimg;
    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private String urls;
    private static final String ONESIGNAL_APP_ID = "90692dbe-efea-4a64-8844-4924a8dbfd9c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        warepo = findViewById(R.id.wareport);
        home = findViewById(R.id.home);
        more = findViewById(R.id.more);
        morel = findViewById(R.id.morel);
        homel = findViewById(R.id.homel);
        profile = findViewById(R.id.profile);
        banner = findViewById(R.id.banner);
        linkgena = findViewById(R.id.linkgene);
        contacts = findViewById(R.id.contacts);
        share = findViewById(R.id.share);
        resaler = findViewById(R.id.resaler);
        waimg = findViewById(R.id.waimg);
        accountmanger = findViewById(R.id.accountmanger);
        bulkmsg = findViewById(R.id.bulkmag);
        caption = findViewById(R.id.caption);
        autoresponse =findViewById(R.id.autorespond);
        chatbot = findViewById(R.id.chatbot);
        account = findViewById(R.id.account);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        plans = findViewById(R.id.plans);
        logout = findViewById(R.id.logout);
        aboutus = findViewById(R.id.aboutus);
        waimg.setColorFilter(Color.parseColor("#8080f1"), PorterDuff.Mode.ADD);

        sp = getSharedPreferences("data", MODE_PRIVATE);
        Log.d("useridss", sp.getString("id", ""));
        Log.d("emailss", sp.getString("email", ""));
try {
            if (sp.getString("showm", "").equals("showm")){
                new iOSDialogBuilder(Main2Home.this)
                        .setTitle("Required")
                        .setSubtitle("Please Link your phone number now to use WhatsApp Tool")
                        .setBoldPositiveLabel(true)
                        .setCancelable(false)
                        .setPositiveListener("Link now", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                Intent i = new Intent(Main2Home.this, addphonenumber.class);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        })
                        .setNegativeListener("Not Now.", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build().show();
        }else
        {

        }}catch (Exception e){
    e.printStackTrace();
}

        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                // `requestPermission` completed successfully and the user has accepted permission
                // `requestPermission` completed successfully but the user has rejected permission
            }
            else {
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
            }
        });
        /*ActivityCompat.requestPermissions( this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                }, 1
        );*/
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
                    bannerimg = Objects.requireNonNull(list.get(0).get("bannerimg")).toString();
                    bannerurl = Objects.requireNonNull(list.get(0).get("bannerurl")).toString();
                    banner2 = Objects.requireNonNull(list.get(0).get("banner2")).toString();
                    waurl = Objects.requireNonNull(list.get(0).get("waurl")).toString();
                    Glide.with(Main2Home.this).load(bannerimg).into(banner);
                    banner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(bannerurl));
                            startActivity(i);
                        }
                    });
                    hud.dismiss();
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
        hud = KProgressHUD.create(Main2Home.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/appdata.php","", _rq_request_listener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()){

    // If you don't have access, launch a new activity to show the user the system's dialog
    // to allow access to the external storage
            }else{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        warepo.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, Home.class);
            startActivity(i);
        });
        profile.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, profile.class);
            startActivity(i);
        });
        linkgena.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, LinkGen.class);
            startActivity(i);
        });
        contacts.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, contacts.class);
            startActivity(i);
        });
        bulkmsg.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, com.waplia.watool.bulkmsg.class);
            startActivity(i);
        });
        autoresponse.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, autoresponder.class);
            startActivity(i);
        });
        chatbot.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, chatbots.class);
            startActivity(i);
        });
        aboutus.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, aboutus.class);
            startActivity(i);
        });
        caption.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, Caption.class);
            startActivity(i);
        });
        accountmanger.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, accountadd.class);
            i.putExtra("url", urls);
            startActivity(i);
        });
        account.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, Account.class);
            startActivity(i);
        });
        resaler.setOnClickListener(v -> {
            Intent i = new Intent(Main2Home.this, resaleapp.class);
            i.putExtra("bannerimg", banner2);
            i.putExtra("bannerurl", waurl);
            startActivity(i);
        });
        home.setColorFilter(Color.parseColor("#FF6200EE"));
        more.setColorFilter(Color.parseColor("#FFBB86FC"));
        morel.setOnClickListener(v -> {
            more.setColorFilter(Color.parseColor("#FF6200EE"));
            home.setColorFilter(Color.parseColor("#FFBB86FC"));
            home.setImageDrawable(getResources().getDrawable(R.drawable.home));
            more.setImageDrawable(getResources().getDrawable(R.drawable.settings1));
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
        });
        homel.setOnClickListener(v -> {
            home.setColorFilter(Color.parseColor("#FF6200EE"));
            more.setColorFilter(Color.parseColor("#FFBB86FC"));
            home.setImageDrawable(getResources().getDrawable(R.drawable.home1));
            more.setImageDrawable(getResources().getDrawable(R.drawable.settings));
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
        });
        share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.waplia.watool");
            startActivity(Intent.createChooser(i, "Share via"));
        });
        plans.setOnClickListener(v -> {
            Intent i = new Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .setData(Uri.parse(getResources().getString(R.string.base_url)+"pricing"));
            startActivity(i);
        });
        logout.setOnClickListener(v -> {
            sp.edit().clear().apply();
            Intent intent = new Intent(Main2Home.this, loginmethods.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
