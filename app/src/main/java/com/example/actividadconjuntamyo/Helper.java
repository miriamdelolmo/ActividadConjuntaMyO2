package com.example.actividadconjuntamyo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TheFruitis.db";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS fruitis(_ID  integer PRIMARY KEY AUTOINCREMENT, name text, weight integer, type text, rotten boolean);";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS  TheFruitis";

    public Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL(SQL_DELETE_ENTRIES);
        //Se crea la nueva versión de la tabla
        db.execSQL(SQL_CREATE_ENTRIES);
    }
}
