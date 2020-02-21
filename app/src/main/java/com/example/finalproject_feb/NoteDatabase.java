package com.example.finalproject_feb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notedbs";
    private static final String DATABASE_TABLE = "notestables";

    //Columns names for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content ";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_LOCATION = "loc";


    public NoteDatabase(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table
        String createDb = "CREATE TABLE "+DATABASE_TABLE+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT,"+
                KEY_LOCATION+" TEXT"
                +" )";
        db.execSQL(createDb);

    }


    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);

    }

    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_TITLE,note.getTitle());
        v.put(KEY_CONTENT,note.getContent());
        v.put(KEY_DATE,note.getDate());
        v.put(KEY_TIME,note.getTime());
        v.put(KEY_LOCATION,note.getLoc());

        // inserting data into db
        long ID = db.insert(DATABASE_TABLE,null,v);
        return  ID;
    }

    public Note getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME,KEY_LOCATION};
        Cursor cursor=  db.query(DATABASE_TABLE,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
    }

    public List<Note> getNotes(){

        SQLiteDatabase db = this.getWritableDatabase();

        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE+" ORDER BY "+KEY_ID+" DESC";


        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                note.setLoc(cursor.getString(5));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }

        return allNotes;

    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getId());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        c.put(KEY_LOCATION,note.getLoc());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }



    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
