package com.example.wardrobe;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.info.ClothesManager;
import com.example.wardrobe.network.netConnector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectPhotosActivity extends AppCompatActivity {



    private String EXTRA_USERNAME = "wardrobefragment_username";
    private String EXTRA_SELECTED = "wardrobefragmen_curlist";

    private String EXTRA_DATE = "wardrobefragmen_date";



    private String date;
    private String username;
    private RecyclerView mRecyclerView;
    private ClothesAdapter mAdapter;
    ClothesManager cm;
    List<Clothes> wardrobe;
    ArrayList selected;





    private class ClothesHolder extends RecyclerView.ViewHolder {

        private Clothes mClothes;

        private ImageView im;

        public TextView mBrandTextView;
        public TextView mSizeTextView;

        public ClothesHolder(View itemView){
            super(itemView);
            im = itemView.findViewById(R.id.select_clothes_item);
            Log.e("calendar",im.toString());

        }

        public void bindClothes(Clothes clothes){
            Log.e("calendar",clothes.getBrand()+"brand");
            Log.e("calendar",clothes.getBitmap()+"bitmap");
            mClothes = clothes;
            im.setImageBitmap(mClothes.getBitmap());
            if(selected.contains(clothes.getID())) im.setAlpha(0.4f);

        }

    }

    private class ClothesAdapter extends RecyclerView.Adapter<ClothesHolder>{

        private List<Clothes> mWardrobe;

        public ClothesAdapter(List<Clothes> wardrobe){
            mWardrobe = wardrobe;
        }

        @Override
        public ClothesHolder onCreateViewHolder(ViewGroup parent, int viewType){

            View view = getLayoutInflater().inflate(R.layout.select_photo,parent,false);
            return new ClothesHolder(view);
        }

        @Override
        public void onBindViewHolder(ClothesHolder holder, int position){
            Clothes clothes = mWardrobe.get(position);
            holder.bindClothes(clothes);

        }

        @Override
        public int getItemCount(){
            return mWardrobe.size();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        cm = ClothesManager.get(this);
        wardrobe = cm.getWardrobe();

        Bundle extras = getIntent().getExtras();
        selected = extras.getIntegerArrayList(EXTRA_SELECTED);
        username = extras.getString(EXTRA_USERNAME);
        date = extras.getString(EXTRA_DATE);

        if(cm.isExtended() == false) {

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
            Clothes extend = new Clothes(-1, -1, null, null, null, null, bitmap);
            wardrobe.add(extend);
            cm.setExtended(true);
        }
        setContentView(R.layout.activity_select_photos);


        mRecyclerView = (RecyclerView) findViewById(R.id.photos_view);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position != wardrobe.size() -1){
                    Toast.makeText(getApplicationContext(),wardrobe.get(position).getBrand(),Toast.LENGTH_SHORT).show();
                    if(selected.contains(wardrobe.get(position).getID()))
                    {   Toast.makeText(getApplicationContext(),"deselected " +String.valueOf(position),Toast.LENGTH_SHORT).show();
                        view.findViewById(R.id.select_clothes_item).setAlpha(1);
                        Log.e("calendar","num"+String.valueOf(position));
                        selected.remove(wardrobe.get(position).getID());
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"selected " +String.valueOf(position),Toast.LENGTH_SHORT).show();
                        view.findViewById(R.id.select_clothes_item).setAlpha(0.4f);
                        selected.add(wardrobe.get(position).getID());
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"add clothes item",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"long "+wardrobe.get(position).getBrand(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }));

        GridLayoutManager lm = new GridLayoutManager(this,3);

        mRecyclerView.setLayoutManager(lm);


        Button m = (Button) findViewById(R.id.select_submit);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean result = postDateResult();
                if(result)
                {Toast.makeText(getApplicationContext(),"提交成功！",Toast.LENGTH_SHORT).show();
                finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"提交失败！",Toast.LENGTH_SHORT).show();

                }

            }
        });

        updateUI();

    }



    private void updateUI(){


        mAdapter = new ClothesAdapter(wardrobe);
        mRecyclerView.setAdapter(mAdapter);
    }

    public ArrayList getSelected(){

        return selected;
    }


    private  Boolean postDateResult(){
        ArrayList clothesIDList = new ArrayList();
        JSONObject response = null;
        //build json requests
        try {
            JSONObject a = new JSONObject();
            a.put("username", username);
            a.put("date",date);
            JSONArray tmp = new JSONArray(selected);
            a.put("clothesID",tmp);

            //connect to server
            response = new netConnector("history_management/set_wear", "POST", a).call();



            if(response != null) {
                String status = response.getString("status");

                Log.e("calendar","status"+status);
                if(status.equals("000"))
                {
                    return true;

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //todo
        return  false;
    }
}


