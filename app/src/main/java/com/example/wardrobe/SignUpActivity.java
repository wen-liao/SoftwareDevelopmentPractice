package com.example.wardrobe;


import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {



    private String username,passwd,nickname,md5passwd;

    private EditText usrnameInput,passwdInput,nicknameInput;
    private Button submitButton,returnButton;

    private String TAG = "sign_up";

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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        usrnameInput = (EditText) findViewById(R.id.sign_up_username);
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

        passwdInput = (EditText) findViewById(R.id.sign_up_password);
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

        nicknameInput = (EditText) findViewById(R.id.sign_up_nickname);
        nicknameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nickname = nicknameInput.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitButton = (Button) findViewById(R.id.sign_up_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SignUpActivity.this,"提交中..",Toast.LENGTH_SHORT).show();

                usrnameInput.setEnabled(false);
                passwdInput.setEnabled(false);
                nicknameInput.setEnabled(false);

                boolean result = signupResult(username,passwd,nickname);


                if(result){
                    Toast.makeText(SignUpActivity.this,"注册成功！请登录。",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), SigninActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(SignUpActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();

                    usrnameInput.setEnabled(true);
                    passwdInput.setEnabled(true);
                    nicknameInput.setEnabled(true);
                }



            }
        });

        returnButton = (Button) findViewById(R.id.sign_up_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean signupResult(String un, String pw,String nn){
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", username);
            a.put("password", passwd);
            a.put("nickname",nickname);

            //connect to server
            response = new netConnector("authentication/sign_up/", "POST", a).call();

            if(response != null) {
                String status = response.getString("status");
                Toast.makeText(SignUpActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();

                if(status.equals("000")) return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //todo: temporary for debug
        return  true;
    }


}
