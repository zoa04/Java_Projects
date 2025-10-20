package com.zoa.todo;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;
public class Persistence {
    private final Path file;
    private final Gson gson;
    public Persistence(Path path) {
        this.file = path;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    public List<Task> load() {
        try {
            if (Files.exists(file)) {
                String json = Files.readString(file);
                Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
                List<Task> list = gson.fromJson(json, listType);
                return list == null ? new ArrayList<>() : list;
            }
        } catch (IOException e) { System.err.println("Falha ao ler arquivo: " + e.getMessage()); }
        return new ArrayList<>();
    }
    public void save(List<Task> tasks) {
        try (Writer w = Files.newBufferedWriter(file)) { gson.toJson(tasks, w); }
        catch (IOException e) { System.err.println("Falha ao salvar arquivo: " + e.getMessage()); }
    }
}