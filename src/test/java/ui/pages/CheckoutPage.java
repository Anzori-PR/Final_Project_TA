package ui.pages;

import core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {
    @FindBy(xpath = "//h2[text()='Address Details']")
    private WebElement addressDetailsTitle;

    @FindBy(name = "message")
    private WebElement commentTextArea;

    @FindBy(xpath = "//a[text()='Place Order']")
    private WebElement placeOrderButton;

    @FindBy(name = "name_on_card")
    private WebElement nameOnCardInput;

    @FindBy(name = "card_number")
    private WebElement cardNumberInput;

    @FindBy(name = "cvc")
    private WebElement cvcInput;

    @FindBy(name = "expiry_month")
    private WebElement expiryMonthInput;

    @FindBy(name = "expiry_year")
    private WebElement expiryYearInput;

    @FindBy(id = "submit")
    private WebElement payButton;

    @FindBy(xpath = "//*[contains(text(), 'Order Placed!')]")
    private WebElement successMessage;

    @FindBy(css = "h2[data-qa='order-placed'] b")
    private WebElement orderPlacedText;
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressDetailsVisible() {
        return WaitUtils.waitForElementVisible(driver, addressDetailsTitle, 10).isDisplayed();
    }

    public void enterCommentAndPlaceOrder(String comment) {
        commentTextArea.sendKeys(comment);
        placeOrderButton.click();
    }

    public void enterPaymentDetails(String name, String card, String cvc, String month, String year) {
        nameOnCardInput.sendKeys(name);
        cardNumberInput.sendKeys(card);
        cvcInput.sendKeys(cvc);
        expiryMonthInput.sendKeys(month);
        expiryYearInput.sendKeys(year);
        payButton.click();
    }

    @Step("Get order success message text")
    public String getSuccessMessageText() {
        // Wait specifically for the confirmation element to appear
        return core.utils.WaitUtils.waitForElementVisible(driver, orderPlacedText, 15).getText();
    }
}
