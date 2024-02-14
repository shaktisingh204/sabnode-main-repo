package com.waplia.watool.biolink;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.waplia.watool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class bshortlinkedit extends AppCompatActivity  {
    private LinearLayout phoneback, messageback, pixels, tempurl, password, targate, advanced,targateclick,
            clicksback, startback, endback, tempurlline, expireback, tempurls, passwordback, protectionline, protections,
            typeback, targating, advanceback, advancedsettings, advanceclicks;
    private ImageView linkic, pixelic, tempic, passwordic, targateic, advancedic, chooseic, chooseica;
    private TextView text, update;
    private EditText messagetext, phonetext, start, end, clicks, expire, passwordtext;
    ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private Switch scheduleswitch, senseswitch;
    private LinearLayout scheduleline;
    private  String schedule, sense, starts, stops ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bshortlinkedit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tempurlline = findViewById(R.id.tempurlline);
        phoneback = findViewById(R.id.phonesback);
        messageback = findViewById(R.id.messageback);
        tempurl = findViewById(R.id.tempurl);
        password = findViewById(R.id.password);
        targate = findViewById(R.id.targate);
        advanced = findViewById(R.id.advanced);
        pixels = findViewById(R.id.pixels);
        linkic = findViewById(R.id.linkic);
        pixelic = findViewById(R.id.pixelic);
        tempic = findViewById(R.id.tempic);
        passwordic = findViewById(R.id.passwordic);
        targateic = findViewById(R.id.targateic);
        advancedic = findViewById(R.id.advancedic);
        text = findViewById(R.id.text);
        messagetext = findViewById(R.id.messagetext);
        phonetext = findViewById(R.id.phonetext);
        clicksback = findViewById(R.id.clicksback);
        startback = findViewById(R.id.startback);
        endback = findViewById(R.id.endback);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        expireback = findViewById(R.id.expireback);
        scheduleswitch = findViewById(R.id.scheduleswitch);
        scheduleline = findViewById(R.id.scheduleline);
        passwordback = findViewById(R.id.passwordback);
        protectionline = findViewById(R.id.protectionline);
        protections = findViewById(R.id.protections);
        targateclick = findViewById(R.id.targateclick);
        typeback = findViewById(R.id.typeback);
        targating = findViewById(R.id.targating);
        chooseic = findViewById(R.id.chooseic);
        advanceback = findViewById(R.id.advanceback);
        advancedsettings = findViewById(R.id.advancedsettings);
        advanceclicks = findViewById(R.id.advanceclicks);
        chooseica = findViewById(R.id.chooseica);
        clicks = findViewById(R.id.clicks);
        expire = findViewById(R.id.expire);
        update = findViewById(R.id.update);
        senseswitch = findViewById(R.id.senseswitch);
        passwordtext = findViewById(R.id.passwordtext);
        linkic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        chooseic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        pixelic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        tempic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        passwordic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        chooseica.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        targateic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        advancedic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        setRoundedCorners(phoneback, "00000000", "dadcdf");
        setRoundedCorners(messageback, "00000000", "dadcdf");
        setRoundedCorners(pixels, "00000000", "dadcdf");
        setRoundedCorners(update, "6200EA", "dadcdf");
        setRoundedCorners(tempurl, "00000000", "dadcdf");
        setRoundedCorners(password, "00000000", "dadcdf");
        setRoundedCorners(targate, "00000000", "dadcdf");
        setRoundedCorners(advanced, "00000000", "dadcdf");
        setRoundedCorners(clicksback, "00000000", "dadcdf");
        setRoundedCorners(startback, "00000000", "dadcdf");
        setRoundedCorners(endback, "00000000", "dadcdf");
        setRoundedCorners(expireback, "00000000", "dadcdf");
        setRoundedCorners(passwordback, "00000000", "dadcdf");
        setRoundedCorners(typeback, "00000000", "dadcdf");
        setRoundedCorners(advanceback, "00000000", "dadcdf");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Get the current date and time
        Date currentDate = new Date();

        // Format the current date and time using the SimpleDateFormat
        String formattedDateTime = dateFormat.format(currentDate);
        start.setText(formattedDateTime);
        end.setText(formattedDateTime);
        new bshortlinkedit.NetworkRequestTask().execute();
        tempurls = findViewById(R.id.tempurls);
        tempurls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempurlline.getVisibility() == View.VISIBLE) {
                    tempurlline.setVisibility(View.GONE);

                } else {
                    tempurlline.setVisibility(View.VISIBLE);

                }
            }
        });
        protections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (protectionline.getVisibility() == View.VISIBLE) {
                    protectionline.setVisibility(View.GONE);
                } else {
                    protectionline.setVisibility(View.VISIBLE);
                }
            }
        });
        targateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targating.getVisibility() == View.VISIBLE) {
                    targating.setVisibility(View.GONE);
                } else {
                    targating.setVisibility(View.VISIBLE);
                }
            }
        });
        advanceclicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advancedsettings.getVisibility() == View.VISIBLE) {
                    advancedsettings.setVisibility(View.GONE);
                } else {
                    advancedsettings.setVisibility(View.VISIBLE);
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new bshortlinkedit.NetworkRequestTask1().execute();

            }
        });
scheduleswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            scheduleline.setVisibility(View.VISIBLE);
        } else {
            scheduleline.setVisibility(View.GONE);
        }
    }
});
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
    private boolean isValidURL(String url) {
        // Implement your URL validation logic here
        // Return true if the URL is valid, false otherwise
        return true; // Replace with your validation logic
    }
    private class NetworkRequestTask extends AsyncTask<Void, Void, bshortlinkedit.NetworkResponse> {

        @Override
        protected bshortlinkedit.NetworkResponse doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                Map<String, String> rqmap3 = new HashMap<>();
                Log.d("idwsw", getIntent().getExtras().getString("id"));
                Request request = new Request.Builder()
                        .url("https://biolink.sabnode.com/api/links/"+getIntent().getExtras().getString("id"))
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer dbb0fc75ba9f33be66e69a408f609838")
                        .addHeader("Content-Type", "application/json")
                        .build();
                    return executeNetworkRequest(client, request);
            } catch (Exception e) {
                e.printStackTrace();
                return new bshortlinkedit.NetworkResponse(false, "Error");
            }
        }

        @Override
        protected void onPostExecute(bshortlinkedit.NetworkResponse result) {
            if (result != null) {
                Log.d("Response", result.getResponse());
                // Handle the result based on the returned NetworkResponse object
                if (result.isSuccess()) {

                    // Parse JSON string
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        // Create HashMap to store key-value pairs including nested objects
                        HashMap<String, Object> map = new HashMap<>();

                        // Populate the HashMap
                        map.put("id", dataObject.getInt("id"));
                        map.put("project_id", dataObject.getInt("project_id"));
                        map.put("domain_id", dataObject.getInt("domain_id"));
                        map.put("type", dataObject.getString("type"));
                        map.put("url", dataObject.getString("url"));
                        map.put("location_url", dataObject.getString("location_url"));
                        map.put("clicks", dataObject.getInt("clicks"));
                        if (dataObject.has("start_date") && !dataObject.isNull("start_date")) {
                            map.put("start_date", dataObject.getString("start_date"));
                        } else {
                            map.put("start_date", " ");
                        }
                        if (dataObject.has("end_date") && !dataObject.isNull("end_date")) {
                            map.put("end_date", dataObject.getString("end_date"));
                        } else {
                            map.put("end_date", " ");
                        }


                          if (dataObject.has("last_datetime") && !dataObject.isNull("last_datetime")) {
                            map.put("last_datetime", dataObject.getString("last_datetime"));
                        } else {
                            map.put("last_datetime", " ");
                        }

                        map.put("is_verified", dataObject.getInt("is_verified"));
                        map.put("is_enabled", dataObject.getInt("is_enabled"));
                        map.put("datetime", dataObject.getString("datetime"));

                        // Get the "settings" JSON object and add its key-value pairs to the HashMap
                        JSONObject settingsObject = dataObject.getJSONObject("settings");
                        if (settingsObject.has("expiration_url") && !settingsObject.isNull("expiration_url")) {
                            map.put("expiration_url", settingsObject.getString("expiration_url"));
                        } else {
                            map.put("expiration_url", " ");
                        }
                        if (settingsObject.has("clicks_limit") && !settingsObject.isNull("clicks_limit")) {
                            map.put("clicks_limit", settingsObject.getString("clicks_limit"));
                        } else {
                            map.put("clicks_limit", " ");
                        }
                        if (settingsObject.has("password") && !settingsObject.isNull("password")) {
                            map.put("password", settingsObject.getString("password"));
                        } else {
                            map.put("password", " ");
                        }
                        map.put("sensitive_content", settingsObject.getBoolean("sensitive_content"));
                        map.put("targeting_type", settingsObject.getString("targeting_type"));
                        // Get the "pixels_ids" JSON array and convert it to a List
                        try {
                        JSONArray pixelsIdsArray = dataObject.getJSONArray("pixels_ids");
                        List<Integer> pixelsIdsList = new ArrayList<>();
                        for (int i = 0; i < pixelsIdsArray.length(); i++) {
                            pixelsIdsList.add(pixelsIdsArray.getInt(i));
                        }
                        } catch (Exception e) {
                            map.put("pixels_ids", "0");
                        }
                        // Add the HashMap to the ArrayList
                        ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
                        list1.add(map);
                        System.out.println(list1);
                        text.setText(list1.get(0).get("url").toString());
                        phonetext.setText(list1.get(0).get("location_url").toString());
                        messagetext.setText(list1.get(0).get("url").toString());
                        start.setText(list1.get(0).get("start_date").toString());
                        end.setText(list1.get(0).get("end_date").toString());
                        clicks.setText(list1.get(0).get("clicks_limit").toString());
                        expire.setText(list1.get(0).get("expiration_url").toString());
                        passwordtext.setText(list1.get(0).get("password").toString());
                        if (!list1.get(0).get("start_date").toString().equals(" ")){
                            scheduleswitch.setChecked(true);
                        }else if (list1.get(0).get("start_date").toString().equals(" ")){
                            scheduleswitch.setChecked(false);
                        }
                        if (list1.get(0).get("sensitive_content").toString().equals("true")){
                           senseswitch.setChecked(true);
                        } else if (list1.get(0).get("sensitive_content").toString().equals("false")){} {
                            senseswitch.setChecked(false);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                       } else {

                    Toast.makeText(bshortlinkedit.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private bshortlinkedit.NetworkResponse executeNetworkRequest(OkHttpClient client, Request request) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Convert the response body to a string
                    String responseBody = response.body().string();
                    return new bshortlinkedit.NetworkResponse(true, responseBody);
                } else {
                    return new bshortlinkedit.NetworkResponse(false, "Request failed: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new bshortlinkedit.NetworkResponse(false, "Error");
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
 private class NetworkRequestTask1 extends AsyncTask<Void, Void, bshortlinkedit.NetworkResponse> {

        @Override
        protected bshortlinkedit.NetworkResponse doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                MediaType mediaType = MediaType.parse("application/json");
                Map<String, String> rqmap3 = new HashMap<>();
                Log.d("idwsw", getIntent().getExtras().getString("id"));

                if(scheduleswitch.isChecked()){
                    schedule = "true"; //schedule
                    starts = start.getText().toString();
                    stops = end.getText().toString();
                }else if(!scheduleswitch.isChecked()){
                    schedule = "false"; //schedule
                    starts = "null";
                    stops = "null";
                }
                if (senseswitch.isChecked()){
                    sense = "true";
                } else if (senseswitch.isChecked()){} {
                    sense = "false"; //sense

                }

                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("url",text.getText().toString())
                        .addFormDataPart("location_url",phonetext.getText().toString())
                        .addFormDataPart("schedule",schedule)
                        .addFormDataPart("start_date",starts)
                        .addFormDataPart("end_date",stops)
                        .addFormDataPart("clicks_limit",clicks.getText().toString())
                        .addFormDataPart("expiration_url",expire.getText().toString())
                        .addFormDataPart("sensitive_content", sense)
                        .addFormDataPart("password",passwordtext.getText().toString().replace(" ", ""))
                        .build();
                Request request = new Request.Builder()
                        .url("https://biolink.sabnode.com/api/links/"+getIntent().getExtras().getString("id"))
                        .method("POST", body)
                        .addHeader("Authorization", "Bearer dbb0fc75ba9f33be66e69a408f609838")
                        .addHeader("Content-Type", "application/json")
                        .build();
                    return executeNetworkRequest(client, request);
            } catch (Exception e) {
                e.printStackTrace();
                return new bshortlinkedit.NetworkResponse(false, "Error");
            }
        }

        @Override
        protected void onPostExecute(bshortlinkedit.NetworkResponse result) {
            if (result != null) {
                Log.d("Response", result.getResponse());
                // Handle the result based on the returned NetworkResponse object
                if (result.isSuccess()) {

                } else {

                    Toast.makeText(bshortlinkedit.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private bshortlinkedit.NetworkResponse executeNetworkRequest(OkHttpClient client, Request request) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Convert the response body to a string
                    String responseBody = response.body().string();
                    return new bshortlinkedit.NetworkResponse(true, responseBody);
                } else {
                    return new bshortlinkedit.NetworkResponse(false, "Request failed: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new bshortlinkedit.NetworkResponse(false, "Error");
            }
        }
    }

    private class NetworkResponse1 {
        private boolean success;
        private String response;

        public NetworkResponse1(boolean success, String response) {
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
