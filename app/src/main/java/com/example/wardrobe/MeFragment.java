package com.example.wardrobe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wardrobe.info.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    public MeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        MainActivity activity = (MainActivity)getActivity();
        TextView textView = view.findViewById(R.id.me_description);
        textView.setText(activity.getUserDescription());
        return view;
    }

}
