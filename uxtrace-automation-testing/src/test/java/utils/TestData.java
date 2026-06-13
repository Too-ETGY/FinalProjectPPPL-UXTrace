package utils;

public class TestData {

    // 1. DATA UNTUK MODUL DASHBOARD (CREATE & UPDATE PANEL)
    public static final String DASHBOARD_PANEL_NAME_LINE = "Line Traffic";
    public static final String DASHBOARD_PANEL_NAME_BAR  = "Bar Traffic";
    public static final String DASHBOARD_PANEL_NAME_PIE  = "Pie Traffic";

    // Teks Query SQL panjang untuk membuat panel di Dashboard
    public static final String DASHBOARD_CREATE_QUERY =
            "SELECT * FROM events WHERE event_name = 'kur_page_stay' " +
                    "AND btm IN ('a345.b432.c120.d0', 'a345.b432.c121.d0', 'a345.b432.c122.d0')";

    // Event type untuk testing update panel fungsional
    public static final String DASHBOARD_UPDATE_EVENT_LINE = "kur_button_click";
    public static final String DASHBOARD_UPDATE_EVENT_BAR  = "kur_page_enter";
    public static final String DASHBOARD_UPDATE_EVENT_PIE  = "kur_page_stay";


    // 2. DATA UNTUK MODUL ALARM (ALARM MANAGEMENT)
    public static final String ALARM_TITLE      = "Button Alert";

    // Teks Query SQL panjang untuk form konfigurasi Alarm
    public static final String ALARM_QUERY      =
            "SELECT * FROM events WHERE event_name = 'kur_button_click' WHEN count > 100";

    public static final String ALARM_INTERVAL   = "5";
    public static final String ALARM_TELEGRAM   = "1484397336";

    // Data untuk skenario pencarian (Search Alarm)
    public static final String ALARM_SEARCH_KEYWORD = "Button Alert";

    // TAMBAHKAN DATA BARU UNTUK SCENARIO EDIT DI SINI:
    public static final String ALARM_EDIT_TITLE    = "Button Alert Updated";
    public static final String ALARM_EDIT_INTERVAL = "10"; // Mengubah interval menjadi 10 menit
    public static final String ALARM_EDIT_QUERY    = "SELECT * FROM events WHERE event_name = 'enter_page' AND btm = 'a345.b432.c120.d0'";
}