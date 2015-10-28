package com.example.arjun.inclass7;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arjun on 10/11/2015.
 */
public class NoteDAO {
    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db){
        this.db = db;
    }

    public long save(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_APPNAME, note.getAppName());
        values.put(NotesTable.COLUMN_DEVNAME, note.getDevName());
        values.put(NotesTable.COLUMN_DATE, note.getDate());
        values.put(NotesTable.COLUMN_PRICE, note.getPrice());
        values.put(NotesTable.COLUMN_CATEGORY, note.getCategory());
        values.put(NotesTable.COLUMN_IMGURL, note.getImgurl());
        values.put(NotesTable.COLUMN_ISFAV, note.getIsfav());

        return db.insert(NotesTable.TABLENAME,null, values);
    }

    public boolean update(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_APPNAME, note.getAppName());
        values.put(NotesTable.COLUMN_DEVNAME, note.getDevName());
        values.put(NotesTable.COLUMN_DATE, note.getDate());
        values.put(NotesTable.COLUMN_PRICE, note.getPrice());
        values.put(NotesTable.COLUMN_CATEGORY, note.getCategory());
        values.put(NotesTable.COLUMN_IMGURL, note.getImgurl());
        values.put(NotesTable.COLUMN_ISFAV, note.getIsfav());

        return db.update(NotesTable.TABLENAME, values, NotesTable.COLUMN_ID + "=?", new String[]{note.get_id() + ""}) > 0;

    }

    public boolean delete(Note note) {
        return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_ID + "=?", new String[]{note.get_id() + ""}) > 0;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Note get(long id) {
        Note note = null;

        //cursor points to location before entry
        Cursor c = db.query(true, NotesTable.TABLENAME,
                new String[]{
                        NotesTable.COLUMN_ID,
                        NotesTable.COLUMN_APPNAME,
                        NotesTable.COLUMN_DEVNAME,
                        NotesTable.COLUMN_DATE,
                        NotesTable.COLUMN_PRICE,
                        NotesTable.COLUMN_CATEGORY,
                        NotesTable.COLUMN_IMGURL,
                NotesTable.COLUMN_ISFAV},
                NotesTable.COLUMN_ID+ "=?", new String[]{id+ ""},null,null,null,null,null);

        if(c != null && c.moveToFirst()) {
            note = buildNoteFromCursor(c);
            if(!c.isClosed()) {
                c.close();
            }
        }

        return null;
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<Note>();
        Cursor c = db.query(
                NotesTable.TABLENAME,new String[]{
                        NotesTable.COLUMN_ID,
                        NotesTable.COLUMN_APPNAME,
                        NotesTable.COLUMN_DEVNAME,
                        NotesTable.COLUMN_DATE,
                        NotesTable.COLUMN_PRICE,
                        NotesTable.COLUMN_CATEGORY,
                        NotesTable.COLUMN_IMGURL,
                NotesTable.COLUMN_ISFAV},
                null, null, null, null, null);

        //check to see if cursor points to first entry
        if(c != null && c.moveToFirst()) {
            do {
            Note note = buildNoteFromCursor(c);
                if(note != null) {
                    notes.add(note);
                }

            }while(c.moveToNext());
            if(!c.isClosed()) {
                c.close();
            }
        }
        return notes;
    }

    private Note buildNoteFromCursor(Cursor c) {
        Note note = null;
        if(c != null) {
            note = new Note();
            note.set_id(c.getLong(0));
            note.setAppName(c.getString(1));
            note.setDevName(c.getString(2));
            note.setDate(c.getString(3));
            note.setPrice(c.getString(4));
            note.setCategory(c.getString(5));
            note.setImgurl(c.getString(6));
            note.setIsfav(c.getString(7));
        }
        return note;
    }
}
