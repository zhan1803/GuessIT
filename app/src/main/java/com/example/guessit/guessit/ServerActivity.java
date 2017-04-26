

        package com.example.guessit.guessit;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.github.nkzawa.socketio.client.IO;
        import com.github.nkzawa.socketio.client.Socket;
        import com.github.nkzawa.emitter.Emitter;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.net.URISyntaxException;

public class ServerActivity extends AppCompatActivity {

    private Socket socket;
    {
        try {
            socket = IO.socket(Constants.url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        socket.on("newGameCreated", newGameCreated);
        socket.connect();
    }

    public void create(View view) {
        socket.emit("hostCreateNewGame");

    }


    private Emitter.Listener newGameCreated = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            String gameId;
            String socketId;

            try {
                gameId = data.getString("gameId");
                socketId = data.getString("mySocketId");
            }catch (JSONException e) {
                return;
            }
            addGameIdToView(gameId, socketId);
        }

    };

    public void printMessage(String msg) {
        Log.d("message is: ", msg);
    };

    public void addGameIdToView(String gameId, String socketId) {
        Log.d("game id is ",gameId);
        Log.d("socket id is ", socketId);
    }

}