package com.hotel;

import com.hotel.service.HotelService;
import com.hotel.models.Guest;
import com.hotel.models.Room;
import com.hotel.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        HotelService svc = new HotelService();
        svc.initSampleData(); // optional: create sample rooms

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Register guest");
            System.out.println("2. List guests");
            System.out.println("3. Make reservation");
            System.out.println("4. List reservations");
            System.out.println("5. Check availability");
            System.out.println("6. Generate reports");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    Guest g = svc.registerGuest(name, email);
                    System.out.println("Guest saved: " + g);
                    break;
                case "2":
                    svc.getAllGuests().forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("Guest id: ");
                    String gid = sc.nextLine();
                    System.out.print("Room number: ");
                    String rn = sc.nextLine();
                    System.out.print("Check-in (YYYY-MM-DD): ");
                    String in = sc.nextLine();
                    System.out.print("Check-out (YYYY-MM-DD): ");
                    String out = sc.nextLine();
                    try {
                        Reservation r = svc.createReservation(Long.parseLong(gid), Integer.parseInt(rn),
                                LocalDate.parse(in), LocalDate.parse(out));
                        System.out.println("Reservation created: " + r);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "4":
                    svc.getAllReservations().forEach(System.out::println);
                    break;
                case "5":
                    System.out.print("Start (YYYY-MM-DD): ");
                    String s = sc.nextLine();
                    System.out.print("End (YYYY-MM-DD): ");
                    String e = sc.nextLine();
                    svc.findAvailableRooms(LocalDate.parse(s), LocalDate.parse(e))
                            .forEach(System.out::println);
                    break;
                case "6":
                    svc.generateReports();
                    System.out.println("Reports generated in data/reports.csv and data/reservations_report.csv");
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
