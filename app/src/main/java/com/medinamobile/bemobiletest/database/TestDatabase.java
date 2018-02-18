package com.medinamobile.bemobiletest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Erick on 2/18/2018.
 */

public class TestDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "test.db";
    public static final int DB_VERSION = 1;

    public TestDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    public TestDatabase(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE "  + TestContract.TransactionEntry.TABLE_NAME + " (" +
                TestContract.TransactionEntry._ID                + " INTEGER PRIMARY KEY, " +
                TestContract.TransactionEntry.COLUMN_SKU + " TEXT NOT NULL, " +
                TestContract.TransactionEntry.COLUMN_AMOUNT + " TEXT NOT NULL, " +
                TestContract.TransactionEntry.COLUMN_CURRENCY    + " TEXT NOT NULL);";

        final String CREATE_TABLE_RATES = "CREATE TABLE "  + TestContract.RatesEntry.TABLE_NAME + " (" +
                TestContract.RatesEntry._ID                + " INTEGER PRIMARY KEY, " +
                TestContract.RatesEntry.COLUMN_FROM + " TEXT NOT NULL, " +
                TestContract.RatesEntry.COLUMN_TO + " TEXT NOT NULL, " +
                TestContract.RatesEntry.COLUMN_RATE    + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_RATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TestContract.RatesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TestContract.TransactionEntry.TABLE_NAME);
        onCreate(db);
    }
}
