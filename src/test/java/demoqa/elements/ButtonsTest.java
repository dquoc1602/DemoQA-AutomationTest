package demoqa.elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.ButtonsPage;

public class ButtonsTest extends BaseTest {

    private ButtonsPage buttonsPage;

    @BeforeMethod
    void setup() {
        buttonsPage = new ButtonsPage();
    }

    @Test(description = "Verify Double Click functionality")
    void testDoubleClick() {
        buttonsPage.doubleClick();
        Assert.assertEquals(buttonsPage.getDoubleClickMessage(), "You have done a double click",
                "Double click message mismatch");
    }

    @Test(description = "Verify Right Click functionality")
    void testRightClick() {
        buttonsPage.rightClick();
        Assert.assertEquals(buttonsPage.getRightClickMessage(), "You have done a right click",
                "Right click message mismatch");
    }

    @Test(description = "Verify Dynamic Click functionality")
    void testDynamicClick() {
        buttonsPage.dynamicClick();
        Assert.assertEquals(buttonsPage.getDynamicClickMessage(), "You have done a dynamic click",
                "Dynamic click message mismatch");
    }
}
