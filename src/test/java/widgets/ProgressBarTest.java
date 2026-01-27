package widgets;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.widgets.ProgressBarPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Progress Bar Tests")
public class ProgressBarTest extends BaseTest {

    private ProgressBarPage page;

    @BeforeEach
    void setup() {
        page = new ProgressBarPage();
    }

    @Test
    @DisplayName("Verify progress bar reaches 100%")
    void testProgressBarComplete() {
        page.clickStartStop();
        page.waitForComplete();

        assertEquals(100, page.getProgressValue(), "Progress bar should be at 100%");
        assertTrue(page.isResetButtonVisible(), "Reset button should be visible after completion");
    }

    @Test
    @DisplayName("Verify progress bar can be stopped and resumed")
    void testProgressStartStop() {
        page.clickStartStop();

        // Wait until it reaches at least 20%
        page.waitForProgress(20);
        page.clickStartStop(); // Stop

        int stoppedValue = page.getProgressValue();
        assertTrue(stoppedValue >= 20 && stoppedValue < 100, "Progress should be stopped mid-way");

        // Wait a bit to ensure it actually stopped
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        assertEquals(stoppedValue, page.getProgressValue(), "Progress should not increase while stopped");

        page.clickStartStop(); // Resume
        page.waitForProgress(stoppedValue + 10);
        assertTrue(page.getProgressValue() > stoppedValue, "Progress should resume from stopped point");
    }

    @Test
    @DisplayName("Verify progress bar reset functionality")
    void testProgressBarReset() {
        page.clickStartStop();
        page.waitForComplete();
        page.clickReset();

        assertEquals(0, page.getProgressValue(), "Progress bar should reset to 0%");
    }
}
