package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {

    private static ReadConfig instance;
    private Properties properties;

    private ReadConfig() {
        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            properties = new Properties();
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Singleton instance
    public static ReadConfig getInstance() {
        if (instance == null) {
            synchronized (ReadConfig.class) {
                if (instance == null) {
                    instance = new ReadConfig();
                }
            }
        }
        return instance;
    }

    public String getBaseURL() {
        return properties.getProperty("BASE_URL");
    }

    public String getUsername() {
        return properties.getProperty("USER_NAME");
    }

    public String getUserEmail() {
        return properties.getProperty("USER_EMAIL");
    }

    public String getPassword() {
        return properties.getProperty("PASSWORD");
    }

    public boolean isHeadlessMode() {
        return Boolean.parseBoolean(
                properties.getProperty("HEADLESS_EXECUTION", "false")
        );
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
