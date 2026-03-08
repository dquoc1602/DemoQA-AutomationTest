package demoqa.widgets;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.widgets.AccordianPage;

public class AccordianTest extends BaseTest {

    private AccordianPage page;

    @BeforeMethod
    void setup() {
        page = new AccordianPage();
    }

    @Test(description = "Verify Section 1 behavior")
    void testSection1() {
        // Section 1 is usually expanded by default in DemoQA
        Assert.assertTrue(page.isSection1Expanded(), "Section 1 should be expanded by default");
        Assert.assertTrue(page.getSection1Text().contains("Lorem Ipsum is simply dummy text"),
                "Section 1 content mismatch");

        page.clickSection1();
        // Give it a moment to collapse if there's animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        Assert.assertFalse(page.isSection1Expanded(), "Section 1 should be collapsed after click");
    }

    @Test(description = "Verify Section 2 expansion")
    void testSection2() {
        Assert.assertFalse(page.isSection2Expanded(), "Section 2 should be collapsed by default");

        page.clickSection2();
        // Give it a moment for animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        Assert.assertTrue(page.isSection2Expanded(), "Section 2 should be expanded after click");
        Assert.assertTrue(page.getSection2Text().contains("Contrary to popular belief"), "Section 2 content mismatch");
    }

    @Test(description = "Verify Section 3 expansion")
    void testSection3() {
        Assert.assertFalse(page.isSection3Expanded(), "Section 3 should be collapsed by default");

        page.clickSection3();
        // Give it a moment for animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        Assert.assertTrue(page.isSection3Expanded(), "Section 3 should be expanded after click");
        Assert.assertTrue(page.getSection3Text().contains("It is a long established fact"),
                "Section 3 content mismatch");
    }
}
