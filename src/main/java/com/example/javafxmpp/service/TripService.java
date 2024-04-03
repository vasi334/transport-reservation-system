package com.example.javafxmpp.service;

import com.example.javafxmpp.model.Trip;
import com.example.javafxmpp.repository.TripRepo;

import java.util.List;

public class TripService{
    private final TripRepo tripRepo;

    public TripService(TripRepo tripRepo) {
        this.tripRepo = tripRepo;
    }

    public void saveTrip(Trip trip) {
        tripRepo.save(trip);
    }

    public void deleteTripById(String id) {
        tripRepo.delete(id);
    }

    public void deleteAllTrips() {
        tripRepo.deleteAll();
    }

    public boolean existsTripById(String id) {
        return tripRepo.existsById(id);
    }

    public Trip findTripById(String id) {
        return tripRepo.findById(id);
    }

    public long countTrips() {
        return tripRepo.count();
    }

    public List<Trip> getAllTrips() {
        return (List<Trip>) tripRepo.findAll();
    }

    public Trip findTripByDestinationDateTime(String destination, String  date, String time){
        return tripRepo.findByDestinationDateTime(destination, date, time);
    }

    public String findTripIdByDestinationDateTime(String destination, String date, String time){
        return tripRepo.findTripIdByDestinationDateTime(destination, date, time);
    };
}
