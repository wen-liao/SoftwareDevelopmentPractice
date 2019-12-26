package com.example.wardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wardrobe.info.User;
import com.example.wardrobe.network.netConnector;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "com.example.wardrobe.username";

    private User user;
    private static Logger logger = Logger.getLogger(MainActivity.class.getCanonicalName());


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wardrobe:
                    replaceFragment(new WardrobeFragment());
                    return true;
                case R.id.navigation_clothesList:
                    replaceFragment(new ClothesListFragment());
                    return true;
                case R.id.navigation_clothesCalendar:
                    replaceFragment(new ClothesCalendarFragment());
                    return true;
                case R.id.navigation_me:
                    replaceFragment(new MeFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initializeUser();
        initializeView();
    }

    private void initializeUser(){

        Bundle extras = getIntent().getExtras();
        String username = extras.getString(EXTRA_USERNAME);
        String iconpic = null;
        String nickname = null;
        //connect to server for user description
        //todo: further edit need
        //JSONObject response = null;
        JSONObject response = new JSONObject();
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", username);

            //todo: 可加机器序列码

            //connect to server
            //todo: convert hardcoded json to server response
            //response = new netConnector("authentication/user_info/", "GET", a).call();

            Log.e("user","json");
            response.put("nickname","tester");
            response.put("iconpic","https://img2.woyaogexing.com/2019/12/24/e931747093e64f1583d90cb8f911d8c3!400x400.jpeg");
            Log.e("user","json success");

            nickname = response.getString("nickname");
            Log.e("user","json nickname"+ " "+nickname);
            iconpic = response.getString("iconpic");
            /*
            if(response != null) {
                String nickname = response.getString("nickname");
                Toast.makeText(MainActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();

                if(status.equals("000")) return true;
            }*/

        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.e("user","nickname "+nickname);
        user = new User(username,nickname,iconpic);
    }

    String getUserDescription(){
        Log.e("user",user.username+" "+user.nickname);
        String delimiter = (user.username == null || user.nickname == null )? "":", ";
        String description = new StringBuilder().append(user.username).append(delimiter).append(user.nickname).toString();
        return description;
    }

    String getUsername(){
        return user.username;
    }
    String getNickname(){
        return user.nickname;
    }

    String getUserIconURL(){
        return user.icon;
    }

    private void initializeView(){
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);
        replaceFragment(new WardrobeFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }



}
