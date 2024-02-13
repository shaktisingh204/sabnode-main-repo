package com.waplia.watool.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbb20.CountryCodePicker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kevinschildhorn.otpview.OTPView;
import com.otpview.OTPTextView;
import com.waplia.watool.Main2Home;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;

import java.util.ArrayList;
import java.util.HashMap;

public class mobilelogin extends AppCompatActivity {
    private LinearLayout phoneback;
    private TextView signin;
    private EditText phone;
    private OTPView  otp_view;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private SharedPreferences sp;
    private Integer type = 0;
    private KProgressHUD hud2;
    private LinearLayout register, phoneline;
    private MaterialButton login;
    private OTPView otpView;
    private CountryCodePicker cpp;

    private String otp, lotp;
    private int intValue;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilelogin);
        phoneback = findViewById(R.id.phoneback);
        signin = findViewById(R.id.signin);
        setRoundedCorners(signin, "2962FF", "2962FF");
        setRoundedCorners(phoneback, "00000000", "f3f5f8");
        changeStatusBarColor();
        phone = findViewById(R.id.phone);
        phone.isFocused();
        otp_view = findViewById(R.id.otp_view);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    lotp = otp_view.getStringFromFields().toString();
                    Log.d("otpl", lotp);
                    Log.d("otp", intValue + "otp");

                    try {
                        int ilotp = Integer.valueOf(lotp);

                        if (intValue == ilotp) {
                            // If the entered OTP matches the expected OTP (lotp)
                            Toast.makeText(getApplicationContext(), "OTP Verified", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(mobilelogin.this, dashboard.class);
                            startActivity(i);
                        } else {
                            // If the entered OTP does not match the expected OTP
                            otpView.clearText(true); // Clear the entered OTP in otpView
                            Toast.makeText(getApplicationContext(), "Wrong OTP!", Toast.LENGTH_LONG).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the exception (e.g., show an error message, log, etc.)
                        e.printStackTrace();
                    }

                } else {
                    hud2 = KProgressHUD.create(mobilelogin.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rqmap.put("mobile_number", phone.getText().toString());
                    rq.setParams(rqmap, 0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl) + "otpsend.php", "", _rq_request_listener);
                }
            }
        });
        sp = getSharedPreferences("sabdata", MODE_PRIVATE);

        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    hud2.dismiss();
                    Log.d("Responseotp", _response);
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    phoneback.setVisibility(View.GONE);
                    otp_view.setVisibility(View.VISIBLE);
                    signin.setText("verify");
                    type = 1;
                    otp = String.valueOf(list.get(0).get("otp").toString());
                    double doubleValue = Double.parseDouble(otp);
                    intValue = (int) doubleValue;
                    login.setText("verify");
                    Log.d("Responsem",_response);
                    Log.d("Responsem otp",String.valueOf(intValue));
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "User not found!", Toast.LENGTH_LONG).show();
                    phone.setError("User not found!");

                    e.printStackTrace();
                }
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
        gradientDrawable.setStroke(2, Color.parseColor("#" + scolor));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
