package com.example.javafxmpp.controller;

import com.example.javafxmpp.controller.alerts.HomeControllerAlerts;
import com.example.javafxmpp.model.CurrentUser;
import com.example.javafxmpp.model.Employee;
import com.example.javafxmpp.model.Reservation;
import com.example.javafxmpp.model.Trip;
import com.example.javafxmpp.service.ServerService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private final ServerService serverService = ServerService.getInstance();
    private static final Logger logger = LogManager.getLogger();

    @FXML
    private Button buttonLogout;

    @FXML
    private TableView<Trip> tripsTable;

    @FXML
    private TableColumn<Trip, String> destinationColumn;

    @FXML
    private TableColumn<Trip, String> dateColumn;

    @FXML
    private TableColumn<Trip, String> timeColumn;

    @FXML
    private TableColumn<Trip, Integer> availableSeatsColumn;

    @FXML
    private TableView<Trip> singleTripTable;

    @FXML
    private TableColumn<Trip, String> singleDestinationColumn;

    @FXML
    private TableColumn<Trip, String> singleDateColumn;

    @FXML
    private TableColumn<Trip, String> singleTimeColumn;

    @FXML
    private TableColumn<Trip, Integer> singleAvailableSeatsColumn;

    @FXML
    private TextField searchDestinationField;

    @FXML
    private TextField searchDateTimeField;

    @FXML
    private TextField searchTimeField, destinationDateField, destinationTimeField;

    @FXML
    private TextField customerFirstNameField, customerLastNameField, destinationField, numberOfSeatsField;

    @FXML
    private TableView<String> seatInformationTable;

    @FXML
    private TableColumn<String, String> seatNumberColumn;

    @FXML
    private TableColumn<String, String> customerNameColumn;

    @FXML
    private void handleSearch() {
        String destination = searchDestinationField.getText();
        String date = searchDateTimeField.getText();
        String time = searchTimeField.getText();

        logger.debug("Searching for trip with destination: {}, date: {}, time: {}", destination, date, time);

        Trip searchedTrip = serverService.getTripService().findTripByDestinationDateTime(destination, date, time);
        String searchedTripId = serverService.getTripService().findTripIdByDestinationDateTime(destination, date, time);

        singleTripTable.getItems().clear();
        if (searchedTrip != null) {
            logger.info("Trip found: {}", searchedTrip);

            ObservableList<Trip> tripObservableList = FXCollections.observableArrayList(searchedTrip);
            singleTripTable.setItems(tripObservableList);

            singleDestinationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestination()));
            singleDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
            singleTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival_time()));
            singleAvailableSeatsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAvailable_seats()).asObject());

            List<Integer> bookedSeats = serverService.getReservationService().findBookedSeatsForTrip(searchedTripId);
            populateSeatInformationTable(searchedTrip, searchedTripId, bookedSeats);
        } else {
            logger.warn("No trip found for destination: {}, date: {}, time: {}", destination, date, time);
            HomeControllerAlerts.showNoTripFoundErrorAlert();
        }
    }


    @FXML
    private void handleBookSeat() {
        logger.debug("Handling booking seat...");

        String customerFirstName = customerFirstNameField.getText();
        String customerLastName = customerLastNameField.getText();
        String destinationDate = destinationDateField.getText();
        String destinationTime = destinationTimeField.getText();
        String destination = destinationField.getText();
        Integer numberOfSeats = Integer.valueOf(numberOfSeatsField.getText());

        String tripId = serverService.getTripService().findTripIdByDestinationDateTime(destination, destinationDate, destinationTime);

        if (!serverService.getReservationService().checkIfSeatReserved(numberOfSeats, tripId)) {
            //Trip trip = tripService.findTripByDestinationDateTime(destination, destinationDate, destinationTime);

            Reservation reservation = new Reservation(tripId, numberOfSeats, customerFirstName, customerLastName);

            logger.info("Creating reservation: {}", reservation);

            serverService.getReservationService().saveReservation(reservation);
        } else {
            logger.warn("Seat already reserved for trip: {}", tripId);
            HomeControllerAlerts.showBookSeatErrorAlert();
        }
    }

    private void populateSeatInformationTable(Trip searchedTrip, String searchedTripId, List<Integer> bookedSeats) {
        logger.debug("Populating seat information table...");

        ObservableList<String> seatInfoList = FXCollections.observableArrayList();

        for (int i = 1; i <= searchedTrip.getAvailable_seats(); i++) {
            String seatInfo = "Seat " + i;
            seatInfoList.add(seatInfo);
        }

        seatInformationTable.setItems(seatInfoList);

        seatNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        // Set the cell value factory for the customerNameColumn to fetch the customer name
        customerNameColumn.setCellValueFactory(data -> {
            String seatInfo = data.getValue();
            // Extract seat number from seat information
            String seatNumber = seatInfo.substring(seatInfo.indexOf("Seat ") + 5).split(" ")[0];
            if (bookedSeats.contains(Integer.parseInt(seatNumber))) {
                String customerName = serverService.getReservationService().findCustomerNameForSeat(searchedTripId, Integer.parseInt(seatNumber));
                return new SimpleStringProperty(customerName);
            } else {
                return new SimpleStringProperty("");
            }
        });

        logger.debug("Seat information table populated.");
    }


    public void initialize(URL location, ResourceBundle resources) {
        initCurrentUser();
        populateTripsTable();
    }

    private void initCurrentUser() {
        CurrentUser user = CurrentUser.getInstance();
        if (user != null && user.getEmail() != null && user.getPassword() != null) {
            Employee employee = serverService.getEmployeeService().findEmployee(user.getEmail(), user.getPassword());
            if (employee != null) {
                user.setId(employee.getId());
            }
        }
    }

    private void populateTripsTable() {
        logger.debug("Populating trips table...");

        List<Trip> trips = serverService.getTripService().getAllTrips();

        ObservableList<Trip> tripObservableList = FXCollections.observableArrayList(trips);

        tripsTable.setItems(tripObservableList);

        destinationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestination()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival_time()));
        availableSeatsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAvailable_seats()).asObject());

        logger.debug("Trips table populated.");
    }

    @FXML
    private void handleLogout() {
        logger.info("Logging out...");

        CurrentUser.getInstance().setId(null);
        CurrentUser.getInstance().setFirstName(null);
        CurrentUser.getInstance().setLastName(null);
        CurrentUser.getInstance().setEmail(null);
        CurrentUser.getInstance().setPassword(null);
        CurrentUser.getInstance().setPhone_number(null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxmpp/signin.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) buttonLogout.getScene().getWindow();
            currentStage.close();

            logger.info("Logged out successfully.");
        } catch (IOException e) {
            logger.error("Error occurred during logout: {}", e.getMessage(), e);
        }
    }
}
