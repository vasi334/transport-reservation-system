package com.example.javafxmpp.service;

import com.example.javafxmpp.repository.EmployeeRepo;
import com.example.javafxmpp.repository.ResevationRepo;
import com.example.javafxmpp.repository.TripRepo;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerService {
    private static ServerService instance;
    private final EmployeeRepo employeeRepo;
    private final TripRepo tripRepo;
    private final ResevationRepo reservationRepo;

    private ServerService() {
        this.employeeRepo = instantiateEmployeeRepo();
        this.tripRepo = instantiateTripRepo();
        this.reservationRepo = instantiateReservationRepo();
    }

    public static synchronized ServerService getInstance() {
        if (instance == null) {
            instance = new ServerService();
        }
        return instance;
    }

    private EmployeeRepo instantiateEmployeeRepo() {
        Properties props = loadProperties();
        return new EmployeeRepo(props);
    }

    private TripRepo instantiateTripRepo() {
        Properties props = loadProperties();
        return new TripRepo(props);
    }

    private ResevationRepo instantiateReservationRepo() {
        Properties props = loadProperties();
        return new ResevationRepo(props);
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        return props;
    }

    public EmployeeService getEmployeeService() {
        return new EmployeeService(employeeRepo);
    }

    public TripService getTripService() {
        return new TripService(tripRepo);
    }

    public ReservationService getReservationService() {
        return new ReservationService(reservationRepo);
    }
}
