package Helper;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class that provides sample/seed data for the Cinema application.
 * Call SampleDataHelper.load() once at startup to get pre-populated lists of
 * Rooms, Movies, Clients, and Showtimes matching your Sample_Data.txt.
 */
public class SampleDataHelper {

    private SampleDataHelper() { /* static helper, no instantiation */ }

    /**
     * Container holding all loaded sample data.
     */
    public static class Data {
        private final List<Room> rooms;
        private final List<Movie> movies;
        private final List<Showtime> showtimes;

        public Data(List<Room> rooms, List<Movie> movies, List<Showtime> showtimes) {
            this.rooms = rooms;
            this.movies = movies;
            this.showtimes = showtimes;
        }

        public List<Room> getRooms() { return rooms; }
        public List<Movie> getMovies() { return movies; }
        public List<Showtime> getShowtimes() { return showtimes; }
    }

    /**
     * Loads and returns sample data matching your Sample_Data.txt file.
     * This uses your existing Movie, Client, and Showtime classes.
     *
     * @return Data object containing lists of rooms, movies, clients, showtimes
     */
    public static Data load() {
        // ========== ROOMS ==========
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("R101", "Room 1", 200));
        rooms.add(new Room("R102", "Room 2", 120));
        rooms.add(new Room("R103", "Room 3", 50));
        rooms.add(new Room("R104", "Room 4", 150));
        rooms.add(new Room("R105", "Room 5", 120));

        // Map for quick lookup by roomId
        Map<String, Room> roomMap = new HashMap<>();
        for (Room r : rooms) {
            roomMap.put(r.getRoomId(), r);
        }

        // ========== MOVIES ==========
        // Using your existing Movie class which extends Media
        // Movie(String title, String genre, String duration, String rating)
        // Duration format: "HH:MM" -> your Movie parses it to minutes
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Sci-Fi", "2:28", "PG-13"));
        movies.add(new Movie("The Dark Knight", "Action", "2:32", "PG-13"));
        movies.add(new Movie("La La Land", "Musical", "2:08", "PG"));
        movies.add(new Movie("Parasite", "Thriller", "2:12", "R"));

        // Map for quick lookup by index (M001 = index 0, etc.)
        Map<String, Movie> movieMap = new HashMap<>();
        for (int i = 0; i < movies.size(); i++) {
            String movieId = String.format("M%03d", i + 1); // M001, M002, ...
            movieMap.put(movieId, movies.get(i));
        }

        // ========== CLIENTS (Users) ==========
        // Reuse the clients already registered in the system (e.g., via login screen)
        List<Client> clients = Client.getRegisteredClientsSnapshot();

        // ========== SHOWTIMES ==========
        // Using your existing Showtime class: Showtime(Movie movie, LocalDate date, String time, String room, int capacity)
        // Note: Your sample dates are invalid (e.g., 2025-11-32).  I'll use valid dates instead.
        List<Showtime> showtimes = new ArrayList<>();

        // s001: M001 (Inception), R101 (IMAX Hall), capacity 200
        showtimes.add(new Showtime(
                movieMap.get("M001"),
                LocalDate.of(2025, 12, 1),
                "8:30 PM",
                "R101 - Room 1",
                roomMap.get("R101").getCapacity()
        ));

        // s002: M002 (The Dark Knight), R102 (Classic), capacity 120
        showtimes.add(new Showtime(
                movieMap.get("M002"),
                LocalDate.of(2025, 12, 2),
                "2:00 PM",
                "R102 - Room 2",
                roomMap.get("R102").getCapacity()
        ));

        // s003: M003 (La La Land), R103 (VIP Lounge), capacity 50
        showtimes.add(new Showtime(
                movieMap.get("M003"),
                LocalDate.of(2025, 12, 3),
                "11:30 PM",
                "R103 - Room 3",
                roomMap.get("R103").getCapacity()
        ));

        // s004: M004 (Parasite), R104 (Standard), capacity 150
        showtimes.add(new Showtime(
                movieMap.get("M004"),
                LocalDate.of(2025, 12, 4),
                "5:00 PM",
                "R104 - Room 4",
                roomMap.get("R104").getCapacity()
        ));
        return new Data(rooms, movies, showtimes);
    }
}