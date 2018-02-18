package com.medinamobile.bemobiletest.providers;

import android.net.Uri;

import com.medinamobile.bemobiletest.database.BeMobileDatabase;
import com.medinamobile.bemobiletest.database.RateTable;
import com.medinamobile.bemobiletest.database.TransactionTable;


/**
 * Created by Erick on 2/17/2018.
 */

//@ContentProvider(authority = BeMobileDataProvider.AUTHORITY, database = BeMobileDatabase.class)
public final class BeMobileDataProvider{
    public static final String AUTHORITY = "com.medinamobile.bemobiletest.BeMobileDataProvider";

    //@TableEndpoint(table = BeMobileDatabase.RATES)
    public static class Rates {

        /*@ContentUri(
                path = "rates",
                type = "vnd.android.cursor.dir/list",
                defaultSort = RateTable.FROM + " ASC")
                */
        public static final Uri RATES = Uri.parse("content://" + AUTHORITY + "/rates");

    }

    //@TableEndpoint(table = BeMobileDatabase.TRANSACTIONS)
    public static class Transactions {

        /*@ContentUri(
                path = "transactions",
                type = "vnd.android.cursor.dir/list",
                defaultSort = TransactionTable.SKU + " ASC")
                */
        public static final Uri TRANSACTIONS = Uri.parse("content://" + AUTHORITY + "/transactions");

    }

}
