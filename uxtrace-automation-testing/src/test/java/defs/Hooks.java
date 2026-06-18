package defs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);

        // Bypass login - otomatis login sebelum setiap scenario
        driver.get("http://localhost:5173/login");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("lilo@gmail.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("12345678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/dashboard"));

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}