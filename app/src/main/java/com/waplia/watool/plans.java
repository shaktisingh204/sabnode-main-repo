package com.waplia.watool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.biolink.biolinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class plans extends AppCompatActivity {
    private RequestNetwork rq;
    private RecyclerView biolinklist;
    private RecyclerView recyclerView1;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD mProgressHUD;
    private TextView planname;
    private LinearLayout line1, line2, line3, line4, line5, line6, line7, line8, next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        planname = findViewById(R.id.planname);
        ViewPager viewPager = findViewById(R.id.viewPager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this);
        viewPager.setAdapter(adapter);
        // Set the custom PageTransformer
        viewPager.setPageTransformer(false, new EnlargedPageTransformer());
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);
        next = findViewById(R.id.next);
        biolinklist = findViewById(R.id.biolinklist1);
        recyclerView1 = findViewById(R.id.biolinklist1);
        setRoundedCorners(line1, "00000000", "dadcdf", 20);
        setRoundedCorners(line2, "00000000", "dadcdf", 20);
        setRoundedCorners(line3, "00000000", "dadcdf", 20);
        setRoundedCorners(line4, "00000000", "dadcdf", 20);
        setRoundedCorners(line5, "00000000", "dadcdf", 20);
        setRoundedCorners(line6, "00000000", "dadcdf", 20);
        setRoundedCorners(line7, "00000000", "dadcdf", 20);
        setRoundedCorners(line8, "00000000", "dadcdf", 20);
        setRoundedCorners(next, "ffffff", "dadcdf", 360);
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
                    String jsonString = list.get(0).get("durations").toString();
                   parseJson(jsonString);
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
        mProgressHUD = KProgressHUD.create(plans.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .show();

        rqmap.put("id", 11);
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"plans.php","", _rq_request_listener);

    }
    private void setRoundedCorners(View linearLayout, String color, String scolor, float radius) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(Color.parseColor("#" + color));

        // Set the stroke (optional)
        gradientDrawable.setStroke(4, Color.parseColor("#" + scolor));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
        linearLayout.setElevation(5);
    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<plans.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public plans.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.planslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new plans.Recyclerview1Adapter.ViewHolder(_v);

        }
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public void onBindViewHolder(plans.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final LinearLayout plans = (LinearLayout) _view.findViewById(R.id.plans);
            /*final TextView name = _view.findViewById(R.id.name);
            final TextView url = _view.findViewById(R.id.url);
            final TextView clicks = _view.findViewById(R.id.clicks);
            final ImageView icon = _view.findViewById(R.id.icon);
            final ImageView stats = _view.findViewById(R.id.stats);*/
            setRoundedCorners(plans, "00000000", "dadcdf", 20);


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
            listviewshow();
            Log.d("mylist", list2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
public void listviewshow(){
        try {
            recyclerView1.setLayoutManager(new LinearLayoutManager(plans.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView1.setAdapter(new Recyclerview1Adapter(list2));}catch (Exception e) {e.printStackTrace();}
}
    private void printList() {
        for (HashMap<String, Object> map : list) {
            System.out.println("name: " + map.get("name") + ", price: " + map.get("price"));
        }
    }

}
