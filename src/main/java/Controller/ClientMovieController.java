package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
     * Handles the back button click to navigate back to the movie list view.
     * It closes the current stage's view and switches to the {@code CLIENT_MOVIE_LIST_VIEW}.
     */
    @FXML
    private void BackButtonClick() {
        Stage stage = (Stage) clientMovieBackButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }
}