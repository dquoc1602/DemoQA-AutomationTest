package pages.demoQA.widgets;

import core.Basepage;
import locators.demoQA.widgets.DatePickerLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static util.Constants.DEMOQA_DATE_PICKER_URL;

public class DatePickerPage extends Basepage {

    public DatePickerPage() {
        super();
        logger.info("Opening Date Picker page: {}", DEMOQA_DATE_PICKER_URL);
        openSite(DEMOQA_DATE_PICKER_URL);
    }

    public void setDateByInput(String date) {
        logger.info("Setting date via input: {}", date);
        WebElement input = findElement(DatePickerLocators.SELECT_DATE_INPUT);
        // Clear value by selecting all and deleting to avoid partial input issues
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(date);
        input.sendKeys(Keys.ENTER);
    }

    public void setDateAndTimeByInput(String dateTime) {
        logger.info("Setting date and time via input: {}", dateTime);
        WebElement input = findElement(DatePickerLocators.DATE_AND_TIME_INPUT);
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(dateTime);
        input.sendKeys(Keys.ENTER);
    }

    public String getSelectedDate() {
        return getElementAttribute(DatePickerLocators.SELECT_DATE_INPUT, "value");
    }

    public String getSelectedDateTime() {
        return getElementAttribute(DatePickerLocators.DATE_AND_TIME_INPUT, "value");
    }

    public void selectDateUsingCalendar(String month, String year, String day) {
        logger.info("Selecting date using calendar: {} {} {}", month, year, day);
        clickButton(DatePickerLocators.SELECT_DATE_INPUT);

        new Select(findElement(DatePickerLocators.MONTH_SELECT)).selectByVisibleText(month);
        new Select(findElement(DatePickerLocators.YEAR_SELECT)).selectByVisibleText(year);

        By dayLocator = By.xpath(String.format(DatePickerLocators.DAY_XPATH, day));
        clickButton(dayLocator);
    }
}
