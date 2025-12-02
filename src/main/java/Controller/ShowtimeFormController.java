package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Movie;
import model.Showtime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the showtime form dialog.
 * This class displays movie details and allows users to create and manage
 * showtimes for a specific movie.
 *
 * <p>This controller demonstrates:
 * <ul>
 *   <li><b>MVC Pattern:</b> Manages the showtime form view logic</li>
 *   <li><b>Data Management:</b> Handles showtime creation and storage</li>
 *   <li><b>User Interaction:</b> Provides form validation and user feedback</li>
 * </ul>
 *
 * @author Cinema App Development Team
 * @version 1.0
 * @since 2024-11-24
 */
public class ShowtimeFormController {

    /**
     * Label displaying the movie title.
     * This is injected by FXML.
     */
    @FXML
    private Label titleLabel;

    /**
     * Label displaying the movie genre.
     * This is injected by FXML.
     */
    @FXML
    private Label genreLabel;

    /**
     * Label displaying the movie duration.
     * This is injected by FXML.
     */
    @FXML
    private Label durationLabel;

    /**
     * Label displaying the movie rating.
     * This is injected by FXML.
     */
    @FXML
    private Label ratingLabel;

    /**
     * Date picker for selecting the showtime date.
     * This is injected by FXML.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * Combo box for selecting the showtime.
     * This is injected by FXML.
     */
    @FXML
    private ComboBox<String> timeComboBox;

    /**
     * Checkbox for selecting Room 1.
     * This is injected by FXML.
     */
    @FXML
    private CheckBox room1;

    /**
     * Checkbox for selecting Room 2.
     * This is injected by FXML.
     */
    @FXML
    private CheckBox room2;

    /**
     * Checkbox for selecting Room 3.
     * This is injected by FXML.
     */
    @FXML
    private CheckBox room3;

    /**
     * Checkbox for selecting Room 4.
     * This is injected by FXML.
     */
    @FXML
    private CheckBox room4;

    /**
     * Checkbox for selecting Room 5.
     * This is injected by FXML.
     */
    @FXML
    private CheckBox room5;

    /**
     * Button to save the created showtime.
     * This is injected by FXML.
     */
    @FXML
    private Button saveButton;

    /**
     * Button to cancel showtime creation.
     * This is injected by FXML.
     */
    @FXML
    private Button cancelButton;

    /**
     * Button to edit an existing showtime.
     * This is injected by FXML.
     */
    @FXML
    private Button editButton;

    /**
     * Button to delete an existing showtime.
     * This is injected by FXML.
     */
    @FXML
    private Button deleteButton;

    /**
     * The movie for which showtimes are being managed.
     */
    private Movie movie;

    /**
     * List of all showtimes created for this movie.
     * Demonstrates encapsulation of the data collection.
     */
    private List<Showtime> showtimes = new ArrayList<>();

    /**
     * Initializes the controller after FXML injection is complete.
     * Populates the time combo box with available showtime options.
     */
    @FXML
    public void initialize() {
        timeComboBox.getItems().addAll(
            "11:30 A.M",
            "2:00 PM",
            "5:00 PM",
            "8:30 PM",
            "11:30 PM"
        );
    }

    /**
     * Sets the movie for which showtimes will be managed.
     * Updates the UI labels with the movie's information.
     *
     * @param movie the movie to display and create showtimes for
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
        titleLabel.setText("Title: " + movie.getTitle());
        genreLabel.setText("Genre: " + movie.getGenre());
        durationLabel.setText("Duration: " + movie.getDuration());
        ratingLabel.setText("Rating: " + movie.getRating());
    }

    /**
     * Handles the save button click event.
     * Validates input, creates a new Showtime object, adds it to the list,
     * and displays a confirmation message.
     * This is an FXML event handler.
     */
    @FXML
    private void handleSave() {
        if (validateInput()) {
            LocalDate date = datePicker.getValue();
            String time = timeComboBox.getValue();
            String room = getSelectedRoom();

            Showtime showtime = new Showtime(movie, date, time, room);
            showtimes.add(showtime);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Showtime Created");
            alert.setContentText("Showtime for " + movie.getTitle() + " has been created.");
            alert.showAndWait();

            clearForm();
        }
    }

    /**
     * Validates all input fields for creating a showtime.
     * Ensures that a date, time, and room have been selected.
     *
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInput() {
        if (datePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a date.");
            return false;
        }
        if (timeComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a time.");
            return false;
        }
        if (getSelectedRoom() == null) {
            showAlert("Validation Error", "Please select a room.");
            return false;
        }
        return true;
    }

    /**
     * Determines which room checkbox is selected.
     * Only one room should be selected at a time.
     *
     * @return the selected room name, or null if no room is selected
     */
    private String getSelectedRoom() {
        if (room1.isSelected()) return "Room 1";
        if (room2.isSelected()) return "Room 2";
        if (room3.isSelected()) return "Room 3";
        if (room4.isSelected()) return "Room 4";
        if (room5.isSelected()) return "Room 5";
        return null;
    }

    /**
     * Clears all form inputs back to their default state.
     * Called after successfully creating a showtime.
     */
    private void clearForm() {
        datePicker.setValue(null);
        timeComboBox.setValue(null);
        room1.setSelected(false);
        room2.setSelected(false);
        room3.setSelected(false);
        room4.setSelected(false);
        room5.setSelected(false);
    }

    /**
     * Handles the cancel button click event.
     * Closes the dialog without saving any changes.
     * This is an FXML event handler.
     */
    @FXML
    private void handleCancel() {
        closeDialog();
    }

    /**
     * Handles the edit button click event.
     * Enables all form fields for editing an existing showtime.
     * This is an FXML event handler.
     */
    @FXML
    private void handleEdit() {
        // Enable editing fields
        datePicker.setDisable(false);
        timeComboBox.setDisable(false);
        room1.setDisable(false);
        room2.setDisable(false);
        room3.setDisable(false);
        room4.setDisable(false);
        room5.setDisable(false);
    }

    /**
     * Handles the delete button click event.
     * Shows a confirmation dialog before deleting the showtime.
     * This is an FXML event handler.
     */
    @FXML
    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Showtime");
        alert.setHeaderText("Are you sure you want to delete this showtime?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                closeDialog();
            }
        });
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

