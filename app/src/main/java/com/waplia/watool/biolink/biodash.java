package com.waplia.watool.biolink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.shortlink.linkdash;

import java.util.ArrayList;
import java.util.HashMap;

public class biodash extends AppCompatActivity {
    private ImageView bio, link, qrcodes, vcardfiles, fileimg, img1, img2, img3, img4;
    private SharedPreferences sp;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
  private RequestNetwork rq1;
    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private TextView shortlink, biolinks, qrcodestext, vcardlinks, filelink;
    private KProgressHUD mProgressHUD;
    private String bioid;
    private CardView biocard, shortcard, qrcodecard, vcard, filecard, customdomainscard;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        qrcodestext = findViewById(R.id.qrcodestext);
        vcardlinks = findViewById(R.id.vcardlinks);
        filelink = findViewById(R.id.filelink);
        bio = findViewById(R.id.bio);
        link = findViewById(R.id.link);
        qrcodes = findViewById(R.id.qrcodes);
        vcardfiles = findViewById(R.id.vcardfiles);
        fileimg = findViewById(R.id.fileimg);
        biolinks = findViewById(R.id.biolinks);
        shortlink = findViewById(R.id.shortlink);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        vcard = findViewById(R.id.vcard);
        filecard = findViewById(R.id.filecard);
        qrcodecard = findViewById(R.id.qrcodecard);
        bio.setColorFilter(Color.parseColor("#3b82f6"), PorterDuff.Mode.MULTIPLY);
        link.setColorFilter(Color.parseColor("#1ebba9"), PorterDuff.Mode.MULTIPLY);
        qrcodes.setColorFilter(Color.parseColor("#c843d9"), PorterDuff.Mode.MULTIPLY);
        vcardfiles.setColorFilter(Color.parseColor("#06b6d4"), PorterDuff.Mode.MULTIPLY);
        fileimg.setColorFilter(Color.parseColor("#10b981"), PorterDuff.Mode.MULTIPLY);
        img2.setColorFilter(Color.parseColor("#0091EA"), PorterDuff.Mode.MULTIPLY);
        img3.setColorFilter(Color.parseColor("#D32F2F"), PorterDuff.Mode.MULTIPLY);
        img4.setColorFilter(Color.parseColor("#AA00FF"), PorterDuff.Mode.MULTIPLY);
        biocard = findViewById(R.id.biocard);
        shortcard = findViewById(R.id.shortcard);
        customdomainscard = findViewById(R.id.customdomainscard);
        biocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, biolinks.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "biolink");
                startActivity(intent);
            }
        });
        shortcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, biolinks.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "link");
                startActivity(intent);
            }
        });
        filecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, biolinks.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "file");
                startActivity(intent);
            }
        });
        vcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, biolinks.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "vcard");
                startActivity(intent);
            }
        });
        qrcodecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, biolinks.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "qrcode");
                startActivity(intent);
            }
        });
        customdomainscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(biodash.this, customdomains.class);
                intent.putExtra("id", bioid);
                intent.putExtra("type", "qrcode");
                startActivity(intent);
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
                    Log.d("biolinkdata", _response);
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    rqmap1.put("id", list.get(0).get("user_id").toString());
                    bioid = list.get(0).get("user_id").toString();
                    rq1.setParams(rqmap1, 0);
                    rq1.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/home.php","", _rq1_request_listener);
                }catch(Exception e){
                    e.printStackTrace();
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        mProgressHUD = KProgressHUD.create(biodash.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .show();
        rqmap.put("id", getIntent().getExtras().getString("email"));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/userid.php","", _rq_request_listener);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);

               refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mProgressHUD = KProgressHUD.create(biodash.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .show();
                        rqmap.put("id", getIntent().getExtras().getString("email"));
                        rq.setParams(rqmap, 0);
                        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/userid.php","", _rq_request_listener);

                        refreshLayout.setRefreshing(false);
                    }
                }
        );
        rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("biolinkdata", _response);
                    list1 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
biolinks.setText(list1.get(0).get("biolink").toString().replace(".0", ""));
shortlink.setText(list1.get(0).get("link").toString().replace(".0", ""));
qrcodestext.setText(list1.get(0).get("qrcodes").toString().replace(".0", ""));
vcardlinks.setText(list1.get(0).get("vcard").toString().replace(".0", ""));
filelink.setText(list1.get(0).get("file").toString().replace(".0", ""));
mProgressHUD.dismiss();
                }catch(Exception e){
                    e.printStackTrace();
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
