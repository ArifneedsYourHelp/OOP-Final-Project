package Controller;


import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Movie;
import model.Showtime;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ClientMovieListController} class is the controller for the client's
 * main movie list view in the application.
 * It handles displaying the list of available movies, allowing selection, and navigating
 * to the details view or exiting the application.
 */
public class ClientMovieListController {

    /** ListView displaying the available {@code Movie} objects for the client to select from. */
    @FXML
    private ListView<Movie> clientMovieList;

    /** Button used to trigger viewing the details of the selected movie. */
    @FXML
    private Button ClientMovieViewButton;

    /** Button used to exit (close) the current application stage. */
    @FXML
    private Button exitbutton;

    /** Defines the FXML file path for the detailed movie view (e.g., /ClientMovie-view.fxml). */
    public static final String DETAIL_VIEW_FXML = ViewSwitcher.DETAIL_VIEW_FXML; // Adjust path as needed

    /**
     * Sample Movie object used for demonstration purposes.
     */
    private Movie sampleMovie;

    /**
     * Initializes the controller after FXML injection is complete.
     * Populates the movie list with a sample movie and creates sample showtimes.
     */
    @FXML
    public void initialize() {
        // Create a single instance of a sample movie
        // Title: The Code Whisperer, Genre: Tech-Thriller, Duration: 01:45:00, Rating: PG-13
        sampleMovie = new Movie("The Code Whisperer", "Tech-Thriller", "01:45:00", "PG-13");

        // Add the sample movie to the list view
        clientMovieList.getItems().add(sampleMovie);

        // --- NOTE: This assumes model.Showtime has the static list and constructor changes from the last step ---
        // Create sample showtimes for this movie. These are saved globally inside model.Showtime.
        new Showtime(sampleMovie, java.time.LocalDate.now().plusDays(1), "5:00 PM", "Room 2");
        new Showtime(sampleMovie, java.time.LocalDate.now().plusDays(2), "8:30 PM", "Room 4");

        // --- MOVIE 2: The Martian Gardener ---
        // Title: The Martian Gardener, Genre: Sci-Fi/Drama, Duration: 02:10:00, Rating: PG
        Movie movie2 = new Movie("The Champlains Brains", "Sci-Fi/Drama", "02:10:00", "PG");

        // Add the second sample movie to the list view
        clientMovieList.getItems().add(movie2);

        // Create sample showtimes for Movie 2.
        new Showtime(movie2, java.time.LocalDate.now().plusDays(1), "7:30 PM", "Room 1");
        new Showtime(movie2, java.time.LocalDate.now().plusDays(3), "4:00 PM", "Room 3");
    }
    /**
     * Handles the action when the "View Movie Details" button is clicked.
     * It checks if a movie is selected, loads the detail view FXML, and switches
     * the current stage's scene to the movie details scene.
     */
    @FXML
    private void ViewMovieDetailsButtonClick() {
        Movie selectedMovie = clientMovieList.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            try {
                // 1. Load the Detail FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource(DETAIL_VIEW_FXML));
                Parent root = loader.load();

                // 2. Get the controller and pass the selected Movie (and its showtimes)
                ClientMovieController controller = loader.getController();

                /**
                 * Filters the global list of all showtimes (stored statically in model.Showtime)
                 * to find only those scheduled for the selected movie.
                 */
                List<Showtime> movieShowtimes = Showtime.getAllShowtimes().stream()
                        .filter(st -> st.getMovie().equals(selectedMovie))
                        .collect(Collectors.toList());

                // Pass the movie and the filtered showtimes to the detail view
                controller.setMovie(selectedMovie, movieShowtimes); // CRITICAL STEP: Passing the data

                // 3. Switch the scene
                Stage stage = (Stage) ClientMovieViewButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load movie details view: " + e.getMessage());
            }
        } else {
            showAlert("Selection Required", "Please select a movie.");
        }
    }

    /**
     * Handles the action when the "Exit" button is clicked.
     * It retrieves the current stage and closes the window.
     */
    @FXML
    private void ExitButtonClick() {
        Stage stage = (Stage) exitbutton.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays a simple information alert box to the user.
     * @param title The title text of the alert window.
     * @param message The main content text displayed in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}