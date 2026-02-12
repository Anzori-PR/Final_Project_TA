package ui.pages;

import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderBar extends BasePage {

    private final By loggedInAs = By.xpath("//*[contains(text(),'Logged in as')]");
    private final By logoutBtn = By.cssSelector("a[href='/logout']");
    private final By deleteAccountBtn = By.cssSelector("a[href='/delete_account']");
    private final By loggedInAsLocator = By.xpath("//li[contains(., 'Logged in as')]");
    @FindBy(xpath = "//a[@href='/products']")
    private WebElement productsButton;
    @FindBy(xpath = "//a[@href='/login']")
    private WebElement loginButton;
    @FindBy(xpath = "//a[@href='/contact_us']")
    private WebElement contactUsButton;
    public HeaderBar(WebDriver driver) {
        super(driver);
    }

    @Step("Click Signup / Login button")
    public void clickLoginButton() {
        WaitUtils.waitForElementClickable(driver, loginButton, 10).click();
    }
    @Step("Verify 'Logged in as' is visible")
    public boolean isLoggedInAsVisible() {
        try {
            // First, wait for the redirect away from the login page
            WaitUtils.waitForUrlContains(driver, "automationexercise.com", 10);

            // Then, wait for the specific 'Logged in as' element to appear
            WebElement element = WaitUtils.waitForElementVisible(driver, driver.findElement(loggedInAsLocator), 15);
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failure! Current URL is: " + driver.getCurrentUrl());
            return false;
        }
    }

    @Step("Click Logout")
    public void clickLogout() {
        safeClick(logoutBtn);
    }

    @Step("Click Delete Account")
    public void clickDeleteAccount() {
        forceClick(deleteAccountBtn);
    }

    public void clickProductsButton() {
        // Wait for it to be ready
        WaitUtils.waitForElementClickable(driver, productsButton, 10);

        try {
            productsButton.click();
        } catch (Exception e) {
            // If a standard click fails (like an ad overlapping), use JavaScript click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", productsButton);
        }

        // Handle potential Google Ad Redirects
        if (driver.getCurrentUrl().contains("#google_vignette")) {
            driver.navigate().to("https://automationexercise.com/products");
        }
    }

}
