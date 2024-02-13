package com.waplia.watool.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.blongho.country_data.World;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class site extends AppCompatActivity {
    private RecyclerView recyclerView1, recycler, recycler1;
    private SharedPreferences sp;
    private RequestNetwork rq;

    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq8;

    private RequestNetwork.RequestListener _rq8_request_listener;
    private ArrayList<HashMap<String, Object>> list8 = new ArrayList<>();
    private HashMap<String, Object> rqmap8 = new HashMap<>();
 private RequestNetwork rq7;

    private RequestNetwork.RequestListener _rq7_request_listener;
    private ArrayList<HashMap<String, Object>> list7 = new ArrayList<>();
    private HashMap<String, Object> rqmap7 = new HashMap<>();

    private RequestNetwork rq3;

    private RequestNetwork.RequestListener _rq3_request_listener;
    private ArrayList<HashMap<String, Object>> list3 = new ArrayList<>();
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private KProgressHUD hud;
    private RequestNetwork rq1;

    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private RequestNetwork rq2;

    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private RequestNetwork rq5;

    private RequestNetwork.RequestListener _rq5_request_listener;
    private ArrayList<HashMap<String, Object>> list5 = new ArrayList<>();
    private HashMap<String, Object> rqmap5 = new HashMap<>();
    private KProgressHUD hud1;
    private ImageView homeic, goalic, realic, teamic;
    private LinearLayout  realtime, teams, goals;
    private LinearLayout dashb, goalsb, realb, teamsb;

    private ScrollView dashboard;
    private TextView dashtxt, goaltxt, realtxt, teamtxt;
    private LinearLayout nameback, websiteback, typeback, typeback1, goneline, goneline1;
    private TextView signin;
    private CardView add;
    private LottieAnimationView lottie;
    private TextView typetxt, typetxt1, lighttxt, advancedtxt;
    private  ImageView checkadvanced, checkedlight;
    private EditText name, website;
    private ImageView webic, nameic, trackic, moreic, moreic1;
    private ImageView countryic, devicesic, pagesic, countryno, devicegone, pagesgone;
    private TextView totalviews;
    private RecyclerView recyclerview2, recyclerView4, recyclerView5, recyclerView6, recyclerView8;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView db = (ImageView) findViewById(R.id.db);
        db.setColorFilter(getResources().getColor(R.color.md_blue_A700));
    lottie = findViewById(R.id.lottie);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview1);
       CardView add = (CardView) findViewById(R.id.add);
       homeic = findViewById(R.id.homeic);
       goalic = findViewById(R.id.goalic);
       realic = findViewById(R.id.realic);
       teamic = findViewById(R.id.teamic);
       homeic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
       goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
       realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
       teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
       realtime = findViewById(R.id.realtime);
       dashboard = findViewById(R.id.dashboard);
       goals = findViewById(R.id.goals);
       teams = findViewById(R.id.teams);
       dashb = findViewById(R.id.dashb);
       goalsb = findViewById(R.id.goleb);
       realb = findViewById(R.id.realb);
       teamsb = findViewById(R.id.teamsb);
        dashtxt = findViewById(R.id.dashtxt);
        goaltxt = findViewById(R.id.goaltxt);
        realtxt = findViewById(R.id.realtxt);
        teamtxt = findViewById(R.id.teamtxt);
        typeback = (LinearLayout) findViewById(R.id.typeback);
        typeback1 = (LinearLayout) findViewById(R.id.typeback1);
        devicesic = findViewById(R.id.devicesic);
        pagesic = findViewById(R.id.pagesic);
        countryic = findViewById(R.id.countryic);
        goneline = findViewById(R.id.goneline);
        goneline1 = findViewById(R.id.goneline1);
        moreic = findViewById(R.id.moreic);
        moreic1 = findViewById(R.id.moreic1);
        typetxt = findViewById(R.id.typetxt);
        typetxt1 = findViewById(R.id.typetxt1);
        lighttxt = findViewById(R.id.lighttxt);
        advancedtxt = findViewById(R.id.advancedtxt);
        checkedlight = findViewById(R.id.checkedlight);
        checkadvanced = findViewById(R.id.checkadvanced);
        countryic.setColorFilter(getResources().getColor(R.color.md_blue_A700));
        pagesic.setColorFilter(getResources().getColor(R.color.md_blue_A700));
        devicesic.setColorFilter(getResources().getColor(R.color.md_blue_A700));
        setRoundedCorners(typeback, "00000000", "dadcdf");
        setRoundedCorners(typeback1, "00000000", "dadcdf");
        recycler = findViewById(R.id.recycler);
        recycler1 = findViewById(R.id.recycler1);
        countryno = findViewById(R.id.countryno);
        recyclerview2 = findViewById(R.id.recyclerview2);
        devicegone = findViewById(R.id.devicegone);
        pagesgone = findViewById(R.id.pagesgone);
        recyclerView4 = findViewById(R.id.recyclerview3);
        recyclerView5 = findViewById(R.id.recyclerview4);
        recyclerView6 = findViewById(R.id.recyclerview6);
        recyclerView8 = findViewById(R.id.recyclerview8);
        totalviews = findViewById(R.id.totalviews);
        World.init(getApplicationContext()); // Initializes the libray and loads all data
        typeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goneline.getVisibility() == View.VISIBLE) {
                    goneline.setVisibility(View.GONE);
                    moreic.setRotation(90);
                } else {
                    goneline.setVisibility(View.VISIBLE);
                    moreic.setRotation(-90);
                }
            }
        });
        typeback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goneline1.getVisibility() == View.VISIBLE) {
                    goneline1.setVisibility(View.GONE);
                    moreic1.setRotation(90);
                } else {
                    goneline1.setVisibility(View.VISIBLE);
                    moreic1.setRotation(-90);
                }
            }
        });

        realb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dashboard.setVisibility(View.GONE);
               teams.setVisibility(View.GONE);
               goals.setVisibility(View.GONE);
               realtime.setVisibility(View.VISIBLE);
               homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               realic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
               teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
               goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_goal));
               realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_realbold));
               teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_team));
               dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               realtxt.setTextColor(getResources().getColor(R.color.purple_500));
               teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               websiteselect(list.get(0).get("host").toString(), list.get((int)0).get("website_id").toString(), 0);
           }
       });
       dashb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dashboard.setVisibility(View.VISIBLE);
               realtime.setVisibility(View.GONE);
               teams.setVisibility(View.GONE);
               goals.setVisibility(View.GONE);
               realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               homeic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
               teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_homebold));
               goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_goal));
               realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_real));
               teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_team));
               dashtxt.setTextColor(getResources().getColor(R.color.purple_500));
               goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
           }
       });
       teamsb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               teams.setVisibility(View.VISIBLE);
               realtime.setVisibility(View.GONE);
               goals.setVisibility(View.GONE);
               dashboard.setVisibility(View.GONE);
               realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               teamic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
               homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               homeic.setImageDrawable(getResources().getDrawable(R.drawable.home));
               goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_goal));
               realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_real));
               teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_teambold));
               teamtxt.setTextColor(getResources().getColor(R.color.purple_500));
               goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));

           }
       });
       goalsb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               teams.setVisibility(View.GONE);
               realtime.setVisibility(View.GONE);
               goals.setVisibility(View.VISIBLE);
               dashboard.setVisibility(View.GONE);
               realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               goalic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
               homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
               homeic.setImageDrawable(getResources().getDrawable(R.drawable.home));
               goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_goalbold));
               realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_real));
               teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_team));
               goaltxt.setTextColor(getResources().getColor(R.color.purple_500));
               teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
               dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));

           }
       });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(site.this, addsite.class);
                startActivity(intent);
            }
        });

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
                    recyclerView1.setAdapter(new Recyclerview1Adapter(list));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(site.this));
                    recycler.setAdapter(new Recyclerview2Adapter(list));
                    recycler.setLayoutManager(new LinearLayoutManager(site.this));
                    recycler1.setAdapter(new Recyclerview2Adapter(list));
                    recycler1.setLayoutManager(new LinearLayoutManager(site.this));
                    typetxt.setText(list.get(0).get("host").toString());
                    typetxt1.setText(list.get(0).get("host").toString());
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
        sp = getSharedPreferences("sabdata", MODE_PRIVATE);
        hud = KProgressHUD.create(site.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();

        rqmap.put("email", sp.getString("email", ""));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"getwebsitelist.php","", _rq_request_listener);

        rq8 = new RequestNetwork(this);
        _rq8_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud.dismiss();
                    list8 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerView8.setAdapter(new Recyclerview8Adapter(list8));
                    recyclerView8.setLayoutManager(new LinearLayoutManager(site.this));
                    lottie.setVisibility(View.GONE);
                    recyclerView8.setVisibility(View.VISIBLE);
                }catch(Exception e){
                    e.printStackTrace();
                    Log.d("Error Response", _response);
                    lottie.setVisibility(View.VISIBLE);
                    recyclerView8.setVisibility(View.GONE);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
 rq7 = new RequestNetwork(this);
        _rq7_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud.dismiss();
                    Log.d("team Response", _response);

                    list7 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerView6.setAdapter(new Recyclerview6Adapter(list7));
                    recyclerView6.setLayoutManager(new LinearLayoutManager(site.this));
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
        rqmap7.put("email", sp.getString("email", ""));
        rq7.setParams(rqmap7, 0);
        rq7.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"teams.php","", _rq7_request_listener);
        rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("websitedata", _response);
                    list1 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    countryno.setVisibility(View.GONE);
                    recyclerview2.setAdapter(new Recyclerview3Adapter(list1));
                    recyclerview2.setLayoutManager(new LinearLayoutManager(site.this));
                    totalviews.setText(list1.get(0).get("totalviews").toString());
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
      rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("websitedata", _response);
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    devicegone.setVisibility(View.GONE);
                    recyclerView4.setAdapter(new Recyclerview4Adapter(list2));
                    recyclerView4.setLayoutManager(new LinearLayoutManager(site.this));
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

        rq5 = new RequestNetwork(this);
        _rq5_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("websitedata", _response);
                    list5 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    pagesgone.setVisibility(View.GONE);
                    recyclerView5.setAdapter(new Recyclerview5Adapter(list5));
                    recyclerView5.setLayoutManager(new LinearLayoutManager(site.this));
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
        rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setProgressBar(findViewById(R.id.progress_bar));

                Cartesian cartesian = AnyChart.line();

                cartesian.animation(true);

                cartesian.padding(10d, 20d, 5d, 20d);

                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                cartesian.yAxis(0).title("Number of Visits");
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                List<DataEntry> seriesData = new ArrayList<>();
                try{
                    Log.d("websitedata", _response);
                    list3 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

                    for (int i = 0; i < list3.size(); i++) {
                        seriesData.add(new CustomDataEntry(list3.get(i).get("date_only").toString(), Integer.parseInt(list3.get(i).get("count_per_date").toString()), 2.3, 2.8));
                    }


                    hud1.dismiss();
                    }catch(Exception e){
                    LocalDate today = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        today = LocalDate.now();
                    }
                    seriesData.add(new CustomDataEntry(today.toString(), 0, 2.3, 2.8));

                    hud1.dismiss();
                    e.printStackTrace();
                    Log.d("Error Response", _response);
                }
                Set set = Set.instantiate();
                set.data(seriesData);
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");


                Line series1 = cartesian.line(series1Mapping);
                series1.name("Visits");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);


                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                anyChartView.setChart(cartesian);
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

    }
    private void setRoundedCorners(View linearLayout, String color, String scolor) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

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
    }


