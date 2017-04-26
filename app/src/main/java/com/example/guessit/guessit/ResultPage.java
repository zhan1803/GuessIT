package com.example.guessit.guessit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Base64;

import com.github.nkzawa.emitter.Emitter;

import java.io.ByteArrayOutputStream;

/**
 * Created by zhan1803, yu599 on 2/16/2017.
 */

public class ResultPage extends AppCompatActivity {

    Button finishResultButton;
    Button scoreResultButton;
    Button exitGameButton;
    Button uploadPicButton;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        finishResultButton = (Button) findViewById(R.id.finishResultButton);
        scoreResultButton = (Button) findViewById(R.id.scoreResultButton);
        exitGameButton = (Button) findViewById(R.id.exitGameButton2);
        uploadPicButton = (Button) findViewById(R.id.uploadPicButton);
        Constants.socket.on("correctImg", acceptCorrectImg);    // receive the decoded string of image

        finishResultButton.setEnabled(false);

        finishResultButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ResultPage.this, HintActivity.class));
            }
        });

        scoreResultButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ResultPage.this, ScoreTable.class));
            }
        });

        exitGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ResultPage.this, ExitGame.class));
            }
        });

        // Upload picture by hint giver from gallary if no one sent the correct picture
        uploadPicButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                // Encrypt image to base 64
                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String DecodedString = Base64.encodeToString(b, Base64.DEFAULT);

                // Store gameId and encrypted string to a JSONObject
                JSONObject obj = new JSONObject();
                try {
                    obj.put("gameId", Constants.gameId);
                    obj.put("decodedImg", DecodedString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Send the object to the server
                Constants.socket.emit("decodedImg", obj);

            }
            else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
    public Emitter.Listener acceptCorrectImg = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Get the encrypted string
            String imgBase64 = (String)args[0];
            // Broadcast the image to other users
            postImg(imgBase64);
        }
    };

    public void postImg (String img) {
        // Decode base64 image
        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // Display the image after the encrypted string is decoded
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finishResultButton.setEnabled(true);
                ImageView imageView = (ImageView) findViewById(R.id.correctImg);
                imageView.setImageBitmap(decodedByte);
            }
        });
    }

}
