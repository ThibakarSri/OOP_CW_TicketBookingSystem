package com.project.ticketbookingsystem.JavaCLI;

import com.google.gson.Gson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static volatile boolean isRunning = true; // Flag to control thread execution
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILENAME = "configs.json";

    public static void main(String[] args) {
        while (true) {
            System.out.println("||-----------------------------------------------------------------||");
            System.out.println("|----------------------Ticket Booking System------------------------|");
            System.out.println("||-----------------------------------------------------------------||");
            // Load or Create Configuration
            Configuration configuration = loadOrCreateConfiguration();

            // Initialize TicketPool
            TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity());

            // Create threads for vendors and customers
            List<Thread> threads = createThreads(configuration, ticketPool);

            // Control the simulation interactively
            controlSimulation(threads, configuration, ticketPool);

            System.out.println("Do you want to restart the system? (Yes to restart / No to exit):");
            String restartCommand = scanner.nextLine().trim().toLowerCase();
            if (!restartCommand.equals("yes")) {
                System.out.println("Exiting Ticketing System...");
                System.exit(0);
            }
        }
    }

    // Section 1: Load or Create Configuration
    private static Configuration loadOrCreateConfiguration() {
        List<Configuration> configurations = new ArrayList<>();
        Configuration configuration = new Configuration();

        // Prompt to load existing configurations
        System.out.println("Load existing configurations? (Yes/No):");
        String loadConfig = scanner.nextLine().trim().toLowerCase();

        if (loadConfig.equals("yes")) {
            try {
                configurations = Configuration.loadConfig(FILENAME, gson);
                System.out.println("Available configurations:");
                for (int i = 0; i < configurations.size(); i++) {
                    System.out.println((i + 1) + ": " + configurations.get(i));
                }
                System.out.println("Select a configuration to load (1-" + configurations.size() + "):");
                int choice = promptInt("", 1, configurations.size());
                configuration = configurations.get(choice - 1);
                System.out.println("Loaded configuration:\n" + configuration);
                return configuration; // Return the loaded configuration
            } catch (Exception e) {
                System.out.println("JSON file is empty or Failed to load configurations.\n Starting fresh.");
            }
        }

        // Prompt for user input to create a new configuration
        System.out.println("Please configure the system:");
        configuration.setEventName(promptString("Enter the event name:"));
        configuration.setTicketPrice(promptDouble("Enter the ticket price:", 1.0, 10000.0));
        configuration.setTotalTickets(promptInt("Enter total number of tickets:", 1, 1000));
        configuration.setTicketReleaseRate(promptInt("Enter ticket release rate (seconds):", 1, 10));
        configuration.setCustomerRetrievalRate(promptInt("Enter customer retrieval rate (seconds):", 1, 10));
        configuration.setMaxTicketCapacity(promptInt("Enter maximum ticket capacity:", 1, 10000));
        configuration.setNumVendors(promptInt("Enter the number of vendors:", 1, 100));
        configuration.setNumCustomers(promptInt("Enter the number of customers:", 1, 100));
        configuration.setTicketsPerCustomer(promptInt("Enter the number of tickets each customer can buy:", 1, configuration.getTotalTickets()));

        // Ask if the user wants to save the configuration
        System.out.println("Do you want to save the configuration? (Yes/No)");
        String saveConfig = scanner.nextLine().trim().toLowerCase();
        if (saveConfig.equals("yes")) {
            try {
                configurations = Configuration.loadConfig(FILENAME, gson);
            } catch (Exception ignored) {
                System.out.println("Failed to load configurations. Starting fresh.");
                // If the file doesn't exist, start with an empty list
                configurations = new ArrayList<>();
            }
            configurations.add(configuration);
            saveConfigurations(configurations);
        }

        return configuration;
    }

    // Section 2: Create Threads
    private static List<Thread> createThreads(Configuration configuration, TicketPool ticketPool) {
        List<Thread> threads = new ArrayList<>();

        // Create vendor threads
        for (int i = 1; i <= configuration.getNumVendors(); i++) {
            Thread vendorThread = new Thread(new Vendor(
                    configuration.getTotalTickets() / configuration.getNumVendors(),
                    configuration.getTicketReleaseRate(),
                    ticketPool,
                    configuration.getEventName(),
                    configuration.getTicketPrice()), "Vendor-" + i);
            threads.add(vendorThread);
        }

        // Create customer threads
        for (int i = 1; i <= configuration.getNumCustomers(); i++) {
            Thread customerThread = new Thread(new Customer(
                    ticketPool,
                    configuration.getCustomerRetrievalRate(),
                    configuration.getTicketsPerCustomer()), // Maximum tickets per customer
                    "Customer-" + i);
            threads.add(customerThread);
        }

        return threads;
    }

    // Section 3: Control Simulation
    private static void controlSimulation(List<Thread> threads, Configuration configuration, TicketPool ticketPool) {
        System.out.println("Enter 'start' to begin, 'stop' to terminate the simulation, or 'exit' to quit:");

        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.equals("start")) {
                isRunning = true;
                System.out.println("Starting ticket handling process...");
                for (Thread thread : threads) {
                    thread.start();
                }
            } else if (command.equals("stop")) {
                isRunning = false;
                System.out.println("Stopping ticket handling process...");
                break;
            } else if (command.equals("exit")) {
                isRunning = false;
                System.out.println("Exiting the system...");
                System.exit(0);
            } else {
                System.out.println("Invalid command. Please enter 'start', 'stop', or 'exit'.");
            }
        }

        // Wait for threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread execution interrupted: " + e.getMessage());
            }
        }

        // Display summary after stopping
        System.out.println("Simulation Summary:");
        System.out.println("Event Name: " + configuration.getEventName());
        System.out.println("Ticket Price: " + configuration.getTicketPrice());
        System.out.println("Total Tickets Sold: " + (configuration.getTotalTickets() - ticketPool.getTicketQueueSize()));
        System.out.println("Remaining Tickets: " + ticketPool.getTicketQueueSize());
    }

    // Section 4: Save Configurations
    private static void saveConfigurations(List<Configuration> configurations) {
        try {
            Configuration.saveConfig(configurations, FILENAME, gson);
            System.out.println("Configuration saved successfully.");
        } catch (Exception e) {
            System.out.println("Failed to save configuration: " + e.getMessage());
        }
    }

    // Helper: Prompt for Integer Input
    private static int promptInt(String message, int min, int max) {
        int value;
        while (true) {
            System.out.print(message + " ");
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) break;
                else System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    // Helper: Prompt for String Input
    private static String promptString(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }

    // Helper: Prompt for Double Input
    private static double promptDouble(String message, double min, double max) {
        double value;
        while (true) {
            System.out.print(message + " ");
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min && value <= max) break;
                else System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    // Helper: Check if System is Running
    public static boolean isRunning() {
        return isRunning;
    }
}
