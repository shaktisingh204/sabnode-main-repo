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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.Login;
import com.waplia.watool.Main2Home;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.start;

import java.util.ArrayList;
import java.util.HashMap;

public class singin extends AppCompatActivity {
    private ImageView mailic, key, google, mobile;
    private TextView signin;
    private LinearLayout devider, glogin, mlogin;
    private CardView login;
    private EditText email, password;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private SharedPreferences sp, sp2;
    private KProgressHUD hud2;
    private static final int RC_SIGN_IN = 9001;
    private LinearLayout emailback, passwordback;
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.singin);
            emailback = findViewById(R.id.emailback);
            passwordback = findViewById(R.id.passwordback);
            mailic = findViewById(R.id.mailic);
            key = findViewById(R.id.key);
            google = findViewById(R.id.google);
            mobile = findViewById(R.id.mobile);
            key.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
            mailic.setColorFilter(getResources().getColor(R.color.md_deep_purple_A400));
            google.setColorFilter(Color.parseColor("#2962FF"));
            mobile.setColorFilter(Color.parseColor("#2962FF"));
            setRoundedCorners(emailback, "00000000", "f3f5f8");
            setRoundedCorners(passwordback, "00000000", "f3f5f8");
            signin = (TextView) findViewById(R.id.signin);
            changeStatusBarColor();
            devider = findViewById(R.id.divider);
            glogin = findViewById(R.id.glogin);
            mlogin = findViewById(R.id.mlogin);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            setRoundedCorners(glogin, "00000000", "f3f5f8");
            setRoundedCorners(mlogin, "00000000", "f3f5f8");
            setRoundedCorners(signin, "2962FF", "2962FF");
            setRoundedCorners(devider, "717595", "717595");
            login = findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(singin.this, signup.class));
                }
            });
            mlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(singin.this, mobilelogin.class));
                }
            });
            sp = getSharedPreferences("sabdata", MODE_PRIVATE);
            sp2 = getSharedPreferences("data", MODE_PRIVATE);

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hud2 = KProgressHUD.create(singin.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    rqmap.put("email", email.getText().toString());
                    rqmap.put("password", password.getText().toString());
                    rq.setParams(rqmap,0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"login.php","", _rq_request_listener);

                }
            });
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("702763829684-p9g3n39khn6n6lqi2oko2hge8vutvd95.apps.googleusercontent.com")
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            LinearLayout signInButton = findViewById(R.id.glogin);
            signInButton.setOnClickListener(v -> {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
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

                        if (!list.contains("status")) {
                            Log.d("Responsel",_response);
                            sp.edit().putString("id", list.get(0).get("id").toString()).commit();
                            sp.edit().putString("login", "done").commit();
                            sp.edit().putString("email", list.get(0).get("email").toString()).commit();
                            sp.edit().putString("plan", list.get(0).get("email").toString()).commit();
                            sp2.edit().putString("id", list.get(0).get("bulkid").toString()).commit();
                            sp2.edit().putString("login", "done").commit();
                            sp2.edit().putString("email", list.get(0).get("email").toString()).commit();
                            sp2.edit().putString("plan", list.get(0).get("email").toString()).commit();
                            Log.d("Responsel", String.valueOf(list));
                            Intent i = new Intent(singin.this, dashboard.class);
                            if (list.get(0).get("mobile_number").toString().equals("")) {
                                i.putExtra("showm","showm");
                                sp.edit().putString("showm", "showm").apply();
                            }
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),_response,Toast.LENGTH_SHORT).show();
                        }

                    }catch(Exception e){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Use account information as needed
            Log.d("GoogleSignIn", "signInResult:success - " + account.getEmail());
        } catch (ApiException e) {
            Log.e("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
        }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(singin.this, start.class));
    }
}
