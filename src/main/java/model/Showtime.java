package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents a scheduled showing of a {@link Movie} including date, time, room and seating info.
 * Implements {@link Comparable} so showtimes can be ordered chronologically.
 */
public class Showtime implements Comparable<Showtime> {


    /** Movie being shown. */
    private Movie movie;
    /** Calendar date of the showing. */
    private LocalDate date;
    /** Original time string as entered by user. */
    private String time; // original string form
    /** Parsed {@link LocalTime} version of the time string. */
    private LocalTime localTime; // parsed
    /** Room / auditorium identifier. */
    private String room;
    /** Remaining seats available for reservation. */
    private int availableSeats;
    /** Total seating capacity of the room. */
    private int totalCapacity;
    /** Flag indicating whether all seats have been reserved. */
    private boolean soldOut;

    /**
     * Creates a showtime with a default capacity of 100 seats.
     * @param movie non-null movie
     * @param date non-null date
     * @param time textual time (supports HH:MM, HH:MM:SS or h:mm AM/PM)
     * @param room room label
     * @throws IllegalArgumentException if movie/date null
     */
    public Showtime(Movie movie, LocalDate date, String time, String room) {
        if (movie == null) throw new IllegalArgumentException("Movie cannot be null");
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.localTime = parseTime(time);
        this.room = room;
        this.totalCapacity = 100;
        this.availableSeats = totalCapacity;
        this.soldOut = false;
    }

    /**
     * Creates a showtime with a custom total capacity.
     * @param movie movie value
     * @param date date value
     * @param time time string
     * @param room room label
     * @param totalCapacity capacity (>=0 assumed; no validation here)
     */
    public Showtime(Movie movie, LocalDate date, String time, String room, int totalCapacity) {
        this(movie, date, time, room);
        this.totalCapacity = totalCapacity;
        this.availableSeats = totalCapacity;
    }


    /**
     * Parses a time string into {@link LocalTime}. Accepts 24h (HH:MM or HH:MM:SS) and 12h (h:mm AM/PM).
     * Invalid input returns 12:00.
     * @param timeStr input time string
     * @return parsed time or noon fallback
     */
    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return LocalTime.of(12, 0);
        try {
            if (timeStr.contains("AM") || timeStr.contains("PM")) {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("h:mm a");
                return LocalTime.parse(timeStr.toUpperCase().replace("A.M", "AM").replace("P.M", "PM"), f);
            }
            return LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) { return LocalTime.of(12, 0); }
    }

    /** @return movie */
    public Movie getMovie() { return movie; }
    /** Sets movie (non-null). */
    public void setMovie(Movie movie) { if (movie == null) throw new IllegalArgumentException("Movie cannot be null"); this.movie = movie; }
    /** @return date */
    public LocalDate getDate() { return date; }
    /** Sets date (non-null). */
    public void setDate(LocalDate date) { if (date == null) throw new IllegalArgumentException("Date cannot be null"); this.date = date; }
    /** @return original time string */
    public String getTime() { return time; }
    /** Updates time string and reparses {@link #localTime}. */
    public void setTime(String time) { this.time = time; this.localTime = parseTime(time); }
    /** @return parsed local time */
    public LocalTime getLocalTime() { return localTime; }
    /** @return room label */
    public String getRoom() { return room; }
    /** Sets room label. */
    public void setRoom(String room) { this.room = room; }
    /** @return remaining seats */
    public int getAvailableSeats() { return availableSeats; }
    /** Sets available seats and updates sold-out flag. */
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; this.soldOut = availableSeats <= 0; }
    /** @return total capacity */
    public int getTotalCapacity() { return totalCapacity; }
    /** Sets total capacity. */
    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }
    /** @return true if sold out */
    public boolean isSoldOut() { return soldOut; }
    /** Manually sets soldOut flag. */
    public void setSoldOut(boolean soldOut) { this.soldOut = soldOut; }

    /**
     * Attempts to reserve a number of seats.
     * @param numberOfSeats seats requested (>0)
     * @return true if reservation succeeded
     */
    public boolean reserveSeats(int numberOfSeats) {
        if (numberOfSeats <= 0) return false;
        if (availableSeats >= numberOfSeats) { availableSeats -= numberOfSeats; if (availableSeats == 0) soldOut = true; return true; }
        return false;
    }

    /**
     * Cancels a prior reservation, freeing seats.
     * @param numberOfSeats seats to restore (>0)
     */
    public void cancelReservation(int numberOfSeats) {
        if (numberOfSeats > 0) {
            availableSeats += numberOfSeats;
            if (availableSeats > totalCapacity) availableSeats = totalCapacity;
            if (availableSeats > 0) soldOut = false;
        }
    }

    /**
     * Compares by date then by parsed local time.
     * @param other another showtime
     * @return ordering value
     */
    @Override public int compareTo(Showtime other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) return dateComparison;
        if (this.localTime != null && other.localTime != null) return this.localTime.compareTo(other.localTime);
        return 0;
    }

    /** @return formatted human-readable date/time string */
    public String getFormattedDateTime() { DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM dd, yyyy"); return date.format(df) + " at " + time; }

    /** @return debug-friendly representation */
    @Override public String toString() { return "Showtime{" + movie.getTitle() + ", " + date + ", " + time + ", room=" + room + ", available=" + availableSeats + "/" + totalCapacity + '}'; }

    /** Equality based on movie, date, time, and room. */
    @Override public boolean equals(Object obj) { if (this == obj) return true; if (obj == null || getClass() != obj.getClass()) return false; Showtime s = (Showtime) obj; return movie.equals(s.movie) && date.equals(s.date) && time.equals(s.time) && room.equals(s.room); }
    /** Hash uses movie, date, time, room fields. */
    @Override public int hashCode() { return java.util.Objects.hash(movie, date, time, room); }
}
