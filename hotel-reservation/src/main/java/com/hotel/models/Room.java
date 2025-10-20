package com.hotel.models;

public class Room {
    private int number;
    private String type;
    private double pricePerNight;

    public Room() {}

    public Room(int number, String type, double pricePerNight) {
        this.number = number; this.type = type; this.pricePerNight = pricePerNight;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    @Override
    public String toString() {
        return "Room{" + number + ":" + type + "," + pricePerNight + "}";
    }
}
