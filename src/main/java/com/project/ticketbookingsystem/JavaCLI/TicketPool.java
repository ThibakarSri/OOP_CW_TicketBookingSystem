package com.project.ticketbookingsystem.JavaCLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maximumTicketCapacity;
    private final Queue<Ticket> ticketQueue;

    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new LinkedList<>();

        // Add initial tickets if needed
//        for (int i = 1; i <= Math.min(5, maximumTicketCapacity); i++) {
//            ticketQueue.add(new Ticket(i, "Initial Event", 1000));
//        }
    }

    // Vendor who is the Producer will call the addTicket() method
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            System.out.println("The ticket pool is full so vendors are waiting for add tickets...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reset thread's interrupted status
                throw new RuntimeException("Vendor thread interrupted while waiting: " + e.getMessage());
            }
        }
        ticketQueue.add(ticket);
        notifyAll(); // Notify all waiting threads when the condition changes
        System.out.println("Ticket added by - " + Thread.currentThread().getName() + " - current size is - " + ticketQueue.size());
    }

    // Customer who is the Consumer will call the buyTicket() method
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            System.out.println("The TicketPool is empty so customers are waiting for buy tickets...");
            try {
                wait(); // If queue is empty, customers wait
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reset thread's interrupted status
                throw new RuntimeException("Customers thread interrupted while waiting: " + e.getMessage());
            }
        }

        Ticket ticket = ticketQueue.poll();
        notifyAll(); // Notify all waiting threads when the condition changes
        System.out.println("Ticket bought by - " + Thread.currentThread().getName() + " - current size is - " + ticketQueue.size() + " - Ticket is - " + ticket);
        return ticket;
    }

    // Get current ticket queue size
    public int getTicketQueueSize() {
        return ticketQueue.size();
    }
}
