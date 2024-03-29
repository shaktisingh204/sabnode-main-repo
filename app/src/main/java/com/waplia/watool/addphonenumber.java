package com.waplia.watool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbb20.CountryCodePicker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kevinschildhorn.otpview.OTPView;

import java.util.ArrayList;
import java.util.HashMap;

public class addphonenumber extends AppCompatActivity {
    private EditText phone;
    private LinearLayout register, phoneline;
    private MaterialButton login;
    private OTPView otpView;
    private CountryCodePicker cpp;
    private Integer type;
    private String otp, lotp;

    private int intValue;
    private KProgressHUD hud2;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private SharedPreferences sp;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphonenumber);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sp = getSharedPreferences("data", MODE_PRIVATE);

        type = 0;
        phone = findViewById(R.id.phoneno);
        phoneline = findViewById(R.id.phoneline);
        setRoundedCorners(phoneline,"#ebf4fa");
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        otpView = findViewById(R.id.otpview);
        cpp = findViewById(R.id.ccp);
        cpp.registerCarrierNumberEditText(phone);
        cpp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (isValidNumber) {
                    login.setEnabled(true);
                } else {
                    phone.setError("Invalid phone number");
                    login.setEnabled(false);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (type == 0) {
                    hud2 = KProgressHUD.create(addphonenumber.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rqmap.put("mobile", cpp.getFullNumber());
                    Log.d("Mobilem", cpp.getFullNumber());
                    rq.setParams(rqmap, 0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url) + "apis/otpcheck.php", "", _rq_request_listener);
                }else{
                    if (type == 1){
                        lotp = otpView.getStringFromFields().toString();
                        Log.d("otpl", lotp);
                        Log.d("otp", intValue+"otp");

                        try {
                            int ilotp = Integer.valueOf(lotp);
                            if (intValue == ilotp) {
                                hud2 = KProgressHUD.create(addphonenumber.this)
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setCancellable(false)
                                        .setAnimationSpeed(2)
                                        .setDimAmount(0.8f)
                                        .show();
                                rqmap2.put("mobile", cpp.getFullNumber());
                                rqmap2.put("id", sp.getString("id", ""));
                                rq2.setParams(rqmap2, 0);
                                rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url) + "apis/newmobile.php", "", _rq2_request_listener);

                            } else {
                                // If the entered OTP does not match the expected OTP
                                otpView.clearText(true); // Clear the entered OTP in otpView
                                Toast.makeText(getApplicationContext(), "Wrong OTP!", Toast.LENGTH_LONG).show();
                            }
                        } catch (NumberFormatException e) {
                            // Handle the exception (e.g., show an error message, log, etc.)
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent()
                        .setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getResources().getString(R.string.base_url)+"signup"));
                startActivity(i);
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
                    hud2.dismiss();
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    if (list.get(0).get("statusp").toString().equals("success")) {
                        new iOSDialogBuilder(addphonenumber.this)
                                .setTitle("Error")
                                .setSubtitle("Mobile number is already in use")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Enter another",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();

                                    }
                                })
                                .build()
                                .show();
                        login.setEnabled(false);
                    }else {if (list.get(0).get("statusp").toString().equals("failed")) {
                        phoneline.setVisibility(View.GONE);
                        otpView.setVisibility(View.VISIBLE);
                        type = 1;
                        otp = String.valueOf(list.get(0).get("otp").toString());
                        double doubleValue = Double.parseDouble(otp);
                        intValue = (int) doubleValue;
                        login.setText("verify");
                        Log.d("Responsem",_response);
                        Log.d("Responsem otp",String.valueOf(intValue));
                      }
                    }
                }catch(Exception e){
                    Log.d("Responsem",_response);
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
                    Log.d("Responsem",_response);
                    hud2.dismiss();
                    if (_response.equals("done")) {
                        sp.edit().putString("showm", "done").apply();
                        new iOSDialogBuilder(addphonenumber.this)
                                .setTitle("Success")
                                .setSubtitle("Mobile number is Successfully Linked")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Go to Dashboard",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();
                                        Intent i = new Intent(addphonenumber.this, Main2Home.class);
                                        startActivity(i);
                                    }
                                })
                                .build().show();
                    }else {
                        if (list.get(0).get("statusp").toString().equals("failed")) {
                        new iOSDialogBuilder(addphonenumber.this)
                                .setTitle("Error")
                                .setSubtitle(_response)
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Try Again",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();

                                    }
                                })
                                .build()
                                .show();
                        login.setEnabled(false);}
                    }
                }catch(Exception e){
                    Log.d("Responsem",_response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
    }


    private void setRoundedCorners(View linearLayout, String color) {
        // Create a new GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable();

        // Set the shape to a rectangle
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Set the corner radius (adjust as needed)
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});

        // Set the background color (adjust as needed)
        gradientDrawable.setColor(Color.parseColor(color));

        // Set the stroke (optional)
        gradientDrawable.setStroke(2, getResources().getColor(R.color.edittextback));

        // Set the gradient type (optional)
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set the orientation of the gradient (optional)
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // Set the background drawable for the LinearLayout
        linearLayout.setBackground(gradientDrawable);
    }

}
