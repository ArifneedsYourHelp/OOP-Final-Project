package Helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewSwitcher {
    public static final String LOGIN_VIEW = "/com/example/finalprojectoop/ClientLogin-view.fxml";
    public static final String SIGNUP_VIEW = "/com/example/finalprojectoop/ClientSignUp-view.fxml";
    public static final String CLIENT_MOVIE_LIST_VIEW = "/com/example/finalprojectoop/ClientMovieList-view.fxml";
    public static final String MANAGER_VIEW =  "/com/example/finalprojectoop/cinema-view.fxml";
    public static final String DETAIL_VIEW_FXML = "/com/example/finalprojectoop/ClientMovie-view.fxml";

    /**
     * Replaces the content of the current window with a new view.
     * @param stage The primary stage of the application.
     * @param fxmlFile The path to the FXML file to load.
     */
    public static void switchView(Stage stage, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewSwitcher.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an alert)
            System.out.println("Could not load view: " + fxmlFile);
        }
    }
}
