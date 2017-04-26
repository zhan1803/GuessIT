package com.example.guessit.guessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MonsterActivity extends AppCompatActivity {
    Map<String,Object> data= new HashMap<String,Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster);
    }

    public void goToWaitingPage1(View view) {
        Constants.avatarName = "monster0";
//        Log.d("Testing id", Integer.toString(view.getId()));
        //Emit data to server
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage2(View view) {
        Constants.avatarName = "monster2";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage3(View view) {
        Constants.avatarName = "monster8";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage4(View view) {
        Constants.avatarName = "monster4";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage5(View view) {
        Constants.avatarName = "monster3";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage6(View view) {
        Constants.avatarName = "monster5";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage7(View view) {
        Constants.avatarName = "monster6";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage8(View view) {
        Constants.avatarName = "monster9";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }

    public void emitData() {
        data.put("gameId", Constants.gameId);
        data.put("socketId", Constants.sockId);
        data.put("playerName", Constants.playerName);
        data.put("avatarName", Constants.avatarName);
        JSONObject obj = new JSONObject(data);
        Constants.socket.emit("putPlayer", obj);
    }

}
