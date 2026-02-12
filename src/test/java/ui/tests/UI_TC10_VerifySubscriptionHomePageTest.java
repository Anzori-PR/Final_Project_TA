package ui.tests;

import core.base.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static core.driver.DriverFactory.getDriver;

public class UI_TC10_VerifySubscriptionHomePageTest extends BaseUiTest {

    @Test
    public void testVerifySubscriptionInHomePage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        // 3. Verify that home page is visible successfully
        // We check for the main slider/carousel to confirm we are on the home page
        WebElement homeSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("slider-carousel")));
        Assert.assertTrue(homeSlider.isDisplayed(), "Home page slider is not visible!");

        // 4. Scroll down to footer
        WebElement footer = getDriver().findElement(By.id("footer"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", footer);

        // 5. Verify text 'SUBSCRIPTION'
        WebElement subscriptionHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Subscription']")));
        Assert.assertTrue(subscriptionHeader.isDisplayed(), "Subscription header is not visible!");
        Assert.assertEquals(subscriptionHeader.getText(), "SUBSCRIPTION", "Subscription header text is incorrect!");

        // 6. Enter email address in input and click arrow button
        WebElement emailInput = getDriver().findElement(By.id("susbscribe_email"));
        emailInput.sendKeys("ani" + System.currentTimeMillis() + "@test.com"); // Unique email

        WebElement arrowBtn = getDriver().findElement(By.id("subscribe"));
        arrowBtn.click();

        // 7. Verify success message 'You have been successfully subscribed!' is visible
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-subscribe")));

        // Wait for the text to actually appear inside the element (sometimes there is a small delay)
        wait.until(ExpectedConditions.textToBePresentInElement(successMsg, "You have been successfully subscribed!"));

        Assert.assertTrue(successMsg.isDisplayed(), "Success message is not displayed!");
        Assert.assertEquals(successMsg.getText(), "You have been successfully subscribed!", "Success message text mismatch!");
    }
}