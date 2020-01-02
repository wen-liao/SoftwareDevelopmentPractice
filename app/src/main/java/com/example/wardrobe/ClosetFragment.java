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
public class ClosetFragment extends Fragment {


    private String EXTRA_USERNAME = "closetfragment_username";
    private String EXTRA_SELECTED = "closetfragmen_curlist";




    private RecyclerView[] mRecyclerView = new RecyclerView[4];
    private ClothesAdapter[] mAdapters = new ClothesAdapter[4];
    ClothesManager cm;
    List<Clothes> [] wardrobe = (List<Clothes>[]) new List[4];
    private ArrayList li;





    private class ClothesHolder extends RecyclerView.ViewHolder {

        private Clothes mClothes;

        private ImageView im;
        public TextView mBrandTextView;
        public TextView mSizeTextView;

        public ClothesHolder(View itemView){
            super(itemView);
            im = itemView.findViewById(R.id.closet_display_item_image);


        }

        public void bindClothes(Clothes clothes){

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
            View view = layoutInflater.inflate(R.layout.closet_display_item,parent,false);
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


        View view = inflater.inflate(R.layout.fragment_closet,container,false);






        cm = ((MainActivity) (getActivity())).mClothesManager;


        //外套
        wardrobe[0] = cm.filteredWardrobe("Category","外套");

        System.out.println(wardrobe[0].size());

        mRecyclerView[0] = (RecyclerView) view.findViewById(R.id.outfit_recycler_view);

        mRecyclerView[0].addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView[0], new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                    Toast.makeText(getContext(),wardrobe[0].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long "+wardrobe[0].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }
        }));

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView[0].setLayoutManager(lm);


        ClosetFragment.ClothesAdapter mAdapter = new ClosetFragment.ClothesAdapter(wardrobe[0]);
        mRecyclerView[0].setAdapter(mAdapter);

        //上装
        wardrobe[1] = cm.filteredWardrobe("Category","上装");



        mRecyclerView[1] = (RecyclerView) view.findViewById(R.id.upper_recycler_view);

        mRecyclerView[1].addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView[1], new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Toast.makeText(getContext(),wardrobe[1].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long "+wardrobe[1].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }
        }));


        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView[1].setLayoutManager(lm);

         mAdapter = new ClosetFragment.ClothesAdapter(wardrobe[1]);
        mRecyclerView[1].setAdapter(mAdapter);
        //裤装
        wardrobe[2] = cm.filteredWardrobe("Category","裤装");


        mRecyclerView[2] = (RecyclerView) view.findViewById(R.id.trouser_recycler_view);

        mRecyclerView[2].addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView[2], new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Toast.makeText(getContext(),wardrobe[2].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long "+wardrobe[2].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }
        }));


        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView[2].setLayoutManager(lm);

        mAdapter = new ClosetFragment.ClothesAdapter(wardrobe[2]);
        mRecyclerView[2].setAdapter(mAdapter);

        //鞋子

        wardrobe[3] = cm.filteredWardrobe("Category","鞋子");


        mRecyclerView[3] = (RecyclerView) view.findViewById(R.id.shoe_recycler_view);

        mRecyclerView[3].addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView[3], new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Toast.makeText(getContext(),wardrobe[3].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long "+wardrobe[3].get(position).getBrand(),Toast.LENGTH_SHORT).show();

            }
        }));


        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView[3].setLayoutManager(lm);

        mAdapter = new ClosetFragment.ClothesAdapter(wardrobe[3]);
        mRecyclerView[3].setAdapter(mAdapter);




        return view;
    }





}


