package core.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper around Selenium's {@link Select} for HTML {@code <select>} dropdowns.
 * Supports single-select and multi-select elements.
 * Reusable across web, app, and mobile projects.
 */
public class DropdownHelper extends WebElementInteraction {

    // ───────────────────── INTERNAL HELPER ─────────────────────

    private Select getSelect(By selector) {
        return new Select(findElement(selector));
    }

    private Select getSelect(WebElement element) {
        return new Select(element);
    }

    // ───────────────────── SELECT ACTIONS ─────────────────────

    /**
     * Selects an option by its visible text.
     *
     * @param selector dropdown locator
     * @param text     visible option text
     */
    protected void selectByVisibleText(By selector, String text) {
        logger.info("Selecting option '{}' by visible text in {}", text, selector);
        getSelect(selector).selectByVisibleText(text);
    }

    /**
     * Selects an option by its value attribute.
     *
     * @param selector dropdown locator
     * @param value    option value
     */
    protected void selectByValue(By selector, String value) {
        logger.info("Selecting option with value '{}' in {}", value, selector);
        getSelect(selector).selectByValue(value);
    }

    /**
     * Selects an option by its zero-based index.
     *
     * @param selector dropdown locator
     * @param index    option index
     */
    protected void selectByIndex(By selector, int index) {
        logger.info("Selecting option at index {} in {}", index, selector);
        getSelect(selector).selectByIndex(index);
    }

    // ───────────────────── DESELECT ACTIONS (multi-select) ─────────────────────

    /**
     * Deselects all selected options (multi-select only).
     *
     * @param selector dropdown locator
     */
    protected void deselectAll(By selector) {
        logger.info("Deselecting all options in {}", selector);
        getSelect(selector).deselectAll();
    }

    protected void deselectByVisibleText(By selector, String text) {
        logger.info("Deselecting option '{}' in {}", text, selector);
        getSelect(selector).deselectByVisibleText(text);
    }

    protected void deselectByValue(By selector, String value) {
        logger.info("Deselecting option with value '{}' in {}", value, selector);
        getSelect(selector).deselectByValue(value);
    }

    protected void deselectByIndex(By selector, int index) {
        logger.info("Deselecting option at index {} in {}", index, selector);
        getSelect(selector).deselectByIndex(index);
    }

    // ───────────────────── QUERY METHODS ─────────────────────

    /**
     * Returns the visible text of the currently selected option.
     *
     * @param selector dropdown locator
     * @return selected option text
     */
    protected String getSelectedOptionText(By selector) {
        return getSelect(selector).getFirstSelectedOption().getText();
    }

    /**
     * Returns the value attribute of the currently selected option.
     *
     * @param selector dropdown locator
     * @return selected option value
     */
    protected String getSelectedOptionValue(By selector) {
        return getSelect(selector).getFirstSelectedOption().getDomAttribute("value");
    }

    /**
     * Returns the visible text of all options in the dropdown.
     *
     * @param selector dropdown locator
     * @return list of option texts
     */
    protected List<String> getAllOptionTexts(By selector) {
        return getSelect(selector).getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns the visible text of all currently selected options (multi-select).
     *
     * @param selector dropdown locator
     * @return list of selected option texts
     */
    protected List<String> getAllSelectedOptionTexts(By selector) {
        return getSelect(selector).getAllSelectedOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns the total number of options in the dropdown.
     *
     * @param selector dropdown locator
     * @return option count
     */
    protected int getOptionCount(By selector) {
        return getSelect(selector).getOptions().size();
    }

    /**
     * Checks whether the dropdown supports multiple selections.
     *
     * @param selector dropdown locator
     * @return true if multi-select
     */
    protected boolean isMultipleSelect(By selector) {
        return getSelect(selector).isMultiple();
    }

    // ───────────────────── WEBELEMENT OVERLOADS ─────────────────────

    protected void selectByVisibleText(WebElement element, String text) {
        logger.info("Selecting option '{}' by visible text on WebElement", text);
        getSelect(element).selectByVisibleText(text);
    }

    protected void selectByValue(WebElement element, String value) {
        logger.info("Selecting option with value '{}' on WebElement", value);
        getSelect(element).selectByValue(value);
    }

    protected void selectByIndex(WebElement element, int index) {
        logger.info("Selecting option at index {} on WebElement", index);
        getSelect(element).selectByIndex(index);
    }

    protected String getSelectedOptionText(WebElement element) {
        return getSelect(element).getFirstSelectedOption().getText();
    }

    protected List<String> getAllOptionTexts(WebElement element) {
        return getSelect(element).getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
