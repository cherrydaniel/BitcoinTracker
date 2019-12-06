package com.danielcherry.bitcointracker.ui.marketprice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.danielcherry.bitcointracker.BitcoinTrackerApplication;
import com.danielcherry.bitcointracker.R;
import com.danielcherry.bitcointracker.data.models.MarketPrice;
import com.danielcherry.bitcointracker.data.models.MarketPriceValue;
import com.danielcherry.bitcointracker.data.models.TimeSpan;
import com.danielcherry.bitcointracker.databinding.FragmentMarketPriceBinding;
import com.danielcherry.bitcointracker.ui.main.OnActionBarChangedCallback;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.wildcherryapps.simpleconnectivitydetector.ConnectivityDetector;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MarketPriceFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private MarketPriceViewModel mViewModel;
    private FragmentMarketPriceBinding mBinding;

    // Snackbar to show when remote fetching fails
    private Snackbar mFetchErrorSnackbar;

    // Activity callback, if it implements this interface
    private OnActionBarChangedCallback mOnActionBarChangedCallback;

    public static MarketPriceFragment newInstance() {
        return new MarketPriceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((BitcoinTrackerApplication) getActivity().getApplication()).getApplicationComponent().inject(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_price, container, false);

        if (mOnActionBarChangedCallback != null)
            mOnActionBarChangedCallback.onActionBarChanged(mBinding.toolbar);

        // Initialize components
        initGraphView();

        setHasOptionsMenu(true);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MarketPriceViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());

        initConnectivityDetector();

        // Observe Market Price Data
        mViewModel.getMarketPriceData().observe(getViewLifecycleOwner(), marketPrice -> {
            mFetchErrorSnackbar.dismiss();
            updateGraph(marketPrice);
        });

        // Observe Errors
        mViewModel.getErrorEventData().observe(getViewLifecycleOwner(),
                throwable -> mFetchErrorSnackbar.show());

        // Fetch initial data
        Timber.d("Fetching initial data");
        mViewModel.fetchMarketPriceData();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_market_price, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mViewModel.fetchMarketPriceData();
                return true;
            case R.id.action_show_30_days:
                mViewModel.fetchMarketPriceData(30, TimeSpan.Unit.DAYS);
                return true;
            case R.id.action_show_60_days:
                mViewModel.fetchMarketPriceData(60, TimeSpan.Unit.DAYS);
                return true;
            case R.id.action_show_180_days:
                mViewModel.fetchMarketPriceData(180, TimeSpan.Unit.DAYS);
                return true;
            case R.id.action_show_1_year:
                mViewModel.fetchMarketPriceData(1, TimeSpan.Unit.YEARS);
                return true;
            case R.id.action_show_2_years:
                mViewModel.fetchMarketPriceData(2, TimeSpan.Unit.YEARS);
                return true;
            case R.id.action_show_all_time:
                mViewModel.fetchMarketPriceData(0, TimeSpan.Unit.ALL);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnActionBarChangedCallback)
            mOnActionBarChangedCallback = (OnActionBarChangedCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnActionBarChangedCallback = null;
    }

    /**
     * Initialize GraphView parameters
     */
    private void initGraphView() {

        // Configure GridLabelRenderer
        GridLabelRenderer grid = mBinding.graphView.getGridLabelRenderer();
        grid.setHumanRounding(false, true);
        grid.setLabelFormatter(new DateAsXAxisLabelFormatter(getContext()));

        // Configure Viewport
        mBinding.graphView.getViewport().setScalable(true);
        mBinding.graphView.getViewport().setScrollable(true);

    }

    /**
     * Initialize ConnectivityDetector and mFetchErrorSnackbar
     */
    private void initConnectivityDetector() {

        // No Connection Snackbar
        mFetchErrorSnackbar = Snackbar.make(mBinding.getRoot(),
                R.string.no_connection, Snackbar.LENGTH_LONG)
                .setAction(R.string.ok, v -> mFetchErrorSnackbar.dismiss());

        // Fetch market price when network is back online
        ConnectivityDetector.create(this)
                .onNetworkAvailable(() -> mViewModel.fetchMarketPriceData())
                .bind();
    }

    /**
     * Calculate new graph and Update UI
     *
     * @param marketPrice The new MarketPrice data
     */
    private void updateGraph(MarketPrice marketPrice) {

        GraphView graphView = mBinding.graphView;

        List<MarketPriceValue> values = marketPrice.getValues();

        if (values == null || values.isEmpty())
            return;

        graphView.removeAllSeries();

        DataPoint[] data = new DataPoint[values.size()];

        double maxY = 0d, minY = 0d;
        for (int i = 0; i < values.size(); i++) {
            MarketPriceValue value = values.get(i);

            if (i == 0) {
                maxY = value.getY();
                minY = value.getY();
            } else if (value.getY() > maxY) {
                maxY = value.getY();
            } else if (value.getY() < minY) {
                minY = value.getY();
            }

            // Convert UNIX time to millis
            data[i] = new DataPoint(new Date(value.getX() * 1000L), value.getY());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);

        Viewport viewport = graphView.getViewport();
        viewport.setMinX(data[0].getX());
        viewport.setMaxX(data[data.length - 1].getX());
        viewport.setXAxisBoundsManual(true);

        double margin = (maxY - minY) * 0.05d;

        viewport.setMinY(Math.max(0, minY - margin));
        viewport.setMaxY(maxY + margin);
        viewport.setYAxisBoundsManual(true);

        GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
        gridLabelRenderer.setNumHorizontalLabels(3);
        gridLabelRenderer.setNumVerticalLabels(10);

        graphView.addSeries(series);

        graphView.onDataChanged(true, false);

    }

}
