package ui_tests;

import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import actions.SignupActions;
import base.BaseUITest;

public class TestActionsExample extends BaseUITest{

	@Test
	public void test001_actions(){
		logger.info("Execute test Re-usable action methods started");
		/*
		SignupActions actions=new SignupActions(driver);
		logger.info("Create admin account");
		actions.signupAdminUser("firstname", "lastName", "hjhj@jhj.com", "9685214789", "12312123", "Pssas@123OP");
*/
		
		NavigationActions navigationActions=new NavigationActions(driver);
		navigationActions.NavigateAuthToLoginAdmin();
		
		LoginActions loginActions=new LoginActions(driver);
		loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
		loginActions.LogoutAdmin();
		
	}
}
