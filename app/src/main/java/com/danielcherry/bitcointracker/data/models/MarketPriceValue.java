package com.danielcherry.bitcointracker.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Graph values of market price over time
 */
public class MarketPriceValue {

    /**
     * UNIX time
     */
    @SerializedName("x")
    private long x;

    /**
     * Market price value in USD
     */
    @SerializedName("y")
    private double y;

    public MarketPriceValue() {}

    public MarketPriceValue(long x, double y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
