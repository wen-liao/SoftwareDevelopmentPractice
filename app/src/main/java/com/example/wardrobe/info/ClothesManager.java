package com.example.wardrobe.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Base64;
import android.util.Log;

import com.example.wardrobe.info.Clothes;
import com.example.wardrobe.network.imageService;

import org.json.JSONArray;
import org.json.JSONObject;

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
        loadClothes();
    }

    public ClothesManager(Context context,int a){
        mWardrobe = new ArrayList<>();
        loadClothes();
    }

    public void loadFromJSON(JSONArray li){

        for(int i = 0; i<li.length();i++){

//            "id_":<int>,
//            "owner":<string>,
//            "usage_count":<int>,
//            "brand":<string>,
//            "category":<string>,
//            "size":<string>
//            "color":<string>
//            "bitmap": <string>
try{
            JSONObject b = li.getJSONObject(i);
            int id = b.getInt("id");
            String owner = b.getString("owner");
            int usageCount = b.getInt("usage_count");
            String brand = b.getString("brand");
            String category = b.getString("category");

            String size = b.getString("size");
            String color = b.getString("color");
            Bitmap bm = StringToBitMap(b.getString("bitmap"));

            //Clothes(int ID, int usageCount, String owner,String brand, String category, String size,String color,Bitmap bm){
            Clothes tmp = new Clothes(id,usageCount,owner,brand,category,size,color,bm);
            addClothes(tmp);
}
catch (Exception e){
    e.printStackTrace();
}
        }
    }

    public void loadClothes(){
        Bitmap[] bitmap = new Bitmap[13];
        try{
             bitmap[0] = imageService.getImage("http://pic.51yuansu.com/pic3/cover/03/88/06/5c18217be3b36_610.jpg");//外套
            bitmap[1] = imageService.getImage("https://png.zhaoxi.net/upload/18/01/1617/a87d33f4a108ed240467c81f4986cee2.png");//外套
            bitmap[2] = imageService.getImage("https://cbu01.alicdn.com/img/ibank/2019/749/577/11207775947_108980591.400x400.jpg");//外套
            bitmap[3] = imageService.getImage("https://bpic.588ku.com//original_origin_min_pic/18/05/04/655234e080ef6b52afd5f34e440fea6f.jpg");//毛衣;
            bitmap[4] = imageService.getImage("https://cbu01.alicdn.com/img/ibank/2017/735/106/4521601537_1345834253.310x310.jpg");//衬衣;
            bitmap[5] = imageService.getImage("http://pic.51yuansu.com/pic3/cover/03/55/08/5bc806ecd9b1d_610.jpg");//衬衣;
            bitmap[6] = imageService.getImage("https://a.vpimg3.com/upload/merchandise/pdcvis/2018/07/09/22/813535ed-125e-4bab-bbc6-2ec47c2148e6.jpg");//毛衣;
            bitmap[7] = imageService.getImage("https://a.vpimg4.com/upload/merchandise/pdcvis/2018/09/11/171/f97cbb7b-a237-4a60-9b88-d03db32e0e9f.jpg");//裤子;
            bitmap[8] = imageService.getImage("https://img11.360buyimg.com/n1/s350x449_jfs/t23875/184/1415987210/150051/9606b86f/5b5eb2f2N4a0197f1.jpg!cc_350x449.jpg");//裤子;
            bitmap[9] = imageService.getImage("http://img.ewebweb.com/uploads/20190614/14/1560493510-VfKCociTLD.jpg");//裤子;
            bitmap[10] = imageService.getImage("http://pic.616pic.com/ys_img/00/07/48/CrQWMvpan6.jpg");//鞋子;
            bitmap[11] = imageService.getImage("https://fs1.shop123.com.tw/300276/upload/standard/29807list_picture_592384.jpeg");//鞋子;
            bitmap[12] = imageService.getImage("https://img.muji.net/img/item/4550182295658_1260.jpg");//鞋子;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //hardcoded for debugging
        Clothes[] source= {
                new Clothes(1,2,"tester","nike","外套","M","blue",bitmap[0]),
                new Clothes(2,5,"tester","adidas","外套","39","black and white",bitmap[1]),
                new Clothes(3,5,"tester","adidas","外套","39","black and white",bitmap[2]),
                new Clothes(4,5,"tester","a","上装","39","black and white",bitmap[3]),
                new Clothes(1008,5,"tester","ab","上装","39","black and white",bitmap[4]),
                new Clothes(1009,5,"tester","abc","上装","39","black and white",bitmap[5]),
                new Clothes(1008,5,"tester","ab","上装","39","black and white",bitmap[6]),
                new Clothes(1009,5,"tester","abc","裤装","39","black and white",bitmap[7]),
                new Clothes(1008,5,"tester","ab","裤装","39","black and white",bitmap[8]),
                new Clothes(1009,5,"tester","abc","裤装","39","black and white",bitmap[9]),
                new Clothes(1008,5,"tester","ab","鞋子","39","black and white",bitmap[10]),
                new Clothes(1009,5,"tester","abc","鞋子","39","black and white",bitmap[11]),
                new Clothes(1009,5,"tester","abc","鞋子","39","black and white",bitmap[12])

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

    private  Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
