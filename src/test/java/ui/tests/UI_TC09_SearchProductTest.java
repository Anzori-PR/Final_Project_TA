package ui.tests;

import core.base.BaseUiTest; // Correct package based on your code
import core.driver.DriverFactory; // Needed to access the driver
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.HeaderBar;
import ui.pages.HomePage;
import ui.pages.ProductsPage;

@Epic("UI Tests")
@Feature("Product Search")
public class UI_TC09_SearchProductTest extends BaseUiTest {

    @Test(description = "Test Case 9: Search Product")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the search functionality returns relevant products.")
    public void testSearchProduct() {
        // Use DriverFactory.getDriver() instead of a local 'driver' variable
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        HeaderBar headerBar = new HeaderBar(DriverFactory.getDriver());
        ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());

        // Step 3: Verify home page visibility
        Assert.assertTrue(homePage.isHomeVisible(), "Home page is not visible!");

        // Step 4: Click Products
        headerBar.clickProductsButton();

        // Step 5: Verify navigation to All Products
        Assert.assertTrue(productsPage.isAllProductsPageVisible(), "Failed to navigate to All Products page.");

        // Step 6: Search
        String searchItem = "tshirt";
        productsPage.searchForProduct(searchItem);

        // Step 7 & 8: Verify results
        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(), "'SEARCHED PRODUCTS' title not visible.");
        Assert.assertTrue(productsPage.areProductsVisible(), "No products found for: " + searchItem);
    }
}