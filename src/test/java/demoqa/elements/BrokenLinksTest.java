package demoqa.elements;

import core.BaseTest;
import locator.demoqa.elements.BrokenLinksLocators;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.BrokenLinksPage;

public class BrokenLinksTest extends BaseTest {

    private BrokenLinksPage page;

    @BeforeMethod
    void setup() {
        page = new BrokenLinksPage();
    }

    @Test(description = "Verify valid image is displayed correctly")
    void testValidImage() {
        Assert.assertFalse(page.isImageBroken(BrokenLinksLocators.VALID_IMAGE),
                "Standard image should NOT be broken");
    }

    @Test(description = "Verify broken image is identified")
    void testBrokenImage() {
        Assert.assertTrue(page.isImageBroken(BrokenLinksLocators.BROKEN_IMAGE),
                "Broken image SHOULD be identified as broken (naturalWidth = 0)");
    }

    @Test(description = "Verify valid link returns 200/300 status")
    void testValidLinkStatusCode() {
        int statusCode = page.getLinkStatusCode(BrokenLinksLocators.VALID_LINK);
        Assert.assertTrue(statusCode >= 200 && statusCode < 400,
                "Valid link should return success or redirect status code");
    }

    @Test(description = "Verify broken link returns 500 status")
    void testBrokenLinkStatusCode() {
        int statusCode = page.getLinkStatusCode(BrokenLinksLocators.BROKEN_LINK);
        Assert.assertEquals(statusCode, 500, "Broken link should return 500 status code");
    }
}
