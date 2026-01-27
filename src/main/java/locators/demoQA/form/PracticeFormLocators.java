package locators.demoQA.form;

import org.openqa.selenium.By;

public final class PracticeFormLocators {

    public static final By FIRST_NAME = By.id("firstName");
    public static final By LAST_NAME = By.id("lastName");
    public static final By EMAIL = By.id("userEmail");

    // Gender
    public static final By GENDER_MALE_RADIO = By.id("gender-radio-1");
    public static final By GENDER_FEMALE_RADIO = By.id("gender-radio-2");
    public static final By GENDER_OTHER_RADIO = By.id("gender-radio-3");

    // Labels are often used for clicking in DemoQA Practice Form
    public static final By GENDER_MALE_LABEL = By.cssSelector("label[for='gender-radio-1']");
    public static final By GENDER_FEMALE_LABEL = By.cssSelector("label[for='gender-radio-2']");
    public static final By GENDER_OTHER_LABEL = By.cssSelector("label[for='gender-radio-3']");

    public static final By MOBILE_NUMBER = By.id("userNumber");
    public static final By DATE_OF_BIRTH_INPUT = By.id("dateOfBirthInput");

    // Subjects
    public static final By SUBJECTS_INPUT = By.id("subjectsInput");

    // Hobbies
    public static final By HOBBY_SPORTS_CHECKBOX = By.id("hobbies-checkbox-1");
    public static final By HOBBY_READING_CHECKBOX = By.id("hobbies-checkbox-2");
    public static final By HOBBY_MUSIC_CHECKBOX = By.id("hobbies-checkbox-3");

    public static final By HOBBY_SPORTS_LABEL = By.cssSelector("label[for='hobbies-checkbox-1']");
    public static final By HOBBY_READING_LABEL = By.cssSelector("label[for='hobbies-checkbox-2']");
    public static final By HOBBY_MUSIC_LABEL = By.cssSelector("label[for='hobbies-checkbox-3']");

    public static final By UPLOAD_PICTURE = By.id("uploadPicture");
    public static final By CURRENT_ADDRESS = By.id("currentAddress");

    // State and City (React Select)
    public static final By STATE_DROPDOWN = By.id("state");
    public static final By STATE_INPUT = By.cssSelector("#state input");
    public static final By CITY_DROPDOWN = By.id("city");
    public static final By CITY_INPUT = By.cssSelector("#city input");

    public static final By SUBMIT_BUTTON = By.id("submit");

    // Confirmation Modal
    public static final By MODAL_CONTENT = By.className("modal-content");
    public static final By MODAL_TITLE = By.id("example-modal-sizes-title-lg");
    public static final By CLOSE_LARGE_MODAL = By.id("closeLargeModal");

    private PracticeFormLocators() {
    }
}
