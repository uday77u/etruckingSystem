package utilities;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties properties = new Properties();
    private static volatile boolean isLoaded = false;

    private ConfigManager() {
    }

    /* =========================
       Load Configuration
       ========================= */
    public static synchronized void loadConfig(String env) {

        if (isLoaded) {
            return;
        }

        String fileName = env.toLowerCase() + ".properties";

        try (InputStream is = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException(
                        "Configuration file not found: " + fileName
                );
            }

            properties.load(is);
            isLoaded = true;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load config for environment: " + env, e
            );
        }
    }

    /* =========================
       ENVIRONMENT
       ========================= */

    public static String getBaseUrl() {
        return getRequired("base.url");
    }

    public static String getAuthPath() {
        return getRequired("auth.path");
    }

    /* =========================
       CREDENTIALS
       ========================= */

    public static String getUsername() {
        return getRequired("USER_NAME");
    }

    public static String getUserEmail() {
        return getRequired("USER_EMAIL");
    }

    public static String getPassword() {
        return getRequired("PASSWORD");
    }

    public static String getTempUserEmail() {
        return get("USER_EMAIL_Temp", "temp@email.com");
    }

    /* =========================
       BROWSER
       ========================= */

    public static boolean isHeadlessExecution() {
        return getBoolean("HEADLESS_EXECUTION");
    }

    /* =========================
       Generic Getters
       ========================= */

    public static String get(String key) {
        ensureLoaded();
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        ensureLoaded();
        return properties.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        ensureLoaded();
        return Boolean.parseBoolean(
                properties.getProperty(key, "false")
        );
    }

    /* =========================
       Internal Helpers
       ========================= */

    private static String getRequired(String key) {
        ensureLoaded();
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                    "Required configuration missing: " + key
            );
        }
        return value;
    }

    private static void ensureLoaded() {
        if (!isLoaded) {
            throw new IllegalStateException(
                    "ConfigManager not initialized. Call loadConfig(env) first."
            );
        }
    }
    
    //api
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
