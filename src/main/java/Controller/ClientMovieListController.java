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

public class ClientMovieListController {

    @FXML
    private ListView<Movie> clientMovieList;

    @FXML
    private Button ClientMovieViewButton;

    @FXML
    private Button exitbutton;

    // Define the FXML file path for the detail view
    public static final String DETAIL_VIEW_FXML = "/ClientMovie-view.fxml"; // Adjust path as needed
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

    @FXML
    private void ExitButtonClick() {
        Stage stage = (Stage) exitbutton.getScene().getWindow();
        stage.close();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }