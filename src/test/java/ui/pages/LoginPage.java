package ui.pages;

import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor; // Added missing import
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By emailInput = By.cssSelector("input[data-qa='login-email']");
    private final By passwordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginBtn = By.cssSelector("button[data-qa='login-button']");
    private final By logoutBtn = By.cssSelector("a[href='/logout']");

    // Locators for Signup (Needed if you use the dynamic user creation for TC16)
    private final By signupNameInput = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailInput = By.cssSelector("input[data-qa='signup-email']");
    private final By signupBtn = By.cssSelector("button[data-qa='signup-button']");

    private final By loginHeader = By.xpath("//h2[contains(text(), 'Login to your account')]");
    private final By errorMessage = By.xpath("//p[contains(text(), 'incorrect')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login with email: {0}")
    public void login(String email, String password) {
        // 1. Wait for and find the email field
        WebElement emailField = WaitUtils.waitForElementVisible(driver, driver.findElement(emailInput), 10);
        emailField.clear();
        emailField.sendKeys(email);

        // 2. Enter password
        driver.findElement(passwordInput).sendKeys(password);

        // 3. Attempt to click the login button
        WebElement loginButton = driver.findElement(loginBtn);
        try {
            loginButton.click();
        } catch (Exception e) {
            // FALLBACK: Force the click via JavaScript if an ad is in the way
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Step("Signup with name: {0} and email: {1}")
    public void signup(String name, String email) {
        WaitUtils.waitForElementVisible(driver, driver.findElement(signupNameInput), 10).sendKeys(name);
        driver.findElement(signupEmailInput).sendKeys(email);
        driver.findElement(signupBtn).click();
    }

    @Step("Verify Logout button is visible")
    public boolean isLogoutVisible() {
        try {
            // Using driver.findElement to convert the By locator for the wait utility
            return WaitUtils.waitForElementVisible(driver, driver.findElement(logoutBtn), 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify 'Login to your account' is visible")
    public boolean isLoginHeaderVisible() {
        return WaitUtils.waitForElementVisible(driver,
                driver.findElement(loginHeader), 10).isDisplayed();
    }

    @Step("Verify error 'Your email or password is incorrect!' is visible")
    public boolean isErrorMessageVisible() {
        try {
            return WaitUtils.waitForElementVisible(driver,
                    driver.findElement(errorMessage), 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}