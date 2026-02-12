package ui.tests;

import core.base.BaseUiTest;
import core.driver.DriverFactory;
import core.listeners.AllureTestListener;
import core.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ui.pages.*;

@Listeners(AllureTestListener.class)
public class UI_TC01_RegisterUserTest extends BaseUiTest {

    @Test(description = "TC01: Register User")
    public void registerUser() {
        String password = "Test@12345";


        // 1-3 Launch + Navigate + Verify home
        HomePage home = new HomePage(DriverFactory.getDriver());
        Assert.assertTrue(home.isHomeVisible(), "Home page should be visible successfully");

        // 4 Click Signup/Login
        home.clickSignupLogin();

        // 5 Verify 'New User Signup!' visible
        AuthPage auth = new AuthPage(DriverFactory.getDriver());
        Assert.assertTrue(auth.isNewUserSignupVisible(), "'New User Signup!' should be visible");

        // 6-7 Enter name/email + click Signup
        String name = "Test";
        String email = TestData.uniqueEmail();
        auth.signup(name, email);

        // After clicking signup, confirm URL moved to /signup
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("/signup"),
                "After Signup button, URL should contain /signup");

        Assert.assertFalse(auth.isExistingEmailErrorVisible(),
                "Signup failed because email already exists. Email used: " + email);


        // 8 Verify ENTER ACCOUNT INFORMATION visible
        SignupAccountInfoPage accountInfo = new SignupAccountInfoPage(DriverFactory.getDriver());
        accountInfo.waitForAccountInfoForm();

        Assert.assertTrue(accountInfo.isAccountInfoFormVisible(),
                "Account Information form (password field) should be visible");


        // 9-12 Fill details
        accountInfo.fillAccountAndAddressDetails(password);

        // 13 Click Create Account
        accountInfo.clickCreateAccount();

        System.out.println("After Create Account URL: " + DriverFactory.getDriver().getCurrentUrl());
        System.out.println("After Create Account title: " + DriverFactory.getDriver().getTitle());


        // 14 Verify ACCOUNT CREATED
        AccountCreatedPage created = new AccountCreatedPage(DriverFactory.getDriver());
        created.waitForAccountCreated();
        Assert.assertTrue(created.isAccountCreatedVisible(), "'ACCOUNT CREATED!' should be visible");


        // 15 Click Continue
        created.clickContinue();

        // 16 Verify Logged in as username
        HeaderBar header = new HeaderBar(DriverFactory.getDriver());
        Assert.assertTrue(header.isLoggedInAsVisible(), "'Logged in as username' should be visible");

        // 17 Click Delete Account
        header.clickDeleteAccount();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("delete_account"),
                "After clicking Delete Account, URL should contain delete_account");


        System.out.println("URL after delete: " + DriverFactory.getDriver().getCurrentUrl());


        // 18 Verify ACCOUNT DELETED + Continue
        AccountDeletedPage deleted = new AccountDeletedPage(DriverFactory.getDriver());
        deleted.waitForAccountDeleted();

        Assert.assertTrue(deleted.isAccountDeletedVisible(), "'ACCOUNT DELETED!' should be visible");

        deleted.clickContinue();
    }
}
