package utils;

/**
 * Central place for environment-level constants.
 * Keeping this separate from TestData (which holds *test input/expected values*)
 * so environment config and test data don't get mixed together.
 */
public class TestConfig {

    public static final String BASE_URL = "https://uxtrace.vercel.app";
    public static final String QUERY_LOG_PATH = "/query";
    public static final String QUERY_LOG_URL = BASE_URL + QUERY_LOG_PATH;

    public static final long DEFAULT_TIMEOUT_SECONDS = 10;
    public static final long SEARCH_DEBOUNCE_MILLIS = 400; // matches handleSearchInput's setTimeout in +page.svelte

    private TestConfig() {
        // no instances
    }
}