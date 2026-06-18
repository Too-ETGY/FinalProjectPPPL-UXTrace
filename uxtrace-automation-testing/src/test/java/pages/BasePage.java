package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import utils.TestConfig;

/**
 * Base class for all Page Objects. Holds the driver + wait and exposes
 * small, reusable interaction helpers so individual page classes stay
 * focused on page-specific behavior rather than raw Selenium boilerplate.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.DEFAULT_TIMEOUT_SECONDS));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> waitAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void waitInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until no elements matching the locator are present in the DOM at all
     * (stronger than "invisible" — handles cases where the skeleton rows are
     * removed/replaced rather than just hidden).
     */
    protected void waitAbsent(By locator) {
        wait.until(d -> d.findElements(locator).isEmpty());
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void typeText(By locator, String text) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected boolean isDisplayed(By locator) {
        List<WebElement> els = driver.findElements(locator);
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    protected void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Native <dialog> elements report isDisplayed() unreliably depending on
     * driver/browser combos when opened via showModal(). This checks the
     * "open" attribute directly via JS as a more reliable signal.
     */
    protected boolean isDialogOpen(By dialogLocator) {
        List<WebElement> dialogs = driver.findElements(dialogLocator);
        if (dialogs.isEmpty()) return false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeScript("return arguments[0].hasAttribute('open');", dialogs.get(0));
        return Boolean.TRUE.equals(result);
    }
}