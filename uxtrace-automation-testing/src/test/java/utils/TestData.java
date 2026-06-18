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
    public static final String COOKIE_TOKEN_VALUE = "105%7C1uUT3KW8H4JEvZ5h8xKsNEPIPOw9YgX3ZyTcmq3Ib9b96099";
    public static final String COOKIE_USER_EMAIL_VALUE = "bahlil%40gmail.com";
    public static final String COOKIE_USER_NAME_VALUE = "Bahlil";

    // ── Search keywords used across scenarios ───────────────
    // Scenario: filter by page/button name
    public static final String SEARCH_KEYWORD_PAGE_OR_BUTTON = "Dashboard";
    // Scenario: filter by event name
    public static final String SEARCH_KEYWORD_EVENT_NAME = "button_click";
    // Scenario: combined time range + keyword
    public static final String SEARCH_KEYWORD_COMBINED = "login";

    // ── Time range labels ────────────────────────────────────
    public static final String TIME_RANGE_LABEL_LAST_7_DAYS = "Last 5 days";

    // ── Modal-related ────────────────────────────────────────
    // Gherkin passes string params like "Detail" / "Tutup" but they aren't real
    // labels in the DOM — kept here only for traceability/logging, not for locating.
    public static final String MODAL_OPEN_TRIGGER_LABEL = "Detail";
    public static final String MODAL_CLOSE_TRIGGER_LABEL = "Tutup";

    // ── Dashboard / Panel ────────────────────────────────────────────────
    /** Query used when creating a new panel in all three chart-type scenarios. */
    public static final String PANEL_CREATE_QUERY_LINE =
            "SELECT * FROM events WHERE event_name = 'kur_page_stay' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Line'";

    public static final String PANEL_CREATE_QUERY_BAR =
            "SELECT * FROM events WHERE event_name = 'kur_page_stay' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Bar'";

    public static final String PANEL_CREATE_QUERY_PIE =
            "SELECT * FROM events WHERE event_name = 'kur_page_stay' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Pie'";

    /** Expected panel card titles after creation (derived from query TITLE clause). */
    public static final String PANEL_TITLE_LINE = "Page Stay Line";
    public static final String PANEL_TITLE_BAR  = "Page Stay Bar";
    public static final String PANEL_TITLE_PIE  = "Page Stay Pie";

    /** One shared update query used for all three chart types. */
    public static final String PANEL_UPDATE_QUERY_LINE =
            "SELECT * FROM events WHERE event_name = 'kur_button_click' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Line Diperbarui'";

    public static final String PANEL_UPDATE_QUERY_BAR =
            "SELECT * FROM events WHERE event_name = 'kur_button_click' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Bar Diperbarui'";

    public static final String PANEL_UPDATE_QUERY_PIE =
            "SELECT * FROM events WHERE event_name = 'kur_button_click' AND btm IN ('a345.b432.c120.d0') TITLE 'Page Stay Pie Diperbarui'";


    /** Expected card title after the update. */
    public static final String PANEL_UPDATED_TITLE_LINE = "Page Stay Line Diperbarui";
    public static final String PANEL_UPDATED_TITLE_BAR = "Page Stay Bar Diperbarui";
    public static final String PANEL_UPDATED_TITLE_PIE = "Page Stay Pie Diperbarui";

    /** Label of the relative time range option to select. Must match RELATIVE_OPTIONS in the app. */
    public static final String TIME_RANGE_LABEL = "Last 7 days";

    // ── Graph type labels (match the button text in AddMetricModal) ───────
    public static final String GRAPH_TYPE_LINE = "Line";
    public static final String GRAPH_TYPE_BAR  = "Bar";
    public static final String GRAPH_TYPE_PIE  = "Pie";

    private TestData() {
        // no instances
    }
}