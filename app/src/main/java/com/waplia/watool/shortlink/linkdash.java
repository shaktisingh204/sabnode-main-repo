package com.waplia.watool.shortlink;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.ui.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class linkdash extends AppCompatActivity {
    private TextView tabhead;
    private TextView shorten, links, clicksc;
    private LinearLayout nameback, line1, line2, copynewurl;
    private ImageView homeic, goalic, realic, teamic;
    private LinearLayout  realtime, teams, goals;
    private LinearLayout dashb, goalsb, realb, teamsb;
    private TextView dashtxt, goaltxt, realtxt, teamtxt;
    private SharedPreferences sp;
    private RequestNetwork rq;

    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq6;

    private RequestNetwork.RequestListener _rq6_request_listener;
    private ArrayList<HashMap<String, Object>> list7 = new ArrayList<>();
    private HashMap<String, Object> rqmap6 = new HashMap<>();
 private RequestNetwork rq5;

    private RequestNetwork.RequestListener _rq5_request_listener;
    private ArrayList<HashMap<String, Object>> list6 = new ArrayList<>();
    private HashMap<String, Object> rqmap5 = new HashMap<>();

    private RequestNetwork rq4;

    private RequestNetwork.RequestListener _rq4_request_listener;
    private ArrayList<HashMap<String, Object>> list4 = new ArrayList<>();
    private HashMap<String, Object> rqmap4 = new HashMap<>();
 private RequestNetwork rq2;

    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
 private RequestNetwork rq1;

    private RequestNetwork.RequestListener _rq1_request_listener;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list5 = new ArrayList<>();
    private HashMap<String, Object> rqmap1 = new HashMap<>();
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private KProgressHUD hud;
    private ImageView faviconImageView, osIconImageView, copy;
    private TextView titleTextView;
    private RecyclerView recyclerView, recyclerview2, recyclerview3;

    private EditText longurl, newurl;
    private KProgressHUD mProgressHUD;
    private ScrollView tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;
    private LinearLayout tab4;
    private CardView contactadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkdash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabhead = findViewById(R.id.titletxt);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        recyclerview2 = findViewById(R.id.recyclerview2);
        recyclerview3 = findViewById(R.id.recyclerview3);
        shorten = findViewById(R.id.shorten);
        nameback = findViewById(R.id.nameback);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        copynewurl = findViewById(R.id.copynewurl);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        setRoundedCorners(nameback, "00000000", "dadcdf");
        setRoundedCorners(copynewurl, "00000000", "dadcdf");
        setRoundedCorners(shorten, "4A148C", "4A148C");
        setRoundedCorners(line1, "EDE7F6", "EDE7F6");
        setRoundedCorners(line2, "E8EAF6", "E8EAF6");
        homeic = findViewById(R.id.homeic);
        goalic = findViewById(R.id.goalic);
        realic = findViewById(R.id.realic);
        teamic = findViewById(R.id.teamic);
        homeic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
        goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
        realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
        teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
        dashb = findViewById(R.id.dashb);
        goalsb = findViewById(R.id.goleb);
        realb = findViewById(R.id.realb);
        teamsb = findViewById(R.id.teamsb);
        dashtxt = findViewById(R.id.dashtxt);
        goaltxt = findViewById(R.id.goaltxt);
        realtxt = findViewById(R.id.realtxt);
        teamtxt = findViewById(R.id.teamtxt);
        links = findViewById(R.id.linksc);
        clicksc = findViewById(R.id.clicksc);
        recyclerView = findViewById(R.id.recyclerview1);
        longurl = findViewById(R.id.longurl);
        newurl = findViewById(R.id.newurl);
        tab4 = findViewById(R.id.tab4);
        World.init(getApplicationContext()); // Initializes the libray and loads all data
        shorten = findViewById(R.id.shorten);
        copy = findViewById(R.id.copy);
        contactadd = findViewById(R.id.contactadd);

        contactadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(linkdash.this, newqr.class);
                startActivity(intent);
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(newurl.getText().toString());
            }
        });
        shorten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 mProgressHUD = KProgressHUD.create(linkdash.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(false)
                        .show();
                // Trigger the AsyncTask to make the network request
                new NetworkRequestTask().execute();
            }
        });
        realb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyClickEffect(realb);
                tab1.setVisibility(View.GONE);
                tab2.setVisibility(View.VISIBLE);
                tab3.setVisibility(View.GONE);
                tab4.setVisibility(View.GONE);
                tabhead.setText("QR Codes");
                homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                realic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
                teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_ldash));
                goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lbio));
                realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lqrbold));
                teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lstats));
                dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                realtxt.setTextColor(getResources().getColor(R.color.purple_500));
                teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
