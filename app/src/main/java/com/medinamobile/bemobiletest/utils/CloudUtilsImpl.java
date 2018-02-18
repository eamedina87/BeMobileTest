package com.medinamobile.bemobiletest.utils;

import com.google.gson.Gson;
import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Erick on 2/17/2018.
 */

public class CloudUtilsImpl implements CloudUtils {

    /*
    public interface CloudUtilsCallbacks{
        void onTransactionListSuccess(ArrayList<Transaction> transactions);
        void onTransactionListEmpty();
        void onTransactionListError(String error);
        void onRateListSuccess(ArrayList<Rate> rates);
        void onRateListEmpty();
        void onRateListError(String error);
    }
    */

    public static final String URL_BASE = "http://quiet-stone-2094.herokuapp.com/";
    public static final String URL_RATES = "rates.json";
    public static final String URL_TRANSACTIONS = "transactions.json";

    /*
    private CloudUtilsCallbacks mCallbacks;

    public CloudUtilsImpl(CloudUtilsCallbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
    }
    */

    @Override
    public void getRates() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CloudUtilsImpl.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        RetrofitService mService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Rate>> mCall = mService.getRatesList();
        mCall.enqueue(new Callback<ArrayList<Rate>>() {
            @Override
            public void onResponse(Call<ArrayList<Rate>> call, Response<ArrayList<Rate>> response) {
                if (response.isSuccessful()){
                    ArrayList<Rate> mList = response.body();
                    if (mList.isEmpty()){
                        //mCallbacks.onRateListEmpty();
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_EMPTY);
                        postEvent(mEvent);
                    } else {
                        //mCallbacks.onRateListSuccess(response.body());
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setRates(response.body());
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_SUCCESS);
                        postEvent(mEvent);
                    }
                } else {
                    //mCallbacks.onRateListError(response.errorBody().toString());
                    TransactionListEvent mEvent = new TransactionListEvent();
                    mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
                    mEvent.setError(response.errorBody().toString());
                    postEvent(mEvent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rate>> call, Throwable t) {
                //mCallbacks.onRateListError(t.getLocalizedMessage());
                TransactionListEvent mEvent = new TransactionListEvent();
                mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
                mEvent.setError(t.getLocalizedMessage());
                postEvent(mEvent);
            }
        });
    }

    @Override
    public void getTransactions() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CloudUtilsImpl.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        RetrofitService mService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Transaction>> mCall = mService.getTransactionsList();
        mCall.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                if (response.isSuccessful()){
                    ArrayList<Transaction> mList = response.body();
                    if (mList.isEmpty()){
                        //mCallbacks.onTransactionListEmpty();
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_EMPTY);
                        postEvent(mEvent);
                    } else {
                        //mCallbacks.onTransactionListSuccess(response.body());
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setTransactions(mList);
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS);
                        postEvent(mEvent);
                    }
                } else {
                    //mCallbacks.onTransactionListError(response.errorBody().toString());
                    TransactionListEvent mEvent = new TransactionListEvent();
                    mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
                    mEvent.setError(response.errorBody().toString());
                    postEvent(mEvent);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
                //mCallbacks.onTransactionListError(t.getLocalizedMessage());
                TransactionListEvent mEvent = new TransactionListEvent();
                mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
                mEvent.setError(t.getLocalizedMessage());
                postEvent(mEvent);
            }
        });
    }

    private void postEvent(TransactionListEvent event){
        EventBus mEventBus = EventBusImpl.getInstance();
        mEventBus.post(event);
    }
}
