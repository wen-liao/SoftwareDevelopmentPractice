package com.example.wardrobe.info;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.example.wardrobe.info.Clothes;

import java.util.ArrayList;
import java.util.List;

public class ClothesManager {
    private static ClothesManager sClothesManager;
    private List<Clothes> mWardrobe;

    public static ClothesManager get(Context context){
        if(sClothesManager == null)
            sClothesManager = new ClothesManager(context);
        return sClothesManager;
    }

    private ClothesManager(Context context){
        mWardrobe = new ArrayList<>();
        loadClothes(0);
    }

    public void loadClothes(int userid){
        //hardcoded for debugging
        Clothes[] source= {
                new Clothes(102,2,"nike","hat","M","blue"),
                new Clothes(1003,5,"adidas","shoes","39","black and white"),
                new Clothes(1004,5,"adidas","shoes","39","black and white"),
                new Clothes(1006,5,"a","shoes","39","black and white"),
                new Clothes(1008,5,"ab","shoes","39","black and white"),
                new Clothes(1009,5,"abc","shoes","39","black and white")

        };

        for( Clothes a:source)
            addClothes(a);
    }

    public void addClothes(Clothes a){
        mWardrobe.add(a);
    }

    public List<Clothes> getWardrobe() {
        return mWardrobe;
    }

    public Clothes getClothes(int id){
        for(Clothes a:mWardrobe)
            if(a.getID() == id)
                return a;
        return null;
    }
    public List<Clothes> fiteredWardrobe(String constraintType, String constraintValue){

        List<Clothes> tmp = new ArrayList<>();
        switch (constraintType){
            case "ID":
            {
                for(Clothes a:mWardrobe){

                    if(a.getID() == Integer.parseInt(constraintValue))
                        tmp.add(a);
                }
                break;}

            case "Brand":
            {
                for(Clothes a:mWardrobe){
                    if(a.getBrand().equals(constraintValue))
                        tmp.add(a);
                }
                break;}
            case "Category":
            {
                for(Clothes a:mWardrobe){
                    if(a.getCategory().equals(constraintValue))
                        tmp.add(a);
                }
                break;}
            case "Color":
            {
                for(Clothes a:mWardrobe){

                    if(a.getColor().equals(constraintValue))
                        tmp.add(a);
                }
                break;}
            default:
                //to do
                break;

        }
        return tmp;
    }

}
