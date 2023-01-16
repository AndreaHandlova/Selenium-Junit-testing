import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Kalkulacka {
    private WebDriver driver;
    private final String BASE_URL = "https://furbo.sk/playground/kalkulacka.php";


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver 4");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);

    }
    @Test
    public void testSum() {
        checkSum("10", "99", "count", "109");
        checkSum("85", "30", "count", "115");
        checkSum("200", "26", "count", "226");
        checkSum("7", "30", "count", "37");
        checkSum("2", "5", "count", "7");
        checkSum("11", "11", "count", "22");
        checkSum("1008", "2574", "count", "3582");
        checkSum("2", "0", "count", "2");
    }
     @Test
    public void testDeduct() {
        checkDeduct("10", "99", "deduct", "-89");
        checkDeduct("8", "2", "deduct", "6");
        checkDeduct("308", "108", "deduct", "200");
    }
    @Test
    public void testReset() {
        enterInputs("5", "6");
        driver.findElement(By.id("count")).click();
        driver.findElement(By.id("reset")).click();

        Assert.assertTrue(driver.findElement(By.id("firstInput")).getAttribute("value").isEmpty());
        Assert.assertTrue(driver.findElement(By.id("secondInput")).getAttribute("value").isEmpty());
        Assert.assertTrue(driver.findElement(By.id("result")).getText().isEmpty());


    }
    @Test
    public void testInvalidInputs() { // vyplnění bludu nebo je input prázdný
        enterInputs("", "blud2");
        driver.findElement(By.id("count")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//div[input[@id='firstInput']]"))
                .getAttribute("class")
                .contains("has-error"));
        Assert.assertTrue(driver.findElement(By.xpath("//div[input[@id='secondInput']]"))
                .getAttribute("class")
                .contains("has-error"));


    }


    @After
    public void tearDown()  {
        driver.close();
        driver.quit();
    }
    private void checkSum(String x, String x1, String count, String expected) {
        enterInputs(x, x1);
        driver.findElement(By.id(count)).click();
        Assert.assertEquals(expected, driver.findElement(By.id("result")).getText());
    }
    private void enterInputs(String x, String x1) {
        driver.findElement(By.id("firstInput")).clear();
        driver.findElement(By.id("firstInput")).sendKeys(x);
        driver.findElement(By.id("secondInput")).clear();
        driver.findElement(By.id("secondInput")).sendKeys(x1);
    }
    private void checkDeduct(String x, String x1, String deduct, String expected) {
        enterInputs(x, x1);
        driver.findElement(By.id(deduct)).click();
        Assert.assertEquals(expected, driver.findElement(By.id("result")).getText());
    }
}
