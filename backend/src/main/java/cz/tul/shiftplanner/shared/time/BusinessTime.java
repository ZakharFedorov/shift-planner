package cz.tul.shiftplanner.shared.time;

import java.time.ZoneId;

/**
 * Time conventions of the application.
 *
 * <p>The reception operates in Prague local time (including DST transitions). Points in time are
 * always represented as {@link java.time.Instant} and stored as {@code timestamptz}; the zone is
 * used only to convert between local calendar values (dates, shift start times) and instants.
 */
public final class BusinessTime {

    /** Time zone in which shifts, calendar days and pay periods are defined. */
    public static final ZoneId ZONE = ZoneId.of("Europe/Prague");

    private BusinessTime() {
    }
}
