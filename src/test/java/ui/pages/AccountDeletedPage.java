package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDeletedPage extends BasePage {

    // More reliable than searching any text anywhere
    private final By deletedTitle = By.cssSelector(".title.text-center");
    private final By continueBtn = By.cssSelector("a[data-qa='continue-button'], a.btn.btn-primary");

    public AccountDeletedPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for Account Deleted page")
    public void waitForAccountDeleted() {
        // Clear possible overlays first
        blockAds();
        wait.visible(deletedTitle);
    }

    @Step("Verify 'ACCOUNT DELETED!' is visible")
    public boolean isAccountDeletedVisible() {
        try {
            String text = wait.visible(deletedTitle).getText().trim().toUpperCase();
            return text.contains("ACCOUNT DELETED");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click Continue after account deletion")
    public void clickContinue() {
        blockAds();
        safeClick(continueBtn); // safeClick already handles ads + retry
    }
}
