package com.medinamobile.bemobiletest.transaction.list;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionListDatabaseRepository {
    void saveTransactions(ArrayList<Transaction> transactions);
    void saveRates(ArrayList<Rate> rates);
    void getTransactionsList();
    void getRatesList();
}
