package com.waplia.watool.shortlink;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class alllinks extends AppCompatActivity {
    private ImageView faviconImageView, osIconImageView, copy;
    private TextView titleTextView;

    private RequestNetwork rq1;

    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list5 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private KProgressHUD hud;
    private RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alllinks);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        recyclerView = findViewById(R.id.recyclerview1);
        rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("alllinksdata1", _response);
                    hud.dismiss();
                    list1 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerView.setAdapter(new alllinks.Recyclerview1Adapter(list1));
                    recyclerView.setLayoutManager(new LinearLayoutManager(alllinks.this));
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
        hud = KProgressHUD.create(alllinks.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap1.put("id", getIntent().getIntExtra("id", 8));
        rq1.setParams(rqmap1, 0);
        rq1.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/alllinks.php","", _rq1_request_listener);


    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<alllinks.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public alllinks.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.statslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new alllinks.Recyclerview1Adapter.ViewHolder(_v);

        }
        @Override
        public void onBindViewHolder(alllinks.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final ImageView faviconImageView = _view.findViewById(R.id.fav);
            final TextView titleTextView = _view.findViewById(R.id.title);
            final TextView url = _view.findViewById(R.id.url);
            url.setText("https://linkshort.sabnode.com/"+_data.get((int)_position).get("alias").toString());
            String websiteUrl = _data.get((int)_position).get("url").toString(); // Replace with the actual website URL
            try {
                Glide.with(alllinks.this).load(getFaviconUrl(websiteUrl))
                        .placeholder(R.drawable.logos)
                        .into(faviconImageView);
                titleTextView.setText(fetchWebsiteTitle(websiteUrl));
            }catch (Exception e){
                e.printStackTrace();
            }
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
    private String getFaviconUrl(String websiteUrl) {
        // Construct the URL for the favicon (assuming it is located at the standard path)
        return websiteUrl + "/favicon.ico";
    }
    private String fetchWebsiteTitle(String websiteUrl) {
        try {
            // Fetch the HTML content of the website
            Document document = Jsoup.connect(websiteUrl).get();
            // Extract the title from the HTML document
            return document.title();
        } catch (IOException e) {
            return websiteUrl;
        }
    }
}
