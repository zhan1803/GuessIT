package com.example.guessit.guessit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HintActivity extends AppCompatActivity {

    // Global var. for ready & notification
    private AlertDialog.Builder alert_toHintGiver;
    private AlertDialog.Builder alert_setHint;
    private AlertDialog.Builder alert_hint;
    private AlertDialog.Builder set_time;
    private TextView gameId;
    static String hint = "";
    static int timeRound = 0;
    final Context context = this;
    public ImageView imageArray[] = new ImageView[8];
    public int count=0;
    private Integer[] Imgid = {R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16,R.id.imageView17,R.id.imageView18,R.id.imageView19 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        JSONObject obj = new JSONObject();
        try {
            obj.put("gameId", Constants.gameId);
            obj.put("avatarName", Constants.avatarName);
            obj.put("playerName", Constants.playerName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constants.socket.emit("getAllPlayers", obj);
        Constants.socket.on("playersInRoom", handleAllPlayers);
        Constants.socket.on("newPlayerEntered", newPlayerEntered);
        Constants.socket.on("startGame", startGame);
        Constants.socket.on("hintGiver", notifyHintGiver);
        Constants.socket.on("goToPicturePage", goToPicturePage);
        //Pop up notification if all players are ready
        gameId = (TextView) findViewById(R.id.textView16);
        String newGameIdString = gameId.getText().toString();
        newGameIdString += Constants.gameId;
        gameId.setText(newGameIdString);
//        button_toHintGiver = (Button) findViewById(R.id.button_toHintGiver);
//        button_toHintGiver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // create new Alert Dialog Builder
//                alert_toHintGiver = new AlertDialog.Builder(context);
//                alert_toHintGiver.setMessage("You are selected as the hint giver for this round.");
//                alert_toHintGiver.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //Toast.makeText(MainActivity.this, "You are the hint giver!", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                startActivity(new Intent(view.getContext(), GamePage.class));
//                alert_toHintGiver.show();
//
//            }
//        });


        // Set hint when the hint giver is notified

//        button_setHint = (Button) findViewById(R.id.button_setHint);
//        button_setHint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create new alert dialog builder called alert_setHint
//                alert_setHint = new AlertDialog.Builder(context);
//                alert_setHint.setMessage("Please enter a hint: ");
//                // create edittext box
//                final EditText input = new EditText(context);
//                // Choices for time selection
//                /*String[] timechoices = {"1 Min", "2 Min", "3 Min"};
//                alert_setHint.setSingleChoiceItems(timechoices, 3, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch(i) {
//                            case 1:
//                                Toast.makeText(getApplicationContext(), "1 Min", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 2:
//                                Toast.makeText(getApplicationContext(), "2 Min", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 3:
//                                Toast.makeText(getApplicationContext(), "3 Min", Toast.LENGTH_SHORT).show();
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                });
//                */
//
//                //alert_setHint.show();
//                final int maxLength = 20;
//                final int minLength = 1;
//
//                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength + 1)});
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//
//                alert_setHint.setView(input);
//
//                input.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if (s.length() < minLength || s.length() > maxLength)
//                            new AlertDialog.Builder(context).setTitle("The number of characters should be between 1 and 20")
//                                    .setPositiveButton(android.R.string.ok, null).show();
//                    }
//                });
//
//                //alert_setHint.setView(input)
//                alert_setHint.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        hint = input.getText().toString();
//                        Toast.makeText(context, "Hint set successfully!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alert_setHint.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                //alert_setHint.create();
//                alert_setHint.show();
//            }
//
//        });
//







        // Check hint

//        button_hint = (Button) findViewById(R.id.button_hint);
//        button_hint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //when this button is clicked, show the alert
//                alert_hint = new AlertDialog.Builder(context);
//                alert_hint.setTitle("Hint");
//                alert_hint.setMessage(hint);
//                alert_hint.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        //Toast.makeText(MainActivity.this, "You are the hint giver!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alert_hint.show();
//            }
//        });

    }

    public Emitter.Listener goToPicturePage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            goToPicture();
        }
    };

    public Emitter.Listener notifyHintGiver = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String hintGiverSocket = (String)args[0];
            makeHintToast(hintGiverSocket);
        }
    };

    public Emitter.Listener startGame = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            goToGamePage();
        }
    };

    public Emitter.Listener handleAllPlayers = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONArray obj = (JSONArray) args[0];
            String avatar;

            try {
                for(int i =0 ; i < obj.length();i++){
                    JSONObject singleObj = (JSONObject) obj.getJSONObject(i);
                    avatar = singleObj.getString("avatarName");
                    createAvatar(avatar);
                }

            } catch (JSONException e) {
                return;
            }
        }
    };

    public Emitter.Listener newPlayerEntered = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject) args[0];
            try {
                String avatarName = obj.getString("avatarName");
                Log.d("Avatar2 name is: ", avatarName);
                createAvatar(avatarName);
            } catch (JSONException e) {
                return;
            }
        }
    };

    public void makeHintToast(final String hintGiverSocket) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(hintGiverSocket.equals(Constants.sockId)) {
                    //Toast.makeText(getApplicationContext(), "You're selected as the hint giver", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    final EditText input = new EditText(context);
                    //builder.setTitle("You are selected as the Hint Giver");
                    input.setHint("Please enter a hint and select time for the next round");
                    layout.addView(input);
                    final Spinner time = new Spinner(context);
                    String[] timechoices = {"1 Min", "2 Min", "3 Min"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, timechoices);
                    time.setAdapter(adapter);
                    layout.addView(time);

                    final int maxLength = 20;
                    final int minLength = 1;
                    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength + 1)});
                    input.setInputType(InputType.TYPE_CLASS_TEXT);

                    input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() < minLength || s.length() > maxLength)
                                new AlertDialog.Builder(context).setTitle("The number of characters should be between 1 and 50")
                                        .setPositiveButton(android.R.string.ok, null).show();
                        }
                    });

                    builder.setView(layout);
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Constants.roundHint = input.getText().toString();
                            //startActivity(new Intent(HintActivity.this, ImagePage.class));

                            hint = input.getText().toString();
                            // Get time selected
                            String timeSelection = time.getSelectedItem().toString();

                            if (timeSelection == "1 Min") {
                                timeRound = 1;
                            } else if (timeSelection == "2 Min") {
                                timeRound = 2;
                            } else if (timeSelection == "3 Min") {
                                timeRound = 3;
                            }
                            Constants.time = timeRound;
                            Log.d("Time in hint is: ", String.valueOf(Constants.time));
                            Toast.makeText(context, "Hint and time set successfully!", Toast.LENGTH_SHORT).show();
                            Constants.socket.emit("startGame", Constants.gameId);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    //alert_setHint.creat
                    builder.show();
                }
            }
        });

    }

    public void goToGamePage() {
        Intent intent = new Intent(getBaseContext(), GamePage.class);
        Bundle bundle = new Bundle();

        bundle.putString("TIME", Integer.toString(timeRound));
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void createAvatar(String avatarName) {
        Log.d("Avatar name is: ", avatarName);
        //Get the iconid from avatar name to link it to drawable folder
        final int iconId =  getResources().getIdentifier("drawable/" + avatarName, null,context.getPackageName());
        Log.d("IconId is: ", Integer.toString(iconId));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Count is: ", Integer.toString(count));
                imageArray[count] = (ImageView) findViewById(Imgid[count]);
                imageArray[count].setImageResource(iconId);
                count++;
            }
        });
    }
    public void goToPicture() {startActivity(new Intent(this, ImagePage.class));}
    public void ready(View view) {
        Constants.socket.emit("ready", Constants.gameId);
        Button ready = (Button)findViewById(R.id.button3);
        ready.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
    }



}
