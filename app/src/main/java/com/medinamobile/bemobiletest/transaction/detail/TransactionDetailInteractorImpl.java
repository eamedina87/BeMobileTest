package com.medinamobile.bemobiletest.transaction.detail;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Erick on 2/18/2018.
 */

public class TransactionDetailInteractorImpl implements TransactionDetailInteractor {

    private TransactionDetailRepository mRepository;

    public TransactionDetailInteractorImpl(AppCompatActivity context, String sku) {
        mRepository = new TransactionDetailRepositoryImpl(context, sku);
    }

    @Override
    public void getTransactions() {
        mRepository.getTransactions();
    }

    @Override
    public void getRates() {
        mRepository.getRates();
    }
}
