package com.project.ticketbookingsystem.Service;

import com.project.ticketbookingsystem.JavaCLI.Configuration;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CliService {

    private static final String CONFIG_FILE = "configs.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ExecutorService executorService; // Thread pool to manage CLI threads
    private final List<Thread> threads = new ArrayList<>(); // List to keep track of running threads
    private volatile boolean isRunning = false; // Flag to control thread execution
    private final StringBuilder summary = new StringBuilder(); // To store CLI summary logs

    public String saveConfiguration(Configuration configuration) {
        try {
            List<Configuration> configurations = Configuration.loadConfig(CONFIG_FILE, gson);
            configurations.add(configuration);
            Configuration.saveConfig(configurations, CONFIG_FILE, gson);
            return "Configuration saved successfully.";
        } catch (IOException e) {
            return "Failed to save configuration: " + e.getMessage();
        }
    }

    public synchronized String startCLI() {
        if (isRunning) {
            return "CLI is already running.";
        }

        try {
            // Load configuration
            Configuration configuration = loadDefaultConfiguration();
            if (configuration == null) {
                return "No valid configuration found.";
            }

            // Initialize the thread pool
            executorService = Executors.newFixedThreadPool(configuration.getNumVendors() + configuration.getNumCustomers());
            isRunning = true;

            // Create vendor and customer threads
            createThreads(configuration);

            // Start threads using the thread pool
            for (Thread thread : threads) {
                executorService.submit(thread);
            }

            return "CLI started successfully.";
        } catch (Exception e) {
            isRunning = false;
            return "Failed to start CLI: " + e.getMessage();
        }
    }

    public synchronized String stopCLI() {
        if (!isRunning) {
            return "No CLI process is currently running.";
        }

        // Stop all threads and shutdown executor
        isRunning = false;
        executorService.shutdownNow();
        threads.clear();
        return "CLI process stopped successfully.";
    }

    public String getSummary() {
        return summary.toString();
    }

    private Configuration loadDefaultConfiguration() {
        try {
            List<Configuration> configurations = Configuration.loadConfig(CONFIG_FILE, gson);
            if (!configurations.isEmpty()) {
                return configurations.get(0); // Load the first configuration as default
            }
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
        return null;
    }

    private void createThreads(Configuration configuration) {
        threads.clear();

        // Create vendor threads
        for (int i = 1; i <= configuration.getNumVendors(); i++) {
            threads.add(new Thread(() -> {
                while (isRunning) {
                    try {
                        // Simulate vendor work
                        System.out.println("Vendor is processing tickets...");
                        Thread.sleep(configuration.getTicketReleaseRate() * 1000L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }, "Vendor-" + i));
        }

        // Create customer threads
        for (int i = 1; i <= configuration.getNumCustomers(); i++) {
            threads.add(new Thread(() -> {
                while (isRunning) {
                    try {
                        // Simulate customer work
                        System.out.println("Customer is retrieving tickets...");
                        Thread.sleep(configuration.getCustomerRetrievalRate() * 1000L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }, "Customer-" + i));
        }
    }
}
