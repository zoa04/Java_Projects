package com.hotel.service;

import com.hotel.datastore.DataStore;
import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import com.hotel.models.Room;
import com.hotel.utils.ReportGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HotelService {
    private final DataStore ds = new DataStore();

    public void initSampleData() {
        if (ds.allRooms().isEmpty()) {
            ds.saveRoom(new Room(101, "Single", 120.0));
            ds.saveRoom(new Room(102, "Double", 180.0));
            ds.saveRoom(new Room(201, "Suite", 350.0));
        }
    }

    public Guest registerGuest(String name, String email) {
        Guest g = new Guest(0, name, email);
        return ds.saveGuest(g);
    }

    public List<Guest> getAllGuests() { return ds.allGuests(); }

    public Reservation createReservation(long guestId, int roomNumber, LocalDate checkIn, LocalDate checkOut) throws Exception {
        if (!ds.findGuest(guestId).isPresent()) throw new Exception("Guest not found");
        Room room = ds.findRoom(roomNumber).orElseThrow(() -> new Exception("Room not found"));
        // check availability
        boolean conflict = ds.findReservationsByRoom(roomNumber).stream().anyMatch(r ->
                !(checkOut.isEqual(r.getCheckIn()) || checkOut.isBefore(r.getCheckIn()) ||
                  checkIn.isEqual(r.getCheckOut()) || checkIn.isAfter(r.getCheckOut()))
        );
        if (conflict) throw new Exception("Room not available for the period");
        Reservation res = new Reservation(0, guestId, roomNumber, checkIn, checkOut);
        return ds.saveReservation(res);
    }

    public List<Reservation> getAllReservations() { return ds.allReservations(); }

    public List<Room> findAvailableRooms(LocalDate start, LocalDate end) {
        return ds.allRooms().stream().filter(room -> {
            boolean conflict = ds.findReservationsByRoom(room.getNumber()).stream().anyMatch(r ->
                    !(end.isEqual(r.getCheckIn()) || end.isBefore(r.getCheckIn()) ||
                      start.isEqual(r.getCheckOut()) || start.isAfter(r.getCheckOut()))
            );
            return !conflict;
        }).collect(Collectors.toList());
    }

    public void generateReports() {
        ReportGenerator gen = new ReportGenerator(ds);
        gen.generateReservationsReport();
        gen.generateOccupancyReport();
    }
}
