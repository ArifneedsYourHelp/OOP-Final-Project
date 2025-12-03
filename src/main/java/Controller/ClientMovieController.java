package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;


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


    @FXML
    public void initialize() {
        clientMovieBackButton.setOnAction(event -> BackButtonClick());
    }

    /**
     * Handles the back button click to navigate back to the movie list view.
     */
    @FXML
    private void BackButtonClick() {
        Stage stage = (Stage) clientMovieBackButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }
}