package ui.tests;

import core.base.BaseUiTest;
import core.driver.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.HeaderBar;
import ui.pages.HomePage;
import ui.pages.LoginPage;

@Epic("User Authentication")
@Feature("Login Functionality")
public class UI_TC03_LoginIncorrectTest extends BaseUiTest {

    @Test(description = "Test Case 3: Login User with incorrect email and password")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that user cannot login with invalid credentials and sees an error message")
    public void loginUserWithIncorrectCredentials() {
        // Initialize Pages
        // (Assuming HomePage exists in your ui.pages package)
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        HeaderBar headerBar = new HeaderBar(DriverFactory.getDriver());
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        // 1. Launch browser (Handled automatically by BaseUiTest)
        // 2. Navigate to url 'http://automationexercise.com' (Handled by BaseUiTest config)

        // 3. Verify that home page is visible successfully

        Assert.assertTrue(homePage.isHomeVisible(), "Home page title does not match!");
        // 4. Click on 'Signup / Login' button
        headerBar.clickLoginButton();

        // 5. Verify 'Login to your account' is visible
        Assert.assertTrue(loginPage.isLoginHeaderVisible(), "'Login to your account' header is not visible");

        // 6. Enter incorrect email address and password
        // 7. Click 'login' button
        // (We reuse the existing login method for steps 6 & 7)
        loginPage.login("wrong_email_" + System.currentTimeMillis() + "@test.com", "wrongPassword");

        // 8. Verify error 'Your email or password is incorrect!' is visible
        Assert.assertTrue(loginPage.isErrorMessageVisible(), "Error message 'Your email or password is incorrect!' is not visible");
    }
}
