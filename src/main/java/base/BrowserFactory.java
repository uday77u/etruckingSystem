package base;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

   // private static final boolean HEADLESS = ReadConfig.getInstance().getHeadlessMode();

    public static WebDriver initBrowser(String browserName,boolean HEADLESS) {

        browserName = browserName.toLowerCase();

        switch (browserName) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                //String filePath=System.getProperty("user.dir")+"\\Photos\\VPNUSAPlanet.crx";
                //chromeOptions.addExtensions(new File(filePath));

                if (HEADLESS) {
                    chromeOptions.addArguments("--headless", "--disable-gpu",
                            "--no-sandbox", "--remote-allow-origins=*", "--window-size=1920,1080");
                    System.out.println("Chrome launched in HEADLESS mode.");
                }
                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();

                if (HEADLESS) {
                    ffOptions.addArguments("--headless", "--disable-gpu",
                            "--no-sandbox", "--window-size=1920,1080");
                    System.out.println("Firefox launched in HEADLESS mode.");
                }
                return new FirefoxDriver(ffOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();

            default:
                throw new RuntimeException("❌ Invalid browser name: " + browserName);
        }
    }
}
