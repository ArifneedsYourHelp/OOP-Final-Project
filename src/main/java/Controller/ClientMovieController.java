package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Movie;
import model.Showtime;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ViewSwitcher} class provides a static utility for managing and
 * switching between different views (scenes) within the main application window (Stage).
 * This centralizes the logic for loading FXML files and setting them as the new scene.
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

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the action listener for the back button.
     */
    @FXML
    public void initialize() {
        clientMovieBackButton.setOnAction(event -> BackButtonClick());
    }

    /**
     * Sets the movie and its associated showtimes to be displayed in the view.
     * This method is called by the parent controller (`ClientMovieListController`).
     * @param movie The selected Movie object.
     * @param showtimes The list of Showtime objects for this movie.
     */
    public void setMovie(Movie movie, List<Showtime> showtimes) { // ADDED METHOD
        // 1. Display Movie Details
        movieNameLabel.setText("Title: " + movie.getTitle());
        movieGenreLabel.setText("Genre: " + movie.getGenre());
        movieDurrationLabel.setText("Duration: " + movie.getDuration());
        movieRatedLabel.setText("Rating: " + movie.getRating());

        // 2. Display Showtimes
        if (showtimes != null && !showtimes.isEmpty()) {
            // Format the showtime data for display in the ListView: "Date at Time in Room X"
            List<String> showtimeStrings = showtimes.stream()
                    .map(st -> st.getFormattedDateTime() + " in " + st.getRoom())
                    .collect(Collectors.toList());

            clientShowTimeList.getItems().setAll(showtimeStrings);
        } else {
            clientShowTimeList.getItems().setAll("No showtimes available for this movie.");
        }

        // Clearing the single 'roomNumberLabel' as it's not applicable here
        roomNumberLabel.setText(""); // Room number is shown per showtime in the list, so clear the single label
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
}