package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderBar extends BasePage {

    private final By loggedInAs = By.xpath("//*[contains(text(),'Logged in as')]");
    private final By logoutBtn = By.cssSelector("a[href='/logout']");
    private final By deleteAccountBtn = By.cssSelector("a[href='/delete_account']");

    public HeaderBar(WebDriver driver) {
        super(driver);
    }

    @Step("Verify 'Logged in as' is visible")
    public boolean isLoggedInAsVisible() {
        try { return wait.visible(loggedInAs).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    @Step("Click Logout")
    public void clickLogout() {
        safeClick(logoutBtn);
    }

    @Step("Click Delete Account")
    public void clickDeleteAccount() {
        forceClick(deleteAccountBtn);
    }


}
