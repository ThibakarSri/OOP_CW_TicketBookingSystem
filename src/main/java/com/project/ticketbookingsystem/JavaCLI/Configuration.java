package com.project.ticketbookingsystem.JavaCLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Configuration {
    private String eventName;
    private double ticketPrice;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int numVendors;
    private int numCustomers;
    private int ticketsPerCustomer;


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getNumVendors() {
        return numVendors;
    }

    public void setNumVendors(int numVendors) {
        this.numVendors = numVendors;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public void setNumCustomers(int numCustomers) {
        this.numCustomers = numCustomers;
    }

    public int getTicketsPerCustomer() {
        return ticketsPerCustomer;
    }

    public void setTicketsPerCustomer(int ticketsPerCustomer) {
        this.ticketsPerCustomer = ticketsPerCustomer;
    }



    // Save a list of configurations to JSON
    public static void saveConfig(List<Configuration>configList, String filename, Gson gson) throws IOException
    {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(configList, writer);
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }



    // Load a list of configurations from JSON
    public static List<Configuration> loadConfig(String filename, Gson gson) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<Configuration>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", numVendors=" + numVendors +
                ", numCustomers=" + numCustomers +
                ", ticketsPerCustomer=" + ticketsPerCustomer +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}




//package com.project.ticketbookingsystem.JavaCLI;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.List;
//
////@Entity
////@Table(name="tiba")
//public class Configuration {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private int totalTickets;
//    private int ticketReleaseRate;
//    private int customerRetrievalRate;
//    private int maxTicketCapacity;
//    private int numVendors;
//    private int numCustomers;
//    private int maxTicketsPerCustomer;
//
//    public int getTotalTickets() {
//        return totalTickets;
//    }
//
//    public void setTotalTickets(int totalTickets) {
//        this.totalTickets = totalTickets;
//    }
//
//    public int getTicketReleaseRate() {
//        return ticketReleaseRate;
//    }
//
//    public void setTicketReleaseRate(int ticketReleaseRate) {
//        this.ticketReleaseRate = ticketReleaseRate;
//    }
//
//    public int getCustomerRetrievalRate() {
//        return customerRetrievalRate;
//    }
//
//    public void setCustomerRetrievalRate(int customerRetrievalRate) {
//        this.customerRetrievalRate = customerRetrievalRate;
//    }
//
//    public int getMaxTicketCapacity() {
//        return maxTicketCapacity;
//    }
//
//    public void setMaxTicketCapacity(int maxTicketCapacity) {
//        this.maxTicketCapacity = maxTicketCapacity;
//    }
//
//    public int getNumVendors() {
//        return numVendors;
//    }
//
//    public void setNumVendors(int numVendors) {
//        this.numVendors = numVendors;
//    }
//
//    public int getNumCustomers() {
//        return numCustomers;
//    }
//
//    public void setNumCustomers(int numCustomers) {
//        this.numCustomers = numCustomers;
//    }
//
//    public int getMaxTicketsPerCustomer() {
//        return maxTicketsPerCustomer;
//    }
//
//    public void setMaxTicketsPerCustomer(int maxTicketsPerCustomer) {
//        this.maxTicketsPerCustomer = maxTicketsPerCustomer;
//    }
//
//
//
//    // Save a list of configurations to JSON
//    public static void saveConfig(List<Configuration>configList, String filename, Gson gson) throws IOException
//    {
//        try (FileWriter writer = new FileWriter(filename)) {
//            gson.toJson(configList, writer);
//        }
////        catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//    // Load a list of configurations from JSON
//    public static List<Configuration> loadConfig(String filename, Gson gson) throws IOException {
//        try (FileReader reader = new FileReader(filename)) {
//            Type listType = new TypeToken<List<Configuration>>() {}.getType();
//            return gson.fromJson(reader, listType);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Configuration{" +
//                "totalTickets=" + totalTickets +
//                ", ticketReleaseRate=" + ticketReleaseRate +
//                ", customerRetrievalRate=" + customerRetrievalRate +
//                ", maxTicketCapacity=" + maxTicketCapacity +
//                ", numVendors=" + numVendors +
//                ", numCustomers=" + numCustomers +
//                ", maxTicketsPerCustomer=" + maxTicketsPerCustomer +
//                '}';
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
