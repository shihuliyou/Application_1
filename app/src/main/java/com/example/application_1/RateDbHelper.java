// com/example/application_1/RateDbHelper.java
package com.example.application_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RateDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "exchange.db";
    private static final int DB_VERSION = 1;

    public RateDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表：汇率表
        String SQL_CREATE_RATES = "CREATE TABLE " + RateContract.RateEntry.TABLE_NAME + " ("
                + RateContract.RateEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RateContract.RateEntry.COLUMN_CODE + " TEXT, "
                + RateContract.RateEntry.COLUMN_NAME + " TEXT, "
                + RateContract.RateEntry.COLUMN_RATE + " REAL"
                + ");";
        db.execSQL(SQL_CREATE_RATES);

        // 建表：元数据表
        String SQL_CREATE_META = "CREATE TABLE " + RateContract.MetaEntry.TABLE_NAME + " ("
                + RateContract.MetaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RateContract.MetaEntry.COLUMN_KEY + " TEXT UNIQUE, "
                + RateContract.MetaEntry.COLUMN_VALUE + " TEXT"
                + ");";
        db.execSQL(SQL_CREATE_META);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 暂无升级策略
        db.execSQL("DROP TABLE IF EXISTS " + RateContract.RateEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RateContract.MetaEntry.TABLE_NAME);
        onCreate(db);
    }
}
