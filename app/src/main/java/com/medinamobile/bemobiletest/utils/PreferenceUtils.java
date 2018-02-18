package com.medinamobile.bemobiletest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Erick on 2/17/2018.
 */

public class PreferenceUtils {

    private static final String PREFERENCES_NAME = "prefs";
    private static final String KEY_TRANSACTIONS_UPDATED = "transactions_updated";
    private static final String KEY_RATES_UPDATED = "rates_updated";

    public static boolean areTransactionsUpdated(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_TRANSACTIONS_UPDATED, false);
    }

    public static void setTransactionsUpdated(AppCompatActivity mContext, boolean value) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_TRANSACTIONS_UPDATED, value).commit();
    }

    public static boolean areRatesUpdated(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_RATES_UPDATED, false);
    }

    public static void setRatesUpdated(AppCompatActivity mContext, boolean value) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_RATES_UPDATED, value).commit();
    }
}
