package com.waplia.watool.scproof;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.ui.dashboard;
import com.waplia.watool.ui.site;


import java.util.ArrayList;
import java.util.HashMap;

public class socialdash extends AppCompatActivity {
    private ImageView database, notification, uses;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq3;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private ArrayList<HashMap<String, Object>> list3 = new ArrayList<>();
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private SharedPreferences sp;
    private KProgressHUD hud2;

    private CardView cards, add;
    private RecyclerView recyclerView, recyclerView1;
    private TextView notifications, campaign;
    private LottieAnimationView lottie, lottie1;
    private String userid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialdash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = findViewById(R.id.database);
        notifications = findViewById(R.id.notifications);
        campaign = findViewById(R.id.campaign);
        cards = findViewById(R.id.cards);
        notification = findViewById(R.id.notification);
        uses = findViewById(R.id.uses);
        lottie = findViewById(R.id.lottie);
        database.setColorFilter(Color.parseColor("#784acf"));
        notification.setColorFilter(Color.parseColor("#784acf"));
        uses.setColorFilter(Color.parseColor("#784acf"));
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView1 = findViewById(R.id.recyclerview2);
        lottie1 = findViewById(R.id.lottie2);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newcamp.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
        sp = getSharedPreferences("sabdata", MODE_PRIVATE);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try {

                    Log.d("dashresponse", "" + _response);

                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                   lottie.setVisibility(View.GONE);
                      campaign.setText(list.get(0).get("total").toString()+" campaigns");
                      notifications.setText(list.get(0).get("notifications").toString()+" notifications");
                      userid = list.get(0).get("userid").toString();
                    rqmap2.put("id", list.get(0).get("userid").toString());
                    rq2.setParams(rqmap2, 0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "socialproof/socialdata.php", "", _rq2_request_listener);
 rqmap3.put("id", list.get(0).get("userid").toString());
                    rq3.setParams(rqmap3, 0);
                    rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "socialproof/notifications.php", "", _rq3_request_listener);

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
        hud2 = KProgressHUD.create(socialdash .this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("email", sp.getString("email", ""));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "socialproof/dashboard.php", "", _rq_request_listener);
 rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try {

                    Log.d("dashresponse", "" + _response);
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    lottie.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new socialdash.Recyclerview6Adapter(list2));
                    recyclerView.setLayoutManager(new LinearLayoutManager(socialdash.this));

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


 rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                hud2.dismiss();

                try {
                    hud2.dismiss();
                    Log.d("dashresponse", "" + _response);
                    list3 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    lottie1.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    recyclerView1.setAdapter(new socialdash.Recyclerview7Adapter(list3));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(socialdash.this));

                       } catch (Exception e) {
                    hud2.dismiss();

                    e.printStackTrace();
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

        rqmap.put("email", sp.getString("email", ""));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "socialproof/dashboard.php", "", _rq_request_listener);
    }

    public class Recyclerview6Adapter extends RecyclerView.Adapter<socialdash.Recyclerview6Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview6Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public socialdash.Recyclerview6Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.campaign, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new socialdash.Recyclerview6Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(socialdash.Recyclerview6Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final ImageView menubar = _view.findViewById(R.id.menubar);
            final LinearLayout goneline = _view.findViewById(R.id.goneline);
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview3 = _view.findViewById(R.id.cdis);
            final Switch switch1 = _view.findViewById(R.id.switch1);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview3.setText(_data.get((int)_position).get("domain").toString());
            if (_data.get((int)_position).get("is_enabled").toString().equals("1")) {
                switch1.setChecked(true);
            }else {
                switch1.setChecked(false);
            }
            menubar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (goneline.getVisibility() == View.VISIBLE) {
                        goneline.setVisibility(View.GONE);
                    } else {
                        goneline.setVisibility(View.VISIBLE);
                    }
                }
            });
            /*final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview3 = _view.findViewById(R.id.cdis);
            final ImageView flag = _view.findViewById(R.id.visitic);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview3.setText(_data.get((int)_position).get("wesites_ids").toString().replace("]", "").replace("[", "") + " Websocialdashs");
            flag.setColorFilter(getResources().getColor(R.color.purple_200), PorterDuff.Mode.MULTIPLY);
         */   /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
           flag.setImageResource(flags);*/
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
    public class Recyclerview7Adapter extends RecyclerView.Adapter<socialdash.Recyclerview7Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview7Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public socialdash.Recyclerview7Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.notifications, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new socialdash.Recyclerview7Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(socialdash.Recyclerview7Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final ImageView menubar = _view.findViewById(R.id.menubar);
            final LinearLayout goneline = _view.findViewById(R.id.goneline);
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview3 = _view.findViewById(R.id.cdis);
            final Switch switch1 = _view.findViewById(R.id.switch1);
            final ImageView noti = _view.findViewById(R.id.noti_ic);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview3.setText(_data.get((int)_position).get("type").toString());
            noti.setColorFilter(getResources().getColor(R.color.purple_700), PorterDuff.Mode.MULTIPLY);
            if (_data.get((int)_position).get("is_enabled").toString().equals("1")) {
                switch1.setChecked(true);
            }else {
                switch1.setChecked(false);
            }
            menubar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (goneline.getVisibility() == View.VISIBLE) {
                        goneline.setVisibility(View.GONE);
                    } else {
                        goneline.setVisibility(View.VISIBLE);
                    }
                }
            });
            /*final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview3 = _view.findViewById(R.id.cdis);
            final ImageView flag = _view.findViewById(R.id.visitic);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview3.setText(_data.get((int)_position).get("wesites_ids").toString().replace("]", "").replace("[", "") + " Websocialdashs");
            flag.setColorFilter(getResources().getColor(R.color.purple_200), PorterDuff.Mode.MULTIPLY);
         */   /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
           flag.setImageResource(flags);*/
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

}
