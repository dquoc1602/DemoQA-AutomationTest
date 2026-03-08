package demoqa.widgets;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.widgets.ProgressBarPage;

public class ProgressBarTest extends BaseTest {

    private ProgressBarPage page;

    @BeforeMethod
    void setup() {
        page = new ProgressBarPage();
    }

    @Test(description = "Verify progress bar reaches 100%")
    void testProgressBarComplete() {
        page.clickStartStop();
        page.waitForComplete();

        Assert.assertEquals(page.getProgressValue(), 100, "Progress bar should be at 100%");
        Assert.assertTrue(page.isResetButtonVisible(), "Reset button should be visible after completion");
    }

    @Test(description = "Verify progress bar can be stopped and resumed")
    void testProgressStartStop() {
        page.clickStartStop();

        // Wait until it reaches at least 20%
        page.waitForProgress(20);
        page.clickStartStop(); // Stop

        int stoppedValue = page.getProgressValue();
        Assert.assertTrue(stoppedValue >= 20 && stoppedValue < 100, "Progress should be stopped mid-way");

        // Wait a bit to ensure it actually stopped
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Assert.assertEquals(page.getProgressValue(), stoppedValue, "Progress should not increase while stopped");

        page.clickStartStop(); // Resume
        page.waitForProgress(stoppedValue + 10);
        Assert.assertTrue(page.getProgressValue() > stoppedValue, "Progress should resume from stopped point");
    }

    @Test(description = "Verify progress bar reset functionality")
    void testProgressBarReset() {
        page.clickStartStop();
        page.waitForComplete();
        page.clickReset();

        Assert.assertEquals(page.getProgressValue(), 0, "Progress bar should reset to 0%");
    }
}
