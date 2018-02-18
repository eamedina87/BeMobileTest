package com.medinamobile.bemobiletest.transaction.list;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;
import com.medinamobile.bemobiletest.utils.CloudUtilsImpl;
import com.medinamobile.bemobiletest.utils.EventBus;
import com.medinamobile.bemobiletest.utils.EventBusImpl;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListCloudRepositoryImpl implements TransactionListCloudRepository{

    //private final CloudUtilsImpl mCloudUtils;

    public TransactionListCloudRepositoryImpl() {
//        mCloudUtils = new CloudUtilsImpl(this);
    }

    @Override
    public void getTransactionsList() {
        //mCloudUtils.getTransactions();
        new CloudUtilsImpl().getTransactions();
    }

    @Override
    public void getRatesList() {
        //mCloudUtils.getRates();
        new CloudUtilsImpl().getRates();
    }
}

 /*
    @Override
    public void onTransactionListSuccess(ArrayList<Transaction> transactions) {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setTransactions(transactions);
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS);
        postEvent(mEvent);
    }

    @Override
    public void onTransactionListEmpty() {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_EMPTY);
        postEvent(mEvent);
    }

    @Override
    public void onTransactionListError(String error) {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
        mEvent.setError(error);
        postEvent(mEvent);
    }

    @Override
    public void onRateListSuccess(ArrayList<Rate> rates) {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setRates(rates);
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_SUCCESS);
        postEvent(mEvent);
    }

    @Override
    public void onRateListEmpty() {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_EMPTY);
        postEvent(mEvent);
    }

    @Override
    public void onRateListError(String error) {
        TransactionListEvent mEvent = new TransactionListEvent();
        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
        mEvent.setError(error);
        postEvent(mEvent);
    }

    private void postEvent(TransactionListEvent event){
        EventBus mEventBus = EventBusImpl.getInstance();
        mEventBus.post(event);
    }
    */
