package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.LinksLocators;
import org.openqa.selenium.By;

import static util.Constants.DEMOQA_LINKS_URL;

public class LinksPage extends Basepage {

    public LinksPage() {
        super();
        logger.info("Opening Links page: {}", DEMOQA_LINKS_URL);
        openSite(DEMOQA_LINKS_URL);
    }

    public LinksPage clickSimpleLink() {
        logger.info("Clicking simple link (Home)");
        clickButton(LinksLocators.SIMPLE_LINK);
        return this;
    }

    public LinksPage clickDynamicLink() {
        logger.info("Clicking dynamic link (HomeXXXXX)");
        clickButton(LinksLocators.DYNAMIC_LINK);
        return this;
    }

    public LinksPage clickCreatedLink() {
        clickApiLink(LinksLocators.CREATED);
        return this;
    }

    public LinksPage clickNoContentLink() {
        clickApiLink(LinksLocators.NO_CONTENT);
        return this;
    }

    public LinksPage clickMovedLink() {
        clickApiLink(LinksLocators.MOVED);
        return this;
    }

    public LinksPage clickBadRequestLink() {
        clickApiLink(LinksLocators.BAD_REQUEST);
        return this;
    }

    public LinksPage clickUnauthorizedLink() {
        clickApiLink(LinksLocators.UNAUTHORIZED);
        return this;
    }

    public LinksPage clickForbiddenLink() {
        clickApiLink(LinksLocators.FORBIDDEN);
        return this;
    }

    public LinksPage clickNotFoundLink() {
        clickApiLink(LinksLocators.NOT_FOUND);
        return this;
    }

    private void clickApiLink(By locator) {
        logger.info("Clicking API link: {}", locator);
        clickButton(locator);
    }

    public String getResponseMessage() {
        String text = getElementText(LinksLocators.LINK_RESPONSE);
        logger.info("Response message retrieved: {}", text);
        return text;
    }

    public void switchToNewTab() {
        swithToNewWindow();
    }

    public void closeNewTabAndSwitchBack() {
        switchBackToOriginalWindow();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
