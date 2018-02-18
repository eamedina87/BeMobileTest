package com.medinamobile.bemobiletest.transaction.detail;

import android.support.v7.app.AppCompatActivity;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.detail.events.TransactionDetailEvent;
import com.medinamobile.bemobiletest.transaction.detail.ui.TransactionDetailView;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;
import com.medinamobile.bemobiletest.utils.BeMobileUtils;
import com.medinamobile.bemobiletest.utils.EventBus;
import com.medinamobile.bemobiletest.utils.EventBusImpl;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Erick on 2/18/2018.
 */

public class TransactionDetailPresenterImpl implements TransactionDetailPresenter {

    private EventBus mEventBus = EventBusImpl.getInstance();
    private TransactionDetailView mView;
    private TransactionDetailInteractor mInteractor;
    private ArrayList<Rate> mRates;
    private ArrayList<Transaction> mTransactions;

    public TransactionDetailPresenterImpl(TransactionDetailView mView, AppCompatActivity context, String sku) {
        this.mView = mView;
        mInteractor = new TransactionDetailInteractorImpl(context,sku);
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
    public void onPause() {
        mEventBus.unregister(this);
    }

    @Override
    public void onResume() {
        mInteractor.getRates();
        mInteractor.getTransactions();
        mView.showProgress();
    }

    @Subscribe
    @Override
    public void onEventMainThread(TransactionListEvent event) {
        if (mView!=null){
            mView.hideProgress();
        }

        switch (event.getEventType()){
            case TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS:{
                onTransactionsSuccess(event.getTransactions());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_RATES_SUCCESS:{
                onRatesSuccess(event.getRates());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR:{
                onTransactionsError(event.getError());
                break;
            }
            case TransactionListEvent.EVENT_TYPE_RATES_ERROR:{
                onRatesError(event.getError());
                break;
            }
        }

    }

    @Override
    public void getTotalSum(ArrayList<Transaction> transactions) {
        if (mView!=null){
            mView.setTotalSum(BeMobileUtils.getTotalSum(transactions));
        }
    }

    private void onTransactionsSuccess(ArrayList<Transaction> transactions) {
        mTransactions = transactions;
        if (mRates!=null)
            transactions = BeMobileUtils.convertTransactionToEuro(transactions, mRates);
        if (mView!=null){
            mView.onTransactionsSuccess(transactions);
        }
    }

    private void onRatesSuccess(ArrayList<Rate> rates) {
        mRates = rates;
        if (mTransactions!=null)
            mTransactions = BeMobileUtils.convertTransactionToEuro(mTransactions, mRates);
        if (mView!=null && mTransactions!=null){
            mView.onTransactionsSuccess(mTransactions);
        }
    }

    private void onTransactionsError(String error) {
        if (mView!=null){
            mView.onTransactionsError(error);
        }
    }

    private void onRatesError(String error) {
        if (mView!=null){
            mView.onRatesError(error);
        }
    }
}
