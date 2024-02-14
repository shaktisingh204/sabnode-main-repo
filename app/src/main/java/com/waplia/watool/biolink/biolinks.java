package com.waplia.watool.biolink;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.waplia.watool.R;
import com.waplia.watool.RequestNetwork;
import com.waplia.watool.RequestNetworkController;
import com.waplia.watool.SketchwareUtil;
import com.waplia.watool.shortlink.linkdash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class biolinks extends AppCompatActivity {
    private RecyclerView biolinklist;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private KProgressHUD mProgressHUD;
    private TextView titletxt;
    private String fontName, newlongurl;
    private ImageView newlink;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biolinks);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titletxt = findViewById(R.id.titletxt);
        biolinklist = findViewById(R.id.biolinklist);
        newlink = findViewById(R.id.newlink);
        newlink.setColorFilter(getResources().getColor(R.color.md_light_green_900), PorterDuff.Mode.MULTIPLY);
        newlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newlink();
            }
        }
        );
        //https://app.sabnode.com/apiv1/biolinks/biolinks.php
        rq = new RequestNetwork(this);
        _rq_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("biolinkdata", _response);
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    biolinklist.setAdapter(new Recyclerview1Adapter(list));
                    biolinklist.setLayoutManager(new LinearLayoutManager(biolinks.this));
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
        mProgressHUD = KProgressHUD.create(biolinks.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .show();
        if (getIntent().getExtras().getString("type").equals("biolink")){
            titletxt.setText("Biolink Pages");
        } else if (getIntent().getExtras().getString("type").equals("link")){
            titletxt.setText("Shortened links");
        }else if (getIntent().getExtras().getString("type").equals("file")){
            titletxt.setText("File links");
        }else if (getIntent().getExtras().getString("type").equals("vcard")){
            titletxt.setText("Vcard links");
        }else if (getIntent().getExtras().getString("type").equals("qrcode")){
            titletxt.setText("QR codes");
    }
        rqmap.put("id", getIntent().getExtras().getString("id"));
        rqmap.put("type", getIntent().getExtras().getString("type"));
        rq.setParams(rqmap, 0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/biolinks.php","", _rq_request_listener);
        SwipeRefreshLayout refresh = findViewById(R.id.refreshLayout);
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mProgressHUD = KProgressHUD.create(biolinks.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .show();
                        rqmap.put("id", getIntent().getExtras().getString("id"));
                        rqmap.put("type", getIntent().getExtras().getString("type"));
                        rq.setParams(rqmap, 0);
                        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.suburl)+"biolinks/biolinks.php","", _rq_request_listener);
                        refresh.setRefreshing(false);
                    }
                }
        );
    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<biolinks.Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public biolinks.Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.biolinkslist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new biolinks.Recyclerview1Adapter.ViewHolder(_v);

        }
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public void onBindViewHolder(biolinks.Recyclerview1Adapter.ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;
           final TextView name = _view.findViewById(R.id.name);
           final TextView url = _view.findViewById(R.id.url);
           final TextView clicks = _view.findViewById(R.id.clicks);
           final ImageView icon = _view.findViewById(R.id.icon);
           final ImageView stats = _view.findViewById(R.id.stats);
           if (_data.get(_position).containsKey("url")){
           name.setText(_data.get(_position).get("url").toString());
           url.setText("biolink.sabnode.com/"+_data.get(_position).get("url").toString());
           clicks.setText(_data.get(_position).get("clicks").toString());
           }else {
               name.setText(_data.get(_position).get("name").toString());
               url.setVisibility(View.GONE);
               clicks.setText(_data.get(_position).get("type").toString().toUpperCase());
               stats.setVisibility(View.GONE);
           }
            if (_data.get(_position).containsKey("url")){
           if (_data.get(_position).get("type").toString().equals("biolink")){
                icon.setImageDrawable(getDrawable(R.drawable.bio));
           } else if (_data.get(_position).get("type").toString().equals("link")){
               icon.setImageDrawable(getDrawable(R.drawable.link));
           }else if (_data.get(_position).get("type").toString().equals("file")){
               icon.setImageDrawable(getDrawable(R.drawable.file));
           }else if (_data.get(_position).get("type").toString().equals("vcard")){
               icon.setImageDrawable(getDrawable(R.drawable.vcard));
           }}else {
                icon.setImageDrawable(getDrawable(R.drawable.qrcode));
                clicks.setTextColor(getColor(R.color.md_light_green_900));
            }
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
public void newlink(){
    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(biolinks.this);

    View bottomSheetView; bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_p5,null );
    bottomSheetDialog.setContentView(bottomSheetView);

    TextView t1 = (TextView) bottomSheetView.findViewById(R.id.t1);

    TextView t2 = (TextView) bottomSheetView.findViewById(R.id.t2);

    TextView b1 = (TextView) bottomSheetView.findViewById(R.id.b1);

    TextView b2 = (TextView) bottomSheetView.findViewById(R.id.b2);

    ImageView i1 = (ImageView) bottomSheetView.findViewById(R.id.i1);
    LinearLayout l1 = (LinearLayout) bottomSheetView.findViewById(R.id.messageback);
    LinearLayout l2 = (LinearLayout) bottomSheetView.findViewById(R.id.aliasback);
    EditText longurl = (EditText) bottomSheetView.findViewById(R.id.messagetext);
    setRoundedCorners(l1, "00000000", "dadcdf");
    setRoundedCorners(l2, "00000000", "dadcdf");

    LinearLayout bg = (LinearLayout) bottomSheetView.findViewById(R.id.bg);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        t1.setTypeface(getResources().getFont(R.font.gpp), Typeface.NORMAL);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        t2.setTypeface(getResources().getFont(R.font.gpp), Typeface.NORMAL);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        b1.setTypeface(getResources().getFont(R.font.gpp), Typeface.NORMAL);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        b2.setTypeface(getResources().getFont(R.font.gpp), Typeface.NORMAL);
    }
    i1.setImageResource(R.drawable.link);
    t1.setText("Disconnected !");
    t2.setText("Sorry you can't connect to this project at this moment. please try again later.");
    b1.setText("Cancel");
    b2.setText("Short Url");
    _RoundAndBorder(i1, "#D50000", 0, "#D50000", 100);
    _rippleRoundStroke(bg, "#FFFFFF", "#000000", 15, 0, "#000000");
    _rippleRoundStroke(b1, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
    _rippleRoundStroke(b2, "#D50000", "#40FFFFFF", 15, 0, "#000000");
    i1.setElevation((float)0.1d);
    _ICC(i1, "#FFFFFF", "#FFFFFF");
    b1.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
        bottomSheetDialog.dismiss();
        SketchwareUtil.showMessage(getApplicationContext(), "button1 Press");
    }
    });
    b2.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
        newlongurl = longurl.getText().toString();
        new biolinks.NetworkRequestTask().execute();

        SketchwareUtil.showMessage(getApplicationContext(), "button2 Press");
    }
    });
    bottomSheetDialog.setCancelable(true);
    bottomSheetDialog.show();
}
    public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float)_round);
        GG.setStroke((int) _stroke,
                Color.parseColor("#" + _strokeclr.replace("#", "")));
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
        _view.setBackground(RE);
    }


    public void _NavStatusBarColor(final String _color1, final String _color2) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
        }
    }


    public void _DARK_ICONS() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    public void _RoundAndBorder(final View _view, final String _color1, final double _border, final String _color2, final double _round) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color1));
        gd.setCornerRadius((int) _round);
        gd.setStroke((int) _border, Color.parseColor(_color2));
        _view.setBackground(gd);
    }


    public void _removeScollBar(final View _view) {
        _view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
    }


    public void _underline(final TextView _textview) {
        _textview.setPaintFlags(_textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }


    public void _changeActivityFont(final String _fontname) {
         fontName = "fonts/".concat(_fontname.concat(".ttf"));
        overrideFonts(this,getWindow().getDecorView());
    }
    private void overrideFonts(final android.content.Context context, final View v) {

        try {
            Typeface
                    typeace = Typeface.createFromAsset(getAssets(), fontName);;
            if ((v instanceof ViewGroup)) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0;
                     i < vg.getChildCount();
                     i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            }
            else {
                if ((v instanceof TextView)) {
                    ((TextView) v).setTypeface(typeace);
                }
                else {
                    if ((v instanceof EditText)) {
                        ((EditText) v).setTypeface(typeace);
                    }
                    else {
                        if ((v instanceof Button)) {
                            ((Button) v).setTypeface(typeace);
                        }
                    }
                }
            }
        }
        catch(Exception e)

        {
            SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
        };
    }


    public void _addCardView(final View _layoutView, final double _margins, final double _cornerRadius, final double _cardElevation, final double _cardMaxElevation, final boolean _preventCornerOverlap, final String _backgroundColor) {
        androidx.cardview.widget.CardView cv = new androidx.cardview.widget.CardView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int m = (int)_margins;
        lp.setMargins(m,m,m,m);
        cv.setLayoutParams(lp);
        int c = Color.parseColor(_backgroundColor);
        cv.setCardBackgroundColor(c);
        cv.setRadius((float)_cornerRadius);
        cv.setCardElevation((float)_cardElevation);
        cv.setMaxCardElevation((float)_cardMaxElevation);
        cv.setPreventCornerOverlap(_preventCornerOverlap);
        if(_layoutView.getParent() instanceof LinearLayout){
            ViewGroup vg = ((ViewGroup)_layoutView.getParent());
            vg.removeView(_layoutView);
            vg.removeAllViews();
            vg.addView(cv);
            cv.addView(_layoutView);
        }else{

        }
    }


    public void _ICC(final ImageView _img, final String _c1, final String _c2) {
        _img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
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
    private class NetworkRequestTask extends AsyncTask<Void, Void, biolinks.NetworkResponse> {

        @Override
        protected biolinks.NetworkResponse doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                MediaType mediaType = MediaType.parse("application/json");
                Map<String, String> rqmap3 = new HashMap<>();
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("url",newlongurl)
                        .addFormDataPart("location_url","https://biolink.sabnode.com/")
                        .build();
                Request request = new Request.Builder()
                        .url("https://biolink.sabnode.com/api/links")
                        .method("POST", body)
                        .addHeader("Authorization", "Bearer dbb0fc75ba9f33be66e69a408f609838")
                        .addHeader("Content-Type", "application/json")
                        .build();

                // Check if the URL is valid before making the network request
                String urlToValidate = newlongurl;
                if (isValidURL(urlToValidate)) {
                    return executeNetworkRequest(client, request);
                } else {
                    // Return an indication that the URL is invalid
                    return new biolinks.NetworkResponse(false, "Invalid URL");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new biolinks.NetworkResponse(false, "Error");
            }
        }

        @Override
        protected void onPostExecute(biolinks.NetworkResponse result) {
            if (result != null) {
                Log.d("Response", result.getResponse());
                // Handle the result based on the returned NetworkResponse object
                if (result.isSuccess()) {
                    mProgressHUD.dismiss();
                   Toast.makeText(biolinks.this, "Request successful: " + result.getResponse(), Toast.LENGTH_SHORT).show();
                } else {
                    mProgressHUD.dismiss();
                    Toast.makeText(biolinks.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private biolinks.NetworkResponse executeNetworkRequest(OkHttpClient client, Request request) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Convert the response body to a string
                    String responseBody = response.body().string();
                    return new biolinks.NetworkResponse(true, responseBody);
                } else {
                    return new biolinks.NetworkResponse(false, "Request failed: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new biolinks.NetworkResponse(false, "Error");
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
