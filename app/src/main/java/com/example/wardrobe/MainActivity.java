package com.example.wardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.wardrobe.info.ClothesManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wardrobe.info.User;
import com.example.wardrobe.network.netConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "com.example.wardrobe.signin.username";
    public static final String EXTRA_PASSWORD = "com.example.wardrobe.signin.password";

    private User user;
    private static Logger logger = Logger.getLogger(MainActivity.class.getCanonicalName());

    public ClothesManager mClothesManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wardrobe:
                    replaceFragment(new ClosetFragment());
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
        updateCMdebug();
        initializeUser();
        initializeView();
    }

    private void initializeUser(){

        Bundle extras = getIntent().getExtras();
        String username = extras.getString(EXTRA_USERNAME);
        Bitmap iconpic = null;


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

            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.nullpic);

            response.put("iconpic",BitMapToString(bitmap));


            iconpic = StringToBitMap(response.getString("iconpic"));
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


        user = new User(username,iconpic);
        Log.e("calendar","user+ "+username);
    }



    String getUsername(){
        return user.username;
    }


    Bitmap getUserIcon(){
        return user.icon;
    }

    private void initializeView(){
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);
        replaceFragment(new ClosetFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void updateCMfromServer(JSONArray li){

        mClothesManager.loadFromJSON(li);


    }
    public void updateCMdebug(){
        mClothesManager = new ClothesManager(getApplicationContext(),1);
    }


    private JSONArray getFullClothesResult(){
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", user.username);



            //connect to server
            response = new netConnector("clothes_management/get_clothes", "GET", a).call();

            if(response != null) {
                String status = response.getString("status");
                //Toast.makeText(SignUpActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();

                if(status.equals("000")) return response.getJSONObject("data").getJSONArray("clothes");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }



}
