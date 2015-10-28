package com.example.arjun.inclass7;

import android.database.sqlite.SQLiteDatabase;


// this takes care of creation and upgrading of table
public class NotesTable {
    static final String TABLENAME = "Apps";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_APPNAME = "appName";
    static final String COLUMN_DEVNAME = "devName";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_CATEGORY = "category";
    static final String COLUMN_IMGURL = "imgUrl";
    static final String COLUMN_ISFAV = "isfav";



    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME+"(");
        sb.append(COLUMN_ID+" "+" integer primary key autoincrement,");
        sb.append(COLUMN_APPNAME+" text not null, ");
        sb.append(COLUMN_DEVNAME+" text not null, ");
        sb.append(COLUMN_DATE+" text not null, ");
        sb.append(COLUMN_PRICE+" text not null, ");
        sb.append(COLUMN_CATEGORY+" text not null, ");
        sb.append(COLUMN_IMGURL+ " text not null,");
        sb.append(COLUMN_ISFAV+ " text not null);");

        db.execSQL(sb.toString());
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        NotesTable.onCreate(db);
    }

}
