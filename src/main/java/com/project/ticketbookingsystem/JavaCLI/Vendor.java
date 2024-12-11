package com.project.ticketbookingsystem.JavaCLI;

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private String eventName;
    private double ticketPrice;

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool,String eventName,double ticketPrice) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

//    @Override
//    public void run() {
//        for (int i = 1; i <= totalTickets; i++) {
//            Ticket ticket = new Ticket(i, "Event No" + i, 1000);
//            ticketPool.addTicket(ticket);
//            try {
//                Thread.sleep(ticketReleaseRate * 1000L);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException("Thread interrupted: " + e.getMessage());
//            }
//        }
//    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets && Main.isRunning(); i++) {
            Ticket ticket = new Ticket(i, eventName, ticketPrice);
            ticketPool.addTicket(ticket);
            Logger.log("Ticket added by - " + Thread.currentThread().getName() + " - current size is - " + ticketPool.getTicketQueueSize());
            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //System.out.println("Vendor interrupted.");
                Logger.log("Vendor interrupted: " + Thread.currentThread().getName());
            }
        }
    }

}
