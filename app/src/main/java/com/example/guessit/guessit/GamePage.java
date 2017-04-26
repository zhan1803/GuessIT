package com.example.guessit.guessit;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Url;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;





public class GamePage extends AppCompatActivity {
    Map<String,Object> data= new HashMap<String,Object>();
    Button takePic;
    Button viewPic;
    Button testResult;
    Button exitGame;
    Button confirmImg;
    static String path; // file path

    private Uri file;
    private ImageView imageView;
    Button viewScoreTableButton;
    Button viewHintButton;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    public TextView timerView;
    private static final String FORMAT = "%02d:%02d";
    int startTime;
    private long interval = 1000;
    private AlertDialog.Builder alert_hint;

    final Context context = this;
    public int count=0;
    private ImageView avatar;
    byte[] ba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.socket.on("timerIs", setTimer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        takePic = (Button)findViewById(R.id.takeimg);
        confirmImg = (Button) findViewById(R.id.confirmimg);
        viewPic = (Button)findViewById(R.id.confirmimg);
        testResult = (Button)findViewById(R.id.testResult);
        exitGame = (Button)findViewById(R.id.exitGameButton);
        imageView = (ImageView)findViewById(R.id.imageview);
        viewHintButton = (Button)findViewById(R.id.viewHintButton);
        viewScoreTableButton = (Button)findViewById(R.id.viewScoreButton);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Constants.socket.on("winner", winner);
        viewScoreTableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(GamePage.this, ScoreTable.class));
            }
        });

        viewHintButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alert_hint = new AlertDialog.Builder(GamePage.this);
                alert_hint.setTitle("Hint");
                alert_hint.setMessage(HintActivity.hint);
                alert_hint.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                alert_hint.show();
            }
        });

        testResult.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(GamePage.this, ResultPage.class));
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(GamePage.this, ExitGame.class));
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePic.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }


        timerView = (TextView) findViewById(R.id.countDownTimer);

    }

    public Emitter.Listener setTimer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            int timer = (Integer) args[0];
            Constants.time = timer;
            startTime = timer * 1000 * 60;
            //Countdown timer  + Progress Bar
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    countDownTimer = new CountDownTimer(startTime, interval) {
                        //final  int time = startTime;
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int progress = (int)(millisUntilFinished/1000);
                            progressBar.setProgress(progressBar.getMax()-progress);

                            // Need code for written time
                            timerView.setText(""+String.format(FORMAT,
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                        }

                        @Override
                        public void onFinish() {
                            //Log.d("Time", "Up!");

                            //timerView.setText("You are out of time!");
                            // When done, it should go to image page.
//                View rootview = getWindow().getDecorView().getRootView();
//                confirmImage(rootview);
                            goToImagePage();
//                timerView.setText("Done");
                            // When done, it should go to image page.

                        }
                    };
                    countDownTimer.start(); // Start the timer
                }
            });

        }
    };
    public void goToImagePage() {

        countDownTimer.cancel();
     //   startActivity(new Intent(this, ImagePage.class));
    }
    // Results after taking the picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file); // set the picture on the view using the uri
            }
        }
    }

    // Function upload to server
    private void upload() {
        //Log.d("Filepath", path);
        // Decodes a file path into bitmap
        Bitmap bm = BitmapFactory.decodeFile(path);
        // Create an output stream in which the data is written into a byte array
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        // Write a compressed version of the bitmap to the outputstream
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        // create a byte array ba from the output stream
        ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        Constants.imgSubmitted = ba1;
        // execute in background?
    }

    // Take the image, get the outputmedia file, and store it in that file
    public void takeImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    // Upload the image to the server and go to image page
    public void confirmImage(View view) {
        upload();
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("gameId", Constants.gameId);
            obj2.put("imgSubmitted", Constants.imgSubmitted);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constants.socket.emit("sendImg", obj2);
        confirmImg.setEnabled(false);
        //Intent intent = new Intent(GamePage.this, ImagePage.class);
        //startActivity(intent);

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

        String uniqueStamp = "guessITOfficial";
        path = mediaStorageDir.getPath() + File.separator + "IMG_guessITOfficial.jpg";
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ uniqueStamp + ".jpg");
    }


    @Override
    public void onBackPressed() {
    }


    public Emitter.Listener winner = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject)args[0];
            String winnerName = "";
            try {
                winnerName = obj.getString("playerName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            announceWinner(winnerName);

        }
    };

    public void announceWinner(final String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView winnerName = new TextView(context);
                //winnerName.setText("Winner is : " + winnerName + "!\n");
                Toast.makeText(getApplicationContext(),"Winner is : " + name + "!\n" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GamePage.this, MainActivity.class));

            }
        });

    }

}

