package com.waplia.watool;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;


public class webview extends AppCompatActivity {
    private WebView web;
    private SharedPreferences sp;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hud = KProgressHUD.create(webview.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
    sp = getSharedPreferences("data", MODE_PRIVATE);

        web.loadUrl(Objects.requireNonNull(getIntent().getStringExtra("url")));
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains(getResources().getString(R.string.base_url)+"dashboard")){
                    web.loadUrl(getResources().getString(R.string.base_url)+"account_manager");
                }else{if (url.contains("account_manager")){
                    web.setVisibility(View.VISIBLE);
                    hud.dismiss();
                }else {
                if (!url.contains("account_manager")||!url.contains("dashboard")){
                    web.loadUrl(getResources().getString(R.string.base_url)+"account_manager");
                }}
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                web.setVisibility(View.GONE);
                hud.show();
            }
        });
        web.setWebChromeClient(new WebChromeClient());
        /* web.loadUrl(getResources().getString(R.string.base_url)+"whatsapp_contact/index/phone_numbers/"+getIntent().getStringExtra("pageid")+"/");
   */ }
}
