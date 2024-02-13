package com.waplia.watool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
public class addphonenumbers extends AppCompatActivity {
 String[] numbs = new String[1000];
    private static final int CONTACTS_PERMISSION_REQUEST = 1; // Choose any positive integer as a request code

    private MaterialButton create, mobile;
 private EditText numbers;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private RequestNetwork rq2;
    private SharedPreferences sp;
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2;
    private final int REQ_CD_FHHH = 101;
    private KProgressHUD hud;
    private Intent fhhh = new Intent(Intent.ACTION_GET_CONTENT);

    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addphonenumbers);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        create = findViewById(R.id.create);
// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        numbers = findViewById(R.id.numbers);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (numbers.getText().toString().equals("")) {
                    Toast.makeText(addphonenumbers.this, "Please enter numbers", Toast.LENGTH_SHORT).show();
                }else {
                    hud = KProgressHUD.create(addphonenumbers.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                numbersadd();
            }}
        });
        MaterialButton btnDlExample = findViewById(R.id.btnDlFile);
        btnDlExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://bulkreply.wapliaa.com/csv/csv_template%20.csv"));
                startActivity(i);
            }
        });
        mobile = findViewById(R.id.mobile);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(addphonenumbers.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(addphonenumbers.this, new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST);
                } else {
                    // Permission already granted, proceed to read contacts
                    hud = KProgressHUD.create(addphonenumbers.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    readContacts();
                }

            }
        });
        MaterialButton btnPickFile = findViewById(R.id.btnPickFile);
        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    pickCsvFile();
            }
        });
        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    list2 = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    Log.d("Response", _response);
                    hud.dismiss();
                    finish();
                }catch(Exception e){
                    hud.dismiss();
                    finish();
                    Log.d("Error instance r2", _response);
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                Log.d("Error Response", _message + "001");
            }
        };

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {

            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {

        }else{
            if (requestCode == CONTACTS_PERMISSION_REQUEST) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hud = KProgressHUD.create(addphonenumbers.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.8f)
                            .show();
                    // Permission granted, proceed to read contacts
                    readContacts();
                } else {
                    // Permission denied, handle accordingly
                }
            }
        }
    }



        public void numbersadd(){
        list.clear();
        rqmap2.clear();
        rqmap.clear();
                String OVERLAPPING_EMAIL_ADDRESSES = numbers.getText().toString()+"\n";
                /*"8955823066\n8696585637\n8696585637\n8945544635\n";*/
                int count = 0;

                for (int i = 0; i < OVERLAPPING_EMAIL_ADDRESSES.length(); i++) {
                    if (OVERLAPPING_EMAIL_ADDRESSES.charAt(i) == '\n'){
                        count++;
                    }
                }
                for (int i = 0; i < count; i++) {
                    String str = OVERLAPPING_EMAIL_ADDRESSES.substring(0,OVERLAPPING_EMAIL_ADDRESSES.indexOf("\n"));
                    OVERLAPPING_EMAIL_ADDRESSES = OVERLAPPING_EMAIL_ADDRESSES.replace(str+"\n", "");
                    OVERLAPPING_EMAIL_ADDRESSES = OVERLAPPING_EMAIL_ADDRESSES.replace(str+" ", "");
                    numbs[i] = str.replace(" ", "");
                    rqmap = new HashMap<>();
                    rqmap.put("Column0", str.replace(" ", ""));
                    list.add(rqmap);
                    Log.d("phonenumber"+i, numbs[i]);
                }
               rqmap2 = new HashMap<>();
                rqmap2.put("team_id", sp.getString("id", ""));
                rqmap2.put("pid", getIntent().getStringExtra("id"));
                rqmap2.put("json", JsonUtils.convertListToJson(list));
            rq2.setParams(rqmap2,0);
            rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/savecontacts.php","", _rq2_request_listener);
            Log.d("phonenumberlist",list.toString());
                /*
                List<Map<String, String>> resultList = Arrays.stream(numbs)
                        .map(item -> {
                            String[] keyValue = item.split(":");
                            return Map.of(keyValue[0], keyValue[1]);
                        })
                        .collect(Collectors.toList());
                    Log.d("phonenumber", String.valueOf(resultList));*/
        }
        public void contactsfromcsv(String csvFilePath) {

            try {
                Reader in = new FileReader(csvFilePath);
                Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
                ArrayList<HashMap<String, Object>> listOfMaps = new ArrayList<>();


                for (CSVRecord record : records) {
                    Map<String, Object> recordMap = recordToMap(record);
                    listOfMaps.add(new HashMap<>(recordMap));

                }
                hud = KProgressHUD.create(addphonenumbers.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.8f)
                        .show();
                rqmap2 = new HashMap<>();
                rqmap2.put("team_id", sp.getString("id", ""));
                rqmap2.put("pid", getIntent().getStringExtra("id"));
                rqmap2.put("json", JsonUtils.convertListToJson(listOfMaps));
                rq2.setParams(rqmap2,0);
                rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/savecontacts.php","", _rq2_request_listener);

                Log.d("datalistcsv", listOfMaps.toString());
            } catch (IOException e) {
            Log.d("csvphonenumber", String.valueOf(e.getMessage()));
            }


        }

    public static List<HashMap<String, Object>> readCsvFile(String filePath) throws IOException {
        List<HashMap<String, Object>> dataList = new ArrayList<>();

        FileReader reader = new FileReader(filePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

        for (CSVRecord record : csvParser) {
            HashMap<String, Object> data = new HashMap<>();
            for (Map.Entry<String, String> entry : record.toMap().entrySet()) {
                // Assuming that your CSV header contains the keys
                // Adjust this based on your actual CSV structure
                data.put(entry.getKey(), entry.getValue());
            }
            dataList.add(data);
        }

        csvParser.close();
        reader.close();

        return dataList;
    }
    private void pickCsvFile() {
        fhhh.setType("*/*");
        fhhh.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
       startActivityForResult(fhhh, REQ_CD_FHHH);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_FHHH:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));

                            }
                        }
                        else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    String filePath = _filePath.get((int)(0));
                    Path path = Paths.get(filePath);

                    // Get the file extension
                    String fileExtension = getFileExtension(path);


                    if (fileExtension.equals("csv")){
                    contactsfromcsv(_filePath.get((int)(0)));
                }else{
                        Toast.makeText(this, "Please Choose a Valid CSV file", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }
                break;
            default:
                break;
        }
    }
    private static Map<String, Object> recordToMap(CSVRecord record) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < record.size(); i++) {
            map.put("Column" + i, record.get(i));
        }
        return map;
    }
    private static String getFileExtension(Path path) {
        return path.getFileName().toString().contains(".")
                ? path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf('.') + 1)
                : "";
    }
    private void readContacts() {

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            JSONArray contactsArray = new JSONArray();

            do {
                @SuppressLint("Range") String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // Retrieve one phone number for the contact
                String phoneNumber = getSingleContactPhoneNumber(contactId);

                // Create a JSON object for each contact
                JSONObject contactJson = new JSONObject();
                try {
                    contactJson.put("Column0", phoneNumber);
                    // Add other contact properties to the JSON object
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Add the JSON object to the array
                contactsArray.put(contactJson);

            } while (cursor.moveToNext());

            cursor.close();
            rqmap2 = new HashMap<>();
            rqmap2.put("team_id", sp.getString("id", ""));
            rqmap2.put("pid", getIntent().getStringExtra("id"));
            rqmap2.put("json", contactsArray.toString());
            rq2.setParams(rqmap2,0);
            rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/savecontacts.php","", _rq2_request_listener);

            Log.d("datalistcsv", contactsArray.toString());
        }
    }
    private String getSingleContactPhoneNumber(String contactId) {
        String phoneNumber = "";

        // Query for one phone number using the contact ID
        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }

        return phoneNumber;
    }}
class JsonUtils {
    // Utility method to convert ArrayList<HashMap<String, Object>> to JSON string
    public static String convertListToJson(ArrayList<HashMap<String, Object>> dataList) {
        // Your JSON conversion logic here
        // You may use a library like Gson or Jackson for better JSON handling
        // For simplicity, this example uses a basic conversion method
        StringBuilder jsonString = new StringBuilder("[");
        for (HashMap<String, Object> data : dataList) {
            jsonString.append("{");
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                jsonString.append("\"").append(entry.getKey()).append("\":");
                if (entry.getValue() instanceof String) {
                    jsonString.append("\"").append(entry.getValue()).append("\",");
                } else {
                    jsonString.append(entry.getValue()).append(",");
                }
            }
            jsonString.deleteCharAt(jsonString.length() - 1); // Remove the trailing comma
            jsonString.append("},");
        }
        if (!dataList.isEmpty()) {
            jsonString.deleteCharAt(jsonString.length() - 1); // Remove the trailing comma
        }
        jsonString.append("]");
        return jsonString.toString();
    }

}

