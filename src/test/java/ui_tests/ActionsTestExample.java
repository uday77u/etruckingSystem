package ui_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import actions.SignupActions;
import base.BaseUITest;
import utilities.MailinatorUtil;

public class ActionsTestExample extends BaseUITest{

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
	public static void main(String[] args) {
		WebDriver driver=new ChromeDriver();
		String inbox = "test" + System.currentTimeMillis();
		String email = inbox + "@mailinator.com";

		boolean status = MailinatorUtil.verifyMailinatorEmail(
		        driver,
		        "testinbox123",          // inbox name only (before @mailinator.com)
		        "Welcome",
		        "Account created successfully"
		);

		System.out.println("Email Verified: " + status);

	}
}
