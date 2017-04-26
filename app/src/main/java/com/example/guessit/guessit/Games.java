package com.example.guessit.guessit;
import java.util.ArrayList;

/**
 * Created by Dennis Chia on 6/2/2017.
 */

public class Games {
    private int id;
    private int numPlayers = 0;
    private final int maxPlayers = 8;

    public Games(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNumPlayers(int num) {
        this.numPlayers = num;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public int getMaxPlayers(){
        return this.maxPlayers;
    }
}
