package pages.demoQA.Elements;

import core.Basepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static util.Constants.DEMOQA_RADIO_BUTTON_URL;

/**
 * Lead / Principal-ready RadioButton Page Object
 * - Deterministic state verification
 * - Result-text as source of truth
 * - Disabled-safe & no-op enforced
 * - Explicit intent per method
 * - Full logging for deep debug
 */
public class RadioButtonPage extends Basepage {

    // ================= SELECTORS =================

    private static final class Selectors {

        static final By QUESTION_TEXT =
                By.cssSelector("div.mb-3");

        static final By RESULT_TEXT =
                By.cssSelector("p.mt-3 span.text-success");

        static final By ALL_RADIOS =
                By.cssSelector("input[type='radio'][name='like']");

        static final String RADIO_BY_ID =
                "input[type='radio'][id='%s']";

        static final String LABEL_BY_FOR =
                "label[for='%s']";

        private Selectors() {}
    }

    // ================= DOMAIN MODEL =================

    private enum RadioOption {
        YES("Yes", "yesRadio"),
        IMPRESSIVE("Impressive", "impressiveRadio"),
        NO("No", "noRadio");

        final String label;
        final String id;

        RadioOption(String label, String id) {
            this.label = label;
            this.id = id;
        }
    }

    // ================= CONSTRUCTOR =================

    public RadioButtonPage() {
        super();
        logger.info("Opening Radio Button page: {}", DEMOQA_RADIO_BUTTON_URL);
        openSite(DEMOQA_RADIO_BUTTON_URL);
    }

    // ================= ACTION METHODS =================

    public RadioButtonPage selectYes() {
        logger.info("Selecting radio: YES");
        selectOption(RadioOption.YES);
        verifySelectedInternal(RadioOption.YES);
        return this;
    }

    public RadioButtonPage selectImpressive() {
        logger.info("Selecting radio: IMPRESSIVE");
        selectOption(RadioOption.IMPRESSIVE);
        verifySelectedInternal(RadioOption.IMPRESSIVE);
        return this;
    }

    public RadioButtonPage selectByLabel(String label) {
        logger.info("Selecting radio by label: {}", label);
        RadioOption option = resolveOptionByLabel(label);
        selectOption(option);
        verifySelectedInternal(option);
        return this;
    }

    /**
     * Attempt to select disabled radio (NO) and enforce NO-OP behavior
     */
    public RadioButtonPage attemptSelectNoAndVerifyUnchanged(String expectedSelection) {
        logger.info("Attempting to select disabled radio: NO");
        verifyDisabled("No");

        selectOption(RadioOption.NO);

        verifyResultText(expectedSelection);
        verifyExactlyOneSelected();

        return this;
    }

    // ================= VERIFICATION METHODS =================

    public RadioButtonPage verifySelected(String label) {
        logger.info("Verifying radio selected: {}", label);
        RadioOption option = resolveOptionByLabel(label);
        verifySelectedInternal(option);
        return this;
    }

    public RadioButtonPage verifyNotSelected(String label) {
        logger.info("Verifying radio NOT selected: {}", label);
        RadioOption option = resolveOptionByLabel(label);

        verifyFalse(isChecked(option),
                "Expected radio NOT to be selected: " + label);

        return this;
    }

    public RadioButtonPage verifyDisabled(String label) {
        logger.info("Verifying radio DISABLED: {}", label);
        RadioOption option = resolveOptionByLabel(label);

        verifyTrue(isDisabled(option),
                "Expected radio to be DISABLED: " + label);

        return this;
    }

    public RadioButtonPage verifyEnabled(String label) {
        logger.info("Verifying radio ENABLED: {}", label);
        RadioOption option = resolveOptionByLabel(label);

        verifyFalse(isDisabled(option),
                "Expected radio to be ENABLED: " + label);

        return this;
    }

    public RadioButtonPage verifyResultText(String expected) {
        logger.info("Verifying result text: {}", expected);
        WebElement result = findElement(Selectors.RESULT_TEXT);

        verifyTrue(result.getText().trim().equals(expected),
                "Expected result text '" + expected + "' but found '" + result.getText() + "'");

        return this;
    }

    public RadioButtonPage verifyExactlyOneSelected() {
        logger.debug("Verifying exactly ONE radio is selected");
        List<WebElement> radios = findElements(Selectors.ALL_RADIOS);

        long checkedCount = radios.stream()
                .filter(WebElement::isSelected)
                .count();

        verifyTrue(checkedCount == 1,
                "Expected exactly one radio selected but found: " + checkedCount);

        return this;
    }

    public RadioButtonPage verifyNoSelection() {
        logger.debug("Verifying NO radio is selected");
        List<WebElement> radios = findElements(Selectors.ALL_RADIOS);

        boolean anySelected = radios.stream()
                .anyMatch(WebElement::isSelected);

        verifyFalse(anySelected,
                "Expected no radio to be selected");

        return this;
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

    // ================= INTERNAL VERIFICATION =================

    private void verifySelectedInternal(RadioOption option) {
        verifyTrue(isChecked(option),
                "Expected radio to be selected: " + option.label);

        verifyResultText(option.label);
        verifyExactlyOneSelected();
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
                String.format(Selectors.RADIO_BY_ID, option.id)));
    }

    private WebElement getLabel(RadioOption option) {
        logger.trace("Locating radio label for '{}'", option.label);
        return findElement(By.cssSelector(
                String.format(Selectors.LABEL_BY_FOR, option.id)));
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
