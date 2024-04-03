package com.example.javafxmpp.controller;

import com.example.javafxmpp.controller.alerts.SignInControllerAlerts;
import com.example.javafxmpp.model.CurrentUser;
import com.example.javafxmpp.model.Employee;
import com.example.javafxmpp.service.ServerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {

    @FXML
    private Button buttonLogin, buttonSignup;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label welcomeText;

    private final ServerService serverService = ServerService.getInstance();

    public void handleLogin() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        Employee employee = serverService.getEmployeeService().findEmployee(email, password);

        if (employee != null) {
            CurrentUser.getInstance().setId(employee.getId());
            loadHomeScene();
        } else {
            SignInControllerAlerts.showLoginErrorAlert();
        }
    }

    public void handleSignup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxmpp/signup.fxml"));
        Parent root = loader.load();

        Stage window = (Stage) buttonSignup.getScene().getWindow();
        window.setScene(new Scene(root, 600, 750));
    }

    private void loadHomeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxmpp/home.fxml"));
            Parent root = loader.load();

            Stage window = (Stage) buttonLogin.getScene().getWindow();
            window.setScene(new Scene(root, 600, 750));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
