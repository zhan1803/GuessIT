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

public class GameDBHandler extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "gameInfo";

    //Contacts Table name
    private static final String TABLE_GAMES = "games";

    //GameInfo Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_NUMPLAYER = "NumPlayers";

    public GameDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_GAMES + "("
                                + KEY_ID + " INTEGER PRIMARY KEY,"
                                + KEY_NUMPLAYER + " INTEGERZ)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_GAMES);
        // Creating tables again
        onCreate(db);

    }

    // Adding / Creating a new game
    // *game is created initially with 0 players. Make sure to increment num players
    public void createGame(Games game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_ID, game.getId()); //Game Id
        value.put(KEY_NUMPLAYER,0);     // Number of player is initialized to 0
        db.insert(TABLE_GAMES, null, value);
        db.close(); //Close database connection
    }

    //getting one game
    public Games getGame(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GAMES, new String[] { KEY_ID, KEY_NUMPLAYER}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Games game = new Games(Integer.parseInt(cursor.getString(0)));
        //return game
        return game;
    }

    //get all games
    public List<Games> getAllGames() {
        List<Games> gameList = new ArrayList<Games>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        // loop through the result and add to the arraylist
        if (cursor.moveToFirst()) {
            do {
                Games game = new Games(Integer.parseInt(cursor.getString(0)));
                //Add game to gameList
                gameList.add(game);
            } while ( cursor.moveToNext());
        }
        //Return the game List
        return gameList;
    }

    //Get game count (No of Rooms);
    public int getGamesCount() {
        String countQuery = "SELECT * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        //return count
        return cursor.getCount();
    }

    //Updating Games
    public int updateGame(Games game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, game.getId());
        values.put(KEY_NUMPLAYER, game.getNumPlayers());
        //update row
        return db.update(TABLE_GAMES, values, KEY_ID + " = ?", new String[]{String.valueOf(game.getId())});
    }

    //Delete a game
    public void deleteGame(Games game) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMES,KEY_ID+ " = ?", new String[]{String.valueOf(game.getId())});
        db.close();
    }
}




