package com.medinamobile.bemobiletest.database;


/**
 * Created by Erick on 2/17/2018.
 */

public interface TransactionTable {

    //@DataType(TEXT) @PrimaryKey @Unique
    String SKU = "sku";

    //@DataType(REAL) @NotNull
    String AMOUNT = "amount";

    //@DataType(TEXT) @NotNull
    String CURRENCY = "currency";

}
