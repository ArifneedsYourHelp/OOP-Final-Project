package Controller;

// Use model.Movie fully-qualified in this controller to avoid import ambiguity
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Controller class for the movie form dialog.
 * This class handles user input for creating new movies and validates
 * the data before saving.
 *
 * <p>This controller demonstrates:
 * <ul>
 *   <li><b>MVC Pattern:</b> Handles view logic for the movie form</li>
 *   <li><b>Data Validation:</b> Ensures all required fields are filled</li>
 *   <li><b>Callback Pattern:</b> Uses Consumer for communication with parent controller</li>
 * </ul>
 *
 * @author Cinema App Development Team
 * @version 1.0
 * @since 2024-11-24
 */
public class MovieFormController {

    /**
     * Text field for entering the movie title.
     * This is injected by FXML.
     */
    @FXML
    private TextField titleField;

    /**
     * Text field for entering the movie genre.
     * This is injected by FXML.
     */
    @FXML
    private TextField genreField;

    /**
     * Text field for entering the movie duration in HH:MM:SS format.
     * This is injected by FXML.
     */
    @FXML
    private TextField durationField;

    /**
     * Radio button for selecting G (General Audiences) rating.
     * This is injected by FXML.
     */
    @FXML
    private RadioButton gRating;

    /**
     * Radio button for selecting PG (Parental Guidance) rating.
     * This is injected by FXML.
     */
    @FXML
    private RadioButton pgRating;

    /**
     * Radio button for selecting PG-13 rating.
     * This is injected by FXML.
     */
    @FXML
    private RadioButton pg13Rating;

    /**
     * Button to save the new movie.
     * This is injected by FXML.
     */
    @FXML
    private Button addButton;

    /**
     * Button to cancel movie creation and close the dialog.
     * This is injected by FXML.
     */
    @FXML
    private Button cancelButton;

    /**
     * Toggle group for the rating radio buttons.
     * Ensures only one rating can be selected at a time.
     */
    private ToggleGroup ratingGroup;

    /**
     * Callback function to execute when a movie is successfully saved.
     * This demonstrates the use of functional interfaces for communication.
     */
    private Consumer<model.Movie> onSave;

    /**
     * Initializes the controller after FXML injection is complete.
     * Sets up the toggle group for rating radio buttons and selects
     * PG as the default rating.
     */
    @FXML
    public void initialize() {
        ratingGroup = new ToggleGroup();
        gRating.setToggleGroup(ratingGroup);
        pgRating.setToggleGroup(ratingGroup);
        pg13Rating.setToggleGroup(ratingGroup);
        pgRating.setSelected(true);
    }

    /**
     * Sets the callback function to be called when a movie is saved.
     *
     * @param onSave the callback function that receives the created Movie
     */
    public void setOnSave(Consumer<model.Movie> onSave) {
        this.onSave = onSave;
    }

    /**
     * Handles the add button click event.
     * Validates the input, creates a new Movie object, calls the save callback,
     * and closes the dialog.
     * This is an FXML event handler.
     */
    @FXML
    private void handleAdd() {
        if (validateInput()) {
            RadioButton selectedRating = (RadioButton) ratingGroup.getSelectedToggle();
            String rating = selectedRating != null ? selectedRating.getText() : "PG";

            model.Movie movie = new model.Movie(
                titleField.getText(),
                genreField.getText(),
                durationField.getText(),
                rating
            );

            if (onSave != null) {
                onSave.accept(movie);
            }

            closeDialog();
        }
    }

    /**
     * Handles the cancel button click event.
     * Closes the dialog without saving.
     * This is an FXML event handler.
     */
    @FXML
    private void handleCancel() {
        closeDialog();
    }

    /**
     * Validates all input fields to ensure required data is provided.
     * Shows appropriate error messages if validation fails.
     *
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a movie title.");
            return false;
        }
        if (genreField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a genre.");
            return false;
        }
        if (durationField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a duration.");
            return false;
        }
        return true;
    }

    /**
     * Displays a warning alert to the user.
     * This is a utility method for showing validation errors.
     *
     * @param title the title of the alert dialog
     * @param message the message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the dialog window.
     * Retrieves the stage from the cancel button's scene and closes it.
     */
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
