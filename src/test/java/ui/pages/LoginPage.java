package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput = By.cssSelector("input[data-qa='login-email']");
    private final By passwordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginBtn = By.cssSelector("button[data-qa='login-button']");
    private final By logoutBtn = By.cssSelector("a[href='/logout']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login with email: {email}")
    public void login(String email, String password) {
        wait.visible(emailInput).sendKeys(email);
        wait.visible(passwordInput).sendKeys(password);
        wait.clickable(loginBtn).click();
    }

    @Step("Verify Logout button is visible")
    public boolean isLogoutVisible() {
        try {
            return wait.visible(logoutBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
