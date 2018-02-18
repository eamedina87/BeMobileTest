package com.medinamobile.bemobiletest.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.database.TestDatabase;


/**
 * Created by Erick on 2/18/2018.
 */

public class TestContentProvider extends ContentProvider {



    private TestDatabase mDatabaseHelper;


    public static final int ID_TRANSACTIONS = 100;
    public static final int ID_RATES = 101;
    private static final int ID_TRANSACTIONS_SKU = 102;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TestContract.AUTHORITY, TestContract.PATH_TRANSACTIONS, ID_TRANSACTIONS);
        uriMatcher.addURI(TestContract.AUTHORITY, TestContract.PATH_TRANSACTIONS + "/*", ID_TRANSACTIONS_SKU);
        uriMatcher.addURI(TestContract.AUTHORITY, TestContract.PATH_RATES, ID_RATES);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new TestDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case ID_TRANSACTIONS: {
                returnCursor = db.query(true,
                        TestContract.TransactionEntry.TABLE_NAME,
                        new String[]{TestContract.TransactionEntry.COLUMN_SKU},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        TestContract.TransactionEntry.COLUMN_SKU + " ASC",
                        null
                        );
                break;
            }
            case ID_TRANSACTIONS_SKU: {
                returnCursor = db.query(
                        TestContract.TransactionEntry.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        TestContract.TransactionEntry.COLUMN_AMOUNT + " ASC",
                        null
                );
                break;
            }
            case ID_RATES: {
                returnCursor = db.query(TestContract.RatesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ID_TRANSACTIONS: {
                long id = db.insert(TestContract.TransactionEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TestContract.TransactionEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case ID_RATES: {
                long id = db.insert(TestContract.RatesEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TestContract.RatesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int insertCount = 0;

        switch (match) {
            case ID_TRANSACTIONS: {
                db.beginTransaction();
                for(ContentValues mValues:values){
                    long id = db.insert(TestContract.TransactionEntry.TABLE_NAME, null, mValues);
                    Log.d("insertTransaction", "sku:"+mValues.getAsString("sku"));
                    if (id > 0) {
                        insertCount++;
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                db.setTransactionSuccessful();
                db.endTransaction();

                break;
            }


            case ID_RATES: {

                db.beginTransaction();
                for(ContentValues mValues:values){
                    long id = db.insert(TestContract.RatesEntry.TABLE_NAME, null, mValues);
                    if (id > 0) {
                        insertCount++;
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return insertCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {



        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
