package common.file;

import common.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides static utilities specifically useful in automated testing scenarios.
 * E.g., waiting for downloads, comparing contents, validating formats.
 */
public class FileTestUtils {
    private static final Logger logger = LogManager.getLogger(FileTestUtils.class);

    private FileTestUtils() {
        // Prevent instantiation
    }

    // ───────────────────── WAIT UTILITIES ─────────────────────

    /**
     * Waits for a specific file to appear in a directory within a timeout.
     * Ideal for verifying file downloads.
     *
     * @param dirPath   Directory to check
     * @param filename  Exact filename (e.g. "report.pdf")
     * @param timeoutSec Maximum time to wait in seconds
     * @return true if the file appears within the timeout, false otherwise
     */
    public static boolean waitForFileToAppear(String dirPath, String filename, long timeoutSec) {
        Path filePath = Paths.get(dirPath, filename);
        Instant endTime = Instant.now().plusSeconds(timeoutSec);

        logger.info("Waiting up to {} seconds for file '{}' to appear in {}", timeoutSec, filename, dirPath);
        while (Instant.now().isBefore(endTime)) {
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                logger.info("File '{}' appeared.", filename);
                return true;
            }
            try {
                Thread.sleep(Constants.FILE_POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Interrupted while waiting for file", e);
                return false;
            }
        }
        logger.warn("File '{}' did not appear within {} seconds.", filename, timeoutSec);
        return false;
    }

    /**
     * Waits for any file with a specific extension to appear in a directory.
     *
     * @param dirPath    Directory to check
     * @param extension  File extension (e.g. "csv")
     * @param timeoutSec Maximum time to wait in seconds
     * @return Path to the file if it appears, null otherwise
     */
    public static Path waitForFileWithExtensionToAppear(String dirPath, String extension, long timeoutSec) {
        Instant endTime = Instant.now().plusSeconds(timeoutSec);
        String ext = extension.startsWith(".") ? extension : "." + extension;

        logger.info("Waiting up to {} seconds for a '{}' file to appear in {}", timeoutSec, extension, dirPath);
        while (Instant.now().isBefore(endTime)) {
            if (DirectoryManager.directoryExists(dirPath)) {
                List<Path> files = DirectoryManager.listFiles(dirPath, ext);
                if (!files.isEmpty()) {
                    Path latestFile = files.get(0); // Return the first one found
                    logger.info("File with extension '{}' appeared: {}", extension, latestFile.getFileName());
                    return latestFile;
                }
            }
            try {
                Thread.sleep(Constants.FILE_POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Interrupted while waiting for file", e);
                return null;
            }
        }
        logger.warn("No '{}' file appeared within {} seconds.", extension, timeoutSec);
        return null;
    }

    /**
     * Waits until a file's size stops changing for a specified stable duration.
     * Useful for large downloads that take time to write to disk.
     *
     * @param filePath         Path to the file
     * @param stableDurationMs How long the file size must remain constant to be considered complete (in ms)
     * @param timeoutSec       Overall maximum time to wait in seconds
     * @return true if the file stabilized, false if timeout occurred
     */
    public static boolean waitForFileStable(String filePath, long stableDurationMs, long timeoutSec) {
        Path path = Paths.get(filePath);
        Instant endTime = Instant.now().plusSeconds(timeoutSec);

        if (!Files.exists(path)) {
            logger.warn("Cannot wait for stability; file does not exist: {}", filePath);
            return false;
        }

        try {
            long initialSize = Files.size(path);
            Instant stableStartTime = Instant.now();

            logger.info("Waiting up to {} seconds for file '{}' to become stable (constant size for {} ms)", timeoutSec, path.getFileName(), stableDurationMs);

            while (Instant.now().isBefore(endTime)) {
                Thread.sleep(Constants.FILE_POLL_INTERVAL_MS);
                long currentSize = Files.size(path);

                if (currentSize == initialSize) {
                    if (Duration.between(stableStartTime, Instant.now()).toMillis() >= stableDurationMs) {
                        logger.info("File '{}' is stable at {} bytes.", path.getFileName(), currentSize);
                        return true;
                    }
                } else {
                    initialSize = currentSize;
                    stableStartTime = Instant.now(); // Reset the stable timer because size grew
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted while waiting for file to stabilize", e);
        } catch (IOException e) {
            logger.error("Error reading file size for stability check: {}", filePath, e);
        }

        logger.warn("File '{}' did not stabilize within {} seconds.", path.getFileName(), timeoutSec);
        return false;
    }

    // ───────────────────── COMPARISON UTILITIES ─────────────────────

    /**
     * Performs a byte-level comparison of two files.
     *
     * @return true if files are exactly identical
     */
    public static boolean compareFileContents(String filePath1, String filePath2) {
        try {
            Path path1 = Paths.get(filePath1);
            Path path2 = Paths.get(filePath2);
            
            if (Files.size(path1) != Files.size(path2)) {
                return false;
            }

            byte[] f1 = Files.readAllBytes(path1);
            byte[] f2 = Files.readAllBytes(path2);
            return Arrays.equals(f1, f2);
        } catch (IOException e) {
            logger.error("Failed to compare file contents: {} and {}", filePath1, filePath2, e);
            throw new RuntimeException("Could not compare files", e);
        }
    }

    /**
     * Performs a line-by-line text comparison of two files.
     *
     * @return true if text contents match exactly (ignoring line ending differences like CRLF vs LF)
     */
    public static boolean compareTextContents(String filePath1, String filePath2) {
        List<String> diffs = getTextDifferences(filePath1, filePath2);
        return diffs.isEmpty();
    }

    /**
     * Returns the lines that differ between two text files.
     *
     * @return List of differences. Empty if identical.
     */
    public static List<String> getTextDifferences(String filePath1, String filePath2) {
        List<String> list1 = FileReaderManager.readLines(filePath1);
        List<String> list2 = FileReaderManager.readLines(filePath2);
        List<String> differences = new ArrayList<>();

        int maxSize = Math.max(list1.size(), list2.size());
        for (int i = 0; i < maxSize; i++) {
            if (i >= list1.size()) {
                differences.add("File1 missing line " + (i + 1) + ". File2 has: " + list2.get(i));
            } else if (i >= list2.size()) {
                differences.add("File2 missing line " + (i + 1) + ". File1 has: " + list1.get(i));
            } else if (!list1.get(i).equals(list2.get(i))) {
                differences.add("Difference at line " + (i + 1) + ":\n  File1: " + list1.get(i) + "\n  File2: " + list2.get(i));
            }
        }
        return differences;
    }

    // ───────────────────── FORMAT VALIDATION UTILITIES ─────────────────────

    /**
     * Checks if a file is a valid JSON document.
     */
    public static boolean validateJsonFormat(String filePath) {
        try {
            FileReaderManager.readJson(filePath);
            return true;
        } catch (Exception e1) {
            try {
                FileReaderManager.readJsonArray(filePath);
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    /**
     * Checks if a file is a valid XML document.
     */
    public static boolean validateXmlFormat(String filePath) {
        try {
            FileReaderManager.readXml(filePath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a file is empty (0 bytes).
     */
    public static boolean isFileEmpty(String filePath) {
        return FileManager.getFileSize(filePath) == 0;
    }

    /**
     * Checks if a file contains a specific text string.
     */
    public static boolean containsText(String filePath, String searchText) {
        String content = FileReaderManager.readText(filePath);
        return content.contains(searchText);
    }
}
