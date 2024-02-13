package com.waplia.watool;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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


public class autoresponder extends AppCompatActivity {
    private LinearLayout selectwhatapp;
   Integer localstatus = 0;
    private RequestNetwork rq;
    private Integer statuspost, sendto = 1;
    private LinearLayout goneline;
    private Integer day, year, hour, minute, second;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private ArrayList<HashMap<String, Object>> list3 = new ArrayList<>();
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private RequestNetwork rq3;
    private TextView textview1;
    private TextView dataid;
    private ImageView imageview1;
    private RadioButton enable, disable, all, indi, group;
    private MaterialButton create;
    private EditText caption, minimum_interval,numbers;
    private KProgressHUD hud, hud2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoresponder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText number = findViewById(R.id.numbers);
        textview1 = findViewById(R.id.username);
        imageview1 = findViewById(R.id.avtar);
        dataid = findViewById(R.id.dataid);
        goneline = findViewById(R.id.goneline);
        selectwhatapp = findViewById(R.id.selectprofile);
        enable = findViewById(R.id.enable);
        disable = findViewById(R.id.disable);
        all = findViewById(R.id.all);
        indi = findViewById(R.id.indi);
        create = findViewById(R.id.create);
        group = findViewById(R.id.group);
        caption = findViewById(R.id.caption);
        minimum_interval = findViewById(R.id.minimum_interval);
        numbers = findViewById(R.id.numbers);
        setRoundedCorners(caption);
        setRoundedCorners(minimum_interval);
        setRoundedCorners(numbers);
        selectwhatapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(autoresponder.this);
                dialog.setContentView(R.layout.dialog_list_view);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
                }
                RecyclerView recyclerView1 = dialog.findViewById(R.id.lv_assignment_users);
                TextView tv = dialog.findViewById(R.id.tv_popup_title);
                LinearLayout click = dialog.findViewById(R.id.click);
                recyclerView1.setAdapter(new autoresponder.Recyclerview1Adapter(list));
                recyclerView1.setLayoutManager(new LinearLayoutManager(autoresponder.this));
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
        enable.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enable.isChecked()) {
                    statuspost = 1;

                } else {

                }
            }
        });
          disable.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enable.isChecked()) {

                    statuspost = 0;
                } else {

                }
            }
        });
          all.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enable.isChecked()) {
                    sendto = 1;
                    rqmap.put("send_to", "1");
                } else {

                }
            }
        });
          disable.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enable.isChecked()) {
                    second = 2;
                    rqmap.put("send_to", "2");
                } else {

                }
            }
        });
          disable.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enable.isChecked()) {
                    second = 3;
                    rqmap.put("send_to", "3");
                } else {

                }
            }
        });
          create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d("localstatus", String.valueOf(localstatus));
                    if (enable.isChecked()) {
                        statuspost = 1;
                    }else {
                        if (disable.isChecked()) {
                            statuspost = 0;
                        }
                    }
                    if (all.isChecked()) {
                        sendto = 1;
                    }else{
                        if (indi.isChecked()) {
                            sendto = 2;
                        }else{
                            if (group.isChecked()) {
                                sendto = 3;
                            }
                        }

                    }
                if (localstatus == 1) {
                    rqmap3.clear();
                    rqmap3.put("id", list2.get(0).get("id").toString());
                    rqmap3.put("caption", caption.getText().toString());
                    rqmap3.put("media", "null");
                    rqmap3.put("send_to", sendto);
                    rqmap3.put("status", statuspost);
                    rqmap3.put("except", number.getText().toString());
                    rqmap3.put("delay", minimum_interval.getText().toString());
                    rq3.setParams(rqmap3,0);
                    rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/updateauto.php","", _rq3_request_listener);
                }else {
                    rqmap3.clear();
                    rqmap3.put("id", sp.getString("id", "").toString());
                    rqmap3.put("instance_id", list.get(0).get("instance_id").toString());
                    rqmap3.put("caption", caption.getText().toString());
                    rqmap3.put("media", "null");
                    rqmap3.put("send_to", sendto);
                    rqmap3.put("status", statuspost);
                    rqmap3.put("except", number.getText().toString());
                    rqmap3.put("delay", minimum_interval.getText().toString());
                    hud2 = KProgressHUD.create(autoresponder.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rq3.setParams(rqmap3,0);
                    rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/addauto.php","", _rq3_request_listener);
                }
            }
          });
        rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Toast.makeText(autoresponder.this, "AutoResponse Updated", Toast.LENGTH_SHORT).show();
                    rqmap2.put("id", list.get(0).get("instance_id").toString());
                    rq2.setParams(rqmap2,0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getautodata.php","", _rq2_request_listener);
                hud2.dismiss();
                }catch(Exception e){
                    hud2.dismiss();
                  Toast.makeText(autoresponder.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    rqmap2.put("id", list.get(0).get("instance_id").toString());
                    rq2.setParams(rqmap2,0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getautodata.php","", _rq2_request_listener);
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                localstatus = 0;
                Log.d("Error Response", _message);
            }
        };

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changing
                final String _charSeq = s.toString();
                /*textview1.setText(_charSeq);*/


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(",")) {
                }
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
                        hud.dismiss();
                        /*rqmap3.put("id", sp.getString("id",""));
                        rq3.setParams(rqmap3,0);
                        rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/userid.php","", _rq3_request_listener);
                        */String data_avatar= dataList.get(0).get("data_name");
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
        hud = KProgressHUD.create(autoresponder.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
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
                    Log.d("Response", _response);
                    localstatus = 1;
                    if (list2.get(0).get("status").toString().equals("1")) {
                        enable.setChecked(true);
                        /*disable.setSelected(false);*/
                    }else{
                        disable.setChecked(true);
                        /*enable.setSelected(false);*/
                    }
                     if (Integer.parseInt(list2.get(0).get("send_to").toString()) == 1) {
                        all.setChecked(true);
                       /* indi.setSelected(false);
                        group.setSelected(false);*/
                    }else{
                         if (Integer.parseInt(list2.get(0).get("send_to").toString()) == 2) {
                         /*all.setChecked(false);*/
                         indi.setChecked(true);
                         /*group.setChecked(false);*/
                     }else{
                        if (Integer.parseInt(list2.get(0).get("send_to").toString()) == 3) {
                      /*  all.setChecked(false);
                        indi.setChecked(false);*/
                         group.setChecked(true);
                     }} }
                     caption.setText(list2.get(0).get("caption").toString());
                     minimum_interval.setText(list2.get(0).get("delay").toString());
                     number.setText(list2.get(0).get("except").toString());

                }catch(Exception e){
                     disable.setChecked(true);
                    all.setChecked(true);
                    Log.d("Error instance r2", _response);
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                localstatus = 0;
                Log.d("Error Response", _message + "001");
            }
        };


    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<autoresponder.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public autoresponder.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.userprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new autoresponder.Recyclerview1Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(autoresponder.Recyclerview1Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final CheckBox checkbox = _view.findViewById(R.id.checkbox);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            textview1.setText(_data.get((int)_position).get("data_name").toString());
            dataid.setText(_data.get((int)_position).get("data_id").toString());
            Glide.with(autoresponder.this).load(_data.get((int)_position).get("data_avatar")).into(imageview1);
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
    private void profile(int pos) {
        CardView selected = (CardView) findViewById(R.id.selectedprofile);
        selected.setVisibility(View.VISIBLE);
        Log.d(TAG, "showAssignmentsList: " + list.get(pos).get("id").toString());
        // TODO : Listen to click callbacks at the position
        textview1.setText(list.get(pos).get("data_name").toString());
        dataid.setText(list.get(pos).get("data_id").toString());
        Glide.with(autoresponder.this).load(list.get(pos).get("data_avatar")).into(imageview1);
        goneline.setVisibility(View.VISIBLE);
        rqmap2.put("id", list.get(pos).get("instance_id").toString());
        rq2.setParams(rqmap2,0);
        rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/getautodata.php","", _rq2_request_listener);
    }
    private void setRoundedCorners(View linearLayout) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(getResources().getColor(R.color.edittextback));

        // Set the stroke (optional)
        gradientDrawable.setStroke(2, getResources().getColor(R.color.edittextback));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
    }}
