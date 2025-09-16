package edu.ccrm.util;

import java.nio.file.*;
import java.io.IOException;

/**
 * Utility for backing up directories
 * Recursively copies entire directory structures
 */
public class BackupUtility {

    /**
     * Create a backup of entire directory tree
     * Copies everything from source to destination directory
     */
    public static void backupDirectory(Path sourceDirectory, Path backupDestination) throws IOException {
        // First, make sure source directory actually exists
        if (!Files.exists(sourceDirectory) || !Files.isDirectory(sourceDirectory)) {
            throw new IOException("Source directory not found or invalid: " + sourceDirectory);
        }

        // Create the backup destination if it doesn't exist yet
        if (!Files.exists(backupDestination)) {
            Files.createDirectories(backupDestination);
        }

        // Use Files.walk to traverse directory tree recursively
        // This is much cleaner than manual recursion
        Files.walk(sourceDirectory)
            .forEach(currentPath -> {
                try {
                    // Calculate relative path from source
                    Path relativePath = sourceDirectory.relativize(currentPath);
                    Path targetPath = backupDestination.resolve(relativePath);
                    
                    if (Files.isDirectory(currentPath)) {
                        // Create directory if it doesn't exist in backup
                        if (!Files.exists(targetPath)) {
                            Files.createDirectories(targetPath);
                        }
                    } else {
                        // Copy file, replacing if it already exists
                        Files.copy(currentPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException ioException) {
                    // Log error but continue with other files
                    System.err.println("Failed to backup: " + currentPath + 
                                     " - Reason: " + ioException.getMessage());
                }
            });
    }
}
