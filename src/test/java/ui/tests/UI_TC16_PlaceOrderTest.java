package ui.tests;
import core.base.BaseUiTest;
import core.driver.DriverFactory;
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
    @Description("Verify that a logged-in user can successfully place an order.")
    public void testPlaceOrderLoginBeforeCheckout() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        HeaderBar headerBar = new HeaderBar(DriverFactory.getDriver());
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        AccountDeletedPage accountDeletedPage = new AccountDeletedPage(DriverFactory.getDriver());

        // 3. Verify home page
        Assert.assertTrue(homePage.isHomeVisible(), "Home page is not visible!");

        // 4 & 5. Login
        headerBar.clickLoginButton();
        loginPage.login("test123@gmail.com", "test123"); // Use valid credentials

        // 6. Verify Logged in as username
        Assert.assertTrue(headerBar.isLoggedInAsVisible(), "'Logged in as' is not visible.");

        // Step 7 & 8: Add to cart and click Cart
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        // Safety: If navigation failed because of an ad, force navigation to the cart
        if (!DriverFactory.getDriver().getCurrentUrl().contains("view_cart")) {
            DriverFactory.getDriver().get("https://automationexercise.com/view_cart");
        }

        // Step 9: Verify cart page
        WaitUtils.waitForUrlContains(DriverFactory.getDriver(), "view_cart", 10);
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("view_cart"), "Failed to navigate to Cart page.");

        // 10 & 11. Checkout and verify address
        cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Address Details not visible.");

        // 12. Place Order
        checkoutPage.enterCommentAndPlaceOrder("Test Order Comment");

        // 13 & 14. Payment
        checkoutPage.enterPaymentDetails("Ani", "11111111111", "111", "01", "2028");

        // Step 15: Verify Success Message
        String successMsg = checkoutPage.getSuccessMessageText();
        Assert.assertTrue(successMsg.toLowerCase().contains("placed") || successMsg.toLowerCase().contains("confirmed"),
                "Expected success message not found. Actual: " + successMsg);

        // 16 & 17. Delete Account
        headerBar.clickDeleteAccount(); // Ensure this method exists in your HeaderBar
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "ACCOUNT DELETED! not visible.");
        accountDeletedPage.clickContinue();
    }
}
