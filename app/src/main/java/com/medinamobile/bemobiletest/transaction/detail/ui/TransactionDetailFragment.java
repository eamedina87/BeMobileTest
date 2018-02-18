package com.medinamobile.bemobiletest.transaction.detail.ui;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.medinamobile.bemobiletest.R;
import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.dummy.DummyContent;
import com.medinamobile.bemobiletest.entities.Rate;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.detail.TransactionDetailPresenter;
import com.medinamobile.bemobiletest.transaction.detail.TransactionDetailPresenterImpl;
import com.medinamobile.bemobiletest.transaction.detail.ui.adapters.TransactionDetailAdapter;
import com.medinamobile.bemobiletest.transaction.list.ui.TransactionListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Transaction detail screen.
 * This fragment is either contained in a {@link TransactionListActivity}
 * in two-pane mode (on tablets) or a {@link TransactionDetailActivity}
 * on handsets.
 */
public class TransactionDetailFragment extends Fragment implements TransactionDetailView {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    @BindView(R.id.transactions_list)
    RecyclerView mList;
    @BindView(R.id.transactions_total)
    TextView mTotal;
    @BindView(R.id.detail_message)
    TextView mMessage;
    @BindView(R.id.detail_progressbar)
    ProgressBar mProgressBar;


    private String mSKU;
    private TransactionDetailPresenter mPresenter;

    public TransactionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleArguments();

    }

    private void getBundleArguments() {
        if (getArguments().containsKey(TestContract.TransactionEntry.COLUMN_SKU)) {
            mSKU = getArguments().getString(TestContract.TransactionEntry.COLUMN_SKU);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mSKU);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TransactionDetailPresenterImpl(this, (TransactionDetailActivity) getActivity(), mSKU);
        mPresenter.onCreate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTransactionsSuccess(ArrayList<Transaction> transactions) {
        TransactionDetailAdapter mAdapter = new TransactionDetailAdapter(transactions);
        mList.setAdapter(mAdapter);
        mPresenter.getTotalSum(transactions);
    }

    @Override
    public void onTransactionsError(String error) {

    }

    @Override
    public void onRatesError(String error) {

    }

    @Override
    public void setTotalSum(String sum) {
        mTotal.setText(sum + Transaction.EURO_SIGN);
    }
}
