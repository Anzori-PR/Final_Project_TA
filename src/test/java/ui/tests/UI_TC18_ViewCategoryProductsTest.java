package ui.tests;

import core.base.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.BasePage;

import java.time.Duration;

import static core.driver.DriverFactory.getDriver;

public class UI_TC18_ViewCategoryProductsTest extends BaseUiTest {

    @Test
    public void testViewCategoryProducts() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        BasePage basePage = new BasePage(getDriver()) {};

        // 3. Verify that categories are visible on left side bar
        WebElement categorySidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accordian")));
        Assert.assertTrue(categorySidebar.isDisplayed(), "Category sidebar is not visible!");

        WebElement womenCategory = getDriver().findElement(By.xpath("//a[normalize-space()='Women']"));
        Assert.assertTrue(womenCategory.isDisplayed(), "Women category is not visible!");

        // 4. Click on 'Women' category
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", womenCategory);
        womenCategory.click();

        // 5. Click on 'Tops'
        // We click 'Tops' specifically because Step 6 requires verifying 'WOMEN - TOPS PRODUCTS'.
        WebElement topsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='Women']//a[contains(text(),'Tops')]")));
        topsLink.click();

        basePage.handleGoogleVignette();

        // 6. Verify that category page is displayed and confirm text 'WOMEN - TOPS PRODUCTS'
        WebElement headerTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='title text-center']")));
        String headerText = headerTitle.getText();
        System.out.println("Current Page Header: " + headerText);

        // This assertion will now PASS because we clicked 'Tops'
        Assert.assertTrue(headerText.contains("WOMEN - TOPS PRODUCTS"), "Header did not contain 'WOMEN - TOPS PRODUCTS'. Actual: " + headerText);

        // 7. On left side bar, click on any sub-category link of 'Men' category
        WebElement menCategory = getDriver().findElement(By.xpath("//a[normalize-space()='Men']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", menCategory);
        menCategory.click();

        // Click 'Tshirts' under Men
        WebElement tshirtsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='Men']//a[contains(text(),'Tshirts')]")));
        tshirtsLink.click();

        basePage.handleGoogleVignette();

        // 8. Verify that user is navigated to that category page
        WebElement menHeaderTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='title text-center']")));
        String menHeaderText = menHeaderTitle.getText();
        System.out.println("Current Page Header: " + menHeaderText);
        Assert.assertEquals(menHeaderText, "MEN - TSHIRTS PRODUCTS", "Header incorrect for Men's category!");
    }
}