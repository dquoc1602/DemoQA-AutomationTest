package pages.demoQA.form;

import core.Basepage;
import locators.demoQA.form.PracticeFormLocators;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static util.Constants.DEMOQA_FORM_URL;

public class PracticeFormPage extends Basepage {

    public PracticeFormPage() {
        super();
        logger.info("Opening Practice Form page: {}", DEMOQA_FORM_URL);
        openSite(DEMOQA_FORM_URL);
    }

    public PracticeFormPage setFirstName(String firstName) {
        enterText(PracticeFormLocators.FIRST_NAME, firstName);
        return this;
    }

    public PracticeFormPage setLastName(String lastName) {
        enterText(PracticeFormLocators.LAST_NAME, lastName);
        return this;
    }

    public PracticeFormPage setEmail(String email) {
        enterText(PracticeFormLocators.EMAIL, email);
        return this;
    }

    public PracticeFormPage selectMale() {
        clickButton(PracticeFormLocators.GENDER_MALE_LABEL);
        return this;
    }

    public PracticeFormPage selectFemale() {
        clickButton(PracticeFormLocators.GENDER_FEMALE_LABEL);
        return this;
    }

    public PracticeFormPage selectOtherGender() {
        clickButton(PracticeFormLocators.GENDER_OTHER_LABEL);
        return this;
    }

    public PracticeFormPage setMobileNumber(String number) {
        enterText(PracticeFormLocators.MOBILE_NUMBER, number);
        return this;
    }

    public PracticeFormPage setSubjects(String... subjects) {
        for (String subject : subjects) {
            WebElement input = findElement(PracticeFormLocators.SUBJECTS_INPUT);
            input.sendKeys(subject);
            input.sendKeys(Keys.ENTER);
        }
        return this;
    }

    public PracticeFormPage selectHobbySports() {
        clickButton(PracticeFormLocators.HOBBY_SPORTS_LABEL);
        return this;
    }

    public PracticeFormPage selectHobbyReading() {
        clickButton(PracticeFormLocators.HOBBY_READING_LABEL);
        return this;
    }

    public PracticeFormPage selectHobbyMusic() {
        clickButton(PracticeFormLocators.HOBBY_MUSIC_LABEL);
        return this;
    }

    public PracticeFormPage uploadPicture(String absolutePath) {
        driver.findElement(PracticeFormLocators.UPLOAD_PICTURE).sendKeys(absolutePath);
        return this;
    }

    public PracticeFormPage setCurrentAddress(String address) {
        enterText(PracticeFormLocators.CURRENT_ADDRESS, address);
        return this;
    }

    public PracticeFormPage selectState(String state) {
        scrollToElement(PracticeFormLocators.STATE_DROPDOWN);
        WebElement input = findElement(PracticeFormLocators.STATE_INPUT);
        input.sendKeys(state);
        input.sendKeys(Keys.ENTER);
        return this;
    }

    public PracticeFormPage selectCity(String city) {
        scrollToElement(PracticeFormLocators.CITY_DROPDOWN);
        WebElement input = findElement(PracticeFormLocators.CITY_INPUT);
        input.sendKeys(city);
        input.sendKeys(Keys.ENTER);
        return this;
    }

    public void submit() {
        scrollToElement(PracticeFormLocators.SUBMIT_BUTTON);
        // Sometimes the submit button is blocked by ads, use JS click as fallback or
        // primary
        executeJavaScript("arguments[0].click();", findElementPresent(PracticeFormLocators.SUBMIT_BUTTON));
    }

    public boolean isSubmissionSuccessful() {
        try {
            return findElement(PracticeFormLocators.MODAL_CONTENT).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getModalTitle() {
        return getElementText(PracticeFormLocators.MODAL_TITLE);
    }
}
