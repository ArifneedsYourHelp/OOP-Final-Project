package Controller;

import Helper.ViewSwitcher; // Assuming ViewSwitcher is available
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Movie;
import java.io.IOException;
import java.util.List;

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
    public static final String DETAIL_VIEW_FXML = "/ClientMovie-view.fxml"; // Adjust path as needed

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


                // 3. Switch the scene
                Stage stage = (Stage) ClientMovieViewButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load movie details view.");
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