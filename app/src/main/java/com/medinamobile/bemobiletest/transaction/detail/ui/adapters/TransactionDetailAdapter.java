package com.medinamobile.bemobiletest.transaction.detail.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medinamobile.bemobiletest.R;
import com.medinamobile.bemobiletest.entities.Transaction;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Erick on 2/18/2018.
 */

public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder> {

    private ArrayList<Transaction> mList;


    public TransactionDetailAdapter(ArrayList<Transaction> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_transaction_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = mList.get(position);
        if (transaction.isInEuro()){
            holder.amount.setVisibility(View.INVISIBLE);
        } else {
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(transaction.getAmountString());
        }
        holder.amountEuro.setText(transaction.getAmountEuroString());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.transaction_amount_euro)
        TextView amountEuro;
        @BindView(R.id.transaction_amount)
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
