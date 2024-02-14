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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.waplia.watool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    private LinearLayout phoneback, messageback, pixels, tempurl, password, targate, advanced;
    private ImageView linkic, pixelic, tempic, passwordic, targateic, advancedic;
    ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();

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
        linkic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        pixelic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        tempic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        passwordic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        targateic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        advancedic.setColorFilter(Color.parseColor("#3E4775"), PorterDuff.Mode.MULTIPLY);
        setRoundedCorners(phoneback, "00000000", "dadcdf");
        setRoundedCorners(messageback, "00000000", "dadcdf");
        setRoundedCorners(pixels, "00000000", "dadcdf");
        setRoundedCorners(tempurl, "00000000", "dadcdf");
        setRoundedCorners(password, "00000000", "dadcdf");
        setRoundedCorners(targate, "00000000", "dadcdf");
        setRoundedCorners(advanced, "00000000", "dadcdf");
        new bshortlinkedit.NetworkRequestTask().execute();

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

                // Check if the URL is valid before making the network request
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
                        map.put("start_date", dataObject.isNull("start_date") ? null : dataObject.getString("start_date"));
                        map.put("end_date", dataObject.isNull("end_date") ? null : dataObject.getString("end_date"));
                        map.put("is_verified", dataObject.getInt("is_verified"));
                        map.put("is_enabled", dataObject.getInt("is_enabled"));
                        map.put("last_datetime", dataObject.isNull("last_datetime") ? null : dataObject.getString("last_datetime"));
                        map.put("datetime", dataObject.getString("datetime"));

                        // Get the "settings" JSON object and add its key-value pairs to the HashMap
                        JSONObject settingsObject = dataObject.getJSONObject("settings");
                        map.put("clicks_limit", settingsObject.isNull("clicks_limit") ? null : settingsObject.getInt("clicks_limit"));
                        map.put("expiration_url", settingsObject.getString("expiration_url"));
                        map.put("password", settingsObject.isNull("password") ? null : settingsObject.getString("password"));
                        map.put("sensitive_content", settingsObject.getBoolean("sensitive_content"));
                        map.put("targeting_type", settingsObject.getString("targeting_type"));
                        // Get the "pixels_ids" JSON array and convert it to a List
                        JSONArray pixelsIdsArray = dataObject.getJSONArray("pixels_ids");
                        List<Integer> pixelsIdsList = new ArrayList<>();
                        for (int i = 0; i < pixelsIdsArray.length(); i++) {
                            pixelsIdsList.add(pixelsIdsArray.getInt(i));
                        }
                        map.put("pixels_ids", pixelsIdsList);

                        // Add the HashMap to the ArrayList
                        ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
                        list1.add(map);

                        // Print the result
                        System.out.println(list1);
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

}
