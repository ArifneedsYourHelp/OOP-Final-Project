package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Movie;
import model.Showtime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
     * ListView to display all saved showtimes for this movie.
     * This is injected by FXML.
     */
    @FXML
    private ListView<String> showtimeListView;

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
     * Callback function to execute when a showtime is successfully saved.
     * This allows the parent controller to be notified of new showtimes.
     */
    private java.util.function.Consumer<Showtime> onSave;

    /**
     * Callback function to execute when a showtime is deleted.
     * This allows the parent controller to remove the showtime from its list.
     */
    private java.util.function.Consumer<Showtime> onDelete;

    /**
     * Reference to all showtimes passed from parent controller.
     * Used for edit and delete operations.
     */
    private List<Showtime> allShowtimes;

    /**
     * Currently selected showtime for editing or deletion.
     */
    private Showtime selectedShowtime;

    /**
     * Initializes the controller after FXML injection is complete.
     * Populates the time combo box with available showtime options.
     * Sets up mutual exclusion for room checkboxes.
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

        // Add mutual exclusion for room checkboxes - only one can be selected at a time
        setupRoomExclusivity();
    }

    /**
     * Sets up mutual exclusion for room checkboxes.
     * When one room is selected, all others are automatically deselected.
     */
    private void setupRoomExclusivity() {
        room1.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                room2.setSelected(false);
                room3.setSelected(false);
                room4.setSelected(false);
                room5.setSelected(false);
            }
        });

        room2.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                room1.setSelected(false);
                room3.setSelected(false);
                room4.setSelected(false);
                room5.setSelected(false);
            }
        });

        room3.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                room1.setSelected(false);
                room2.setSelected(false);
                room4.setSelected(false);
                room5.setSelected(false);
            }
        });

        room4.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                room1.setSelected(false);
                room2.setSelected(false);
                room3.setSelected(false);
                room5.setSelected(false);
            }
        });

        room5.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                room1.setSelected(false);
                room2.setSelected(false);
                room3.setSelected(false);
                room4.setSelected(false);
            }
        });
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
     * Sets the callback function to be called when a showtime is saved.
     *
     * @param onSave the callback function that receives the created Showtime
     */
    public void setOnSave(java.util.function.Consumer<Showtime> onSave) {
        this.onSave = onSave;
    }

    /**
     * Sets the movie and pre-selects a showtime if provided.
     * This allows editing or viewing existing showtimes with pre-filled data.
     *
     * @param movie the movie to display
     * @param showtime the showtime to pre-select (can be null for new showtime)
     * @param allShowtimes list of all showtimes to display for this movie
     */
    public void setMovieAndShowtime(Movie movie, Showtime showtime, List<Showtime> allShowtimes) {
        setMovie(movie);
        this.allShowtimes = allShowtimes;

        // Load and display all showtimes for this movie
        loadShowtimesForMovie(movie, allShowtimes);

        // Set up selection listener for the ListView
        setupListViewSelection();

        if (showtime != null) {
            // Pre-select the date
            if (showtime.getDate() != null) {
                datePicker.setValue(showtime.getDate());
            }

            // Pre-select the time
            if (showtime.getTime() != null) {
                timeComboBox.setValue(showtime.getTime());
            }

            // Pre-select the room
            if (showtime.getRoom() != null) {
                String room = showtime.getRoom();
                if (room.contains("Room 1") || room.contains("R101")) {
                    room1.setSelected(true);
                } else if (room.contains("Room 2") || room.contains("R102")) {
                    room2.setSelected(true);
                } else if (room.contains("Room 3") || room.contains("R103")) {
                    room3.setSelected(true);
                } else if (room.contains("Room 4") || room.contains("R104")) {
                    room4.setSelected(true);
                } else if (room.contains("Room 5") || room.contains("R105")) {
                    room5.setSelected(true);
                }
            }
        }
    }

    /**
     * Sets up a selection listener on the ListView to track which showtime is selected.
     */
    private void setupListViewSelection() {
        showtimeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals("No showtimes for this movie yet") && !newVal.equals("No showtimes available")) {
                // Find the actual Showtime object that matches the selected display string
                selectedShowtime = findShowtimeByDisplay(newVal);
            } else {
                selectedShowtime = null;
            }
        });
    }

    /**
     * Finds a showtime object based on its display string.
     *
     * @param displayString the formatted display string from ListView
     * @return the matching Showtime object, or null if not found
     */
    private Showtime findShowtimeByDisplay(String displayString) {
        if (allShowtimes == null) return null;

        for (Showtime st : allShowtimes) {
            if (st.getMovie().equals(movie)) {
                String display = formatShowtimeDisplay(st);
                if (display.equals(displayString)) {
                    return st;
                }
            }
        }
        return null;
    }

    /**
     * Loads and displays all showtimes for a specific movie in the ListView.
     *
     * @param movie the movie to filter showtimes for
     * @param allShowtimes the complete list of all showtimes
     */
    private void loadShowtimesForMovie(Movie movie, List<Showtime> allShowtimes) {
        showtimeListView.getItems().clear();

        if (allShowtimes == null || allShowtimes.isEmpty()) {
            showtimeListView.getItems().add("No showtimes available");
            return;
        }

        boolean hasShowtimes = false;
        for (Showtime st : allShowtimes) {
            if (st.getMovie().equals(movie)) {
                String display = formatShowtimeDisplay(st);
                showtimeListView.getItems().add(display);
                hasShowtimes = true;
            }
        }

        if (!hasShowtimes) {
            showtimeListView.getItems().add("No showtimes for this movie yet");
        }
    }

    /**
     * Formats a showtime for display in the ListView.
     *
     * @param showtime the showtime to format
     * @return formatted string representation
     */
    private String formatShowtimeDisplay(Showtime showtime) {
        String date = showtime.getDate().toString();
        String time = showtime.getTime();
        String room = showtime.getRoom();

        // Extract just the room name if it contains " - "
        if (room != null && room.contains(" - ")) {
            String[] parts = room.split(" - ", 2);
            room = parts.length > 1 ? parts[1] : parts[0];
        }

        return String.format("%s at %s - %s", date, time, room);
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

            // Notify parent controller if callback is set
            if (onSave != null) {
                onSave.accept(showtime);
            }

            // Add the new showtime to the display list immediately
            String display = formatShowtimeDisplay(showtime);
            showtimeListView.getItems().remove("No showtimes for this movie yet");
            showtimeListView.getItems().remove("No showtimes available");
            showtimeListView.getItems().add(display);

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
        String timeValue = timeComboBox.getValue();
        if (timeValue == null || timeValue.trim().isEmpty()) {
            showAlert("Validation Error", "Please select or enter a time.");
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
     * Handles clicking on the time buttons in the left panel.
     * Sets the clicked time into the time combo box.
     * This is an FXML event handler.
     */
    @FXML
    private void handleTimeButtonClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String time = clickedButton.getText();
        timeComboBox.setValue(time);
    }

    /**
     * Handles the edit button click event.
     * Loads the selected showtime from the ListView into the form fields for editing.
     * This is an FXML event handler.
     */
    @FXML
    private void handleEdit() {
        if (selectedShowtime == null) {
            showAlert("No Selection", "Please select a showtime from the list to edit.");
            return;
        }

        // Load the selected showtime into the form
        datePicker.setValue(selectedShowtime.getDate());
        timeComboBox.setValue(selectedShowtime.getTime());

        // Select the appropriate room checkbox
        String room = selectedShowtime.getRoom();
        clearRoomSelection();

        if (room != null) {
            if (room.contains("Room 1") || room.contains("R101")) {
                room1.setSelected(true);
            } else if (room.contains("Room 2") || room.contains("R102")) {
                room2.setSelected(true);
            } else if (room.contains("Room 3") || room.contains("R103")) {
                room3.setSelected(true);
            } else if (room.contains("Room 4") || room.contains("R104")) {
                room4.setSelected(true);
            } else if (room.contains("Room 5") || room.contains("R105")) {
                room5.setSelected(true);
            }
        }

        // Delete the old showtime from both lists
        showtimes.remove(selectedShowtime);
        if (allShowtimes != null) {
            allShowtimes.remove(selectedShowtime);
        }

        // Remove from ListView
        String displayToRemove = formatShowtimeDisplay(selectedShowtime);
        showtimeListView.getItems().remove(displayToRemove);

        // Notify parent controller about deletion
        if (onDelete != null) {
            onDelete.accept(selectedShowtime);
        }

        selectedShowtime = null;

        showAlert("Edit Mode", "Showtime loaded into form. Modify the fields and click Save.");
    }

    /**
     * Clears all room checkbox selections.
     */
    private void clearRoomSelection() {
        room1.setSelected(false);
        room2.setSelected(false);
        room3.setSelected(false);
        room4.setSelected(false);
        room5.setSelected(false);
    }

    /**
     * Handles the delete button click event.
     * Shows a confirmation dialog before deleting the selected showtime.
     * This is an FXML event handler.
     */
    @FXML
    private void handleDelete() {
        if (selectedShowtime == null) {
            showAlert("No Selection", "Please select a showtime from the list to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Showtime");
        alert.setHeaderText("Are you sure you want to delete this showtime?");
        alert.setContentText(formatShowtimeDisplay(selectedShowtime));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remove from local list
                showtimes.remove(selectedShowtime);

                // Remove from all showtimes list
                if (allShowtimes != null) {
                    allShowtimes.remove(selectedShowtime);
                }

                // Remove from ListView
                String displayToRemove = formatShowtimeDisplay(selectedShowtime);
                showtimeListView.getItems().remove(displayToRemove);

                // Check if list is now empty
                if (showtimeListView.getItems().isEmpty()) {
                    showtimeListView.getItems().add("No showtimes for this movie yet");
                }

                // Notify parent controller
                if (onDelete != null) {
                    onDelete.accept(selectedShowtime);
                }

                selectedShowtime = null;

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Showtime Deleted");
                successAlert.setContentText("The showtime has been successfully deleted.");
                successAlert.showAndWait();
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

    /**
     * Sets the callback function to be called when a showtime is deleted.
     *
     * @param onDelete the callback function that receives the deleted Showtime
     */
    public void setOnDelete(java.util.function.Consumer<Showtime> onDelete) {
        this.onDelete = onDelete;
    }
}