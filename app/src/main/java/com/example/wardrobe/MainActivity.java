package com.example.wardrobe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView wardrobeView, newView, meView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wardrobeView = findViewById(R.id.wardrobe_view);
        newView = findViewById(R.id.new_view);
        meView = findViewById(R.id.me_view);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
