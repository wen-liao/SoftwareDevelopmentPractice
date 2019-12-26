package com.example.wardrobe;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wardrobe.network.imageService;

import com.example.wardrobe.info.User;
import com.example.wardrobe.network.netConnector;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private String TAG = "me";

    public MeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        MainActivity activity = (MainActivity)getActivity();
        TextView textView = view.findViewById(R.id.me_username);
        textView.setText(activity.getUsername());
        TextView textView1 = view.findViewById(R.id.me_nickname);
        textView1.setText(activity.getNickname());
        CircleImageView im = (CircleImageView) view.findViewById(R.id.profile_image);
        try{
        Bitmap a = imageService.getImage(activity.getUserIconURL());
        im.setImageBitmap(a);}
        catch (Exception e){
            e.printStackTrace();
        }

        final String username = activity.getUsername();

        Button logout = (Button) view.findViewById(R.id.me_signout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = signoutResult(username);
                if(result){
                    Toast.makeText(getActivity(),"注销成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), SigninActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(),"注销失败！",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    private boolean signoutResult(String un){
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", un);

            //connect to server
            response = new netConnector("authentication/sign_out", "POST", a).call();

            Log.e(TAG,response.toString());
            if(response != null) {
                String status = response.getString("status");

                Log.e(TAG,status);

                if(status.equals("000")) return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //todo: temporary for debug
        return  false;
    }
}
