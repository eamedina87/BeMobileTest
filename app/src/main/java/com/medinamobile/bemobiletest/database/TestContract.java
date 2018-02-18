package com.medinamobile.bemobiletest.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Erick on 2/18/2018.
 */

public class TestContract {

    public static final String AUTHORITY = "com.medinamobile.bemobiletest.BeMobileContentProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_TRANSACTIONS = "transactions";
    public static final String PATH_RATES = "rates";

    public static final class TransactionEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACTIONS).build();

        public static final Uri CONTENT_URI_SINGLE =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACTIONS).appendPath("*").build();


        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_SKU = "sku";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_CURRENCY = "currency";


    }

    public static final class RatesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATES).build();

        public static final String TABLE_NAME = "rates";
        public static final String COLUMN_FROM = "from_";
        public static final String COLUMN_TO = "to_";
        public static final String COLUMN_RATE = "rate";


    }

}
