package com.danielcherry.bitcointracker.data.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeSpanFormatTest {

    @Test
    public void timeSpanFromStringTest() {
        assertEquals(TimeSpan.fromString("1minutes").toPrettyString(), "1 Minute");
        assertEquals(TimeSpan.fromString("45minutes").toPrettyString(), "45 Minutes");
        assertEquals(TimeSpan.fromString("1hours").toPrettyString(), "1 Hour");
        assertEquals(TimeSpan.fromString("2hours").toPrettyString(), "2 Hours");
        assertEquals(TimeSpan.fromString("1days").toPrettyString(), "1 Day");
        assertEquals(TimeSpan.fromString("60days").toPrettyString(), "60 Days");
        assertEquals(TimeSpan.fromString("1weeks").toPrettyString(), "1 Week");
        assertEquals(TimeSpan.fromString("13weeks").toPrettyString(), "13 Weeks");
        assertEquals(TimeSpan.fromString("1months").toPrettyString(), "1 Month");
        assertEquals(TimeSpan.fromString("6months").toPrettyString(), "6 Months");
        assertEquals(TimeSpan.fromString("1years").toPrettyString(), "1 Year");
        assertEquals(TimeSpan.fromString("2years").toPrettyString(), "2 Years");
        assertEquals(TimeSpan.fromString("all").toPrettyString(), "All Time");
    }

}