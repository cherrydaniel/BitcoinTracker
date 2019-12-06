package com.danielcherry.bitcointracker.data.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convenience data model for formatting and parsing the "timespan" parameter requested by the API.
 */
public class TimeSpan {

    public enum Unit {
        MINUTES("minutes"),
        HOURS("hours"),
        DAYS("days"),
        WEEKS("weeks"),
        MONTHS("months"),
        YEARS("years"),
        ALL("all");

        private String name;

        Unit(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }

        public static Unit findByName(String name) {
            Objects.requireNonNull(name, "name == null");
            for (Unit value : Unit.values()) {
                if (value.name.equalsIgnoreCase(name))
                    return value;
            }
            throw new IllegalArgumentException("Unable to find Unit by name: " + name);
        }

    }

    private int quantity;
    private Unit unit;

    public TimeSpan(int quantity, @NonNull Unit unit) {
        if (quantity < 0) throw new IllegalArgumentException("quantity can't be a negative number");
        this.unit = Objects.requireNonNull(unit, "unit == null");
        this.quantity = quantity;
    }

    // Logical equals method that checks equality of the unique TimeSpan string representation
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        return obj instanceof TimeSpan && this.toString().equals(obj.toString());
    }

    // Logical hashCode method based on the unique TimeSpan string representation
    @Override
    public int hashCode() {
        return 31 * toString().hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return unit == Unit.ALL ? Unit.ALL.toString() : quantity + unit.toString();
    }

    public String toPrettyString() {

        if (unit == Unit.ALL)
            return "All Time";

        String unitName = unit.name;

        // Capitalize
        unitName = unitName.substring(0, 1).toUpperCase() + unitName.substring(1);

        if (quantity == 1 && unitName.endsWith("s"))
            unitName = unitName.substring(0, unitName.length() - 1);

        return quantity + " " + unitName;

    }

    public static TimeSpan fromString(String s) {
        Objects.requireNonNull(s, "s == null");

        if (s.equalsIgnoreCase("all"))
            return new TimeSpan(0, Unit.ALL);

        Pattern pattern = Pattern.compile("(\\d+)([a-z]+)");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.find() || matcher.groupCount() != 2)
            throw new IllegalArgumentException("String incompatible with TimeSpan format: " + s);

        int quantity = Integer.valueOf(matcher.group(1));
        Unit unit = Unit.findByName(matcher.group(2));

        return new TimeSpan(quantity, unit);
    }

    public static TimeSpan getDefault() {
        return new TimeSpan(30, Unit.DAYS);
    }

}
