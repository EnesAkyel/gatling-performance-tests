package config;

public class Config {

    // Base URLs
    public static final String BASE_URL = System.getenv().getOrDefault("BASE_URL", "http://localhost:8080");

    // Auth credentials
    public static final String AUTH_USERNAME = System.getenv().getOrDefault("AUTH_USERNAME", "");
    public static final String AUTH_PASSWORD = System.getenv().getOrDefault("AUTH_PASSWORD", "");

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
