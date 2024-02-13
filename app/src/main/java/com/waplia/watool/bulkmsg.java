package com.waplia.watool;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



public class bulkmsg extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private  String month, minuted;

    private Integer day, year, hour, minute, second;

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
    private int mYear, mMonth, mDay, mHour, mMinute, mSeceond;
    private CardView contactadd;
    private KProgressHUD hud;
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private RequestNetwork rq3;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private LinearLayout empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkmsg);
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
        search = (CardView) findViewById(R.id.search);
        contactadd = (CardView) findViewById(R.id.contactadd);
        empty = (LinearLayout) findViewById(R.id.empty);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(bulkmsg.this, phonesearch.class);
                i.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
        contactadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(bulkmsg.this, createbulk.class);
                startActivity(i);
            }
        });

        sp = getSharedPreferences("data", MODE_PRIVATE);
        Log.d("useridss", sp.getString("id", ""));
        Log.d("emailss", sp.getString("email", ""));
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
                    recyclerView1.setAdapter(new Recyclerview1Adapter(list));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(bulkmsg.this));
                    hud.dismiss();
                }catch(Exception e){
                    Log.d("Error Response", _response);
                    hud.dismiss();
                    recyclerView1.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    Toast.makeText(bulkmsg.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        hud = KProgressHUD.create(bulkmsg.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id","")/*getIntent().getStringExtra("id")*/);
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/bulklist.php","", _rq_request_listener);
/*TODO:?limit="+pagesizel.toString()+"&last=0*/
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
rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                try{
                    Toast.makeText(bulkmsg.this, "Restarted", Toast.LENGTH_SHORT).show();
                    rqmap.put("id", sp.getString("id","")/*getIntent().getStringExtra("id")*/);
                    rq.setParams(rqmap,0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/bulklist.php","", _rq_request_listener);
                    Log.d("data Response", _response);


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

    }
    @Override
    protected void onStart() {
        super.onStart();
        rqmap.put("id", sp.getString("id","")/*getIntent().getStringExtra("id")*/);
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/bulklist.php","", _rq_request_listener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        rqmap.put("id", sp.getString("id","")/*getIntent().getStringExtra("id")*/);
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/bulklist.php","", _rq_request_listener);

    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.bulklist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.name);
            final TextView textview2 = _view.findViewById(R.id.contacts);
            textview1.setText(_data.get((int)_position).get("name").toString()+" ");
            Integer i = parseInt(_data.get((int)_position).get("sent").toString())+ parseInt(_data.get((int)_position).get("failed").toString());
            Log.d("Contacts",_data.get((int)_position).get("sent").toString());
            textview2.setText(i.toString()+" contacts");
            final ImageView menu = _view.findViewById(R.id.menu);
            final ImageView status = _view.findViewById(R.id.status);
            final LinearLayout menubar = _view.findViewById(R.id.menubar);
            final TextView date = _view.findViewById(R.id.date);
            final ImageView delete = _view.findViewById(R.id.delete);
            final ImageView restart = _view.findViewById(R.id.restart);
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hud = KProgressHUD.create(bulkmsg.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rqmap3.put("reid", _data.get((int)_position).get("id").toString());
                    rq3.setParams(rqmap3,0);
                    rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/restart.php","", _rq3_request_listener);
                }
            });
            Long _time = Long.parseLong(_data.get((int)_position).get("time_post").toString());
            String x = String.valueOf(_time * 1000);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            long milliSeconds= Long.parseLong(x);
            System.out.println(milliSeconds);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            System.out.println(formatter.format(calendar.getTime()));
            Long durationInMillis = Long.parseLong(x);
            long millis = durationInMillis % 1000;
            long second = (durationInMillis / 1000) % 60;
            long minute = (durationInMillis / (1000 * 60)) % 60;
            long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

            String time = String.format("%02d:%02d:%02d.%03d", hour, minute, second, millis);
            Instant timestamp = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                timestamp = Instant.parse( formatter.format(calendar.getTime())+"T"+time + "Z");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                System.out.println(timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String times = String.valueOf(timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getDayOfMonth());
                times = times +"-" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getMonth();
                times = times +"-" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getYear();
                times = times +" " + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getHour();
                times = times +":" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getMinute();
                times = times +":" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getSecond();

                date.setText(times);
            }
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menubar.getVisibility() == View.VISIBLE) {
                        menubar.setVisibility(View.GONE);
                    }else {
                    menubar.setVisibility(View.VISIBLE);}
                }
            });
if (_data.get((int)_position).get("status").toString().equals("0")) {
    status.setImageDrawable(getResources().getDrawable(R.drawable.pause));
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
public void restaredbulk(){

    }
}
