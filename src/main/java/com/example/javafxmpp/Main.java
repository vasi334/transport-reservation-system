package com.example.javafxmpp;

import com.example.javafxmpp.model.Employee;
import com.example.javafxmpp.model.Reservation;
import com.example.javafxmpp.model.Trip;
import com.example.javafxmpp.repository.EmployeeRepo;
import com.example.javafxmpp.repository.ResevationRepo;
import com.example.javafxmpp.repository.TripRepo;
import com.example.javafxmpp.repository.interfaces.IEmployeeRepo;
import com.example.javafxmpp.repository.interfaces.IReservationRepo;
import com.example.javafxmpp.repository.interfaces.ITripRepo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        IEmployeeRepo employeeRepo = new EmployeeRepo(props);

        Employee Vasile = new Employee("ieri@yahoo.com", "Mihai",
                "Ghita", "parola", "0745097211");

        //employeeRepo.save(Vasile);

        employeeRepo.delete("1");

//        employeeRepo.deleteAll();

//        employeeRepo.existsById("8");

//        employeeRepo.findById("8");

//        employeeRepo.count();
        IReservationRepo reservationRepo = new ResevationRepo(props);
        ITripRepo tripRepo = new TripRepo(props);

        // Create and save an employee
//        Employee employee = new Employee("ieri@yahoo.com", "Mihai", "Ghita", "parola", "0745097211");
//        employeeRepo.save(employee);

//        // Create and save reservations
//        Reservation reservation1 = new Reservation("1", 1, "John", "Doe");
//        Reservation reservation2 = new Reservation("2", 2, "Jane", "Smith");
//        reservationRepo.save(reservation1);
//        reservationRepo.save(reservation2);

//         Create and save trips
        Trip trip1 = new Trip("New York", Date.valueOf("2024-04-10"), "08:00 AM", "05:00 PM", 100);
        Trip trip2 = new Trip("Los Angeles", Date.valueOf("2024-04-15"), "09:00 AM", "06:00 PM", 120);
        tripRepo.save(trip1);
        tripRepo.save(trip2);
    }
}