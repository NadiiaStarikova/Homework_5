import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssertShopVersion implements BaseTest {

    @Test
    public void AssertShopVersion() {
        WebDriver driver = new ChromeDriver();
        //Переход на главную страницу магазина -> Все товары
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_mobile_cart")));

        //Проверка мобильной версии сайта
        Assert.assertTrue("Desktop website version is on mobile device.", driver.findElements(By.id("_mobile_cart")).size() > 0);
        System.out.println("Mobile website version is on mobile device.");
    }
}
