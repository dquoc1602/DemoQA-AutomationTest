package demoqa.widgets;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.widgets.SliderPage;

public class SliderTest extends BaseTest {

    private SliderPage page;

    @BeforeMethod
    void setup() {
        page = new SliderPage();
    }

    @Test(description = "Verify default slider value")
    void testDefaultValue() {
        Assert.assertEquals(page.getSliderValue(), 25, "Initial slider value should be 25");
    }

    @Test(description = "Verify moving slider forward")
    void testMoveSliderForward() {
        int target = 80;
        page.moveSliderToValue(target);
        Assert.assertEquals(page.getSliderValue(), target, "Slider value mismatch after moving forward");
    }

    @Test(description = "Verify moving slider backward")
    void testMoveSliderBackward() {
        // Move forward first to have room to move backward
        page.moveSliderToValue(50);
        int target = 10;
        page.moveSliderToValue(target);
        Assert.assertEquals(page.getSliderValue(), target, "Slider value mismatch after moving backward");
    }
}
