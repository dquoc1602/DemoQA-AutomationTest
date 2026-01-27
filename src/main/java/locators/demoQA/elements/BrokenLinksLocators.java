package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class BrokenLinksLocators {

    public static final By VALID_IMAGE = By.xpath("//p[text()='Valid image']/following-sibling::img[1]");
    public static final By BROKEN_IMAGE = By.xpath("//p[text()='Broken image']/following-sibling::img[1]");

    public static final By VALID_LINK = By.xpath("//a[text()='Click Here for Valid Link']");
    public static final By BROKEN_LINK = By.xpath("//a[text()='Click Here for Broken Link']");

    private BrokenLinksLocators() {
    }
}
