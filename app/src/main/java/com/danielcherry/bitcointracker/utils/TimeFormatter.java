package com.danielcherry.bitcointracker.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for formatting time milliseconds to String
 */
public final class TimeFormatter {

    private TimeFormatter() {
        throw new AssertionError();
    }

    private static final DateFormat sdf = SimpleDateFormat.getDateTimeInstance();

    public static String format(long millis) {
        return sdf.format(new Date(millis));
    }

}
