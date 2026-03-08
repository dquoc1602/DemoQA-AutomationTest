package common.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides static utilities for managing directories (creation, deletion, listing, searching).
 */
public class DirectoryManager {
    private static final Logger logger = LogManager.getLogger(DirectoryManager.class);

    private DirectoryManager() {
        // Prevent instantiation
    }

    /**
     * Creates a directory and all non-existent parent directories.
     *
     * @param dirPath Path to the directory
     * @return Path to the created directory
     */
    public static Path createDirectory(String dirPath) {
        try {
            Path path = Files.createDirectories(Paths.get(dirPath));
            logger.info("Created directory: {}", dirPath);
            return path;
        } catch (IOException e) {
            logger.error("Failed to create directory: {}", dirPath, e);
            throw new RuntimeException("Could not create directory: " + dirPath, e);
        }
    }

    /**
     * Checks if a directory exists and is actually a directory.
     *
     * @param dirPath Path to the directory
     * @return true if it exists and is a directory
     */
    public static boolean directoryExists(String dirPath) {
        Path path = Paths.get(dirPath);
        return Files.exists(path) && Files.isDirectory(path);
    }

    /**
     * Recursively deletes a directory and all its contents.
     *
     * @param dirPath Path to the directory
     */
    public static void deleteDirectory(String dirPath) {
        Path path = Paths.get(dirPath);
        if (!directoryExists(dirPath)) {
            throw new RuntimeException("Directory does not exist or is not a directory: " + dirPath);
        }

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            logger.info("Deleted directory recursively: {}", dirPath);
        } catch (IOException e) {
            logger.error("Failed to delete directory: {}", dirPath, e);
            throw new RuntimeException("Could not delete directory: " + dirPath, e);
        }
    }

    /**
     * Recursively deletes a directory if it exists, silently skipping if not found.
     *
     * @param dirPath Path to the directory
     * @return true if it was found and deleted
     */
    public static boolean deleteDirectoryIfExists(String dirPath) {
        if (!directoryExists(dirPath)) {
            return false;
        }
        try {
            deleteDirectory(dirPath);
            return true;
        } catch (Exception e) {
            logger.error("Failed to safely delete directory: {}", dirPath, e);
            return false;
        }
    }

    /**
     * Lists all regular files directly within a directory (non-recursive).
     *
     * @param dirPath Path to the directory
     * @return List of file paths
     */
    public static List<Path> listFiles(String dirPath) {
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
            return stream.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to list files in directory: {}", dirPath, e);
            throw new RuntimeException("Could not list files in: " + dirPath, e);
        }
    }

    /**
     * Lists all files directly within a directory matching the specified extension (non-recursive).
     *
     * @param dirPath   Path to the directory
     * @param extension Extension to filter by (e.g. "txt", "json" - no dot)
     * @return List of matching file paths
     */
    public static List<Path> listFiles(String dirPath, String extension) {
        String ext = extension.startsWith(".") ? extension : "." + extension;
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
            return stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(ext))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to list files with extension {} in directory: {}", extension, dirPath, e);
            throw new RuntimeException("Could not list files in: " + dirPath, e);
        }
    }

    /**
     * Lists all sub-directories directly within a directory (non-recursive).
     *
     * @param dirPath Path to the directory
     * @return List of directory paths
     */
    public static List<Path> listDirectories(String dirPath) {
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
            return stream.filter(Files::isDirectory).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to list sub-directories in: {}", dirPath, e);
            throw new RuntimeException("Could not list directories in: " + dirPath, e);
        }
    }

    /**
     * Lists all entries (both files and directories) directly within a directory (non-recursive).
     *
     * @param dirPath Path to the directory
     * @return List of all entry paths
     */
    public static List<Path> listAll(String dirPath) {
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to list all entries in: {}", dirPath, e);
            throw new RuntimeException("Could not list all entries in: " + dirPath, e);
        }
    }

    /**
     * Recursively searches for files matching a Glob pattern.
     * Example pattern: "*.{json,xml}" or "** /test_*.csv"
     *
     * @param dirPath     Root directory to search in
     * @param globPattern The glob pattern syntax (e.g. "*.txt")
     * @return List of matching file paths
     */
    public static List<Path> searchFiles(String dirPath, String globPattern) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + globPattern);
        List<Path> matchedFiles = new ArrayList<>();
        
        try {
            Files.walkFileTree(Paths.get(dirPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (matcher.matches(file.getFileName())) {
                        matchedFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            return matchedFiles;
        } catch (IOException e) {
            logger.error("Failed to search files with pattern {} in: {}", globPattern, dirPath, e);
            throw new RuntimeException("Could not search files", e);
        }
    }

    /**
     * Recursively searches for a specific file by exact name.
     *
     * @param dirPath  Root directory to search in
     * @param filename Exact filename with extension
     * @return List of matching file paths (in case there are multiple in different subdirs)
     */
    public static List<Path> searchFilesByName(String dirPath, String filename) {
        List<Path> matchedFiles = new ArrayList<>();
        try {
            Files.walkFileTree(Paths.get(dirPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName().toString().equals(filename)) {
                        matchedFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            return matchedFiles;
        } catch (IOException e) {
            logger.error("Failed to search files by name {} in: {}", filename, dirPath, e);
            throw new RuntimeException("Could not search files by name", e);
        }
    }

    /**
     * Gets the total size of a directory and all its contents in bytes.
     *
     * @param dirPath Path to the directory
     * @return Total size in bytes
     */
    public static long getDirectorySize(String dirPath) {
        try (Stream<Path> pathStream = Files.walk(Paths.get(dirPath))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();
        } catch (IOException e) {
            logger.error("Failed to calculate size of directory: {}", dirPath, e);
            throw new RuntimeException("Could not calculate directory size", e);
        }
    }
}
