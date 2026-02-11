package core.driver;

import core.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> driverTL = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");

        if (!browser.equals("chrome")) {
            throw new RuntimeException("Only chrome implemented. Set browser=chrome");
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--window-size=1440,900");

        driverTL.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverTL.get();
        if (driver == null) throw new IllegalStateException("WebDriver is null. Did you call initDriver()?");
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverTL.get();
        if (driver != null) {
            driver.quit();
            driverTL.remove();
        }
    }
}
