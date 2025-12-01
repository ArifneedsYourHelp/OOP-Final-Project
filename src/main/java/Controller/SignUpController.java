package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Client;

public class SignUpController {

    @FXML
    private TextField usernameSignTextField;

    @FXML
    private TextField passwordSignTextField;

    @FXML
    private TextField emailSignTextField;

    @FXML
    private void handleSignUp() {
        String username = usernameSignTextField.getText();
        String password = passwordSignTextField.getText();
        String email = emailSignTextField.getText();

        try {
            Client newClient = new Client(username, password, email);
            showAlert("Sign Up Successful", "You have successfully signed up as a new client.");
        } catch (IllegalArgumentException e) {
            showAlert("Sign Up Failed", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}