package ui_BaseClass;

import ui_Utilities.ReadConfig;

public interface FW_Constants {
	//read configuration values
	ReadConfig readConfig=new ReadConfig();
	public String BASE_URL=readConfig.getBaseURL();
	public String USER_NAME=readConfig.getUsername();
	public String USER_EMAIL=readConfig.getUserEmail();
	public String PASSWORD=readConfig.getPassword();
	public Boolean HEADLESS_EXECUTION=readConfig.getHeadlessMode();
	
	public String USER_EMAIL_Temp="tempAbc@gmail.com";
	public final String HOME_TITLE = "Automation Exercise";
	public final String SIGNUP_TITLE = "Signup";
	public final String TESTCASE_TITLE = "test cases";
	public final String PRODUCTS_TITLE = "all products";
}
