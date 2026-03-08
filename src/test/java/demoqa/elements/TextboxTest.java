package demoqa.elements;

import core.BaseTest;
import testdata.TestDataFactory;
import model.TextboxFormData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page.demoqa.elements.TextboxPage;

public class TextboxTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(TextboxTest.class);

    private TextboxPage textboxPage;
    private TextboxFormData testData;

    @BeforeMethod
    public void setUpPages() {
        logger.info("===== SETUP TEST =====");
        textboxPage = new TextboxPage();
        testData = TestDataFactory.createMinimalTextboxData();
        logger.info("TextboxPage initialized with test data: {}", testData);
    }

    @Test(description = "Verify form submission with valid data")
    public void verifyValidData() {
        logger.info("Starting test: verifyValidData with data: {}", testData);

        textboxPage.fillForm(testData)
                .clickSubmit();

        Assert.assertTrue(textboxPage.isOutputContainerVisible(), "Output container should be visible");
        Assert.assertEquals(textboxPage.getOutputName(), testData.getFullName(), "Name mismatch");
        Assert.assertEquals(textboxPage.getOutputEmail(), testData.getEmail(), "Email mismatch");

        logger.info("Test completed successfully: All validations passed");
    }

    @Test(description = "Verify form submission with invalid email format")
    public void verifyInvalidData() {
        logger.info("Starting test: verifyInvalidData");

        TextboxFormData invalidData = TestDataFactory.createInvalidEmailData();
        logger.info("Using invalid test data: {}", invalidData);

        textboxPage.fillForm(invalidData);
        textboxPage.clickSubmit();

        // When email is invalid, the output container is not shown at all natively by DemoQA
        Assert.assertFalse(textboxPage.isOutputContainerVisible(), 
                "Output container should NOT be visible when email is invalid");

        logger.info("Test completed: Invalid data scenario tested");
    }

    @Test(description = "Verify form submission with comprehensive valid data")
    public void verifyComprehensiveValidData() {
        logger.info("Starting test: verifyComprehensiveValidData");

        TextboxFormData comprehensiveData = TestDataFactory.createValidTextboxData();
        logger.info("Using comprehensive test data: {}", comprehensiveData);

        textboxPage.fillForm(comprehensiveData)
                .clickSubmit();

        Assert.assertTrue(textboxPage.isOutputContainerVisible(), "Output container should be visible");
        Assert.assertEquals(textboxPage.getOutputName(), comprehensiveData.getFullName(), "Name mismatch");
        Assert.assertEquals(textboxPage.getOutputEmail(), comprehensiveData.getEmail(), "Email mismatch");
        Assert.assertEquals(textboxPage.getOutputCurrentAddress(), comprehensiveData.getCurrentAddress(),
                "Current Address mismatch");
        Assert.assertEquals(textboxPage.getOutputPermanentAddress(), comprehensiveData.getPermanentAddress(),
                "Permanent Address mismatch");

        logger.info("Test completed successfully: Comprehensive validations passed");
    }
}
