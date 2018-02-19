package com.medinamobile.bemobiletest.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.entities.BeMobileEvent;
import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.providers.BeMobileDataProvider;
import com.medinamobile.bemobiletest.providers.TestContentProvider;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class DatabaseUtilsImpl implements DatabaseUtils, LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: 2/17/2018 DESTROY LOADERS

    private static final int ID_TRANSACTIONS_LOADER = 0;
    private static final int ID_RATES_LOADER = 1;
    private static final int ID_TRANSACTIONS_SKU_LOADER = 2;
    private final AppCompatActivity mContext;


    public DatabaseUtilsImpl(AppCompatActivity activity) {
        this.mContext = activity;
    }

    @Override
    public void getTransactions() {
        mContext.getSupportLoaderManager().initLoader(ID_TRANSACTIONS_LOADER, null, this);
    }

    @Override
    public void getTransactionsForSku(String sku) {
        Bundle args = new Bundle();
        args.putString(TestContract.TransactionEntry.COLUMN_SKU, sku);
        mContext.getSupportLoaderManager().initLoader(ID_TRANSACTIONS_SKU_LOADER, args, this);
    }

    @Override
    public void getRates() {
        mContext.getSupportLoaderManager().initLoader(ID_RATES_LOADER, null, this);
    }

    @Override
    public void saveTransactions(ArrayList<Transaction> transactions) {

        Uri mUri = TestContract.TransactionEntry.CONTENT_URI;
        int insertedRows = mContext.getContentResolver().bulkInsert(mUri, BeMobileUtils.getTransactionListContentValues(transactions));
    }

    @Override
    public void saveRates(ArrayList<Rate> rates) {

        Uri mUri = TestContract.RatesEntry.CONTENT_URI;
        int insertedRows = mContext.getContentResolver().bulkInsert(mUri, BeMobileUtils.getRateListContentValues(rates));

    }

    @Override
    public void deleteAll() {
        Uri mUri = TestContract.TransactionEntry.CONTENT_URI;
        int deleted = mContext.getContentResolver().delete(mUri, null, null);
        if (deleted>=0){
            TransactionListEvent event = new TransactionListEvent();
            event.setEventType(TransactionListEvent.EVENT_TYPE_DELETE_SUCCESS);
            postEvent(event);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_RATES_LOADER:{
                return new CursorLoader(mContext, TestContract.RatesEntry.CONTENT_URI, null, null, null, null);
            }
            case ID_TRANSACTIONS_LOADER:{
                return new CursorLoader(mContext, TestContract.TransactionEntry.CONTENT_URI, null, null, null, null);
            }
            case ID_TRANSACTIONS_SKU_LOADER:{
                String sku = args.getString(TestContract.TransactionEntry.COLUMN_SKU);
                if (sku!=null)
                    return new CursorLoader(mContext,
                        TestContract.TransactionEntry.CONTENT_URI_SINGLE,
                        null,
                        TestContract.TransactionEntry.COLUMN_SKU+"=?",
                        new String[]{sku},
                        null);
            }
            default:
                throw new RuntimeException("Loader not implemented: "+id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case ID_RATES_LOADER:{
                ArrayList<Rate> rates =  BeMobileUtils.getRateListFromCursor(data);
                if (rates==null){
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
                    postEvent(event);
                } else if (rates.isEmpty()){
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_RATES_EMPTY);
                    postEvent(event);
                } else {
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_RATES_SUCCESS);
                    event.setRates(rates);
                    postEvent(event);
                }
            }
            case ID_TRANSACTIONS_SKU_LOADER:
            case ID_TRANSACTIONS_LOADER:{
                ArrayList<Transaction> transactions =  BeMobileUtils.getTransactionsListFromCursor(data);
                if (transactions==null){
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
                    postEvent(event);
                } else if (transactions.isEmpty()){
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_EMPTY);
                    postEvent(event);
                } else {
                    TransactionListEvent event = new TransactionListEvent();
                    event.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS);
                    event.setTransactions(transactions);
                    postEvent(event);
                }
            }


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void postEvent(BeMobileEvent event) {
        EventBus mEventBus = EventBusImpl.getInstance();
        mEventBus.post(event);
    }
}
