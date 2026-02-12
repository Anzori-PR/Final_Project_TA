package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage extends BasePage {

    private final By accountCreatedText = By.cssSelector("h2[data-qa='account-created']");

    public void waitForAccountCreated() {
        wait.visible(accountCreatedText);
    }


    private final By continueBtn = By.cssSelector("a[data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }


    @Step("Verify 'ACCOUNT CREATED!' is visible")
    public boolean isAccountCreatedVisible() {
        try { return wait.visible(accountCreatedText).isDisplayed(); }
        catch (Exception e) { return false; }
    }


    @Step("Click Continue on Account Created page")
    public void clickContinue() {
        try {
            safeClick(continueBtn);
        } catch (Exception e) {
            blockAds();
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", wait.visible(continueBtn));
        }
    }

}
