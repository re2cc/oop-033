package io.re2cc.model;

import io.re2cc.exception.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GymSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Client> clients;
    private List<MembershipType> membershipTypes;
    private List<Equipment> inventory;
    private List<ClassSession> classSessions;
    private List<AccessLog> accessLogs; // For reports
    private LocalDate systemDate;

    public GymSystem() {
        this.clients = new ArrayList<>();
        this.membershipTypes = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.classSessions = new ArrayList<>();
        this.accessLogs = new ArrayList<>();
        this.systemDate = LocalDate.now();
    }

    public void seedData() {
        // Membership Types
        MembershipType trial = new MembershipType("Trial", 10.0, 3);
        MembershipType basic = new MembershipType("Basic", 40.0, 30);
        MembershipType premium = new MembershipType("Premium", 70.0, 60);
        membershipTypes.add(trial);
        membershipTypes.add(basic);
        membershipTypes.add(premium);

        // Inventory Items
        inventory.add(new Equipment("Treadmill", 5));
        inventory.add(new Equipment("Dumbbell Rack", 8));
        inventory.add(new Equipment("Stationary Bike", 6));
        inventory.add(new Equipment("Bench Press", 4));
        inventory.add(new Equipment("Yoga Mat", 12));

        // Clients
        clients.add(new Client(1001, "Alice Smith", basic, systemDate.plusDays(15), true));
        clients.add(new Client(1002, "Bob Jones", premium, systemDate.plusDays(5), false));
        clients.add(new Client(1003, "Charlie Brown", trial, systemDate.plusDays(2), true));
        clients.add(new Client(1004, "Diana Prince", basic, systemDate.minusDays(1), false)); // Expired
        clients.add(new Client(1005, "Ethan Hunt", premium, systemDate.plusDays(20), true));

        // Class Sessions
        classSessions.add(new ClassSession(systemDate, "Spinning"));
        classSessions.add(new ClassSession(systemDate, "Yoga"));
        classSessions.add(new ClassSession(systemDate.plusDays(1), "Zumba"));
        classSessions.add(new ClassSession(systemDate.plusDays(1), "Crossfit"));
        classSessions.add(new ClassSession(systemDate.plusDays(2), "Pilates"));
    }

    // Client and access

    public void addClient(Client c) throws GymException {
        for (Client other : clients) {
            if (other.getId() == c.getId()) {
                throw new GymException("Client with ID " + c.getId() + " already exists.");
            }
        }
        clients.add(c);
    }

    public void editClient(Client c, String newName, MembershipType newType, boolean autoRenew) {
        c.setName(newName);
        c.setMembershipType(newType);
        c.setAutoRenew(autoRenew);
    }

    public Client findClientById(int id) throws ClientNotFoundException {
        for (Client c : clients) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new ClientNotFoundException("Client with ID " + id + " not found.");
    }

    public void registerAccessEnter(int id) throws ClientNotFoundException, MembershipExpiredException, GymException {
        Client client = findClientById(id);
        if (client.isInside()) {
            throw new GymException("Client " + client.getName() + " is already inside the gym.");
        }
        if (client.getExpirationDate().isBefore(systemDate)) {
            throw new MembershipExpiredException(
                    "Access denied: " + client.getName() + "'s membership expired on " + client.getExpirationDate());
        }
        client.setInside(true);
        client.addPoints(1);
        accessLogs.add(new AccessLog(client, systemDate, "ENTRY"));
    }

    public void registerAccessExit(int id) throws ClientNotFoundException, GymException {
        Client client = findClientById(id);
        if (!client.isInside()) {
            throw new GymException("Client " + client.getName() + " is not currently inside the gym.");
        }
        client.setInside(false);
        accessLogs.add(new AccessLog(client, systemDate, "EXIT"));
    }

    // Membership

    public void addMembershipType(String name, double price, int durationDays) throws GymException {
        for (MembershipType mt : membershipTypes) {
            if (mt.getName().equalsIgnoreCase(name)) {
                throw new GymException("Membership plan '" + name + "' already exists.");
            }
        }
        membershipTypes.add(new MembershipType(name, price, durationDays));
    }

    public void editMembershipType(MembershipType mt, double newPrice, int newDurationDays) {
        mt.setPrice(newPrice);
        mt.setDurationDays(newDurationDays);
    }

    public void renewMembership(Client c) throws InvalidPaymentException {
        Random random = new Random();
        if (random.nextInt(100) < 25) { // 25% chance of card decline
            throw new InvalidPaymentException("Card declined");
        }
        MembershipType mt = c.getMembershipType();
        c.setExpirationDate(systemDate.plusDays(mt.getDurationDays()));
    }

    // Class Session

    public void addClassSession(LocalDate date, String name) {
        classSessions.add(new ClassSession(date, name));
    }

    public void editClassSession(ClassSession cs, LocalDate newDate, String newName) {
        cs.setDate(newDate);
        cs.setName(newName);
    }

    public List<ClassSession> getClassSessionsForDate(LocalDate date) {
        List<ClassSession> list = new ArrayList<>();
        for (ClassSession cs : classSessions) {
            if (cs.getDate().equals(date)) {
                list.add(cs);
            }
        }
        return list;
    }

    // Helper class for grouping class sessions by date, required to populate
    // right table
    public static class ClassDateGroup {
        private final LocalDate date;
        private final int quantity;

        public ClassDateGroup(LocalDate date, int quantity) {
            this.date = date;
            this.quantity = quantity;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public List<ClassDateGroup> getClassDateGroups() {
        Map<LocalDate, Integer> counts = new HashMap<>();
        for (ClassSession cs : classSessions) {
            counts.put(cs.getDate(), counts.getOrDefault(cs.getDate(), 0) + 1);
        }
        List<ClassDateGroup> groups = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : counts.entrySet()) {
            groups.add(new ClassDateGroup(entry.getKey(), entry.getValue()));
        }
        groups.sort((g1, g2) -> g1.getDate().compareTo(g2.getDate()));
        return groups;
    }

    // Equipment Operations

    public void addEquipment(String name, int quantity) throws InventoryException {
        if (quantity < 0) {
            throw new InventoryException("Quantity cannot be negative.");
        }
        for (Equipment eq : inventory) {
            if (eq.getName().equalsIgnoreCase(name)) {
                throw new InventoryException("Equipment '" + name + "' already exists in inventory.");
            }
        }
        inventory.add(new Equipment(name, quantity));
    }

    public void editEquipment(Equipment eq, String newName, int newQuantity) throws InventoryException {
        if (newQuantity < 0) {
            throw new InventoryException("Quantity cannot be negative.");
        }
        if (!eq.getName().equalsIgnoreCase(newName)) {
            for (Equipment other : inventory) {
                if (other.getName().equalsIgnoreCase(newName)) {
                    throw new InventoryException("Equipment with name '" + newName + "' already exists.");
                }
            }
        }
        eq.setName(newName);
        eq.setQuantity(newQuantity);
    }

    // Simulated Date Progression

    public List<String> advanceOneDay() {
        systemDate = systemDate.plusDays(1);
        List<String> renewalLog = new ArrayList<>();

        for (Client c : clients) {
            if (!c.getExpirationDate().isAfter(systemDate)) {
                if (c.isAutoRenew()) {
                    try {
                        renewMembership(c);
                        MembershipType mt = c.getMembershipType();
                        renewalLog.add("SUCCESS Auto-renew for " + c.getName() + " (" + mt.getName() + " - "
                                + mt.getDurationDays() + " days)");
                    } catch (InvalidPaymentException e) {
                        renewalLog.add("FAILED Auto-renew for " + c.getName() + " (" + e.getMessage() + ")");
                    }
                } else {
                    renewalLog.add("EXPIRED membership for " + c.getName() + " (No Auto-Renew)");
                }
            }
        }
        return renewalLog;
    }

    // Serialization

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    public static GymSystem loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GymSystem) ois.readObject();
        }
    }

    // Getters and setters

    public List<Client> getClients() {
        return clients;
    }

    public List<MembershipType> getMembershipTypes() {
        return membershipTypes;
    }

    public List<Equipment> getInventory() {
        return inventory;
    }

    public List<ClassSession> getClassSessions() {
        return classSessions;
    }

    public List<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(LocalDate systemDate) {
        this.systemDate = systemDate;
    }
}
