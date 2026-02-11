package ui.pages;

import core.config.ConfigReader;
import core.utils.WaitUtils;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;

import org.openqa.selenium.StaleElementReferenceException;


public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(ConfigReader.getInt("timeoutSeconds"));
    }

    protected void safeClick(By locator) {
        blockAds();
        try {
            wait.clickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            blockAds();
            wait.clickable(locator).click();
        }
    }

    protected void blockAds() {
        try {
            String js =
                    "const selectors = [" +
                            " '.adsbygoogle'," +
                            " 'iframe[id^=\"aswift_\"]'," +
                            " 'iframe[src*=\"doubleclick\"]'," +
                            " 'iframe[title=\"Advertisement\"]'," +
                            " 'ins.adsbygoogle'," +
                            " 'div[id^=\"aswift_\"]'," +
                            " 'div[id*=\"google_ads_iframe\"]'" +
                            "];" +
                            "selectors.forEach(sel => document.querySelectorAll(sel).forEach(el => el.remove()));";

            ((JavascriptExecutor) driver).executeScript(js);
        } catch (Exception ignored) {
        }
    }

    protected void removeAdsIfPresent() {
        try {
            driver.switchTo().defaultContent();

            String js =
                    "const selectors = [" +
                            " 'iframe[id^=\"aswift_\"]'," +
                            " 'iframe[src*=\"doubleclick\"]'," +
                            " 'iframe[src*=\"googlesyndication\"]'," +
                            " 'iframe[title=\"Advertisement\"]'," +
                            " 'div[id^=\"aswift_\"]'," +
                            " 'div[id*=\"google_ads_iframe\"]'," +
                            " 'ins.adsbygoogle'," +
                            " 'iframe'" +   // last fallback: remove iframes that are full screen
                            "];" +
                            "selectors.forEach(sel => {" +
                            "  document.querySelectorAll(sel).forEach(el => {" +
                            "    try {" +
                            "      const r = el.getBoundingClientRect();" +
                            "      const full = (r.width >= window.innerWidth - 5) && (r.height >= window.innerHeight - 5);" +
                            "      if (el.tagName.toLowerCase() !== 'iframe' || full || el.id.startsWith('aswift_') || (el.title === 'Advertisement')) {" +
                            "        el.remove();" +
                            "      }" +
                            "    } catch(e) { try { el.remove(); } catch(e2){} }" +
                            "  });" +
                            "});";

            // Run it a few times because ads re-insert quickly
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            for (int i = 0; i < 3; i++) {
                jse.executeScript(js);
            }
        } catch (Exception ignored) {}
    }

    protected void prepareForAction() {
        removeAdsIfPresent();
    }

    protected void forceClick(By locator) {
        try {
            safeClick(locator);
            return;
        } catch (Exception ignored) {}

        // Second attempt: remove ads + JS click fallback
        try {
            removeAdsIfPresent();
            WebElement el = wait.visible(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        } catch (StaleElementReferenceException e) {
            WebElement el = wait.visible(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public void handleGoogleVignette() {
        try {
            if (driver.getCurrentUrl().contains("google_vignette")) {
                driver.navigate().refresh();
                prepareForAction(); // Remove other banners after refresh
            }
        } catch (Exception e) {
            System.out.println("Vignette handling failed: " + e.getMessage());
        }
    }
}

