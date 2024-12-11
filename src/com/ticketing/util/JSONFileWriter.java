package com.ticketing.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for writing and reading data to/from JSON files.
 */
public class JSONFileWriter {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();  // Use pretty printing

    /**
     * Writes a JSON object to a file.
     *
     * @param json     The JSON object to write.
     * @param filename The filename where the data will be saved.
     */
    public static void writeToJSON(JsonObject json, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(json, writer);  // Pretty print JSON
            System.out.println("Configuration successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write to JSON file: " + e.getMessage());
        }
    }

    /**
     * Reads a JSON object from a file.
     *
     * @param filename The filename to read from.
     * @return The JSON object, or null if there was an error.
     */
    public static JsonObject readFromJSON(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Failed to read from JSON file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes a list of TicketRecord entries to a JSON file.
     *
     * @param records  List of TicketRecord entries to be written.
     * @param filename Name of the JSON file.
     */
    public static void writeTicketRecordsToJSON(Object records, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(records, writer);  // Pretty print JSON
            System.out.println("Records successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write records to JSON file: " + e.getMessage());
        }
    }

    /**
     * Writes the configuration data to a JSON file.
     *
     * @param config   The Configuration object to save.
     * @param filename Name of the JSON file.
     */
    public static void writeConfigurationToJSON(Object config, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(config, writer);  // Pretty print JSON
            System.out.println("Configuration successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write configuration to JSON file: " + e.getMessage());
        }
    }

    /**
     * Reads the configuration from a JSON file and returns a Configuration object.
     *
     * @param filename Name of the JSON file.
     * @return The loaded Configuration object.
     */
    public static Object readConfigurationFromJSON(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Object.class);
        } catch (IOException e) {
            System.err.println("Failed to read configuration from JSON file: " + e.getMessage());
            return null;  // Return null if there's an error
        }
    }
}
