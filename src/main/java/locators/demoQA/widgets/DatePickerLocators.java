package locators.demoQA.widgets;

import org.openqa.selenium.By;

public final class DatePickerLocators {

    public static final By SELECT_DATE_INPUT = By.id("datePickerMonthYearInput");
    public static final By DATE_AND_TIME_INPUT = By.id("dateAndTimePickerInput");

    // Calendar Elements (for Select Date)
    public static final By MONTH_SELECT = By.className("react-datepicker__month-select");
    public static final By YEAR_SELECT = By.className("react-datepicker__year-select");
    public static final String DAY_XPATH = "//div[contains(@class, 'react-datepicker__day') and text()='%s' and not(contains(@class, 'outside-month'))]";

    // Calendar Elements (for Date and Time)
    public static final By MONTH_DROPDOWN_CONTAINER = By.className("react-datepicker__month-read-view");
    public static final By YEAR_DROPDOWN_CONTAINER = By.className("react-datepicker__year-read-view");
    public static final By TIME_LIST = By.className("react-datepicker__time-list");
    public static final String TIME_OPTION_XPATH = "//li[contains(@class, 'react-datepicker__time-list-item') and text()='%s']";

    private DatePickerLocators() {
    }
}
