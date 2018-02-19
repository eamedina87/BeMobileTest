package com.medinamobile.bemobiletest.utils;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public interface DatabaseUtils {
    void getTransactions();
    void getTransactionsForSku(String sku);
    void getRates();
    void saveTransactions(ArrayList<Transaction> transactions);
    void saveRates(ArrayList<Rate> rates);

    void deleteAll();
}
