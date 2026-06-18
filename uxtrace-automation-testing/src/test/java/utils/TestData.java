package utils;

/**
 * Holds test input values and expected/auth values, kept separate from
 * locators and page logic so they can be tweaked without touching test flow.
 *
 * NOTE: cookie values below are placeholders. Replace with real values
 * (or wire to env vars / a secrets file) before running against a real session.
 */
public class TestData {

    // ── Auth cookies (injected directly, no real login flow) ───
    public static final String COOKIE_TOKEN_NAME = "token";
    public static final String COOKIE_USER_EMAIL_NAME = "user_email";
    public static final String COOKIE_USER_NAME_NAME = "user_name";

    // token is JSON, plain values for name/email — replace with real seed values
    // public static final String COOKIE_TOKEN_VALUE = "{\"sub\":\"test-user-id\",\"role\":\"admin\"}";
    public static final String COOKIE_TOKEN_VALUE = "29%7CvXxYpVn9ltQelRvMN2qtJ67ghz64RUeVkIgoV9G8b0dc55d9";
    public static final String COOKIE_USER_EMAIL_VALUE = "thegarradit%40gmail.com";
    public static final String COOKIE_USER_NAME_VALUE = "Tegar";

    // ── Search keywords used across scenarios ───────────────
    // Scenario: filter by page/button name
    public static final String SEARCH_KEYWORD_PAGE_OR_BUTTON = "Dashboard";
    // Scenario: filter by event name
    public static final String SEARCH_KEYWORD_EVENT_NAME = "button_click";
    // Scenario: combined time range + keyword
    public static final String SEARCH_KEYWORD_COMBINED = "login";

    // ── Time range labels ────────────────────────────────────
    public static final String TIME_RANGE_LABEL_LAST_7_DAYS = "Last 7 days";

    // ── Modal-related ────────────────────────────────────────
    // Gherkin passes string params like "Detail" / "Tutup" but they aren't real
    // labels in the DOM — kept here only for traceability/logging, not for locating.
    public static final String MODAL_OPEN_TRIGGER_LABEL = "Detail";
    public static final String MODAL_CLOSE_TRIGGER_LABEL = "Tutup";

    private TestData() {
        // no instances
    }
}