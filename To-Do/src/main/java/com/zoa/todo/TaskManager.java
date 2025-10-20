package com.zoa.todo;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    public synchronized void load(List<Task> loaded) {
        tasks.clear();
        if (loaded != null) {
            tasks.addAll(loaded);
            int max = tasks.stream().mapToInt(Task::getId).max().orElse(0);
            nextId.set(max + 1);
        }
    }
    public synchronized Task add(String title, String description) {
        int id = nextId.getAndIncrement();
        Task t = new Task(id, title, description);
        tasks.add(t);
        return t;
    }
    public synchronized List<Task> list() { return new ArrayList<>(tasks); }
    public synchronized Optional<Task> find(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }
    public synchronized boolean delete(int id) { return tasks.removeIf(t -> t.getId() == id); }
    public synchronized boolean toggle(int id) {
        Optional<Task> ot = find(id);
        if (ot.isPresent()) {
            Task t = ot.get();
            t.setDone(!t.isDone());
            return true;
        }
        return false;
    }
    public synchronized boolean edit(int id, String newTitle, String newDesc) {
        Optional<Task> ot = find(id);
        if (ot.isPresent()) {
            Task t = ot.get();
            if (newTitle != null && !newTitle.isBlank()) t.setTitle(newTitle);
            if (newDesc != null) t.setDescription(newDesc);
            return true;
        }
        return false;
    }
}