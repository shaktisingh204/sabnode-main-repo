package com.waplia.watool;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;

public class contacts extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RequestNetwork rq;
    private SharedPreferences sp;
    private RequestNetwork.RequestListener _rq_request_listener;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private HashMap<String, Object> rqmap = new HashMap<>();
    private RequestNetwork rq2;
    private HashMap<String, Object> rqmap3 = new HashMap<>();
    private RequestNetwork rq3;
    private RequestNetwork.RequestListener _rq3_request_listener;
    private RequestNetwork.RequestListener _rq2_request_listener;
    private ArrayList<HashMap<String, Object>> list2 = new ArrayList<>();
    private HashMap<String, Object> rqmap2 = new HashMap<>();
    private ImageView create;
    private EditText groupname;
    private MaterialButton creategroup;
    private LinearLayout addform, empty;
    private KProgressHUD hud, hud2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        create = (ImageView) findViewById(R.id.groupadd);
        groupname = (EditText) findViewById(R.id.groupname);
        creategroup = (MaterialButton) findViewById(R.id.create);
        addform = (LinearLayout) findViewById(R.id.addform);
        empty = (LinearLayout) findViewById(R.id.empty);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addform.setVisibility(View.VISIBLE);

            }
        });

// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));}
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        sp = getSharedPreferences("data", MODE_PRIVATE);

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
                    recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview1);
                    recyclerView1.setAdapter(new Recyclerview1Adapter(list));
                    recyclerView1.setLayoutManager(new LinearLayoutManager(contacts.this));
                    hud.dismiss();
                }catch(Exception e){
                    Log.d("Error Response", _response);
                    hud.dismiss();
                    recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview1);
                    recyclerView1.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    Toast.makeText(contacts.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
        hud = KProgressHUD.create(contacts.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .show();
        rqmap.put("id", sp.getString("id",""));
        rq.setParams(rqmap,0);
        rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/contacts.php","", _rq_request_listener);


        rq2 = new RequestNetwork(this);
        _rq2_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                      Log.d("Response", _response);
                      hud2.dismiss();
                 //   list = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    addform.setVisibility(View.GONE);
                    groupname.setText("");;
                    rqmap.put("id", sp.getString("id",""));
                    rq.setParams(rqmap,0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/contacts.php","", _rq_request_listener);
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
        rq3 = new RequestNetwork(this);
        _rq3_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    Log.d("Response delete", _response);
                    rqmap.put("id", sp.getString("id",""));
                    rq.setParams(rqmap,0);
                    rq.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/contacts.php","", _rq_request_listener);
                    Toast.makeText(contacts.this, "Deleted", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Log.d("Error delete", _response);
                }
            }
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };

        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hud2 = KProgressHUD.create(contacts.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.8f)
                        .show();
                rqmap2.put("tid", sp.getString("id",""));
                rqmap2.put("name", groupname.getText().toString());
                rq2.setParams(rqmap2,0);
                rq2.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/createcontact.php","", _rq2_request_listener);
            }
        });
    }
    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;
        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.contactlist, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }
        @Override
        public void onBindViewHolder(ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;
            final TextView textview1 = _view.findViewById(R.id.name);
            final ImageView imageview1 = _view.findViewById(R.id.phonelist);
            textview1.setText(_data.get((int)_position).get("name").toString());
            final ImageView delete = _view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog _alert = new AlertDialog.Builder(contacts.this).create();
                    _alert.setTitle("Delete Contact");
                    _alert.setMessage("Are you sure you want to delete this contact?");
                    _alert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rqmap3.put("contactid", _data.get((int)_position).get("id").toString());
                            rq3.setParams(rqmap3,0);
                            rq3.startRequestNetwork(RequestNetworkController.POST, getResources().getString(R.string.base_url)+"apis/deletecontact.php","", _rq3_request_listener);
                        }
                    });
                    _alert.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                    });
                    _alert.show();

                      }
            });
            imageview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contacts.this, phonelist.class);
                    intent.putExtra("id", _data.get((int)_position).get("id").toString());
                    intent.putExtra("ids", _data.get((int)_position).get("ids").toString());
                    startActivity(intent);
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
}
