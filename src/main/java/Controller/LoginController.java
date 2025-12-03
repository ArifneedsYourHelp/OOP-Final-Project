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

/**
 * The {@code LoginController} class manages the user login interface and authentication process.
 * It handles input from the username and password fields, verifies credentials against
 * {@code Manager} and {@code Client} models, and navigates the user to the appropriate
 * application view upon successful login.
 */
public class LoginController {

    /** The text field where the user enters their username. */
    @FXML
    private TextField usernameTextField;

    /** The password field where the user enters their password securely. */
    @FXML
    private PasswordField passwordTextField;

    /** The button that initiates the login authentication process. */
    @FXML
    private Button loginButton;

    /** The hyperlink used to navigate to the user registration (sign-up) view. */
    @FXML
    private Hyperlink signUpLink;

    /** Instance of the application's {@code Manager} model for manager authentication. */
    private Manager manager = new Manager();

    /**
     * Handles the action when the Login button is clicked.
     * It retrieves the entered credentials, attempts to authenticate as a Manager first,
     * then as a Client. Shows an alert for successful login or failure.
     */
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

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up action listeners for the login button and the sign-up hyperlink.
     */
    @FXML
    private void initialize() {
        // Set up the button and link actions
        loginButton.setOnAction(event -> OnLoginButtonClick());
        signUpLink.setOnAction(event -> OnSignUpLinkClick());
    }

    /**
     * Handles the action when the Sign Up hyperlink is clicked.
     * It uses the {@code ViewSwitcher} to navigate to the user registration view.
     */
    @FXML
    private void OnSignUpLinkClick() {
        Stage stage = (Stage) signUpLink.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.SIGNUP_VIEW);
    }

    /**
     * Navigates the application's view to the client's main movie list view.
     * Requires the {@code ViewSwitcher.CLIENT_MOVIE_LIST_VIEW} constant to be defined.
     */
    private void navigateToClientMovieList() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }

    /**
     * Navigates the application's view to the manager's main view.
     * Requires the {@code ViewSwitcher.MANAGER_VIEW} constant to be defined.
     */
    private void navigateToManagerView() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.MANAGER_VIEW);
    }

    /**
     * Displays an alert box to the user.
     * @param title The title text of the alert window.
     * @param message The main content text displayed in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}