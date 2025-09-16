package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;

/**
 * Entry point for the Campus Course & Records Manager
 * This is where everything starts - sets up config and launches the CLI
 */
public class Main {
    public static void main(String[] args) {
        // Initialize application configuration first
        AppConfig.getInstance().loadConfig();
        
        // Create and start the main CLI application
        CLIApplication crmApplication = new CLIApplication();
        crmApplication.run();
    }
}
