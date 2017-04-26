package com.example.guessit.guessit;

/**
 * Created by Dennis Chia on 6/2/2017.
 */

public class Players {
    private int id;
    private int score;
    private String userName;
    private String faction;
    private int gameId;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Players(int id, int score, String userName, String faction, int gameId, String avatar) {
        this.id = id;
        this.score = score;
        this.userName = userName;
        this.faction = faction;
        this.gameId = gameId;
        this.avatar = avatar;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
