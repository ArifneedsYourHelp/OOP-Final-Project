module com.example.finalprojectoop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalprojectoop to javafx.fxml;
    exports com.example.finalprojectoop;
}