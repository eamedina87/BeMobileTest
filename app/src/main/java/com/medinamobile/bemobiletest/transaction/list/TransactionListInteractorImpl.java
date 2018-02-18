package com.medinamobile.bemobiletest.transaction.list;

import android.support.v7.app.AppCompatActivity;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListInteractorImpl implements TransactionListInteractor {

    private TransactionListDatabaseRepository mDatabaseRepository;
    private TransactionListCloudRepository mCloudRepository;

    public TransactionListInteractorImpl(AppCompatActivity context) {
        mDatabaseRepository = new TransactionListDatabaseRepositoryImpl(context);
        mCloudRepository = new TransactionListCloudRepositoryImpl();
    }

    @Override
    public void getTransactionsFromDatabase() {
        mDatabaseRepository.getTransactionsList();
    }

    @Override
    public void getTransactionsFromCloud() {
        mCloudRepository.getTransactionsList();
    }

    @Override
    public void getRatesFromDatabase() {
        mDatabaseRepository.getRatesList();
    }

    @Override
    public void getRatesFromCloud() {
        mCloudRepository.getRatesList();
    }

    @Override
    public void saveTransactions(ArrayList<Transaction> transactions) {
        mDatabaseRepository.saveTransactions(transactions);
    }

    @Override
    public void saveRates(ArrayList<Rate> rates) {
        mDatabaseRepository.saveRates(rates);
    }
}
