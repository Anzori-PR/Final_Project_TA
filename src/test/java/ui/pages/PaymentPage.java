package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.File;

public class PaymentPage extends BasePage {

    private By nameOnCard = By.name("name_on_card");
    private By cardNumber = By.name("card_number");
    private By cvc = By.name("cvc");
    private By expiryMonth = By.name("expiry_month");
    private By expiryYear = By.name("expiry_year");
    private By payButton = By.id("submit");
    private By successMessage = By.cssSelector("[data-qa='order-placed']");
    private By downloadInvoiceBtn = By.linkText("Download Invoice");
    private By continueBtn = By.linkText("Continue");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void enterPaymentDetails(String name, String number, String cvcNum, String month, String year) {
        // FIX: We use 'wait.visible' here to ensure the Payment Page has fully loaded
        // before we try to type anything.
        wait.visible(nameOnCard).sendKeys(name);
        driver.findElement(cardNumber).sendKeys(number);
        driver.findElement(cvc).sendKeys(cvcNum);
        driver.findElement(expiryMonth).sendKeys(month);
        driver.findElement(expiryYear).sendKeys(year);
    }

    public void clickPayAndConfirm() {
        forceClick(payButton);
    }

    public void verifyOrderSuccess() {
        WebElement message = wait.visible(successMessage);
        Assert.assertTrue(message.isDisplayed(), "Order success message not displayed!");
    }

    public void downloadInvoice() {
        driver.findElement(downloadInvoiceBtn).click();
    }

    public void clickContinue() {
        driver.findElement(continueBtn).click();
    }

    public boolean waitForFileDownload(String fileName) {
        String home = System.getProperty("user.home");
        File file = new File(home + "/Downloads/" + fileName);

        int attempts = 0;
        while (attempts < 10) {
            if (file.exists()) {
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
        return false;
    }

    public void deleteInvoice(String fileName) {
        String home = System.getProperty("user.home");
        File file = new File(home + "/Downloads/" + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}