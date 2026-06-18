package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.DashboardLocators;
import utils.TestConfig;

import java.util.List;

/**
 * Page Object for the Dashboard page (/dashboard).
 *
 * Key design note — native <dialog> elements:
 *   Svelte renders ALL dialogs (add modal, delete dialog, detail dialog) in
 *   the DOM unconditionally. They are opened via showModal() which sets the
 *   HTML 'open' attribute. visibilityOfElementLocated() is therefore
 *   UNRELIABLE for dialogs — always use isDialogOpen() (JS attribute check).
 */
public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ── Navigation ─────────────────────────────────────────────────────────

    public void open() {
        driver.get(TestConfig.BASE_URL + "/dashboard");
        waitForGridReady();
    }

    private void waitForGridReady() {
        // Skeleton pulse divs disappear once panels are loaded
        wait.until(d -> d.findElements(By.cssSelector("div.animate-pulse")).isEmpty());
    }

    // ── TimeRangePicker ────────────────────────────────────────────────────

    public void selectRelativeTimeRange(String label) {
        click(DashboardLocators.TIME_PICKER__TOGGLE_BUTTON);
        waitVisible(DashboardLocators.TIME_PICKER__DROPDOWN);
        click(By.xpath(DashboardLocators.timePickerRelativeOption(label)));
        waitInvisible(DashboardLocators.TIME_PICKER__DROPDOWN);
        waitForGridReady();
    }

    // ── Panel grid queries ─────────────────────────────────────────────────

    public int getPanelCount() {
        return driver.findElements(DashboardLocators.GRID__PANEL_CARDS).size();
    }

    public boolean isPanelVisible(String title) {
        By locator = By.xpath(DashboardLocators.panelCardByTitle(title));
        List<WebElement> cards = driver.findElements(locator);
        return !cards.isEmpty() && cards.get(0).isDisplayed();
    }

    // ── Create panel flow ──────────────────────────────────────────────────

    public void clickAddMetrics() {
        click(DashboardLocators.GRID__ADD_METRICS_BUTTON);
        waitForAddModalOpen();
    }

    public void selectGraphType(String graphType) {
        click(By.xpath(DashboardLocators.graphTypeButton(graphType)));
    }

    public void fillQuery(String query) {
        WebElement textarea = waitVisible(DashboardLocators.ADD_MODAL__QUERY_TEXTAREA);
        // Clear via JS + input event first so Svelte resets its internal state,
        // then sendKeys fires native input events that Svelte's bind:value picks up.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                textarea
        );
        textarea.sendKeys(query);
    }

    public void clearQuery() {
        WebElement textarea = waitVisible(DashboardLocators.ADD_MODAL__QUERY_TEXTAREA);
        // Svelte uses bind:value so a plain .clear() doesn't trigger reactivity.
        // Set value to empty via JS then dispatch an 'input' event so Svelte
        // picks up the change and resets its internal query state.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                textarea
        );
    }

    public void clickCheckPreview() {
        click(DashboardLocators.ADD_MODAL__PREVIEW_BUTTON);

        // Wait for the "Cek Preview" button to become enabled again.
        // previewLoading=true disables it; when it flips back to false the API call
        // is done — whether result is empty, has data, or returned an error.
        wait.until(d -> {
            List<WebElement> btns = d.findElements(DashboardLocators.ADD_MODAL__PREVIEW_BUTTON);
            if (btns.isEmpty()) return false;
            WebElement btn = btns.get(0);
            // Button is disabled while loading (disabled attr or disabled property)
            Object disabled = ((JavascriptExecutor) d)
                    .executeScript("return arguments[0].disabled;", btn);
            return !Boolean.TRUE.equals(disabled);
        });
    }
    
    public void clickSavePanel() {
        click(DashboardLocators.ADD_MODAL__SAVE_BUTTON);
        waitForAddModalClosed();
        waitForGridReady();
    }

    public void clickUpdatePanel() {
        click(DashboardLocators.ADD_MODAL__UPDATE_BUTTON);
        waitForAddModalClosed();
        waitForGridReady();
    }

    // ── Update panel flow ──────────────────────────────────────────────────

    public void openUpdateModalForPanel(String title) {
        openPanelMenu(title);
        jsClick(DashboardLocators.PANEL_MENU__UPDATE_ITEM);
        waitForAddModalOpen();
    }

    // ── Delete panel flow ──────────────────────────────────────────────────

    public void openDeleteDialogForPanel(String title) {
        openPanelMenu(title);
        jsClick(DashboardLocators.PANEL_MENU__DELETE_ITEM);
        wait.until(d -> anyDialogOpen(DashboardLocators.DELETE_DIALOG__DIALOG));
    }

    public void confirmDelete() {
        // Count open delete dialogs before clicking — wait until that count drops.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long openBefore = (Long) js.executeScript(
                "var count = 0;" +
                        "document.querySelectorAll('dialog[aria-label=\"Delete panel modal\"]')" +
                        "  .forEach(function(d){ if(d.hasAttribute('open')) count++; });" +
                        "return count;"
        );

        // Click Hapus inside whichever delete dialog is currently open.
        js.executeScript(
                "var dialogs = document.querySelectorAll('dialog[aria-label=\"Delete panel modal\"]');" +
                        "for (var d of dialogs) {" +
                        "  if (d.hasAttribute('open')) {" +
                        "    var buttons = d.querySelectorAll('button');" +
                        "    for (var b of buttons) {" +
                        "      if (b.textContent.trim() === 'Hapus') { b.click(); break; }" +
                        "    }" +
                        "    break;" +
                        "  }" +
                        "}"
        );

        // Wait until open count is less than before.
        wait.until(d -> {
            Long openNow = (Long) ((JavascriptExecutor) d).executeScript(
                    "var count = 0;" +
                            "document.querySelectorAll('dialog[aria-label=\"Delete panel modal\"]')" +
                            "  .forEach(function(d){ if(d.hasAttribute('open')) count++; });" +
                            "return count;"
            );
            return openNow < openBefore;
        });

        waitForGridReady();
        sleepMillis(800);
    }

    // ── Detail dialog flow ─────────────────────────────────────────────────

    public void clickFirstPanel() {
        List<WebElement> cards = waitAllVisible(DashboardLocators.GRID__PANEL_CARDS);
        cards.get(0).click();
        wait.until(d -> anyDialogOpen(DashboardLocators.DETAIL_DIALOG__DIALOG));
        wait.until(d -> d.findElements(DashboardLocators.DETAIL_DIALOG__LOADING).isEmpty());
    }

    public void clickDetailRefresh() {
        click(DashboardLocators.DETAIL_DIALOG__REFRESH_BUTTON);
        sleepMillis(300);
        wait.until(d -> d.findElements(DashboardLocators.DETAIL_DIALOG__LOADING).isEmpty());
    }

    public void closeDetailDialog() {
        click(DashboardLocators.DETAIL_DIALOG__CLOSE_BUTTON);
        wait.until(d -> !anyDialogOpen(DashboardLocators.DETAIL_DIALOG__DIALOG));
    }

    // ── State checks ───────────────────────────────────────────────────────

    public boolean isDetailDialogOpen() {
        return anyDialogOpen(DashboardLocators.DETAIL_DIALOG__DIALOG);
    }

    public boolean isDetailDialogClosed() {
        return !anyDialogOpen(DashboardLocators.DETAIL_DIALOG__DIALOG);
    }

    public boolean isDetailTableVisible() {
        return isDisplayed(DashboardLocators.DETAIL_DIALOG__TABLE);
    }

    public boolean isAddModalOpen() {
        return anyDialogOpen(DashboardLocators.ADD_MODAL__DIALOG);
    }

    // ── Private helpers ────────────────────────────────────────────────────

    /**
     * Opens the three-dot context menu for the panel card matching title.
     * Uses JS click to avoid Svelte's stopPropagation on the window click
     * handler that would immediately close the menu if a normal click
     * triggers the window listener first.
     */
    private void openPanelMenu(String title) {
        WebElement card = waitVisible(By.xpath(DashboardLocators.panelCardByTitle(title)));
        WebElement menuBtn = card.findElement(By.cssSelector("button[aria-label='Panel options']"));
        jsClick(menuBtn);
        // Wait for the menu div to appear in the DOM
        waitVisible(By.cssSelector("div[role='menu']"));
    }

    /**
     * JS click by locator — resolves the element first, then clicks via JS.
     * Needed for elements behind Svelte's stopPropagation or partially
     * obscured by overlays.
     */
    private void jsClick(By locator) {
        WebElement el = waitVisible(locator);
        jsClick(el);
    }

    private void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private void waitForAddModalOpen() {
        wait.until(d -> anyDialogOpen(DashboardLocators.ADD_MODAL__DIALOG));
    }

    private void waitForAddModalClosed() {
        wait.until(d -> !anyDialogOpen(DashboardLocators.ADD_MODAL__DIALOG));
    }

    /**
     * Returns true if ANY element matching the locator has the HTML 'open'
     * attribute set. isDialogOpen() in BasePage only checks index 0, which
     * is always the first PanelCard's dialog — not the one that just opened.
     */
    private boolean anyDialogOpen(By locator) {
        List<WebElement> dialogs = driver.findElements(locator);
        if (dialogs.isEmpty()) return false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement dialog : dialogs) {
            Object open = js.executeScript("return arguments[0].hasAttribute('open');", dialog);
            if (Boolean.TRUE.equals(open)) return true;
        }
        return false;
    }
}