package Elements;

import core.BaseTest;
import locators.demoQA.elements.BrokenLinksLocators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.BrokenLinksPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Broken Links and Images Tests")
public class BrokenLinksTest extends BaseTest {

    private BrokenLinksPage page;

    @BeforeEach
    void setup() {
        page = new BrokenLinksPage();
    }

    @Test
    @DisplayName("Verify valid image is displayed correctly")
    void testValidImage() {
        assertFalse(page.isImageBroken(BrokenLinksLocators.VALID_IMAGE),
                "Standard image should NOT be broken");
    }

    @Test
    @DisplayName("Verify broken image is identified")
    void testBrokenImage() {
        assertTrue(page.isImageBroken(BrokenLinksLocators.BROKEN_IMAGE),
                "Broken image SHOULD be identified as broken (naturalWidth = 0)");
    }

    @Test
    @DisplayName("Verify valid link returns 200/300 status")
    void testValidLinkStatusCode() {
        int statusCode = page.getLinkStatusCode(BrokenLinksLocators.VALID_LINK);
        assertTrue(statusCode >= 200 && statusCode < 400,
                "Valid link should return success or redirect status code");
    }

    @Test
    @DisplayName("Verify broken link returns 500 status")
    void testBrokenLinkStatusCode() {
        int statusCode = page.getLinkStatusCode(BrokenLinksLocators.BROKEN_LINK);
        assertEquals(500, statusCode, "Broken link should return 500 status code");
    }
}
