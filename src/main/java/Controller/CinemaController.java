package Controller;

import model.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the main cinema view.
 * This class manages the display of movies in a grid layout and handles
 * user interactions such as adding, deleting, and viewing movie details.
 *
 * <p>This controller demonstrates:
 * <ul>
 *   <li><b>MVC Pattern:</b> Separates UI logic from business logic</li>
 *   <li><b>Encapsulation:</b> Private methods for internal operations</li>
 *   <li><b>Event Handling:</b> FXML-bound event handlers</li>
 * </ul>
 *
 * @author Cinema App Development Team
 * @version 1.0
 * @since 2024-11-24
 */
public class CinemaController {

    /**
     * The grid pane that displays movie cards in a grid layout.
     * This is injected by FXML.
     */
    @FXML
    private GridPane moviesGrid;

    /**
     * Button to add a new movie to the cinema.
     * This is injected by FXML.
     */
    @FXML
    private Button addButton;

    /**
     * Button to delete the selected movie from the cinema.
     * This is injected by FXML.
     */
    @FXML
    private Button deleteButton;

    /**
     * The list of all movies currently in the cinema system.
     * This demonstrates encapsulation of the data model.
     */
    private List<Movie> movies = new ArrayList<>();

    /**
     * The currently selected movie for operations like delete or view details.
     */
    private Movie selectedMovie;

    /**
     * Initializes the controller after FXML injection is complete.
     * This method is automatically called by JavaFX after the FXML file has been loaded.
     * It loads and displays the initial list of movies.
     */
    @FXML
    public void initialize() {
        loadMovies();
    }

    /**
     * Loads and displays all movies in the grid layout.
     * This method clears the current grid and populates it with movie cards,
     * arranging them in a 3-column grid layout.
     *
     * <p>This is a private helper method that demonstrates encapsulation
     * of the display logic.
     */
    private void loadMovies() {
        moviesGrid.getChildren().clear();
        int col = 0;
        int row = 0;

        for (Movie movie : movies) {
            VBox movieCard = createMovieCard(movie);
            moviesGrid.add(movieCard, col, row);

            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Creates a visual card representation of a movie.
     * The card displays the movie title and is clickable to view details.
     *
     * @param movie the movie to create a card for
     * @return a VBox containing the movie card UI
     */
    private VBox createMovieCard(Movie movie) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
        card.setPrefSize(200, 200);
        card.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label(movie.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        card.getChildren().add(titleLabel);

        card.setOnMouseClicked(e -> {
            selectedMovie = movie;
            showMovieDetails(movie);
        });

        return card;
    }

    /**
     * Handles the add button click event.
     * Opens a dialog for adding a new movie to the cinema.
     * This is an FXML event handler bound to the add button.
     */
    @FXML
    private void handleAdd() {
        showAddMovieDialog();
    }

    /**
     * Handles the delete button click event.
     * Deletes the currently selected movie after user confirmation.
     * If no movie is selected, shows a warning dialog.
     * This is an FXML event handler bound to the delete button.
     */
    @FXML
    private void handleDelete() {
        if (selectedMovie != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Movie");
            alert.setHeaderText("Are you sure you want to delete this movie?");
            alert.setContentText(selectedMovie.getTitle());

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    movies.remove(selectedMovie);
                    selectedMovie = null;
                    loadMovies();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Movie Selected");
            alert.setContentText("Please select a movie to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Displays a dialog for adding a new movie.
     * Loads the movie form FXML and sets up a callback to add the movie
     * to the list when the user saves.
     *
     * <p>This demonstrates separation of concerns by delegating
     * the form UI to a separate controller.
     */
    private void showAddMovieDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalprojectoop/movie-form.fxml"));
            Scene scene = new Scene(loader.load());

            MovieFormController controller = loader.getController();
            java.util.function.Consumer<model.Movie> onSave = m -> {
                movies.add(m);
                loadMovies();
            };
            controller.setOnSave(onSave);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Movie");
            dialog.setScene(scene);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not load movie form: " + e.getMessage());
        }
    }

    /**
     * Displays detailed information about a movie and allows creating showtimes.
     * Opens the showtime form dialog for the specified movie.
     *
     * @param movie the movie to display details for
     */
    private void showMovieDetails(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalprojectoop/showtime-form.fxml"));
            Scene scene = new Scene(loader.load());

            ShowtimeFormController controller = loader.getController();
            controller.setMovie(movie);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Movie Details - Showtime");
            dialog.setScene(scene);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not load showtime form: " + e.getMessage());
        }
    }

    /**
     * Displays an error dialog to the user.
     * This is a utility method for consistent error reporting.
     *
     * @param title the title of the error dialog
     * @param message the error message to display
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
