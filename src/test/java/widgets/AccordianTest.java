package widgets;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.widgets.AccordianPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Accordion Tests")
public class AccordianTest extends BaseTest {

    private AccordianPage page;

    @BeforeEach
    void setup() {
        page = new AccordianPage();
    }

    @Test
    @DisplayName("Verify Section 1 behavior")
    void testSection1() {
        // Section 1 is usually expanded by default in DemoQA
        assertTrue(page.isSection1Expanded(), "Section 1 should be expanded by default");
        assertTrue(page.getSection1Text().contains("Lorem Ipsum is simply dummy text"), "Section 1 content mismatch");

        page.clickSection1();
        // Give it a moment to collapse if there's animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        assertFalse(page.isSection1Expanded(), "Section 1 should be collapsed after click");
    }

    @Test
    @DisplayName("Verify Section 2 expansion")
    void testSection2() {
        assertFalse(page.isSection2Expanded(), "Section 2 should be collapsed by default");

        page.clickSection2();
        // Give it a moment for animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        assertTrue(page.isSection2Expanded(), "Section 2 should be expanded after click");
        assertTrue(page.getSection2Text().contains("Contrary to popular belief"), "Section 2 content mismatch");
    }

    @Test
    @DisplayName("Verify Section 3 expansion")
    void testSection3() {
        assertFalse(page.isSection3Expanded(), "Section 3 should be collapsed by default");

        page.clickSection3();
        // Give it a moment for animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        assertTrue(page.isSection3Expanded(), "Section 3 should be expanded after click");
        assertTrue(page.getSection3Text().contains("It is a long established fact"), "Section 3 content mismatch");
    }
}
