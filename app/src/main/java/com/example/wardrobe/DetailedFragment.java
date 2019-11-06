package com.example.wardrobe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.info.ClothesManager;


public class DetailedFragment extends Fragment {

    private Clothes mClothes;

    private TextView mIDText,mBrandText,mCategoryText,mSizeText,mColorText;



    public DetailedFragment(){};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int clothesID = (int) getActivity().getIntent().getIntExtra(DetailedActivity.EXTRA_CLOTHES_ID,-1);
        mClothes = ClothesManager.get(getActivity()).getClothes(clothesID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detailed, container, false);

        mIDText = (TextView) v.findViewById(R.id.detail_id);
        mBrandText = (TextView) v.findViewById(R.id.detail_brand);
        mColorText = (TextView) v.findViewById(R.id.detail_color);
        mSizeText = (TextView) v.findViewById(R.id.detail_size);
        mCategoryText = (TextView) v.findViewById(R.id.detail_category);

        String tmp = "ID : "+ mClothes.getID()+" .";
        mIDText.setText(tmp);
        tmp = "Brand : "+ mClothes.getBrand()+" .";
        mBrandText.setText(tmp);
        tmp = "Color : "+ mClothes.getColor()+" .";
        mColorText.setText(tmp);
        tmp = "Size : "+ mClothes.getSize()+" .";
        mSizeText.setText(tmp);
        tmp = "Category : "+ mClothes.getCategory()+" .";
        mCategoryText.setText(tmp);


        return v;
    }





}
