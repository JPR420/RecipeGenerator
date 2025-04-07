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
        // No image is being set, this is left empty now
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.", AlertType.ERROR);
            return;
        }

        // For now, we are skipping the authentication logic
        // Open the main window (fridge management window)
        openMainWindow();
    }

    @FXML
    private void handleSignUpButtonAction() {
        // Open the Sign Up window
        openSignUpWindow();
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

    private void openSignUpWindow() {
        try {
            // Close the current window (login window)
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.close();

            // Load the Sign-Up scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/farmingdale/recipegenerator/sign-up.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the sign-up window.", AlertType.ERROR);
        }
    }
}