private class CustomDataEntry extends ValueDataEntry {

    CustomDataEntry(String x, Number value, Number value2, Number value3) {
        super(x, value);
        setValue("value2", value2);
        setValue("value3", value3);
    }

}


    public class Recyclerview1Adapter extends RecyclerView.Adapter<site.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.websites, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview1Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
            final ImageView sendto = _view.findViewById(R.id.sendto);
            final ImageView menubar = _view.findViewById(R.id.menubar);
            final ImageView type = _view.findViewById(R.id.type);
            final LinearLayout goneline = _view.findViewById(R.id.goneline);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview2.setText(_data.get((int)_position).get("host").toString());
            menubar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (goneline.getVisibility() == View.VISIBLE) {
                        goneline.setVisibility(View.GONE);
                    } else {
                        goneline.setVisibility(View.VISIBLE);
                    }
                }
            });
            type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new iOSDialogBuilder(site.this)
                            .setTitle("Install pixel")
                            .setSubtitle("Copy and paste the following JS Code Snippet before the end of the </head> of your website.\n")
                            .setBoldPositiveLabel(true)
                            .setCancelable(true)
                            .setPositiveListener("Copy Code", new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                                    String textToCopy = "<!-- Pixel Code for https://siteanalytics.sabnode.com/ -->\n" +
                                            "<script defer src=\"https://siteanalytics.sabnode.com/pixel/"+_data.get((int)_position).get("pixel_key").toString() +"\"></script>\n" +
                                            "<!-- END Pixel Code -->";
                                        copyToClipboard(textToCopy);

                                }
                            })

                            .build().show();

                }
            });
            sendto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(site.this, addsite.class);
                    intent.putExtra("id", _data.get((int)_position).get("website_id").toString());
                    intent.putExtra("type", "edit");
                    intent.putExtra("name", _data.get((int)_position).get("name").toString());
                    intent.putExtra("host", _data.get((int)_position).get("host").toString());
                    intent.putExtra("tracking_type", _data.get((int)_position).get("tracking_type").toString());
                    startActivity(intent);
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
    public class Recyclerview2Adapter extends RecyclerView.Adapter<site.Recyclerview2Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.selectsite, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview2Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview2Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
            final LinearLayout mainline = _view.findViewById(R.id.mainline);
            textview1.setText(_data.get((int)_position).get("name").toString());
            textview2.setText(_data.get((int)_position).get("host").toString());
            mainline.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    websiteselect(_data.get((int)_position).get("host").toString(), _data.get((int)_position).get("website_id").toString(), _position);
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
    public class Recyclerview3Adapter extends RecyclerView.Adapter<site.Recyclerview3Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.moredata, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview3Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview3Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
