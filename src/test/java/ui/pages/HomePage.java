package ui.pages;

import core.driver.DriverFactory;
import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final By homeSlider = By.cssSelector("#slider"); // simple "home visible" proof
    private final By signupLoginBtn = By.cssSelector("a[href='/login']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify home page is visible")
    public boolean isHomeVisible() {
        WebElement sliderElement = DriverFactory.getDriver().findElement(homeSlider);
        return WaitUtils.waitForElementVisible(DriverFactory.getDriver(), sliderElement, 10).isDisplayed();

    }

    @Step("Click Signup/Login")
    public void clickSignupLogin() {
        wait.clickable(signupLoginBtn).click();
    }
}
