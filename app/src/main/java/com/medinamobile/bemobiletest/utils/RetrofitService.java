package com.medinamobile.bemobiletest.utils;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Erick on 2/17/2018.
 */

public interface RetrofitService {

    @GET(CloudUtilsService.URL_RATES)
    Call<ArrayList<Rate>> getRatesList();

    @GET(CloudUtilsService.URL_TRANSACTIONS)
    Call<ArrayList<Transaction>> getTransactionsList();

}
