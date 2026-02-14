package base;

import utilities.ConfigManager;
import utilities.RandomDataGenerator;

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
    
    /*
	
	//TestData files
	String DLpath=System.getProperty("user.dir")+"/TestData/DlTesting.jpeg";
	String SSNpath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";
	String photopath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";
	String TWICpath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";

*/
	
}
