package com.medinamobile.bemobiletest.transaction.list;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;
import com.medinamobile.bemobiletest.transaction.list.ui.TransactionListView;
import com.medinamobile.bemobiletest.utils.EventBus;
import com.medinamobile.bemobiletest.utils.EventBusImpl;
import com.medinamobile.bemobiletest.utils.PreferenceUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListPresenterImpl implements TransactionListPresenter {

    private final AppCompatActivity mContext;
    private TransactionListInteractor mInteractor;
    private EventBus mEventBus = EventBusImpl.getInstance();
    private TransactionListView mView;

    public TransactionListPresenterImpl(TransactionListView mView, AppCompatActivity mContext) {
        this.mView = mView;
        this.mContext = mContext;
        mInteractor = new TransactionListInteractorImpl(mContext);
    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        mView = null;
    }

    @Override
    public void onResume() {
        mView.showProgress();
        //Get Transactions
        if (PreferenceUtils.areTransactionsUpdated(mContext)){
            mInteractor.getTransactionsFromDatabase();
        } else {
            mInteractor.getTransactionsFromCloud();
        }
        //Get Rates
        if (!PreferenceUtils.areRatesUpdated(mContext)){
            mInteractor.getRatesFromCloud();
        }
    }

    @Override
    public void onPause() {

    }

    @Subscribe
    @Override
    public void onEventMainThread(TransactionListEvent event) {
        switch (event.getEventType()){
            case TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS:{
                onTransactionsSuccess(event.getTransactions());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_RATES_SUCCESS:{
                onRatesSuccess(event.getRates());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_DELETE_SUCCESS:{
                onDeleteSuccess();
                break;
            }
            case TransactionListEvent.EVENT_TYPE_TRANSACTIONS_EMPTY:{
                onTransactionsEmpty();
                break;
            }
            case TransactionListEvent.EVENT_TYPE_RATES_EMPTY:{
                onRatesEmpty();
                break;
            }
            case TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR:{
                onTransactionError(event.getError());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_RATES_ERROR:{
                onRatesError(event.getError());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_DELETE_ERROR:{
                onDeleteError();
                break;
            }
        }
    }

    private void onDeleteSuccess() {
        if (mView!=null){
            mView.hideProgress();
        }
        PreferenceUtils.setTransactionsUpdated(mContext, false);
        PreferenceUtils.setRatesUpdated(mContext, false);
        onResume();
    }

    private void onDeleteError() {
        if (mView!=null){
            mView.hideProgress();
        }
    }

    @Override
    public void refreshData() {
        mInteractor.deleteDataFromDatabase();
        if (mView!=null){
            mView.showProgress();
        }
    }

    private void onTransactionsSuccess(ArrayList<Transaction> transactions) {

        if (!PreferenceUtils.areTransactionsUpdated(mContext)){
            mInteractor.saveTransactions(transactions);
            PreferenceUtils.setTransactionsUpdated(mContext, true);
            mInteractor.getTransactionsFromDatabase();
            return;
        }

        if (mView!=null){
            mView.hideProgress();
            mView.onTransactionListSuccess(transactions);
        }
    }

    private void onRatesSuccess(ArrayList<Rate> rates) {
        if (!PreferenceUtils.areRatesUpdated(mContext)){
            mInteractor.saveRates(rates);
            PreferenceUtils.setRatesUpdated(mContext, true);
        }
    }

    private void onTransactionsEmpty() {
        if (mView!=null){
            mView.hideProgress();
            mView.onTransactionListEmpty();
        }
    }

    private void onRatesEmpty() {
        //No Action
    }

    private void onTransactionError(String error) {
        if (mView!=null){
            mView.hideProgress();
            mView.onTransactionListError(error);
        }
    }

    private void onRatesError(String error) {
        //No Action
    }
}
