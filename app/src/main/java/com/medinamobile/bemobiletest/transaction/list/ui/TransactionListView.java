package com.medinamobile.bemobiletest.transaction.list.ui;

import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionListView {

    void showProgress();
    void hideProgress();

    void onTransactionListSuccess(ArrayList<Transaction> products);
    void onTransactionListEmpty();
    void onTransactionListError(String error);

}
