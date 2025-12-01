package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Manager;
import model.Client;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private Manager manager = new Manager();  // Predefined manager
    private Client client = null;  // Example, normally you'd retrieve this from a data source

    @FXML
    private void handleLogin() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (manager.getUsername().equals(username) && manager.verifyPassword(password)) {
            // Login as Manager
            showAlert("Login Successful", "You are logged in as Manager.");
        } else if (client != null && client.getUsername().equals(username) && client.verifyPassword(password)) {
            // Login as Client
            showAlert("Login Successful", "You are logged in as Client.");
        } else {
            showAlert("Login Failed", "Incorrect username or password.");
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
