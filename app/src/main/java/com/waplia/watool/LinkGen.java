package com.waplia.watool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacterEnums;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class LinkGen extends AppCompatActivity {
    private TextView linktext;
    private TextView username;
    private TextView pid;
    private ImageView avatar;
    private EditText textmsg;
    private MaterialButton copy;
    private TextView sharetext;
    private SharedPreferences sp;
    private RequestNetwork rq;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private QRCodeBitmapGenerator qrCodeBitmapGenerator;
private ImageView qrimg;
private MaterialButton Download;
private MaterialButton gqr;
private LinearLayout qrmail;
private MaterialButton shareqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkgen);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        linktext = findViewById(R.id.linktext);
        username = findViewById(R.id.username);
        pid = findViewById(R.id.pid);
        avatar = findViewById(R.id.avtar);
        textmsg = findViewById(R.id.email);
        copy = findViewById(R.id.copytext);
        sharetext = findViewById(R.id.sharetext);
        qrimg = findViewById(R.id.qr_code_image_view);
        Download = findViewById(R.id.gnarateqr);
        gqr = findViewById(R.id.saveqr);
        qrmail = findViewById(R.id.qrpart);
        shareqr = findViewById(R.id.shareqr);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        qrCodeBitmapGenerator = new QRCodeBitmapGenerator();
        textmsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changing
                final String _charSeq = s.toString();
                /*textview1.setText(_charSeq);*/


            }

            @Override
            public void afterTextChanged(Editable s) {
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
                    list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response", _response);
                    username.setText(list.get(0).get("name").toString());
                    Glide.with(getApplicationContext())
                            .load(getResources().getString(R.string.base_url)+"writable/"+list.get(0).get("avatar").toString())
                            .into(avatar);
                    pid.setText(list.get(0).get("pid").toString());
                    String urlwa = "https://api.whatsapp.com/send?phone="+list.get(0).get("pid").toString().replace("@s.whatsapp.net","");
                    linktext.setText("https://api.whatsapp.com/send?phone="+list.get(0).get("pid").toString().replace("@s.whatsapp.net",""));
                  /*  instance.setText(list.get(0).get("token").toString());
                    token.setText(list.get(0).get("idsn").toString());*/
                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = textmsg.getText().toString();
                            try {
                                String encodedText = URLEncoder.encode(text, "UTF-8");
                                text = encodedText;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            linktext.setText(urlwa+"&text="+text);
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Link", linktext.getText().toString());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(LinkGen.this, "Link Copied", Toast.LENGTH_SHORT).show();
                        }
                    });
                    sharetext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = textmsg.getText().toString();
                            try {
                                String encodedText = URLEncoder.encode(text, "UTF-8");
                                text = encodedText;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            linktext.setText(urlwa+"&text="+text);
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Link");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, linktext.getText().toString());
                            startActivity(Intent.createChooser(sharingIntent, "Share Link"));
                        }
                    });
                    Download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = textmsg.getText().toString();
                            try {
                                String encodedText = URLEncoder.encode(text, "UTF-8");
                                 text = encodedText;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            linktext.setText(urlwa+"&text="+text);
                            String message = linktext.getText().toString();
                            if (!TextUtils.isEmpty(message)) {
                                qrimg.setImageBitmap(qrCodeBitmapGenerator.encodeAsBitmap(message));
                                qrmail.setVisibility(View.VISIBLE);
                            }else {
                                linktext.setError("Message is empty");
                            }
                        }
                    });
                    gqr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            capture(gqr);
                        }
                    });
                    shareqr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("image/jpeg");
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(savedImageURL)));
                            startActivity(Intent.createChooser(sharingIntent, "Share Image"));
                         */   Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.setType("image/jpeg");

// Get the image from the ImageView
                            ImageView imageView = findViewById(R.id.qr_code_image_view);
                            Drawable drawable = imageView.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

// Create a new Uri object for the bitmap
                            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));

// Set the Intent's data to the Uri object
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

// Start the Intent
                            startActivity(Intent.createChooser(shareIntent, "Share Image"));
                        }
                    });
                }catch(Exception e){
                    Log.d("Error Response", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/profile.php","", _rq_request_listener);

    }
    public void capture(View view){
        Bitmap photo = getBitmapFromView((ImageView) findViewById(R.id.qr_code_image_view));
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                photo,
                "your_layout",
                "image"

        );

        Toast.makeText(this, "Qr saved", Toast.LENGTH_SHORT).show();

    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}
