package ui_tests;

import org.testng.annotations.Test;

import base.BaseUITest;

public class Examle extends BaseUITest{

@Test
public  void testExample() throws Exception {
	
 TC002_AdminLogin tc002_AdminLogin=new TC002_AdminLogin();
 tc002_AdminLogin.TestAdminLogin();

	}

}
