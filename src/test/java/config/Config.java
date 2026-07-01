package config;

public class Config {

    // Base URLs
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // Load profile defaults
    public static final int DEFAULT_USERS       = 10;
    public static final int RAMP_DURATION_SEC   = 10;
    public static final int TEST_DURATION_SEC   = 60;
    public static final int PEAK_USERS          = 50;

    // Thresholds
    public static final double MAX_RESPONSE_TIME_MS   = 60000;
    public static final double MAX_ERROR_RATE_PERCENT = 1.0;
    public static final double PERCENTILE_95_MS       = 1500;

    private Config() {}
}
