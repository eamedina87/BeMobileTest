package com.medinamobile.bemobiletest.transaction.list.events;

import com.medinamobile.bemobiletest.entities.BeMobileEvent;
import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListEvent extends BeMobileEvent{
    public static final int EVENT_TYPE_TRANSACTIONS_SUCCESS = 1;
    public static final int EVENT_TYPE_TRANSACTIONS_EMPTY = 2;
    public static final int EVENT_TYPE_TRANSACTIONS_ERROR = 3;
    public static final int EVENT_TYPE_RATES_SUCCESS = 4;
    public static final int EVENT_TYPE_RATES_EMPTY = 5;
    public static final int EVENT_TYPE_RATES_ERROR = 6;
    public static final int EVENT_TYPE_DELETE_SUCCESS = 7;
    public static final int EVENT_TYPE_DELETE_ERROR = 8;

    private ArrayList<Transaction> transactions;
    private ArrayList<Rate> rates;

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setRates(ArrayList<Rate> rates) {
        this.rates = rates;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }
}
