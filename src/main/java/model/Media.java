package model;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base class representing a generic media item in the cinema system.
 * <p>
 * This class demonstrates core OOP principles:
 * <ul>
 *   <li><b>Encapsulation:</b> Private fields with public getters/setters controlling validation.</li>
 *   <li><b>Inheritance:</b> Extended by concrete media types such as {@link Movie}.</li>
 *   <li><b>Polymorphism:</b> Subclasses implement {@link #getDescription()} and {@link #getMediaType()}.</li>
 * </ul>
 * Each media item has a generated unique identifier, a title, genre, and a duration
 * stored internally in minutes.
 * </p>
 * @author Student
 * @since 1.0
 */
public abstract class Media {
    /** Unique immutable identifier generated at construction time. */
    private final String id = UUID.randomUUID().toString();
    /** Human-readable title of the media item. */
    private String title;
    /** Genre/category (e.g., Action, Comedy). */
    private String genre;
    /** Total duration in minutes. */
    private int durationMinutes;

    /**
     * Constructs a new media item.
     * @param title  non-empty title
     * @param genre  genre label (may be null/empty if unknown)
     * @param durationMinutes duration in minutes (must be >= 0)
     * @throws IllegalArgumentException if title empty or duration negative
     */
    protected Media(String title, String genre, int durationMinutes) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        if (durationMinutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    /** @return unique identifier string */
    public String getId() { return id; }
    /** @return media title */
    public String getTitle() { return title; }
    /**
     * Updates the title.
     * @param title new non-empty title
     * @throws IllegalArgumentException if null or blank
     */
    public void setTitle(String title) { if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty"); this.title = title; }
    /** @return genre string */
    public String getGenre() { return genre; }
    /** Sets genre (no validation). */
    public void setGenre(String genre) { this.genre = genre; }
    /** @return duration in minutes */
    public int getDurationMinutes() { return durationMinutes; }
    /**
     * Sets duration.
     * @param durationMinutes minutes >= 0
     * @throws IllegalArgumentException if negative
     */
    public void setDurationMinutes(int durationMinutes) { if (durationMinutes < 0) throw new IllegalArgumentException("Duration cannot be negative"); this.durationMinutes = durationMinutes; }

    /**
     * Returns the duration formatted as HH:MM:SS (seconds fixed to 00).
     * @return formatted duration string
     */
    public String getFormattedDuration() { int hours = durationMinutes / 60; int minutes = durationMinutes % 60; return String.format("%02d:%02d:00", hours, minutes); }

    /**
     * Produces a human-readable description of the media item.
     * Implemented by subclasses to include specific fields.
     * @return description string
     */
    public abstract String getDescription();
    /**
     * @return type label (e.g., "Movie") for this media instance.
     */
    public abstract String getMediaType();

    /**
     * Simplified placeholder method that would calculate an end time.
     * @param startTime textual start time (unused in current stub)
     * @return message containing duration info
     */
    public String calculateEndTime(String startTime) { return "End time after " + durationMinutes + " minutes"; }

    /** Equality based solely on generated id. */
    @Override public boolean equals(Object o) { if (this == o) return true; if (o == null || getClass() != o.getClass()) return false; Media media = (Media) o; return Objects.equals(id, media.id); }
    /** Hash code consistent with {@link #equals(Object)}. */
    @Override public int hashCode() { return Objects.hash(id); }
    /** @return simple string representation including type and title */
    @Override public String toString() { return getMediaType() + ": " + title; }
}
