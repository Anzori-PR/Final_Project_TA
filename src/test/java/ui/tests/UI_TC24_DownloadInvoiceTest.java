package ui.tests;

import core.base.BaseUiTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.PaymentPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.File;

import static core.driver.DriverFactory.getDriver;

public class UI_TC24_DownloadInvoiceTest extends BaseUiTest {

    @Test
    public void testDownloadInvoice() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        PaymentPage paymentPage = new PaymentPage(getDriver());

        // 0. Cleanup
        deleteFile("invoice.txt");
        deleteFile("invoice (1).txt");

        // 4. Add products to cart
        WebElement addToCartBtn = getDriver().findElement(By.xpath("(//a[contains(text(),'Add to cart')])[1]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", addToCartBtn);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue Shopping']"))).click();

        // 5. Click 'Cart' button
        getDriver().findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        paymentPage.handleGoogleVignette();

        // 7. Click Proceed To Checkout
        getDriver().findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();

        // 8. Click 'Register / Login' button
        getDriver().findElement(By.xpath("//u[text()='Register / Login']")).click();

        // --- REGISTER USER FLOW ---
        getDriver().findElement(By.name("name")).sendKeys("AniUser");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("ani" + System.currentTimeMillis() + "@test.com");
        getDriver().findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        getDriver().findElement(By.id("password")).sendKeys("password123");
        getDriver().findElement(By.id("first_name")).sendKeys("Ani");
        getDriver().findElement(By.id("last_name")).sendKeys("Merabishvili");
        getDriver().findElement(By.id("address1")).sendKeys("123 Tbilisi St");

        getDriver().findElement(By.id("country")).sendKeys("United States");

        getDriver().findElement(By.id("state")).sendKeys("Georgia");
        getDriver().findElement(By.id("city")).sendKeys("Tbilisi");
        getDriver().findElement(By.id("zipcode")).sendKeys("00000");
        getDriver().findElement(By.id("mobile_number")).sendKeys("555123456");

        WebElement createAccBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-qa='create-account']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", createAccBtn);

        // 10. Verify 'ACCOUNT CREATED!' and click 'Continue'
        getDriver().findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        paymentPage.handleGoogleVignette();

        // 12. Click 'Cart' button
        getDriver().findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // 13. Click 'Proceed To Checkout' button
        getDriver().findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();

        // 15. Enter description and click Place Order
        getDriver().findElement(By.name("message")).sendKeys("Test Order by Ani");

        WebElement placeOrderBtn = getDriver().findElement(By.xpath("//a[@href='/payment']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrderBtn);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", placeOrderBtn);

        // --- SAFETY CHECK ---
        paymentPage.handleGoogleVignette();

        // 16. Enter payment details
        paymentPage.enterPaymentDetails("Ani Merabishvili", "410000000000", "123", "01", "2030");

        // 17. Click 'Pay'
        paymentPage.clickPayAndConfirm();

        // 18. Verify success
        paymentPage.verifyOrderSuccess();

        // 19. Download Invoice
        WebElement downloadBtn = getDriver().findElement(By.linkText("Download Invoice"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", downloadBtn);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", downloadBtn);

        // --- SMART FILE CHECK ---
        boolean isFound = verifyFileDownloaded();
        Assert.assertTrue(isFound, "Could not find 'invoice.txt' (or similar) in Downloads folder.");

        // 20. Click 'Continue'
        paymentPage.clickContinue();

        paymentPage.handleGoogleVignette();

        // 21. Delete Account
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/delete_account']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", deleteBtn);

        WebElement deleteMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Account Deleted')]")));
        Assert.assertTrue(deleteMsg.isDisplayed(), "Account was not deleted successfully!");

        getDriver().findElement(By.xpath("//a[@data-qa='continue-button']")).click();
    }

    // --- HELPER METHODS ---

    public void deleteFile(String fileName) {
        String home = System.getProperty("user.home");
        File file1 = new File(home + "/Downloads/" + fileName);
        if (file1.exists()) file1.delete();

        File file2 = new File(home + "/OneDrive/Downloads/" + fileName);
        if (file2.exists()) file2.delete();
    }

    public boolean verifyFileDownloaded() {
        String home = System.getProperty("user.home");
        String[] possiblePaths = {
                home + "/Downloads/",
                home + "/OneDrive/Downloads/"
        };

        for (int i = 0; i < 20; i++) {
            for (String path : possiblePaths) {
                File dir = new File(path);
                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles((d, name) -> name.startsWith("invoice") && name.endsWith(".txt"));
                    if (files != null && files.length > 0) {
                        System.out.println("DEBUG: Found file at " + files[0].getAbsolutePath());
                        return true;
                    }
                }
            }
            try { Thread.sleep(500); } catch (Exception e) {}
        }

        System.out.println("DEBUG: Failed to find file.");
        return false;
    }
}