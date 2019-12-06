package com.danielcherry.bitcointracker.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {

    /**
     * Blockchain base URL
     */
    private static final String BASE_URL = "https://api.blockchain.info";

    /**
     * Builds a Retrofit client with a logging interceptor, {@link MarketPriceInterceptor},
     * {@link GsonConverterFactory} and {@link RxJava2CallAdapterFactory}
     * @return The Retrofit client
     */
    public static Retrofit initClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new MarketPriceInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

}
