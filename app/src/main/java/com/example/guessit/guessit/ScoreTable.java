package com.example.guessit.guessit;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by zhan1803 on 2/7/2017.
 */
public class ScoreTable extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_table);

        DisplayMetrics scoreTableDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(scoreTableDisplayMetrics);

        int tableWidth = scoreTableDisplayMetrics.widthPixels;
        int tableHeight = scoreTableDisplayMetrics.heightPixels;

        getWindow().setLayout((int)(tableWidth*0.9), (int)(tableHeight*.75));
    }
}
