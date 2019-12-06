package com.danielcherry.bitcointracker.data.remote;

import com.danielcherry.bitcointracker.data.MarketPriceSource;
import com.danielcherry.bitcointracker.data.models.MarketPrice;
import com.danielcherry.bitcointracker.data.models.TimeSpan;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Provides {@link MarketPrice} data models from remote source
 */
public class MarketPriceRemoteSource implements MarketPriceSource {

    private final MarketPriceService service;

    @Inject
    public MarketPriceRemoteSource(MarketPriceService service) {
        this.service = service;
    }

    @Override
    public Single<MarketPrice> getMarketPrice(String timeSpan) {
        if (timeSpan == null) timeSpan = TimeSpan.getDefault().toString();
        Timber.d("Fetching remote MarketPrice. timeSpan = %s", timeSpan);
        return service.fetchMarketPrice("json", timeSpan);
    }

}
