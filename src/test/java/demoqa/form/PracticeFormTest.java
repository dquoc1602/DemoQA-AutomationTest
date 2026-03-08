package demoqa.form;

import core.BaseTest;
import common.Helper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.form.PracticeFormPage;

public class PracticeFormTest extends BaseTest {

    private PracticeFormPage form;

    @BeforeMethod
    void setup() {
        form = new PracticeFormPage();
    }

    @Test(description = "Verify successful student registration")
    void testSuccessfulRegistration() {
        String firstName = Helper.generateRandomString(5);
        String lastName = Helper.generateRandomString(7);
        String email = Helper.generateRandomString(8) + "@test.com";
        String mobile = Helper.generateRandomNumber(10);
        String address = Helper.generateRandomString(15) + ", " + Helper.generateRandomString(5);

        form.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .selectMale()
                .setMobileNumber(mobile)
                .setSubjects("Maths", "English")
                .selectHobbySports()
                .selectHobbyReading()
                .setCurrentAddress(address)
                .selectState("NCR")
                .selectCity("Delhi")
                .submit();

        Assert.assertTrue(form.isSubmissionSuccessful(), "Registration modal should be displayed");
        Assert.assertEquals(form.getModalTitle(), "Thanks for submitting the form",
                "Submission success message mismatch");
    }

    @Test(description = "Verify mandatory fields validation")
    void testMandatoryFields() {
        // Just submit without filling required fields (FirstName, LastName, Gender,
        // Mobile)
        form.submit();

        // Modal should not appear
        Assert.assertFalse(form.isSubmissionSuccessful(),
                "Registration modal should NOT appear with empty required fields");
    }
}
