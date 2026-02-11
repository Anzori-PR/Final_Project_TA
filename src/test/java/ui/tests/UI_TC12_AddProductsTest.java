package ui.tests;
import core.base.BaseUiTest;
import core.driver.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.*;
public class UI_TC12_AddProductsTest extends BaseUiTest {
    @Test(description = "Test Case 12: Add Products in Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that user can add multiple products to the cart and prices are correct.")
    public void testAddProductsToCart() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        HeaderBar headerBar = new HeaderBar(DriverFactory.getDriver());
        ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // 3. Verify home page
        Assert.assertTrue(homePage.isHomeVisible(), "Home page is not visible!");

        // 4. Click 'Products'
        headerBar.clickProductsButton();

        // 5 & 6. Add first product and continue
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        // 7 & 8. Add second product and view cart
        productsPage.addSecondProductToCart();
        productsPage.clickViewCart();

        // 9. Verify both products added
        Assert.assertEquals(cartPage.getCartCount(), 2, "Cart should contain 2 products.");

        // 10. Verify price, quantity, and total
        // Note: You can add specific price string matches if needed
        Assert.assertEquals(cartPage.getProductQuantity(1), "1", "Quantity of first product is wrong.");
        Assert.assertEquals(cartPage.getProductQuantity(2), "1", "Quantity of second product is wrong.");

        System.out.println("First Product Total: " + cartPage.getProductTotalPrice(1));
        System.out.println("Second Product Total: " + cartPage.getProductTotalPrice(2));
    }
}
