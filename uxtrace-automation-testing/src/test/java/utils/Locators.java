package utils;

import org.openqa.selenium.By;

/**
 * Centralized locators for the Query Log page (+page.svelte),
 * TimeRangePicker.svelte, and QueryLogModal.svelte.
 *
 * There are no data-testid attributes in the source, so these rely on
 * placeholders, aria-labels, and structural selectors. If the markup
 * changes, this is the only file that should need updating.
 */
public class Locators {

    // ── Search ───────────────────────────────────────────────
    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Cari...']");
    public static final By SEARCH_CLEAR_BUTTON = By.cssSelector("button[aria-label='Clear search']");

    // ── Table ────────────────────────────────────────────────
    public static final By TABLE = By.cssSelector("table");
    public static final By TABLE_BODY_ROWS = By.cssSelector("table tbody tr");
    public static final By LOADING_SKELETON_ROWS = By.cssSelector("table tbody tr.animate-pulse");
    public static final By EMPTY_STATE_CELL = By.cssSelector("table tbody td[colspan='6']");

    // Column order in the table (0-indexed), used when reading a row's <td> cells:
    // 0 = Timestamp, 1 = Event Name, 2 = Status, 3 = Page/Button Name, 4 = Browser+Version, 5 = IP Address
    public static final int COL_TIMESTAMP = 0;
    public static final int COL_EVENT_NAME = 1;
    public static final int COL_STATUS = 2;
    public static final int COL_PAGE_OR_BUTTON_NAME = 3;
    public static final int COL_BROWSER = 4;
    public static final int COL_IP_ADDRESS = 5;

    // ── Status filter (header button) ───────────────────────
    public static final By STATUS_FILTER_HEADER_BUTTON = By.xpath("//button[@title='Klik untuk filter status']");

    // ── Pagination ───────────────────────────────────────────
    public static final By PAGINATION_PREV_BUTTON = By.cssSelector("button[aria-label='Previous page']");
    public static final By PAGINATION_NEXT_BUTTON = By.cssSelector("button[aria-label='Next page']");
    public static final By PAGINATION_TOTAL_LABEL = By.xpath("//p[contains(@class,'text-gray-text')][contains(text(),'total log') or contains(text(),'Memuat')]");

    // ── Time Range Picker ────────────────────────────────────
    // Main toggle button that opens the dropdown (shows the active label, e.g. "Last 7 days")
    public static final By TIME_RANGE_TOGGLE_BUTTON = By.xpath("//button[.//span[contains(@class,'truncate')]]");
    // Dropdown panel becomes visible only when open
    public static final By TIME_RANGE_DROPDOWN_PANEL = By.xpath("//p[text()='Relative time range']/ancestor::div[contains(@class,'absolute')]");
    // Relative option buttons live under the "Relative time range" section; locate by visible text
    public static By relativeOptionButton(String label) {
        return By.xpath("//p[text()='Relative time range']/following-sibling::div//button[normalize-space(text())='" + label + "']");
    }
    // Absolute range inputs (kept here for future absolute-range scenario)
    public static final By ABSOLUTE_START_INPUT = By.id("start-time");
    public static final By ABSOLUTE_END_INPUT = By.id("end-time");
    public static final By ABSOLUTE_APPLY_BUTTON = By.xpath("//button[normalize-space(text())='Apply time range']");

    // ── Detail Modal (QueryLogModal.svelte) ──────────────────
    public static final By MODAL_DIALOG = By.cssSelector("dialog");
    public static final By MODAL_CLOSE_BUTTON = By.cssSelector("dialog button[aria-label='Close']");
    public static final By MODAL_TITLE = By.xpath("//dialog//h2[text()='Detail Query']");
    // Each info box inside the modal grid; used to verify highlighted/contained search text
    public static final By MODAL_INFO_BOXES = By.cssSelector("dialog .grid > div");
    public static final By MODAL_EVENT_PROPERTIES_BLOCK = By.cssSelector("dialog pre");

    private Locators() {
        // no instances
    }
}