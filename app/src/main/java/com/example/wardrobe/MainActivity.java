package com.example.wardrobe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.wardrobe.info.User;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

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
                case R.id.navigation_new:
                    replaceFragment(new NewFragment());
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
        setContentView(R.layout.activity_main);
        initializeUser();
        initializeView();
    }

    private void initializeUser(){
        user = new User("David","123456","123456@qq.com");
    }

    String getUserDescription(){
        String delimiter = (user.username == null || user.email == null )? "":", ";
        String description = new StringBuilder().append(user.username).append(delimiter).append(user.email).toString();
        return description;
    }

    private void initializeView(){
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(new WardrobeFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

}
