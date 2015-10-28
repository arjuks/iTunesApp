package com.example.arjun.inclass7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//enables to open database and upgrade it
public class DatabaseOpenHelper extends SQLiteOpenHelper{
    static final String DB_NAME = "apps.db";
    static final int DB_VERSION  = 9;


    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null , DB_VERSION); //creates instance of parent class

    }

    //if database does not exist, oncreate will be called
    @Override
    public void onCreate(SQLiteDatabase db) {
        NotesTable.onCreate(db);
    }

    //onupgrade will be called only if newversion is greater than oldversion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    NotesTable.onUpgrade(db,oldVersion,newVersion);
    }

}
