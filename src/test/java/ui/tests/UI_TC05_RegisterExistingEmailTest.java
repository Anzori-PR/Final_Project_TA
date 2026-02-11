package ui.tests;

import core.base.BaseUiTest;
import core.config.ConfigReader;
import core.driver.DriverFactory;
import core.listeners.AllureTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.AuthPage;
import ui.pages.HomePage;

@Listeners(AllureTestListener.class)
public class UI_TC05_RegisterExistingEmailTest extends BaseUiTest {

    @Test(description = "TC05: Register User with existing email")
    public void registerWithExistingEmail() {
        HomePage home = new HomePage(DriverFactory.getDriver());
        Assert.assertTrue(home.isHomeVisible(), "Home page should be visible");

        home.clickSignupLogin();

        AuthPage auth = new AuthPage(DriverFactory.getDriver());
        Assert.assertTrue(auth.isNewUserSignupVisible(), "'New User Signup!' should be visible");

        auth.signup("ExistingUser", ConfigReader.get("testUserEmail"));

        Assert.assertTrue(auth.isExistingEmailErrorVisible(),
                "Error 'Email Address already exist!' should be visible");
    }
}
