package com.danielcherry.bitcointracker.data.remote;

import com.danielcherry.bitcointracker.data.models.MarketPrice;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketPriceService {

    /**
     * @param format   json/csv
     * @param timespan Durations are represented by concatenating the number of time units
     *                 and the time unit it represents (for example '1year', '3months', etc..).
     *                 Available time units are: minute, hour, day, week and year.
     * @return Single with the requested {@link MarketPrice}
     */
    @GET("/charts/market-price")
    Single<MarketPrice> fetchMarketPrice(@Query("format") String format,
                                         @Query("timespan") String timespan);

}
