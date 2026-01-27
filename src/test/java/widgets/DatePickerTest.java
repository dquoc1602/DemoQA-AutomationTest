package widgets;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.widgets.DatePickerPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Date Picker Tests")
public class DatePickerTest extends BaseTest {

    private DatePickerPage page;

    @BeforeEach
    void setup() {
        page = new DatePickerPage();
    }

    @Test
    @DisplayName("Verify default date is today")
    void testDefaultDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        assertEquals(today, page.getSelectedDate(), "Default date should be today");
    }

    @Test
    @DisplayName("Select date using keyboard input")
    void testSelectDateByInput() {
        String targetDate = "12/25/2025";
        page.setDateByInput(targetDate);
        assertEquals(targetDate, page.getSelectedDate());
    }

    @Test
    @DisplayName("Select date using calendar dropdowns")
    void testSelectDateByCalendar() {
        page.selectDateUsingCalendar("May", "2024", "15");
        assertEquals("05/15/2024", page.getSelectedDate());
    }

    @Test
    @DisplayName("Select date and time using keyboard input")
    void testSelectDateTimeByInput() {
        String targetDateTime = "January 27, 2030 10:00 AM";
        page.setDateAndTimeByInput(targetDateTime);
        assertEquals(targetDateTime, page.getSelectedDateTime());
    }
}
