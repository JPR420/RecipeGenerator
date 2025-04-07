package edu.farmingdale.recipegenerator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Objects;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private void initialize() {
        // Set the image for the background
        try {
            Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/b4.jpg")));
            backgroundImageView.setImage(backgroundImage);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the background image.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleSignUpButtonAction() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.", AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Password Mismatch", "Passwords do not match.", AlertType.ERROR);
            return;
        }

        // Simulate successful account creation and close window
        showAlert("Success", "Account created successfully!", AlertType.INFORMATION);
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
