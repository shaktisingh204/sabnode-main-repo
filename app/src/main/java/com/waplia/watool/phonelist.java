package com.waplia.watool;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class phonelist extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq1;
    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private Integer pagesizel = 20;
    private CardView pre;
    private CardView next;
    private CardView search;
    private String urls;
    private ImageView contactadd;
private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phoneactivity);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview1);
        pre = (CardView) findViewById(R.id.pre);
        next = (CardView) findViewById(R.id.next);
        search = (CardView) findViewById(R.id.search);
        contactadd = (ImageView) findViewById(R.id.contactadd);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(phonelist.this, phonesearch.class);
                i.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
        contactadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(phonelist.this, addphonenumbers.class);
                i.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
     pre.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             hud = KProgressHUD.create(phonelist.this)
                     .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                     .setCancellable(false)
                     .setAnimationSpeed(2)
                     .setDimAmount(0.8f)
                     .show();
             pagesizel = pagesizel - 20;
             rqmap.put("id", getIntent().getStringExtra("id"));
             rq.setParams(rqmap,0);
             rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/phonelist.php?limit="+pagesizel.toString()+"&last="+(pagesizel-20),"", _rq_request_listener);
         }
     });
     next.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             hud = KProgressHUD.create(phonelist.this)
                     .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                     .setCancellable(false)
                     .setAnimationSpeed(2)
                     .setDimAmount(0.8f)
                     .show();
             pagesizel = pagesizel + 20;
             rqmap.put("id", getIntent().getStringExtra("id"));
             rq.setParams(rqmap,0);
             rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/phonelist.php?limit="+pagesizel.toString()+"&last="+(pagesizel-20),"", _rq_request_listener);
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
                    recyclerView1.setAdapter(new Recyclerview1Adapter(list));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(phonelist.this));
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
        hud = KProgressHUD.create(phonelist.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", getIntent().getStringExtra("id"));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/phonelist.php?limit="+pagesizel.toString()+"&last=0","", _rq_request_listener);

        rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    String testurl = _response;
                    testurl = testurl.replace("{\"status\":\"success\",\"message\":\"success\",\"data\":","");
                    testurl = testurl.replace("}}","}");
                    testurl = "[" + testurl + "]";
                    list1 = new Gson().fromJson(testurl, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response autologin", _response);
                    urls = list1.get(0).get("url").toString();
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
        Log.d("URL", getResources().getString(R.string.base_url)+"admin_api/get_autologin?api_key=asg12345&user="+sp.getString("email",""));
        rq1.startRequestNetwork(RequestNetworkController.GET, getResources().getString(R.string.base_url)+"admin_api/get_autologin?api_key=asg12345&user="+sp.getString("email",""),"", _rq1_request_listener);

    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.phonelist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.phonenumber);
            final ImageView imageview1 = _view.findViewById(R.id.phonelist);
            textview1.setText(_data.get((int)_position).get("phone").toString());
        }
        @Override
        public int getItemCount() {
            return _data.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume", "onResume");
        rqmap.put("id", getIntent().getStringExtra("id"));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/phonelist.php?limit="+pagesizel.toString()+"&last=0","", _rq_request_listener);

    }


}
