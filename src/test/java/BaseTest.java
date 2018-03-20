import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Listeners(Listener.class)

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    protected WebDriver getDriver(String browser) {
        if (browser.equals("chrome")) {
            try {
                ChromeOptions options = new ChromeOptions();
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), (Capabilities) options);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SkipException("Unable to create RemoteWebDriver instance!");
            }
        }
        else if(browser.equals("firefox")) {
            try {
                FirefoxOptions ffoptions = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), (Capabilities) ffoptions);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SkipException("Unable to create RemoteWebDriver instance!");
            }
        }
        else if(browser.equals("mobile")) {
            try {
                Map<String, String> mobileEmulation = new HashMap<String, String>();
                mobileEmulation.put("deviceName", "Android");

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                WebDriver driver = new ChromeDriver(chromeOptions);
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), (Capabilities) chromeOptions);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SkipException("Unable to create RemoteWebDriver instance!");
            }
        }

    @AfterClass
    protected void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void AssertShopVersion() {System.out.println("AssertShopVersion execution");}

    @Test
    public void CreationOrder() {System.out.println("CreationOrder execution");}
}
