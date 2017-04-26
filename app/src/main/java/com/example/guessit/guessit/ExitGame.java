package com.example.guessit.guessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by zhan1803 on 3/2/2017.
 */

public class ExitGame extends AppCompatActivity{

    private Button confirmExit;
    private Button backToGame;
    Map<String, Object> data = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        confirmExit = (Button)findViewById(R.id.confirmExit);
        backToGame = (Button)findViewById(R.id.backToGame);

        backToGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ExitGame.this, GamePage.class));
            }
        });

        confirmExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Player exits game", Toast.LENGTH_LONG).show();
                StartGameUsername.globalplayercount--;
                data.remove(Constants.playerName);

                exitSystem();
            }
        });
    }

    public void exitSystem(){
        finish();
        System.exit(0);
    }
}
