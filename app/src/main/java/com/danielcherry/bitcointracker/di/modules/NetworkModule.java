package com.danielcherry.bitcointracker.di.modules;

import com.danielcherry.bitcointracker.data.remote.ApiClient;
import com.danielcherry.bitcointracker.data.remote.MarketPriceService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    @Provides
    Retrofit provideRetrofit() {
        return ApiClient.initClient();
    }

}
