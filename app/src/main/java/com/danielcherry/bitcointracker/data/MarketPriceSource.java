package com.danielcherry.bitcointracker.data;

import com.danielcherry.bitcointracker.data.models.MarketPrice;

import io.reactivex.Single;

/**
 * Specific interface that is to be implemented by any local or remote source
 * that provides MarketPrice data based on requested time span.
 */
public interface MarketPriceSource {

    /**
     * @return RxJava Single with MarketPrice data according to the requested {@param timeSpan}.
     */
    Single<MarketPrice> getMarketPrice(String timeSpan);

}
