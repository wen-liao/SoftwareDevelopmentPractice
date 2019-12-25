package com.example.wardrobe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.wardrobe.network.netConnector;

import org.json.JSONObject;

import java.util.Calendar;


public class ClothesCalendarFragment extends Fragment {



    public ClothesCalendarFragment() {
        // Required empty public constructor
    }

    String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_clothes_calendar, container, false);

        MainActivity activity = (MainActivity)getActivity();

        username = activity.getUsername();

        CalendarView ca = (CalendarView) view.findViewById(R.id.calendar);




        ca.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofmonth) {
                String selectedDate = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayofmonth);
                Log.e("calendar","selected date: "+selectedDate);

            }
        });


        return view;

    }





}
