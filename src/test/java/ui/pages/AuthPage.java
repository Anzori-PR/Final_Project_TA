package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage extends BasePage {

    private final By newUserSignupText = By.xpath("//*[contains(text(),'New User Signup!')]");
    private final By loginToAccountText = By.xpath("//*[contains(text(),'Login to your account')]");

    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupBtn = By.cssSelector("button[data-qa='signup-button']");

    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginBtn = By.cssSelector("button[data-qa='login-button']");

    private final By signupExistingEmailError = By.xpath("//*[contains(text(),'Email Address already exist!')]");

    private final By existingEmailError = By.xpath("//*[contains(text(),'Email Address already exist!')]");



    public AuthPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify 'New User Signup!' is visible")
    public boolean isNewUserSignupVisible() {
        try { return wait.visible(newUserSignupText).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    @Step("Verify 'Login to your account' is visible")
    public boolean isLoginToAccountVisible() {
        try { return wait.visible(loginToAccountText).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    @Step("Signup with name={name}, email={email}")
    public void signup(String name, String email) {
        wait.visible(signupName).sendKeys(name);
        wait.visible(signupEmail).sendKeys(email);

        // Try click, if ad iframe blocks it, remove ads and retry
        try {
            safeClick(signupBtn);
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            removeAdsIfPresent();
            safeClick(signupBtn);
        }
    }


    @Step("Login with email={email}")
    public void login(String email, String password) {
        wait.visible(loginEmail).sendKeys(email);
        wait.visible(loginPassword).sendKeys(password);
        safeClick(loginBtn);
    }

    @Step("Verify signup existing email error is visible")
    public boolean isExistingEmailErrorVisible() {
        try { return wait.visible(existingEmailError).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}
