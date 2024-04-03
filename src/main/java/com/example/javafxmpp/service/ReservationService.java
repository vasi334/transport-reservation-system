package com.example.javafxmpp.service;

import com.example.javafxmpp.model.Reservation;
import com.example.javafxmpp.repository.ResevationRepo;

import java.util.Date;
import java.util.List;

public class ReservationService{
    private final ResevationRepo resevationRepo;

    public ReservationService(ResevationRepo resevationRepo) {
        this.resevationRepo = resevationRepo;
    }

    public void saveReservation(Reservation reservation) {
        resevationRepo.save(reservation);
    }

    public void deleteReservationById(String id) {
        resevationRepo.delete(id);
    }

    public void deleteAllReservations() {
        resevationRepo.deleteAll();
    }

    public boolean existsReservationById(String id) {
        return resevationRepo.existsById(id);
    }

    public Reservation findReservationById(String id) {
        return resevationRepo.findById(id);
    }

    public long countReservations() {
        return resevationRepo.count();
    }

    public boolean checkIfSeatReserved(Integer seatNumber, String trip_id) { return resevationRepo.checkIfSeatReserved(seatNumber, trip_id);}

    public List<Integer> findBookedSeatsForTrip(String tripId) { return resevationRepo.findBookedSeatsForTrip(tripId); }

    public String findCustomerNameForSeat(String tripId, int seatNumber) {
        return resevationRepo.findCustomerNameForSeat(tripId, seatNumber);
    }

}
