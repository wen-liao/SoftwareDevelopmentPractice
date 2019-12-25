package com.example.wardrobe;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.info.ClothesManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    ClothesManager cm;
    List<Clothes> wardrobe;




    private class ClothesHolder extends RecyclerView.ViewHolder {

        private Clothes mClothes;

        public TextView mBrandTextView;
        public TextView mSizeTextView;

        public ClothesHolder(View itemView){
            super(itemView);
        }

        public void bindClothes(Clothes clothes){
            mClothes = clothes;

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

        cm = ClothesManager.get(getActivity());
        wardrobe = cm.getWardrobe();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.clothes_recycler_view);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),wardrobe.get(position).getBrand(),Toast.LENGTH_SHORT).show();
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

    private void updateUI(){
        cm = ClothesManager.get(getActivity());
        wardrobe = cm.getWardrobe();

        mAdapter = new ClothesAdapter(wardrobe);
        mRecyclerView.setAdapter(mAdapter);
    }

}


