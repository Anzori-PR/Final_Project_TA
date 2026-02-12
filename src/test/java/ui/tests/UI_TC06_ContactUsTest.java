package ui.tests;

import core.base.BaseUiTest;
import core.driver.DriverFactory;
import core.listeners.AllureTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.ContactUsPage;
import ui.pages.HeaderBar;
import ui.pages.HomePage;
import java.io.File;

@Listeners(AllureTestListener.class)
public class UI_TC06_ContactUsTest extends BaseUiTest {

    @Test(description = "Test Case 6: Contact Us Form")
    public void contactUsFormTest() {
        HomePage home = new HomePage(DriverFactory.getDriver());
        HeaderBar header = new HeaderBar(DriverFactory.getDriver());
        ContactUsPage contact = new ContactUsPage(DriverFactory.getDriver());

        // 3. Verify home page visibility
        Assert.assertTrue(home.isHomeVisible(), "Home page should be visible");

        // 4. Click on 'Contact Us' button
        header.clickContactUs();

        // 5. Verify 'GET IN TOUCH' is visible
        Assert.assertTrue(contact.isGetInTouchVisible(), "'GET IN TOUCH' header should be visible");

        // 6. Enter name, email, subject and message
        contact.fillContactForm("User", "test@example.com", "Project Help", "Automating TC06");

        // 7. Upload file (Create a dummy file in your project root or use an existing one)
        File file = new File("src/test/resources/testfile");
        contact.uploadFile(file.getAbsolutePath());

        // 8 & 9. Click 'Submit' and Click OK button
        contact.submitForm();

        // 10. Verify success message
        Assert.assertEquals(contact.getSuccessMessage(), "Success! Your details have been submitted successfully.");

        // 11. Click 'Home' button and verify landing
        contact.clickHome();
        Assert.assertTrue(home.isHomeVisible(), "Should have landed back on home page. Current URL: "
                + DriverFactory.getDriver().getCurrentUrl());
    }
}