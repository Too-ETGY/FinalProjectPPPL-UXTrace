package pages;

import defs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ConsolePage extends BasePage {

    private final WebDriverWait wait;

    private final By searchInput = By.cssSelector("input[placeholder='Cari berdasarkan event name...']");
    private final By eventListItems = By.xpath("//div[@role='button'][@tabindex='0'][contains(@class,'px-4')]");
    private final By eventDetailPanel = By.cssSelector("pre.bg-secondary\\/50");
    private final By timeFilterButton = By.xpath("//button[contains(@class,'mx-1.5') and contains(@class,'h-8')]");
    private final By startTimeInput = By.id("start-time");
    private final By endTimeInput = By.id("end-time");
    private final By applyTimeRangeButton = By.xpath("//button[text()='Apply time range']");
    private final By removeFilterButton = By.cssSelector("button[aria-label='Remove filter']");
    private final By eventDropdownOptions = By.xpath("//button[contains(@class,'w-full flex items-center gap-2.5')]");

    public ConsolePage(WebDriver driver) {
            super(driver);
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public void openSearchDropdown() {
            WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
            search.click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void selectEventFromDropdown(String eventName) {
        openSearchDropdown();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'w-full') and contains(., '" + eventName + "')]")
        ));
        option.click();
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        Hooks.driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void selectMultipleEventsFromDropdown(String... eventNames) {
        openSearchDropdown();
        for (String eventName : eventNames) {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'w-full') and contains(., '" + eventName + "')]")
            ));
            option.click();
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Hooks.driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public List<WebElement> getEventListItems() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(eventListItems));
    }

    public void clickFirstEvent() {
        List<WebElement> items = getEventListItems();
        if (!items.isEmpty()) {
            items.get(0).click();
        }
    }

    public boolean isDetailPanelVisible() {
        try {
            WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(eventDetailPanel));
            return panel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openTimeFilterDropdown() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(timeFilterButton));
        btn.click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void setAbsoluteTimeRange(String startTime, String endTime) {
        WebElement start = wait.until(ExpectedConditions.elementToBeClickable(startTimeInput));
        WebElement end = wait.until(ExpectedConditions.elementToBeClickable(endTimeInput));

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]", start, startTime
        );
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]", end, endTime
        );
    }

    public void applyTimeRange() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(applyTimeRangeButton));
        btn.click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        Hooks.driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void removeFirstFilter() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(removeFilterButton));
        btn.click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}