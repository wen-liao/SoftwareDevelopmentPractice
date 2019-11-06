package com.example.wardrobe;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.info.ClothesManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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



    private RecyclerView mRecyclerView;
    private ClothesAdapter mAdapter;




    private class ClothesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Clothes mClothes;

        public TextView mBrandTextView;
        public TextView mSizeTextView;

        public ClothesHolder(View itemView){
            super(itemView);
            mBrandTextView = (TextView) itemView.findViewById(R.id.list_item_brand);
            mSizeTextView = (TextView) itemView.findViewById(R.id.list_item_size);

            itemView.setOnClickListener(this);
        }

        public void bindClothes(Clothes clothes){
            mClothes = clothes;

            mBrandTextView.setText(mClothes.getBrand());
            mSizeTextView.setText(mClothes.getSize());
        }

        @Override
        public void onClick(View v){
            //Toast.makeText(getActivity(),mClothes.getID() + " Clicked! ",Toast.LENGTH_SHORT).show();
            //Intent intent = DetailedActivity.newIntent(getActivity(),mClothes.getID());
            //startActivity(intent);
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


        View view = inflater.inflate(R.layout.fragment_wardrobe,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.clothes_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        updateUI();


        return view;
    }

    private void updateUI(){
        ClothesManager cm = ClothesManager.get(getActivity());
        List<Clothes> wardrobe = cm.getWardrobe();

        mAdapter = new ClothesAdapter(wardrobe);
        mRecyclerView.setAdapter(mAdapter);
    }

}


