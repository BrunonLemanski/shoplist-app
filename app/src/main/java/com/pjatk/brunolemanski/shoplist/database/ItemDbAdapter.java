package com.pjatk.brunolemanski.shoplist.database;

import android.provider.BaseColumns;


/**
 * Connecting with SQLite Database
 *
 * @author Brunon Lemański
 */
public class ItemDbAdapter {

    /**
     * Constructor - must be empty.
     */
    public ItemDbAdapter() {}

    /**
     * 1. Build fields to entry.
     */
    public static final class ItemDbEntry implements BaseColumns {

        /**
         * Fields in database
         */
        public static final String TABLE_NAME = "itemList";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_DONE = "done";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
