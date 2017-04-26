package com.example.guessit.guessit;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Dennis Chia on 6/2/2017.
 */

public class PlayerDBHandler extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "playerInfo";

    //Contacts Table name
    private static final String TABLE_PLAYERS = "players";

    //GameInfo Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_GAMEID = "gameId";
    private static final String KEY_FACTION = "faction";
    private static final String KEY_SCORE = "score";
    private static final String KEY_AVATAR = "avatar";


    public PlayerDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SCORE + " INTEGER,"
                + KEY_USERNAME + " STRING,"
                + KEY_FACTION + " STRING,"
                + KEY_GAMEID + " INTEGER,"
                + KEY_AVATAR + " STRING)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PLAYERS);
        // Creating tables again
        onCreate(db);

    }

    // Adding / Creating a new player
    public void addPlayer(Players player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_ID, player.getId()); //Player Id
        value.put(KEY_SCORE, player.getScore());    //Score of that player
        value.put(KEY_USERNAME,player.getUserName());     // UserName of a player
        value.put(KEY_FACTION, player.getFaction()); //Faction of that player
        value.put(KEY_GAMEID, player.getGameId());     // Game ID
        value.put(KEY_AVATAR,player.getAvatar());     // Avatar possibly link to an img
        db.insert(TABLE_PLAYERS, null, value);
        db.close(); //Close database connection
    }

    //Getting a player
    public Players getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYERS, new String[] { KEY_ID,KEY_SCORE,KEY_USERNAME,KEY_FACTION, KEY_GAMEID , KEY_AVATAR}, KEY_ID + "=?",
                new String[] { String.valueOf(id)}, null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Players player = new Players(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),cursor.getString(5));
        //return player
        return player;
    }

    //get all players
    public List<Players> getAllPlayers() {
        List<Players> playerList = new ArrayList<Players>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        // loop through the result and add to the arraylist
        if (cursor.moveToFirst()) {
            do {
                Players player = new Players(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),cursor.getString(5));
                //Add players into playerList
                playerList.add(player);
            } while ( cursor.moveToNext());
        }
        //Return the playerList
        return playerList;
    }

    //Get player count
    public int getGamesCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        //return count
        return cursor.getCount();
    }

    //Updating Player
    public int updateGame(Players player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, player.getId());
        values.put(KEY_SCORE, player.getScore());
        //update row
        return db.update(TABLE_PLAYERS, values, KEY_ID + " = ?", new String[]{String.valueOf(player.getId())});
    }

    //Delete a player
    public void deletePlayer(Players player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS,KEY_ID+ " = ?", new String[]{String.valueOf(player.getId())});
        db.close();
    }
}
