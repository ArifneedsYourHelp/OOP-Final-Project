package Controller;

import Helper.SampleDataHelper;
import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Movie;
import model.Showtime;

import java.util.List;

/**
 * Controller for the client movie details view.
 * Displays movie information, available showtimes, and room assignments.
 * Pre-selects the first available showtime for the movie.
 */
public class ClientMovieController {

    // --- FXML Labels mapped from the provided FXML ---
    @FXML private Label movieNameLabel;
    @FXML private Label movieGenreLabel;
    @FXML private Label movieDurrationLabel;
    @FXML private Label movieRatedLabel;
    @FXML private Label roomNumberLabel;

    // --- FXML Components ---
    @FXML private ListView<String> clientShowTimeList;
    @FXML private Button clientMovieBackButton;

    private Movie currentMovie;
    private List<Showtime> showtimes;
    private Showtime preselectedShowtime;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the action listener for the back button.
     */
    @FXML
    public void initialize() {
        clientMovieBackButton.setOnAction(event -> BackButtonClick());
    }

    /**
     * Handles the back button click to navigate back to the movie list view.
     * It closes the current stage's view and switches to the {@code CLIENT_MOVIE_LIST_VIEW}.
     */
    @FXML
    private void BackButtonClick() {
        Stage stage = (Stage) clientMovieBackButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }

    /**
     * Sets the movie to display and loads its associated data.
     * Populates all movie labels and loads available showtimes.
     *
     * @param movie the movie to display
     */
    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        // Load sample data to get showtimes
        SampleDataHelper.Data data = SampleDataHelper.load();
        this.showtimes = data.getShowtimes();

        // Set movie information in labels
        movieNameLabel.setText(movie.getTitle());
        movieGenreLabel.setText("Genre: " + movie.getGenre());
        movieDurrationLabel.setText("Duration: " + movie.getDuration());
        movieRatedLabel.setText("Rated: " + movie.getRating());

        // Load showtimes and apply preselection
        loadShowtimes();
        applyPreselection();
    }

    /**
     * Loads showtimes for the current movie and populates the ListView.
     * Stores the first matching showtime as the preselected showtime.
     */
    private void loadShowtimes() {
        clientShowTimeList.getItems().clear();
        preselectedShowtime = null;

        for (Showtime showtime : showtimes) {
            if (showtime.getMovie().getTitle().equals(currentMovie.getTitle())) {
                String showtimeDisplay = showtime.getDate() + " at " + showtime.getTime();
                clientShowTimeList.getItems().add(showtimeDisplay);
                if (preselectedShowtime == null) {
                    preselectedShowtime = showtime; // pick the first matching showtime
                }
            }
        }
    }

    /**
     * Applies preselection: selects the first available showtime in the list
     * and sets the room label accordingly.
     */
    private void applyPreselection() {
        if (preselectedShowtime != null) {
            // Select first item in ListView
            if (!clientShowTimeList.getItems().isEmpty()) {
                clientShowTimeList.getSelectionModel().select(0);
            }
            // Set room label (name only)
            String fullRoom = preselectedShowtime.getRoom();
            String roomInfo = fullRoom;
            if (fullRoom != null && fullRoom.contains(" - ")) {
                String[] parts = fullRoom.split(" - ", 2);
                roomInfo = parts.length > 1 ? parts[1] : parts[0];
            }
            roomNumberLabel.setText(roomInfo != null && !roomInfo.isEmpty() ? roomInfo : "No room assigned");
        } else {
            roomNumberLabel.setText("No room assigned");
        }
    }
}