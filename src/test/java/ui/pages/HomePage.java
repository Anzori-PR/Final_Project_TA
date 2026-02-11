package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By homeSlider = By.cssSelector("#slider"); // simple "home visible" proof
    private final By signupLoginBtn = By.cssSelector("a[href='/login']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify home page is visible")
    public boolean isHomeVisible() {
        try {
            return wait.visible(homeSlider).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click Signup/Login")
    public void clickSignupLogin() {
        wait.clickable(signupLoginBtn).click();
    }
}
