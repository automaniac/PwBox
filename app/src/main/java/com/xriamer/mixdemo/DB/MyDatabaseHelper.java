package com.xriamer.mixdemo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * MixDemo;
 * com.xriamer.mixdemo.db;
 * Created by Xriam on 12/22/2018;
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_PWBOX = "create table PwBox("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "account text, "
            + "password text)";

    private Context mContext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PWBOX);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
