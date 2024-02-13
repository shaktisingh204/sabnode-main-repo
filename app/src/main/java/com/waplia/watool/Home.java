package com.waplia.watool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.progress.progressview.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {
private SharedPreferences sp;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private TextView totalmsgmonth;
    private TextView watsapptosend;
    private TextView watotal;
    private TextView wasucess, chatsend, autosend;
    private ProgressView progressView;
    private TextView wafailed;
    private KProgressHUD hud;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressView = (ProgressView) findViewById(R.id.progressView);
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        totalmsgmonth = findViewById(R.id.totalmsgmonth);
        watsapptosend = findViewById(R.id.watsapptosend);
        watotal = findViewById(R.id.watotal);
        chatsend = findViewById(R.id.chatsend);
        autosend = findViewById(R.id.autosend);
        wasucess = findViewById(R.id.wasucess);
        wafailed = findViewById(R.id.wafailed);
        sp = getSharedPreferences("data", MODE_PRIVATE);

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
                    totalmsgmonth.setText(list.get(0).get("wa_total_sent_by_month").toString()+"/1000000");
                    watsapptosend.setText(list.get(0).get("wa_total_sent").toString());
                    watotal.setText(list.get(0).get("wa_bulk_total_count").toString());
                    wasucess.setText(list.get(0).get("wa_bulk_sent_count").toString());
                    wafailed.setText(list.get(0).get("wa_bulk_failed_count").toString());
                    Integer progress1 = Integer.parseInt(list.get(0).get("wa_bulk_total_count").toString());
                    progress1 = progress1 + Integer.parseInt(list.get(0).get("wa_autoresponder_count").toString());
                    Integer progress2 = Integer.parseInt(list.get(0).get("wa_chatbot_count").toString());
                    Integer progress3 = Integer.parseInt(list.get(0).get("wa_total_sent").toString());
                    chatsend.setText(list.get(0).get("wa_chatbot_count").toString());
                    autosend.setText(list.get(0).get("wa_autoresponder_count").toString());
                    try {


                        Integer progress4 = progress3 / 100;
                        Integer progress5 = progress2 / progress4;
                        Log.d("progress", progress5+"%");
                        progressView.setProgress(progress5*1000);
                    } catch (Exception e) {

                        Integer progress5 = 0;
                        Log.d("progress", progress5+"%");
                        progressView.setProgress(progress5*1000);
                    }
                    rqmap2.put("id", list.get(0).get("plan").toString());
                    rq2.setParams(rqmap2,0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/planinfo.php","", _rq2_request_listener);
                    Log.d("progressview", progressView.getProgress()*100+"%");
                    Float npro = progressView.getProgress();
                    progressView.setProgress(npro);
                    Log.d("progressview1", npro+"%");
                }catch(Exception e){
                    Log.d("HResponse", _response);
                    Log.d("HResponse",e.getMessage());
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
finish();                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/report.php","", _rq_request_listener);

        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                Log.d("planresponse", _response);
                try{

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
                        dataMap.put("name", jsonObject.getString("name"));

                        // Parse the "data" field, which is itself a JSON string
                        JSONObject dataObject = new JSONObject(jsonObject.getString("permissions"));
                        dataMap.put("whatsapp_message_per_month", dataObject.getString("whatsapp_message_per_month"));
                        dataList.add(dataMap);
                    }
                    ArrayList<HashMap<String, Object>> convertedList = new ArrayList<>();
                    for (Map<String, String> originalMap : dataList) {
                        HashMap<String, Object> newMap = new HashMap<>();
                        for (Map.Entry<String, String> entry : originalMap.entrySet()) {
                            // Convert values to Object type
                            newMap.put(entry.getKey(), (Object) entry.getValue());
                        }
                        list2.add(newMap);
                        totalmsgmonth.setText(list.get(0).get("wa_total_sent_by_month").toString()+"/"+list2.get(0).get("whatsapp_message_per_month").toString());

                    }
                    for (Map<String, String> data : dataList) {
                        System.out.println(data);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            catch(Exception e){
                totalmsgmonth.setText(list.get(0).get("wa_total_sent_by_month").toString()+"/"+"1000000");
                    Log.d("HResponse",e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

    }

}

