package com.hotel.utils;

import com.hotel.datastore.DataStore;
import com.hotel.models.Reservation;
import com.hotel.models.Room;

import java.io.FileWriter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReportGenerator {
    private final DataStore ds;

    public ReportGenerator(DataStore ds) { this.ds = ds; }

    public void generateReservationsReport() {
        try (FileWriter w = new FileWriter("data/reservations_report.csv")) {
            w.write("res_id,guest_id,room,check_in,check_out,nights\n");
            for (Reservation r : ds.allReservations()) {
                long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
                w.write(String.format("%d,%d,%d,%s,%s,%d\n", r.getId(), r.getGuestId(),
                        r.getRoomNumber(), r.getCheckIn(), r.getCheckOut(), nights));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void generateOccupancyReport() {
        try (FileWriter w = new FileWriter("data/reports.csv")) {
            w.write("room,bookings\n");
            for (Room room : ds.allRooms()) {
                List<Reservation> byRoom = ds.findReservationsByRoom(room.getNumber());
                w.write(String.format("%d,%d\n", room.getNumber(), byRoom.size()));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
