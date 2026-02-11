package ui.pages;
import core.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
public class CartPage extends BasePage {
    @FindBy(xpath = "//a[text()='Proceed To Checkout']")
    private WebElement proceedToCheckoutButton;
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartCount() {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='cart_info_table']/tbody/tr"));
        return rows.size();
    }

    public String getProductPrice(int rowIndex) {
        return driver.findElement(By.xpath("//table[@id='cart_info_table']/tbody/tr[" + rowIndex + "]//td[@class='cart_price']")).getText();
    }

    public String getProductQuantity(int rowIndex) {
        return driver.findElement(By.xpath("//table[@id='cart_info_table']/tbody/tr[" + rowIndex + "]//button[@class='disabled']")).getText();
    }

    public String getProductTotalPrice(int rowIndex) {
        return driver.findElement(By.xpath("//table[@id='cart_info_table']/tbody/tr[" + rowIndex + "]//p[@class='cart_total_price']")).getText();
    }

    //test case 16
    public void clickProceedToCheckout() {
        WaitUtils.waitForElementClickable(driver, proceedToCheckoutButton, 10);
        try {
            proceedToCheckoutButton.click();
        } catch (Exception e) {
            // Fallback for ad-interception
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", proceedToCheckoutButton);
        }
    }
}
