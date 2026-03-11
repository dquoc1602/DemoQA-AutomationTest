package page.demoqa.elements;

import core.BasePage;
import locator.demoqa.elements.BrokenLinksLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;

import static common.Constants.DEMOQA_BROKEN_URL;

public class BrokenLinksPage extends BasePage {

    public BrokenLinksPage() {
        super();
        logger.info("Opening Broken Links page: {}", DEMOQA_BROKEN_URL);
        openSite(DEMOQA_BROKEN_URL);
    }

    public boolean isImageBroken(By locator) {
        WebElement img = findElement(locator);
        
        // Wait for image to be 'complete' AND (for potential non-broken images) have naturalWidth > 0
        // We only wait a short time because broken images will NEVER have naturalWidth > 0
        try {
            getWait(5).until(d -> {
                Object complete = executeJavaScriptReturn("return arguments[0].complete;", img);
                Object width = executeJavaScriptReturn("return arguments[0].naturalWidth;", img);
                return (Boolean) complete && (width != null);
            });
        } catch (Exception e) {
            logger.warn("Timeout waiting for image properties: {}", locator);
        }

        Object naturalWidth = executeJavaScriptReturn("return arguments[0].naturalWidth;", img);
        boolean isBroken = naturalWidth == null || (long) naturalWidth == 0;
        logger.info("Image {} is broken: {}", locator, isBroken);
        return isBroken;
    }

    public int getLinkStatusCode(By locator) {
        String href = getElementAttribute(locator, "href");
        logger.info("Checking status code for URL: {}", href);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(href).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            logger.info("URL {} responded with status {}", href, code);
            return code;
        } catch (Exception e) {
            logger.error("Error checking status code for {}", href, e);
            return -1;
        }
    }




    public void clickValidLink() {
        clickButton(BrokenLinksLocators.VALID_LINK);
    }

    public void clickBrokenLink() {
        clickButton(BrokenLinksLocators.BROKEN_LINK);
    }
}
