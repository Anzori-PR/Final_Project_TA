package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProductDetailsPage extends BasePage {

    // Locators
    private By nameInput = By.id("name");
    private By emailInput = By.id("email");
    private By reviewInput = By.id("review");
    private By submitButton = By.id("button-review");
    private By successMessage = By.xpath("//div[@class='alert-success alert']//span");
    private By writeReviewHeader = By.xpath("//a[contains(text(),'Write Your Review')]");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyReviewSectionVisible() {
        Assert.assertTrue(driver.findElement(writeReviewHeader).isDisplayed(), "'Write Your Review' header is not visible");
    }

    public void submitReview(String name, String email, String review) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(reviewInput).sendKeys(review);
        driver.findElement(submitButton).click();
    }

    public void verifySuccessMessage(String expectedMessage) {
        String actualMessage = driver.findElement(successMessage).getText();
        Assert.assertEquals(actualMessage, expectedMessage);
    }
}