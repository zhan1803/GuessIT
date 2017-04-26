package com.example.guessit.guessit;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static android.R.id.message;


public class JoinGameUsername extends AppCompatActivity {
    Button check;
    EditText name;
    boolean disableSubmit = true;
    Button submit;
    EditText secret;
    Players player;
    PlayerDBHandler pdb = new PlayerDBHandler(this);
    Map<String, Object> data = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.socket.on("error", errorHandler);
        Constants.socket.on("playerJoinedRoom", playerJoinedRoom);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game_username);
        check = (Button)findViewById(R.id.checkusername);
        name = (EditText)findViewById(R.id.username);
        submit = (Button)findViewById(R.id.submitcode);
        secret = (EditText)findViewById(R.id.secretcode);
        submit.setEnabled(false);

    }
    private Emitter.Listener playerJoinedRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String socketId;
            try {
                socketId = data.getString("socketId");
            } catch (JSONException e) {
               return;
            }

            setSocketId(socketId);
        }

    };

    private Emitter.Listener errorHandler = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            String message;

            try {
                message = data.getString("message");
            }catch (JSONException e) {
                return;
            }
            handleNoRoom(message);
        }

    };


    public void handleNoRoom(String msg) {
        Log.d("No Room:", msg);
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "The Room Does Not Exist", Toast.LENGTH_SHORT).show();
                submit.setEnabled(false);
            }
        });

        //error = true;
    }

    public void setSocketId(String sockId) {
        Constants.sockId = sockId;
        System.out.printf("Socket ID is %s",sockId);
    }
    public void checkUserName (View view){
        // Check if shorter than 1
        if (name.getText().toString().length() < 1) {
            Toast.makeText(getApplicationContext(), "Username too short", Toast.LENGTH_SHORT).show();
            submit.setEnabled(false);
        } else if (name.getText().toString().length() > 25) { // Check if longer than 25
            Toast.makeText(getApplicationContext(), "Username too long", Toast.LENGTH_SHORT).show();
            submit.setEnabled(false);
        } else if (!name.getText().toString().matches("[a-zA-Z0-9]*")) { // Check if alphanumeric
            Toast.makeText(getApplicationContext(), "Username must only be alphanumeric with no space", Toast.LENGTH_SHORT).show();
            submit.setEnabled(false);
        } else {
            Toast.makeText(getApplicationContext(), "Awesome username!", Toast.LENGTH_SHORT).show();
            Constants.playerName = name.getText().toString();
            //pdb.addPlayer(player);
            // Added player, so increment count
            Constants.gameId = secret.getText().toString();
            data.put("playerName", Constants.playerName);
            data.put("gameId" , Constants.gameId);
            JSONObject obj = new JSONObject(data);
            Constants.socket.emit("playerJoinGame", obj);
            submit.setEnabled(true);
        }
        // Check if in database table already

    }

    public void submitCode(View view) {
        startActivity(new Intent(this, ChooseFaction.class));

    }
}
