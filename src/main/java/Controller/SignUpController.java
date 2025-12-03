package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Client;

public class SignUpController {

    @FXML
    private TextField usernameSignTextField;

    @FXML
    private TextField passwordSignTextField;

    @FXML
    private TextField emailSignTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signUpBackButton;


    @FXML
    private void initialize() {
        // Set up the button actions
        signUpButton.setOnAction(event -> OnSignUpButtonClick());
        signUpBackButton.setOnAction(event -> OnBackButtonClick());
    }

    @FXML
    private void OnSignUpButtonClick() {
        String username = usernameSignTextField.getText();
        String password = passwordSignTextField.getText();
        String email = emailSignTextField.getText();

        try {
            Client newClient = new Client(username, password, email);
            Client.registerClient(newClient);

            showAlert("Sign Up Successful", "You have successfully signed up as a new client: " + newClient.getUsername());

            navigateToClientMovieList();

        } catch (IllegalArgumentException e) {
            showAlert("Sign Up Failed", e.getMessage());
        }
    }

    // --- NEW METHOD TO SWITCH TO MOVIE LIST ---
    private void navigateToClientMovieList() {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }

    // --- Existing handleBack() for the "Back" button ---
    @FXML
    private void OnBackButtonClick() {
        Stage stage = (Stage) signUpBackButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.LOGIN_VIEW);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}