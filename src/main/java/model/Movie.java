package model;

import java.time.Year;

/**
 * Concrete media type representing a movie.
 * <p>Extends {@link Media} adding rating, director and release year.
 * Demonstrates inheritance (from Media) and polymorphism by implementing
 * {@link #getDescription()} and {@link #getMediaType()}.</p>
 */
public class Movie extends Media {
    /** Age/content rating (e.g., G, PG, PG-13). */
    private String rating;
    /** Director name (may be "Unknown"). */
    private String director;
    /** Year the movie was released. */
    private int releaseYear;

    /**
     * Primary constructor accepting a duration string in HH:MM:SS form.
     * @param title movie title
     * @param genre genre label
     * @param duration formatted duration string (HH:MM or HH:MM:SS); invalid parses to 0
     * @param rating age rating
     */
    public Movie(String title, String genre, String duration, String rating) {
        super(title, genre, parseDurationToMinutes(duration));
        this.rating = rating;
        this.director = "Unknown";
        this.releaseYear = Year.now().getValue();
    }

    /**
     * Secondary constructor accepting explicit minutes and full metadata.
     * @param title movie title
     * @param genre genre label
     * @param durationMinutes total duration in minutes
     * @param rating age rating
     * @param director director name
     * @param releaseYear release year
     */
    public Movie(String title, String genre, int durationMinutes, String rating, String director, int releaseYear) {
        super(title, genre, durationMinutes);
        this.rating = rating;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    /**
     * Parses a duration string (HH:MM or HH:MM:SS) into minutes.
     * Invalid formats return 0.
     * @param duration duration string
     * @return minutes (>=0)
     */
    private static int parseDurationToMinutes(String duration) {
        if (duration == null || duration.isEmpty()) return 0;
        try {
            String[] parts = duration.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            return hours * 60 + minutes;
        } catch (NumberFormatException e) { return 0; }
    }

    /** @return rating string */
    public String getRating() { return rating; }
    /** Sets rating (no validation). */
    public void setRating(String rating) { this.rating = rating; }

    /**
     * Backward-compatible getter returning formatted duration.
     * @return duration as HH:MM:SS
     */
    public String getDuration() { return getFormattedDuration(); }
    /** Sets duration from formatted string. */
    public void setDuration(String duration) { setDurationMinutes(parseDurationToMinutes(duration)); }

    /** @return director name */
    public String getDirector() { return director; }
    /** Sets director name. */
    public void setDirector(String director) { this.director = director; }

    /** @return release year */
    public int getReleaseYear() { return releaseYear; }
    /** Sets release year. */
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    /**
     * Provides a detailed description including title, year, director, rating and genre.
     * @return description string
     */
    @Override public String getDescription() { return getTitle() + " (" + releaseYear + ") - " + director + " | Rated: " + rating + " | Genre: " + getGenre(); }
    /** @return the literal string "Movie" */
    @Override public String getMediaType() { return "Movie"; }

    /** @return true if movie is rated G or PG (child friendly heuristic) */
    public boolean isChildFriendly() { return "G".equalsIgnoreCase(rating) || "PG".equalsIgnoreCase(rating); }
    /** @return true if released within the last 2 years */
    public boolean isNewRelease() { return Year.now().getValue() - releaseYear <= 2; }
}
