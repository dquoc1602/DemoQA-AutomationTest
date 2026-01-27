package form;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.form.PracticeFormPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Practice Form Tests")
public class PracticeFormTest extends BaseTest {

    private PracticeFormPage form;

    @BeforeEach
    void setup() {
        form = new PracticeFormPage();
    }

    @Test
    @DisplayName("Verify successful student registration")
    void testSuccessfulRegistration() {
        form.setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@test.com")
                .selectMale()
                .setMobileNumber("1234567890")
                .setSubjects("Maths", "English")
                .selectHobbySports()
                .selectHobbyReading()
                .setCurrentAddress("123 Test Street, Automation City")
                .selectState("NCR")
                .selectCity("Delhi")
                .submit();

        assertTrue(form.isSubmissionSuccessful(), "Registration modal should be displayed");
        assertEquals("Thanks for submitting the form", form.getModalTitle(),
                "Submission success message mismatch");
    }

    @Test
    @DisplayName("Verify mandatory fields validation")
    void testMandatoryFields() {
        // Just submit without filling required fields (FirstName, LastName, Gender,
        // Mobile)
        form.submit();

        // Modal should not appear
        assertFalse(form.isSubmissionSuccessful(), "Registration modal should NOT appear with empty required fields");
    }

    private void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }
}
