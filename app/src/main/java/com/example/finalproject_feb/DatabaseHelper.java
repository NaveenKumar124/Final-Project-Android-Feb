package com.example.finalproject_feb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NAME = "notestables";
    public static final String COL1 = "ID";
    public static final String COL2 = "ADDRESS";
    public static final String COL3 = "LAT";
    public static final String COL4 = "LNG";
   



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT , ADDRESS VARCHAR(100) , LAT VARCHAR(100) , LNG VARCHAR(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String address , String lat , String lng , String visted){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2 , address );
        contentValues.put(COL3 , lat);
        contentValues.put(COL4 , lng);




       long result = db.insert(TABLE_NAME , null , contentValues);

            if(result == -1)
                return false;
            else
                return true;

    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return res;



    }
    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME , COL2 + "=?" , new String[]{name} );


    }
    public boolean updateData(String address , String lat , String lng  , String newAdress , String visited){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2 , newAdress );
        contentValues.put(COL3 , lat);
        contentValues.put(COL4 , lng);


        db.update(TABLE_NAME , contentValues , COL2 + "=?" , new String[]{address});
        return true;


    }



}
