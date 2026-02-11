package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage extends BasePage {

    private final By accountCreatedText = By.cssSelector("h2[data-qa='account-created']");

    public void waitForAccountCreated() {
        wait.visible(accountCreatedText);
    }


    private final By continueBtn = By.cssSelector("a[data-qa='continue-button'], a.btn.btn-primary");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }


    @Step("Verify 'ACCOUNT CREATED!' is visible")
    public boolean isAccountCreatedVisible() {
        try { return wait.visible(accountCreatedText).isDisplayed(); }
        catch (Exception e) { return false; }
    }


    @Step("Click 'Continue' button")
    public void clickContinue() {
        try {
            wait.clickable(continueBtn).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            removeAdsIfPresent();
            wait.clickable(continueBtn).click();
        }
    }

}
