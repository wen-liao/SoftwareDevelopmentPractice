package com.example.wardrobe;


import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wardrobe.network.netConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SigninActivity extends AppCompatActivity {

    private String username,passwd,md5passwd;
    private EditText usrnameInput,passwdInput;
    private Button loginButton;

    private String TAG = "SigninActivtity";

    public static final boolean DEVELOPER_MODE = BuildConfig.DEBUG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        usrnameInput = (EditText) findViewById(R.id.username);
        usrnameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                username = usrnameInput.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwdInput = (EditText) findViewById(R.id.password);
        passwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwd = passwdInput.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = loginResult(username,passwd);


            }
        });

    }

    private boolean loginResult(String un, String pw){
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", username);
            a.put("password", passwd);
            //connect to server
            response = new netConnector("authentication/sign_in/", "POST", a).call();

            if(response != null) {
                String status = response.getString("status");
                Toast.makeText(SigninActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                if(status == "000") return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }


}
