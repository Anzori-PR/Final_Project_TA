package ui.tests;

import core.base.BaseUiTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import ui.pages.ProductDetailsPage;
import org.openqa.selenium.By;
import java.time.Duration;

import static core.driver.DriverFactory.getDriver;

public class UI_TC21_AddReviewTest extends BaseUiTest {

    @Test
    public void testAddReviewOnProduct() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        ProductDetailsPage productPage = new ProductDetailsPage(getDriver());

        // 1. Click on 'Products' button
        getDriver().findElement(By.xpath("//a[@href='/products']")).click();

        // --- NEW LINE: Call your new BasePage method here! ---
        // This checks if the URL is stuck on '#google_vignette' and refreshes if needed.
        productPage.handleGoogleVignette();
        // -----------------------------------------------------

        // 2. Click on 'View Product' button (First product)
        // We wait for the button to be clickable
        By viewProductBtn = By.xpath("(//a[contains(text(),'View Product')])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(viewProductBtn));

        // Use JS Click to avoid "Element Click Intercepted" errors from bottom banners
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", getDriver().findElement(viewProductBtn));

        // 3. Verify 'Write Your Review' is visible
        productPage.verifyReviewSectionVisible();

        // 4. Enter name, email and review
        productPage.submitReview("Ani", "ani.test@test.com", "This is a great product!");

        // 5. Verify success message
        productPage.verifySuccessMessage("Thank you for your review.");
    }
}