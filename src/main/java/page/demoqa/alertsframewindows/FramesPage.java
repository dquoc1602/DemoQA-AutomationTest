package page.demoqa.alertsframewindows;

import core.BasePage;
import locator.demoqa.alertsframewindows.FramesLocators;
// import org.openqa.selenium.By;

import static common.Constants.DEMOQA_FRAMES_URL;

public class FramesPage extends BasePage {

    public FramesPage() {
        super();
        logger.info("Opening Frames page: {}", DEMOQA_FRAMES_URL);
        openSite(DEMOQA_FRAMES_URL);
    }

    public void switchToFrame1() {
        switchToFrame(FramesLocators.FRAME1);
    }

    public void switchToFrame2() {
        switchToFrame(FramesLocators.FRAME2);
    }

    public void exitFrame() {
        switchToDefaultContent();
    }

    public String getFrameText() {
        return getElementText(FramesLocators.SAMPLE_HEADING);
    }
}
