package config;

public final class AppConfig {

    private AppConfig() {
    }

    public static final String APPLICATION_NAME = "RailSync";
    public static final String APPLICATION_VERSION = "1.0.0";

    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 850;

    public static final int MIN_WINDOW_WIDTH = 1100;
    public static final int MIN_WINDOW_HEIGHT = 700;

    /*
     * The Indian Rail API is configured from Settings.
     * No credential is embedded in source code.
     */
    public static final String INDIAN_RAIL_API_HTTP_URL = "";

    public static final String INDIAN_RAIL_API_BASE =
            "http://indianrailapi.com/api/v2/";

    public static final int API_TIMEOUT_SECONDS = 15;
}
