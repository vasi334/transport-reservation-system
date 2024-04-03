package com.example.javafxmpp.controller;

import com.example.javafxmpp.model.CurrentUser;
import com.example.javafxmpp.model.Employee;
import com.example.javafxmpp.service.ServerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField firstNameField, lastNameField, emailField, phoneNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink backToLogin;

    @FXML
    private Button buttonSignup;

    private final ServerService serverService = ServerService.getInstance();

    public void handleSignup() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();

        Employee employee = new Employee(email, firstName, lastName, password, phoneNumber);

        serverService.getEmployeeService().saveEmployee(employee);

        CurrentUser.getInstance().setId(employee.getId());

        loadHomeScene();
    }

    public void handleLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxmpp/signin.fxml"));
        Parent root = loader.load();

        Stage window = (Stage) backToLogin.getScene().getWindow();
        window.setScene(new Scene(root, 600, 750));
    }

    private void loadHomeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxmpp/home.fxml"));
            Parent root = loader.load();

            Stage window = (Stage) buttonSignup.getScene().getWindow();
            window.setScene(new Scene(root, 600, 750));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
