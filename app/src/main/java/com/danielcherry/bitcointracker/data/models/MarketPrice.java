package com.danielcherry.bitcointracker.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Market price over time. Compatible with the API response.
 */
public class MarketPrice {

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;

    @SerializedName("unit")
    private String unit;

    @SerializedName("period")
    private String period;

    @SerializedName("description")
    private String description;

    @SerializedName("values")
    private List<MarketPriceValue> values;

    /**
     * Timespan of the network request
     * Note: This field is not present in the response, we add it manually based on the request
     */
    @SerializedName("timespan")
    private String timeSpan;

    /**
     * Time of the network response
     * Note: This field is not present in the response, we add it manually based on the request
     */
    @SerializedName("time")
    private long time;

    public MarketPrice() {}

    public MarketPrice(String status, String name, String unit, String period, String description,
                       List<MarketPriceValue> values, String timeSpan, long time) {
        this.status = status;
        this.name = name;
        this.unit = unit;
        this.period = period;
        this.description = description;
        this.values = values;
        this.timeSpan = timeSpan;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public List<MarketPriceValue> getValues() {
        return values;
    }

    public void setValues(List<MarketPriceValue> values) {
        this.values = values;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
