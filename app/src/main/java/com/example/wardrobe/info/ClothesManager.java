package com.example.wardrobe.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Log;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.network.imageService;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClothesManager {
    private static ClothesManager sClothesManager;
    private List<Clothes> mWardrobe;

    public boolean isExtended() {
        return mExtended;
    }

    public void setExtended(boolean extended) {
        mExtended = extended;
    }

    private boolean mExtended = false;

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
        Bitmap bitmap = null;
        try{
             bitmap = imageService.getImage("http://pic.90sjimg.com/design/00/07/85/23/5a05427d0d499.png");}
        catch (Exception e){
            e.printStackTrace();
        }
        //hardcoded for debugging
        Clothes[] source= {
                new Clothes(1,2,"nike","hat","M","blue",bitmap),
                new Clothes(2,5,"adidas","shoes","39","black and white",bitmap),
                new Clothes(3,5,"adidas","shoes","39","black and white",bitmap),
                new Clothes(4,5,"a","shoes","39","black and white",bitmap),
                new Clothes(1008,5,"ab","shoes","39","black and white",bitmap),
                new Clothes(1009,5,"abc","shoes","39","black and white",bitmap)

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
    public List<Clothes> filteredWardrobe(String constraintType, String constraintValue){

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

    public List<Clothes> filteredWardrobe(ArrayList idList){

        List<Clothes> tmp = new ArrayList<>();

        Collections.sort(idList);

        for(Object a:idList){
            if((int) a > mWardrobe.size())
            {
                Log.e("debug","wrong clothes id");
            }

            tmp.add(mWardrobe.get((int) a));
        }

        return tmp;
    }

}
