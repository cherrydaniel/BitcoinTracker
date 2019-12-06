package com.danielcherry.bitcointracker.di.modules;

import com.danielcherry.bitcointracker.data.remote.MarketPriceService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MarketPriceServiceModule {

    @Provides
    MarketPriceService provideMarketPriceService(Retrofit retrofit) {
        return retrofit.create(MarketPriceService.class);
    }

}
