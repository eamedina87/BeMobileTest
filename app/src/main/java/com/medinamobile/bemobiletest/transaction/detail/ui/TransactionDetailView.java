package com.medinamobile.bemobiletest.transaction.detail.ui;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionDetailView {

    void showProgress();
    void hideProgress();

    void onTransactionsSuccess(ArrayList<Transaction> transactions);
    void onTransactionsError(String error);

    void onRatesError(String error);


    void setTotalSum(String sum);
}
