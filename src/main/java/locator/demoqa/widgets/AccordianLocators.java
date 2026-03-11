package locator.demoqa.widgets;

import org.openqa.selenium.By;

public final class AccordianLocators {

    public static final By SECTION1_HEADING = By.xpath("//div[@class='accordion-item'][1]//button[contains(@class, 'accordion-button')]");
    public static final By SECTION1_CONTENT = By.xpath("//div[@class='accordion-item'][1]//div[contains(@class, 'accordion-collapse')]");

    public static final By SECTION2_HEADING = By.xpath("//div[@class='accordion-item'][2]//button[contains(@class, 'accordion-button')]");
    public static final By SECTION2_CONTENT = By.xpath("//div[@class='accordion-item'][2]//div[contains(@class, 'accordion-collapse')]");

    public static final By SECTION3_HEADING = By.xpath("//div[@class='accordion-item'][3]//button[contains(@class, 'accordion-button')]");
    public static final By SECTION3_CONTENT = By.xpath("//div[@class='accordion-item'][3]//div[contains(@class, 'accordion-collapse')]");

    private AccordianLocators() {
    }
}
