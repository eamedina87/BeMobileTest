package com.medinamobile.bemobiletest.transaction.list;

import android.support.v7.app.AppCompatActivity;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.utils.DatabaseUtilsImpl;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListDatabaseRepositoryImpl implements TransactionListDatabaseRepository {

    private final DatabaseUtilsImpl mDatabaseUtils;
    //private AppCompatActivity mContext;

    public TransactionListDatabaseRepositoryImpl(AppCompatActivity mContext) {
        //this.mContext = mContext;
        mDatabaseUtils = new DatabaseUtilsImpl(mContext);
    }

    @Override
    public void saveTransactions(ArrayList<Transaction> transactions) {
        mDatabaseUtils.saveTransactions(transactions);
    }

    @Override
    public void saveRates(ArrayList<Rate> rates) {
        mDatabaseUtils.saveRates(rates);
    }

    @Override
    public void getTransactionsList() {
        mDatabaseUtils.getTransactions();
    }

    @Override
    public void getRatesList() {
        mDatabaseUtils.getRates();
    }

    @Override
    public void deleteAll() {
        mDatabaseUtils.deleteAll();
    }
}
