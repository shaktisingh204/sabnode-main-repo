package com.waplia.watool.shortlink;

import static android.view.View.VISIBLE;



import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.anychart.scales.Linear;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import com.waplia.watool.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class newqr extends AppCompatActivity {
    private LinearLayout domainback, nameback, textline, smsline, wifiline, staticline, vline, linkline, emailline, phoneline, smsline1;
    private LinearLayout vcardline, application, fileline, waline, cryptoline, textback, cryptosline, fileback;
    private LinearLayout smssline, textsline, textssback, phonesback, messageback, wifisline, passtype, passwordback, networkidback;
   private LinearLayout linksline, linkback, emailsline, emailback, subjectback, emessageback, phonesline, phonenoback;
   private LinearLayout smssline2, phonesback1, messageback1, applicationsline, applinkback3, applinkback1, applinkback2;
  private LinearLayout wasline, phonesback2, messageback2, walletback, staticvsline;
  private EditText url;
  private KProgressHUD mProgressHUD;
  private EditText phonetext, messagetext, ssid, password, texttext, message, subject, email, phoneno, messagetext1, phonetext1;
    private ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
    private EditText applink3, applink1, applink2, messagetext2, phonetext2, wallettext, longurl;
  private MaterialButton create;
    private static final int FILE_PICKER_REQUEST_CODE = 154;
    private Uri selectedFileUri;
    String randomName ;
    String ext;
    private RadioButton btcc, eth, btc;
    private LinearLayout websiteback, vemailback, phonenos, lnameback, fnameback;
    private EditText website, fname, lname, vemail, phonenosss;
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newqr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        randomName = generateRandomName();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ssid = findViewById(R.id.networkid);
        websiteback = findViewById(R.id.websiteback);
        fnameback = findViewById(R.id.fnameback);
        lnameback = findViewById(R.id.lnameback);
        vemailback = findViewById(R.id.vemailback);
        phonenos = findViewById(R.id.phonenos);
        website = findViewById(R.id.websitetext);
        fname = findViewById(R.id.fnametext);
        lname = findViewById(R.id.lnametext);
        vemail = findViewById(R.id.vemail);
        phonenosss = findViewById(R.id.phonenostext);

        password = findViewById(R.id.passwordtext);
        domainback = findViewById(R.id.domainback);
        nameback = findViewById(R.id.nameback);
        textline = findViewById(R.id.textline);
        smsline = findViewById(R.id.smsline);
        wifiline = findViewById(R.id.wifiline);
        staticline = findViewById(R.id.staticline);
        vline = findViewById(R.id.eventline);
        linkline = findViewById(R.id.linkline);
       fileback = findViewById(R.id.fileback);
       cryptosline = findViewById(R.id.cryptosline);
        emailline = findViewById(R.id.emailline);
        phoneline = findViewById(R.id.phoneline);
        smsline1 = findViewById(R.id.smsline1);
        vcardline = findViewById(R.id.vcardline);
        application = findViewById(R.id.application);
        fileline = findViewById(R.id.fileline);
        waline = findViewById(R.id.waline);
        cryptoline = findViewById(R.id.cryptoline);
        smssline = findViewById(R.id.smssline);
        textsline = findViewById(R.id.textsline);
        textssback = findViewById(R.id.textssback);
        phonesback = findViewById(R.id.phonesback);
        messageback = findViewById(R.id.messageback);
        wifisline = findViewById(R.id.wifisline);
        passtype = findViewById(R.id.passtype);
        passwordback = findViewById(R.id.passwordback);
        networkidback = findViewById(R.id.networkidback);
        linksline = findViewById(R.id.linksline);
        linkback = findViewById(R.id.linkback);
        emailsline = findViewById(R.id.emailsline);
        emailback = findViewById(R.id.emailback);
        subjectback = findViewById(R.id.subjectback);
        emessageback = findViewById(R.id.emessageback);
        phonesline = findViewById(R.id.phonesline);
        phonenoback = findViewById(R.id.phonenoback);
        smssline2 = findViewById(R.id.smssline2);
        phonesback1 = findViewById(R.id.phonesback1);
        messageback1 = findViewById(R.id.messageback1);
        applicationsline = findViewById(R.id.applicationsline);
        create = findViewById(R.id.create);
        applinkback3 = findViewById(R.id.applinkback3);
        applinkback1 = findViewById(R.id.applinkback1);
        applinkback2 = findViewById(R.id.applinkback2);
        wasline = findViewById(R.id.wasline);
        phonesback2 = findViewById(R.id.phonesback2);
        url = findViewById(R.id.url);
        messageback2 = findViewById(R.id.messageback2);
        messagetext = findViewById(R.id.messagetext);
        phonetext = findViewById(R.id.phonetext);
       texttext = findViewById(R.id.texttext);
       message = findViewById(R.id.message);
       subject = findViewById(R.id.subject);
       email = findViewById(R.id.email);
       phoneno = findViewById(R.id.phoneno);
       phonetext1 = findViewById(R.id.phonetext1);
       messagetext1 = findViewById(R.id.messagetext1);
       applink1 = findViewById(R.id.applink1);
       applink2 = findViewById(R.id.applink2);
       applink3 = findViewById(R.id.applink3);
       phonetext2 = findViewById(R.id.phonetext2);
       messagetext2 = findViewById(R.id.messagetext2);
       walletback = findViewById(R.id.walletback);
       btcc = findViewById(R.id.btcc);
       eth = findViewById(R.id.eth);
       btc = findViewById(R.id.btc);
       wallettext = findViewById(R.id.wallettext);
       staticvsline = findViewById(R.id.staticvsline);
       longurl = findViewById(R.id.longurl);
        waline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(wasline, waline);
            }
        });

        textline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(textsline, textline);
            }
        });

        smsline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(smssline, smsline);
            }
        });
        wifiline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(wifisline, wifiline);
            }
        });
        emailline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(emailsline, emailline);
            }
        });
            linkline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(linksline, linkline);
                }
            });
            phoneline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(phonesline, phoneline);
                }
            });
            smsline1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(smssline2, smsline1);
                }
            });
            application.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(applicationsline, application);
                }
            });
            cryptoline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(cryptosline, cryptoline);
                }
            });
            staticline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(staticvsline, staticline);
                }
            });
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (longurl.getText().toString().isEmpty()){
                        Toast.makeText(newqr.this, "Enter Name First", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mProgressHUD = KProgressHUD.create(newqr.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setCancellable(false)
                            .show();
                    // Trigger the AsyncTask to make the network request
                    new newqr.NetworkRequestTask().execute();
                }
            });
        setRoundedCorners(textline, "00000000", "304FFE");
        setRoundedCorners(smsline, "00000000", "dadcdf");
        setRoundedCorners(wifiline, "00000000", "dadcdf");
        setRoundedCorners(staticline, "00000000", "dadcdf");
        setRoundedCorners(vline, "00000000", "dadcdf");
        setRoundedCorners(linkline, "00000000", "dadcdf");
        setRoundedCorners(emailline, "00000000", "dadcdf");
        setRoundedCorners(phoneline, "00000000", "dadcdf");
        setRoundedCorners(smsline1, "00000000", "dadcdf");
        setRoundedCorners(nameback, "00000000", "dadcdf");
        setRoundedCorners(domainback, "00000000", "dadcdf");
        setRoundedCorners(vcardline, "00000000", "dadcdf");
        setRoundedCorners(application, "00000000", "dadcdf");
        setRoundedCorners(fileline, "00000000", "dadcdf");
        setRoundedCorners(waline, "00000000", "dadcdf");
        setRoundedCorners(cryptoline, "00000000", "dadcdf");
        setRoundedCorners(textssback, "00000000", "dadcdf");
        setRoundedCorners(phonesback, "00000000", "dadcdf");
        setRoundedCorners(messageback, "00000000", "dadcdf");
        setRoundedCorners(passtype, "00000000", "dadcdf");
        setRoundedCorners(passwordback, "00000000", "dadcdf");
        setRoundedCorners(networkidback, "00000000", "dadcdf");
        setRoundedCorners(linkback, "00000000", "dadcdf");
        setRoundedCorners(emailback, "00000000", "dadcdf");
        setRoundedCorners(subjectback, "00000000", "dadcdf");
        setRoundedCorners(emessageback, "00000000", "dadcdf");
        setRoundedCorners(phonenoback, "00000000", "dadcdf");
        setRoundedCorners(smssline2, "00000000", "dadcdf");
        setRoundedCorners(phonesback1, "00000000", "dadcdf");
        setRoundedCorners(messageback1, "00000000", "dadcdf");
        setRoundedCorners(applinkback1, "00000000", "dadcdf");
        setRoundedCorners(applinkback2, "00000000", "dadcdf");
        setRoundedCorners(applinkback3, "00000000", "dadcdf");
  setRoundedCorners(phonesback2, "00000000", "dadcdf");
        setRoundedCorners(messageback2, "00000000", "dadcdf");
        setRoundedCorners(walletback, "00000000", "dadcdf");
        setRoundedCorners(fileback, "00000000", "dadcdf");
        setRoundedCorners(websiteback, "00000000", "dadcdf");
        setRoundedCorners(fnameback, "00000000", "dadcdf");
        setRoundedCorners(lnameback, "00000000", "dadcdf");
        setRoundedCorners(websiteback, "00000000", "dadcdf");
        setRoundedCorners(vemailback, "00000000", "dadcdf");
        setRoundedCorners(phonenos, "00000000", "dadcdf");

        fileback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);    }
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
public void selected(LinearLayout view, LinearLayout clicked){
    setRoundedCorners(textline, "00000000", "dadcdf");
    setRoundedCorners(smsline, "00000000", "dadcdf");
    setRoundedCorners(wifiline, "00000000", "dadcdf");
    setRoundedCorners(staticline, "00000000", "dadcdf");
    setRoundedCorners(vline, "00000000", "dadcdf");
    setRoundedCorners(linkline, "00000000", "dadcdf");
    setRoundedCorners(emailline, "00000000", "dadcdf");
    setRoundedCorners(phoneline, "00000000", "dadcdf");
    setRoundedCorners(smsline1, "00000000", "dadcdf");
    setRoundedCorners(nameback, "00000000", "dadcdf");
    setRoundedCorners(domainback, "00000000", "dadcdf");
    setRoundedCorners(vcardline, "00000000", "dadcdf");
    setRoundedCorners(application, "00000000", "dadcdf");
    setRoundedCorners(fileline, "00000000", "dadcdf");
    setRoundedCorners(waline, "00000000", "dadcdf");
    setRoundedCorners(cryptoline, "00000000", "dadcdf");
    setRoundedCorners(clicked, "00000000", "304FFE");
    textsline.setVisibility(View.GONE);
    smssline.setVisibility(View.GONE);
    wifisline.setVisibility(View.GONE);
    linksline.setVisibility(View.GONE);
    emailsline.setVisibility(View.GONE);
    phonesline.setVisibility(View.GONE);
    smssline2.setVisibility(View.GONE);
    applicationsline.setVisibility(View.GONE);
    wasline.setVisibility(View.GONE);
    cryptosline.setVisibility(View.GONE);
    staticvsline.setVisibility(View.GONE);
    view.setVisibility(VISIBLE);
    applyClickEffect(view);
    applyClickEffect(clicked);
}
    private void applyClickEffect(LinearLayout menu) {
        Animation clickEffect = AnimationUtils.loadAnimation(this, R.anim.bottom_nav_item_click);
        menu.startAnimation(clickEffect);
    }
    private void createqr() {

    }
    private boolean isValidURL(String url) {
        // Implement your URL validation logic here
        // Return true if the URL is valid, false otherwise
        return true; // Replace with your validation logic
    }

    private class NetworkRequestTask extends AsyncTask<Void, Void, newqr.NetworkResponse> {

        @Override
        protected newqr.NetworkResponse doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                MediaType mediaType = MediaType.parse("application/json");
                Map<String, Object> rqmap3 = new HashMap<>();
                Map<String, String> rqmap4 = new HashMap<>();

                if (linksline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "link");
                    rqmap3.put("type", "link");
                    rqmap3.put("data", url.getText().toString());
                    rqmap3.put("data", rqmap4);

                } else if (smssline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "sms");
                    rqmap3.put("type", "sms");
                    rqmap4.put("phone", phonetext.getText().toString());
                    rqmap4.put("message", messagetext.getText().toString());
                    rqmap3.put("data", rqmap4);

                } else if (wasline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "sms");
                    rqmap3.put("type", "whatsapp");
                    rqmap4.put("phone", phonetext2.getText().toString());
                    rqmap4.put("body", messagetext2.getText().toString());
                    rqmap3.put("data", rqmap4);

                } else if (smssline2.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "sms");
                    rqmap3.put("type", "smsonly");
                    rqmap4.put("phone", phonetext1.getText().toString());
                    rqmap4.put("message", messagetext1.getText().toString());
                    rqmap3.put("data", rqmap4);

                } else if (wifisline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "wifi");
                    rqmap3.put("type", "wifi");
                    rqmap4.put("ssid", ssid.getText().toString());
                    rqmap4.put("pass", password.getText().toString());
                    rqmap4.put("encryption", "wep");
                    rqmap3.put("data", rqmap4);

                } else if (textsline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "text");
                    rqmap3.put("type", "text");
                    rqmap3.put("data", texttext.getText().toString());
                } else if (emailsline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "email");
                    rqmap3.put("type", "email");
                    rqmap4.put("email", email.getText().toString());
                    rqmap4.put("body", message.getText().toString());
                    rqmap4.put("subject", subject.getText().toString());
                    rqmap3.put("data", rqmap4);
                } else if (applicationsline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "application");
                    rqmap3.put("type", "application");
                    rqmap4.put("apple", applink1.getText().toString());
                    rqmap4.put("google", applink2.getText().toString());
                    rqmap4.put("link", applink3.getText().toString());
                    rqmap3.put("data", rqmap4);
                } else if (phonesline.getVisibility() == View.VISIBLE) {
                    Log.d("link type", "phone");
                    rqmap3.put("type", "phone");
                    rqmap3.put("data", phoneno.getText().toString());
                } else if (cryptosline.getVisibility() == View.VISIBLE) {
                    rqmap3.put("type", "crypto");
                    if (btc.isChecked()) {
                        rqmap4.put("currency", "btc");
                    } else if (eth.isChecked()) {
                        rqmap4.put("currency", "eth");
                    }else if(btcc.isChecked()) {
                        rqmap4.put("currency", "bch");
                    }
                    rqmap4.put("wallet", wallettext.getText().toString());
                    rqmap3.put("data", rqmap4);
                    } else if (staticvsline.getVisibility() == View.VISIBLE) {
                    rqmap3.put("type", "staticvcard");
                    rqmap4.put("fname", fname.getText().toString());
                    rqmap4.put("lname", lname.getText().toString());
                    rqmap4.put("phone", phonenosss.getText().toString());
                    rqmap4.put("email", vemail.getText().toString());
                    rqmap4.put("site", website.getText().toString());
                    rqmap3.put("data", rqmap4);
                }


                Gson gson = new Gson();
                    String json = gson.toJson(rqmap3);
                    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                    RequestBody body = RequestBody.create(mediaType, json);

                    Log.d("requestbody", gson.toJson(rqmap3));
                    Request request = new Request.Builder()
                            .url("https://linkshort.sabnode.com/api/qr/add")
                            .method("POST", body)
                            .addHeader("Authorization", "Bearer ehPLPJqzatBUxmMP")
                            .addHeader("Content-Type", "application/json")
                            .build();

                    // Check if the URL is valid before making the network request
                    String urlToValidate = url.getText().toString();
                    if (isValidURL(urlToValidate)) {
                        return executeNetworkRequest(client, request);
                    } else {
                        // Return an indication that the URL is invalid
                        return new newqr.NetworkResponse(false, "Invalid URL");
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    return new newqr.NetworkResponse(false, "Error");
                }
            }

            @Override
            protected void onPostExecute (newqr.NetworkResponse result){
                if (result != null) {
                    Log.d("NetworkRequestTask", result.getResponse());
                    // Handle the result based on the returned NetworkResponse object
                    if (result.isSuccess()) {
                        mProgressHUD.dismiss();
                        // Convert the response body to a string
                        try {


                            list1 = new Gson().fromJson("[" + result.getResponse() + "]", new TypeToken<ArrayList<HashMap<String, Object>>>() {
                            }.getType());
                            String link = list1.get(0).get("link").toString();
                            Uri uri = Uri.parse(link);
                            openCustomTab(uri);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(newqr.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mProgressHUD.dismiss();
                        Toast.makeText(newqr.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private newqr.NetworkResponse executeNetworkRequest (OkHttpClient client, Request
            request){
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        // Convert the response body to a string
                        String responseBody = response.body().string();
                        return new newqr.NetworkResponse(true, responseBody);
                    } else {
                        return new newqr.NetworkResponse(false, "Request failed: " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return new newqr.NetworkResponse(false, "Error");
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

        private void openCustomTab(Uri uri) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, uri);
        }

        // ...

        private void yourMethodToOpenCustomTab() {

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                ext =  selectedFileUri.getPath().substring(selectedFileUri.getPath().lastIndexOf(".") + 1);
                uploadFile(selectedFileUri);
            } else {
                Log.e("FileUploader", "Selected file URI is null");
            }
        } else {
            Log.e("FileUploader", "File picker cancelled");
        }
    }
    private static String generateRandomName() {
        Random random = new Random();
        StringBuilder randomNameBuilder = new StringBuilder();

        // Ensure the name has exactly 16 digits
        while (randomNameBuilder.length() < 16) {
            int digit = random.nextInt(10);
            randomNameBuilder.append(digit);
        }

        return randomNameBuilder.toString();
    }
    private void uploadFile(Uri fileUri) {
        try {
            File file = new File(fileUri.getPath());
            URL url = new URL("https://linkshort.sabnode.com/uploadqrfile.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set content type
            String boundary = "*****";
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Create output stream to write data
            try (OutputStream outputStream = connection.getOutputStream()) {
                // Write file data
                outputStream.write(("--" + boundary + "\r\n").getBytes());
                outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
                outputStream.write(("Content-Type: " + getMimeType(fileUri) + "\r\n\r\n").getBytes());

                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            Log.i("FileUploader", "Server response code: " + responseCode);

            // Handle the response if needed...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMimeType(Uri uri) {
        // Determine the MIME type of the selected file
        return getContentResolver().getType(uri);
    }
}
