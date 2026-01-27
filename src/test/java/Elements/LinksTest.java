package Elements;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.LinksPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Links Page Tests")
public class LinksTest extends BaseTest {

    private LinksPage linksPage;

    @BeforeEach
    void setup() {
        linksPage = new LinksPage();
    }

    @Test
    @DisplayName("Verify simple link opens Home page in new tab")
    void testSimpleLink() {
        linksPage.clickSimpleLink();
        linksPage.switchToNewTab();

        assertEquals("https://demoqa.com/", linksPage.getCurrentUrl(),
                "New tab URL mismatch");

        linksPage.closeNewTabAndSwitchBack();
        assertTrue(linksPage.getCurrentUrl().contains("/links"),
                "Should be back on links page");
    }

    @Test
    @DisplayName("Verify dynamic link opens Home page in new tab")
    void testDynamicLink() {
        linksPage.clickDynamicLink();
        linksPage.switchToNewTab();

        assertEquals("https://demoqa.com/", linksPage.getCurrentUrl(),
                "New tab URL mismatch");

        linksPage.closeNewTabAndSwitchBack();
        assertTrue(linksPage.getCurrentUrl().contains("/links"),
                "Should be back on links page");
    }

    @Test
    @DisplayName("Verify Created link API response")
    void testCreatedLink() {
        linksPage.clickCreatedLink();
        assertEquals("Link has responded with staus 201 and status text Created",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify No Content link API response")
    void testNoContentLink() {
        linksPage.clickNoContentLink();
        assertEquals("Link has responded with staus 204 and status text No Content",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify Moved link API response")
    void testMovedLink() {
        linksPage.clickMovedLink();
        assertEquals("Link has responded with staus 301 and status text Moved Permanently",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify Bad Request link API response")
    void testBadRequestLink() {
        linksPage.clickBadRequestLink();
        assertEquals("Link has responded with staus 400 and status text Bad Request",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify Unauthorized link API response")
    void testUnauthorizedLink() {
        linksPage.clickUnauthorizedLink();
        assertEquals("Link has responded with staus 401 and status text Unauthorized",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify Forbidden link API response")
    void testForbiddenLink() {
        linksPage.clickForbiddenLink();
        assertEquals("Link has responded with staus 403 and status text Forbidden",
                linksPage.getResponseMessage());
    }

    @Test
    @DisplayName("Verify Not Found link API response")
    void testNotFoundLink() {
        linksPage.clickNotFoundLink();
        assertEquals("Link has responded with staus 404 and status text Not Found",
                linksPage.getResponseMessage());
    }
}
