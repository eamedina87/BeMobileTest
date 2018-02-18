package com.medinamobile.bemobiletest.transaction.detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.medinamobile.bemobiletest.R;
import com.medinamobile.bemobiletest.database.TestContract;
import com.medinamobile.bemobiletest.transaction.list.ui.TransactionListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Transaction detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TransactionListActivity}.
 */
public class TransactionDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    private String mSKU;
    private TransactionDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        getIntentData();
        setupToolbar();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            setupDetailFragment();
        }
    }

    private void setupDetailFragment() {
        Bundle arguments = new Bundle();
        arguments.putString(TestContract.TransactionEntry.COLUMN_SKU,
                mSKU);
        fragment = new TransactionDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.product_detail_container, fragment)
                .commit();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null && intent.hasExtra(TestContract.TransactionEntry.COLUMN_SKU)){
            mSKU = intent.getStringExtra(TestContract.TransactionEntry.COLUMN_SKU);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mSKU!=null)
            getSupportActionBar().setTitle(mSKU);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, TransactionListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
