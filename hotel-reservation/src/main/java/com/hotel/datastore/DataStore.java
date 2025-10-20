package com.hotel.datastore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hotel.models.Guest;
import com.hotel.models.Room;
import com.hotel.models.Reservation;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class DataStore {
    private static final String DATA_DIR = "data";
    private static final String GUESTS_FILE = DATA_DIR + "/guests.json";
    private static final String ROOMS_FILE = DATA_DIR + "/rooms.json";
    private static final String RES_FILE = DATA_DIR + "/reservations.json";
    private final Gson gson = new Gson();
    private final AtomicLong guestSeq = new AtomicLong(1);
    private final AtomicLong resSeq = new AtomicLong(1);

    private Map<Long, Guest> guests = new HashMap<>();
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Long, Reservation> reservations = new HashMap<>();

    public DataStore() {
        try { Files.createDirectories(Paths.get(DATA_DIR)); } catch (IOException ignored) {}
        loadAll();
    }

    private <T> Map<Long, T> loadMap(String file, Type typeToken) {
        try {
            Path p = Paths.get(file);
            if (!Files.exists(p)) return new HashMap<>();
            String txt = Files.readString(p);
            Type t = TypeToken.getParameterized(HashMap.class, Long.class, typeToken).getType();
            Map<Long, T> m = gson.fromJson(txt, t);
            return m == null ? new HashMap<>() : m;
        } catch (Exception e) { return new HashMap<>(); }
    }

    private <T> Map<?, T> loadMapKeyString(String file, Type valueType) {
        try {
            Path p = Paths.get(file);
            if (!Files.exists(p)) return new HashMap<>();
            String txt = Files.readString(p);
            Type t = TypeToken.getParameterized(HashMap.class, String.class, valueType).getType();
            Map<String, T> m = gson.fromJson(txt, t);
            if (m == null) return new HashMap<>();
            // convert string keys to int
            return m.entrySet().stream().collect(Collectors.toMap(e -> Integer.parseInt(e.getKey()), Map.Entry::getValue));
        } catch (Exception e) { return new HashMap<>(); }
    }

    private void loadAll() {
        try {
            // Guests (long keys)
            Path g = Paths.get(GUESTS_FILE);
            if (Files.exists(g)) {
                String txt = Files.readString(g);
                Type t = new TypeToken<HashMap<Long, Guest>>(){}.getType();
                Map<Long, Guest> m = gson.fromJson(txt,t);
                if (m!=null) {
                    guests.putAll(m);
                    long max = m.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);
                    guestSeq.set(max+1);
                }
            }
            // Rooms (int keys as string)
            Path r = Paths.get(ROOMS_FILE);
            if (Files.exists(r)) {
                String txt = Files.readString(r);
                Type t = new TypeToken<HashMap<String, Room>>(){}.getType();
                Map<String, Room> m = gson.fromJson(txt,t);
                if (m!=null) {
                    for (Map.Entry<String, Room> e : m.entrySet()) {
                        rooms.put(Integer.parseInt(e.getKey()), e.getValue());
                    }
                }
            }
            // Reservations
            Path re = Paths.get(RES_FILE);
            if (Files.exists(re)) {
                String txt = Files.readString(re);
                Type t = new TypeToken<HashMap<Long, Reservation>>(){}.getType();
                Map<Long, Reservation> m = gson.fromJson(txt,t);
                if (m!=null) {
                    reservations.putAll(m);
                    long max = m.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);
                    resSeq.set(max+1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(String file, Object obj) {
        try (Writer w = new FileWriter(file)) {
            gson.toJson(obj, w);
        } catch (Exception ignored) {}
    }

    public Guest saveGuest(Guest g) {
        if (g.getId() == 0) g.setId(guestSeq.getAndIncrement());
        guests.put(g.getId(), g);
        save(GUESTS_FILE, guests);
        return g;
    }

    public List<Guest> allGuests() { return new ArrayList<>(guests.values()); }

    public void saveRoom(Room r) {
        rooms.put(r.getNumber(), r);
        // convert keys to string for JSON stable map
        Map<String, Room> m = new HashMap<>();
        for (Map.Entry<Integer, Room> e : rooms.entrySet()) m.put(String.valueOf(e.getKey()), e.getValue());
        save(ROOMS_FILE, m);
    }

    public List<Room> allRooms() { return new ArrayList<>(rooms.values()); }

    public Reservation saveReservation(Reservation re) {
        if (re.getId() == 0) re.setId(resSeq.getAndIncrement());
        reservations.put(re.getId(), re);
        save(RES_FILE, reservations);
        return re;
    }

    public List<Reservation> allReservations() { return new ArrayList<>(reservations.values()); }

    public Optional<Room> findRoom(int number) { return Optional.ofNullable(rooms.get(number)); }

    public Optional<Guest> findGuest(long id) { return Optional.ofNullable(guests.get(id)); }

    public List<Reservation> findReservationsByRoom(int roomNumber) {
        return reservations.values().stream().filter(r -> r.getRoomNumber()==roomNumber).collect(Collectors.toList());
    }
}
