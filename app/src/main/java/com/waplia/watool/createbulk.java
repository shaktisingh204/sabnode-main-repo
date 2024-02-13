package com.waplia.watool;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createbulk extends AppCompatActivity {
    private TextView timetext, datetext;
    private  String month, minuted;
    private String accounts;
    private RequestNetwork rq;
    private Integer day, year, hour, minute, second;
    private SharedPreferences sp;
    private LinearLayout goneline;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;

    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private RequestNetwork rq4;
private String idsnew;
    private RequestNetwork.RequestListener _rq4_request_listener;
    private ArrayList<HashMap<String, Object>> list4 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private HashMap<String, Object> submitmap = new HashMap<>();
    private RequestNetwork rq3;

    private RequestNetwork.RequestListener _rq3_request_listener;
    private ArrayList<HashMap<String, Object>> list3 = new ArrayList<>();
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private LinearLayout selectwhatapp;private LinearLayout selectcontactgroup;
    private TextView textview1;
    private TextView dataid;
    private ImageView imageview1;
    private TextView contactname;
    private CardView mainline;
    private Integer pos;
    private EditText minimum_interval, Maximum_interval, campname, caption;
    private MaterialButton create;
    private LinearLayout dateline, timeline;
    private KProgressHUD hud, hud1, hud2, hud3;
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private RecyclerView rcview;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeceond;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createbulk);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        timetext = findViewById(R.id.timetext);
        selectwhatapp = findViewById(R.id.selectprofile);
        contactname = findViewById(R.id.contactname);
        dateline = findViewById(R.id.dateline);
        timeline = findViewById(R.id.timeline);
        mainline = findViewById(R.id.mainline);
        datetext = findViewById(R.id.datetext);
        minimum_interval = findViewById(R.id.minimum_interval);
        Maximum_interval = findViewById(R.id.maximum_interval);
        campname = findViewById(R.id.campname);
        caption = findViewById(R.id.caption);
        create = findViewById(R.id.create);
        rcview = findViewById(R.id.rcprolist);
        selectcontactgroup = findViewById(R.id.selectcontactgroup);
        setRoundedCorners(campname);
        setRoundedCorners(dateline);
        setRoundedCorners(minimum_interval);
        setRoundedCorners(Maximum_interval);
        setRoundedCorners(timeline);
        setRoundedCorners(caption);
        selectwhatapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list4.clear();
                final Dialog dialog = new Dialog(createbulk.this);
                dialog.setContentView(R.layout.dialog_list_view);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
                }
                RecyclerView recyclerView1 = dialog.findViewById(R.id.lv_assignment_users);
                TextView tv = dialog.findViewById(R.id.tv_popup_title);
                MaterialButton select = dialog.findViewById(R.id.selected);
                LinearLayout click = dialog.findViewById(R.id.click);
                recyclerView1.setAdapter(new createbulk.Recyclerview1Adapter(list));
                recyclerView1.setLayoutManager(new LinearLayoutManager(createbulk.this));
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        profile(0);
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subbmit();
            }
        });
        selectcontactgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(createbulk.this);
                dialog1.setContentView(R.layout.dlgcontacts);
                if (dialog1.getWindow() != null) {
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
                }
                RecyclerView recyclerView1 = dialog1.findViewById(R.id.lv_assignment_users);
                TextView tv = dialog1.findViewById(R.id.tv_popup_title);
                MaterialButton sub = dialog1.findViewById(R.id.create);
                LinearLayout click = dialog1.findViewById(R.id.click);
                recyclerView1.setAdapter(new createbulk.Recyclerview2Adapter(list2));
                recyclerView1.setLayoutManager(new LinearLayoutManager(createbulk.this));
                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                        contactname.setText(list2.get(pos).get("name").toString());
