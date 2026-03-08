package demoqa.elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.LinksPage;

public class LinksTest extends BaseTest {

    private LinksPage linksPage;

    @BeforeMethod
    void setup() {
        linksPage = new LinksPage();
    }

    @Test(description = "Verify simple link opens Home page in new tab")
    void testSimpleLink() {
        linksPage.clickSimpleLink();
        linksPage.switchToNewTab();

        Assert.assertEquals(linksPage.getCurrentUrl(), "https://demoqa.com/",
                "New tab URL mismatch");

        linksPage.closeNewTabAndSwitchBack();
        Assert.assertTrue(linksPage.getCurrentUrl().contains("/links"),
                "Should be back on links page");
    }

    @Test(description = "Verify dynamic link opens Home page in new tab")
    void testDynamicLink() {
        linksPage.clickDynamicLink();
        linksPage.switchToNewTab();

        Assert.assertEquals(linksPage.getCurrentUrl(), "https://demoqa.com/",
                "New tab URL mismatch");

        linksPage.closeNewTabAndSwitchBack();
        Assert.assertTrue(linksPage.getCurrentUrl().contains("/links"),
                "Should be back on links page");
    }

    @Test(description = "Verify Created link API response")
    void testCreatedLink() {
        linksPage.clickCreatedLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 201 and status text Created");
    }

    @Test(description = "Verify No Content link API response")
    void testNoContentLink() {
        linksPage.clickNoContentLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 204 and status text No Content");
    }

    @Test(description = "Verify Moved link API response")
    void testMovedLink() {
        linksPage.clickMovedLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 301 and status text Moved Permanently");
    }

    @Test(description = "Verify Bad Request link API response")
    void testBadRequestLink() {
        linksPage.clickBadRequestLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 400 and status text Bad Request");
    }

    @Test(description = "Verify Unauthorized link API response")
    void testUnauthorizedLink() {
        linksPage.clickUnauthorizedLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 401 and status text Unauthorized");
    }

    @Test(description = "Verify Forbidden link API response")
    void testForbiddenLink() {
        linksPage.clickForbiddenLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 403 and status text Forbidden");
    }

    @Test(description = "Verify Not Found link API response")
    void testNotFoundLink() {
        linksPage.clickNotFoundLink();
        Assert.assertEquals(linksPage.getResponseMessage(),
                "Link has responded with staus 404 and status text Not Found");
    }
}
