package com.project.ticketbookingsystem.Controller;

import com.project.ticketbookingsystem.JavaCLI.Configuration;
import com.project.ticketbookingsystem.Service.CliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cli")
public class ConfigurationController {

    private final CliService cliService;

    @Autowired
    public ConfigurationController(CliService cliService) {
        this.cliService = cliService;
    }

    /**
     * Endpoint to save configuration to configs.json.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveConfiguration(@RequestBody Object config) {
        cliService.saveConfiguration((Configuration) config);
        return ResponseEntity.ok("Configuration saved successfully.");
    }

    /**
     * Endpoint to start the CLI process.
     */
    @PostMapping("/start")
    public ResponseEntity<String> startCLI() throws IOException {
        try {
            cliService.startCLI();
            return ResponseEntity.ok("CLI process started successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    /**
     * Endpoint to stop the CLI process.
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopCLI() {
        cliService.stopCLI();
        return ResponseEntity.ok("CLI process stopped successfully.");
    }

    /**
     * Endpoint to get the summary from the CLI process.
     */
    @GetMapping("/summary")
    public ResponseEntity<String> getSummary() throws IOException {
        String summary = cliService.getSummary();
        return ResponseEntity.ok(summary);
    }
}



//package com.project.ticketbookingsystem.Controller;
//
//
//import com.project.ticketbookingsystem.JavaCLI.Configuration;
//import com.project.ticketbookingsystem.Service.CliService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cli")
//public class ConfigurationController {
//
//    @Autowired
//    private CliService cliService; // Service to handle CLI operations
//
//    @PostMapping("/save")
//    public String saveConfiguration(@RequestBody Configuration configuration) {
//        return cliService.saveConfiguration(configuration);
//    }
//
//    @PostMapping("/start")
//    public String startCI() {
//        return cliService.startCLI();
//    }
//
//    @PostMapping("/stop")
//    public String stopCLI() {
//        return cliService.stopCLI();
//    }
//
//    @GetMapping("/summary")
//    public String getSummary() {
//        return cliService.getSummary();
//    }
//}

