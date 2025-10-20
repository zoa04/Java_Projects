package com.hotel.models;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private long guestId;
    private int roomNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Reservation() {}

    public Reservation(long id, long guestId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        this.id = id; this.guestId = guestId; this.roomNumber = roomNumber;
        this.checkIn = checkIn; this.checkOut = checkOut;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getGuestId() { return guestId; }
    public void setGuestId(long guestId) { this.guestId = guestId; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    @Override
    public String toString() {
        return "Reservation{" + id + ":guest=" + guestId + ",room=" + roomNumber +
                "," + checkIn + "->" + checkOut + "}";
    }
}
