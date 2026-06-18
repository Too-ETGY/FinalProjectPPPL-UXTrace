package defs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import utils.*;
/**
 * Driver lifecycle + auth cookie seeding.
 *
 * IMPORTANT: this intentionally does NOT perform a real login. It only
 * injects the token/user_email/user_name cookies directly into the browser
 * session before each scenario, which is the standard way to skip a slow
 * UI login flow in E2E tests while still exercising an authenticated page.
 */
public class Hooks {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Uncomment for CI / headless runs:
        // options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        injectAuthCookies();
    }

    /**
     * Selenium requires the browser to already be on the target domain
     * before cookies can be added, so we hit the base URL first, seed the
     * cookies, then let the actual page object navigate to /query.
     */
    private void injectAuthCookies() {
        driver.get(TestConfig.BASE_URL);

        driver.manage().addCookie(new Cookie(
                TestData.COOKIE_TOKEN_NAME,
                TestData.COOKIE_TOKEN_VALUE
        ));
        driver.manage().addCookie(new Cookie(
                TestData.COOKIE_USER_EMAIL_NAME,
                TestData.COOKIE_USER_EMAIL_VALUE
        ));
        driver.manage().addCookie(new Cookie(
                TestData.COOKIE_USER_NAME_NAME,
                TestData.COOKIE_USER_NAME_VALUE
        ));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}