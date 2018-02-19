package com.medinamobile.bemobiletest.utils;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

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

public class CloudUtilsService extends IntentService implements CloudUtils {

    public static final String URL_BASE = "http://quiet-stone-2094.herokuapp.com/";
    public static final String URL_RATES = "rates.json";
    public static final String URL_TRANSACTIONS = "transactions.json";

    public CloudUtilsService() {
        super("CloudUtilsService");
    }

    public CloudUtilsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null && intent.getExtras()!=null && intent.hasExtra(CloudUtils.MODE)){
            if (intent.getStringExtra(CloudUtils.MODE).equals(CloudUtils.MODE_RATES)){
                getRates();
            } else if (intent.getStringExtra(CloudUtils.MODE).equals(CloudUtils.MODE_TRANSACTIONS)){
                getTransactions();
            }
        }
    }

    @Override
    public void getRates() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CloudUtilsService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        RetrofitService mService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Rate>> mCall = mService.getRatesList();
        mCall.enqueue(new Callback<ArrayList<Rate>>() {
            @Override
            public void onResponse(Call<ArrayList<Rate>> call, Response<ArrayList<Rate>> response) {
                if (response.isSuccessful()){
                    ArrayList<Rate> mList = response.body();
                    if (mList==null){
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
                        postEvent(mEvent);
                    } else if (mList.isEmpty()){
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_EMPTY);
                        postEvent(mEvent);
                    } else {
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setRates(response.body());
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_SUCCESS);
                        postEvent(mEvent);
                    }
                } else if (response.errorBody()!=null){
                    TransactionListEvent mEvent = new TransactionListEvent();
                    mEvent.setEventType(TransactionListEvent.EVENT_TYPE_RATES_ERROR);
                    mEvent.setError(response.errorBody().toString());
                    postEvent(mEvent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rate>> call, Throwable t) {
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
                .baseUrl(CloudUtilsService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        RetrofitService mService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Transaction>> mCall = mService.getTransactionsList();
        mCall.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                if (response.isSuccessful()){
                    ArrayList<Transaction> mList = response.body();
                    if (mList==null){
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
                        postEvent(mEvent);
                    } else if (mList.isEmpty()){
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_EMPTY);
                        postEvent(mEvent);
                    } else {
                        TransactionListEvent mEvent = new TransactionListEvent();
                        mEvent.setTransactions(mList);
                        mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_SUCCESS);
                        postEvent(mEvent);
                    }
                } else if (response.errorBody()!=null){
                    TransactionListEvent mEvent = new TransactionListEvent();
                    mEvent.setEventType(TransactionListEvent.EVENT_TYPE_TRANSACTIONS_ERROR);
                    mEvent.setError(response.errorBody().toString());
                    postEvent(mEvent);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
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