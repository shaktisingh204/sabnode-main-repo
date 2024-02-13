package com.waplia.watool;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class profile extends AppCompatActivity {
    private ImageView avtar;
    private TextView username;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private TextView pid;
    private SharedPreferences sp;
    private TextView token;
    private Integer pose;
    private  LinearLayout profileline;
    private TextView dataid, textView1, instid;
    private ImageView imageView1;
    private LinearLayout cardgone;
    private ImageView setting;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        username = findViewById(R.id.username);
        avtar = findViewById(R.id.avtar);
        pid = findViewById(R.id.pid);
        instid = findViewById(R.id.instid);
        token = findViewById(R.id.token);
        CardView profilecard = findViewById(R.id.profilecard);
        profileline = findViewById(R.id.profileline);
        textView1 = findViewById(R.id.username);
        dataid  = findViewById(R.id.pid);
        imageView1 = findViewById(R.id.avtar);
        profilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    final Dialog dialog = new Dialog(profile.this);
                    dialog.setContentView(R.layout.dialog_list_view);
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
                    }
                    TextView tv = dialog.findViewById(R.id.tv_popup_title);
                    LinearLayout click = dialog.findViewById(R.id.click);
                    RecyclerView recyclerView1 = dialog.findViewById(R.id.lv_assignment_users);
                    recyclerView1.setAdapter(new profile.Recyclerview1Adapter(list2));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(profile.this));
                    MaterialButton select = dialog.findViewById(R.id.selected);

                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                dialog.dismiss();

                        }
                    });
                    dialog.setCancelable(true);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    dialog.show();

            }
        });
        sp = getSharedPreferences("data", MODE_PRIVATE);
        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;

                try{
                    hud.dismiss();
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
                            Log.d("List", list2.toString());
                        }
                        ArrayList<HashMap<String, Object>> convertedList = new ArrayList<>();
                        for (Map<String, String> originalMap : dataList) {
                            HashMap<String, Object> newMap = new HashMap<>();
                            for (Map.Entry<String, String> entry : originalMap.entrySet()) {
                                // Convert values to Object type
                                newMap.put(entry.getKey(), (Object) entry.getValue());
                            }
                            list2.add(newMap);
                        }
                        Log.d("List id", list.toString());
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
        hud = KProgressHUD.create(profile.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap2.put("id", sp.getString("id",""));
        rq2.setParams(rqmap2,0);
        Log.d("url", getResources().getString(R.string.base_url)+"apis/whatsappsesions.php");
        rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/waaccountlist.php","", _rq2_request_listener);

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
                    instid.setText(list.get(0).get("token").toString());
                    /*token.setText(list.get(0).get("idsn").toString());*/
                }catch(Exception e){

                    Log.d("Error Response  nn", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };


    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<profile.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public profile.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.userprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new profile.Recyclerview1Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(profile.Recyclerview1Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final CheckBox checkbox = _view.findViewById(R.id.checkbox);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            textview1.setText(_data.get((int)_position).get("data_name").toString());
            dataid.setText(_data.get((int)_position).get("data_id").toString());
            Glide.with(profile.this).load(_data.get((int)_position).get("data_avatar")).into(imageview1);
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
    private void profile(Integer pos) {
        pose = pos;
        // TODO : Listen to click callbacks at the position
        textView1.setText(list2.get(pos).get("data_name").toString());
        dataid.setText(list2.get(pos).get("data_id").toString());
        Glide.with(profile.this).load(list2.get(pos).get("data_avatar")).into(imageView1);
        profileline.setVisibility(View.VISIBLE);
        instid.setText(list2.get(pos).get("instance_id").toString());
        /*rqmap.put("id",  list2.get(pos).get("instance_id").toString());
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/profilenew.php","", _rq_request_listener);
    */}
}
