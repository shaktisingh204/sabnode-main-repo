package com.waplia.watool;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatbots extends AppCompatActivity {
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list4 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private RequestNetwork rq2;
    private TextView dataid, textView1;
    private ImageView imageView1;
    private LinearLayout cardgone;
    private ImageView setting;
    private LinearLayout selectprofile;
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private RequestNetwork rq3;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private CardView createbot;
    private KProgressHUD hud, hud2;
    private String Instance;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbots);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Dialog dialog = new Dialog(chatbots.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        textView1 = findViewById(R.id.df);
        dataid  = findViewById(R.id.pid);
        imageView1 = findViewById(R.id.avtar);
        cardgone = findViewById(R.id.fullprofile);
        selectprofile = findViewById(R.id.selectprofile);
        createbot = findViewById(R.id.createbot);
        setting = findViewById(R.id.settings);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerview1);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatbots.this, chatbotsetting.class);
                intent.putExtra("id",Instance);
                startActivity(intent);
            }
        });
        createbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatbots.this, createchatbot.class);
                intent.putExtra("type","new");
                intent.putExtra("id",Instance);
                startActivity(intent);
            }
        });
        selectprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.dialog_list_view);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
                }
                TextView tv = dialog.findViewById(R.id.tv_popup_title);
                LinearLayout click = dialog.findViewById(R.id.click);
                RecyclerView recyclerView1 = dialog.findViewById(R.id.lv_assignment_users);
                recyclerView1.setAdapter(new chatbots.Recyclerview1Adapter(list));
                recyclerView1.setLayoutManager(new LinearLayoutManager(chatbots.this));
                MaterialButton select = dialog.findViewById(R.id.selected);

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
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
                hud.dismiss();
                try{
                    // list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response session", _response);
                    try {
                        // Parse the JSON array
                        JSONArray jsonArray = new JSONArray(_response);

                        // List to store the parsed data
                        List<Map<String, String>> dataList = new ArrayList<>();

                        // Iterate through the JSON array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // Get each JSON object from the array
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            // Map to store the key-value pairs for each object
                            Map<String, String> dataMap = new HashMap<>();

                            // Extract values and put them into the map
                            dataMap.put("id", jsonObject.getString("id"));
                            dataMap.put("ids", jsonObject.getString("ids"));
                            dataMap.put("team_id", jsonObject.getString("team_id"));
                            dataMap.put("instance_id", jsonObject.getString("token"));

                            // Parse the "data" field, which is itself a JSON string
                            JSONObject dataObject = new JSONObject(jsonObject.getString("tmp"));
                            dataMap.put("data_id", dataObject.getString("id"));
                            dataMap.put("data_name", dataObject.getString("name"));
                            dataMap.put("data_avatar", dataObject.getString("avatar"));

                            dataMap.put("status", jsonObject.getString("status"));

                            // Add the map to the list
                            dataList.add(dataMap);

                            Log.d("List", list.toString());

                        }
                        ArrayList<HashMap<String, Object>> convertedList = new ArrayList<>();
                        for (Map<String, String> originalMap : dataList) {
                            HashMap<String, Object> newMap = new HashMap<>();
                            for (Map.Entry<String, String> entry : originalMap.entrySet()) {
                                // Convert values to Object type
                                newMap.put(entry.getKey(), (Object) entry.getValue());
                            }
                            list.add(newMap);
                        }
                        Log.d("List id", list.toString());
                        rqmap3.put("id", sp.getString("id",""));


                        String data_avatar= dataList.get(0).get("data_name");
                        Log.d("data_avatar", data_avatar);
                        String data_avatar2= dataList.get(1).get("data_name");
                        Log.d("data_avatar", data_avatar2);
                        Log.d("Datalist", dataList.toString());
                        // Print the result
                        for (Map<String, String> data : dataList) {
                            System.out.println(data);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }catch(Exception e){
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                Log.d("Error Response", _message);
            }
        };
        hud = KProgressHUD.create(chatbots.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        Log.d("url", getResources().getString(R.string.base_url)+"apis/whatsappsesions.php");
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/waaccountlist.php","", _rq_request_listener);
        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    TextView cahtcount = findViewById(R.id.chatcount);
                    cahtcount.setText(String.valueOf(list2.size()));
                    Log.d("Response autobots", _response);
                    recyclerView2.setAdapter(new chatbots.Recyclerview2Adapter(list2));
                    recyclerView2.setLayoutManager(new LinearLayoutManager(chatbots.this));
                    hud2.dismiss();
                }catch(Exception e){
                    list2.clear();
                    TextView cahtcount = findViewById(R.id.chatcount);
                    cahtcount.setText(String.valueOf(list2.size()));
                    Log.d("Response autobots", _response);
                    recyclerView2.setAdapter(new chatbots.Recyclerview2Adapter(list2));
                    recyclerView2.setLayoutManager(new LinearLayoutManager(chatbots.this));
                    try {
                        hud2.dismiss();
                    }catch (Exception ex){

                    }
                    Log.d("Error instance r2", _response);
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                Log.d("Error Response", _message + "001");
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
                    Log.d("Response delete", _response);
                    rqmap2.put("id", Instance);
                    rq2.setParams(rqmap2,0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatbotslist.php","", _rq2_request_listener);
                    Toast.makeText(chatbots.this, "Deleted", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Log.d("Error delete", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<chatbots.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public chatbots.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.userprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new chatbots.Recyclerview1Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(chatbots.Recyclerview1Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final CheckBox checkbox = _view.findViewById(R.id.checkbox);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            textview1.setText(_data.get((int)_position).get("data_name").toString());
            dataid.setText(_data.get((int)_position).get("data_id").toString());
            Glide.with(chatbots.this).load(_data.get((int)_position).get("data_avatar")).into(imageview1);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profile(_position);
                }
            });
            checkbox.setVisibility(View.GONE);
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

    public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.chatbotslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
            final ImageView edit = _view.findViewById(R.id.edit);
            textview1.setText(_data.get((int)_position).get("name").toString());
             textview2.setText(_data.get((int)_position).get("description").toString());
            final ImageView menu = _view.findViewById(R.id.menu);
            final ImageView status = _view.findViewById(R.id.status);
            final LinearLayout menubar = _view.findViewById(R.id.menubar);
            final TextView date = _view.findViewById(R.id.date);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(chatbots.this, createchatbot.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("id",_data.get((int)_position).get("id").toString());
                    startActivity(intent);
                }
            });
            final ImageView delete = _view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog _alert = new AlertDialog.Builder(chatbots.this).create();
                    _alert.setTitle("Delete Contact");
                    _alert.setMessage("Are you sure you want to delete this contact?");
                    _alert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rqmap3.put("botid", _data.get((int)_position).get("id").toString());
                            rq3.setParams(rqmap3,0);
                            rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatbotdelete.php","", _rq3_request_listener);
                        }
                    });
                    _alert.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    _alert.show();

                }
            });
            Long _time = Long.parseLong(_data.get((int)_position).get("created").toString());
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
              /*  times = times +" " + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getHour();
                times = times +":" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getMinute();
                times = times +":" + timestamp.atZone(ZoneId.systemDefault()).toOffsetDateTime().getSecond();*/

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

    private void profile(Integer pos) {
        Log.d(TAG, "showAssignmentsList: " + list.get(0).get("id").toString());
        // TODO : Listen to click callbacks at the position
        textView1.setText(list.get(pos).get("data_name").toString());
        dataid.setText(list.get(pos).get("data_id").toString());
        Glide.with(chatbots.this).load(list.get(pos).get("data_avatar")).into(imageView1);
        cardgone.setVisibility(View.VISIBLE);
        createbot.setVisibility(View.VISIBLE);
   rqmap2.put("id", list.get(pos).get("instance_id").toString());
        hud2 = KProgressHUD.create(chatbots.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        Instance = list.get(pos).get("instance_id").toString();
                rq2.setParams(rqmap2,0);
        rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatbotslist.php","", _rq2_request_listener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            rqmap2.put("id", Instance);
            rq2.setParams(rqmap2,0);
            rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/chatbotslist.php","", _rq2_request_listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

