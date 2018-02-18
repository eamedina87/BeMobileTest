package com.medinamobile.bemobiletest.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.database.TransactionTable;

import java.math.BigDecimal;

import static com.medinamobile.bemobiletest.entities.Rate.CURRENCY_EURO;


/**
 * Created by Erick on 2/17/2018.
 */

public class Transaction implements Parcelable{


    private static final String TRANSACTION_SKU = TestContract.TransactionEntry.COLUMN_SKU;
    private static final String TRANSACTION_AMOUNT = TestContract.TransactionEntry.COLUMN_AMOUNT;
    private static final String TRANSACTION_CURRENCY = TestContract.TransactionEntry.COLUMN_CURRENCY;

    public static final  String  EURO_SIGN = "€";


    @SerializedName(TRANSACTION_SKU)
    @Expose
    private String sku;
    @SerializedName(TRANSACTION_AMOUNT)
    @Expose
    private String amount;
    @SerializedName(TRANSACTION_CURRENCY)
    @Expose
    private String currency;
    private String amountEuro;

    public Transaction(){

    }

    public Transaction(Cursor data) {
        setSku(data.getString(data.getColumnIndex(TRANSACTION_SKU)));
        if (data.getColumnIndex(TRANSACTION_AMOUNT)!=-1)
            setAmount(data.getString(data.getColumnIndex(TRANSACTION_AMOUNT)));
        if (data.getColumnIndex(TRANSACTION_CURRENCY)!=-1)
            setCurrency(data.getString(data.getColumnIndex(TRANSACTION_CURRENCY)));
    }

    protected Transaction(Parcel in) {
        sku = in.readString();
        amount = in.readString();
        currency = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getSku());
        dest.writeString(getAmount());
        dest.writeString(getCurrency());
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_SKU, getSku());
        contentValues.put(TRANSACTION_AMOUNT, getAmount());
        contentValues.put(TRANSACTION_CURRENCY, getCurrency());
        return contentValues;
    }

    @Override
    public String toString() {
        return "sku:"+getSku()+" amount:"+getAmount()+" currency:"+getCurrency();
    }

    public String getAmountEuro() {
        return amountEuro;
    }

    public void setAmountEuro(String amountEuro) {
        this.amountEuro = amountEuro;
    }

    public boolean isInEuro() {
        return getCurrency().equals(CURRENCY_EURO);
    }

    public String getAmountString() {
        return getAmount() + " " + getCurrency();
    }

    public String getAmountEuroString() {
        if (isInEuro())
            return getAmount() + EURO_SIGN ;
        return getAmountEuro() + EURO_SIGN ;
    }

    public BigDecimal getAmountEuroBigDecimal() {
        if (isInEuro())
            return new BigDecimal(getAmount());
        return new BigDecimal(getAmountEuro());
    }
}
