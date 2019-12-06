package com.danielcherry.bitcointracker.data.remote;

import com.danielcherry.bitcointracker.data.models.MarketPrice;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Since the Blockchain API doesn't return all data we need in the response
 * (timespan, for instance), we use this interceptor to get some of it from the request
 */
public class MarketPriceInterceptor implements Interceptor {

    private static final String PARAM_TIMESPAN = "timespan";

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String timeSpan = request.url().queryParameter(PARAM_TIMESPAN);

        Response response = chain.proceed(request);

        Gson gson = new Gson();
        MarketPrice marketPrice = gson
                .fromJson(Objects.requireNonNull(response.body()).string(), MarketPrice.class);
        marketPrice.setTimeSpan(timeSpan);
        marketPrice.setTime(System.currentTimeMillis());

        ResponseBody body = ResponseBody.create(response.body().contentType(), gson.toJson(marketPrice));
        return response.newBuilder().body(body).build();
    }

}
