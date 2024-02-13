package com.waplia.watool.biolink;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;

import java.util.ArrayList;
import java.util.HashMap;

public class customdomains extends AppCompatActivity {
    private RecyclerView biolinklist;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD mProgressHUD;
    private TextView titletxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdomains);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        biolinklist = findViewById(R.id.biolinklist);
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
                    biolinklist.setAdapter(new Recyclerview1Adapter(list));
                    biolinklist.setLayoutManager(new LinearLayoutManager(customdomains.this));
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
        mProgressHUD = KProgressHUD.create(customdomains.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .show();
        rqmap.put("id", getIntent().getExtras().getString("id"));
        rqmap.put("type", getIntent().getExtras().getString("type"));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/cdomains.php","", _rq_request_listener);
        SwipeRefreshLayout refresh = findViewById(R.id.refreshLayout);
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mProgressHUD = KProgressHUD.create(customdomains.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .show();
                        rqmap.put("id", getIntent().getExtras().getString("id"));
                        rqmap.put("type", getIntent().getExtras().getString("type"));
                        rq.setParams(rqmap, 0);
                        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/cdomains.php","", _rq_request_listener);
                        refresh.setRefreshing(false);
                    }
                }
        );
    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<customdomains.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public customdomains.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.customdomainlist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new customdomains.Recyclerview1Adapter.ViewHolder(_v);

        }
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public void onBindViewHolder(customdomains.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView name = _view.findViewById(R.id.title);
            final TextView url = _view.findViewById(R.id.url);
            final TextView clicks = _view.findViewById(R.id.clicks);
            final ImageView icon = _view.findViewById(R.id.eye);
            final CardView clickcard = _view.findViewById(R.id.clickcard);
            final ImageView fav = _view.findViewById(R.id.fav);
             icon.setColorFilter(getResources().getColor(R.color.md_yellow_900), PorterDuff.Mode.MULTIPLY);
            name.setText(_data.get(_position).get("host").toString());
            url.setText(_data.get(_position).get("custom_index_url").toString());
        Glide.with(customdomains.this).load(getFaviconUrl(_data.get(_position).get("scheme").toString()+_data.get(_position).get("host").toString())).into(fav);
            if (_data.get(_position).get("is_enabled").toString().equals("1")){
                clicks.setTextColor(getResources().getColor(R.color.md_light_green_900));
                clicks.setText("Active");
                clickcard.setCardBackgroundColor(getResources().getColor(R.color.md_light_green_100));
                icon.setColorFilter(getResources().getColor(R.color.md_light_green_900), PorterDuff.Mode.MULTIPLY);
                icon.setImageDrawable(getResources().getDrawable(R.drawable.eyeic));
            }else if (_data.get(_position).get("is_enabled").toString().equals("0")){
                clicks.setText("Pending");
                clicks.setTextColor(getResources().getColor(R.color.md_yellow_900));
                clickcard.setCardBackgroundColor(getResources().getColor(R.color.md_yellow_100));
                icon.setColorFilter(getResources().getColor(R.color.md_yellow_900), PorterDuff.Mode.MULTIPLY);
                icon.setImageDrawable(getResources().getDrawable(R.drawable.ceyeic));
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
}
