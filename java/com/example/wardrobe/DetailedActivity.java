package com.example.wardrobe;


import android.support.v4.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DetailedActivity extends SingleFragmentActivity {

    public static final String EXTRA_CLOTHES_ID = "com.example.wardrobe.clothesintent.clothes_id";

    public static Intent newIntent(Context packageContext, int clothesID){
        Intent intent = new Intent(packageContext, DetailedActivity.class);
        intent.putExtra(EXTRA_CLOTHES_ID, clothesID);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        return new DetailedFragment();
    }


}
