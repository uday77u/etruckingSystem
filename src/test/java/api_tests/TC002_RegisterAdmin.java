package api_tests;

import org.testng.annotations.BeforeClass;

import base.BaseAPITest;
import services.AdminService;

public class TC002_RegisterAdmin extends BaseAPITest {

    private AdminService adminService;

@BeforeClass
public void setup() {
	super.setup();
    adminService = new AdminService();
}



}