/*
                websiteselect(list.get(0).get("host").toString(), list.get((int)0).get("website_id").toString(), 0);
*/
            }
        });
        dashb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyClickEffect(dashb);
                tab1.setVisibility(View.VISIBLE);
                tab2.setVisibility(View.GONE);
                tab3.setVisibility(View.GONE);
                tab4.setVisibility(View.GONE);
                tabhead.setText("Short Link");
                realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                homeic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
                teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_ldashbold));
                goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lbio));
                realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lqr));
                teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lstats));
                dashtxt.setTextColor(getResources().getColor(R.color.purple_500));
                goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
            }
        });
        teamsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyClickEffect(teamsb);
                tab1.setVisibility(View.GONE);
                tab2.setVisibility(View.GONE);
                tab4.setVisibility(View.VISIBLE);
                tab3.setVisibility(View.GONE);
                tabhead.setText("Statistics");
                realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                goalic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                teamic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
                homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_ldash));
                goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lbio));
                realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lqr));
                teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lstatsbold));
                teamtxt.setTextColor(getResources().getColor(R.color.purple_500));
                goaltxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));

            }
        });
        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyClickEffect(line1);
                Intent intent = new Intent(getApplicationContext(), alllinks.class);
                intent.putExtra("id", list.get(0).get("id").toString());
                startActivity(intent);
            }
        });
        goalsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyClickEffect(goalsb);
                tab1.setVisibility(View.GONE);
                tab2.setVisibility(View.GONE);
                tab4.setVisibility(View.GONE);
                tab3.setVisibility(View.VISIBLE);
                tabhead.setText("Bio Pages");
                realic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                teamic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                goalic.setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
                homeic.setColorFilter(getResources().getColor(R.color.md_purple_100), PorterDuff.Mode.MULTIPLY);
                homeic.setImageDrawable(getResources().getDrawable(R.drawable.ic_ldash));
                goalic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lbiobold));
                realic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lqr));
                teamic.setImageDrawable(getResources().getDrawable(R.drawable.ic_lstats));
                goaltxt.setTextColor(getResources().getColor(R.color.purple_500));
                teamtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                realtxt.setTextColor(getResources().getColor(R.color.md_purple_100));
                dashtxt.setTextColor(getResources().getColor(R.color.md_purple_100));

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

                    Log.d("linkdashdata", _response);
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    links.setText(list.get(0).get("links").toString());
                    clicksc.setText(list.get(0).get("clicks").toString());
                    rqmap1.put("id", list.get(0).get("id").toString());
                    rq1.setParams(rqmap1, 0);
                    rq1.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/getclicks.php","", _rq1_request_listener);
                    rq2.setParams(rqmap1, 0);
                    rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/qrcodes.php","", _rq2_request_listener);
                 rq4.setParams(rqmap1, 0);
                    rq4.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/biopages.php","", _rq4_request_listener);
              rq5.setParams(rqmap1, 0);
                    rq5.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/linksstats.php","", _rq5_request_listener);
               rq6.setParams(rqmap1, 0);
                    rq6.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/clicksstats.php","", _rq6_request_listener);
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

        rq4 = new RequestNetwork(this);
        _rq4_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{

                    Log.d("linkdashdata", _response);
                    list4 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerview3.setAdapter(new Recyclerview3Adapter(list4));
                    recyclerview3.setLayoutManager(new LinearLayoutManager(linkdash.this));

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

                    AnyChartView anyChartView1 = findViewById(R.id.any_chart_view);
                    anyChartView1.setProgressBar(findViewById(R.id.progress_bar));

                    Cartesian cartesian1 = AnyChart.line();

                    cartesian1.animation(true);

                    cartesian1.padding(10d, 20d, 5d, 20d);

                    cartesian1.crosshair().enabled(true);
                    cartesian1.crosshair()
                            .yLabel(true)
                            // TODO ystroke
                            .yStroke((Stroke) null, null, null, (String) null, (String) null);

                    cartesian1.tooltip().positionMode(TooltipPositionMode.POINT);

                    cartesian1.yAxis(0).title("Number of Visits");
                    cartesian1.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                    List<DataEntry> seriesData = new ArrayList<>();
                    try{
                        Log.d("websitedata", _response);
                        list6 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                        for (int i = 0; i < list6.size(); i++) {
                            seriesData.add(new linkdash.CustomDataEntry("0",0, 2.3, 2.8));
                        }
                    }catch(Exception e){
                        LocalDate today = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            today = LocalDate.now();
                        }
                        seriesData.add(new linkdash.CustomDataEntry(today.toString(), 0, 2.3, 2.8));
                        e.printStackTrace();
                        Log.d("Error Response", _response);
                    }
                    Set set = Set.instantiate();
                    set.data(seriesData);
                    Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
                    Line series1 = cartesian1.line(series1Mapping);
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
                    cartesian1.legend().enabled(true);
                    cartesian1.legend().fontSize(13d);
                    cartesian1.legend().padding(0d, 0d, 10d, 0d);

                    anyChartView1.setChart(cartesian1);
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
    rq6 = new RequestNetwork(this);
        _rq6_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    AnyChartView anyChartView = findViewById(R.id.any_chart_view1);
                    anyChartView.setProgressBar(findViewById(R.id.progress_bar1));

                    Cartesian cartesian = AnyChart.line();

                    cartesian.animation(true);

                    cartesian.padding(10d, 20d, 5d, 20d);

                    cartesian.crosshair().enabled(true);
                    cartesian.crosshair()
                            .yLabel(true)
                            // TODO ystroke
                            .yStroke((Stroke) null, null, null, (String) null, (String) null);

                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                    cartesian.yAxis(0).title("Number of Clicks");
                    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                    List<DataEntry> seriesData1 = new ArrayList<>();
                    try{
                        Log.d("websitedata", _response);
                        list7 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

                        for (int i = 0; i < list7.size(); i++) {
                            seriesData1.add(new linkdash.CustomDataEntry(list7.get(i).get("day").toString(), Integer.parseInt(list7.get(i).get("clicks_count").toString()), 2.3, 2.8));
                        }



                    }catch(Exception e){
                        LocalDate today = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            today = LocalDate.now();
                        }
                        seriesData1.add(new linkdash.CustomDataEntry(today.toString(), 0, 2.3, 2.8));


                        e.printStackTrace();
                        Log.d("Error Response", _response);
                    }
                    Set set1 = Set.instantiate();
                    set1.data(seriesData1);
                    Mapping series1Mapping1 = set1.mapAs("{ x: 'x', value: 'value' }");


                    Line series11 = cartesian.line(series1Mapping1);
                    series11.name("Clicks");
                    series11.hovered().markers().enabled(true);
                    series11.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    series11.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d);


                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13d);
                    cartesian.legend().padding(0d, 0d, 10d, 0d);
                    anyChartView.setChart(cartesian);
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
                    Log.d("linkdashdata11", _response);
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerview2.setAdapter(new Recyclerview2Adapter(list2));
                    recyclerview2.setLayoutManager(new LinearLayoutManager(linkdash.this));
                    int numberOfColumns = 2;
                   /* GridLayoutManager layoutManager = new GridLayoutManager(linkdash.this, numberOfColumns);*/
                    /*recyclerview2.setLayoutManager(layoutManager);*/

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
        hud = KProgressHUD.create(linkdash.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();

        rqmap.put("id", sp.getString("email", ""));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/linkdash.php","", _rq_request_listener);
        rq1 = new RequestNetwork(this);
        _rq1_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud.dismiss();
                    Log.d("linkdashdata1", _response);
                    list1 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    recyclerView.setAdapter(new Recyclerview1Adapter(list1));
                    recyclerView.setLayoutManager(new LinearLayoutManager(linkdash.this));
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


    }

    private void copyToClipboard(String string) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", string);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
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
        linearLayout.setElevation(5);
    }
