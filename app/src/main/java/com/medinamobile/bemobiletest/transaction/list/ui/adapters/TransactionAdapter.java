package com.medinamobile.bemobiletest.transaction.list.ui.adapters;

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
 * Created by Erick on 2/17/2018.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    public interface TransactionCallbacks{
        void onTransactionClicked(Transaction transaction);
    }

    private ArrayList<Transaction> mList;
    private TransactionCallbacks mCallbacks;

    public TransactionAdapter(ArrayList<Transaction> mList, TransactionCallbacks mCallbacks) {
        this.mList = mList;
        this.mCallbacks = mCallbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_transaction_sku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transaction transaction = mList.get(position);
        holder.title.setText(transaction.getSku());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onTransactionClicked(transaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transaction_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
