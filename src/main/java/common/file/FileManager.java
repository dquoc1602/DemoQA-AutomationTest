package common.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides static utilities for managing files (CRUD operations, metadata, copying, moving).
 */
public class FileManager {
    private static final Logger logger = LogManager.getLogger(FileManager.class);

    private FileManager() {
        // Prevent instantiation
    }

    /**
     * Creates an empty file and its parent directories if they do not exist.
     *
     * @param filePath Absolute or relative path to the file
     * @return Path to the created file
     */
    public static Path createFile(String filePath) {
        return createFile(filePath, "");
    }

    /**
     * Creates a file with initial content and its parent directories.
     *
     * @param filePath Absolute or relative path to the file
     * @param content  Initial content to write (UTF-8)
     * @return Path to the created file
     */
    public static Path createFile(String filePath, String content) {
        Path path = Paths.get(filePath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Created file successfully: {}", filePath);
            return path;
        } catch (IOException e) {
            logger.error("Failed to create file: {}", filePath, e);
            throw new RuntimeException("Could not create file: " + filePath, e);
        }
    }

    /**
     * Deletes a file. Throws an exception if the file does not exist.
     *
     * @param filePath Path to the file
     */
    public static void deleteFile(String filePath) {
        try {
            Files.delete(Paths.get(filePath));
            logger.info("Deleted file: {}", filePath);
        } catch (NoSuchFileException e) {
            logger.warn("Attempted to delete a non-existent file: {}", filePath);
            throw new RuntimeException("File does not exist: " + filePath, e);
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", filePath, e);
            throw new RuntimeException("Could not delete file: " + filePath, e);
        }
    }

    /**
     * Silently deletes a file if it exists.
     *
     * @param filePath Path to the file
     * @return true if deleted, false if it didn't exist
     */
    public static boolean deleteFileIfExists(String filePath) {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(filePath));
            if (deleted) {
                logger.info("Deleted file: {}", filePath);
            }
            return deleted;
        } catch (IOException e) {
            logger.error("Failed to delete file if exists: {}", filePath, e);
            return false;
        }
    }

    /**
     * Checks if a file exists and is a regular file (not a directory).
     *
     * @param filePath Path to check
     * @return true if it exists and is a file
     */
    public static boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && Files.isRegularFile(path);
    }

    /**
     * Copies a file from source to target, replacing existing target.
     *
     * @param sourcePath Source file path
     * @param targetPath Target file path
     * @return Path to the copied file
     */
    public static Path copyFile(String sourcePath, String targetPath) {
        try {
            Path target = Paths.get(targetPath);
            if (target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }
            Files.copy(Paths.get(sourcePath), target, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Copied file from {} to {}", sourcePath, targetPath);
            return target;
        } catch (IOException e) {
            logger.error("Failed to copy file from {} to {}", sourcePath, targetPath, e);
            throw new RuntimeException("Could not copy file", e);
        }
    }

    /**
     * Moves a file from source to target atomically. If atomic move fails, falls back to replace.
     *
     * @param sourcePath Source file path
     * @param targetPath Target file path
     * @return Path to the moved file
     */
    public static Path moveFile(String sourcePath, String targetPath) {
        try {
            Path target = Paths.get(targetPath);
            if (target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }
            Files.move(Paths.get(sourcePath), target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            logger.info("Moved file from {} to {}", sourcePath, targetPath);
            return target;
        } catch (AtomicMoveNotSupportedException e) {
            try {
                logger.warn("Atomic move not supported. Falling back to replace existing for move from {} to {}", sourcePath, targetPath);
                return Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                logger.error("Failed fallback move file from {} to {}", sourcePath, targetPath, ex);
                throw new RuntimeException("Could not move file", ex);
            }
        } catch (IOException e) {
            logger.error("Failed to move file from {} to {}", sourcePath, targetPath, e);
            throw new RuntimeException("Could not move file", e);
        }
    }

    /**
     * Renames a file within the same directory.
     *
     * @param filePath Current file path
     * @param newName  New name (just the file name, not the path)
     * @return Path to the renamed file
     */
    public static Path renameFile(String filePath, String newName) {
        Path source = Paths.get(filePath);
        Path target = source.resolveSibling(newName);
        return moveFile(source.toString(), target.toString());
    }

    /**
     * Gets the size of a file in bytes.
     *
     * @param filePath Path to the file
     * @return Size in bytes
     */
    public static long getFileSize(String filePath) {
        try {
            return Files.size(Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Failed to get file size for: {}", filePath, e);
            throw new RuntimeException("Could not get file size for: " + filePath, e);
        }
    }

    /**
     * Gets a human-readable file size (e.g., "1.5 MB").
     *
     * @param filePath Path to the file
     * @return Formatted size string
     */
    public static String getFileSizeFormatted(String filePath) {
        long bytes = getFileSize(filePath);
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Gets fundamental metadata for a file.
     *
     * @param filePath Path to the file
     * @return A map containing size, creationTime, lastAccessTime, lastModifiedTime, and isDirectory
     */
    public static Map<String, Object> getFileMetadata(String filePath) {
        try {
            BasicFileAttributes attr = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("size", attr.size());
            metadata.put("creationTime", attr.creationTime());
            metadata.put("lastAccessTime", attr.lastAccessTime());
            metadata.put("lastModifiedTime", attr.lastModifiedTime());
            metadata.put("isDirectory", attr.isDirectory());
            return metadata;
        } catch (IOException e) {
            logger.error("Failed to read file metadata for: {}", filePath, e);
            throw new RuntimeException("Could not read metadata for: " + filePath, e);
        }
    }

    /**
     * Extracts the extension from a file path.
     *
     * @param filePath Path to the file
     * @return The extension (e.g., "txt"), or empty string if no extension
     */
    public static String getFileExtension(String filePath) {
        String name = Paths.get(filePath).getFileName().toString();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; 
        }
        return name.substring(lastIndexOf + 1);
    }
}
