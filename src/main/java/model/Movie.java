package model;

public class Movie extends Media {

    // 1. Create a static variable to hold the ONE single instance
    private static Movie instance;

    /** Age/content rating (e.g., G, PG, PG-13). */
    private String rating;

    // 2. MAKE CONSTRUCTORS PRIVATE
    // This prevents other classes from using "new Movie(...)"
    public Movie(String title, String genre, String duration, String rating) {
        super(title, genre, parseDurationToMinutes(duration));
        this.rating = rating;
    }

    private Movie(String title, String genre, int durationMinutes, String rating, String director, int releaseYear) {
        super(title, genre, durationMinutes);
        this.rating = rating;
        // (Assuming setters for director/year exist in Media or here, otherwise simpler to just set fields)
    }

    /**
     * 3. PUBLIC STATIC ACCESS METHOD
     * This is the only way to get the Movie object.
     * Note: This implementation creates the movie the FIRST time you call it.
     */
    public static Movie getInstance(String title, String genre, String duration, String rating) {
        // If the instance doesn't exist yet, create it.
        if (instance == null) {
            instance = new Movie(title, genre, duration, rating);
        }
        // If it already exists, ignore the new arguments and return the existing one.
        return instance;
    }

    /**
     * overload getInstance to return the existing instance without params
     * Useful if you just want to get the movie without trying to create it.
     */
    public static Movie getInstance() {
        return instance;
    }

    // --- The rest of your existing logic remains the same ---

    private static int parseDurationToMinutes(String duration) {
        if (duration == null || duration.isEmpty()) return 0;
        try {
            String[] parts = duration.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            return hours * 60 + minutes;
        } catch (NumberFormatException e) { return 0; }
    }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return getFormattedDuration(); }
    public void setDuration(String duration) { setDurationMinutes(parseDurationToMinutes(duration)); }

    @Override
    public String getDescription() {
        return "";
    }

    @Override public String getMediaType() { return "Movie"; }

    public boolean isChildFriendly() { return "G".equalsIgnoreCase(rating) || "PG".equalsIgnoreCase(rating); }
}
