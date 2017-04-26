package com.example.guessit.guessit;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by zhan1803 on 2/7/2017.
 */
public class HintPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_hint_page);

        DisplayMetrics hintPageDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(hintPageDisplayMetrics);

        int tableWidth = hintPageDisplayMetrics.widthPixels;
        int tableHeight = hintPageDisplayMetrics.heightPixels;

        getWindow().setLayout((int)(tableWidth*0.85), (int)(tableHeight*.5));
    }
}
