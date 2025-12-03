package Controller;

import Helper.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Client;

/**
 * The {@code SignUpController} class manages the user registration (sign-up) interface.
 * It handles input validation for new client registration, creates a new {@code Client}
 * object, and navigates the user to the main movie list upon successful registration.
 */
public class SignUpController {

    // --- FXML Components ---
    /** The text field for the new user's desired username. */
    @FXML
    private TextField usernameSignTextField;

    /** The text field for the new user's desired password. (Should ideally be a PasswordField, but is defined as TextField in FXML). */
    @FXML
    private TextField passwordSignTextField;

    /** The text field for the new user's email address. */
    @FXML
    private TextField emailSignTextField;

    /** The button that initiates the client registration process. */
    @FXML
    private Button signUpButton;

    /** The button that allows the user to return to the previous view (Login). */
    @FXML
    private Button signUpBackButton;


    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up action listeners for the sign-up and back buttons.
     */
    @FXML
    private void initialize() {
        // Set up the button actions
        signUpButton.setOnAction(event -> OnSignUpButtonClick());
        signUpBackButton.setOnAction(event -> OnBackButtonClick());
    }

    /**
     * Handles the action when the Sign Up button is clicked.
     * It validates the input fields, creates a new {@code Client} object, registers it,
     * displays an alert, and navigates to the client movie list.
     */
    @FXML
    private void OnSignUpButtonClick() {
        String username = usernameSignTextField.getText();
        String password = passwordSignTextField.getText();
        String email = emailSignTextField.getText();

        try {
            // 1. Create a new Client model object (handles basic validation within constructor)
            Client newClient = new Client(username, password, email);

            // 2. Register the client (e.g., save to a database or list)
            Client.registerClient(newClient);

            // 3. Success notification
            showAlert("Sign Up Successful", "You have successfully signed up as a new client: " + newClient.getUsername());

            // 4. Navigate to the next view
            navigateToClientMovieList();

        } catch (IllegalArgumentException e) {
            // Handle validation errors (e.g., empty fields, duplicate username)
            showAlert("Sign Up Failed", e.getMessage());
        }
    }

    /**
     * Navigates the application's view to the client's main movie list view after successful sign-up.
     * Requires the {@code ViewSwitcher.CLIENT_MOVIE_LIST_VIEW} constant to be defined.
     */
    private void navigateToClientMovieList() {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.CLIENT_MOVIE_LIST_VIEW);
    }

    /**
     * Handles the action when the Back button is clicked.
     * It uses the {@code ViewSwitcher} to navigate back to the main login view.
     * Requires the {@code ViewSwitcher.LOGIN_VIEW} constant to be defined.
     */
    @FXML
    private void OnBackButtonClick() {
        Stage stage = (Stage) signUpBackButton.getScene().getWindow();
        ViewSwitcher.switchView(stage, ViewSwitcher.LOGIN_VIEW);
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