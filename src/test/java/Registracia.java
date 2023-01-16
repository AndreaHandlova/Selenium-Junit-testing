import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;



public class Registracia {
    private WebDriver driver;
    private final String BASE_URL = "https://furbo.sk/playground/registracia.php";
    private String validEmail = "Andrea123@obecny123.cz";
    private String validName = "Andrea";
    private String validSurname = "Handlová";

    @Before
        public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver 4");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }
    @Test
    public void testMissingAllInputs() throws InterruptedException, IOException {
        driver.findElement(By.id("checkbox")).click();

        driver.findElement(By.xpath("//button[@type = 'submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-danger')]")).isDisplayed());
    }
    @Test
    public void testMissingPassword() {
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("name")).sendKeys(validName);
        driver.findElement(By.name("surname")).sendKeys(validSurname);
        driver.findElement(By.id("checkbox")).click();

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-danger')]")).isDisplayed());
    }
    @Test
    public void testMismatchedPassword() {
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("name")).sendKeys(validName);
        driver.findElement(By.name("surname")).sendKeys(validSurname);
        driver.findElement(By.id("checkbox")).click();
        driver.findElement(By.name("password")).sendKeys("heslojedna");
        driver.findElement(By.name("password-repeat")).sendKeys("heslodva");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-danger')]")).isDisplayed());
    }
    @Test
    public void testMissingRobotCheckbox() {
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("name")).sendKeys(validName);
        driver.findElement(By.name("surname")).sendKeys(validSurname);

        driver.findElement(By.name("password")).sendKeys("stejneheslo");
        driver.findElement(By.name("password-repeat")).sendKeys("stejneheslo");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-danger')]")).isDisplayed());
    }
    @Test
    public void testSuccessfullRegistration() {
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("name")).sendKeys(validName);
        driver.findElement(By.name("surname")).sendKeys(validSurname);
        driver.findElement(By.id("checkbox")).click();
        driver.findElement(By.name("password")).sendKeys("stejneheslo");
        driver.findElement(By.name("password-repeat")).sendKeys("stejneheslo");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).isDisplayed());
    }
    @Test
    public void testInputErrorBorder() {
        String expectedClass = "has-error";
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        List<WebElement> formDivs = driver.findElements(By.xpath("//div[input]"));

        for (WebElement formDiv : formDivs) {
            Assert.assertTrue(formDiv.getAttribute("class").contains(expectedClass));
        }
        Assert.assertTrue(driver.findElement(By.xpath("//div[label[input[@id='checkbox']]]"))
                .getAttribute("class")
                .contains(expectedClass));


    }

    @After
    public void tearDown()  {
        driver.close();
        driver.quit();
    }
}

