package com.medinamobile.bemobiletest.transaction.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.medinamobile.bemobiletest.utils.CloudUtils;
import com.medinamobile.bemobiletest.utils.CloudUtilsService;

/**
 * Created by Erick on 2/17/2018.
 */

public class TransactionListCloudRepositoryImpl implements TransactionListCloudRepository{

    //private final CloudUtilsService mCloudUtils;
    private Context mContext;

    public TransactionListCloudRepositoryImpl(Context context) {
//        mCloudUtils = new CloudUtilsService(this);
        mContext = context;
    }

    @Override
    public void getTransactionsList() {
        Intent service = new Intent(mContext, CloudUtilsService.class);
        Bundle args = new Bundle();
        args.putString(CloudUtils.MODE, CloudUtils.MODE_TRANSACTIONS);
        service.putExtras(args);
        mContext.startService(service);
    }

    @Override
    public void getRatesList() {
        Intent service = new Intent(mContext, CloudUtilsService.class);
        Bundle args = new Bundle();
        args.putString(CloudUtils.MODE, CloudUtils.MODE_RATES);
        service.putExtras(args);
        mContext.startService(service);
    }
}

