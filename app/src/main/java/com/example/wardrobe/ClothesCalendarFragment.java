package com.example.wardrobe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.wardrobe.network.netConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class ClothesCalendarFragment extends Fragment {



    public ClothesCalendarFragment() {
        // Required empty public constructor
    }

    String username;

    private FragmentManager fm;
    private WardrobeFragment wf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_clothes_calendar, container, false);


        MainActivity activity = (MainActivity)getActivity();

        username = activity.getUsername();

        CalendarView ca = (CalendarView) view.findViewById(R.id.calendar);

        fm = getChildFragmentManager();


        wf = (WardrobeFragment) fm.findFragmentById(R.id.headlines_fragment);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String selectedDate = String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(day);
        ArrayList clothesToDisplay = requestDateResult(selectedDate);
        wf.setData(clothesToDisplay);

        ca.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofmonth) {
                String selectedDate = String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(dayofmonth);
                Log.e("calendar","selected date: "+selectedDate);
                ArrayList clothesToDisplay = requestDateResult(selectedDate);
                wf.setData(clothesToDisplay);


            }
        });


        return view;

    }

    private  ArrayList requestDateResult(String selectedDate){
        ArrayList clothesIDList = new ArrayList();
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", username);
            a.put("date",selectedDate);

            //connect to server
            response = new netConnector("history_management/get_wear", "GET", a).call();

            //todo: debug issue
            response = new JSONObject();
            response.put("status","000");
            if(selectedDate.equals("2019/12/25")){
                JSONArray jsonArray=new JSONArray();
                jsonArray.put(1);
                jsonArray.put(2);

                response.put("clothesID",jsonArray);
            }
            else{
                JSONArray jsonArray=new JSONArray();


                response.put("clothesID",jsonArray);}

            Log.e("calendar","json built");

            if(response != null) {
                String status = response.getString("status");

                Log.e("calendar","status"+status);
                if(status.equals("000"))
                {
                    JSONArray clothesArray = response.getJSONArray("clothesID");

                    Log.e("calendar",String.valueOf(clothesArray.length()));
                    for(int j = 0;j<clothesArray.length();j++)
                    {
                        clothesIDList.add(clothesArray.getInt(j));
                    }
                    Log.e("calendar",String.valueOf(clothesIDList.size()));
                    return clothesIDList;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //todo
        return  null;
    }




}
