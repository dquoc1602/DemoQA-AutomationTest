package page.demoqa.widgets;

import core.BasePage;
import locator.demoqa.widgets.AccordianLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static common.Constants.DEMOQA_ACCORDION_URL;

public class AccordianPage extends BasePage {

    public AccordianPage() {
        super();
        logger.info("Opening Accordion page: {}", DEMOQA_ACCORDION_URL);
        openSite(DEMOQA_ACCORDION_URL);
    }

    public void clickSection1() {
        logger.info("Clicking Section 1 Heading");
        clickButton(AccordianLocators.SECTION1_HEADING);
    }

    public void clickSection2() {
        logger.info("Clicking Section 2 Heading");
        clickButton(AccordianLocators.SECTION2_HEADING);
    }

    public void clickSection3() {
        logger.info("Clicking Section 3 Heading");
        clickButton(AccordianLocators.SECTION3_HEADING);
    }

    public boolean isSection1Expanded() {
        return isSectionExpanded(AccordianLocators.SECTION1_CONTENT);
    }

    public boolean isSection2Expanded() {
        return isSectionExpanded(AccordianLocators.SECTION2_CONTENT);
    }

    public boolean isSection3Expanded() {
        return isSectionExpanded(AccordianLocators.SECTION3_CONTENT);
    }

    public String getSection1Text() {
        return getElementText(AccordianLocators.SECTION1_CONTENT);
    }

    public String getSection2Text() {
        return getElementText(AccordianLocators.SECTION2_CONTENT);
    }

    public String getSection3Text() {
        return getElementText(AccordianLocators.SECTION3_CONTENT);
    }

    private boolean isSectionExpanded(By locator) {
        try {
            // Using presence instead of visibility because collapsed elements might be in
            // DOM but have height 0 or hidden
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
