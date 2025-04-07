package edu.farmingdale.recipegenerator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void initialize() {
        // Initialize logic if needed
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.", AlertType.ERROR);
            return;
        }

        // Here you can add authentication logic, for example:
        // If the username and password are valid (this can be a simple check for now):
        if (username.equals("user") && password.equals("password")) {
            // Login success, proceed to open the next window (Main Window)
            openMainWindow();
        } else {
            showAlert("Login Error", "Invalid username or password.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleSignUpButtonAction() {
        // Handle Sign Up (this can be implemented later or could lead to another scene)
        showAlert("Sign Up", "Sign Up functionality is not yet implemented.", AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openMainWindow() {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();  // Close the login window

            // Load the main scene (your fridge management window)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/farmingdale/recipegenerator/hello-view.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the main window.", AlertType.ERROR);
        }
    }
}
