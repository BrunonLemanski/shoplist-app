package com.pjatk.brunolemanski.shoplist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pjatk.brunolemanski.shoplist.database.ItemDbAdapter.*;


/**
 * 2. Build helper to db.
 *
 * SQLite Database helper.
 *
 */
public class ItemDbHelper extends SQLiteOpenHelper {


    //------------------------------------------------------ Constants
    /**
     * Database name.
     */
    public static final String DATABASE_NAME = "itemslist.db";

    /**
     * Database version.
     */
    public static final int DATABASE_VERSION = 1;


    //------------------------------------------------------ Constructor
    /**
     * Class constructor.
     * @param context
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //------------------------------------------------------ Methods
    /**
     * Create method - creating table in database.
     * @param db Your database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_ITEMLIST_TABLE = "CREATE TABLE " +
                ItemDbEntry.TABLE_NAME + " (" +
                ItemDbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemDbEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ItemDbEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                ItemDbEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                ItemDbEntry.COLUMN_DONE + " BOOLEAN DEFAULT 0, " +
                ItemDbEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_ITEMLIST_TABLE);
    }


    /**
     * Update method - updating line in database.
     * @param db Your database.
     * @param oldVersion Database before changes.
     * @param newVersion Database after changes.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ItemDbEntry.TABLE_NAME);
        onCreate(db);
    }
}
