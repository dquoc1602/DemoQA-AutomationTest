package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.RadioButtonLocators;
import models.enums.RadioOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static util.Constants.DEMOQA_RADIO_BUTTON_URL;

public class RadioButtonPage extends Basepage {

    public RadioButtonPage() {
        super();
        logger.info("Opening Radio Button page: {}", DEMOQA_RADIO_BUTTON_URL);
        openSite(DEMOQA_RADIO_BUTTON_URL);
    }

    // ================= ACTION METHODS =================

    public RadioButtonPage selectYes() {
        logger.info("Selecting radio: YES");
        selectOption(RadioOption.YES);
        return this;
    }

    public RadioButtonPage selectImpressive() {
        logger.info("Selecting radio: IMPRESSIVE");
        selectOption(RadioOption.IMPRESSIVE);
        return this;
    }

    public RadioButtonPage selectByLabel(String label) {
        logger.info("Selecting radio by label: {}", label);
        RadioOption option = resolveOptionByLabel(label);
        selectOption(option);
        return this;
    }

    public RadioButtonPage attemptSelectNo(String expectedSelection) {
        logger.info("Attempting to select disabled radio: NO");
        selectOption(RadioOption.NO);
        return this;
    }

    // ================= QUERY METHODS =================

    public boolean isSelected(String label) {
        RadioOption option = resolveOptionByLabel(label);
        return isChecked(option);
    }

    public boolean isDisabled(String label) {
        RadioOption option = resolveOptionByLabel(label);
        return isDisabled(option);
    }

    public String getResultText() {
        WebElement result = findElement(RadioButtonLocators.RESULT_TEXT);
        return result.getText().trim();
    }

    public long getSelectedCount() {
        List<WebElement> radios = findElements(RadioButtonLocators.ALL_RADIOS);
        return radios.stream()
                .filter(WebElement::isSelected)
                .count();
    }

    // ================= INTERNAL ACTION =================

    private void selectOption(RadioOption option) {
        logger.debug("Resolving radio option: {}", option.label);
        WebElement radio = getRadio(option);
        WebElement label = getLabel(option);

        if (isDisabled(option)) {
            logger.warn("Radio '{}' is disabled, enforcing NO-OP click", option.label);
            scrollToWebElement(label);
            label.click();
            return;
        }

        if (radio.isSelected()) {
            logger.debug("Radio '{}' already selected, skipping click", option.label);
            return;
        }

        logger.debug("Clicking radio label: {}", option.label);
        scrollToWebElement(label);
        label.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> radio.isSelected());

        logger.debug("Radio '{}' checked state confirmed", option.label);
    }

    // ================= STATE HELPERS =================

    private boolean isChecked(RadioOption option) {
        boolean checked = getRadio(option).isSelected();
        logger.trace("Radio '{}' checked state: {}", option.label, checked);
        return checked;
    }

    private boolean isDisabled(RadioOption option) {
        boolean disabled = !getRadio(option).isEnabled();
        logger.trace("Radio '{}' disabled state: {}", option.label, disabled);
        return disabled;
    }

    private WebElement getRadio(RadioOption option) {
        logger.trace("Locating radio input for '{}'", option.label);
        return findElementPresent(By.cssSelector(
                String.format(RadioButtonLocators.RADIO_BY_ID, option.id)));
    }

    private WebElement getLabel(RadioOption option) {
        logger.trace("Locating radio label for '{}'", option.label);
        return findElement(By.cssSelector(
                String.format(RadioButtonLocators.LABEL_BY_FOR, option.id)));
    }

    private RadioOption resolveOptionByLabel(String label) {
        String normalized = label.trim();
        logger.debug("Resolving RadioOption by label: {}", normalized);

        for (RadioOption option : RadioOption.values()) {
            if (option.label.equalsIgnoreCase(normalized)) {
                return option;
            }
        }

        logger.error("Unknown radio label: {}", normalized);
        throw new IllegalArgumentException("Unknown radio label: " + normalized);
    }
}
