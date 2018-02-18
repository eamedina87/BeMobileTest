package com.medinamobile.bemobiletest.transaction.detail;

import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.detail.events.TransactionDetailEvent;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionDetailPresenter {

    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void onEventMainThread(TransactionListEvent event);

    void getTotalSum(ArrayList<Transaction> transactions);
}
