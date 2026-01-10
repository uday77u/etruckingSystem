package base;

import utilities.ConfigManager;

public interface FrameWorkConstants {

    /* =========================
       URLs
       ========================= */

    String BASE_URL = ConfigManager.getBaseUrl();
    String AUTH_PATH = ConfigManager.getAuthPath();

    /* =========================
       Credentials
       ========================= */

    String USER_NAME = ConfigManager.getUsername();
    String USER_EMAIL = ConfigManager.getUserEmail();
    String PASSWORD = ConfigManager.getPassword();
    String USER_EMAIL_TEMP = ConfigManager.getTempUserEmail();

    /* =========================
       Browser
       ========================= */

    boolean HEADLESS_EXECUTION = ConfigManager.isHeadlessExecution();

    /* =========================
       Page Titles
       ========================= */

    String HOME_TITLE = "Automation Exercise";
    String SIGNUP_TITLE = "Signup";
    String TESTCASE_TITLE = "test cases";
    String PRODUCTS_TITLE = "all products";
    
    int EXPLICIT_WAIT=20;
}
