package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class Caption extends AppCompatActivity {
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private RecyclerView recyclerView1;
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD hud;
    private CardView contactadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);
        contactadd = (CardView) findViewById(R.id.contactadd);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        recyclerView1 = findViewById(R.id.recyclerview1);
        contactadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Caption.this, captioncreate.class);
                intent.putExtra("type","new");
                startActivity(intent);
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
                    recyclerView1.setAdapter(new Caption.Recyclerview1Adapter(list));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(Caption.this));

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
        hud = KProgressHUD.create(Caption.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getcaptionlist.php","", _rq_request_listener);

    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<Caption.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public Caption.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.captionlist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new Caption.Recyclerview1Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(Caption.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.name);
            final ImageView imageview1 = _view.findViewById(R.id.edit);
            textview1.setText(_data.get((int)_position).get("title").toString());
            imageview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Caption.this, captioncreate.class);
                    intent.putExtra("id",_data.get((int)_position).get("id").toString());
                    intent.putExtra("title",_data.get((int)_position).get("title").toString());
                    intent.putExtra("caption",_data.get((int)_position).get("content").toString());
                    intent.putExtra("type","edit");
                    startActivity(intent);
                }
            });
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
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getcaptionlist.php","", _rq_request_listener);
    }
}
