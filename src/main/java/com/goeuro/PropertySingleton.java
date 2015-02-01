package com.goeuro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Property singleton. Allowing access to the configuration file in the entire application.
 */
public class PropertySingleton {

    private static final String CONFIG_FILE = "config.properties";

    private static PropertySingleton instance;

    private Properties properties = new Properties();

    private PropertySingleton() {
        loadPropertyFile();
    }

    /**
     * Creates or returns the singleton object for the configuration.
     *
     * @return The instance of the singleton.
     */
    public static synchronized PropertySingleton getInstance() {

        if (instance == null) {
            instance = new PropertySingleton();
        }
        return instance;
    }

    /**
     * Load the property file.
     * Will stop the application if it fails to load the file.
     */
    private void loadPropertyFile() {

        try (InputStream input = this.getClass().getResourceAsStream("/META-INF/" + CONFIG_FILE)) {

            if (input == null) {
                throw new RuntimeException("Configuration could not be loaded."); // no sense to continue from here
            }

            // load a properties file
            properties.load(input);

        } catch (IOException ioe) {
            System.out.println("Failed to load properties '" + CONFIG_FILE + "'.");
            throw new RuntimeException("Configuration could not be loaded.", ioe); // no sense to continue from here
        }
    }

    /** Returns the loaded properties */
    public Properties getProperties() {
        return properties;
    }
}
