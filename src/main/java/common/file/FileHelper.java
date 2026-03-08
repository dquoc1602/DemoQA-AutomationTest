package common.file;

import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Facade class for all File Operations.
 * Use this class as the single entry point for file handling in tests.
 * Delegates calls to specific managers (FileManager, DirectoryManager, FileReaderManager, FileWriterManager, FileTestUtils).
 */
public class FileHelper {

    private FileHelper() {
        // Prevent instantiation
    }

    // ==========================================
    // FILE MANAGEMENT (Delegates to FileManager)
    // ==========================================

    public static Path createFile(String filePath) {
        return FileManager.createFile(filePath);
    }

    public static Path createFile(String filePath, String content) {
        return FileManager.createFile(filePath, content);
    }

    public static void deleteFile(String filePath) {
        FileManager.deleteFile(filePath);
    }

    public static boolean deleteFileIfExists(String filePath) {
        return FileManager.deleteFileIfExists(filePath);
    }

    public static boolean fileExists(String filePath) {
        return FileManager.fileExists(filePath);
    }

    public static Path copyFile(String sourcePath, String targetPath) {
        return FileManager.copyFile(sourcePath, targetPath);
    }

    public static Path moveFile(String sourcePath, String targetPath) {
        return FileManager.moveFile(sourcePath, targetPath);
    }

    public static Path renameFile(String filePath, String newName) {
        return FileManager.renameFile(filePath, newName);
    }

    public static long getFileSize(String filePath) {
        return FileManager.getFileSize(filePath);
    }

    public static String getFileSizeFormatted(String filePath) {
        return FileManager.getFileSizeFormatted(filePath);
    }

    public static Map<String, Object> getFileMetadata(String filePath) {
        return FileManager.getFileMetadata(filePath);
    }

    public static String getFileExtension(String filePath) {
        return FileManager.getFileExtension(filePath);
    }

    // ====================================================
    // DIRECTORY MANAGEMENT (Delegates to DirectoryManager)
    // ====================================================

    public static Path createDirectory(String dirPath) {
        return DirectoryManager.createDirectory(dirPath);
    }

    public static boolean directoryExists(String dirPath) {
        return DirectoryManager.directoryExists(dirPath);
    }

    public static void deleteDirectory(String dirPath) {
        DirectoryManager.deleteDirectory(dirPath);
    }

    public static boolean deleteDirectoryIfExists(String dirPath) {
        return DirectoryManager.deleteDirectoryIfExists(dirPath);
    }

    public static List<Path> listFiles(String dirPath) {
        return DirectoryManager.listFiles(dirPath);
    }

    public static List<Path> listFiles(String dirPath, String extension) {
        return DirectoryManager.listFiles(dirPath, extension);
    }

    public static List<Path> listDirectories(String dirPath) {
        return DirectoryManager.listDirectories(dirPath);
    }

    public static List<Path> listAll(String dirPath) {
        return DirectoryManager.listAll(dirPath);
    }

    public static List<Path> searchFiles(String dirPath, String globPattern) {
        return DirectoryManager.searchFiles(dirPath, globPattern);
    }

    public static List<Path> searchFilesByName(String dirPath, String filename) {
        return DirectoryManager.searchFilesByName(dirPath, filename);
    }

    public static long getDirectorySize(String dirPath) {
        return DirectoryManager.getDirectorySize(dirPath);
    }

    // ==========================================
    // FILE READING (Delegates to FileReaderManager)
    // ==========================================

    public static String readText(String filePath) {
        return FileReaderManager.readText(filePath);
    }

    public static String readText(String filePath, Charset charset) {
        return FileReaderManager.readText(filePath, charset);
    }

    public static List<String> readLines(String filePath) {
        return FileReaderManager.readLines(filePath);
    }

    public static JSONObject readJson(String filePath) {
        return FileReaderManager.readJson(filePath);
    }

    public static JSONArray readJsonArray(String filePath) {
        return FileReaderManager.readJsonArray(filePath);
    }

    public static List<Map<String, String>> readCsv(String filePath) {
        return FileReaderManager.readCsv(filePath);
    }

    public static List<Map<String, String>> readCsv(String filePath, char delimiter) {
        return FileReaderManager.readCsv(filePath, delimiter);
    }

    public static List<CSVRecord> readCsvAsRecords(String filePath) {
        return FileReaderManager.readCsvAsRecords(filePath);
    }

    public static Document readXml(String filePath) {
        return FileReaderManager.readXml(filePath);
    }

    public static Map<String, Object> readXmlAsMap(String filePath) {
        return FileReaderManager.readXmlAsMap(filePath);
    }

    // ==========================================
    // FILE WRITING (Delegates to FileWriterManager)
    // ==========================================

    public static void writeText(String filePath, String content) {
        FileWriterManager.writeText(filePath, content);
    }

    public static void writeText(String filePath, String content, Charset charset) {
        FileWriterManager.writeText(filePath, content, charset);
    }

    public static void appendText(String filePath, String content) {
        FileWriterManager.appendText(filePath, content);
    }

    public static void writeLines(String filePath, List<String> lines) {
        FileWriterManager.writeLines(filePath, lines);
    }

    public static void writeJson(String filePath, JSONObject jsonObject) {
        FileWriterManager.writeJson(filePath, jsonObject);
    }

    public static void writeJson(String filePath, JSONObject jsonObject, int indent) {
        FileWriterManager.writeJson(filePath, jsonObject, indent);
    }

    public static void writeCsv(String filePath, String[] headers, List<List<String>> records) {
        FileWriterManager.writeCsv(filePath, headers, records);
    }

    public static void writeXml(String filePath, Document document) {
        FileWriterManager.writeXml(filePath, document);
    }

    // ==========================================
    // TEST UTILITIES (Delegates to FileTestUtils)
    // ==========================================

    public static boolean waitForFileToAppear(String dirPath, String filename, long timeoutSec) {
        return FileTestUtils.waitForFileToAppear(dirPath, filename, timeoutSec);
    }

    public static Path waitForFileWithExtensionToAppear(String dirPath, String extension, long timeoutSec) {
        return FileTestUtils.waitForFileWithExtensionToAppear(dirPath, extension, timeoutSec);
    }

    public static boolean waitForFileStable(String filePath, long stableDurationMs, long timeoutSec) {
        return FileTestUtils.waitForFileStable(filePath, stableDurationMs, timeoutSec);
    }

    public static boolean compareFileContents(String filePath1, String filePath2) {
        return FileTestUtils.compareFileContents(filePath1, filePath2);
    }

    public static boolean compareTextContents(String filePath1, String filePath2) {
        return FileTestUtils.compareTextContents(filePath1, filePath2);
    }

    public static List<String> getTextDifferences(String filePath1, String filePath2) {
        return FileTestUtils.getTextDifferences(filePath1, filePath2);
    }

    public static boolean validateJsonFormat(String filePath) {
        return FileTestUtils.validateJsonFormat(filePath);
    }

    public static boolean validateXmlFormat(String filePath) {
        return FileTestUtils.validateXmlFormat(filePath);
    }

    public static boolean isFileEmpty(String filePath) {
        return FileTestUtils.isFileEmpty(filePath);
    }

    public static boolean containsText(String filePath, String searchText) {
        return FileTestUtils.containsText(filePath, searchText);
    }
}
