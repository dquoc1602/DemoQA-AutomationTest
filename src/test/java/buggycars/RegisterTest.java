package buggycars;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.buggycars.RegisterPage;

public class RegisterTest extends BaseTest {

    @Test(description = "Verify successful registration")
    public void testSuccessfulRegistration() {
        RegisterPage registerPage = new RegisterPage();

        // Data for registration
        String username = "User" + generateRandomString(5) + generateRandomNumber(3);
        String firstName = generateRandomString(6);
        String lastName = generateRandomString(8);
        String password = "User" + generateRandomPassword(10) + "@";
        String confirmPassword = password;

        registerPage.fillRegistrationForm(username, firstName, lastName, password, confirmPassword);

        Assert.assertTrue(registerPage.isRegisterButtonEnabled(),
                "Registration is successful");

        registerPage.clickRegister();

        // Verify success message
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Success message should be displayed");
    }
}
