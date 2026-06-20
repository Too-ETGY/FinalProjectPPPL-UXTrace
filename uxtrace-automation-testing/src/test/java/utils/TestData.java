package utils;

public class TestData {

    public static final String BASE_URL = "http://localhost:5173";

    public static final String ALARM_TITLE = "Alert Click";
    public static final String ALARM_QUERY = "SELECT * FROM events WHERE event_name = 'kur_button_click' WHEN count > 100";
    public static final String ALARM_MESSAGE = "Pesan pemicu click button terdeteksi";
    public static final String ALARM_INTERVAL = "5";
    public static final String ALARM_TELEGRAM = "1484397336";
    public static final String ALARM_SEARCH_KEYWORD = "Alert Click";

    //EDIT DATA
    public static final String ALARM_EDIT_TITLE = "Alert Click Updated";
    public static final String ALARM_EDIT_QUERY = "SELECT * FROM events WHERE event_name = 'kur_button_click' WHEN count > 100";
    public static final String ALARM_EDIT_MESSAGE = "Pesan Notifikasi Hasil Update";
    public static final String ALARM_EDIT_INTERVAL = "10";
    public static final String ALARM_EDIT_TELEGRAM = "1484397336";
}