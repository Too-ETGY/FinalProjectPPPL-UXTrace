package utils;

import org.openqa.selenium.By;

/**
 * All locators for the Dashboard page, AddMetricModal, PanelCard,
 * DeleteDialog, DetailDialog, and TimeRangePicker.
 *
 * Naming convention:
 *   SECTION__ELEMENT
 *
 * Selector strategy priority (matches the Svelte source):
 *   1. aria-label / role  — most stable
 *   2. button text        — readable and stable
 *   3. CSS class fragment — only when no better anchor exists
 */
public class DashboardLocators {

    private DashboardLocators() {}

    // ── Grid / Panel cards ──────────────────────────────────────────────────

    /** The dashed "Add more metrics" card/button in the grid. */
    public static final By GRID__ADD_METRICS_BUTTON =
            By.cssSelector("button[aria-label='Add more metrics']");

    /**
     * All rendered PanelCard containers.
     * Targets the white rounded card divs that hold real panels
     * (not the skeleton or the add-button card).
     */
    public static final By GRID__PANEL_CARDS =
            By.cssSelector("div.bg-white.rounded-2xl.border.border-border.shadow-sm");

    /**
     * A panel card whose title (the small uppercase <p>) matches exactly.
     * Usage: By.xpath(DashboardLocators.panelCardByTitle("My Title"))
     */
    public static String panelCardByTitle(String title) {
        return String.format(
                "//div[contains(@class,'rounded-2xl') and contains(@class,'shadow-sm')]" +
                        "//p[contains(@class,'uppercase') and normalize-space(text())='%s']" +
                        "/ancestor::div[contains(@class,'rounded-2xl')]",
                title
        );
    }

    // ── Refresh / last-updated (topbar) ────────────────────────────────────

    public static final By TOPBAR__REFRESH_BUTTON =
            By.cssSelector("button[aria-label='Refresh data']");

    public static final By TOPBAR__LAST_UPDATED_LABEL =
            By.cssSelector("span.text-\\[11px\\].text-gray-text");

    // ── TimeRangePicker ────────────────────────────────────────────────────

    /** The main toggle button that opens the picker dropdown. */
    public static final By TIME_PICKER__TOGGLE_BUTTON =
            By.cssSelector("button.h-8.px-3\\.5");

    /** The dropdown panel (visible only when open=true). */
    public static final By TIME_PICKER__DROPDOWN =
            By.cssSelector("div[style*='min-width: 520px']");

    /**
     * A relative-option button inside the open dropdown, matched by exact label text.
     * Usage: By.xpath(DashboardLocators.timePickerRelativeOption("Last 7 days"))
     */
    public static String timePickerRelativeOption(String label) {
        return String.format(
                "//div[contains(@style,'min-width: 520px')]" +
                        "//button[normalize-space(text())='%s']",
                label
        );
    }

    // ── PanelCard — three-dot menu ─────────────────────────────────────────

    /** Three-dot options button on any panel card. */
    public static final By PANEL_MENU__DOTS_BUTTON =
            By.cssSelector("button[aria-label='Panel options']");

    /** "Update Panel" item inside the open context menu. */
    public static final By PANEL_MENU__UPDATE_ITEM =
            By.xpath("//div[@role='menu']//button[normalize-space(.)='Update Panel']");

    /** "Delete Panel" item inside the open context menu. */
    public static final By PANEL_MENU__DELETE_ITEM =
            By.xpath("//div[@role='menu']//button[normalize-space(.)='Delete Panel']");

    // ── AddMetricModal (create + update) ──────────────────────────────────

    /**
     * The AddMetricModal <dialog> — aria-label added to Svelte source.
     * Always present in DOM per card; check open state via isDialogOpen().
     */
    public static final By ADD_MODAL__DIALOG =
            By.cssSelector("dialog[aria-label='Panel metric modal']");

