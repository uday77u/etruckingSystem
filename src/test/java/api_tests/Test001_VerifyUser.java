package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import base.BaseAPITest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;
import utilities.API_ExtentReportManager;

//@Listeners(API_ExtentReportManager.class)
public class Test001_VerifyUser extends BaseAPITest{
	    private AdminService adminService;

    @BeforeClass
    public void setup() {
    	super.setup();
        adminService = new AdminService();
    }
	
	@Test(priority = 1, groups = {"smoke", "regression"})
	public void TestVerifyUser_successSchemaValidation() {

		VerifyUserRequestData data=validUser();
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
		Response response=adminService.verifyUser(data);
		
		logger.info("STEP 3 :HTTP status validation");
		assertStatusCode(response, 200);
		
	    VerifyUserResponseData verifyUserResponseData = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 4 :Recieved response verifyUserResponseData: "+verifyUserResponseData);
	   
	    response.then().body(matchesJsonSchemaInClasspath("schemas/verify-user-response-schema.json"));

	    logger.info("========== Verify User Response Validation(Business assertions) ==========");
        
		logger.info("STEP 5 :Verify Response Status: "+verifyUserResponseData.isStatus());
		assertTrue(verifyUserResponseData.isStatus(), "Status should be true, success response status: "  );
		
		logger.info("STEP 6 :Verify Response Code: "+verifyUserResponseData.getCode());
		assertNull(verifyUserResponseData.getCode(), "Code should be null on success, success response code: ");
		
		logger.info("STEP 7 :Verify Response Message: "+verifyUserResponseData.getMessage());
		assertNotNull(verifyUserResponseData.getMessage(), "Message should not be null");
		assertEquals(verifyUserResponseData.getMessage(), "new user","Mis match in the message: ");
		
		logger.info("STEP 8 :Verify Response Error: "+verifyUserResponseData.getError());
		assertNull(verifyUserResponseData.getError(), "Error should be null on success, success response Error: ");
		
		logger.info("STEP 9 :Verify Response Description: "+verifyUserResponseData.getDescription());
		assertNull(verifyUserResponseData.getDescription(),"Description should be null on success, success response Description: ");
		
		logger.info("STEP 10 :Verify Response Data: "+verifyUserResponseData.getData());
		assertNotNull(verifyUserResponseData.getData(), "Data should not be null");
		assertEquals(verifyUserResponseData.getData(), "new user","Success Response Data: ");
		
	}
	
	@Test(priority = 2, groups = {"regression"})
	public void TestVerifyUser_MissingEmail() {
		
		VerifyUserRequestData data = validUser();
		data.setEmail(null);  // ❌ missing email
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Send verifyUser request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response UserRequestData: "+data);
	    
	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), MISSING_EMAIL);  //email: email must be a string--defect
	}

	@Test(priority = 3,groups = {"regression"})
	public void TestVerifyUser_MissingPhone() {
		
		VerifyUserRequestData data = validUser();
		data.setPhone(null);  // ❌ missing phone
		logger.info("STEP 1 :Created UserRequestData: "+data);

		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), MISSING_PHONE_NUMBER); //phone: phone must be a string --defect
	}
	
	@Test(priority = 4,groups = {"regression"})
	public void TestVerifyUser_InvalidEmailFormat() {

		VerifyUserRequestData data = validUser();
		data.setEmail("tester@@gmail");  // ❌ invalid email
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), INVALID_EMAIL); //Invalid email format
	}
	
	@Test(priority = 5,groups = {"regression"})
	public void TestVerifyUser_InvalidPhoneFormat() {

		
		VerifyUserRequestData data = validUser();
		data.setPhone("98ABCD211");  // ❌ invalid phone
		logger.info("STEP 1 :Created UserRequestData: "+data);

		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);
	    
	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);

	    logger.info("STEP 5 :Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), INVALID_PHONE); //Invalid phone number
	    
	}
	
	@Test(priority = 6,groups = {"regression"})
	public void TestVerifyUser_MinPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("98");  // ⚠️ minimum length
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    
	    logger.info("STEP 3 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 4 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), PHONE_LENGTH_LEAST7); //Phone number length must be at least 7 characters long
	    assertEquals(response.getStatusCode(), 400);
	    
	}
	
	@Test(priority = 7,groups = {"regression"})
	public void TestVerifyUser_MaxPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("12345678901234567890");  // ❌ too long
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    
	    logger.info("STEP 3 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 4 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), PHONE_LENGTH_MOST10);
	    assertEquals(response.getStatusCode(), 400);
	}
	
  
	//@Test(priority = 8,groups = {"regression"})
	public void TestVerifyUser_Unauthorized() {

		VerifyUserRequestData data = validUser();
		logger.info("Created UserRequestData: "+data);

	   // Response response = adminService.verifyUserWithoutAuth(data);

	    //assertEquals(response.getStatusCode(), 401);
	}

	private VerifyUserRequestData validUser() {
	    return new VerifyUserRequestData(
	        "Tester",
	        "LastName",
	        "tester@gmail.com",
	        "IN",
	        "+91",
	        "9876543211",
	        "253"
	    );
	}



	
}
