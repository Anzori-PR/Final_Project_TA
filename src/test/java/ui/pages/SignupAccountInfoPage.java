package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SignupAccountInfoPage extends BasePage {

    // Heading can be inconsistent, so don't rely only on it
    private final By accountInfoHeading = By.xpath("//div[contains(@class,'login-form')]//h2");

    // This is the BEST proof you are on the account info form
    private final By passwordInput = By.cssSelector("input[data-qa='password']");

    private final By titleMrRadio = By.id("id_gender1");

    private final By daysSelect = By.cssSelector("select[data-qa='days']");
    private final By monthsSelect = By.cssSelector("select[data-qa='months']");
    private final By yearsSelect = By.cssSelector("select[data-qa='years']");

    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");

    private final By firstNameInput = By.cssSelector("input[data-qa='first_name']");
    private final By lastNameInput = By.cssSelector("input[data-qa='last_name']");
    private final By companyInput = By.cssSelector("input[data-qa='company']");
    private final By address1Input = By.cssSelector("input[data-qa='address']");
    private final By address2Input = By.cssSelector("input[data-qa='address2']");
    private final By countrySelect = By.cssSelector("select[data-qa='country']");
    private final By stateInput = By.cssSelector("input[data-qa='state']");
    private final By cityInput = By.cssSelector("input[data-qa='city']");
    private final By zipInput = By.cssSelector("input[data-qa='zipcode']");
    private final By mobileInput = By.cssSelector("input[data-qa='mobile_number']");

    private final By createAccountBtn = By.cssSelector("button[data-qa='create-account']");

    public SignupAccountInfoPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for Account Information form (Password field) to be visible")
    public void waitForAccountInfoForm() {
        wait.visible(passwordInput);
    }

    @Step("Verify Account Information form is visible")
    public boolean isAccountInfoFormVisible() {
        try {
            return wait.visible(passwordInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Fill account information and address details")
    public void fillAccountAndAddressDetails() {
        safeClick(titleMrRadio);

        wait.visible(passwordInput).sendKeys("Test@12345");

        new Select(wait.visible(daysSelect)).selectByValue("10");
        new Select(wait.visible(monthsSelect)).selectByValue("5");
        new Select(wait.visible(yearsSelect)).selectByValue("2000");

        wait.clickable(newsletterCheckbox).click();
        wait.clickable(offersCheckbox).click();

        wait.visible(firstNameInput).sendKeys("NewTest");
        wait.visible(lastNameInput).sendKeys("Test");
        wait.visible(companyInput).sendKeys("QA Team");
        wait.visible(address1Input).sendKeys("Test Street 1");
        wait.visible(address2Input).sendKeys("Apartment 2");
        new Select(wait.visible(countrySelect)).selectByVisibleText("Canada");

        wait.visible(stateInput).sendKeys("Ontario");
        wait.visible(cityInput).sendKeys("Toronto");
        wait.visible(zipInput).sendKeys("12345");
        wait.visible(mobileInput).sendKeys("5551234567");
    }

    @Step("Click 'Create Account' button")
    public void clickCreateAccount() {
        try {
            wait.clickable(createAccountBtn).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            removeAdsIfPresent();
            wait.clickable(createAccountBtn).click();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            // sometimes page re-renders
            removeAdsIfPresent();
            wait.clickable(createAccountBtn).click();
        }
    }

}