    /** Header title text — used to confirm the modal opened. */
    public static final By ADD_MODAL__HEADER_TITLE =
            By.cssSelector("dialog[aria-label='Panel metric modal'] h2");

    /** Query textarea — scoped to the open add modal dialog to avoid matching closed ones. */
    public static final By ADD_MODAL__QUERY_TEXTAREA =
            By.cssSelector("dialog[aria-label='Panel metric modal'][open] #query-input");

    /** "Cek Preview" button — contains SVG spinner when loading, use normalize-space(.). */
    public static final By ADD_MODAL__PREVIEW_BUTTON =
            By.cssSelector("dialog[aria-label='Panel metric modal'][open] button.bg-primary");

    /** Preview chart container — scoped to open modal. */
    public static final By ADD_MODAL__PREVIEW_CHART =
            By.cssSelector("dialog[aria-label='Panel metric modal'][open] canvas");

    /**
     * The primary submit button — same element in create and update mode.
     * Scoped to the open modal via aria-label on both dialog and button.
     */
    public static final By ADD_MODAL__SUBMIT_BUTTON =
            By.cssSelector("dialog[aria-label='Panel metric modal'][open] button[aria-label='Save Button']");

    /** Alias for create mode readability. */
    public static final By ADD_MODAL__SAVE_BUTTON = ADD_MODAL__SUBMIT_BUTTON;

    /** Alias for update mode readability. */
    public static final By ADD_MODAL__UPDATE_BUTTON = ADD_MODAL__SUBMIT_BUTTON;

    /** "Batal" button inside the add/update modal footer. */
    public static final By ADD_MODAL__CANCEL_BUTTON =
            By.xpath("//dialog[@aria-label='Panel metric modal'][@open]//button[normalize-space(.)='Batal']");

    /**
     * Graph type toggle buttons — scoped to the OPEN modal only.
     */
    public static String graphTypeButton(String type) {
        return String.format(
                "//dialog[@aria-label='Panel metric modal'][@open]//button[normalize-space(.)='%s']",
                type
        );
    }

    // ── DeleteDialog ───────────────────────────────────────────────────────

    /** The delete confirmation <dialog> — aria-label added to Svelte source. */
    public static final By DELETE_DIALOG__DIALOG =
            By.cssSelector("dialog[aria-label='Delete panel modal']");

    /**
     * "Hapus" confirm button — scoped to delete dialog via aria-label.
     */
    public static final By DELETE_DIALOG__CONFIRM_BUTTON =
            By.xpath("//dialog[@aria-label='Delete panel modal']//button[normalize-space(.)='Hapus']");

    /** "Batal" cancel button in the delete dialog. */
    public static final By DELETE_DIALOG__CANCEL_BUTTON =
            By.xpath("//dialog[@aria-label='Delete panel modal']//button[normalize-space(.)='Batal']");

    // ── DetailDialog ───────────────────────────────────────────────────────

    /**
     * The detail <dialog> — identified by its max-w-4xl width class,
     * which only the detail dialog has (add modal uses max-w-3xl).
     */
    public static final By DETAIL_DIALOG__DIALOG =
            By.cssSelector("dialog.max-w-4xl");

    /** Refresh button inside the detail dialog header. */
    public static final By DETAIL_DIALOG__REFRESH_BUTTON =
            By.cssSelector("dialog.max-w-4xl button[aria-label='Refresh']");

    /** Close button inside the detail dialog header. */
    public static final By DETAIL_DIALOG__CLOSE_BUTTON =
            By.cssSelector("dialog.max-w-4xl button[aria-label='Close']");

    /** The data table inside the detail dialog. */
    public static final By DETAIL_DIALOG__TABLE =
            By.cssSelector("dialog.max-w-4xl table");

    /** Loading spinner inside the detail dialog. */
    public static final By DETAIL_DIALOG__LOADING =
            By.cssSelector("dialog.max-w-4xl div.animate-pulse");
}