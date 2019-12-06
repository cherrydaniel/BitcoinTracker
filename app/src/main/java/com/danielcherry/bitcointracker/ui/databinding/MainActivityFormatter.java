package com.danielcherry.bitcointracker.ui.databinding;

import com.danielcherry.bitcointracker.data.models.TimeSpan;
import com.danielcherry.bitcointracker.utils.TimeFormatter;

/**
 * Utility class for MainActivity databinding
 */
public final class MainActivityFormatter {

    private MainActivityFormatter() {
        throw new AssertionError();
    }

    public static String formatToolbarTitle(String name, String timeSpan) {
        if (name != null && timeSpan != null) {
            try {
                return name + " - " + TimeSpan.fromString(timeSpan).toPrettyString();
            } catch (Exception ignored) {}
        }
        return "BitcoinTracker";
    }

    public static String formatToolbarSubtitle(long time) {
        return time == 0L ? null : "Last fetched: " + TimeFormatter.format(time);
    }

}