private void showdata(String url) {
    list5 = new Gson().fromJson("[" + url + "]",  new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
    nameback.setVisibility(View.GONE);
    copynewurl.setVisibility(View.VISIBLE);
    newurl.setText(list5.get(0).get("shorturl").toString());
    rqmap.put("id", sp.getString("email", ""));
    rq.setParams(rqmap, 0);
    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"linkshort/linkdash.php","", _rq_request_listener);

}
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

    private void setOsIcon(String infoString) {
        String osName = detectOSName(infoString);
        int drawableId = getDrawableIdForOs(osName);
        if (drawableId != 0) {
            osIconImageView.setImageResource(drawableId);
        }
    }

    private String detectOSName(String infoString) {
        // This is a simplified example, and you might need to enhance this logic based on your actual data
        if (infoString.toLowerCase().contains("android")) {
            return "android";
        } else if (infoString.toLowerCase().contains("iPhone")) {
            return "ios";
        } else if (infoString.toLowerCase().contains("windows")) {
            return "windows";
        } else if (infoString.toLowerCase().contains("linux")) {
            return "linux";
        } else if (infoString.toLowerCase().contains("macos")) {
            return "macos";
        } else {
            return "unknown";
        }
    }


    private int getDrawableIdForOs(String osName) {
        switch (osName.toLowerCase()) {
            case "android":
                return R.drawable.android;
            case "ios":
                return R.drawable.mac;
            case "windows":
                return R.drawable.windows;
            case "linux":
                return R.drawable.linux;
            case "macos":
                return R.drawable.mac;
            // Add more cases for other operating systems as needed
            default:
                return 0;  // Return 0 for unknown OS (you can handle this case accordingly)
        }
    }
    private void applyClickEffect(LinearLayout menu) {
        Animation clickEffect = AnimationUtils.loadAnimation(this, R.anim.bottom_nav_item_click);
        menu.startAnimation(clickEffect);
    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<linkdash.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public linkdash.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.statslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new linkdash.Recyclerview1Adapter.ViewHolder(_v);

        }
        @Override
        public void onBindViewHolder(linkdash.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final ImageView faviconImageView = _view.findViewById(R.id.fav);
            final TextView titleTextView = _view.findViewById(R.id.title);
            osIconImageView = _view.findViewById(R.id.os);
             final TextView osname = _view.findViewById(R.id.osname);
             final TextView location = _view.findViewById(R.id.location);
             final TextView url = _view.findViewById(R.id.url);
             final ImageView flag = _view.findViewById(R.id.flag);
             url.setText("https://linkshort.sabnode.com/"+_data.get((int)_position).get("short").toString());
            final int flags= World.getFlagOf(_data.get((int)_position).get("country").toString());
            location.setText(_data.get((int)_position).get("city").toString()+","+_data.get((int)_position).get("country").toString());
            flag.setImageResource(flags);
            String websiteUrl = _data.get((int)_position).get("longurl").toString(); // Replace with the actual website URL
            try {
                titleTextView.setText(fetchWebsiteTitle(websiteUrl));
                Glide.with(linkdash.this).load(getFaviconUrl(websiteUrl))
                        .placeholder(R.drawable.logos)
                        .into(faviconImageView);
            }catch (Exception e){
                Log.d("Error", e.toString());
            }
            String osName = _data.get((int)_position).get("os").toString();
            osname.setText(osName);
            setOsIcon(osName);
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
    public class Recyclerview2Adapter extends RecyclerView.Adapter<linkdash.Recyclerview2Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public linkdash.Recyclerview2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.qrslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new linkdash.Recyclerview2Adapter.ViewHolder(_v);

        }
        @Override
        public void onBindViewHolder(linkdash.Recyclerview2Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
            final ImageView qrcodeimg = _view.findViewById(R.id.qrcodeimg);
            final TextView qrtext = _view.findViewById(R.id.qrtext);
            osIconImageView = _view.findViewById(R.id.os);
            final LinearLayout qrslayout = _view.findViewById(R.id.qrslayout);
             final TextView osname = _view.findViewById(R.id.osname);
             final TextView location = _view.findViewById(R.id.location);
             final TextView url = _view.findViewById(R.id.url);
             final ImageView flag = _view.findViewById(R.id.flag);
             qrtext.setText(_data.get(_position).get("name").toString());
            qrslayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applyClickEffect(qrslayout);
                    String link = "https://linkshort.sabnode.com/qr/"+_data.get(_position).get("alias").toString();
                    Uri uri = Uri.parse(link);
                    openCustomTab(uri);
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
    private void openCustomTab(Uri uri) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, uri);
    }

    public class Recyclerview3Adapter extends RecyclerView.Adapter<linkdash.Recyclerview3Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public linkdash.Recyclerview3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.biopageslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new linkdash.Recyclerview3Adapter.ViewHolder(_v);

        }
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(linkdash.Recyclerview3Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
           final LinearLayout line2 = _view.findViewById(R.id.line2);
           final TextView name = _view.findViewById(R.id.name);
           final TextView url = _view.findViewById(R.id.url);
           final TextView clicks = _view.findViewById(R.id.clicks);
           final TextView date = _view.findViewById(R.id.time);
            setRoundedCorners(line2, "00000000", "dadcdf");
            name.setText(_data.get(_position).get("name").toString());
            url.setText("https://linkshort.sabnode.com/"+_data.get(_position).get("alias").toString());
            clicks.setText(_data.get(_position).get("clicks").toString()+" clicks");
            // Get the date from MySQL (replace this with your actual date)
            String mysqlDateString = _data.get(_position).get("created_at").toString();
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }
            LocalDateTime mysqlDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mysqlDate = LocalDateTime.parse(mysqlDateString, formatter);
            }

            // Get the current date and time
            LocalDateTime currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDate = LocalDateTime.now();
            }

            // Calculate the time difference
            Duration duration = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                duration = Duration.between(mysqlDate, currentDate);
            }

            // Determine whether the difference is in hours or days
            long hours = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hours = duration.toHours();
            }
            long days = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                days = duration.toDays();
            }

            String result;
            if (days > 0) {
                result = days + (days == 1 ? " day" : " days") + " ago";
            } else {
                result = hours + (hours == 1 ? " hour" : " hours") + " ago";
            }

    date.setText(result);
            /* qrtext.setText(_data.get(_position).get("name").toString());
             Glide.with(linkdash.this).load("https://linkshort.sabnode.com/qr/"+_data.get(_position).get("alias").toString()).into(qrcodeimg);
           //"https://linkshort.sabnode.com/content/qr/"+_data.get(_position).get("filename").toString()
   */     }
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
    private InputStream getInputStreamFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            return urlConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getFaviconUrl(String websiteUrl) {
        // Construct the URL for the favicon (assuming it is located at the standard path)
        return websiteUrl + "/favicon.ico";
    }
    private String fetchWebsiteTitle(String websiteUrl) {
        try {
            // Fetch the HTML content of the website
            Document document = Jsoup.connect(websiteUrl).get();
            // Extract the title from the HTML document
            return document.title();
        } catch (IOException e) {
            return websiteUrl;
        }
    }
    private boolean isValidURL(String url) {
        // Implement your URL validation logic here
        // Return true if the URL is valid, false otherwise
        return true; // Replace with your validation logic
    }

    private class NetworkRequestTask extends AsyncTask<Void, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                MediaType mediaType = MediaType.parse("application/json");
                Map<String, String> rqmap3 = new HashMap<>();
                rqmap3.put("url", longurl.getText().toString());

                Gson gson = new Gson();
                RequestBody body = RequestBody.create(mediaType, gson.toJson(rqmap3));

                Request request = new Request.Builder()
                        .url("https://linkshort.sabnode.com/api/url/add")
                        .method("POST", body)
                        .addHeader("Authorization", "Bearer ehPLPJqzatBUxmMP")
                        .addHeader("Content-Type", "application/json")
                        .build();

                // Check if the URL is valid before making the network request
                String urlToValidate = longurl.getText().toString();
                if (isValidURL(urlToValidate)) {
                    return executeNetworkRequest(client, request);
                } else {
                    // Return an indication that the URL is invalid
                    return new NetworkResponse(false, "Invalid URL");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new NetworkResponse(false, "Error");
            }
        }

        @Override
        protected void onPostExecute(NetworkResponse result) {
            if (result != null) {
                // Handle the result based on the returned NetworkResponse object
                if (result.isSuccess()) {
                    mProgressHUD.dismiss();
                    showdata(result.getResponse());
                  //  Toast.makeText(linkdash.this, "Request successful: " + result.getResponse(), Toast.LENGTH_SHORT).show();
                } else {
                    mProgressHUD.dismiss();
                    Toast.makeText(linkdash.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private NetworkResponse executeNetworkRequest(OkHttpClient client, Request request) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Convert the response body to a string
                    String responseBody = response.body().string();
                    return new NetworkResponse(true, responseBody);
                } else {
                    return new NetworkResponse(false, "Request failed: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new NetworkResponse(false, "Error");
            }
        }
    }

    private class NetworkResponse {
        private boolean success;
        private String response;

        public NetworkResponse(boolean success, String response) {
            this.success = success;
            this.response = response;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getResponse() {
            return response;
        }
    }
}