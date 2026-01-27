package Elements;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pages.demoQA.Elements.UploadDownloadPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Upload and Download Tests")
public class UploadDownloadTest extends BaseTest {

    private UploadDownloadPage page;

    @BeforeEach
    void setup() {
        page = new UploadDownloadPage();
    }

    @Test
    @DisplayName("Verify download button has correct data URI")
    void testDownloadLink() {
        String href = page.getDownloadHref();
        // The provided HTML shows a base64 jpeg
        assertTrue(href.startsWith("data:image/jpeg;base64"),
                "Download link should contain base64 image data");
    }

    @Test
    @DisplayName("Verify file upload functionality")
    void testFileUpload(@TempDir Path tempDir) throws IOException {
        // Create a temporary file to upload
        Path tempFile = tempDir.resolve("testUpload.txt");
        Files.writeString(tempFile, "Hello DemoQA Automation");

        String absolutePath = tempFile.toAbsolutePath().toString();

        page.uploadFile(absolutePath);

        String resultText = page.getUploadedFilePathText();

        // DemoQA shows C:\fakepath\filename usually
        assertTrue(resultText.contains("testUpload.txt"),
                "Resulting path should contain the uploaded file name. Actual: " + resultText);
    }
}
