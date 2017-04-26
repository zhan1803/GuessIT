package com.example.guessit.guessit;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Dennis Chia on 8/2/2017.
 */

public final class Constants {
    public static final String url="https://guessit-cs408.herokuapp.com/";
    public static final String testUrl="http://10.186.4.183:8080";
    public static String gameId;
    public static String sockId;
    public static Socket socket;
    public static String playerName;
    public static String avatarName;
    public static String roundHint;
    public static String imgSubmitted;
    public static int time;
}
