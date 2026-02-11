package core.utils;

import core.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public final class WaitUtils {
    private final WebDriverWait wait;

    public WaitUtils(int timeoutSeconds) {
        this.wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void urlContains(String part) {
        wait.until(ExpectedConditions.urlContains(part));
    }

    public void textPresent(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    public static WebElement waitForElementVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static void waitForAdToDisappear(WebDriver driver) {
        try {
            // If the URL contains the google vignette fragment, navigate away or refresh
            if (driver.getCurrentUrl().contains("#google_vignette")) {
                driver.navigate().refresh();
            }
        } catch (Exception e) {
            // Ignore if no ad is present
        }
    }
    public static void waitForUrlContains(WebDriver driver, String partialUrl, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

}
