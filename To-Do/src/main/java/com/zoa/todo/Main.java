package com.zoa.todo;
import java.nio.file.*; import java.util.*;
public class Main {
    private static final String DATA_FILE = "tasks.json";
    public static void main(String[] args) {
        Path file = Paths.get(DATA_FILE);
        Persistence persistence = new Persistence(file);
        TaskManager manager = new TaskManager();
        manager.load(persistence.load());
        System.out.println("== To-Do List (Java CLI) ==");
        System.out.println("Digite 'help' para ver os comandos\n");
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String line = sc.nextLine();
            if (line == null) break;
            String[] parts = line.trim().split("\\s+", 2);
            String cmd = parts.length > 0 ? parts[0].toLowerCase() : "";
            String rest = parts.length > 1 ? parts[1] : "";
            switch (cmd) {
                case "help": printHelp(); break;
                case "add": handleAdd(manager, rest, sc); persistence.save(manager.list()); break;
                case "list": handleList(manager); break;
                case "delete": handleDelete(manager, rest); persistence.save(manager.list()); break;
                case "toggle": handleToggle(manager, rest); persistence.save(manager.list()); break;
                case "edit": handleEdit(manager, rest, sc); persistence.save(manager.list()); break;
                case "exit": case "quit": running = false; System.out.println("Saindo...\n"); break;
                default: System.out.println("Comando não reconhecido. Digite 'help'."); break;
            }
        }
        sc.close();
    }
    private static void printHelp() {
        System.out.println("Comandos:\n  add <título> - adiciona tarefa\n  list - lista tarefas\n  delete <id> - remove\n  toggle <id> - marcar/desmarcar\n  edit <id> - editar tarefa\n  exit - sair");
    }
    private static void handleAdd(TaskManager manager, String rest, Scanner sc) {
        String title = rest;
        if (title == null || title.isBlank()) { System.out.print("Título: "); title = sc.nextLine(); }
        System.out.print("Descrição (opcional): "); String desc = sc.nextLine();
        Task t = manager.add(title.trim(), desc == null ? "" : desc.trim());
        System.out.println("Tarefa criada: " + t.getId());
    }
    private static void handleList(TaskManager manager) {
        List<Task> list = manager.list();
        if (list.isEmpty()) { System.out.println("Nenhuma tarefa encontrada."); return; }
        list.forEach(t -> System.out.println(t.toString()));
    }
    private static void handleDelete(TaskManager manager, String rest) {
        try { int id = Integer.parseInt(rest.trim());
            boolean ok = manager.delete(id);
            System.out.println(ok ? "Tarefa removida." : "Tarefa não encontrada.");
        } catch (Exception e) { System.out.println("Uso: delete <id>"); }
    }
    private static void handleToggle(TaskManager manager, String rest) {
        try { int id = Integer.parseInt(rest.trim());
            boolean ok = manager.toggle(id);
            System.out.println(ok ? "Alterado." : "Tarefa não encontrada.");
        } catch (Exception e) { System.out.println("Uso: toggle <id>"); }
    }
    private static void handleEdit(TaskManager manager, String rest, Scanner sc) {
        try { int id = Integer.parseInt(rest.trim());
            Optional<Task> ot = manager.find(id);
            if (ot.isEmpty()) { System.out.println("Tarefa não encontrada."); return; }
            Task t = ot.get();
            System.out.println("Título atual: " + t.getTitle());
            System.out.print("Novo título: "); String nt = sc.nextLine();
            System.out.println("Descrição atual: " + t.getDescription());
            System.out.print("Nova descrição: "); String nd = sc.nextLine();
            boolean ok = manager.edit(id, nt.isBlank() ? null : nt.trim(), nd.isBlank() ? null : nd.trim());
            System.out.println(ok ? "Atualizado." : "Falha ao atualizar.");
        } catch (Exception e) { System.out.println("Uso: edit <id>"); }
    }
}