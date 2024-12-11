package com.ticketing.config;

import com.google.gson.JsonObject; // Importing the JsonObject class from Gson library to represent JSON objects
import com.ticketing.util.JSONFileWriter; // Importing the custom JSONFileWriter utility class for saving and loading configurations in JSON format

import java.io.File; // Importing Java's File class to check for file existence and work with files in the system
import java.util.Scanner; // Importing Scanner for reading user input from the command line

/**
 * Handles system configuration through command-line input with validation.
 * This class allows users to input key parameters and ensures data integrity
 * by saving and loading configurations.
 */

public class Configuration {
    // Instance variables to store the configuration values
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    private static final String CONFIG_FILE = "config.json"; // The file to save and load configurations

    /**
     * Prompts the user to configure system parameters with validation.
     * It also asks whether to load existing configurations or enter new ones.
     */
    public void configureSystem() {
        Scanner scanner = new Scanner(System.in);

        // Ask the user whether they want to load the existing configuration or enter a new one
        System.out.println("Do you want to load the previous configuration? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("y")) {
            // If user chooses 'y' to load, check if the configuration file exists
            File configFile = new File(CONFIG_FILE);
            if (configFile.exists() && !configFile.isDirectory()) {
                // Load the existing configuration if the file exists
                loadConfiguration();
            } else {
                // If the file doesn't exist, show this message and proceed to new configuration input
                System.out.println("No previous configurations. Enter a new configuration.");
                System.out.println("Entering new configuration...\n");

                // Prompt for new configuration values
                promptForConfiguration(scanner);
            }
        } else {
            // If user chooses 'n', directly prompt for new configuration input
            System.out.println("Entering new configuration...\n");
            promptForConfiguration(scanner);
        }

        System.out.println("\nConfiguration complete!");
        displayConfiguration();
    }

    /**
     * Prompt the user to input the configuration values.
     */
    private void promptForConfiguration(Scanner scanner) {
        totalTickets = getValidatedInput(scanner, "Enter Total Tickets (>0): ", 1, Integer.MAX_VALUE);
        ticketReleaseRate = getValidatedInput(scanner, "Enter Ticket Release Rate (milliseconds): ", 1, Integer.MAX_VALUE);
        customerRetrievalRate = getValidatedInput(scanner, "Enter Customer Retrieval Rate (milliseconds): ", 1, Integer.MAX_VALUE);
        maxTicketCapacity = getValidatedInput(scanner, "Enter Max Ticket Capacity (>= Total Tickets): ", totalTickets, Integer.MAX_VALUE);

        // After gathering the data, save the configuration for future use
        saveConfiguration();
    }

    /**
     * Validates user input to ensure it falls within the specified range.
     */
    private int getValidatedInput(Scanner scanner, String prompt, int minValue, int maxValue) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (value >= minValue && value <= maxValue) {
                    return value;
                }
                System.out.println("Value must be between " + minValue + " and " + maxValue + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Displays the current system configuration.
     */
    public void displayConfiguration() {
        System.out.println("System Configuration:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate + " ms");
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate + " ms");
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }

    /**
     * Saves the current configuration to a JSON file.
     */
    private void saveConfiguration() {
        JsonObject config = new JsonObject();
        config.addProperty("totalTickets", totalTickets);
        config.addProperty("ticketReleaseRate", ticketReleaseRate);
        config.addProperty("customerRetrievalRate", customerRetrievalRate);
        config.addProperty("maxTicketCapacity", maxTicketCapacity);

        // Use JSONFileWriter to save the configuration to the file
        JSONFileWriter.writeToJSON(config, CONFIG_FILE);
    }

    /**
     * Load the configuration from a JSON file.
     */
    public void loadConfiguration() {
        JsonObject config = JSONFileWriter.readFromJSON(CONFIG_FILE);
        if (config != null) {
            totalTickets = config.get("totalTickets").getAsInt();
            ticketReleaseRate = config.get("ticketReleaseRate").getAsInt();
            customerRetrievalRate = config.get("customerRetrievalRate").getAsInt();
            maxTicketCapacity = config.get("maxTicketCapacity").getAsInt();
            System.out.println("Configuration loaded from file.");
        }
    }

    // Getters for retrieving configuration parameters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}
