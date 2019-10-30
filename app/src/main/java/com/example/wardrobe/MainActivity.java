package com.example.wardrobe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wardrobe.info.User;

import org.w3c.dom.Text;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private TextView wardrobeView, newView;
    private LinearLayout meView;
    private User user;
    private static Logger logger = Logger.getLogger(MainActivity.class.getCanonicalName());

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wardrobe:
                    newView.setVisibility(View.GONE);
                    meView.setVisibility(View.GONE);
                    wardrobeView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_new:
                    wardrobeView.setVisibility(View.GONE);
                    meView.setVisibility(View.GONE);
                    newView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_me:
                    wardrobeView.setVisibility(View.GONE);
                    newView.setVisibility(View.GONE);
                    meView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    private void initializeUser(){
        user = new User("David","123456","123456@qq.com");
    }

    private void initializeView(){
        wardrobeView = findViewById(R.id.wardrobe_view);
        newView = findViewById(R.id.new_view);
        meView = findViewById(R.id.me_view);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUser();
        initializeView();
        TextView userDescription = findViewById(R.id.me_description);
        String delimiter = (user.username == null || user.email == null )? "":", ";
        String description = user.username + delimiter + user.email;
        userDescription.setText(description);
        logger.log(Level.INFO,description);
    }

}
