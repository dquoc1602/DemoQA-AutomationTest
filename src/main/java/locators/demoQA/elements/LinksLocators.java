package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class LinksLocators {

    // Tab links
    public static final By SIMPLE_LINK = By.id("simpleLink");
    public static final By DYNAMIC_LINK = By.id("dynamicLink");

    // API links
    public static final By CREATED = By.id("created");
    public static final By NO_CONTENT = By.id("no-content");
    public static final By MOVED = By.id("moved");
    public static final By BAD_REQUEST = By.id("bad-request");
    public static final By UNAUTHORIZED = By.id("unauthorized");
    public static final By FORBIDDEN = By.id("forbidden");
    public static final By NOT_FOUND = By.id("invalid-url");

    // Output
    public static final By LINK_RESPONSE = By.id("linkResponse");

    private LinksLocators() {
    }
}
