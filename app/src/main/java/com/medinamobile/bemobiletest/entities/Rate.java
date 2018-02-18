package com.medinamobile.bemobiletest.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.medinamobile.bemobiletest.database.RateTable;
import com.medinamobile.bemobiletest.database.TestContract;


/**
 * Created by Erick on 2/17/2018.
 */

public class Rate implements Parcelable{

    public static final String CURRENCY_EURO = "EUR";
    public static final String CURRENCY_CAD = "CAD";
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_AUD = "AUD";

    private static final String RATE_FROM = "from";
    private static final String RATE_TO = "to";
    private static final String RATE_RATE = "rate";

    @SerializedName(RATE_FROM)
    @Expose
    private String from;
    @SerializedName(RATE_TO)
    @Expose
    private String to;
    @SerializedName(RATE_RATE)
    @Expose
    private String rate;

    public Rate(Cursor data) {
        setFrom(data.getString(data.getColumnIndex(TestContract.RatesEntry.COLUMN_FROM)));
        setTo(data.getString(data.getColumnIndex(TestContract.RatesEntry.COLUMN_TO)));
        setRate(data.getString(data.getColumnIndex(TestContract.RatesEntry.COLUMN_RATE)));
    }

    protected Rate(Parcel in) {
        from = in.readString();
        to = in.readString();
        rate = in.readString();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getFrom());
        dest.writeString(getTo());
        dest.writeString(getRate());
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestContract.RatesEntry.COLUMN_FROM, getFrom());
        contentValues.put(TestContract.RatesEntry.COLUMN_TO, getTo());
        contentValues.put(TestContract.RatesEntry.COLUMN_RATE, getRate());
        return contentValues;
    }

    @Override
    public String toString() {
        return "from:"+getFrom()+" to:"+getTo()+" rate:"+getRate();
    }
}
