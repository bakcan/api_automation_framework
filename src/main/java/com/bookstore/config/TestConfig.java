package com.bookstore.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for reading test properties and environment variables
 */
public class TestConfig {
    private static TestConfig instance;
    private Properties properties;

    private TestConfig() {
        properties = new Properties();
        loadProperties();
    }

    public static TestConfig getInstance() {
        if (instance == null) {
            synchronized (TestConfig.class) {
                if (instance == null) {
                    instance = new TestConfig();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    /**
     * Get property value, with environment variable override
     * Environment variables take precedence over config.properties
     */
    public String getProperty(String key) {
        // Check environment variable first (replace . with _)
        String envKey = key.toUpperCase().replace(".", "_");
        String envValue = System.getenv(envKey);
        
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }
        
        // Fall back to properties file
        return properties.getProperty(key);
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBasePath() {
        return getProperty("base.path");
    }

    public int getConnectionTimeout() {
        return Integer.parseInt(getProperty("connection.timeout"));
    }

    public int getSocketTimeout() {
        return Integer.parseInt(getProperty("socket.timeout"));
    }

    public boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("enable.request.logging"));
    }

    public boolean isResponseLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("enable.response.logging"));
    }
}
