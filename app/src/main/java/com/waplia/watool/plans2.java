package com.waplia.watool;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.scales.Linear;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class plans2 extends AppCompatActivity {
    private LinearLayout mainline;
    private LinearLayout line1, line2, line3, line4, line5, line6, line7, line8, next;
    private TextView btn, planname, price;
    private RequestNetwork rq;
    private RecyclerView biolinklist;
    private RecyclerView recyclerView1;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD mProgressHUD;
    private ImageView nextimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#201d3a"));
        }
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainline = findViewById(R.id.mainline);
        btn = findViewById(R.id.btn);
        planname = findViewById(R.id.planname);
        price = findViewById(R.id.price);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        line3.setVisibility(View.GONE);
        line4.setVisibility(View.GONE);
        line5.setVisibility(View.GONE);
        line6.setVisibility(View.GONE);
        line7.setVisibility(View.GONE);
        line8.setVisibility(View.GONE);
        next = findViewById(R.id.next);
        nextimg = findViewById(R.id.nextimg);
        setRoundedCorners(mainline, "0f0d1e", "dadcdf", 20, 0);
        setRoundedCorners(btn, "5b35d5", "dadcdf", 20, 0);
        setRoundedCorners(next, "201d3a", "dadcdf", 360, 0);
        nextimg.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("plansdata", _response);
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    planname.setText(list.get(0).get("plan_name").toString());
                    price.setText("RS " + list.get(0).get("plan_price").toString());

                    if (list.get(0).get("assigned_apps").toString().contains("6")){
                    line1.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("4")){
                        line2.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("2")){
                        line3.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("7")){
                        line4.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("3")){
                        line5.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("1")){
                        line6.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("9")){
                        line7.setVisibility(View.VISIBLE);
                    }
                    if (list.get(0).get("assigned_apps").toString().contains("8")){
                        line8.setVisibility(View.VISIBLE);
                    }
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
        mProgressHUD = KProgressHUD.create(plans2.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .show();
        rqmap.put("id", 11);
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"plans.php","", _rq_request_listener);

    }
    private void setRoundedCorners(View linearLayout, String color, String scolor, float radius, int storck) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();
        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(Color.parseColor("#" + color));

        // Set the stroke (optional)
        gradientDrawable.setStroke(storck, Color.parseColor("#" + scolor));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
        linearLayout.setElevation(5);
    }
    public void parseJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray jsonArray = jsonObject.getJSONArray(key);

                String price = jsonArray.optString(0, "");
                String name = jsonArray.optString(1, "");

                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("price", price);

                list2.add(map);
            }
            Log.d("mylist", list2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void printList() {
        for (HashMap<String, Object> map : list) {
            System.out.println("name: " + map.get("name") + ", price: " + map.get("price"));
        }
    }

}
