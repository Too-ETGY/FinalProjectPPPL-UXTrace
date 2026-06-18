package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage {

    private final WebDriverWait wait;

    private final By nameField = By.cssSelector("input[name='name'], input[placeholder*='Nama']");
    private final By emailField = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordField = By.cssSelector("input[name='password'], input[id='password']");
    private final By confirmPasswordField = By.cssSelector("input[name='password_confirmation'], input[id='password_confirmation']");
    private final By registerButton = By.cssSelector("button[type='submit']");

    public RegisterPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToRegister() {
        driver.get("http://localhost:5173/register");
    }

    public void register(String name, String email, String password, String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordField)).sendKeys(confirmPassword);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }
}