final ImageView flag = _view.findViewById(R.id.flag);
textview1.setText(_data.get((int)_position).get("country_code").toString());
            textview2.setText(_data.get((int)_position).get("total").toString());
            final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
           flag.setImageResource(flags);
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
    public class Recyclerview4Adapter extends RecyclerView.Adapter<site.Recyclerview4Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview4Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.moredata, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview4Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview4Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
final ImageView flag = _view.findViewById(R.id.flag);
textview1.setText(_data.get((int)_position).get("device_type").toString());
            textview2.setText(_data.get((int)_position).get("total").toString());
            /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
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
    public class Recyclerview5Adapter extends RecyclerView.Adapter<site.Recyclerview5Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview5Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview5Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.moredata, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview5Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview5Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.cdis);
final ImageView flag = _view.findViewById(R.id.flag);
textview1.setText(_data.get((int)_position).get("path").toString());
            textview2.setText(_data.get((int)_position).get("total").toString());
            /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
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
    public class Recyclerview6Adapter extends RecyclerView.Adapter<site.Recyclerview6Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview6Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview6Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.teamsdata, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview6Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview6Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview2 = _view.findViewById(R.id.visitors);
            final TextView textview3 = _view.findViewById(R.id.cdis);
final ImageView flag = _view.findViewById(R.id.visitic);
textview1.setText(_data.get((int)_position).get("name").toString());
            textview2.setText(_data.get((int)_position).get("websites_ids").toString().replace("]", "").replace("[", ""));
            textview3.setText(_data.get((int)_position).get("websites_ids").toString().replace("]", "").replace("[", "") + " Websites");
            flag.setColorFilter(getResources().getColor(R.color.purple_200), PorterDuff.Mode.MULTIPLY);
            /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
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
    public class Recyclerview8Adapter extends RecyclerView.Adapter<site.Recyclerview8Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview8Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public site.Recyclerview8Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.moredata, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new site.Recyclerview8Adapter.ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(site.Recyclerview8Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.cnname);
            final TextView textview3 = _view.findViewById(R.id.cdis);

