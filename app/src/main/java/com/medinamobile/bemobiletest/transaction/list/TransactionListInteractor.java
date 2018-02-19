package com.medinamobile.bemobiletest.transaction.list;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionListInteractor {
    void getTransactionsFromDatabase();
    void getTransactionsFromCloud();
    void getRatesFromDatabase();
    void getRatesFromCloud();
    void saveTransactions(ArrayList<Transaction> transactions);
    void saveRates(ArrayList<Rate> rates);


    void deleteDataFromDatabase();
}
