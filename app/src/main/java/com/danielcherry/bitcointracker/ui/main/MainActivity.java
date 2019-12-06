package com.danielcherry.bitcointracker.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.danielcherry.bitcointracker.R;
import com.danielcherry.bitcointracker.ui.marketprice.MarketPriceFragment;

/**
 * Main Activity used as a mediator between OS callback events and fragments.
 */
public class MainActivity extends AppCompatActivity implements OnActionBarChangedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, MarketPriceFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public void onActionBarChanged(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

}