mainline.setVisibility(View.VISIBLE);
                        dialog1.dismiss();
                        }catch (Exception e) {

                        }
                    }
                });
                dialog1.setCancelable(true);
                dialog1.show();
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, (mMonth), mDay);

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = format.format(calendar.getTime());
        Log.d("Date", strDate);
        datetext.setText(strDate);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeceond = c.get(Calendar.SECOND);

        if(mHour < 10){

            String month = "0" + mHour;
            hour = Integer.parseInt(month);
        }else{
            hour = mHour;
        }
        if(mMinute < 10){

            String minuted  = "0" + mMinute  ;
            minute = Integer.parseInt(minuted);
        }else{
            minute = mMinute ;
        }
        if(mSeceond < 10){

            String secenod  = "0" + mSeceond;
            mSeceond = Integer.parseInt(secenod);
        }else{
            mSeceond = mSeceond ;
        }
        timetext.setText( hour+ ":" + minute + ":" + mSeceond);

        // Get a reference to the TimePicker widget
        dateline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar calendar = Calendar.getInstance();
                calendar.set(mYear, (mMonth), mDay);

                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String strDate = format.format(calendar.getTime());
                Log.d("Date", strDate);
                datetext.setText(strDate);
                DatePickerDialog datePickerDialog = new DatePickerDialog(createbulk.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, (monthOfYear), dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                                String strDate = format.format(calendar.getTime());
                                Log.d("Date", strDate);
                                datetext.setText(strDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);
                        mSeceond = c.get(Calendar.SECOND);

                if(mHour < 10){

                    String month = "0" + mHour;
                    hour = Integer.parseInt(month);
                }else{
                    hour = mHour;
                }
                if(mMinute < 10){

                    String minuted  = "0" + mMinute  ;
                    minute = Integer.parseInt(minuted);
                }else{
                    minute = mMinute ;
                }
                if(mSeceond < 10){

                    String secenod  = "0" + mSeceond;
                    mSeceond = Integer.parseInt(secenod);
                }else{
                    mSeceond = mSeceond ;
                }
                timetext.setText( hour+ ":" + minute + ":" + mSeceond);

                        TimePickerDialog timepicker = new TimePickerDialog(createbulk.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                                if(hourOfDay < 10){

                                    String month = "0" + hourOfDay;
                                    hour = Integer.parseInt(month);
                                }else{
                                    hour = hourOfDay;
                                }
                                if(mMinute < 10){

                                    String minuted  = "0" + minute ;
                                    minute = Integer.parseInt(minuted);
                                }else{
                                    minute = minute;
                                }
                                timetext.setText( hour+ ":" + minute + ":" + mSeceond);
                            }
                        }, mHour, mMinute, true);
                timepicker.show();
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
                        hud2 = KProgressHUD.create(createbulk.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.8f)
                                .show();
                        rq3.setParams(rqmap3,0);
                        rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/userid.php","", _rq3_request_listener);
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
        hud = KProgressHUD.create(createbulk.this)
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
                hud1.dismiss();
                try{
                  list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response", _response);
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
        hud1 = KProgressHUD.create(createbulk.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap2.put("id", sp.getString("id",""));
        rq2.setParams(rqmap2,0);
        rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/contacts.php","", _rq2_request_listener);
        rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                hud2.dismiss();
                try{
                  list3 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("user data", _response);

                }catch(Exception e){
                    Log.d("Error Response user data", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                Log.d("Error Response", _message);
            }
        };
        rq4 = new RequestNetwork(this);
        _rq4_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                hud3.dismiss();
                try{
                  list4 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                  if (list4.get(0).get("status").toString().equals("success")){
                      Toast.makeText(createbulk.this, "Message Scheduled", Toast.LENGTH_SHORT).show();
                      Log.d("sechdeul ", _response);
                      finish();
                  }else{
                      Toast.makeText(createbulk.this, "Message Scheduled", Toast.LENGTH_SHORT).show();
                      Log.d("sechdeul ", _response);
finish();
                  }

                }catch(Exception e){
                    Toast.makeText(createbulk.this, "Message Scheduled", Toast.LENGTH_SHORT).show();
                    Log.d("sechdeul ", _response);
                    finish();
                    Log.d("Error Response user data", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                Log.d("Error Response", _message);
                Log.d("sechdeul ", _message);
            }
        };

    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<createbulk.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public createbulk.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.userprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new createbulk.Recyclerview1Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(createbulk.Recyclerview1Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            textview1.setText(_data.get((int)_position).get("data_name").toString());
            dataid.setText(_data.get((int)_position).get("data_id").toString());
            Glide.with(createbulk.this).load(_data.get((int)_position).get("data_avatar")).into(imageview1);
            final CheckBox checkBox = _view.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        list4.add(list.get(_position));
                    } else {
                        list4.remove(list.get(_position));
                    }
                }
            });
                    main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   profile(_position);
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
    public class Recyclerview2Adapter extends RecyclerView.Adapter<createbulk.Recyclerview2Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public createbulk.Recyclerview2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.contactslistdlg, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new createbulk.Recyclerview2Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(createbulk.Recyclerview2Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id. contactname);
            final ImageView selected = _view.findViewById(R.id.selected);
            final LinearLayout main = _view.findViewById(R.id.mainline);

            textview1.setText(_data.get((int)_position).get("name").toString());
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  selected.setVisibility(View.VISIBLE);
                  pos = _position;
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
    public class Recyclerview3Adapter extends RecyclerView.Adapter<createbulk.Recyclerview3Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public createbulk.Recyclerview3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.userprofiles, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new createbulk.Recyclerview3Adapter.ViewHolder(_v);        }
        @Override
        public void onBindViewHolder(createbulk.Recyclerview3Adapter.ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.username);
            final ImageView imageview1 = _view.findViewById(R.id.avtar);
            final TextView dataid = _view.findViewById(R.id.dataid);
            final LinearLayout main = _view.findViewById(R.id.mainline);
            final CheckBox checkBox = _view.findViewById(R.id.checkbox);
            checkBox.setVisibility(View.GONE);
            textview1.setText(_data.get((int)_position).get("data_name").toString());
            dataid.setText(_data.get((int)_position).get("data_id").toString());
            Glide.with(createbulk.this).load(_data.get((int)_position).get("data_avatar")).into(imageview1);
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
private void profile(Integer position) {
        CardView selected = (CardView) findViewById(R.id.selectedprofile);
        selected.setVisibility(View.VISIBLE);
    Log.d(TAG, "showAssignmentsList: " + list.get(position).get("id").toString());
    // TODO : Listen to click callbacks at the position
   /* textview1.setText(list.get(position).get("data_name").toString());
    dataid.setText(list.get(position).get("data_id").toString());
    Glide.with(createbulk.this).load(list.get(position).get("data_avatar")).into(imageview1);*/
    Log.d( "selected list: " ,list4.toString());
    rcview.setAdapter(new createbulk.Recyclerview3Adapter(list4));
    rcview.setLayoutManager(new LinearLayoutManager(createbulk.this));

}
private void subbmit() {
        // id auto
    //ids random

        submitmap.put("team_id", sp.getString("id",""));
        for (int i = 0; i <list4.size(); i++) {
            accounts = accounts + list4.get(i).get("id").toString()+ ",";
        }
        accounts = "[\"" +accounts+ "\"]";
        accounts = accounts.replace(",\"]", "\"]");
        accounts = accounts.replace("null","");
        submitmap.put("accounts", accounts);
        submitmap.put("next_account", 1);
        submitmap.put("contact_id", list2.get(pos).get("id").toString());
        submitmap.put("type", 1);
        idsnew = generateRandomCode();
        submitmap.put("ids", idsnew);
        submitmap.put("template", 0);
        submitmap.put("min_delay", minimum_interval.getText().toString());
        submitmap.put("max_delay", Maximum_interval.getText().toString());
        submitmap.put("name", campname.getText().toString());
        submitmap.put("caption", caption.getText().toString());
        submitmap.put("timezone", Calendar.getInstance().getTimeZone().getDisplayName());

        create.setEnabled(false);
    hud3 = KProgressHUD.create(createbulk.this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.8f)
            .show();
    Log.d("subbmitmap", submitmap.toString());
    rq4.setParams(submitmap,0);
    rq4.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/postbulk.php","", _rq4_request_listener);

}



    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            int index = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            char randomChar = ALPHANUMERIC_CHARACTERS.charAt(index);
            code.append(randomChar);
        }

        return code.toString();
    }
    public Long gettimestamp() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, (mMonth), mDay);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = format.format(calendar.getTime());
        Log.d("Date", strDate);
        datetext.setText(strDate);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeceond = c.get(Calendar.SECOND);

        if(mHour < 10){

            String month = "0" + mHour;
            hour = Integer.parseInt(month);
        }else{
            hour = mHour;
        }
        if(mMinute < 10){

            String minuted  = "0" + mMinute  ;
            minute = Integer.parseInt(minuted);
        }else{
            minute = mMinute ;
        }
        if(mSeceond < 10){

            String secenod  = "0" + mSeceond;
            mSeceond = Integer.parseInt(secenod);
        }else{
            mSeceond = mSeceond ;
        }
        String Date = strDate;
        String timenew = hour+ ":" + minute + ":" + mSeceond;
        String myDate = strDate + " " + timenew;
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.parse(myDate,
                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
        }
        long millis = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            millis = localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        }
        Log.d(TAG, "Timems: " + millis);
        Long time = millis;
        time = time/1000;
        return time;
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

