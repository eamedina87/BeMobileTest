package com.medinamobile.bemobiletest.transaction.detail;

import android.support.v7.app.AppCompatActivity;

import com.medinamobile.bemobiletest.utils.DatabaseUtils;
import com.medinamobile.bemobiletest.utils.DatabaseUtilsImpl;

/**
 * Created by Erick on 2/18/2018.
 */

public class TransactionDetailRepositoryImpl implements TransactionDetailRepository {

    DatabaseUtils mDatabaseUtils;
    private String mSKU;

    public TransactionDetailRepositoryImpl(AppCompatActivity context, String mSKU) {
        this.mSKU = mSKU;
        mDatabaseUtils = new DatabaseUtilsImpl(context);
    }

    @Override
    public void getTransactions() {
        mDatabaseUtils.getTransactionsForSku(mSKU);
    }

    @Override
    public void getRates() {
        mDatabaseUtils.getRates();
    }

}
