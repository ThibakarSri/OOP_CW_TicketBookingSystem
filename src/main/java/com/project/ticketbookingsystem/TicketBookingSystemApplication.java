package com.project.ticketbookingsystem;

import com.project.ticketbookingsystem.JavaCLI.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TicketBookingSystemApplication implements CommandLineRunner  {
    public static void main(String[] args) {
        SpringApplication.run(TicketBookingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main.main(args);
    }
}