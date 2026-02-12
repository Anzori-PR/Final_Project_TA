package ui.pages;

import core.driver.DriverFactory;
import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

public class ContactUsPage extends BasePage {

    private final By getInTouchHeader = By.xpath("//h2[contains(text(),'Get In Touch')]");
    private final By nameInput = By.cssSelector("input[data-qa='name']");
    private final By emailInput = By.cssSelector("input[data-qa='email']");
    private final By subjectInput = By.cssSelector("input[data-qa='subject']");
    private final By messageInput = By.id("message");
    private final By uploadFile = By.name("upload_file");
    private final By submitBtn = By.cssSelector("input[data-qa='submit-button']");
    private final By successMsg = By.cssSelector(".status.alert.alert-success");
    private final By homeBtn = By.xpath("//span[text()=' Home']");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify 'GET IN TOUCH' is visible")
    public boolean isGetInTouchVisible() {
        WebElement header = DriverFactory.getDriver().findElement(getInTouchHeader);
        return WaitUtils.waitForElementVisible(DriverFactory.getDriver(), header, 10).isDisplayed();
    }


    @Step("Fill contact form")
    public void fillContactForm(String name, String email, String subject, String message) {
        DriverFactory.getDriver().findElement(nameInput).sendKeys(name);
        DriverFactory.getDriver().findElement(emailInput).sendKeys(email);
        DriverFactory.getDriver().findElement(subjectInput).sendKeys(subject);
        DriverFactory.getDriver().findElement(messageInput).sendKeys(message);
    }

    @Step("Upload file")
    public void uploadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        }

        WebElement uploadElement = DriverFactory.getDriver().findElement(uploadFile);
        uploadElement.sendKeys(file.getAbsolutePath());
    }


    @Step("Click Submit and accept alert")
    public void submitForm() {
        DriverFactory.getDriver().findElement(submitBtn).click();
        // Step 9: Click OK button (Browser Alert)
        DriverFactory.getDriver().switchTo().alert().accept();
    }

    @Step("Verify success message is visible")
    public String getSuccessMessage() {
        WebElement msgElement = DriverFactory.getDriver().findElement(successMsg);
        return WaitUtils.waitForElementVisible(DriverFactory.getDriver(), msgElement, 10).getText();
    }


    @Step("Click Home button")
    public void clickHome() {
        WaitUtils.waitForElementClickable(DriverFactory.getDriver(),
                DriverFactory.getDriver().findElement(homeBtn), 10).click();

        // Handle the common Google Ad redirect after clicking Home
        if (DriverFactory.getDriver().getCurrentUrl().contains("#google_vignette")) {
            DriverFactory.getDriver().get("https://automationexercise.com/");
        }
    }
}