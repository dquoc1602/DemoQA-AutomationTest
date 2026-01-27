package Elements;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.ButtonsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Buttons Page Tests")
public class ButtonsTest extends BaseTest {

    private ButtonsPage buttonsPage;

    @BeforeEach
    void setup() {
        buttonsPage = new ButtonsPage();
    }

    @Test
    @DisplayName("Verify Double Click functionality")
    void testDoubleClick() {
        buttonsPage.doubleClick();
        assertEquals("You have done a double click", buttonsPage.getDoubleClickMessage(),
                "Double click message mismatch");
    }

    @Test
    @DisplayName("Verify Right Click functionality")
    void testRightClick() {
        buttonsPage.rightClick();
        assertEquals("You have done a right click", buttonsPage.getRightClickMessage(),
                "Right click message mismatch");
    }

    @Test
    @DisplayName("Verify Dynamic Click functionality")
    void testDynamicClick() {
        buttonsPage.dynamicClick();
        assertEquals("You have done a dynamic click", buttonsPage.getDynamicClickMessage(),
                "Dynamic click message mismatch");
    }
}
