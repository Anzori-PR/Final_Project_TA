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

    private final By logoutBtn = By.xpath("//a[normalize-space()='Logout']");

    private final By deleteAccountBtn = By.xpath("//a[normalize-space()='Delete Account']");

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
        safeClick(By.xpath("//a[@href='/login']"));
    }

    @Step("Verify 'Logged in as' is visible")
    public boolean isLoggedInAsVisible() {
        try {
            blockAds();

            // Best locator for this site header
            By loggedInAsText = By.xpath("//a[contains(normalize-space(),'Logged in as')]");

            // Wait for it properly (By, not WebElement)
            WebElement el = wait.visible(loggedInAsText);   // uses your WaitUtils wrapper inside BasePage
            return el.isDisplayed();
        } catch (Exception e) {
            System.out.println("Logged in as not visible. URL: " + driver.getCurrentUrl());
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
