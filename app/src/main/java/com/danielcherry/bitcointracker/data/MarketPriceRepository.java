package com.danielcherry.bitcointracker.data;

import com.danielcherry.bitcointracker.data.models.MarketPrice;
import com.danielcherry.bitcointracker.data.remote.MarketPriceRemoteSource;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * The single source of truth for {@link MarketPrice} data models.
 * Currently contains only a remote source, can contain a local source with either
 * internal storage or SQLite persistence implementation.
 */
public class MarketPriceRepository {

    private final MarketPriceRemoteSource remoteSource;

    @Inject
    public MarketPriceRepository(MarketPriceRemoteSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    /**
     * All logic for fetching MarketPrice data will be handled by this method.
     */
    public Single<MarketPrice> fetchMarketPrice(String timeSpan) {
        Timber.d("Begin fetching. timeSpan = %s", timeSpan);
        return remoteSource.getMarketPrice(timeSpan);
    }

}
