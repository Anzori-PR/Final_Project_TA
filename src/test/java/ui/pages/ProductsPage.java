package ui.pages;

import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductsPage extends BasePage {
    @FindBy(xpath = "//h2[text()='All Products']")
    private WebElement allProductsTitle;

    @FindBy(id = "search_product")
    private WebElement searchInput;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(xpath = "//h2[text()='Searched Products']")
    private WebElement searchedProductsTitle;

    @FindBy(css = ".features_items .col-sm-4")
    private List<WebElement> displayedProducts;

    @FindBy(xpath = "(//a[@data-product-id])[1]")
    private WebElement firstProductAddToCart;

    @FindBy(xpath = "(//a[@data-product-id])[3]") // Index 3 is often the 2nd product's overlay button
    private WebElement secondProductAddToCart;

    @FindBy(xpath = "//button[text()='Continue Shopping']")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//u[text()='View Cart']")
    private WebElement viewCartLink;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Requirement: Use Explicit Waits (WaitUtils) instead of Thread.sleep or hard-coded waits.
     */
    public boolean isAllProductsPageVisible() {
        core.utils.WaitUtils.waitForAdToDisappear(driver); // Clear the ad first
        return core.utils.WaitUtils.waitForElementVisible(driver, allProductsTitle, 15).isDisplayed();
    }

    public void searchForProduct(String productName) {
        // Best practice: Wait for the input to be clickable before interaction
        WaitUtils.waitForElementVisible(driver, searchInput, 10).clear();
        searchInput.sendKeys(productName);
        searchButton.click();
    }

    public boolean isSearchedProductsTitleVisible() {
        return WaitUtils.waitForElementVisible(driver, searchedProductsTitle, 10).isDisplayed();
    }


    //12th test case
    public boolean areProductsVisible() {
        // List elements don't work directly with WaitUtils.waitForElementVisible
        // because that method expects a single WebElement.
        // We verify the list is not empty.
        return !displayedProducts.isEmpty();
    }
    @Step("Add first product to cart")
    public void addFirstProductToCart() {
        Actions actions = new Actions(driver);
        WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='single-products'])[1]"));

        // Hover over the product to make the 'Add to Cart' overlay button visible
        actions.moveToElement(firstProduct).perform();

        // Locate the overlay button
        WebElement addToCartBtn = driver.findElement(By.xpath("(//a[@data-product-id='1'])[2]"));

        try {
            // Attempt standard click first
            WaitUtils.waitForElementClickable(driver, addToCartBtn, 10).click();
        } catch (Exception e) {
            // FALLBACK: Use JavaScript click if an ad intercepts the UI
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);
        }
    }

    public void clickContinueShopping() {
        WaitUtils.waitForElementClickable(driver, continueShoppingButton, 10).click();
    }

    public void addSecondProductToCart() {
        Actions actions = new Actions(driver);
        // Requirement 7: Hover over second product
        WebElement secondProduct = driver.findElement(By.xpath("(//div[@class='single-products'])[2]"));
        actions.moveToElement(secondProduct).perform();
        WaitUtils.waitForElementClickable(driver, secondProductAddToCart, 10).click();
    }

    @Step("Click 'View Cart' link in modal")
    public void clickViewCart() {
        // Ensure the success modal is actually visible before looking for the link
        WebElement modalContent = driver.findElement(By.id("cartModal"));
        WaitUtils.waitForElementVisible(driver, modalContent, 10);

        WaitUtils.waitForElementVisible(driver, viewCartLink, 10);

        try {
            // Standard click
            viewCartLink.click();
        } catch (Exception e) {
            // FALLBACK: If a Google ad overlay blocks the UI click, use JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartLink);
        }
    }
}
