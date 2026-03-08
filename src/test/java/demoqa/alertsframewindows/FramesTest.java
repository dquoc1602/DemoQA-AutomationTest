package demoqa.alertsframewindows;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.alertsframewindows.FramesPage;

public class FramesTest extends BaseTest {

    private FramesPage page;

    @BeforeMethod
    void setup() {
        page = new FramesPage();
    }

    @Test(description = "Verify content of Frame 1")
    void testFrame1Content() {
        page.switchToFrame1();
        Assert.assertEquals(page.getFrameText(), "This is a sample page", "Frame 1 heading mismatch");
        page.exitFrame();
    }

    @Test(description = "Verify content of Frame 2")
    void testFrame2Content() {
        page.switchToFrame2();
        Assert.assertEquals(page.getFrameText(), "This is a sample page", "Frame 2 heading mismatch");
        page.exitFrame();
    }
}
