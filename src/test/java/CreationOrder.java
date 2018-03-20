import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreationOrder implements BaseTest {
    @Test
    public void CreationOrder () {
        //1. Перейти на главную страницу магазина
        driver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //2. Перейти к списку всех товаров воспользовавшись ссылкой «Все товары»
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-info")));
        driver.findElement(By.cssSelector("#content > section > a")).click();

        //3. Открыть случайный товар из отображаемого списка
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#js-product-list > div > article > div > a")));
        driver.findElement(By.cssSelector("#js-product-list > div > article > div > a")).click();
        //создание переменных для проверки в п.6 и п. 13
        String expectedGoodName = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/nav/ol/li[5]/a/span")).getText();
        String expectedGoodPrice = driver.findElement(By.className("current-price")).getText();
        String url = driver.getCurrentUrl();
        driver.findElement(By.xpath(".//*[text()='Подробнее о товаре']/..")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#product-details > div.product-quantities > span")));
        String oldgoodQuantity = driver.findElement(By.cssSelector("#product-details > div.product-quantities > span")).getText().split(" ")[0];
        Integer oldGoodQuantity = Integer.valueOf(oldgoodQuantity);

        //4. Добавить товар в корзину
        driver.findElement(By.xpath("//button[@class='btn btn-primary add-to-cart']")).click();

        //5. В сплывающем окне нажать на кнопку «Перейти к оформлению» для перехода в корзину пользователя
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='btn btn-primary']")));
        driver.findElement(By.xpath("//a[@class='btn btn-primary']")).click();

        //6. Проверить, что в корзине отображается одна позиция, название и цена добавленного товара соответствует значениям, которые отображались на странице товара
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='product-quantity-spin']")));
        String quantity = driver.findElement(By.xpath("//span[@class='label js-subtotal']")).getText();
        assertEquals("Quantity of product is incorrect", quantity.equals("1 шт."));
        System.out.println("Expected product quantity: 1 шт. Actual product quantity: " + quantity);

        WebElement actualName = driver.findElement(By.className("label"));
        assertEquals("Name of product is incorrect", actualName.getText().equals(expectedGoodName));
        System.out.println("Expected product name: " + expectedGoodName + " Actual product name: "  + actualName.getText());

        WebElement actualPrice = driver.findElement(By.className("value"));
        assertEquals("Price of product is incorrect", actualPrice.getText().equals(expectedGoodPrice));
        System.out.println("Expected product price: " + expectedGoodPrice + " Actual product price: "  + actualPrice.getText());

        //7. Нажать на кнопку «Оформление заказа»
        driver.findElement(By.xpath(".//*[text()='Оформление заказа']/.")).click();

        //8. Заполнить поля Имя, Фамилия, E-mail (должно быть уникальным) и перейти к следующему шагу оформления заказа.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        driver.findElement(By.name("firstname")).sendKeys("Nadiia");
        driver.findElement(By.name("lastname")).sendKeys("Starikova");
        driver.findElement(By.name("email")).sendKeys("nadiia.garkovych@gmail.com");
        driver.findElement(By.name("continue")).click();

        //9. Указать адрес, почтовый индекс, город доставки. Перейти к следующему шагу.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("address1")));
        driver.findElement(By.name("address1")).sendKeys("Rayduzhna,3b");
        driver.findElement(By.name("postcode")).sendKeys("02218");
        driver.findElement(By.name("city")).sendKeys("Kyiv");
        driver.findElement(By.name("confirm-addresses")).click();

        //10. Оставить настройки доставки без изменений, перейти к шагу оплаты заказа.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmDeliveryOption")));
        driver.findElement(By.name("confirmDeliveryOption")).click();

        //11. Выбрать любой способ оплаты. Отметить опцию «Я ознакомлен(а) и согласен(на) с Условиями обслуживания.» Оформить заказ.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text()='Оплата чеком']/..")));
        driver.findElement(By.xpath(".//*[text()='Оплата чеком']/..")).click();
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        driver.findElement(By.id("payment-confirmation")).click();

        //12. В открывшемся окне подтверждения заказа проверить следующие значения:
        // Пользователю отображается сообщение «Ваш заказ подтвержден»
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-xs-2")));
        assertTrue("Acceptance message is absent on the page.", driver.findElements(By.className("col-md-12")).size() > 0);
        System.out.println("Acceptance message is present on the page");
        // В деталях заказа отображается одна позиция, название и цена товара соответствует значениям, которые отображались на странице товара.
        WebElement value = driver.findElement(By.className("col-xs-2"));
        assertEquals("Quantity of product is incorrect", value.getText().equals("1"));
        System.out.println("Expected product quantity: 1. Actual product quantity: "  + value.getText());

        WebElement goodName = driver.findElement(By.cssSelector("#order-items > div > div > div.col-sm-4.col-xs-9.details > span"));
        String actualGoodName = goodName.getText().split(" -")[0];
        assertEquals("Name of product is incorrect", actualGoodName.equals(expectedGoodName));
        System.out.println("Expected product name: " + expectedGoodName + " Actual product name: "  + actualGoodName);

        WebElement actualGoodPrice = driver.findElement(By.xpath("//*[@id=\"order-items\"]/div/div/div[3]/div/div[1]"));
        assertEquals("Price of product is incorrect", actualGoodPrice.getText().equals(expectedGoodPrice));
        System.out.println("Expected product price: " + expectedGoodPrice + " Actual product price: "  + actualGoodPrice.getText());

        //13. Вернуться на страницу товара и проверить изменения количества товара на единицу.
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text()='Подробнее о товаре']/..")));
        driver.findElement(By.xpath(".//*[text()='Подробнее о товаре']/..")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#product-details > div.product-quantities > span")));
        String newgoodQuantity = driver.findElement(By.cssSelector("#product-details > div.product-quantities > span")).getText().split(" ")[0];
        Integer newGoodQuantity = Integer.valueOf(newgoodQuantity);
        assertEquals("Product quantity is incorrect", (oldGoodQuantity - newGoodQuantity) == 1);
        System.out.println("Product quantity decreased by one. Old product quantity: " + oldgoodQuantity + " New product quantity: " + newgoodQuantity);
    }
}
