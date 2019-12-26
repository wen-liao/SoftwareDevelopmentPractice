package com.example.wardrobe;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.info.ClothesManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {


    private String EXTRA_USERNAME = "wardrobefragment_username";
    private String EXTRA_SELECTED = "wardrobefragmen_curlist";
    private String EXTRA_DATE = "wardrobefragmen_date";



    private RecyclerView mRecyclerView;
    private ClothesAdapter mAdapter;
    ClothesManager cm;
    List<Clothes> wardrobe;
    private ArrayList li;





    private class ClothesHolder extends RecyclerView.ViewHolder {

        private Clothes mClothes;

        private ImageView im;
        public TextView mBrandTextView;
        public TextView mSizeTextView;

        public ClothesHolder(View itemView){
            super(itemView);
            im = itemView.findViewById(R.id.calendar_clothes_item);
            Log.e("calendar",im.toString());

        }

        public void bindClothes(Clothes clothes){
            Log.e("calendar",clothes.getBrand()+"brand");
            Log.e("calendar",clothes.getBitmap()+"bitmap");
            mClothes = clothes;
            im.setImageBitmap(mClothes.getBitmap());

        }

    }

    private class ClothesAdapter extends RecyclerView.Adapter<ClothesHolder>{

        private List<Clothes> mWardrobe;

        public ClothesAdapter(List<Clothes> wardrobe){
            mWardrobe = wardrobe;
        }

        @Override
        public ClothesHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_layout,parent,false);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        cm = ((MainActivity) (getParentFragment().getActivity())).mClothesManager;
        System.out.println(cm);

        wardrobe = cm.filteredWardrobe(new ArrayList());

        if(cm.isExtended() == false) {

            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.edit);
            Clothes extend = new Clothes(-1, -1,"tester", null, null, null, null, bitmap);
            wardrobe.add(extend);
            cm.setExtended(true);
        }
        View view = inflater.inflate(R.layout.fragment_wardrobe,container,false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.clothes_recycler_view);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position != wardrobe.size() -1)

                Toast.makeText(getContext(),wardrobe.get(position).getBrand(),Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getContext(),"enter edit mode",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(((MainActivity)getParentFragment().getActivity()), SelectPhotosActivity.class);

                    intent.putExtra(EXTRA_USERNAME, ((MainActivity)getParentFragment().getActivity()).getUsername());
                    intent.putExtra(EXTRA_SELECTED,li);

                    startActivity(intent);

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long "+wardrobe.get(position).getBrand(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }));

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(lm);

        updateUI();


        return view;
    }

    private int extendPlusIcon(){

        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.edit);
        Clothes extend = new Clothes(-1,-1,"tester",null,null,null,null,bitmap);
        wardrobe.add(extend);
        return 1;
    }

    private void updateUI(){


        mAdapter = new ClothesAdapter(wardrobe);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setData(ArrayList l){
        li = l;
        Log.e("calendar",String.valueOf(l.size())+"size");
        wardrobe = cm.filteredWardrobe(l);
        Log.e("calendar",String.valueOf(wardrobe.size()));
        cm.setExtended(false);

        extendPlusIcon();
        Log.e("calendar",String.valueOf(wardrobe.size()));
        updateUI();

    }

}


