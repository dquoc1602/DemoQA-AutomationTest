package demoqa.widgets;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.widgets.DatePickerPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerTest extends BaseTest {

    private DatePickerPage page;

    @BeforeMethod
    void setup() {
        page = new DatePickerPage();
    }

    @Test(description = "Verify default date is today")
    void testDefaultDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Assert.assertEquals(page.getSelectedDate(), today, "Default date should be today");
    }

    @Test(description = "Select date using keyboard input")
    void testSelectDateByInput() {
        String targetDate = "12/25/2025";
        page.setDateByInput(targetDate);
        Assert.assertEquals(page.getSelectedDate(), targetDate);
    }

    @Test(description = "Select date using calendar dropdowns")
    void testSelectDateByCalendar() {
        page.selectDateUsingCalendar("May", "2024", "15");
        Assert.assertEquals(page.getSelectedDate(), "05/15/2024");
    }

    @Test(description = "Select date and time using keyboard input")
    void testSelectDateTimeByInput() {
        String targetDateTime = "January 27, 2030 10:00 AM";
        page.setDateAndTimeByInput(targetDateTime);
        Assert.assertEquals(page.getSelectedDateTime(), targetDateTime);
    }
}
