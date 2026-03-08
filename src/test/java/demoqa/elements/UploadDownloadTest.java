package demoqa.elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.UploadDownloadPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadDownloadTest extends BaseTest {

    private UploadDownloadPage page;

    @BeforeMethod
    void setup() {
        page = new UploadDownloadPage();
    }

    @Test(description = "Verify download button has correct data URI")
    void testDownloadLink() {
        String href = page.getDownloadHref();
        // The provided HTML shows a base64 jpeg
        Assert.assertTrue(href.startsWith("data:image/jpeg;base64"),
                "Download link should contain base64 image data");
    }

    @Test(description = "Verify file upload functionality")
    void testFileUpload() throws IOException {
        // Create a temporary file to upload
        Path tempDir = Files.createTempDirectory("uploadTest");
        Path tempFile = tempDir.resolve("testUpload.txt");
        Files.writeString(tempFile, "Hello DemoQA Automation");

        try {
            String absolutePath = tempFile.toAbsolutePath().toString();

            page.uploadFile(absolutePath);

            String resultText = page.getUploadedFilePathText();

            // DemoQA shows C:\fakepath\filename usually
            Assert.assertTrue(resultText.contains("testUpload.txt"),
                    "Resulting path should contain the uploaded file name. Actual: " + resultText);
        } finally {
            try {
                Files.deleteIfExists(tempFile);
                Files.deleteIfExists(tempDir);
            } catch (IOException ignored) {
            }
        }
    }
}
