package com.medinamobile.bemobiletest.transaction.list;

import android.content.Context;

import com.medinamobile.bemobiletest.transaction.list.events.TransactionListEvent;

/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionListPresenter {

    void onCreate();
    void onDestroy();
    void onResume();
    void onPause();

    void onEventMainThread(TransactionListEvent event);

}
