package com.blq.qrcode.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/7 9:26
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final int VERSION = 1;
    private static final String DB_NAME="QRHISTORY";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static final String HISTORY="CREATE TABLE IF NOT EXISTS history" +
            " (mid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "status INTEGER DEFAULT 0," +
            "style VARCHAR(20)," +
            "content1 text," +
            "content2 text," +
            "time text)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
