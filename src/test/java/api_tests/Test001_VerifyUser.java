package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

@Listeners(API_ExtentReportManager.class)
public class Test001_VerifyUser extends BaseAPITest{
	
    private AdminService adminService;

    @BeforeClass
    public void setup() {
        adminService = new AdminService();
    }
	
	@Test(priority = 1, groups = {"smoke", "regression"})
	public void TestVerifyUser_successSchemaValidation() {

		VerifyUserRequestData data=validUser();
		logger.info("Created UserRequestData: "+data);
		
		Response response=adminService.verifyUser(data);
		logger.info("Response "+response.asPrettyString());
		
	    VerifyUserResponseData verifyUserResponseData = response.as(VerifyUserResponseData.class);
	    logger.info("verifyUserResponseData: "+verifyUserResponseData);
	    
	    try {
	        response.then()
	            .body(matchesJsonSchemaInClasspath(
	                "schemas/verify-user-response-schema.json"));
	    } catch (AssertionError e) {
	        System.err.println("❌ Schema validation failed:");
	        System.err.println(e.getMessage());
	        throw e;
	    }

	    
	    logger.info("========== Verify User Response Validation ==========");
		logger.info("Response Status: "+verifyUserResponseData.isStatus());
		assertTrue(verifyUserResponseData.isStatus(), "Success Response status: "  );
		
		logger.info("Response Code: "+verifyUserResponseData.getCode());
		assertNull(verifyUserResponseData.getCode(), "Success Response code: ");
		
		logger.info("Response Message: "+verifyUserResponseData.getMessage());
		assertEquals(verifyUserResponseData.getMessage(), "new user","Mis match in the message: ");
		
		logger.info("Response Error: "+verifyUserResponseData.getError());
		assertNull(verifyUserResponseData.getError(), "Success Response Error: ");
		
		logger.info("Response Description: "+verifyUserResponseData.getDescription());
		assertNull(verifyUserResponseData.getDescription(),"Success Response Description: ");
		
		logger.info("Response Data: "+verifyUserResponseData.getData());
		assertEquals(verifyUserResponseData.getData(), "new user","Success Response Data: ");
		
	}
	
	@Test(priority = 2, groups = {"regression"})
	public void TestVerifyUser_MissingEmail() {
		
		VerifyUserRequestData data = validUser();
		data.setEmail(null);  // ❌ missing email
		logger.info("Created UserRequestData: "+data);

	    Response response = adminService.verifyUser(data);
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

	    logger.info("Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "Invalid email format");  //email: email must be a string--defect
	}

	@Test(priority = 3,groups = {"regression"})
	public void TestVerifyUser_MissingPhone() {
		
		VerifyUserRequestData data = validUser();
		data.setPhone(null);  // ❌ missing phone
		logger.info("Created UserRequestData: "+data);

	    Response response = adminService.verifyUser(data);
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

	    logger.info("Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "Missing phone number"); //phone: phone must be a string --defect
	}
	@Test(priority = 4,groups = {"regression"})
	public void TestVerifyUser_InvalidEmailFormat() {

		VerifyUserRequestData data = validUser();
		data.setEmail("tester@@gmail");  // ❌ invalid email
		logger.info("Created UserRequestData: "+data);

	    Response response = adminService.verifyUser(data);
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

	    logger.info("Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "email: email must be a valid email"); //Invalid email format
	}
	
	@Test(priority = 5,groups = {"regression"})
	public void TestVerifyUser_InvalidPhoneFormat() {

		
		VerifyUserRequestData data = validUser();
		data.setPhone("98ABCD211");  // ❌ invalid phone
		logger.info("Created UserRequestData: "+data);

	    Response response = adminService.verifyUser(data);
	    logger.info("Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "phone: Invalid phone"); //Invalid phone number
	    
	}
	
	@Test(priority = 6,groups = {"regression"})
	public void TestVerifyUser_MinPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("98");  // ⚠️ minimum length
		logger.info("Created UserRequestData: "+data);

	    Response response = adminService.verifyUser(data);
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "phone: phone length must be at least 7 characters long"); //Phone number length must be at least 7 characters long
	    assertEquals(response.getStatusCode(), 400);
	    
	}
	
	@Test(priority = 7,groups = {"regression"})
	public void TestVerifyUser_MaxPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("12345678901234567890");  // ❌ too long
		logger.info("Created UserRequestData: "+data);
		
	    Response response = adminService.verifyUser(data);
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), "too long length phone");
	    assertEquals(response.getStatusCode(), 400);
	}
	
  
	//@Test(priority = 8,groups = {"regression"})
	public void TestVerifyUser_Unauthorized() {

		VerifyUserRequestData data = validUser();
		logger.info("Created UserRequestData: "+data);

	   // Response response = adminService.verifyUserWithoutAuth(data);

	   // assertEquals(response.getStatusCode(), 401);
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
