package com.medinamobile.bemobiletest.utils;

/**
 * Created by Erick on 2/17/2018.
 */

public interface CloudUtils {
    String MODE_TRANSACTIONS = "transactions";
    String MODE_RATES = "rates";
    String MODE = "mode";

    void getRates();
    void getTransactions();
}
