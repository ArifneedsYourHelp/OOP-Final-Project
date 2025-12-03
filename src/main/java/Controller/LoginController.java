package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import model.Manager;
import model.Client;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton; // FXML ID: loginButton

    @FXML
    private Hyperlink signUpLink; // FXML ID: signUpLink

    // ... existing model instances ...
    private Manager manager = new Manager();

    @FXML
    private void OnLoginButtonClick() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (manager.getUsername().equals(username) && manager.verifyPassword(password)) {
            showAlert("Login Successful", "You are logged in as Manager.");
            // Assuming Manager also goes to the movie list or a manager view
            // For now, let's navigate to the client list view for demonstration.
            navigateToManagerView();
            return;
        }

        Client loggedInClient = Client.findClientByUsername(username);

        if (loggedInClient != null && loggedInClient.verifyPassword(password)) {
            showAlert("Login Successful", "You are logged in as Client: " + username);
            // Navigate to the client's movie list
            navigateToClientMovieList();
        }
        else {
            showAlert("Login Failed", "Incorrect username or password.");
        }
    }

    @FXML
    private void initialize() {
        // Set up the button and link actions
        loginButton.setOnAction(event -> OnLoginButtonClick());
        signUpLink.setOnAction(event -> OnSignUpLinkClick());
    }

    // --- New Action Handler ---
    @FXML
    private void OnSignUpLinkClick() {
        Stage stage = (Stage) signUpLink.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.SIGNUP_VIEW);
    }

    private void navigateToClientMovieList() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }


    private void navigateToManagerView() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.MANAGER_VIEW);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}