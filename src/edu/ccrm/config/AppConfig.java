package edu.ccrm.config;

/**
 * Application configuration management using Singleton pattern
 * Thread-safe implementation because you never know when concurrency might matter
 */
public class AppConfig {
    // The one and only instance - using volatile for thread safety
    private static volatile AppConfig singletonInstance;
    
    // Configuration settings
    private String primaryDataFolder = "data";
    private boolean debugModeEnabled = false;
    private int maxStudentsPerCourse = 100;  // reasonable default

    // Private constructor - nobody can create instances directly
    private AppConfig() {
        // Maybe load from properties file here in the future
    }

    /**
     * Get the singleton instance using double-checked locking
     * This is the "proper" thread-safe way to do singletons in Java
     */
    public static AppConfig getInstance() {
        if (singletonInstance == null) {
            synchronized (AppConfig.class) {
                // Double-check inside the synchronized block
                if (singletonInstance == null) {
                    singletonInstance = new AppConfig();
                }
            }
        }
        return singletonInstance;
    }

    /**
     * Initialize configuration settings
     * Could be extended to read from config files, environment variables, etc.
     */
    public void loadConfig() {
        System.out.println("Loading application configuration...");
        System.out.println("Primary data folder: " + primaryDataFolder);
        System.out.println("Debug mode: " + (debugModeEnabled ? "ON" : "OFF"));
        System.out.println("Max students per course: " + maxStudentsPerCourse);
        System.out.println("Configuration loaded successfully!\n");
    }
    
    // Getters for configuration values
    public String getDataFolder() {
        return primaryDataFolder;
    }
    
    public boolean isDebugEnabled() {
        return debugModeEnabled;
    }
    
    public int getMaxStudentsPerCourse() {
        return maxStudentsPerCourse;
    }
    
    // Setters for dynamic configuration changes
    public void setDataFolder(String newDataFolder) {
        this.primaryDataFolder = newDataFolder;
    }
    
    public void setDebugMode(boolean enableDebug) {
        this.debugModeEnabled = enableDebug;
    }
}
