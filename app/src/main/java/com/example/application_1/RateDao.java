// com/example/application_1/RateDao.java
package com.example.application_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateDao {
    private final RateDbHelper dbHelper;
    private static final String KEY_LAST_UPDATE = "last_update_date";

    public RateDao(Context ctx) {
        dbHelper = new RateDbHelper(ctx);
    }

    public void saveRates(List<Rate> rates) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(RateContract.RateEntry.TABLE_NAME, null, null);
            for (Rate r : rates) {
                ContentValues cv = new ContentValues();
                cv.put(RateContract.RateEntry.COLUMN_CODE, r.getCode());
                cv.put(RateContract.RateEntry.COLUMN_NAME, r.getName());
                cv.put(RateContract.RateEntry.COLUMN_RATE, r.getRate());
                db.insert(RateContract.RateEntry.TABLE_NAME, null, cv);
            }

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            ContentValues meta = new ContentValues();
            meta.put(RateContract.MetaEntry.COLUMN_KEY, KEY_LAST_UPDATE);
            meta.put(RateContract.MetaEntry.COLUMN_VALUE, today);
            db.insertWithOnConflict(
                    RateContract.MetaEntry.TABLE_NAME,
                    null,
                    meta,
                    SQLiteDatabase.CONFLICT_REPLACE
            );

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public List<Rate> getAllRates() {
        List<Rate> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                RateContract.RateEntry.TABLE_NAME,
                null, null, null, null, null, null
        );
        while (c.moveToNext()) {
            Rate r = new Rate();
            r.setId(c.getLong(c.getColumnIndexOrThrow(RateContract.RateEntry._ID)));
            r.setCode(c.getString(c.getColumnIndexOrThrow(RateContract.RateEntry.COLUMN_CODE)));
            r.setName(c.getString(c.getColumnIndexOrThrow(RateContract.RateEntry.COLUMN_NAME)));
            r.setRate(c.getDouble(c.getColumnIndexOrThrow(RateContract.RateEntry.COLUMN_RATE)));
            list.add(r);
        }
        c.close();
        db.close();
        return list;
    }

    public String getLastUpdateDate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                RateContract.MetaEntry.TABLE_NAME,
                new String[]{RateContract.MetaEntry.COLUMN_VALUE},
                RateContract.MetaEntry.COLUMN_KEY + "=?",
                new String[]{KEY_LAST_UPDATE},
                null, null, null
        );
        String date = null;
        if (c.moveToFirst()) {
            date = c.getString(c.getColumnIndexOrThrow(RateContract.MetaEntry.COLUMN_VALUE));
        }
        c.close();
        db.close();
        return date;
    }
}
