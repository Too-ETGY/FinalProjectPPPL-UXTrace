package defs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestData;
import java.time.Duration;
import java.util.List;

public class Hooks {

    public static WebDriver driver;
    @Before(order = 1)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            driver.get(TestData.BASE_URL + "/login");
            System.out.println("Membuka halaman login...");
            System.out.println("Current URL: " + driver.getCurrentUrl());

            wait.until(ExpectedConditions.urlContains("/login"));
            Thread.sleep(2000);

            List<WebElement> allInputs = driver.findElements(By.tagName("input"));
            System.out.println("Jumlah input ditemukan: " + allInputs.size());
            for (WebElement input : allInputs) {
                System.out.println("Input → type: " + input.getAttribute("type")
                        + ", name: " + input.getAttribute("name")
                        + ", placeholder: " + input.getAttribute("placeholder")
                        + ", id: " + input.getAttribute("id"));
            }

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='email' or @type='text'][1]")
            ));
            emailField.clear();
            emailField.sendKeys("gweh@mail.com");
            System.out.println("Mengisi email berhasil");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='password']")
            ));
            passwordField.clear();
            passwordField.sendKeys("asdfghjkl");
            System.out.println("Mengisi password berhasil");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit'] | //button[contains(text(), 'Login')] | //button[contains(text(), 'Masuk')] | //button[contains(text(), 'Sign In')]")
            ));
            System.out.println("Tombol login ditemukan: " + loginButton.getText());
            loginButton.click();
            System.out.println("Klik tombol login");

            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("/login")
            ));
            System.out.println("Login berhasil! Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("Error saat login: " + e.getMessage());
            System.out.println("Current URL saat error: " + driver.getCurrentUrl());
            System.out.println("Page source (500 char): " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
            e.printStackTrace();
        }
    }
}