package ui.tests;

import core.base.BaseUiTest;
import core.driver.DriverFactory;
import core.utils.TestData;
import core.utils.WaitUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.*;

@Epic("UI Tests")
@Feature("Order Placement")
public class UI_TC16_PlaceOrderTest extends BaseUiTest {

    @Test(description = "Test Case 16: Place Order: Login before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register a new user, then verify they can log in and successfully place an order.")
    public void testPlaceOrderLoginBeforeCheckout() {
        // 1. Initialize Page Objects
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        HeaderBar headerBar = new HeaderBar(DriverFactory.getDriver());
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        SignupAccountInfoPage signupPage = new SignupAccountInfoPage(DriverFactory.getDriver());
        AccountCreatedPage accountCreatedPage = new AccountCreatedPage(DriverFactory.getDriver()); // Added
        ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        AccountDeletedPage accountDeletedPage = new AccountDeletedPage(DriverFactory.getDriver());

        String uniqueEmail = TestData.uniqueEmail();
        String password = "Test@12345";
        String username = "Ani Test";

        Assert.assertTrue(homePage.isHomeVisible(), "Home page is not visible!");

        // --- PRE-REQUISITE: Register User ---
        headerBar.clickLoginButton();
        loginPage.signup(username, uniqueEmail);
        signupPage.fillAccountAndAddressDetails(password);
        signupPage.clickCreateAccount();


        // ✅ Strong wait: URL or header text
        WaitUtils.waitForUrlContains(DriverFactory.getDriver(), "account_created", 15);

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("account_created"),
                "Did not navigate to account_created page. Current URL: " + DriverFactory.getDriver().getCurrentUrl());


        // Sometimes Continue is blocked or doesn't redirect. Go home and verify session.
        DriverFactory.getDriver().get("https://automationexercise.com/");

        Assert.assertTrue(headerBar.isLoggedInAsVisible(),
                "After account creation, user is not logged in (Logged in as not visible) even on home page.");

        // FIX: Handle "Account Created" page before logging out
        Assert.assertTrue(accountCreatedPage.isAccountCreatedVisible(), "Account Created page not visible.");
        accountCreatedPage.clickContinue();

        // ✅ Wait for home/header to be ready and logged-in state visible
        Assert.assertTrue(headerBar.isLoggedInAsVisible(), "After Continue, user is not logged in (Logged in as not visible).");

        // NOW we are on the home page as a logged-in user, so we can logout
        headerBar.clickLogout();

        // 4 & 5. Official TC Step: Login
        headerBar.clickLoginButton();
        loginPage.login(uniqueEmail, password);

        // 6. Verify Logged in as username
        Assert.assertTrue(headerBar.isLoggedInAsVisible(), "'Logged in as' is not visible.");

        // 7 & 8. Add products to cart and click Cart
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        if (!DriverFactory.getDriver().getCurrentUrl().contains("view_cart")) {
            DriverFactory.getDriver().get("https://automationexercise.com/view_cart");
        }

        // 9. Verify cart page
        WaitUtils.waitForUrlContains(DriverFactory.getDriver(), "view_cart", 10);
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("view_cart"), "Cart page not displayed.");

        // 10 & 11. Checkout and verify address
        cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Address Details not visible.");

        // 12. Place Order
        checkoutPage.enterCommentAndPlaceOrder("Final Project Order - Self Sufficient Test");

        // 13 & 14. Payment
        checkoutPage.enterPaymentDetails("Ani", "4111222233334444", "123", "01", "2028");

        // 15. Verify Success Message
        String successMsg = checkoutPage.getSuccessMessageText();
        Assert.assertTrue(successMsg.toLowerCase().contains("placed") || successMsg.toLowerCase().contains("confirmed"),
                "Order success message not found. Actual: " + successMsg);

        // 16 & 17. Delete Account
        headerBar.clickDeleteAccount();
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "ACCOUNT DELETED! title not visible.");
        accountDeletedPage.clickContinue();
    }
}