package widgets;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.widgets.SliderPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Slider Tests")
public class SliderTest extends BaseTest {

    private SliderPage page;

    @BeforeEach
    void setup() {
        page = new SliderPage();
    }

    @Test
    @DisplayName("Verify default slider value")
    void testDefaultValue() {
        assertEquals(25, page.getSliderValue(), "Initial slider value should be 25");
    }

    @Test
    @DisplayName("Verify moving slider forward")
    void testMoveSliderForward() {
        int target = 80;
        page.moveSliderToValue(target);
        assertEquals(target, page.getSliderValue(), "Slider value mismatch after moving forward");
    }

    @Test
    @DisplayName("Verify moving slider backward")
    void testMoveSliderBackward() {
        // Move forward first to have room to move backward
        page.moveSliderToValue(50);
        int target = 10;
        page.moveSliderToValue(target);
        assertEquals(target, page.getSliderValue(), "Slider value mismatch after moving backward");
    }
}
