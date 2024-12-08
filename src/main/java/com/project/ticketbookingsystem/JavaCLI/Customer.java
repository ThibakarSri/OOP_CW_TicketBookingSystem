package com.project.ticketbookingsystem.JavaCLI;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int maxQuantity ;



    public Customer(TicketPool ticketPool, int customerRetrievalRate, int maxQuantity ) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxQuantity  = maxQuantity ;
    }

//    @Override
//    public void run() {
//        for (int i = 0; i < maxQuantity ; i++) {
//            Ticket ticket = ticketPool.buyTicket();
//            System.out.println("Ticket purchased by - " + Thread.currentThread().getName() + ": " + ticket);
//            try {
//                Thread.sleep(customerRetrievalRate * 1000L);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException("Thread interrupted: " + e.getMessage());
//            }
//        }
//    }

    @Override
    public void run() {
        for (int i = 0; i < maxQuantity  && Main.isRunning(); i++) {
            Ticket ticket = ticketPool.buyTicket();
            //System.out.println("Ticket purchased by - " + Thread.currentThread().getName() + ": " + ticket);
            try {
                Thread.sleep(customerRetrievalRate * 2000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer interrupted.");
            }
        }
    }

}
