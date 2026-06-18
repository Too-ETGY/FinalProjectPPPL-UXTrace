package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import utils.*;

public class QueryLogPage extends BasePage {

    public QueryLogPage(WebDriver driver) {
        super(driver);
    }

    // ── Navigation ───────────────────────────────────────────
    public void open() {
        driver.get(TestConfig.QUERY_LOG_URL);
        waitForTableToLoad();
    }

    // ── Loading state ───────────────────────────────────────
    /**
     * Waits for the skeleton/loading rows to disappear from the DOM.
     * Industry-standard pattern: wait for the loading indicator to detach
     * rather than polling a class on elements that get re-rendered.
     */
    public void waitForTableToLoad() {
        waitAbsent(Locators.LOADING_SKELETON_ROWS);
    }

    // ── Search ───────────────────────────────────────────────
    public void searchFor(String keyword) {
        typeText(Locators.SEARCH_INPUT, keyword);
        // input is debounced 400ms before the request fires
        sleepMillis(TestConfig.SEARCH_DEBOUNCE_MILLIS + 100);
        waitForTableToLoad();
    }

    public void clearSearch() {
        click(Locators.SEARCH_CLEAR_BUTTON);
        waitForTableToLoad();
    }

    public boolean isSearchCleared() {
        WebElement input = waitVisible(Locators.SEARCH_INPUT);
        return input.getAttribute("value") == null || input.getAttribute("value").isEmpty();
    }

    // ── Time range ───────────────────────────────────────────
    public void selectRelativeTimeRange(String label) {
        click(Locators.TIME_RANGE_TOGGLE_BUTTON);
        waitVisible(Locators.TIME_RANGE_DROPDOWN_PANEL);
        click(Locators.relativeOptionButton(label));
        waitForTableToLoad();
    }

    // ── Table reading / assertions ────────────────────────────
    public List<WebElement> getVisibleRows() {
        waitForTableToLoad();
        if (isEmptyStateShown()) {
            return List.of();
        }
        return driver.findElements(Locators.TABLE_BODY_ROWS);
    }

    public boolean isEmptyStateShown() {
        return isPresent(Locators.EMPTY_STATE_CELL);
    }

    public String getRowEventName(WebElement row) {
        return getCellText(row, Locators.COL_EVENT_NAME);
    }

    public String getRowPageOrButtonName(WebElement row) {
        return getCellText(row, Locators.COL_PAGE_OR_BUTTON_NAME);
    }

    private String getCellText(WebElement row, int columnIndex) {
        List<WebElement> cells = row.findElements(By.tagName("td"));
        if (columnIndex >= cells.size()) return "";
        return cells.get(columnIndex).getText();
    }

    /**
     * Returns true if every currently visible row contains the keyword
     * (case-insensitive) in either the Event Name or Page/Button Name column —
     * mirrors what the app itself highlights via highlight() in +page.svelte.
     */
    public boolean allVisibleRowsContainKeyword(String keyword) {
        List<WebElement> rows = getVisibleRows();
        if (rows.isEmpty()) {
            // No rows is acceptable only if the empty-state message references the search term;
            // caller should check isEmptyStateShown() separately if that distinction matters.
            return true;
        }
        String needle = keyword.toLowerCase();
        return rows.stream().allMatch(row -> {
            String combined = (getRowEventName(row) + " " + getRowPageOrButtonName(row)).toLowerCase();
            return combined.contains(needle);
        });
    }

    public int getVisibleRowCount() {
        return getVisibleRows().size();
    }

    // ── Detail modal ───────────────────────────────────────────
    /**
     * Clicks the first row in the table to open its detail modal.
     * There is no dedicated "Detail" button/icon in the markup — the whole
     * row is the click target — so the Gherkin string param is not used here.
     */
    public void openFirstRowDetail() {
        List<WebElement> rows = getVisibleRows();
        if (rows.isEmpty()) {
            throw new IllegalStateException("Cannot open row detail: no rows are currently visible.");
        }
        rows.get(0).click();
        waitVisible(Locators.MODAL_DIALOG);
        wait.until(d -> isDialogOpen(Locators.MODAL_DIALOG));
    }

    public boolean isDetailModalOpen() {
        return isDialogOpen(Locators.MODAL_DIALOG);
    }

    /**
     * Closes the modal via its close control (aria-label="Close").
     * The Gherkin string param (e.g. "Tutup") is not used to locate anything,
     * since no element in the DOM actually carries that label.
     */
    public void closeDetailModal() {
        click(Locators.MODAL_CLOSE_BUTTON);
        wait.until(d -> !isDialogOpen(Locators.MODAL_DIALOG));
    }

    public List<String> getModalInfoBoxTexts() {
        return driver.findElements(Locators.MODAL_INFO_BOXES)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}