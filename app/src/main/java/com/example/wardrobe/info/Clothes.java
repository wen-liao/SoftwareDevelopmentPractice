package com.example.wardrobe.info;

import android.graphics.Bitmap;

import java.util.Date;

public class Clothes {
    private int mID;

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    private String mOwner;
    private int mUsageCount;
    private String mBrand;
    private String mCategory;
    private String mSize;
    private String mColor;
    private Bitmap mBitmap;

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }



    //String pic;
    Clothes(){
        setID(-1);
        setUsageCount(-1);
        setBrand("null");
        setCategory("null");
        setSize("null");
        setColor("null");
        setBitmap(null);}

    public Clothes(int ID, int usageCount, String owner,String brand, String category, String size,String color,Bitmap bm){
        setID(ID);
        setOwner(owner);
        setUsageCount(usageCount);
        setBrand(brand);
        setCategory(category);
        setSize(size);
        setColor(color);
        setBitmap(bm);
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getUsageCount() {
        return mUsageCount;
    }

    public void setUsageCount(int usageCount) {
        mUsageCount = usageCount;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }
}
