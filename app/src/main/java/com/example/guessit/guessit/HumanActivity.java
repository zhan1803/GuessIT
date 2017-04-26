package com.example.guessit.guessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HumanActivity extends AppCompatActivity {
    Map<String,Object> data= new HashMap<String,Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human);
    }

    public void goToGamePage(View view) {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);
    }

    public void goToWaitingPage1(View view) {
        Constants.avatarName = "hero8";
//        Log.d("Testing id", Integer.toString(view.getId()));
        //Emit data to server
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage2(View view) {
        Constants.avatarName = "hero7";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage3(View view) {
        Constants.avatarName = "hero4";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage4(View view) {
        Constants.avatarName = "hero6";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage5(View view) {
        Constants.avatarName = "hero5";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage6(View view) {
        Constants.avatarName = "hero3";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage7(View view) {
        Constants.avatarName = "hero2";
//        Log.d("Testing id", Integer.toString(view.getId()));
        emitData();
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }
    public void goToWaitingPage8(View view) {
        Constants.avatarName = "hero1";
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
