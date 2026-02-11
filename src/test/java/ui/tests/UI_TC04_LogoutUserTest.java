package ui.tests;

import core.base.BaseUiTest;
import core.config.ConfigReader;
import core.driver.DriverFactory;
import core.listeners.AllureTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.AuthPage;
import ui.pages.HeaderBar;
import ui.pages.HomePage;

@Listeners(AllureTestListener.class)
public class UI_TC04_LogoutUserTest extends BaseUiTest {

    @Test(description = "TC02: Logout User")
    public void logoutUser() {
        HomePage home = new HomePage(DriverFactory.getDriver());
        Assert.assertTrue(home.isHomeVisible(), "Home page should be visible");

        home.clickSignupLogin();

        AuthPage auth = new AuthPage(DriverFactory.getDriver());
        Assert.assertTrue(auth.isLoginToAccountVisible(), "'Login to your account' should be visible");

        auth.login(ConfigReader.get("testUserEmail"), ConfigReader.get("testUserPassword"));

        HeaderBar header = new HeaderBar(DriverFactory.getDriver());
        Assert.assertTrue(header.isLoggedInAsVisible(), "'Logged in as username' should be visible");

        header.clickLogout();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("/login"),
                "User should be navigated to login page after logout");
    }
}
