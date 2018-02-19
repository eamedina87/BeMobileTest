package com.medinamobile.bemobiletest.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Erick on 2/17/2018.
 */

public class BeMobileUtils {

    public static ArrayList<Rate> getRateListFromCursor(Cursor data) {
        if (data==null)
            return null;
        ArrayList<Rate> list = new ArrayList<>();
        while (data.moveToNext()){
            list.add(new Rate(data));
        }
        return list;
    }

    public static ArrayList<Transaction> getTransactionsListFromCursor(Cursor data) {
        if (data==null)
            return null;
        ArrayList<Transaction> list = new ArrayList<>();
        while (data.moveToNext()){
            list.add(new Transaction(data));
        }
        return list;
    }

    public static ContentValues[] getTransactionListContentValues(ArrayList<Transaction> list) {
        ContentValues[] contentValues = new ContentValues[list.size()];
        for (int i=0; i<list.size();i++){
            contentValues[i] = list.get(i).toContentValues();
        }
        return contentValues;
    }

    public static ContentValues[] getRateListContentValues(ArrayList<Rate> list) {
        ContentValues[] contentValues = new ContentValues[list.size()];
        for (int i=0; i<list.size();i++){
            contentValues[i] = list.get(i).toContentValues();
        }
        return contentValues;
    }

    public static ArrayList<Transaction> convertTransactionToEuro(ArrayList<Transaction> transactions, ArrayList<Rate> mRates) {
        for (int i=0; i<transactions.size();i++){
            Transaction transaction = transactions.get(i);
            if (transaction.isInEuro()){
                continue;
            }

            transactions.remove(i);
            transaction = convertCurrency(transaction, getConversionRate(transaction.getCurrency(), mRates));
            transactions.add(i, transaction);
        }
        return transactions;
    }

    private static String getConversionRate(String currency, ArrayList<Rate> mRates) {
        for(Rate rate:mRates){
            if (rate.getFrom().equals(currency)
                    && rate.getTo().equals(Rate.CURRENCY_EURO)){
                return rate.getRate();
            } else if (rate.getFrom().equals(currency)){
                for (Rate tmpRate:mRates){
                    if (tmpRate.getFrom().equals(rate.getTo())
                            && tmpRate.getTo().equals(Rate.CURRENCY_EURO)){
                        BigDecimal factor1 = new BigDecimal(rate.getRate());
                        BigDecimal factor2 = new BigDecimal(tmpRate.getRate());
                        BigDecimal finalFactor = factor1.multiply(factor2);
                        return finalFactor.toString();
                    }
                }
            } else if (rate.getTo().equals(currency)){
                for (Rate tmpRate:mRates){
                    if (tmpRate.getFrom().equals(Rate.CURRENCY_EURO)
                            && tmpRate.getTo().equals(rate.getTo())){
                        BigDecimal factor1 = new BigDecimal(rate.getRate());
                        BigDecimal factor2 = new BigDecimal(tmpRate.getRate());
                        BigDecimal finalFactor = factor1.divide(factor2);
                        return finalFactor.toString();
                    }
                }



            }
        }
        return null;
    }

    private static Transaction convertCurrency(Transaction transaction, String rate) {
        if (rate==null)
            return transaction;
        BigDecimal mRate = new BigDecimal(rate);
        BigDecimal mAmount = new BigDecimal(transaction.getAmount());

        BigDecimal mAmountEuro = mRate.multiply(mAmount);

        transaction.setAmountEuro(mAmountEuro.toString());
        return transaction;
    }

    public static String getTotalSum(ArrayList<Transaction> transactions) {
        BigDecimal sum = new BigDecimal("0");
        for (Transaction transaction:transactions){
            sum = sum.add(transaction.getAmountEuroBigDecimal());
        }
        return sum.toString();
    }
}
