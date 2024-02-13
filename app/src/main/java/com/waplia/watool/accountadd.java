package com.waplia.watool;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class accountadd extends AppCompatActivity {
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private RequestNetwork rq3;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private ArrayList<HashMap<String, Object>> list3 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();

    private RequestNetwork rq4;
    private RequestNetwork.RequestListener _rq4_request_listener;
    private ArrayList<HashMap<String, Object>> list4 = new ArrayList<>();
    private HashMap<String, Object> rqmap4 = new HashMap<>();
    private TextView pid;
    private SharedPreferences sp;
    private TextView instance;
    private ScrollView mainscrollview;
    private TextView token;

    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountadd);
        instance = (TextView) findViewById(R.id.instance);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerview1);
CardView refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hud = KProgressHUD.create(accountadd.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.8f)
                        .show();
                rqmap.put("id", sp.getString("id",""));
                rq.setParams(rqmap,0);
                rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/profile.php","", _rq_request_listener);
            }
        });
        sp = getSharedPreferences("data", MODE_PRIVATE);
        mainscrollview = findViewById(R.id.mainscrollview);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{

                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response profile", _response);
                 /*   rqmap2.put("id", sp.getString("id",""));
                    rq2.setParams(rqmap2,0);*/
                    rq2.startRequestNetwork(RequestNetworkController.GET, "https://bulkreply.wapliaa.com/api/create_instance?access_token="+list.get(0).get("idsn").toString(),"", _rq2_request_listener);
                    Log.d("Intance url", "https://bulkreply.wapliaa.com/api/create_instance?access_token="+list.get(0).get("idsn").toString());

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
        hud = KProgressHUD.create(accountadd.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/profile.php","", _rq_request_listener);


        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{

                    list2 = new Gson().fromJson("[" + _response + "]", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()); //new Gson().fromJson("_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response intance", _response);
                    instance.setText(list2.get(0).get("instance_id").toString());
                    rq3.startRequestNetwork(RequestNetworkController.GET, "https://bulkreply.wapliaa.com/api/get_qrcode?instance_id="+list2.get(0).get("instance_id").toString()+"&access_token="+list.get(0).get("idsn").toString(),"", _rq3_request_listener);
                    Log.d("url Image", "https://bulkreply.wapliaa.com/api/get_qrcode?instance_id="+list2.get(0).get("instance_id").toString()+"&access_token="+list.get(0).get("idsn").toString());
                }catch(Exception e){
                    Log.d("Error Image", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{

                    list3 = new Gson().fromJson("[" + _response + "]", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()); //new Gson().fromJson("_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    ImageView image =(ImageView)findViewById(R.id.image);

                    //encode image to base64 string
                    String base64String = list3.get(0).get("base64").toString();
                    hud.dismiss();
                    //decode base64 string to image
                    String base64Image = base64String.split(",")[1];
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    image.setImageBitmap(decodedByte);
                    Log.d("Response Image", _response);
                    rqmap4.put("id", sp.getString("id",""));
                    rq4.setParams(rqmap4,0);
                    rq4.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getallids.php","", _rq4_request_listener);
                }catch(Exception e){
                    Log.d("Error Image", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

rq4 = new RequestNetwork(this);
        _rq4_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud.dismiss();
                    mainscrollview.setVisibility(View.VISIBLE);
                    list4 = new Gson().fromJson( _response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()); //new Gson().fromJson("_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerView1.setAdapter(new accountadd.Recyclerview1Adapter(list4));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(accountadd.this));
                    Log.d("Response Image", _response);
                }catch(Exception e){
                    Log.d("Error Image", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };


    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<accountadd.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public accountadd.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.waprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new accountadd.Recyclerview1Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(accountadd.Recyclerview1Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            final CardView login = _view.findViewById(R.id.login);
            textview1.setText(_data.get((int)_position).get("name").toString());
            dataid.setText(_data.get((int)_position).get("pid").toString());
            if (_data.get((int)_position).get("avatar").toString().contains("avatar")) {
                Glide.with(accountadd.this).load(getResources().getString(R.string.base_url)+"writable/"+_data.get((int)_position).get("avatar")).into(imageview1);

            }else {
                Glide.with(accountadd.this).load(_data.get((int)_position).get("avatar")).into(imageview1);

            }
            if (Integer.parseInt(_data.get((int)_position).get("status").toString()) == 1){
                login.setVisibility(View.GONE);
            }
             login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relogin(_data.get((int)_position).get("token").toString());
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
        public void relogin(String _instance){
            hud = KProgressHUD.create(accountadd.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.8f)
                    .show();
            rq2.startRequestNetwork(RequestNetworkController.GET, "https://bulkreply.wapliaa.com/api/create_instance?access_token="+list.get(0).get("idsn").toString(),"", _rq2_request_listener);
            Log.d("Intance url", "https://bulkreply.wapliaa.com/api/create_instance?access_token="+list.get(0).get("idsn").toString());
        }
}
