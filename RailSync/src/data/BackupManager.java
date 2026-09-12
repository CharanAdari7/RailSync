package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupManager {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public Path createBackup(Path source, Path backupDirectory)
            throws IOException {

        if (source == null) {
            throw new IllegalArgumentException("Source file cannot be null.");
        }

        if (!Files.exists(source)) {
            throw new IllegalArgumentException(
                    "Source file does not exist: " + source);
        }

        if (backupDirectory == null) {
            throw new IllegalArgumentException(
                    "Backup directory cannot be null.");
        }

        Files.createDirectories(backupDirectory);

        String timestamp = LocalDateTime.now().format(FORMATTER);

        String fileName = source.getFileName().toString();

        Path backupFile = backupDirectory.resolve(
                fileName + "." + timestamp + ".backup"
        );

        Files.copy(
                source,
                backupFile,
                StandardCopyOption.REPLACE_EXISTING
        );

        return backupFile;
    }

    public Path restoreBackup(Path backupFile, Path destination)
            throws IOException {

        if (backupFile == null) {
            throw new IllegalArgumentException(
                    "Backup file cannot be null.");
        }

        if (!Files.exists(backupFile)) {
            throw new IllegalArgumentException(
                    "Backup file does not exist: " + backupFile);
        }

        if (destination == null) {
            throw new IllegalArgumentException(
                    "Destination cannot be null.");
        }

        Path parent = destination.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.copy(
                backupFile,
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        return destination;
    }

    public boolean backupExists(Path backupFile) {

        return backupFile != null
                && Files.exists(backupFile)
                && Files.isRegularFile(backupFile);
    }

    public boolean deleteBackup(Path backupFile) throws IOException {

        if (backupFile == null) {
            return false;
        }

        return Files.deleteIfExists(backupFile);
    }
}