textview1.setText(_data.get((int)_position).get("name").toString());
            textview3.setText(_data.get((int)_position).get("type").toString());

            /*final int flags= World.getFlagOf(_data.get((int)_position).get("country_code").toString());
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
    private void copyToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied Text", text);
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this, "Code has been copied to the clipboard", Toast.LENGTH_SHORT).show();
    }

    private void websiteselect(String name, String id, Integer pos) {
        goneline.setVisibility(View.GONE);
        goneline1.setVisibility(View.GONE);
        moreic.setRotation(90);
        moreic1.setRotation(90);
        typetxt.setText(name);
        hud1 = KProgressHUD.create(site.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap1.put("id", id);
        rq1.setParams(rqmap1, 0);
        rq1.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"websiteinfo2.php","", _rq1_request_listener);
        rqmap2.put("id", id);
        rq2.setParams(rqmap2, 0);
        rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"getdevices.php","", _rq2_request_listener);
 rqmap3.put("id", id);
        rq3.setParams(rqmap3, 0);
        rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"chart.php","", _rq3_request_listener);
rqmap5.put("id", id);
        rq5.setParams(rqmap5, 0);
        rq5.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"pages.php","", _rq5_request_listener);
        rqmap8.put("id", id);
        rq8.setParams(rqmap8, 0);
        rq8.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"goals.php","", _rq8_request_listener);

    }
}
