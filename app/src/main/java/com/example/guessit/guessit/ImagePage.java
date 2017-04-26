package com.example.guessit.guessit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ImagePage extends AppCompatActivity {
    private ImageView imageView;
    private Uri file;
    public ImageView imageArray[] = new ImageView[8];
    public int count=0;
    private Integer[] Imgid = {R.id.imageButton17,R.id.imageButton18,R.id.imageButton19,R.id.imageButton24,R.id.imageButton25,R.id.imageButton27,R.id.imageButton26,R.id.imageButton28 };
    int isHintGiver = 0; // is not hint giver
    String hintGiverSocket = "";
    Bitmap bm;
    ArrayList<JSONObject> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        //Log.d("Does it?", Constants.imgSubmitted);
        imageList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        try {
            obj.put("gameId", Constants.gameId);
            obj.put("playerName", Constants.playerName);
            obj.put("timer", Constants.time);
            //obj.put("avatarName", Constants.avatarName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constants.socket.emit("getAllPlayers", obj);
        Constants.socket.on("newImageSubmitted", newImageSubmitted);
        Constants.socket.on("hintGiver", notifyHintGiver);

        //imageView = (ImageView)findViewById(R.id.img1);
        //file = Uri.fromFile(getOutputMediaFile());
        //imageView.setImageURI(file);
    }


    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String uniqueStamp = "guessITOfficial";
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ uniqueStamp + ".jpg");
    }

    public void goToWaitingPg(View view) {
        Intent intent = new Intent(this, HintActivity.class);
        startActivity(intent);
    }

    public Emitter.Listener notifyHintGiver = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            hintGiverSocket = (String)args[0];
        }
    };

    public Emitter.Listener newImageSubmitted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject) args[0];
            try {
                String img2 = obj.getString("imgSubmitted");
                String sender = obj.getString("senderSocket");
                //if(hintGiverSocket.equals(0)) {
                createImage(img2, sender);

                //}
            } catch (JSONException e) {
                Log.i("Exception2", e.getMessage());
                return;
            }
        }
    };

 public void createImage(final String image, final String sender) {
        //Get the iconid from avatar name to link it to drawable folder
        //final int iconId =  getResources().getIdentifier("drawable/" + avatarName, null,context.getPackageName());
        Log.d("Goes in", "method");
        //Log.d("IconId is: ", Integer.toString(iconId));

        runOnUiThread(new Runnable() {
            //       Log.d("Count is: ", Integer.toString(count));


            public void run(){
                imageArray[count] = (ImageView) findViewById(Imgid[count]);
                JSONObject imageObj = new JSONObject();
                try {
                    imageObj.put("imagId", Imgid[count]);
                    imageObj.put("senderSocket",sender );

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                imageList.add(imageObj);
                //file = Uri.fromFile(getOutputMediaFile());
                //imageView.setImageURI(file);
                final byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Log.d("Test COunt is", String.valueOf(count));
                bm = BitmapFactory.decodeByteArray(decodedString, 0 , decodedString.length);
                imageArray[count].setImageBitmap(bm);
                count++;
            }


            //imageView = (ImageView)findViewById(R.id.img1);
            //file = Uri.fromFile(getOutputMediaFile());
            //imageView.setImageURI(file);
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void whenHintGiverClicks0(View view) {
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 17
        JSONObject obj2 = imageList.get(0);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));

    }

    public void whenHintGiverClicks1(View view) {
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 18
        JSONObject obj2 = imageList.get(1);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));
    }

    public void whenHintGiverClicks2(View view) {

        // picture 19
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 18
        JSONObject obj2 = imageList.get(1);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));
    }

    public void whenHintGiverClicks3(View view) {
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 24
        JSONObject obj2 = imageList.get(3);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));
    }

    public void whenHintGiverClicks4(View view) {
        // picture 25
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket

        JSONObject obj2 = imageList.get(4);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));
    }

    public void whenHintGiverClicks5(View view) {
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 27
        JSONObject obj2 = imageList.get(5);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));

    }

    public void whenHintGiverClicks6(View view) {
        // it should go to a screen where they know who won
        // picture 26

        JSONObject obj2 = imageList.get(6);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));
    }

    public void whenHintGiverClicks7(View view) {
        // it should go to a screen where they know who won
        // pass in json object with gameID and winner socket
        // picture 28
        JSONObject obj2 = imageList.get(7);

        JSONObject obj = new JSONObject();

        try {
            obj.put("gameId", Constants.gameId);
            // winner socket
            obj.put("winnerSocket", obj2.getString("senderSocket"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("roundWinner", obj);
        startActivity(new Intent(ImagePage.this, MainActivity.class));

    }


}
