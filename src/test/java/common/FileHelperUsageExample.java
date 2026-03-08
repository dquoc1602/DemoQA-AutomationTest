package common;

import common.file.FileHelper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Example describing how to use the comprehensive FileHelper in test automation.
 */
public class FileHelperUsageExample {

    private final String testDirPath = Paths.get(Constants.PROJECT_ROOT_PATH, "target", "test-files").toString();
    private final String txtFilePath = Paths.get(testDirPath, "sample.txt").toString();
    private final String jsonFilePath = Paths.get(testDirPath, "data.json").toString();
    private final String csvFilePath = Paths.get(testDirPath, "data.csv").toString();

    @BeforeClass
    public void setUp() {
        // Clean up any previous run
        FileHelper.deleteDirectoryIfExists(testDirPath);
        FileHelper.createDirectory(testDirPath);
    }

    @AfterClass
    public void tearDown() {
        // Clean up test directory
        FileHelper.deleteDirectoryIfExists(testDirPath);
    }

    @Test
    public void testTextFileOperations() {
        // Create and write
        FileHelper.writeText(txtFilePath, "Line 1");
        FileHelper.appendText(txtFilePath, "\nLine 2");

        // Read and verify
        List<String> lines = FileHelper.readLines(txtFilePath);
        Assert.assertEquals(lines.size(), 2);
        Assert.assertEquals(lines.get(0), "Line 1");
        Assert.assertEquals(lines.get(1), "Line 2");

        // Metadata
        long size = FileHelper.getFileSize(txtFilePath);
        Assert.assertTrue(size > 0, "File size should be greater than 0");

        // Compare
        String txtCopyPath = Paths.get(testDirPath, "sample_copy.txt").toString();
        FileHelper.copyFile(txtFilePath, txtCopyPath);
        Assert.assertTrue(FileHelper.compareFileContents(txtFilePath, txtCopyPath));
    }

    @Test
    public void testJsonFileOperations() {
        // Create JSON Object
        JSONObject json = new JSONObject();
        json.put("name", "Automation Test");
        json.put("status", "Running");

        // Write
        FileHelper.writeJson(jsonFilePath, json);
        Assert.assertTrue(FileHelper.validateJsonFormat(jsonFilePath));

        // Read and verify
        JSONObject readJson = FileHelper.readJson(jsonFilePath);
        Assert.assertEquals(readJson.getString("name"), "Automation Test");
        Assert.assertEquals(readJson.getString("status"), "Running");
    }

    @Test
    public void testCsvFileOperations() {
        // Data
        String[] headers = {"ID", "Name", "Role"};
        List<List<String>> records = Arrays.asList(
                Arrays.asList("1", "Alice", "Admin"),
                Arrays.asList("2", "Bob", "User")
        );

        // Write
        FileHelper.writeCsv(csvFilePath, headers, records);

        // Read and verify
        List<Map<String, String>> readRecords = FileHelper.readCsv(csvFilePath);
        Assert.assertEquals(readRecords.size(), 2);
        Assert.assertEquals(readRecords.get(0).get("Name"), "Alice");
        Assert.assertEquals(readRecords.get(1).get("Role"), "User");
    }

    @Test
    public void testWaitAndDirectoryOperations() {
        String downloadFilePath = Paths.get(testDirPath, "report.pdf").toString();
        
        // Simulate a background download process
        new Thread(() -> {
            try {
                Thread.sleep(1000); // 1 second delay
                FileHelper.createFile(downloadFilePath, "PDF Content");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Wait for file
        boolean appeared = FileHelper.waitForFileToAppear(testDirPath, "report.pdf", 5);
        Assert.assertTrue(appeared, "File should have appeared within 5 seconds");

        // List files in directory
        List<Path> files = FileHelper.listFiles(testDirPath);
        Assert.assertTrue(files.size() > 0, "Directory should not be empty");
    }
}
