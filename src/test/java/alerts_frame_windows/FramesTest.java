package alerts_frame_windows;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.alerts_frame_windows.FramesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Frames Tests")
public class FramesTest extends BaseTest {

    private FramesPage page;

    @BeforeEach
    void setup() {
        page = new FramesPage();
    }

    @Test
    @DisplayName("Verify content of Frame 1")
    void testFrame1Content() {
        page.switchToFrame1();
        assertEquals("This is a sample page", page.getFrameText(), "Frame 1 heading mismatch");
        page.exitFrame();
    }

    @Test
    @DisplayName("Verify content of Frame 2")
    void testFrame2Content() {
        page.switchToFrame2();
        assertEquals("This is a sample page", page.getFrameText(), "Frame 2 heading mismatch");
        page.exitFrame();
    }
}
