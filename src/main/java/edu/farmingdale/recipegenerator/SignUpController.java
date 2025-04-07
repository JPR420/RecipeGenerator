package edu.farmingdale.recipegenerator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleSignUpButtonAction() {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Sign Up Error", "Please fill in all fields.", AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Sign Up Error", "Passwords do not match.", AlertType.ERROR);
            return;
        }

        // Here you could add logic to save the new user to a database or local storage.

        // Show success message
        showAlert("Sign Up Success", "You have successfully signed up.", AlertType.INFORMATION);

        // Close the sign-up window and open the login window again
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();

        // You can either go back to the login window or open the main window
        // For now, we will close the sign-up window and return to login
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
