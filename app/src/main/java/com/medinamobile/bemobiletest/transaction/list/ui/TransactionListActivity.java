package com.medinamobile.bemobiletest.transaction.list.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.entities.Transaction;
import com.medinamobile.bemobiletest.transaction.detail.ui.TransactionDetailActivity;
import com.medinamobile.bemobiletest.R;
import com.medinamobile.bemobiletest.transaction.list.TransactionListPresenter;
import com.medinamobile.bemobiletest.transaction.list.TransactionListPresenterImpl;
import com.medinamobile.bemobiletest.transaction.list.ui.adapters.TransactionAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a mList of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a mList of items, which when touched,
 * lead to a {@link TransactionDetailActivity} representing
 * item details. On tablets, the activity presents the mList of items and
 * item details side-by-side using two vertical panes.
 */
public class TransactionListActivity extends AppCompatActivity implements TransactionListView, TransactionAdapter.TransactionCallbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.product_list)
    RecyclerView mList;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.products_msg)
    TextView mMessage;
    @Nullable @BindView(R.id.product_detail_container)
    View detailContainer;

    private TransactionListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        setupToolbar();
        //setupRecyclerView();
        checkTwoPane();

        mPresenter = new TransactionListPresenterImpl(this, this);
        mPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    private void checkTwoPane() {
        if (detailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());
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
    public void onTransactionListSuccess(ArrayList<Transaction> products) {
        mList.setAdapter(null);
        mMessage.setVisibility(View.INVISIBLE);
        mList.setVisibility(View.VISIBLE);
        TransactionAdapter mAdapter = new TransactionAdapter(products, this);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onTransactionListEmpty() {
        mMessage.setVisibility(View.VISIBLE);
        mList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTransactionListError(String error) {
        mMessage.setVisibility(View.VISIBLE);
        mMessage.setText(error);
        mList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTransactionClicked(Transaction transaction) {
        Snackbar.make(mList, transaction.toString(), Snackbar.LENGTH_SHORT).show();
        if (mTwoPane){
            //Replace fragment in container
        } else {
            //Open Detail Activity
            Intent intent = new Intent(this, TransactionDetailActivity.class);
            Bundle args = new Bundle();
            args.putString(TestContract.TransactionEntry.COLUMN_SKU, transaction.getSku());
            intent.putExtras(args);
            startActivity(intent);
        }
    }
}
